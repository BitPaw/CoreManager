/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.PacketType.Play.Server;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.reflect.StructureModifier;
/*     */ import org.bukkit.GameMode;
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
/*     */ public class WrapperPlayServerGameStateChange
/*     */   extends AbstractPacket
/*     */ {
/*  26 */   public static final PacketType TYPE = PacketType.Play.Server.GAME_STATE_CHANGE;
/*     */   
/*     */   public WrapperPlayServerGameStateChange() {
/*  29 */     super(new PacketContainer(TYPE), TYPE);
/*  30 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerGameStateChange(PacketContainer packet) {
/*  34 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class Reasons
/*     */   {
/*     */     public static final int INVALID_BED = 0;
/*     */     
/*     */ 
/*     */     public static final int BEGIN_RAINING = 1;
/*     */     
/*     */ 
/*     */     public static final int END_RAINING = 2;
/*     */     
/*     */ 
/*     */     public static final int CHANGE_GAME_MODE = 3;
/*     */     
/*     */ 
/*     */     public static final int ENTER_CREDITS = 4;
/*     */     
/*     */ 
/*     */     public static final int DEMO_MESSAGES = 5;
/*     */     
/*     */ 
/*     */     public static final int ARROW_HITTING_PLAYER = 6;
/*     */     
/*     */ 
/*     */     public static final int SKY_FADE_VALUE = 7;
/*     */     
/*     */ 
/*     */     public static final int SKY_FADE_TIME = 8;
/*     */     
/*     */ 
/*  69 */     private static final Reasons INSTANCE = new Reasons();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public static Reasons getInstance()
/*     */     {
/*  76 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getReason()
/*     */   {
/*  86 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setReason(int value)
/*     */   {
/*  95 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public GameMode getGameMode()
/*     */   {
/* 105 */     return GameMode.getByValue(((Integer)this.handle.getIntegers().read(1)).intValue());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setGameMode(GameMode value)
/*     */   {
/* 115 */     this.handle.getIntegers().write(1, Integer.valueOf(value.getValue()));
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerGameStateChange.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */