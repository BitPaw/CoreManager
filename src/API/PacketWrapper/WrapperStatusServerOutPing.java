/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ 
/*    */ public class WrapperStatusServerOutPing extends AbstractPacket
/*    */ {
/*  7 */   public static final com.comphenix.protocol.PacketType TYPE = com.comphenix.protocol.PacketType.Status.Server.OUT_PING;
/*    */   
/*    */   public WrapperStatusServerOutPing() {
/* 10 */     super(new PacketContainer(TYPE), TYPE);
/* 11 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperStatusServerOutPing(PacketContainer packet) {
/* 15 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public long getTime()
/*    */   {
/* 23 */     return ((Long)this.handle.getLongs().read(0)).longValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setToken(long value)
/*    */   {
/* 31 */     this.handle.getLongs().write(0, Long.valueOf(value));
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperStatusServerOutPing.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */