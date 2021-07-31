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
/*    */ public class WrapperPlayClientTransaction
/*    */   extends AbstractPacket
/*    */ {
/* 24 */   public static final PacketType TYPE = PacketType.Play.Client.TRANSACTION;
/*    */   
/*    */   public WrapperPlayClientTransaction() {
/* 27 */     super(new PacketContainer(TYPE), TYPE);
/* 28 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayClientTransaction(PacketContainer packet) {
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
/*    */ 
/*    */ 
/*    */   public short getActionNumber()
/*    */   {
/* 58 */     return ((Short)this.handle.getShorts().read(0)).shortValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setActionNumber(short value)
/*    */   {
/* 68 */     this.handle.getShorts().write(0, Short.valueOf(value));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean getAccepted()
/*    */   {
/* 76 */     return ((Boolean)this.handle.getSpecificModifier(Boolean.TYPE).read(0)).booleanValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setAccepted(boolean value)
/*    */   {
/* 84 */     this.handle.getSpecificModifier(Boolean.TYPE).write(0, Boolean.valueOf(value));
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayClientTransaction.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */