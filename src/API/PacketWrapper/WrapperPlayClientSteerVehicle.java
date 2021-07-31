/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ 
/*    */ public class WrapperPlayClientSteerVehicle extends AbstractPacket
/*    */ {
/*  7 */   public static final com.comphenix.protocol.PacketType TYPE = com.comphenix.protocol.PacketType.Play.Client.STEER_VEHICLE;
/*    */   
/*    */   public WrapperPlayClientSteerVehicle() {
/* 10 */     super(new PacketContainer(TYPE), TYPE);
/* 11 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayClientSteerVehicle(PacketContainer packet) {
/* 15 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public float getSideways()
/*    */   {
/* 23 */     return ((Float)this.handle.getFloat().read(0)).floatValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setSideways(float value)
/*    */   {
/* 31 */     this.handle.getFloat().write(0, Float.valueOf(value));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public float getForward()
/*    */   {
/* 39 */     return ((Float)this.handle.getFloat().read(1)).floatValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setForward(float value)
/*    */   {
/* 47 */     this.handle.getFloat().write(1, Float.valueOf(value));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean getJump()
/*    */   {
/* 55 */     return ((Boolean)this.handle.getSpecificModifier(Boolean.TYPE).read(0)).booleanValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setJump(boolean value)
/*    */   {
/* 63 */     this.handle.getSpecificModifier(Boolean.TYPE).write(0, Boolean.valueOf(value));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean getUnmount()
/*    */   {
/* 71 */     return ((Boolean)this.handle.getSpecificModifier(Boolean.TYPE).read(1)).booleanValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setUnmount(boolean value)
/*    */   {
/* 79 */     this.handle.getSpecificModifier(Boolean.TYPE).write(1, Boolean.valueOf(value));
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayClientSteerVehicle.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */