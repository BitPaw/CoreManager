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
/*    */ public class WrapperPlayServerUpdateTime
/*    */   extends AbstractPacket
/*    */ {
/* 24 */   public static final PacketType TYPE = PacketType.Play.Server.UPDATE_TIME;
/*    */   
/*    */   public WrapperPlayServerUpdateTime() {
/* 27 */     super(new PacketContainer(TYPE), TYPE);
/* 28 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayServerUpdateTime(PacketContainer packet) {
/* 32 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public long getAgeOfTheWorld()
/*    */   {
/* 42 */     return ((Long)this.handle.getLongs().read(0)).longValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setAgeOfTheWorld(long value)
/*    */   {
/* 52 */     this.handle.getLongs().write(0, Long.valueOf(value));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public long getTimeOfDay()
/*    */   {
/* 60 */     return ((Long)this.handle.getLongs().read(1)).longValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setTimeOfDay(long value)
/*    */   {
/* 68 */     this.handle.getLongs().write(1, Long.valueOf(value));
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerUpdateTime.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */