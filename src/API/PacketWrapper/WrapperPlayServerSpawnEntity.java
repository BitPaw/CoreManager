/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.PacketType.Play.Server;
/*     */ import com.comphenix.protocol.ProtocolLibrary;
/*     */ import com.comphenix.protocol.ProtocolManager;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.events.PacketEvent;
/*     */ import com.comphenix.protocol.injector.PacketConstructor;
/*     */ import com.comphenix.protocol.reflect.IntEnum;
/*     */ import com.comphenix.protocol.reflect.StructureModifier;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
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
/*     */ public class WrapperPlayServerSpawnEntity
/*     */   extends AbstractPacket
/*     */ {
/*  30 */   public static final PacketType TYPE = PacketType.Play.Server.SPAWN_ENTITY;
/*     */   
/*     */   private static PacketConstructor entityConstructor;
/*     */   
/*     */ 
/*     */   public static class ObjectTypes
/*     */     extends IntEnum
/*     */   {
/*     */     public static final int BOAT = 1;
/*     */     
/*     */     public static final int ITEM_STACK = 2;
/*     */     
/*     */     public static final int MINECART = 10;
/*     */     
/*     */     public static final int MINECART_STORAGE = 11;
/*     */     
/*     */     public static final int MINECART_POWERED = 12;
/*     */     
/*     */     public static final int ACTIVATED_TNT = 50;
/*     */     
/*     */     public static final int ENDER_CRYSTAL = 51;
/*     */     public static final int ARROW_PROJECTILE = 60;
/*     */     public static final int SNOWBALL_PROJECTILE = 61;
/*     */     public static final int EGG_PROJECTILE = 62;
/*     */     public static final int FIRE_BALL_GHAST = 63;
/*     */     public static final int FIRE_BALL_BLAZE = 64;
/*     */     public static final int THROWN_ENDERPEARL = 65;
/*     */     public static final int WITHER_SKULL = 66;
/*     */     public static final int FALLING_BLOCK = 70;
/*     */     public static final int ITEM_FRAME = 71;
/*     */     public static final int EYE_OF_ENDER = 72;
/*     */     public static final int THROWN_POTION = 73;
/*     */     public static final int FALLING_DRAGON_EGG = 74;
/*     */     public static final int THROWN_EXP_BOTTLE = 75;
/*     */     public static final int FISHING_FLOAT = 90;
/*  65 */     private static ObjectTypes INSTANCE = new ObjectTypes();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public static ObjectTypes getInstance()
/*     */     {
/*  72 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */   
/*     */   public WrapperPlayServerSpawnEntity() {
/*  77 */     super(new PacketContainer(TYPE), TYPE);
/*  78 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerSpawnEntity(PacketContainer packet) {
/*  82 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */   public WrapperPlayServerSpawnEntity(Entity entity, int type, int objectData) {
/*  86 */     super(fromEntity(entity, type, objectData), TYPE);
/*     */   }
/*     */   
/*     */   private static PacketContainer fromEntity(Entity entity, int type, int objectData)
/*     */   {
/*  91 */     if (entityConstructor == null)
/*  92 */       entityConstructor = ProtocolLibrary.getProtocolManager().createPacketConstructor(TYPE, new Object[] { entity, Integer.valueOf(type), Integer.valueOf(objectData) });
/*  93 */     return entityConstructor.createPacket(new Object[] { entity, Integer.valueOf(type), Integer.valueOf(objectData) });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getEntityID()
/*     */   {
/* 101 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getEntity(World world)
/*     */   {
/* 110 */     return (Entity)this.handle.getEntityModifier(world).read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getEntity(PacketEvent event)
/*     */   {
/* 119 */     return getEntity(event.getPlayer().getWorld());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEntityID(int value)
/*     */   {
/* 127 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getType()
/*     */   {
/* 135 */     return ((Integer)this.handle.getIntegers().read(9)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setType(int value)
/*     */   {
/* 143 */     this.handle.getIntegers().write(9, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getX()
/*     */   {
/* 153 */     return ((Integer)this.handle.getIntegers().read(1)).intValue() / 32.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setX(double value)
/*     */   {
/* 161 */     this.handle.getIntegers().write(1, Integer.valueOf((int)Math.floor(value * 32.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getY()
/*     */   {
/* 171 */     return ((Integer)this.handle.getIntegers().read(2)).intValue() / 32.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setY(double value)
/*     */   {
/* 179 */     this.handle.getIntegers().write(2, Integer.valueOf((int)Math.floor(value * 32.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getZ()
/*     */   {
/* 189 */     return ((Integer)this.handle.getIntegers().read(3)).intValue() / 32.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setZ(double value)
/*     */   {
/* 197 */     this.handle.getIntegers().write(3, Integer.valueOf((int)Math.floor(value * 32.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getOptionalSpeedX()
/*     */   {
/* 207 */     return ((Integer)this.handle.getIntegers().read(4)).intValue() / 8000.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOptionalSpeedX(double value)
/*     */   {
/* 215 */     this.handle.getIntegers().write(4, Integer.valueOf((int)(value * 8000.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getOptionalSpeedY()
/*     */   {
/* 225 */     return ((Integer)this.handle.getIntegers().read(5)).intValue() / 8000.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOptionalSpeedY(double value)
/*     */   {
/* 233 */     this.handle.getIntegers().write(5, Integer.valueOf((int)(value * 8000.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getOptionalSpeedZ()
/*     */   {
/* 243 */     return ((Integer)this.handle.getIntegers().read(6)).intValue() / 8000.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOptionalSpeedZ(double value)
/*     */   {
/* 251 */     this.handle.getIntegers().write(6, Integer.valueOf((int)(value * 8000.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getYaw()
/*     */   {
/* 259 */     return ((Integer)this.handle.getIntegers().read(7)).intValue() * 360.0F / 256.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setYaw(float value)
/*     */   {
/* 267 */     this.handle.getIntegers().write(7, Integer.valueOf((int)(value * 256.0F / 360.0F)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getPitch()
/*     */   {
/* 275 */     return ((Integer)this.handle.getIntegers().read(8)).intValue() * 360.0F / 256.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPitch(float value)
/*     */   {
/* 283 */     this.handle.getIntegers().write(8, Integer.valueOf((int)(value * 256.0F / 360.0F)));
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getObjectData()
/*     */   {
/* 320 */     return ((Integer)this.handle.getIntegers().read(10)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setObjectData(int value)
/*     */   {
/* 330 */     this.handle.getIntegers().write(10, Integer.valueOf(value));
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerSpawnEntity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */