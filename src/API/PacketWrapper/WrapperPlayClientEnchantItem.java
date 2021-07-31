/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.PacketType;
/*    */ import com.comphenix.protocol.PacketType.Play.Client;
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
/*    */ 
/*    */ public class WrapperPlayClientEnchantItem
/*    */   extends AbstractPacket
/*    */ {
/* 24 */   public static final PacketType TYPE = PacketType.Play.Client.ENCHANT_ITEM;
/*    */   
/*    */   public WrapperPlayClientEnchantItem() {
/* 27 */     super(new PacketContainer(TYPE), TYPE);
/* 28 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayClientEnchantItem(PacketContainer packet) {
/* 32 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public byte getWindowId()
/*    */   {
/* 40 */     return ((Integer)this.handle.getIntegers().read(0)).byteValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setWindowId(byte value)
/*    */   {
/* 48 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public byte getEnchantment()
/*    */   {
/* 56 */     return ((Integer)this.handle.getIntegers().read(1)).byteValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setEnchantment(byte value)
/*    */   {
/* 64 */     this.handle.getIntegers().write(1, Integer.valueOf(value));
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayClientEnchantItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */