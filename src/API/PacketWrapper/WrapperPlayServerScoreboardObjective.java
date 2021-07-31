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
/*     */ public class WrapperPlayServerScoreboardObjective
/*     */   extends AbstractPacket
/*     */ {
/*  25 */   public static final PacketType TYPE = PacketType.Play.Server.SCOREBOARD_OBJECTIVE;
/*     */   
/*     */ 
/*     */   public static class Modes
/*     */     extends IntEnum
/*     */   {
/*     */     public static final int ADD_OBJECTIVE = 0;
/*     */     
/*     */     public static final int REMOVE_OBJECTIVE = 1;
/*     */     
/*     */     public static final int UPDATE_VALUE = 2;
/*     */     
/*  37 */     private static final Modes INSTANCE = new Modes();
/*     */     
/*     */     public static Modes getInstance() {
/*  40 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */   
/*     */   public WrapperPlayServerScoreboardObjective() {
/*  45 */     super(new PacketContainer(TYPE), TYPE);
/*  46 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerScoreboardObjective(PacketContainer packet) {
/*  50 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getObjectiveName()
/*     */   {
/*  58 */     return (String)this.handle.getStrings().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setObjectiveName(String value)
/*     */   {
/*  66 */     this.handle.getStrings().write(0, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getObjectiveValue()
/*     */   {
/*  74 */     return (String)this.handle.getStrings().read(1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setObjectiveValue(String value)
/*     */   {
/*  82 */     this.handle.getStrings().write(1, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getPacketMode()
/*     */   {
/*  93 */     return ((Integer)this.handle.getIntegers().read(0)).byteValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPacketMode(byte value)
/*     */   {
/* 104 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerScoreboardObjective.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */