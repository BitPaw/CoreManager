/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ 
/*    */ public class WrapperPlayClientCloseWindow extends AbstractPacket
/*    */ {
/*  7 */   public static final com.comphenix.protocol.PacketType TYPE = com.comphenix.protocol.PacketType.Play.Client.CLOSE_WINDOW;
/*    */   
/*    */   public WrapperPlayClientCloseWindow() {
/* 10 */     super(new PacketContainer(TYPE), TYPE);
/* 11 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayClientCloseWindow(PacketContainer packet) {
/* 15 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public byte getWindowId()
/*    */   {
/* 23 */     return ((Integer)this.handle.getIntegers().read(0)).byteValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setWindowId(byte value)
/*    */   {
/* 31 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayClientCloseWindow.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */