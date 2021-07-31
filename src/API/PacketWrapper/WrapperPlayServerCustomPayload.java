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
/*    */ 
/*    */ public class WrapperPlayServerCustomPayload
/*    */   extends AbstractPacket
/*    */ {
/* 24 */   public static final PacketType TYPE = PacketType.Play.Server.CUSTOM_PAYLOAD;
/*    */   
/*    */   public WrapperPlayServerCustomPayload() {
/* 27 */     super(new PacketContainer(TYPE), TYPE);
/* 28 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayServerCustomPayload(PacketContainer packet) {
/* 32 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getChannel()
/*    */   {
/* 40 */     return (String)this.handle.getStrings().read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setChannel(String value)
/*    */   {
/* 48 */     this.handle.getStrings().write(0, value);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public byte[] getData()
/*    */   {
/* 56 */     return (byte[])this.handle.getByteArrays().read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setData(byte[] value)
/*    */   {
/* 64 */     this.handle.getByteArrays().write(0, value);
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerCustomPayload.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */