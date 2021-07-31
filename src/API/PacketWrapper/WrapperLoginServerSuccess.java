/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.wrappers.WrappedGameProfile;
/*    */ 
/*    */ public class WrapperLoginServerSuccess extends AbstractPacket
/*    */ {
/*  8 */   public static final com.comphenix.protocol.PacketType TYPE = com.comphenix.protocol.PacketType.Login.Server.SUCCESS;
/*    */   
/*    */   public WrapperLoginServerSuccess() {
/* 11 */     super(new PacketContainer(TYPE), TYPE);
/* 12 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperLoginServerSuccess(PacketContainer packet) {
/* 16 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public WrappedGameProfile getProfile()
/*    */   {
/* 24 */     return (WrappedGameProfile)this.handle.getGameProfiles().read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setProfile(WrappedGameProfile value)
/*    */   {
/* 32 */     this.handle.getGameProfiles().write(0, value);
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperLoginServerSuccess.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */