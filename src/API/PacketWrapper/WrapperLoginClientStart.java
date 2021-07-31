/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.wrappers.WrappedGameProfile;
/*    */ 
/*    */ public class WrapperLoginClientStart extends AbstractPacket
/*    */ {
/*  8 */   public static final com.comphenix.protocol.PacketType TYPE = com.comphenix.protocol.PacketType.Login.Client.START;
/*    */   
/*    */   public WrapperLoginClientStart() {
/* 11 */     super(new PacketContainer(TYPE), TYPE);
/* 12 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperLoginClientStart(PacketContainer packet) {
/* 16 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public WrappedGameProfile getProfile()
/*    */   {
/* 26 */     return (WrappedGameProfile)this.handle.getGameProfiles().read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setProfile(WrappedGameProfile value)
/*    */   {
/* 34 */     this.handle.getGameProfiles().write(0, value);
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperLoginClientStart.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */