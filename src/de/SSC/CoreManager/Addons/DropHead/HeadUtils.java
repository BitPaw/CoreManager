package de.SSC.CoreManager.Addons.DropHead;

import org.bukkit.OfflinePlayer;
import org.bukkit.Bukkit;
import org.bukkit.inventory.meta.ItemMeta;
import com.mojang.authlib.properties.Property;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Skull;
import com.mojang.authlib.GameProfile;
import org.bukkit.inventory.meta.SkullMeta;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.lang.reflect.Field;
import org.bukkit.entity.EntityType;
import java.util.HashMap;
import java.util.Map;

public class HeadUtils 
{
    public static final String[] MHF_Heads;
    public static final Map<String, String> MHF_Lookup;
    public static final HashMap<EntityType, String> customHeads;
    private static Field fieldProfileItem;
    private static Field fieldProfileBlock;
    
    static {
        MHF_Heads = new String[] { "MHF_Alex", "MHF_Blaze", "MHF_CaveSpider", "MHF_Chicken", "MHF_Cow", "MHF_Creeper", "MHF_Enderman", "MHF_Ghast", "MHF_Golem", "MHF_Herobrine", "MHF_LavaSlime", "MHF_MushroomCow", "MHF_Ocelot", "MHF_Pig", "MHF_PigZombie", "MHF_Sheep", "MHF_Skeleton", "MHF_Slime", "MHF_Spider", "MHF_Squid", "MHF_Steve", "MHF_Villager", "MHF_Witch", "MHF_Wither", "MHF_WSkeleton", "MHF_Zombie", "MHF_Cactus", "MHF_Cake", "MHF_Chest", "MHF_CoconutB", "MHF_CoconutG", "MHF_Melon", "MHF_OakLog", "MHF_Present1", "MHF_Present2", "MHF_Pumpkin", "MHF_TNT", "MHF_TNT2", "MHF_ArrowUp", "MHF_ArrowDown", "MHF_ArrowLeft", "MHF_ArrowRight", "MHF_Exclamation", "MHF_Question" };
        MHF_Lookup = Stream.of(HeadUtils.MHF_Heads).collect(Collectors.toMap(h -> h.toUpperCase(), h -> h));
        (customHeads = new HashMap<EntityType, String>()).put(EntityType.BAT, "ManBatPlaysMC");
        HeadUtils.customHeads.put(EntityType.ELDER_GUARDIAN, "MHF_EGuardian");
        HeadUtils.customHeads.put(EntityType.ENDERMITE, "MHF_Endermites");
        HeadUtils.customHeads.put(EntityType.EVOKER, "MHF_Evoker");
        HeadUtils.customHeads.put(EntityType.GUARDIAN, "MHF_Guardian");
        HeadUtils.customHeads.put(EntityType.HORSE, "gavertoso");
        HeadUtils.customHeads.put(EntityType.PARROT, "MHF_Parrot");
        HeadUtils.customHeads.put(EntityType.POLAR_BEAR, "NiXWorld");
        HeadUtils.customHeads.put(EntityType.RABBIT, "MHF_Rabbit");
        HeadUtils.customHeads.put(EntityType.SHULKER, "MHF_Shulker");
        HeadUtils.customHeads.put(EntityType.SILVERFISH, "MHF_Silverfish");
        HeadUtils.customHeads.put(EntityType.VEX, "MHF_Vex");
        HeadUtils.customHeads.put(EntityType.VINDICATOR, "Vindicator");
        HeadUtils.customHeads.put(EntityType.SNOWMAN, "MHF_SnowGolem");
        HeadUtils.customHeads.put(EntityType.WITCH, "MHF_Witch");
        HeadUtils.customHeads.put(EntityType.WOLF, "MHF_Wolf");
        HeadUtils.customHeads.put(EntityType.ZOMBIE_VILLAGER, "scraftbrothers11");
    }
    
    public static void setGameProfile(final SkullMeta meta, final GameProfile profile) {
        try {
            if (HeadUtils.fieldProfileItem == null) {
                HeadUtils.fieldProfileItem = meta.getClass().getDeclaredField("profile");
            }
            HeadUtils.fieldProfileItem.setAccessible(true);
            HeadUtils.fieldProfileItem.set(meta, profile);
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
    
    public static void setGameProfile(final Skull bState, final GameProfile profile) {
        try {
            if (HeadUtils.fieldProfileBlock == null) {
                HeadUtils.fieldProfileBlock = bState.getClass().getDeclaredField("profile");
            }
            HeadUtils.fieldProfileBlock.setAccessible(true);
            HeadUtils.fieldProfileBlock.set(bState, profile);
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
    
    public static GameProfile getGameProfile(final SkullMeta meta) {
        try {
            if (HeadUtils.fieldProfileItem == null) {
                HeadUtils.fieldProfileItem = meta.getClass().getDeclaredField("profile");
            }
            HeadUtils.fieldProfileItem.setAccessible(true);
            return (GameProfile)HeadUtils.fieldProfileItem.get(meta);
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
    
    public static GameProfile getGameProfile(final Skull bState) {
        try {
            if (HeadUtils.fieldProfileBlock == null) {
                HeadUtils.fieldProfileBlock = bState.getClass().getDeclaredField("profile");
            }
            HeadUtils.fieldProfileBlock.setAccessible(true);
            return (GameProfile)HeadUtils.fieldProfileBlock.get(bState);
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
    
    public static ItemStack makeSkull(final String textureCode) {
        return makeSkull(textureCode, ChatColor.YELLOW + "UNKNOWN Head");
    }
    
    public static ItemStack makeSkull(final String textureCode, final String headName) {
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
	static ItemStack makeSkull(final EntityType entity) {
        final ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        final SkullMeta meta = (SkullMeta)head.getItemMeta();
        final String MHFName = EvUtils.getMHFHeadName(entity.name());
        if (HeadUtils.MHF_Lookup.containsKey(MHFName.toUpperCase())) {
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
    
    public static ItemStack getPlayerHead(final GameProfile profile) {
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
    
    public static ItemStack getPlayerHead(final OfflinePlayer player) {
        final GameProfile profile = new GameProfile(player.getUniqueId(), player.getName());
        return getPlayerHead(profile);
    }
}
