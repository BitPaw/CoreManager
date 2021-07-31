/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.wrappers.WrappedChatComponent;
/*    */ 
/*    */ public class WrapperPlayServerChat extends AbstractPacket
/*    */ {
/*  8 */   public static final com.comphenix.protocol.PacketType TYPE = com.comphenix.protocol.PacketType.Play.Server.CHAT;
/*    */   
/*    */   public WrapperPlayServerChat() {
/* 11 */     super(new PacketContainer(TYPE), TYPE);
/* 12 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayServerChat(PacketContainer packet) {
/* 16 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public WrappedChatComponent getMessage()
/*    */   {
/* 24 */     return (WrappedChatComponent)this.handle.getChatComponents().read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setMessage(WrappedChatComponent value)
/*    */   {
/* 32 */     this.handle.getChatComponents().write(0, value);
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerChat.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */