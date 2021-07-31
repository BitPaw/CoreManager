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
/*     */ public class WrapperPlayServerEntityMoveLook
/*     */   extends WrapperPlayServerEntity
/*     */ {
/*  24 */   public static final PacketType TYPE = PacketType.Play.Server.ENTITY_MOVE_LOOK;
/*     */   
/*     */   public WrapperPlayServerEntityMoveLook() {
/*  27 */     super(new PacketContainer(TYPE), TYPE);
/*  28 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerEntityMoveLook(PacketContainer packet) {
/*  32 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getDx()
/*     */   {
/*  42 */     return ((Byte)this.handle.getBytes().read(0)).byteValue() / 32.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDx(double value)
/*     */   {
/*  52 */     if (Math.abs(value) > 4.0D)
/*  53 */       throw new IllegalArgumentException("Displacement cannot exceed 4 meters.");
/*  54 */     this.handle.getBytes().write(0, Byte.valueOf((byte)(int)Math.min(Math.floor(value * 32.0D), 127.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getDy()
/*     */   {
/*  64 */     return ((Byte)this.handle.getBytes().read(1)).byteValue() / 32.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDy(double value)
/*     */   {
/*  74 */     if (Math.abs(value) > 4.0D)
/*  75 */       throw new IllegalArgumentException("Displacement cannot exceed 4 meters.");
/*  76 */     this.handle.getBytes().write(1, Byte.valueOf((byte)(int)Math.min(Math.floor(value * 32.0D), 127.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getDz()
/*     */   {
/*  86 */     return ((Byte)this.handle.getBytes().read(2)).byteValue() / 32.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDz(double value)
/*     */   {
/*  96 */     if (Math.abs(value) > 4.0D)
/*  97 */       throw new IllegalArgumentException("Displacement cannot exceed 4 meters.");
/*  98 */     this.handle.getBytes().write(2, Byte.valueOf((byte)(int)Math.min(Math.floor(value * 32.0D), 127.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getYaw()
/*     */   {
/* 106 */     return ((Byte)this.handle.getBytes().read(3)).byteValue() * 360.0F / 256.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setYaw(float value)
/*     */   {
/* 114 */     this.handle.getBytes().write(3, Byte.valueOf((byte)(int)(value * 256.0F / 360.0F)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getPitch()
/*     */   {
/* 122 */     return ((Byte)this.handle.getBytes().read(4)).byteValue() * 360.0F / 256.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPitch(float value)
/*     */   {
/* 130 */     this.handle.getBytes().write(4, Byte.valueOf((byte)(int)(value * 256.0F / 360.0F)));
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerEntityMoveLook.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */