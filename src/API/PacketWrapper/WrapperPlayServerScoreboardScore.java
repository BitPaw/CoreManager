/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.PacketType.Play.Server;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.reflect.IntEnum;
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
/*     */ public class WrapperPlayServerScoreboardScore
/*     */   extends AbstractPacket
/*     */ {
/*  25 */   public static final PacketType TYPE = PacketType.Play.Server.SCOREBOARD_SCORE;
/*     */   
/*     */ 
/*     */ 
/*     */   public static class Modes
/*     */     extends IntEnum
/*     */   {
/*     */     public static final int SET_SCORE = 0;
/*     */     
/*     */     public static final int REMOVE_SCORE = 1;
/*     */     
/*  36 */     private static final Modes INSTANCE = new Modes();
/*     */     
/*     */     public static Modes getInstance() {
/*  39 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */   
/*     */   public WrapperPlayServerScoreboardScore() {
/*  44 */     super(new PacketContainer(TYPE), TYPE);
/*  45 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerScoreboardScore(PacketContainer packet) {
/*  49 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getItemName()
/*     */   {
/*  57 */     return (String)this.handle.getStrings().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setItemName(String value)
/*     */   {
/*  65 */     this.handle.getStrings().write(0, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getPacketMode()
/*     */   {
/*  75 */     return ((Integer)this.handle.getIntegers().read(1)).byteValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPacketMode(byte value)
/*     */   {
/*  85 */     this.handle.getIntegers().write(1, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getScoreName()
/*     */   {
/*  93 */     return (String)this.handle.getStrings().read(1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setScoreName(String value)
/*     */   {
/* 101 */     this.handle.getStrings().write(1, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getValue()
/*     */   {
/* 109 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValue(int value)
/*     */   {
/* 117 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerScoreboardScore.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */