package de.SSC.CoreManager.Addons.DropHead;

import java.util.HashMap;
import java.util.regex.Pattern;

import org.bukkit.DyeColor;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Shulker;
import org.bukkit.entity.TraderLlama;
import org.bukkit.entity.TropicalFish;
import org.bukkit.entity.Vex;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.ZombieVillager;

public class DropHeadTextureLookUp 
{
	 static class CCP
	  {
	    DyeColor bodyColor;
	    DyeColor patternColor;
	    TropicalFish.Pattern pattern;
	    
	    CCP(DyeColor color, DyeColor pColor, TropicalFish.Pattern p)
	    {
	      this.bodyColor = color;this.patternColor = pColor;this.pattern = p;
	    }
	    
	    public boolean equals(Object o)
	    {
	      if (o == this) {
	        return true;
	      }
	      if ((o == null) || (o.getClass() != getClass())) {
	        return false;
	      }
	      CCP ccp = (CCP)o;
	      return (ccp.bodyColor == this.bodyColor) && (ccp.patternColor == this.patternColor) && (ccp.pattern == this.pattern);
	    }
	    
	    public int hashCode()
	    {
	      return this.bodyColor.hashCode() + 16 * (this.patternColor.hashCode() + 16 * this.pattern.hashCode());
	    }
	  }
	  
	  public static final HashMap<CCP, String> tropicalFishNames = new HashMap<CCP, String>();
	  public static final HashMap<DyeColor, String> fishColorNames;
	  
	  static
	  {
	    tropicalFishNames.put(new CCP(DyeColor.ORANGE, DyeColor.GRAY, TropicalFish.Pattern.STRIPEY), "Anemone");
	    tropicalFishNames.put(new CCP(DyeColor.GRAY, DyeColor.GRAY, TropicalFish.Pattern.FLOPPER), "Black Tang");
	    tropicalFishNames.put(new CCP(DyeColor.GRAY, DyeColor.BLUE, TropicalFish.Pattern.FLOPPER), "Blue Dory");
	    tropicalFishNames.put(new CCP(DyeColor.WHITE, DyeColor.GRAY, TropicalFish.Pattern.BRINELY), "Butterflyfish");
	    tropicalFishNames.put(new CCP(DyeColor.BLUE, DyeColor.GRAY, TropicalFish.Pattern.SUNSTREAK), "Cichlid");
	    tropicalFishNames.put(new CCP(DyeColor.ORANGE, DyeColor.WHITE, TropicalFish.Pattern.KOB), "Clownfish");
	    tropicalFishNames.put(new CCP(DyeColor.PINK, DyeColor.LIGHT_BLUE, TropicalFish.Pattern.SPOTTY), "Cotton Candy Betta");
	    tropicalFishNames.put(new CCP(DyeColor.PURPLE, DyeColor.YELLOW, TropicalFish.Pattern.BLOCKFISH), "Dottyback");
	    tropicalFishNames.put(new CCP(DyeColor.WHITE, DyeColor.RED, TropicalFish.Pattern.CLAYFISH), "Red Emperor");
	    tropicalFishNames.put(new CCP(DyeColor.WHITE, DyeColor.YELLOW, TropicalFish.Pattern.SPOTTY), "Goatfish");
	    tropicalFishNames.put(new CCP(DyeColor.WHITE, DyeColor.GRAY, TropicalFish.Pattern.GLITTER), "Moorish Idol");
	    tropicalFishNames.put(new CCP(DyeColor.WHITE, DyeColor.ORANGE, TropicalFish.Pattern.CLAYFISH), "Ornate Butterflyfish");
	    tropicalFishNames.put(new CCP(DyeColor.CYAN, DyeColor.PINK, TropicalFish.Pattern.DASHER), "Parrotfish");
	    tropicalFishNames.put(new CCP(DyeColor.LIME, DyeColor.LIGHT_BLUE, TropicalFish.Pattern.BRINELY), "Queen Angelfish");
	    tropicalFishNames.put(new CCP(DyeColor.RED, DyeColor.WHITE, TropicalFish.Pattern.BETTY), "Red Cichlid");
	    tropicalFishNames.put(new CCP(DyeColor.GRAY, DyeColor.RED, TropicalFish.Pattern.SNOOPER), "Red Lipped Blenny");
	    tropicalFishNames.put(new CCP(DyeColor.RED, DyeColor.WHITE, TropicalFish.Pattern.BLOCKFISH), "Red Snapper");
	    tropicalFishNames.put(new CCP(DyeColor.WHITE, DyeColor.YELLOW, TropicalFish.Pattern.FLOPPER), "Threadfin");
	    tropicalFishNames.put(new CCP(DyeColor.RED, DyeColor.WHITE, TropicalFish.Pattern.KOB), "Tomato Clownfish");
	    tropicalFishNames.put(new CCP(DyeColor.GRAY, DyeColor.WHITE, TropicalFish.Pattern.SUNSTREAK), "Triggerfish");
	    tropicalFishNames.put(new CCP(DyeColor.CYAN, DyeColor.YELLOW, TropicalFish.Pattern.DASHER), "Yellowtail Parrotfish");
	    tropicalFishNames.put(new CCP(DyeColor.YELLOW, DyeColor.YELLOW, TropicalFish.Pattern.STRIPEY), "Yellow Tang");
	    
	    fishColorNames = new HashMap<DyeColor, String>();
	    fishColorNames.put(DyeColor.BLACK, "Black");
	    fishColorNames.put(DyeColor.BLUE, "Blue");
	    fishColorNames.put(DyeColor.BROWN, "Brown");
	    fishColorNames.put(DyeColor.CYAN, "Teal");
	    fishColorNames.put(DyeColor.GRAY, "Gray");
	    fishColorNames.put(DyeColor.GREEN, "Green");
	    fishColorNames.put(DyeColor.LIGHT_BLUE, "Sky");
	    fishColorNames.put(DyeColor.LIGHT_GRAY, "Silver");
	    fishColorNames.put(DyeColor.LIME, "Lime");
	    fishColorNames.put(DyeColor.MAGENTA, "Magenta");
	    fishColorNames.put(DyeColor.ORANGE, "Orange");
	    fishColorNames.put(DyeColor.PINK, "Rose");
	    fishColorNames.put(DyeColor.PURPLE, "Plum");
	    fishColorNames.put(DyeColor.RED, "Red");
	    fishColorNames.put(DyeColor.WHITE, "White");
	    fishColorNames.put(DyeColor.YELLOW, "Yellow");
	  }
	  
	  static String getTropicalFishName(CCP ccp)
	  {
	    String name = (String)tropicalFishNames.get(ccp);
	    if (name == null)
	    {
	      StringBuilder builder = new StringBuilder((String)fishColorNames.get(ccp.bodyColor));
	      if (ccp.bodyColor != ccp.patternColor) {
	        builder.append('-').append((String)fishColorNames.get(ccp.patternColor));
	      }
	      builder.append(' ').append(EvUtils.capitalizeAndSpacify(ccp.pattern.name(), '_'));
	      name = builder.toString();
	      tropicalFishNames.put(ccp, name);
	    }
	    return name;
	  }
	  
	  static String getTropicalFishName(TropicalFish fish)
	  {
	    return getTropicalFishName(new CCP(fish.getBodyColor(), fish.getPatternColor(), fish.getPattern()));
	  }
	  
	  static String getTextureKey(LivingEntity entity)
	  {
	    switch (entity.getType())
	    {
	    case MAGMA_CUBE: 
	      if (((Creeper)entity).isPowered()) {
	        return "CREEPER|CHARGED";
	      }
	      return "CREEPER";
	    case SHEEP: 
	      if (((Wolf)entity).isAngry()) {
	        return "WOLF|ANGRY";
	      }
	      return "WOLF";
	    case SKELETON_HORSE: 
	      return "HORSE|" + ((Horse)entity).getColor().name();
	    case SNOWBALL: 
	      return "LLAMA|" + ((Llama)entity).getColor().name();
	    case SPECTRAL_ARROW: 
	      return "PARROT|" + ((Parrot)entity).getVariant().name();
	    case SLIME: 
	      return "RABBIT|" + ((Rabbit)entity).getRabbitType().name();
	    case PUFFERFISH: 
	      if ((entity.getCustomName() != null) && (entity.getCustomName().equals("jeb_"))) {
	        return "SHEEP|JEB";
	      }
	      return "SHEEP|" + ((Sheep)entity).getColor().name();
	    case POLAR_BEAR: 
	      return "SHULKER|" + ((Shulker)entity).getColor().name();
	    case TURTLE: 
	      TropicalFish f = (TropicalFish)entity;
	      return "TROPICAL_FISH|" + f.getBodyColor() + "|" + f.getPatternColor() + "|" + f.getPattern();
	    case GIANT: 
	      if (((Vex)entity).isCharging()) {
	        return "VEX|CHARGING";
	      }
	      return "VEX";
	    case EVOKER_FANGS: 
	      return "ZOMBIE_VILLAGER|" + ((ZombieVillager)entity).getVillagerProfession().name();
	    case SPIDER: 
	      return "VILLAGER|" + ((Villager)entity).getProfession().name();
	    case SILVERFISH: 
	      return "OCELOT|" + ((Ocelot)entity).getCatType().name();
	    case VILLAGER: 
	      switch (((Cat)entity).getCatType())
	      {
	      case WHITE: 
	        return "CAT|BLACK";
	      case BLACK: 
	        return "TUXEDO";
	        
		case ALL_BLACK:			
		case BRITISH_SHORTHAIR:			
		case CALICO:			
		case JELLIE:			
		case PERSIAN:			
		case RAGDOLL:			
		case RED:			
		case SIAMESE:			
		case TABBY:
			
		default:
			break;
	      }
	      return "CAT|" + ((Cat)entity).getCatType().name();
	    case SHULKER: 
	      return "MUSHROOM_COW|" + ((MushroomCow)entity).getVariant().name();
	    case WITHER_SKULL: 
	      if (((Fox)entity).isSleeping()) {
	        return "FOX|" + ((Fox)entity).getFoxType().name() + "|SLEEPING";
	      }
	      return "FOX|" + ((Fox)entity).getFoxType().name();
	    case VINDICATOR: 
	      return "PANDA|" + EvUtils.getPandaTrait((Panda)entity);
	    case WITHER: 
	      return "TRADER_LLAMA|" + ((TraderLlama)entity).getColor().name();
		case AREA_EFFECT_CLOUD:
			break;
		case ARMOR_STAND:
			break;
		case ARROW:
			break;
		case BAT:
			break;
		case BLAZE:
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
		case MINECART:
			break;
		case MINECART_CHEST:
			break;
		case MINECART_COMMAND:
			break;
		case MINECART_FURNACE:
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
		case PARROT:
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
		case PRIMED_TNT:
			break;
		case RABBIT:
			break;
		case RAVAGER:
			break;
		case SALMON:
			break;
		case SHULKER_BULLET:
			break;
		case SKELETON:
			break;
		case SMALL_FIREBALL:
			break;
		case SNOWMAN:
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
		case UNKNOWN:
			break;
		case VEX:
			break;
		case WANDERING_TRADER:
			break;
		case WITCH:
			break;
		case WITHER_SKELETON:
			break;
		case WOLF:
			break;
		case ZOMBIE:
			break;
		case ZOMBIE_HORSE:
			break;
		case ZOMBIE_VILLAGER:
			break;
		default:
			break;
	    }
	    return entity.getType().name();
	  }
	  
	  static String getNameFromKey(EntityType entity, String textureKey)
	  {
	    String[] dataFlags = textureKey.split(Pattern.quote("|"));
	    if (entity == null ? textureKey.startsWith("TROPICAL_FISH|") : entity == EntityType.TROPICAL_FISH)
	    {
	      if (dataFlags.length == 2) {
	        return EvUtils.capitalizeAndSpacify(dataFlags[1], '_');
	      }
	      try
	      {
	        DyeColor bodyColor = DyeColor.valueOf(dataFlags[1]);
	        DyeColor patternColor = dataFlags.length == 3 ? bodyColor : DyeColor.valueOf(dataFlags[2]);
	        TropicalFish.Pattern pattern = TropicalFish.Pattern.valueOf(dataFlags[3]);
	        return getTropicalFishName(new CCP(bodyColor, patternColor, pattern));
	      }
	      catch (IllegalArgumentException localIllegalArgumentException) {}
	    }
	    StringBuilder builder = new StringBuilder("");
	    for (int i = dataFlags.length - 1; i > 0; i--)
	    {
	      String dataStr = EvUtils.capitalizeAndSpacify(dataFlags[i], '_');
	      builder.append(dataStr).append(' ');
	    }
	    builder.append(EvUtils.getNormalizedName(dataFlags[0]));
	    return builder.toString();
	  }
}
