/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.PacketType.Play.Client;
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
/*     */ public class WrapperPlayClientPositionLook
/*     */   extends WrapperPlayClientFlying
/*     */ {
/*  24 */   public static final PacketType TYPE = PacketType.Play.Client.POSITION_LOOK;
/*     */   
/*     */   public WrapperPlayClientPositionLook() {
/*  27 */     super(new PacketContainer(TYPE), TYPE);
/*  28 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayClientPositionLook(PacketContainer packet) {
/*  32 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getX()
/*     */   {
/*  40 */     return ((Double)this.handle.getDoubles().read(0)).doubleValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setX(double value)
/*     */   {
/*  48 */     this.handle.getDoubles().write(0, Double.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getY()
/*     */   {
/*  56 */     return ((Double)this.handle.getDoubles().read(1)).doubleValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setY(double value)
/*     */   {
/*  64 */     this.handle.getDoubles().write(1, Double.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getStance()
/*     */   {
/*  72 */     return ((Double)this.handle.getDoubles().read(3)).doubleValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setStance(double value)
/*     */   {
/*  80 */     this.handle.getDoubles().write(3, Double.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getZ()
/*     */   {
/*  88 */     return ((Double)this.handle.getDoubles().read(2)).doubleValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setZ(double value)
/*     */   {
/*  96 */     this.handle.getDoubles().write(2, Double.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getYaw()
/*     */   {
/* 104 */     return ((Float)this.handle.getFloat().read(0)).floatValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setYaw(float value)
/*     */   {
/* 112 */     this.handle.getFloat().write(0, Float.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getPitch()
/*     */   {
/* 120 */     return ((Float)this.handle.getFloat().read(1)).floatValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPitch(float value)
/*     */   {
/* 128 */     this.handle.getFloat().write(1, Float.valueOf(value));
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayClientPositionLook.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */