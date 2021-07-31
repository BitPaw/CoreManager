/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.PacketType.Play.Server;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.reflect.IntEnum;
/*     */ import com.comphenix.protocol.reflect.StructureModifier;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WrapperPlayServerWorldEvent
/*     */   extends AbstractPacket
/*     */ {
/*  28 */   public static final PacketType TYPE = PacketType.Play.Server.WORLD_EVENT;
/*     */   
/*     */ 
/*     */   public static class SoundEffects
/*     */     extends IntEnum
/*     */   {
/*     */     public static final int RANDOM_CLICK_1 = 1000;
/*     */     
/*     */     public static final int RANDOM_CLICK_2 = 1001;
/*     */     
/*     */     public static final int RANDOM_BOW = 1002;
/*     */     
/*     */     public static final int RANDOM_DOOR = 1003;
/*     */     
/*     */     public static final int RANDOM_FIZZ = 1004;
/*     */     
/*     */     public static final int PLAY_MUSIC_DISK = 1005;
/*     */     
/*     */     public static final int MOB_GHAST_CHARGE = 1007;
/*     */     
/*     */     public static final int MOB_GHAST_FIREBALL_QUIET = 1009;
/*     */     
/*     */     public static final int MOB_GHAST_FIREBALL = 1008;
/*     */     
/*     */     public static final int MOB_ZOMBIE_WOOD = 1010;
/*     */     public static final int MOB_ZOMBIE_METAL = 1011;
/*     */     public static final int MOB_ZOMBIE_WOODBREAK = 1012;
/*     */     public static final int MOB_WITHER_SPAWN = 1013;
/*     */     public static final int MOB_WITHER_SHOOT = 1014;
/*     */     public static final int MOB_BAT_TAKEOFF = 1015;
/*     */     public static final int MOB_ZOMBIE_INFECT = 1016;
/*     */     public static final int MOB_ZOMBIE_UNFECT = 1017;
/*     */     public static final int MOB_ENDER_DRAGON_END = 1018;
/*     */     public static final int RANDOM_ANVIL_BREAK = 1020;
/*     */     public static final int RANDOM_ANVIL_USE = 1021;
/*     */     public static final int RANDOM_ANVIL_LAND = 1022;
/*  64 */     private static final SoundEffects INSTANCE = new SoundEffects();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public static SoundEffects getInstance()
/*     */     {
/*  71 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class ParticleEffects
/*     */     extends IntEnum
/*     */   {
/*     */     public static final int SPAWN_SMOKE_PARTICLES = 2000;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public static final int BLOCK_BREAK = 2001;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public static final int SPLASH_POTION = 2002;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public static final int EYE_OF_ENDER = 2003;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public static final int MOB_SPAWN_EFFECT = 2004;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public static final int HAPPY_VILLAGER = 2005;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public static final int FALL_PARTICLES = 2006;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 117 */     private static final ParticleEffects INSTANCE = new ParticleEffects();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public static ParticleEffects getInstance()
/*     */     {
/* 124 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SmokeDirections extends IntEnum
/*     */   {
/*     */     public static final int SOUTH_EAST = 0;
/*     */     public static final int SOUTH = 1;
/*     */     public static final int SOUTH_WEST = 2;
/*     */     public static final int EAST = 3;
/*     */     public static final int UP = 4;
/*     */     public static final int WEST = 5;
/*     */     public static final int NORTH_EAST = 6;
/*     */     public static final int NORTH = 7;
/*     */     public static final int NORTH_WEST = 8;
/* 139 */     private static final SmokeDirections INSTANCE = new SmokeDirections();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public static SmokeDirections getInstance()
/*     */     {
/* 146 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */   
/*     */   public WrapperPlayServerWorldEvent() {
/* 151 */     super(new PacketContainer(TYPE), TYPE);
/* 152 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerWorldEvent(PacketContainer packet) {
/* 156 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getEffectId()
/*     */   {
/* 165 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEffectId(int value)
/*     */   {
/* 174 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getX()
/*     */   {
/* 182 */     return ((Integer)this.handle.getIntegers().read(2)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setX(int value)
/*     */   {
/* 190 */     this.handle.getIntegers().write(2, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getY()
/*     */   {
/* 198 */     return ((Integer)this.handle.getIntegers().read(3)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setY(int value)
/*     */   {
/* 206 */     this.handle.getIntegers().write(3, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getZ()
/*     */   {
/* 214 */     return ((Integer)this.handle.getIntegers().read(4)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setZ(int value)
/*     */   {
/* 222 */     this.handle.getIntegers().write(4, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Location getLocation(World world)
/*     */   {
/* 231 */     return new Location(world, getX(), getY(), getZ());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLocation(Location loc)
/*     */   {
/* 239 */     setX(loc.getBlockX());
/* 240 */     setY(loc.getBlockY());
/* 241 */     setZ(loc.getBlockZ());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getData()
/*     */   {
/* 250 */     return ((Integer)this.handle.getIntegers().read(1)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setData(int value)
/*     */   {
/* 259 */     this.handle.getIntegers().write(1, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getDisableRelativeVolume()
/*     */   {
/* 269 */     return ((Boolean)this.handle.getSpecificModifier(Boolean.TYPE).read(0)).booleanValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDisableRelativeVolume(boolean value)
/*     */   {
/* 279 */     this.handle.getSpecificModifier(Boolean.TYPE).write(0, Boolean.valueOf(value));
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerWorldEvent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */