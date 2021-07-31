/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.PacketType;
/*    */ import com.comphenix.protocol.PacketType.Play.Client;
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.reflect.StructureModifier;
/*    */ 
/*    */ public class WrapperPlayClientKeepAlive extends AbstractPacket
/*    */ {
/* 10 */   public static final PacketType TYPE = PacketType.Play.Client.KEEP_ALIVE;
/*    */   
/*    */   public WrapperPlayClientKeepAlive() {
/* 13 */     super(new PacketContainer(TYPE), TYPE);
/* 14 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayClientKeepAlive(PacketContainer packet) {
/* 18 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getKeepAliveId()
/*    */   {
/* 26 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setKeepAliveId(int value)
/*    */   {
/* 34 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayClientKeepAlive.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */