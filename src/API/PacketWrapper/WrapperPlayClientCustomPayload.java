/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ 
/*    */ public class WrapperPlayClientCustomPayload extends AbstractPacket
/*    */ {
/*  7 */   public static final com.comphenix.protocol.PacketType TYPE = com.comphenix.protocol.PacketType.Play.Client.CUSTOM_PAYLOAD;
/*    */   
/*    */   public WrapperPlayClientCustomPayload() {
/* 10 */     super(new PacketContainer(TYPE), TYPE);
/* 11 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayClientCustomPayload(PacketContainer packet) {
/* 15 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getChannel()
/*    */   {
/* 23 */     return (String)this.handle.getStrings().read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setChannel(String value)
/*    */   {
/* 31 */     this.handle.getStrings().write(0, value);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public short getLength()
/*    */   {
/* 39 */     return ((Integer)this.handle.getIntegers().read(0)).shortValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setLength(short value)
/*    */   {
/* 47 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public byte[] getData()
/*    */   {
/* 55 */     return (byte[])this.handle.getByteArrays().read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setData(byte[] value)
/*    */   {
/* 65 */     setLength((short)value.length);
/* 66 */     this.handle.getByteArrays().write(0, value);
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayClientCustomPayload.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */