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
/*    */ public class WrapperPlayClientLook
/*    */   extends WrapperPlayClientFlying
/*    */ {
/* 24 */   public static final PacketType TYPE = PacketType.Play.Client.LOOK;
/*    */   
/*    */   public WrapperPlayClientLook() {
/* 27 */     super(new PacketContainer(TYPE), TYPE);
/* 28 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayClientLook(PacketContainer packet) {
/* 32 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public float getYaw()
/*    */   {
/* 40 */     return ((Float)this.handle.getFloat().read(0)).floatValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setYaw(float value)
/*    */   {
/* 48 */     this.handle.getFloat().write(0, Float.valueOf(value));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public float getPitch()
/*    */   {
/* 56 */     return ((Float)this.handle.getFloat().read(1)).floatValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setPitch(float value)
/*    */   {
/* 64 */     this.handle.getFloat().write(1, Float.valueOf(value));
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayClientLook.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */