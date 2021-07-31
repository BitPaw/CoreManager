/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.reflect.StructureModifier;
/*    */ import java.security.PublicKey;
/*    */ 
/*    */ public class WrapperLoginServerEncryptionBegin extends AbstractPacket
/*    */ {
/*  9 */   public static final com.comphenix.protocol.PacketType TYPE = com.comphenix.protocol.PacketType.Login.Server.ENCRYPTION_BEGIN;
/*    */   
/*    */   public WrapperLoginServerEncryptionBegin() {
/* 12 */     super(new PacketContainer(TYPE), TYPE);
/* 13 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperLoginServerEncryptionBegin(PacketContainer packet) {
/* 17 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getServerId()
/*    */   {
/* 25 */     return (String)this.handle.getStrings().read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setServerId(String value)
/*    */   {
/* 33 */     this.handle.getStrings().write(0, value);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public PublicKey getPublicKey()
/*    */   {
/* 41 */     return (PublicKey)this.handle.getSpecificModifier(PublicKey.class).read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setPublicKey(PublicKey value)
/*    */   {
/* 49 */     this.handle.getSpecificModifier(PublicKey.class).write(0, value);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public byte[] getVerifyToken()
/*    */   {
/* 57 */     return (byte[])this.handle.getByteArrays().read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void getVerifyToken(byte[] value)
/*    */   {
/* 65 */     this.handle.getByteArrays().write(0, value);
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperLoginServerEncryptionBegin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */