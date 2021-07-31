/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ 
/*     */ public class WrapperPlayServerAbilities extends AbstractPacket
/*     */ {
/*   7 */   public static final com.comphenix.protocol.PacketType TYPE = com.comphenix.protocol.PacketType.Play.Server.ABILITIES;
/*     */   
/*     */   public WrapperPlayServerAbilities() {
/*  10 */     super(new PacketContainer(TYPE), TYPE);
/*  11 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerAbilities(PacketContainer packet) {
/*  15 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isCreativeMode()
/*     */   {
/*  23 */     return ((Boolean)this.handle.getSpecificModifier(Boolean.TYPE).read(0)).booleanValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCreativeMode(boolean value)
/*     */   {
/*  31 */     this.handle.getSpecificModifier(Boolean.TYPE).write(0, Boolean.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFlying()
/*     */   {
/*  39 */     return ((Boolean)this.handle.getSpecificModifier(Boolean.TYPE).read(1)).booleanValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFlying(boolean value)
/*     */   {
/*  47 */     this.handle.getSpecificModifier(Boolean.TYPE).write(1, Boolean.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFlyingAllowed()
/*     */   {
/*  55 */     return ((Boolean)this.handle.getSpecificModifier(Boolean.TYPE).read(2)).booleanValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFlyingAllowed(boolean value)
/*     */   {
/*  63 */     this.handle.getSpecificModifier(Boolean.TYPE).write(2, Boolean.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isGodMode()
/*     */   {
/*  71 */     return ((Boolean)this.handle.getSpecificModifier(Boolean.TYPE).read(3)).booleanValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setGodMode(boolean value)
/*     */   {
/*  79 */     this.handle.getSpecificModifier(Boolean.TYPE).write(3, Boolean.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getFlyingSpeed()
/*     */   {
/*  87 */     return ((Float)this.handle.getFloat().read(0)).floatValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFlyingSpeed(float value)
/*     */   {
/*  95 */     this.handle.getFloat().write(0, Float.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getWalkingSpeed()
/*     */   {
/* 103 */     return ((Float)this.handle.getFloat().read(1)).floatValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setWalkingSpeed(float value)
/*     */   {
/* 111 */     this.handle.getFloat().write(1, Float.valueOf(value));
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerAbilities.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */