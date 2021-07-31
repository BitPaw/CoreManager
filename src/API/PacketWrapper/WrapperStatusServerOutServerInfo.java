/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.wrappers.WrappedServerPing;
/*    */ 
/*    */ public class WrapperStatusServerOutServerInfo extends AbstractPacket
/*    */ {
/*  8 */   public static final com.comphenix.protocol.PacketType TYPE = com.comphenix.protocol.PacketType.Status.Server.OUT_SERVER_INFO;
/*    */   
/*    */   public WrapperStatusServerOutServerInfo() {
/* 11 */     super(new PacketContainer(TYPE), TYPE);
/* 12 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperStatusServerOutServerInfo(PacketContainer packet) {
/* 16 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public WrappedServerPing getServerPing()
/*    */   {
/* 24 */     return (WrappedServerPing)this.handle.getServerPings().read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setServerPing(WrappedServerPing value)
/*    */   {
/* 32 */     this.handle.getServerPings().write(0, value);
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperStatusServerOutServerInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */