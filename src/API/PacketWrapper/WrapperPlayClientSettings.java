/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.reflect.StructureModifier;
/*     */ import com.comphenix.protocol.wrappers.EnumWrappers.ChatVisibility;
/*     */ 
/*     */ public class WrapperPlayClientSettings extends AbstractPacket
/*     */ {
/*   9 */   public static final com.comphenix.protocol.PacketType TYPE = com.comphenix.protocol.PacketType.Play.Client.SETTINGS;
/*     */   
/*     */   public WrapperPlayClientSettings() {
/*  12 */     super(new PacketContainer(TYPE), TYPE);
/*  13 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayClientSettings(PacketContainer packet) {
/*  17 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getLocale()
/*     */   {
/*  25 */     return (String)this.handle.getStrings().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLocale(String value)
/*     */   {
/*  33 */     this.handle.getStrings().write(0, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getViewDistance()
/*     */   {
/*  41 */     return ((Integer)this.handle.getIntegers().read(0)).byteValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setViewDistance(byte value)
/*     */   {
/*  49 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public EnumWrappers.ChatVisibility getChatVisibility()
/*     */   {
/*  57 */     return (EnumWrappers.ChatVisibility)this.handle.getChatVisibilities().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setChatFlags(EnumWrappers.ChatVisibility value)
/*     */   {
/*  65 */     this.handle.getChatVisibilities().write(0, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getChatColours()
/*     */   {
/*  73 */     return ((Boolean)this.handle.getSpecificModifier(Boolean.TYPE).read(0)).booleanValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setChatColours(boolean value)
/*     */   {
/*  81 */     this.handle.getSpecificModifier(Boolean.TYPE).write(0, Boolean.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public com.comphenix.protocol.wrappers.EnumWrappers.Difficulty getDifficulty()
/*     */   {
/*  89 */     return (com.comphenix.protocol.wrappers.EnumWrappers.Difficulty)this.handle.getDifficulties().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDifficulty(com.comphenix.protocol.wrappers.EnumWrappers.Difficulty difficulty)
/*     */   {
/*  97 */     this.handle.getDifficulties().write(0, difficulty);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getShowCape()
/*     */   {
/* 105 */     return ((Boolean)this.handle.getSpecificModifier(Boolean.TYPE).read(1)).booleanValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setShowCape(boolean value)
/*     */   {
/* 113 */     this.handle.getSpecificModifier(Boolean.TYPE).write(1, Boolean.valueOf(value));
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayClientSettings.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */