/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.PacketType;
/*    */ import com.comphenix.protocol.PacketType.Play.Client;
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.reflect.StructureModifier;
/*    */ 
/*    */ public class WrapperPlayClientTabComplete
/*    */   extends AbstractPacket
/*    */ {
/* 11 */   public static final PacketType TYPE = PacketType.Play.Client.TAB_COMPLETE;
/*    */   
/*    */   public WrapperPlayClientTabComplete() {
/* 14 */     super(new PacketContainer(TYPE), TYPE);
/* 15 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayClientTabComplete(PacketContainer packet) {
/* 19 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getText()
/*    */   {
/* 27 */     return (String)this.handle.getStrings().read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setText(String value)
/*    */   {
/* 35 */     this.handle.getStrings().write(0, value);
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayClientTabComplete.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */