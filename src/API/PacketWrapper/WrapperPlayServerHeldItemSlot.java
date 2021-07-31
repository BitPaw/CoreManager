/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.PacketType;
/*    */ import com.comphenix.protocol.PacketType.Play.Server;
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.reflect.StructureModifier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WrapperPlayServerHeldItemSlot
/*    */   extends AbstractPacket
/*    */ {
/* 23 */   public static final PacketType TYPE = PacketType.Play.Server.HELD_ITEM_SLOT;
/*    */   
/*    */   public WrapperPlayServerHeldItemSlot() {
/* 26 */     super(new PacketContainer(TYPE), TYPE);
/* 27 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayServerHeldItemSlot(PacketContainer packet) {
/* 31 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public short getSlotId()
/*    */   {
/* 39 */     return ((Integer)this.handle.getIntegers().read(0)).shortValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setSlotId(short value)
/*    */   {
/* 47 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerHeldItemSlot.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */