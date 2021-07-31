/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.PacketType;
/*    */ import com.comphenix.protocol.PacketType.Play.Server;
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.reflect.StructureModifier;
/*    */ 
/*    */ 
/*    */ public class WrapperPlayServerKeepAlive
/*    */   extends AbstractPacket
/*    */ {
/* 12 */   public static final PacketType TYPE = PacketType.Play.Server.KEEP_ALIVE;
/*    */   
/*    */   public WrapperPlayServerKeepAlive() {
/* 15 */     super(new PacketContainer(TYPE), TYPE);
/* 16 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayServerKeepAlive(PacketContainer packet) {
/* 20 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getKeepAliveId()
/*    */   {
/* 28 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setKeepAliveId(int value)
/*    */   {
/* 36 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerKeepAlive.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */