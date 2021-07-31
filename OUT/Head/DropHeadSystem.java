package de.BitFire.Head;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import de.BitFire.API.Bukkit.BukkitAPIPlayer;
import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Module;
import de.BitFire.Core.Exception.NotForConsoleException;

public class DropHeadSystem extends BaseSystem implements ISystem
{	
	private static DropHeadSystem _instance;
	
	public DropHeadEvent Event;	
	
	private final boolean grummEnabled;
	  //private final boolean updateOldPlayerHeads;
	  private final TreeMap<String, String> textures;
	
	  private static final String[] MHF_Heads;
	    private static final Map<String, String> MHF_Lookup;
	    private static final HashMap<EntityType, String> customHeads;
	    private static Field fieldProfileItem;
	    
	    static {
	        MHF_Heads = new String[] { "MHF_Alex", "MHF_Blaze", "MHF_CaveSpider", "MHF_Chicken", "MHF_Cow", "MHF_Creeper", "MHF_Enderman", "MHF_Ghast", "MHF_Golem", "MHF_Herobrine", "MHF_LavaSlime", "MHF_MushroomCow", "MHF_Ocelot", "MHF_Pig", "MHF_PigZombie", "MHF_Sheep", "MHF_Skeleton", "MHF_Slime", "MHF_Spider", "MHF_Squid", "MHF_Steve", "MHF_Villager", "MHF_Witch", "MHF_Wither", "MHF_WSkeleton", "MHF_Zombie", "MHF_Cactus", "MHF_Cake", "MHF_Chest", "MHF_CoconutB", "MHF_CoconutG", "MHF_Melon", "MHF_OakLog", "MHF_Present1", "MHF_Present2", "MHF_Pumpkin", "MHF_TNT", "MHF_TNT2", "MHF_ArrowUp", "MHF_ArrowDown", "MHF_ArrowLeft", "MHF_ArrowRight", "MHF_Exclamation", "MHF_Question" };
	        MHF_Lookup = Stream.of(MHF_Heads).collect(Collectors.toMap(h -> h.toUpperCase(), h -> h));
	        (customHeads = new HashMap<EntityType, String>()).put(EntityType.BAT, "ManBatPlaysMC");
	        customHeads.put(EntityType.ELDER_GUARDIAN, "MHF_EGuardian");
	        customHeads.put(EntityType.ENDERMITE, "MHF_Endermites");
	        customHeads.put(EntityType.EVOKER, "MHF_Evoker");
	        customHeads.put(EntityType.GUARDIAN, "MHF_Guardian");
	        customHeads.put(EntityType.HORSE, "gavertoso");
	        customHeads.put(EntityType.PARROT, "MHF_Parrot");
	        customHeads.put(EntityType.POLAR_BEAR, "NiXWorld");
	        customHeads.put(EntityType.RABBIT, "MHF_Rabbit");
	        customHeads.put(EntityType.SHULKER, "MHF_Shulker");
	        customHeads.put(EntityType.SILVERFISH, "MHF_Silverfish");
	        customHeads.put(EntityType.VEX, "MHF_Vex");
	        customHeads.put(EntityType.VINDICATOR, "Vindicator");
	        customHeads.put(EntityType.SNOWMAN, "MHF_SnowGolem");
	        customHeads.put(EntityType.WITCH, "MHF_Witch");
	        customHeads.put(EntityType.WOLF, "MHF_Wolf");
	        customHeads.put(EntityType.ZOMBIE_VILLAGER, "scraftbrothers11");
	    }
	    
	  
	  
	private DropHeadSystem()
	{	
		super(Module.DropHead, SystemState.Active, Priority.Low);
		_instance = this;
		
		Event = new DropHeadEvent(this);
		
		
		   textures = new TreeMap<String, String>();
		    grummEnabled = true;
		    //updateOldPlayerHeads =  true;	  
	}
	  
	public static DropHeadSystem Instance()
	{
		return _instance == null ? new DropHeadSystem() : _instance;
	}	  
	
	public void GetHeadCommand(CommandSender sender, String[] args) throws NotForConsoleException
	{
		final boolean isSenderPlayer = sender instanceof Player;	
		int i;
		String headName;
		String headStr;  
		String extraData;
		String target;	
		ItemStack head = null;
		 OfflinePlayer p;
		
		if (isSenderPlayer)
		{
			   headStr = args.length == 0 ? sender.getName() : String.join("_", args).replace(':', '|');
			    
			    i = headStr.indexOf('|');			  
			    
			    if (i != -1)
			    {
			      target = headStr.substring(0, i);
			      extraData = headStr.substring(i + 1).toUpperCase();
			    }
			    else
			    {
			      target = headStr;
			      extraData = null;
			    }
			   
			    
			    EntityType eType = EvUtils.getEntityByName(target.toUpperCase());
			    String textureKey = eType.name() + "|" + extraData;
			    
			    if (((eType != null) && (eType != EntityType.UNKNOWN)) || (DoesTextureExists(textureKey)))
			    {
			      if (extraData != null)
			      {
			        if (DoesTextureExists(textureKey)) 
			        {
			          sender.sendMessage(ChatColor.GRAY + "Getting entity head with data value: " + extraData);
			        } 
			        else 
			        {
			          sender.sendMessage(ChatColor.RED + "Unknown data value for " + eType + ": " + ChatColor.YELLOW + extraData);
			        }
			      }
			      head = getHead(eType, textureKey);
			    }
			    else
			    {
			    	
			    	
			      p = BukkitAPIPlayer.GetOfflinePlayer(target);;
			      
			      if ((p.hasPlayedBefore()) || (EvUtils.checkExists(p.getName())))
			      {
			        head = getPlayerHead(p);
			      }
			      else if ((target.startsWith("MHF_")) && (MHF_Lookup.containsKey(target.toUpperCase())))
			      {
			        head = new ItemStack(Material.PLAYER_HEAD);
			        SkullMeta meta = (SkullMeta)head.getItemMeta();
			        //meta.setOwner(target);
			        meta.setDisplayName(ChatColor.YELLOW + (String)MHF_Lookup.get(target.toUpperCase()));
			        head.setItemMeta(meta);
			      }
			      else if (target.length() > 100)
			      {
			        head = makeSkull(target);
			      }
			    }
			    if (head != null)
			    {
			      headName = (head.hasItemMeta()) && (head.getItemMeta().hasDisplayName()) ? 
			        head.getItemMeta().getDisplayName() : head.getType().name(); // maybe wrong
			      ((Player)sender).getInventory().addItem(new ItemStack[] { head });
			      sender.sendMessage(ChatColor.GREEN + "Spawned Head: " + ChatColor.GOLD + headName);
			    }
			    else
			    {
			      sender.sendMessage(ChatColor.RED + "Head \"" + target + "\" not found");
			    }
		}
		else
		{
			throw new NotForConsoleException();
		}
	}

	 public ItemStack getHead(LivingEntity entity)
	 {
		 if (entity.getType() == EntityType.PLAYER) 
		 {
			 return getPlayerHead((OfflinePlayer)entity);
		 }
		 String textureKey = DropHeadTextureLookUp.getTextureKey(entity);
		 
		 if ((this.grummEnabled) && (EvUtils.hasGrummName(entity)) && (this.textures.containsKey(textureKey + "|GRUMM"))) {
	      return makeTextureSkull(textureKey + "|GRUMM");
		 }
		 return getHead(entity.getType(), textureKey);
	  }
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  

	  
	  private boolean DoesTextureExists(String textureKey)
	  {
	    return textures.containsKey(textureKey);
	  }
	  
	  private ItemStack makeTextureSkull(String textureKey)
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
	    setGameProfile(meta, profile);
	    
	    meta.setDisplayName(ChatColor.YELLOW + DropHeadTextureLookUp.getNameFromKey(eType, textureKey) + " Head");
	    item.setItemMeta(meta);
	    return item;
	  }
	  
	  private ItemStack getHead(EntityType eType, String textureKey)
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
	    return makeSkull(eType);
	  }
	  

	  

	  
	  
	  
	  
	  
	  
	  
	  
	  
	    private ItemStack getPlayerHead(final GameProfile profile) {
	        final ItemStack head = new ItemStack(Material.PLAYER_HEAD);
	        final SkullMeta meta = (SkullMeta)head.getItemMeta();
	        setGameProfile(meta, profile);
	        if (profile.getName() != null) {
	            if (profile.getName().startsWith("MHF_")) {
	                meta.setDisplayName(ChatColor.YELLOW + profile.getName());
	            }
	            else {
	                meta.setDisplayName(ChatColor.YELLOW + profile.getName() + " Head");
	            }
	        }
	        head.setItemMeta((ItemMeta)meta);
	        return head;
	    }
	    
	    private ItemStack getPlayerHead(final OfflinePlayer player) {
	        final GameProfile profile = new GameProfile(player.getUniqueId(), player.getName());
	        return getPlayerHead(profile);
	    }
	    
	    
	    private void setGameProfile(final SkullMeta meta, final GameProfile profile) {
	        try {
	            if (fieldProfileItem == null) {
	                fieldProfileItem = meta.getClass().getDeclaredField("profile");
	            }
	            fieldProfileItem.setAccessible(true);
	            fieldProfileItem.set(meta, profile);
	        }
	        catch (NoSuchFieldException e) {
	            e.printStackTrace();
	        }
	        catch (SecurityException e2) {
	            e2.printStackTrace();
	        }
	        catch (IllegalArgumentException e3) {
	            e3.printStackTrace();
	        }
	        catch (IllegalAccessException e4) {
	            e4.printStackTrace();
	        }
	    }
	  
	    private ItemStack makeSkull(final String textureCode) {
	        return makeSkull(textureCode, ChatColor.YELLOW + "UNKNOWN Head");
	    }
	    
	    private ItemStack makeSkull(final String textureCode, final String headName) {
	        final ItemStack item = new ItemStack(Material.PLAYER_HEAD);
	        if (textureCode == null) {
	            return item;
	        }
	        final SkullMeta meta = (SkullMeta)item.getItemMeta();
	        final GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes(textureCode.getBytes()), textureCode);
	        
	                
	        profile.getProperties().put("textures", new Property("textures", textureCode));
	       
	        
	        setGameProfile(meta, profile);
	        meta.setDisplayName(headName);
	        item.setItemMeta((ItemMeta)meta);
	        
	        return item;
	    }
	    
	    @SuppressWarnings("deprecation")
	    private ItemStack makeSkull(final EntityType entity) {
	        final ItemStack head = new ItemStack(Material.PLAYER_HEAD);
	        final SkullMeta meta = (SkullMeta)head.getItemMeta();
	        final String MHFName = EvUtils.getMHFHeadName(entity.name());
	        if (MHF_Lookup.containsKey(MHFName.toUpperCase())) {
	            meta.setOwningPlayer(Bukkit.getOfflinePlayer(MHFName));
	            meta.setDisplayName(ChatColor.YELLOW + MHFName);
	        }
	        else {
	            final GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes(entity.name().getBytes()), entity.name());
	            setGameProfile(meta, profile);
	            meta.setDisplayName(ChatColor.YELLOW + EvUtils.getNormalizedName(entity.name()) + " Head");
	        }
	        head.setItemMeta((ItemMeta)meta);
	        return head;
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    /*
	      	  private ItemStack getHead(GameProfile profile)
	  {
	    if ((profile == null) || (profile.getName() == null)) {
	      return null;
	    }
	    if (DoesTextureExists(profile.getName())) {
	      return makeTextureSkull(profile.getName());
	    }
	    BukkitAPISystem bukkitUtility = BukkitAPISystem.Instance();
	 
	    
	    OfflinePlayer p = bukkitUtility.Player.GetOfflinePlayer(profile.getId());
	    if ((p != null) && (p.getName() != null) && (
	      (this.updateOldPlayerHeads) || (p.getName().equals(profile.getName())))) {
	      return getPlayerHead(p);
	    }
	    return getPlayerHead(profile);
	  }
	  
	    private void setGameProfile(final Skull bState, final GameProfile profile) {
	        try {
	            if (fieldProfileBlock == null) {
	                fieldProfileBlock = bState.getClass().getDeclaredField("profile");
	            }
	            fieldProfileBlock.setAccessible(true);
	            fieldProfileBlock.set(bState, profile);
	        }
	        catch (NoSuchFieldException e) {
	            e.printStackTrace();
	        }
	        catch (SecurityException e2) {
	            e2.printStackTrace();
	        }
	        catch (IllegalArgumentException e3) {
	            e3.printStackTrace();
	        }
	        catch (IllegalAccessException e4) {
	            e4.printStackTrace();
	        }
	    }
	    
	    
	    private GameProfile getGameProfile(final SkullMeta meta) {
	        try {
	            if (fieldProfileItem == null) {
	                fieldProfileItem = meta.getClass().getDeclaredField("profile");
	            }
	            fieldProfileItem.setAccessible(true);
	            return (GameProfile)fieldProfileItem.get(meta);
	        }
	        catch (NoSuchFieldException e) {
	            e.printStackTrace();
	        }
	        catch (SecurityException e2) {
	            e2.printStackTrace();
	        }
	        catch (IllegalArgumentException e3) {
	            e3.printStackTrace();
	        }
	        catch (IllegalAccessException e4) {
	            e4.printStackTrace();
	        }
	        return null;
	    }
	    
	    private GameProfile getGameProfile(final Skull bState) {
	        try {
	            if (fieldProfileBlock == null) {
	                fieldProfileBlock = bState.getClass().getDeclaredField("profile");
	            }
	            fieldProfileBlock.setAccessible(true);
	            return (GameProfile)fieldProfileBlock.get(bState);
	        }
	        catch (NoSuchFieldException e) {
	            e.printStackTrace();
	        }
	        catch (SecurityException e2) {
	            e2.printStackTrace();
	        }
	        catch (IllegalArgumentException e3) {
	            e3.printStackTrace();
	        }
	        catch (IllegalAccessException e4) {
	            e4.printStackTrace();
	        }
	        return null;
	    }
	    
	    	  @SuppressWarnings("rawtypes")
	private void loadTextures(String headsList)
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
	  
	  	  private TreeMap<String, String> getTextures()
	  {
	    return this.textures;
	  }
	    */
}