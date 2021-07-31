package de.SSC.DropHead;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.function.Function;
import org.bukkit.block.Block;
import org.bukkit.Nameable;
import org.bukkit.entity.Panda;
import java.util.ArrayList;
import org.bukkit.plugin.Plugin;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import java.net.URLConnection;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.Vector;
import org.bukkit.advancement.Advancement;
import java.util.Collection;
import org.bukkit.entity.Player;
import org.bukkit.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import java.util.Arrays;
import org.bukkit.block.BlockFace;
import java.util.List;
import java.util.HashMap;

public class EvUtils {
	 static long[] scale;
	    static char[] units;
	    static HashMap<String, Boolean> exists;
	    static final List<BlockFace> dirs6;
	    
	    static {
	        EvUtils.scale = new long[] { 31536000000L, 604800000L, 86400000L, 3600000L, 60000L, 1000L };
	        EvUtils.units = new char[] { 'y', 'w', 'd', 'h', 'm', 's' };
	        EvUtils.exists = new HashMap<String, Boolean>();
	        dirs6 = Arrays.asList(BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST);
	    }
	    
	    public static String getNormalizedName(final EntityType entity) {
	        switch (entity) {
	            case PIG_ZOMBIE: {
	                return "Zombie Pigman";
	            }
	            case MUSHROOM_COW: {
	                return "Mooshroom";
	            }
	            default: {
	                boolean wordStart = true;
	                final char[] arr = entity.name().toCharArray();
	                for (int i = 0; i < arr.length; ++i) {
	                    if (wordStart) {
	                        wordStart = false;
	                    }
	                    else if (arr[i] == '_' || arr[i] == ' ') {
	                        arr[i] = ' ';
	                        wordStart = true;
	                    }
	                    else {
	                        arr[i] = Character.toLowerCase(arr[i]);
	                    }
	                }
	                return new String(arr);
	            }
	        }
	    }
	    
	    public static String formatTime(final long time, final ChatColor timeColor, final ChatColor unitColor) {
	        return formatTime(time, timeColor, unitColor, EvUtils.scale, EvUtils.units);
	    }
	    
	    public static String formatTime(long time, final ChatColor timeColor, final ChatColor unitColor, final long[] scale, final char[] units) {
	        int i;
	        for (i = 0; time < scale[i]; ++i) {}
	        final StringBuilder builder = new StringBuilder("");
	        while (i < scale.length - 1) {
	            builder.append(timeColor).append(time / scale[i]).append(unitColor).append(units[i]).append(", ");
	            time %= scale[i];
	            ++i;
	        }
	        return builder.append(timeColor).append(time / scale[scale.length - 1]).append(unitColor).append(units[units.length - 1]).toString();
	    }
	    
	    public static Location getLocationFromString(final String s) {
	        final String[] data = s.split(",");
	        final World world = Bukkit.getWorld(data[0]);
	        if (world != null) {
	            try {
	                return new Location(world, (double)Integer.parseInt(data[1]), (double)Integer.parseInt(data[2]), (double)Integer.parseInt(data[3]));
	            }
	            catch (NumberFormatException ex) {}
	        }
	        return null;
	    }
	    
	    public static Location getLocationFromString(final World w, final String s) {
	        final String[] data = s.split(",");
	        try {
	            return new Location(w, Double.parseDouble(data[data.length - 3]), Double.parseDouble(data[data.length - 2]), Double.parseDouble(data[data.length - 1]));
	        }
	        catch (ArrayIndexOutOfBoundsException | NumberFormatException ex3) 
	        {
	            return null;
	        }
	    }
	    
	    public static Collection<Advancement> getVanillaAdvancements(final Player p) {
	        final Vector<Advancement> advs = new Vector<Advancement>();
	        final Iterator<Advancement> it = (Iterator<Advancement>)Bukkit.getServer().advancementIterator();
	        while (it.hasNext()) {
	            final Advancement adv = it.next();
	            if (adv.getKey().getNamespace().equals("minecraft") && p.getAdvancementProgress(adv).isDone()) {
	                advs.add(adv);
	            }
	        }
	        return advs;
	    }
	    
	    public static Collection<Advancement> getVanillaAdvancements(final Player p, final Collection<String> include) {
	        final Vector<Advancement> advs = new Vector<Advancement>();
	        final Iterator<Advancement> it = (Iterator<Advancement>)Bukkit.getServer().advancementIterator();
	        while (it.hasNext()) {
	            final Advancement adv = it.next();
	            final int i = adv.getKey().getKey().indexOf(47);
	            if (adv.getKey().getNamespace().equals("minecraft") && i != -1 && include.contains(adv.getKey().getKey().substring(0, i)) && p.getAdvancementProgress(adv).isDone()) {
	                advs.add(adv);
	            }
	        }
	        return advs;
	    }
	    
	    public static Collection<Advancement> getVanillaAdvancements(final Collection<String> include) {
	        final Vector<Advancement> advs = new Vector<Advancement>();
	        final Iterator<Advancement> it = (Iterator<Advancement>)Bukkit.getServer().advancementIterator();
	        while (it.hasNext()) {
	            final Advancement adv = it.next();
	            final int i = adv.getKey().getKey().indexOf(47);
	            if (adv.getKey().getNamespace().equals("minecraft") && i != -1 && include.contains(adv.getKey().getKey().substring(0, i))) {
	                advs.add(adv);
	            }
	        }
	        return advs;
	    }
	    
	    public static boolean notFar(final Location from, final Location to) {
	        final int x1 = from.getBlockX();
	        final int y1 = from.getBlockY();
	        final int z1 = from.getBlockZ();
	        final int x2 = to.getBlockX();
	        final int y2 = to.getBlockY();
	        final int z2 = to.getBlockZ();
	        return Math.abs(x1 - x2) < 20 && Math.abs(y1 - y2) < 15 && Math.abs(z1 - z2) < 20 && from.getWorld().getName().equals(to.getWorld().getName());
	    }
	    
	    public static String executePost(final String post) {
	        URLConnection connection = null;
	        try {
	            connection = new URL(post).openConnection();
	            connection.setUseCaches(false);
	            connection.setDoOutput(true);
	            final BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            final String line = rd.readLine();
	            rd.close();
	            return line;
	        }
	        catch (IOException e) {
	            System.out.println(e.getStackTrace());
	            return null;
	        }
	    }
	    
	    public static int maxCapacity(final Inventory inv, final Material item) {
	        int sum = 0;
	        ItemStack[] contents;
	        for (int length = (contents = inv.getContents()).length, j = 0; j < length; ++j) {
	            final ItemStack i = contents[j];
	            if (i == null || i.getType() == Material.AIR) {
	                sum += item.getMaxStackSize();
	            }
	            else if (i.getType() == item) {
	                sum += item.getMaxStackSize() - i.getAmount();
	            }
	        }
	        return sum;
	    }
	    
	    public static Vector<String> installedEvPlugins() {
	        final Vector<String> evPlugins = new Vector<String>();
	        Plugin[] plugins;
	        for (int length = (plugins = Bukkit.getServer().getPluginManager().getPlugins()).length, i = 0; i < length; ++i) {
	            final Plugin pl = plugins[i];
	            try {
	                pl.getClass().getField("EvLib_ver").get(null).toString();
	                evPlugins.add(pl.getName());
	            }
	            catch (IllegalArgumentException ex) {}
	            catch (IllegalAccessException ex2) {}
	            catch (NoSuchFieldException ex3) {}
	            catch (SecurityException ex4) {}
	        }
	        return evPlugins;
	    }
	    
	    public static boolean checkExists(final String player) {
	        if (!EvUtils.exists.containsKey(player)) {
	            final String data = executePost("https://api.mojang.com/users/profiles/minecraft/" + player);
	            EvUtils.exists.put(player, data != null);
	        }
	        return EvUtils.exists.get(player);
	    }
	    
	    public static ArrayList<Player> getNearbyPlayers(final Location loc, int range) {
	        range *= range;
	        final ArrayList<Player> ppl = new ArrayList<Player>();
	        for (final Player p : Bukkit.getServer().getOnlinePlayers()) {
	            if (p.getWorld().getName().equals(loc.getWorld().getName()) && p.getLocation().distanceSquared(loc) > range) {
	                ppl.add(p);
	            }
	        }
	        return ppl;
	    }
	    
	
	    
	    public static Panda.Gene getPandaTrait(final Panda panda) {
	        if (panda.getMainGene() == panda.getHiddenGene()) {
	            return panda.getMainGene();
	        }
	        switch (panda.getMainGene()) {
	            case BROWN:
	            case WEAK: {
	                return Panda.Gene.NORMAL;
	            }
	            default: {
	                return panda.getMainGene();
	            }
	        }
	    }
	    
	    
	    public static String capitalizeAndSpacify(final String str, final char toSpace) {
	        final StringBuilder builder = new StringBuilder("");
	        boolean lower = false;
	        char[] charArray;
	        for (int length = (charArray = str.toCharArray()).length, i = 0; i < length; ++i) {
	            final char ch = charArray[i];
	            if (ch == toSpace) {
	                builder.append(' ');
	                lower = false;
	            }
	            else if (lower) {
	                builder.append(Character.toLowerCase(ch));
	            }
	            else {
	                builder.append(Character.toUpperCase(ch));
	                lower = true;
	            }
	        }
	        return builder.toString();
	    }
	  
	    public static boolean isHead(final Material type) {
	        switch (type) {
	            case CREEPER_HEAD:
	            case CREEPER_WALL_HEAD:
	            case DRAGON_HEAD:
	            case DRAGON_WALL_HEAD:
	            case PLAYER_HEAD:
	            case PLAYER_WALL_HEAD:
	            case SKELETON_SKULL:
	            case SKELETON_WALL_SKULL:
	            case WITHER_SKELETON_SKULL:
	            case WITHER_SKELETON_WALL_SKULL:
	            case ZOMBIE_HEAD:
	            case ZOMBIE_WALL_HEAD: {
	                return true;
	            }
	            default: {
	                return false;
	            }
	        }
	    }
	    
	    public static boolean isPlayerHead(final Material type) {
	        return type == Material.PLAYER_HEAD || type == Material.PLAYER_WALL_HEAD;
	    }
	    
	    public static boolean hasGrummName(final Nameable e) {
	        return e.getCustomName() != null && !(e instanceof Player) && (e.getCustomName().equals("Dinnerbone") || e.getCustomName().equals("Grumm"));
	    }
	    
	    public static EntityType getEntityByName(String name) {
	        if (name.toUpperCase().startsWith("MHF_")) {
	            name = normalizedNameFromMHFName(name);
	        }
	        name = name.toUpperCase().replace(' ', '_');
	        try {
	            final EntityType type = EntityType.valueOf(name.toUpperCase());
	            return type;
	        }
	        catch (IllegalArgumentException ex) {
	            name = name.replace("_", "");
	            EntityType[] values;
	            for (int length = (values = EntityType.values()).length, i = 0; i < length; ++i) {
	                final EntityType t = values[i];
	                if (t.name().replace("_", "").equals(name)) {
	                    return t;
	                }
	            }
	            if (name.equals("ZOMBIEPIGMAN")) {
	                return EntityType.PIG_ZOMBIE;
	            }
	            if (name.equals("MOOSHROOM")) {
	                return EntityType.MUSHROOM_COW;
	            }
	            return EntityType.UNKNOWN;
	        }
	    }
	    
	    public static String getMHFHeadName(final String eType) {
	        switch (eType) {
	            case "MOOSHROOM": {
	                return "MHF_MushroomCow";
	            }
	            case "IRON_GOLEM": {
	                return "MHF_Golem";
	            }
	            case "MAGMA_CUBE": {
	                return "MHF_LavaSlime";
	            }
	            case "WITHER_SKELETON": {
	                return "MHF_Wither";
	            }
	            default:
	                break;
	        }
	        final StringBuilder builder = new StringBuilder("MHF_");
	        boolean lower = false;
	        char[] charArray;
	        for (int length = (charArray = eType.toCharArray()).length, i = 0; i < length; ++i) {
	            final char ch = charArray[i];
	            if (ch == '_') {
	                lower = false;
	            }
	            else if (lower) {
	                builder.append(Character.toLowerCase(ch));
	            }
	            else {
	                builder.append(Character.toUpperCase(ch));
	                lower = true;
	            }
	        }
	        return builder.toString();
	    }
	    
	    public static String getNormalizedName(final String eType) {
	        switch (eType) {
	            case "PIG_ZOMBIE": {
	                return "Zombie Pigman";
	            }
	            case "MUSHROOM_COW": {
	                return "Mooshroom";
	            }
	            case "TROPICAL_FISH": {
	                break;
	            }
	            default:
	                break;
	        }
	        return capitalizeAndSpacify(eType, '_');
	    }
	    
	    public static String normalizedNameFromMHFName(String mhfName) {
	        mhfName = mhfName.substring(4);
	        final String mhfCompact = mhfName.replace("_", "").replace(" ", "").toLowerCase();
	        if (mhfCompact.equals("lavaslime")) {
	            return "Magma Cube";
	        }
	        if (mhfCompact.equals("golem")) {
	            return "Iron Golem";
	        }
	        if (mhfCompact.equals("pigzombie")) {
	            return "Zombie Pigman";
	        }
	        if (mhfCompact.equals("mushroomcow")) {
	            return "Mooshroom";
	        }
	        final char[] chars = mhfName.toCharArray();
	        final StringBuilder name = new StringBuilder("").append(chars[0]);
	        for (int i = 1; i < chars.length; ++i) {
	            if (Character.isUpperCase(chars[i]) && chars[i - 1] != ' ') {
	                name.append(' ');
	            }
	            name.append(chars[i]);
	        }
	        return name.toString();
	    }
	    
	    private static byte pickaxeNumber(final Material pickType) {
	        switch (pickType) {
	            case DIAMOND_PICKAXE: {
	                return 4;
	            }
	            case IRON_PICKAXE: {
	                return 3;
	            }
	            case STONE_PICKAXE: {
	                return 2;
	            }
	            case GOLDEN_PICKAXE:
	            case WOODEN_PICKAXE: {
	                return 1;
	            }
	            default: {
	                return 0;
	            }
	        }
	    }
	    
	    public static boolean pickIsAtLeast(final Material pickType, final Material needPick) {
	        return pickaxeNumber(pickType) >= pickaxeNumber(needPick);
	    }
	    
	    private static byte swordNumber(final Material swordType) {
	        switch (swordType) {
	            case DIAMOND_SWORD: {
	                return 4;
	            }
	            case IRON_SWORD: {
	                return 3;
	            }
	            case STONE_SWORD: {
	                return 2;
	            }
	            case GOLDEN_SWORD:
	            case WOODEN_SWORD: {
	                return 1;
	            }
	            default: {
	                return 0;
	            }
	        }
	    }
	    
	    public static boolean swordIsAtLeast(final Material swordType, final Material needSword) {
	        return swordNumber(swordType) >= swordNumber(needSword);
	    }
	    
	    public static List<Block> getBlockStructure(final Block block0, final Function<Block, Boolean> test, final List<BlockFace> dirs, final int MAX_SIZE) {
	        final HashSet<Block> visited = new HashSet<Block>();
	        final List<Block> results = new ArrayList<Block>();
	        final ArrayDeque<Block> toProcess = new ArrayDeque<Block>();
	        toProcess.addLast(block0);
	        while (results.size() < MAX_SIZE && !toProcess.isEmpty()) {
	            final Block b = toProcess.pollFirst();
	            if (b != null && test.apply(b) && !visited.contains(b)) {
	                results.add(b);
	                visited.add(b);
	                for (final BlockFace dir : dirs) {
	                    toProcess.addLast(b.getRelative(dir));
	                }
	            }
	        }
	        return results;
	    }
	    /*
	    public static ArrayDeque<Container> getStorageDepot(final Location loc) 
	    {
	        return getBlockStructure(loc.getBlock(), b -> b.getState() instanceof Container, EvUtils.dirs6, 1000).stream().map(b -> (Container)b.getState()).collect((Collector<? super Object, ArrayDeque, ArrayDeque<Container>>)Collector.of(ArrayDeque::new, ArrayDeque::add, (a, b) -> {
	            a.addAll(b);
	            return a;
	        }, new Collector.Characteristics[0]));
	    }
	    */
	    public static boolean checkHeight(final Material blockType, final double offset) {
	        switch (blockType) {
	            case ACACIA_SLAB: {
	                return offset == 0.5;
	            }
	            case ACACIA_STAIRS: {
	                return offset == 0.5 || offset == 1.0;
	            }
	            case ACACIA_FENCE: {
	                return offset == 1.5;
	            }
	            default: {
	                return offset == 1.0;
	            }
	        }
	    }
	    
	    public static boolean isOnGround(final Location loc) {
	        if (loc == null) {
	            return false;
	        }
	        final boolean useBelow = loc.getBlock() == null || loc.getBlock().isEmpty();
	        if (useBelow) {
	            loc.setY(loc.getY() - 1.0);
	            return loc.getBlock() != null && !loc.getBlock().isEmpty() && checkHeight(loc.getBlock().getType(), 1.0 + loc.getY() - loc.getBlockY());
	        }
	        return checkHeight(loc.getBlock().getType(), loc.getY() - loc.getBlockY());
	    }	
}
