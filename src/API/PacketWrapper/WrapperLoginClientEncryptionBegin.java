/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ 
/*    */ public class WrapperLoginClientEncryptionBegin extends AbstractPacket
/*    */ {
/*  7 */   public static final com.comphenix.protocol.PacketType TYPE = com.comphenix.protocol.PacketType.Login.Client.ENCRYPTION_BEGIN;
/*    */   
/*    */   public WrapperLoginClientEncryptionBegin() {
/* 10 */     super(new PacketContainer(TYPE), TYPE);
/* 11 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperLoginClientEncryptionBegin(PacketContainer packet) {
/* 15 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public byte[] getSharedSecret()
/*    */   {
/* 23 */     return (byte[])this.handle.getByteArrays().read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void getSharedSecret(byte[] value)
/*    */   {
/* 31 */     this.handle.getByteArrays().write(0, value);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public byte[] getVerifyTokenResponse()
/*    */   {
/* 39 */     return (byte[])this.handle.getByteArrays().read(1);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setVerifyTokenResponse(byte[] value)
/*    */   {
/* 47 */     this.handle.getByteArrays().write(1, value);
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperLoginClientEncryptionBegin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */