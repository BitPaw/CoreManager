/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.PacketType.Play.Server;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.reflect.StructureModifier;
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
/*     */ public class WrapperPlayServerSpawnEntityWeather
/*     */   extends AbstractPacket
/*     */ {
/*  24 */   public static final PacketType TYPE = PacketType.Play.Server.SPAWN_ENTITY_WEATHER;
/*     */   
/*     */   public WrapperPlayServerSpawnEntityWeather() {
/*  27 */     super(new PacketContainer(TYPE), TYPE);
/*  28 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerSpawnEntityWeather(PacketContainer packet) {
/*  32 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getEntityId()
/*     */   {
/*  40 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEntityId(int value)
/*     */   {
/*  48 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getType()
/*     */   {
/*  58 */     return ((Integer)this.handle.getIntegers().read(4)).byteValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setType(byte value)
/*     */   {
/*  68 */     this.handle.getIntegers().write(4, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getX()
/*     */   {
/*  76 */     return ((Integer)this.handle.getIntegers().read(1)).intValue() / 32.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setX(double value)
/*     */   {
/*  84 */     this.handle.getIntegers().write(1, Integer.valueOf((int)(value * 32.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getY()
/*     */   {
/*  92 */     return ((Integer)this.handle.getIntegers().read(2)).intValue() / 32.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setY(double value)
/*     */   {
/* 100 */     this.handle.getIntegers().write(2, Integer.valueOf((int)(value * 32.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getZ()
/*     */   {
/* 108 */     return ((Integer)this.handle.getIntegers().read(3)).intValue() / 32.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setZ(double value)
/*     */   {
/* 116 */     this.handle.getIntegers().write(3, Integer.valueOf((int)(value * 32.0D)));
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerSpawnEntityWeather.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */