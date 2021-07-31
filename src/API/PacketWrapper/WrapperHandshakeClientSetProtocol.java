/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.reflect.StructureModifier;
/*    */ 
/*    */ public class WrapperHandshakeClientSetProtocol extends AbstractPacket
/*    */ {
/*  8 */   public static final com.comphenix.protocol.PacketType TYPE = com.comphenix.protocol.PacketType.Handshake.Client.SET_PROTOCOL;
/*    */   
/*    */   public WrapperHandshakeClientSetProtocol() {
/* 11 */     super(new PacketContainer(TYPE), TYPE);
/* 12 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperHandshakeClientSetProtocol(PacketContainer packet) {
/* 16 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getProtocolVersion()
/*    */   {
/* 26 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setProtocolVersion(int value)
/*    */   {
/* 36 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getServerHostname()
/*    */   {
/* 44 */     return (String)this.handle.getStrings().read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setServerHostname(String value)
/*    */   {
/* 52 */     this.handle.getStrings().write(0, value);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public short getServerPort()
/*    */   {
/* 60 */     return ((Integer)this.handle.getIntegers().read(1)).shortValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setServerPort(short value)
/*    */   {
/* 68 */     this.handle.getIntegers().write(1, Integer.valueOf(value));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public com.comphenix.protocol.PacketType.Protocol getNextProtocol()
/*    */   {
/* 76 */     return (com.comphenix.protocol.PacketType.Protocol)this.handle.getProtocols().read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setNextProtocol(com.comphenix.protocol.PacketType.Protocol value)
/*    */   {
/* 84 */     this.handle.getProtocols().write(0, value);
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperHandshakeClientSetProtocol.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */