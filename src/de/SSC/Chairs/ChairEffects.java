// 
// Decompiled by Procyon v0.5.36
// 

package de.SSC.Chairs;

import org.bukkit.plugin.Plugin;
import org.bukkit.attribute.Attribute;
import org.bukkit.Bukkit;

public class ChairEffects
{
    protected final ChairsSystem plugin;
    protected final ChairsConfig config;
    protected final PlayerSitData sitdata;
    protected int healTaskID;
    protected int pickupTaskID;
    
    public ChairEffects(final ChairsSystem aplugin) {
        this.healTaskID = -1;
        this.pickupTaskID = -1;
        this.plugin = aplugin;
        this.config = plugin.getChairsConfig();
        this.sitdata = plugin.getPlayerSitData();
    }
    
    protected void startHealing() {

         
        
        this.healTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, () -> Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission("chairs.sit.health")).filter(this.plugin.getPlayerSitData()::isSitting).forEach(p -> {
        	double health = p.getHealth();
        	double maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        	double newHealth;
        	
            if (health / maxHealth * 100.0 < this.config.effectsHealMaxHealth && health < maxHealth) {
                newHealth = this.config.effectsHealHealthPerInterval + health;
                if (newHealth > maxHealth) {
                    newHealth = maxHealth;
                }
                p.setHealth(newHealth);
            }
        }), (long)this.config.effectsHealInterval, (long)this.config.effectsHealInterval);
    }
    
    public void cancelHealing() {
        if (this.healTaskID != -1) {
            Bukkit.getServer().getScheduler().cancelTask(this.healTaskID);
            this.healTaskID = -1;
        }
    }
    
    public void restartHealing() {
        this.cancelHealing();
        this.startHealing();
    }
    
    protected void startPickup() 
    {       
    	return;
    	/*
    	
        this.pickupTaskID = this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, () -> Bukkit.getOnlinePlayers().stream().filter(this.plugin.getPlayerSitData()::isSitting).forEach(p -> {
          
        	final Iterator<Entity> iterator = p.iterator(); 
        	  EntityPickupItemEvent pickupevent;
        	  Entity entity;
              Item item;
              
              ExperienceOrb eorb;
              int exptoadd;
              int localexptoadd;
              int localexptoadd2;
              PlayerExpChangeEvent expchangeevent;
              PlayerLevelChangeEvent levelchangeevent;
              PlayerExpChangeEvent expchangeevent2;
        	  
        	p.getNearbyEntities(1.0, 2.0, 1.0).iterator();
        	
        	
            while (iterator.hasNext()) 
            {
                entity = iterator.next();
                if (entity instanceof Item) {
                    item = (Item)entity;
                    if (item.getPickupDelay() == 0 && p.getInventory().firstEmpty() != -1) {
                        pickupevent = new EntityPickupItemEvent((LivingEntity)p, item, 0);
                        Bukkit.getPluginManager().callEvent((Event)pickupevent);
                        if (!pickupevent.isCancelled()) {
                            p.getInventory().addItem(new ItemStack[] { item.getItemStack() });
                            entity.remove();
                        }
                        else {
                            continue;
                        }
                    }
                    else {
                        continue;
                    }
                }
                else if (entity instanceof ExperienceOrb) {
                    eorb = (ExperienceOrb)entity;
                    for (exptoadd = eorb.getExperience(); exptoadd > 0; exptoadd -= localexptoadd2) {
                        localexptoadd = 0;
                        if (p.getExpToLevel() < exptoadd) {
                            localexptoadd2 = p.getExpToLevel();
                            expchangeevent = new PlayerExpChangeEvent(p, localexptoadd2);
                            Bukkit.getPluginManager().callEvent((Event)expchangeevent);
                            p.giveExp(expchangeevent.getAmount());
                            if (p.getExpToLevel() <= 0) {
                                levelchangeevent = new PlayerLevelChangeEvent(p, p.getLevel(), p.getLevel() + 1);
                                Bukkit.getPluginManager().callEvent((Event)levelchangeevent);
                                p.setExp(0.0f);
                                p.giveExpLevels(1);
                            }
                        }
                        else {
                            localexptoadd2 = exptoadd;
                            expchangeevent2 = new PlayerExpChangeEvent(p, localexptoadd2);
                            Bukkit.getPluginManager().callEvent((Event)expchangeevent2);
                            p.giveExp(expchangeevent2.getAmount());
                        }
                    }
                    entity.remove();
                }
                else {
                    continue;
                }
            }
        }), 1L, 1L);
        
        */
    }
    
    public void cancelPickup() {
        if (this.pickupTaskID != -1) {
            Bukkit.getServer().getScheduler().cancelTask(this.pickupTaskID);
        }
        this.pickupTaskID = -1;
    }
    
    public void restartPickup() {
        this.cancelPickup();
        this.startPickup();
    }
}
