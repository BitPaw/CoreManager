/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.PacketType;
/*    */ import com.comphenix.protocol.PacketType.Play.Server;
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.reflect.IntEnum;
/*    */ import com.comphenix.protocol.reflect.StructureModifier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WrapperPlayServerScoreboardDisplayObjective
/*    */   extends AbstractPacket
/*    */ {
/* 25 */   public static final PacketType TYPE = PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE;
/*    */   
/*    */ 
/*    */   public static class Positions
/*    */     extends IntEnum
/*    */   {
/*    */     public static final int LIST = 0;
/*    */     
/*    */     public static final int SIDEBAR = 1;
/*    */     
/*    */     public static final int BELOW_NAME = 2;
/* 36 */     private static final Positions INSTANCE = new Positions();
/*    */     
/*    */     public static Positions getInstance() {
/* 39 */       return INSTANCE;
/*    */     }
/*    */   }
/*    */   
/*    */   public WrapperPlayServerScoreboardDisplayObjective() {
/* 44 */     super(new PacketContainer(TYPE), TYPE);
/* 45 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayServerScoreboardDisplayObjective(PacketContainer packet) {
/* 49 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public byte getPosition()
/*    */   {
/* 57 */     return ((Integer)this.handle.getIntegers().read(0)).byteValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setPosition(byte value)
/*    */   {
/* 65 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getScoreName()
/*    */   {
/* 73 */     return (String)this.handle.getStrings().read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setScoreName(String value)
/*    */   {
/* 81 */     this.handle.getStrings().write(0, value);
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerScoreboardDisplayObjective.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */