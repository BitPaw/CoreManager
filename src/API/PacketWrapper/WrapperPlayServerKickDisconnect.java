/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.wrappers.WrappedChatComponent;
/*    */ 
/*    */ public class WrapperPlayServerKickDisconnect extends AbstractPacket
/*    */ {
/*  8 */   public static final com.comphenix.protocol.PacketType TYPE = com.comphenix.protocol.PacketType.Play.Server.KICK_DISCONNECT;
/*    */   
/*    */   public WrapperPlayServerKickDisconnect() {
/* 11 */     super(new PacketContainer(TYPE), TYPE);
/* 12 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayServerKickDisconnect(PacketContainer packet) {
/* 16 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public WrappedChatComponent getReason()
/*    */   {
/* 24 */     return (WrappedChatComponent)this.handle.getChatComponents().read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setReason(WrappedChatComponent value)
/*    */   {
/* 32 */     this.handle.getChatComponents().write(0, value);
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerKickDisconnect.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */