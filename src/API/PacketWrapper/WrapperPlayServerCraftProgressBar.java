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
/*     */ public class WrapperPlayServerCraftProgressBar
/*     */   extends AbstractPacket
/*     */ {
/*  24 */   public static final PacketType TYPE = PacketType.Play.Server.CRAFT_PROGRESS_BAR;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class FurnaceProperties
/*     */   {
/*     */     public static final int PROGRESS_ARROW = 0;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public static final int PROGRESS_FIRE_ICON = 1;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  42 */     private static FurnaceProperties INSTANCE = new FurnaceProperties();
/*     */     
/*     */     public static FurnaceProperties getInstace() {
/*  45 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */   
/*     */   public WrapperPlayServerCraftProgressBar() {
/*  50 */     super(new PacketContainer(TYPE), TYPE);
/*  51 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerCraftProgressBar(PacketContainer packet) {
/*  55 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getWindowId()
/*     */   {
/*  63 */     return ((Integer)this.handle.getIntegers().read(0)).byteValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setWindowId(byte value)
/*     */   {
/*  71 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getProperty()
/*     */   {
/*  82 */     return ((Integer)this.handle.getIntegers().read(1)).shortValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setProperty(short value)
/*     */   {
/*  93 */     this.handle.getIntegers().write(1, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getValue()
/*     */   {
/* 103 */     return ((Integer)this.handle.getIntegers().read(2)).shortValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValue(short value)
/*     */   {
/* 113 */     this.handle.getIntegers().write(2, Integer.valueOf(value));
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerCraftProgressBar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */