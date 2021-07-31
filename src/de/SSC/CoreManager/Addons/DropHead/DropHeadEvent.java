package de.SSC.CoreManager.Addons.DropHead;

import java.util.Iterator;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import com.mojang.authlib.GameProfile;

public class DropHeadEvent 
{
	
	private HeadAPI _headAPI;
	//private BukkitUtility _bukkitUtility;
	private DropHeadConfig _dropHeadConfig;

	public DropHeadEvent()
	{
		  _headAPI = new HeadAPI();		  
		  //_bukkitUtility = BukkitUtility.Instance();	
		  
		  _dropHeadConfig = new DropHeadConfig();
	}
	 
	  private long timeSinceLastPlayerDamage(Entity entity)
	  {
	    long lastDamage = entity.hasMetadata("PlayerDamage") ? ((MetadataValue)entity.getMetadata("PlayerDamage").get(0)).asLong() : 0L;
	    return System.currentTimeMillis() - lastDamage;
	  }	  
	
	  public void onBlockBreakEvent(BlockBreakEvent evt)
	  {
		  
		  Player player = evt.getPlayer();
		  GameMode gamemode = player.getGameMode();
		  boolean isCanceled = evt.isCancelled();
		  boolean isCreative = gamemode == GameMode.CREATIVE;
			
			 if (isCanceled || isCreative || (!EvUtils.isPlayerHead(evt.getBlock().getType()))) 
			 {
			  return;
			 }
	    Skull skull = (Skull)evt.getBlock().getState();
	    GameProfile profile = HeadUtils.getGameProfile(skull);
	    if (profile != null)
	    {
	      evt.setCancelled(true);
	      evt.getBlock().setType(Material.AIR);
	      evt.getBlock().getWorld().dropItemNaturally(skull.getLocation(), _headAPI.getHead(profile));
	    }
	  }
	  
	  public void entityDamageEvent(EntityDamageByEntityEvent evt)
	  {		  
	    if (evt.isCancelled()) 
	    {
	      return;
	    }
	    if (((evt.getDamager() instanceof Player)) || ((_dropHeadConfig.allowProjectileKills) && ((evt.getDamager() instanceof Projectile)) && 
	      ((((Projectile)evt.getDamager()).getShooter() instanceof Player)))) {
	      evt.getEntity().setMetadata("PlayerDamage", new FixedMetadataValue(null, Long.valueOf(System.currentTimeMillis())));
	    }
	  }
	  
	  public void entityDeathEvent(EntityDeathEvent evt)
	  {
		  double dropChance;		  
		    double lootBonus = 0.0D;double toolBonus = 0.0D;
		    LivingEntity victim = evt.getEntity();
		    
		    
	    double spawnCauseModifier = victim.hasMetadata("SpawnReason") ? 
	      ((MetadataValue)victim.getMetadata("SpawnReason").get(0)).asDouble() : 1.0D;
	    if ((_dropHeadConfig.playerHeadsOnly) && (!(victim instanceof Player))) {
	      return;
	    }
	    Entity killer = null;
	    EntityDamageEvent lastDamage = victim.getLastDamageCause();
	   
	 
	   
	    
	    
	    if ((lastDamage != null) && ((lastDamage instanceof EntityDamageByEntityEvent)))
	    {
	      killer = ((EntityDamageByEntityEvent)lastDamage).getDamager();

	      
	      if ((_dropHeadConfig.chargedCreepers) && ((killer instanceof Creeper)) && (((Creeper)killer).isPowered()))
	      {
	        dropChance = spawnCauseModifier = 1.0D;
	      }
	      else
	      {
	        if ((_dropHeadConfig.playerKillsOnly) && (!(killer instanceof Player)) && 
	          ((!_dropHeadConfig.allowProjectileKills) || (!(killer instanceof Projectile)) || 
	          (!(((Projectile)killer).getShooter() instanceof Player))) && (
	          (!_dropHeadConfig.allowIndirectKills) || (timeSinceLastPlayerDamage(victim) >= 60000L))) {
	          return;
	        }
	        if (!killer.hasPermission("dropheads.canbehead")) {
	          return;
	        }
	        ItemStack heldItem = null;
	        if ((killer instanceof LivingEntity))
	        {
	          heldItem = ((LivingEntity)killer).getEquipment().getItemInMainHand();
	          if ((heldItem != null) && (!_dropHeadConfig.noLootingEffectMobs.contains(victim.getType())))
	          {
	            lootBonus = heldItem.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS) * _dropHeadConfig.lootingBonus;
	            if (_dropHeadConfig.toolBonuses.containsKey(heldItem.getType())) {
	              toolBonus = ((Double)_dropHeadConfig.toolBonuses.get(heldItem.getType())).doubleValue();
	            }
	          }
	        }
	        if ((!_dropHeadConfig.mustUseTools.isEmpty()) && ((heldItem == null) || (!_dropHeadConfig.mustUseTools.contains(heldItem.getType())))) {
	          return;
	        }
	        
	        
	 
	        
	        if (killer.hasPermission("dropheads.alwaysbehead")) {
	          dropChance = spawnCauseModifier = 1.0D;
	        } else {
	          dropChance = ((Double)_dropHeadConfig.mobChances.getOrDefault(victim.getType(), Double.valueOf(0.0D))).doubleValue();
	        }
	      }
	    }
	    else
	    {
	      if (_dropHeadConfig.playerKillsOnly) {
	        return;
	      }
	      dropChance = ((Double)_dropHeadConfig.mobChances.getOrDefault(victim.getType(), Double.valueOf(_dropHeadConfig.DEFAULT_CHANCE))).doubleValue();
	    }
	    dropChance *= spawnCauseModifier;
	    if (_dropHeadConfig.useTaylorModifiers)
	    {
	      toolBonus = Math.pow(2.0D, toolBonus * dropChance) - 1.0D;
	      lootBonus = Math.pow(2.0D, lootBonus * dropChance) - 1.0D;
	      dropChance += (lootBonus == 0.0D ? 0.0D : lootBonus * (1.0D - dropChance) / (lootBonus + 1.0D));
	      dropChance += (toolBonus == 0.0D ? 0.0D : toolBonus * (1.0D - dropChance) / (toolBonus + 1.0D));
	    }
	    else
	    {
	      dropChance += lootBonus * dropChance + toolBonus * dropChance;
	    }
	    Iterator<ItemStack> it = evt.getDrops().iterator();
	    while (it.hasNext()) {
	      if (EvUtils.isHead(((ItemStack)it.next()).getType())) {
	        it.remove();
	      }
	    }
	    if (_dropHeadConfig.rand.nextDouble() < dropChance)
	    {
	      victim.getWorld().dropItem(victim.getLocation(),_headAPI.getHead(victim));
	      
	      //this.pl.getLogger().info("Head dropped!\nDrop chance before tool modifiers: " + rawDropChance + 
	      //  "\nDrop chance after tool modifiers: " + dropChance + 
	      //  "\nMob killed: " + victim.getType().name());
	    }
	  }

	  
		  

		  

		  public void onBarf(ItemSpawnEvent evt)
		  {
		    if ((evt.isCancelled()) || (!EvUtils.isPlayerHead(evt.getEntity().getItemStack().getType())) || 
		      (!evt.getEntity().getItemStack().hasItemMeta())) {
		      return;
		    }
		    SkullMeta meta = (SkullMeta)evt.getEntity().getItemStack().getItemMeta();
		    String name = (meta.hasDisplayName()) ? meta.getDisplayName() : null;
		    GameProfile profile = HeadUtils.getGameProfile(meta);
		    if (profile != null)
		    {
		      ItemStack refreshedItem = _headAPI.getHead(profile);
		      if (name != null)
		      {
		        ItemMeta newMeta = refreshedItem.getItemMeta();
		        newMeta.setDisplayName(name);
		        refreshedItem.setItemMeta(newMeta);
		      }
		      evt.getEntity().setItemStack(refreshedItem);
		    }
		  }
	  
}
