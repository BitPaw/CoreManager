package de.SSC.CoreManager.Addons.DropHead;


import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.SSC.CoreManager.Utility.BukkitUtility;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class HeadAPI 
{
	  final boolean grummEnabled;
	  final boolean updateOldPlayerHeads;
	  final TreeMap<String, String> textures;
	  
	  public HeadAPI()
	  {
	    this.textures = new TreeMap<String, String>();
	    this.grummEnabled = true;
	    this.updateOldPlayerHeads =  true;	    
	  }
	  
	  @SuppressWarnings("rawtypes")
	void loadTextures(String headsList)
	  {
	    String[] arrayOfString;
	    int j = (arrayOfString = headsList.split("\n")).length;
	    
	    for (int i = 0; i < j; i++)
	    {
	      String head = arrayOfString[i];
	      head = head.replaceAll(" ", "");
	      i = head.indexOf(":");
	      if (i != -1) {
	        try
	        {
	          String texture = head.substring(i + 1);
	          if (!texture.isEmpty())
	          {
	            String key = head.substring(0, i);
	            j = key.indexOf('|');
	            
	            if (j == -1)
	            {
	              EntityType type = EntityType.valueOf(key.toUpperCase());
	              this.textures.put(type.name(), texture);
	            }
	            else
	            {
	              this.textures.put(key, texture);
	              
	              //this.pl.getLogger().fine("Loaded: " + type.name() + " - " + key.substring(j + 1));
	            }
	          }
	        }
	        catch (IllegalArgumentException ex)
	        {
	          //this.pl.getLogger().warning("Invalid entity name '" + head.substring(0, i) + "' from head-list.txt file!");
	        }
	      }
	    }
	    Iterator<Map.Entry<String, String>> it = this.textures.entrySet().iterator();
	    String redirect;
	    
	    while(it.hasNext())
	    {
	    	
	    	
	    	Object e = (Map.Entry)it.next();
		      redirect = (String)((Map.Entry)e).getValue();
		      
		    	redirect = (String)textures.get(redirect);
		      
		      continue;
	    	
		      //((Map.Entry)e).setValue(redirect);
	
	    }	   
	  }
	  
	  public boolean textureExists(String textureKey)
	  {
	    return this.textures.containsKey(textureKey);
	  }
	  
	  public TreeMap<String, String> getTextures()
	  {
	    return this.textures;
	  }
	  
	  public ItemStack makeTextureSkull(String textureKey)
	  {
	    int j = textureKey.indexOf('|');
	    String nameStr = (j == -1 ? textureKey : textureKey.substring(0, j)).toUpperCase();
	    EntityType eType;
	   
	    try
	    {
	      eType = EntityType.valueOf(nameStr);
	    }
	    catch (IllegalArgumentException ex)
	    {
	      //this.pl.getLogger().warning("Unknown EntityType: " + nameStr + "!");
	      eType = null;
	    }
	    ItemStack item = new ItemStack(Material.PLAYER_HEAD);
	    SkullMeta meta = (SkullMeta)item.getItemMeta();
	    
	    UUID uuid = UUID.nameUUIDFromBytes(textureKey.getBytes());
	    GameProfile profile = new GameProfile(uuid, textureKey);
	    String code = (String)this.textures.get(textureKey);
	    if (code != null) {
	      profile.getProperties().put("textures", new Property("textures", code));
	    }
	    HeadUtils.setGameProfile(meta, profile);
	    
	    meta.setDisplayName(ChatColor.YELLOW + DropHeadTextureLookUp.getNameFromKey(eType, textureKey) + " Head");
	    item.setItemMeta(meta);
	    return item;
	  }
	  
	  public ItemStack getHead(EntityType eType, String textureKey)
	  {
	    if ((textureKey != null) && (textureKey.indexOf('|') != -1) && (this.textures.containsKey(textureKey))) {
	      return makeTextureSkull(textureKey);
	    }
	    switch (eType)
	    {
	    case ZOMBIE_HORSE: 
	      return new ItemStack(Material.PLAYER_HEAD);
	    case BLAZE: 
	      return new ItemStack(Material.WITHER_SKELETON_SKULL);
	    case MINECART: 
	      return new ItemStack(Material.SKELETON_SKULL);
	    case MINECART_FURNACE: 
	      return new ItemStack(Material.ZOMBIE_HEAD);
	    case MAGMA_CUBE: 
	      return new ItemStack(Material.CREEPER_HEAD);
	    case PARROT: 
	      return new ItemStack(Material.DRAGON_HEAD);
		case AREA_EFFECT_CLOUD:
			break;
		case ARMOR_STAND:
			break;
		case ARROW:
			break;
		case BAT:
			break;
		case BOAT:
			break;
		case CAT:
			break;
		case CAVE_SPIDER:
			break;
		case CHICKEN:
			break;
		case COD:
			break;
		case COW:
			break;
		case CREEPER:
			break;
		case DOLPHIN:
			break;
		case DONKEY:
			break;
		case DRAGON_FIREBALL:
			break;
		case DROPPED_ITEM:
			break;
		case DROWNED:
			break;
		case EGG:
			break;
		case ELDER_GUARDIAN:
			break;
		case ENDERMAN:
			break;
		case ENDERMITE:
			break;
		case ENDER_CRYSTAL:
			break;
		case ENDER_DRAGON:
			break;
		case ENDER_PEARL:
			break;
		case ENDER_SIGNAL:
			break;
		case EVOKER:
			break;
		case EVOKER_FANGS:
			break;
		case EXPERIENCE_ORB:
			break;
		case FALLING_BLOCK:
			break;
		case FIREBALL:
			break;
		case FIREWORK:
			break;
		case FISHING_HOOK:
			break;
		case FOX:
			break;
		case GHAST:
			break;
		case GIANT:
			break;
		case GUARDIAN:
			break;
		case HORSE:
			break;
		case HUSK:
			break;
		case ILLUSIONER:
			break;
		case IRON_GOLEM:
			break;
		case ITEM_FRAME:
			break;
		case LEASH_HITCH:
			break;
		case LIGHTNING:
			break;
		case LLAMA:
			break;
		case LLAMA_SPIT:
			break;
		case MINECART_CHEST:
			break;
		case MINECART_COMMAND:
			break;
		case MINECART_HOPPER:
			break;
		case MINECART_MOB_SPAWNER:
			break;
		case MINECART_TNT:
			break;
		case MULE:
			break;
		case MUSHROOM_COW:
			break;
		case OCELOT:
			break;
		case PAINTING:
			break;
		case PANDA:
			break;
		case PHANTOM:
			break;
		case PIG:
			break;
		case PIG_ZOMBIE:
			break;
		case PILLAGER:
			break;
		case PLAYER:
			break;
		case POLAR_BEAR:
			break;
		case PRIMED_TNT:
			break;
		case PUFFERFISH:
			break;
		case RABBIT:
			break;
		case RAVAGER:
			break;
		case SALMON:
			break;
		case SHEEP:
			break;
		case SHULKER:
			break;
		case SHULKER_BULLET:
			break;
		case SILVERFISH:
			break;
		case SKELETON:
			break;
		case SKELETON_HORSE:
			break;
		case SLIME:
			break;
		case SMALL_FIREBALL:
			break;
		case SNOWBALL:
			break;
		case SNOWMAN:
			break;
		case SPECTRAL_ARROW:
			break;
		case SPIDER:
			break;
		case SPLASH_POTION:
			break;
		case SQUID:
			break;
		case STRAY:
			break;
		case THROWN_EXP_BOTTLE:
			break;
		case TRADER_LLAMA:
			break;
		case TRIDENT:
			break;
		case TROPICAL_FISH:
			break;
		case TURTLE:
			break;
		case UNKNOWN:
			break;
		case VEX:
			break;
		case VILLAGER:
			break;
		case VINDICATOR:
			break;
		case WANDERING_TRADER:
			break;
		case WITCH:
			break;
		case WITHER:
			break;
		case WITHER_SKELETON:
			break;
		case WITHER_SKULL:
			break;
		case WOLF:
			break;
		case ZOMBIE:
			break;
		case ZOMBIE_VILLAGER:
			break;
		default:
			break;
	    }
	    if (this.textures.containsKey(eType.name())) {
	      return makeTextureSkull(eType.name());
	    }
	    return HeadUtils.makeSkull(eType);
	  }
	  
	  public ItemStack getHead(LivingEntity entity)
	  {
	    if (entity.getType() == EntityType.PLAYER) {
	      return HeadUtils.getPlayerHead((OfflinePlayer)entity);
	    }
	    String textureKey = DropHeadTextureLookUp.getTextureKey(entity);
	    if ((this.grummEnabled) && (EvUtils.hasGrummName(entity)) && (this.textures.containsKey(textureKey + "|GRUMM"))) {
	      return makeTextureSkull(textureKey + "|GRUMM");
	    }
	    return getHead(entity.getType(), textureKey);
	  }
	  
	  public ItemStack getHead(GameProfile profile)
	  {
	    if ((profile == null) || (profile.getName() == null)) {
	      return null;
	    }
	    if (textureExists(profile.getName())) {
	      return makeTextureSkull(profile.getName());
	    }
	    BukkitUtility bukkitUtility = BukkitUtility.Instance();
	 
	    
	    OfflinePlayer p = bukkitUtility.PlayerUtility.GetOfflinePlayer(profile.getId());
	    if ((p != null) && (p.getName() != null) && (
	      (this.updateOldPlayerHeads) || (p.getName().equals(profile.getName())))) {
	      return HeadUtils.getPlayerHead(p);
	    }
	    return HeadUtils.getPlayerHead(profile);
	  }
}
