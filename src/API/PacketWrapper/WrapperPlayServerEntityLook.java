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
/*    */ public class WrapperPlayServerEntityLook
/*    */   extends WrapperPlayServerEntity
/*    */ {
/* 24 */   public static final PacketType TYPE = PacketType.Play.Server.ENTITY_LOOK;
/*    */   
/*    */   public WrapperPlayServerEntityLook() {
/* 27 */     super(new PacketContainer(TYPE), TYPE);
/* 28 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayServerEntityLook(PacketContainer packet) {
/* 32 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public float getYaw()
/*    */   {
/* 40 */     return ((Byte)this.handle.getBytes().read(3)).byteValue() * 360.0F / 256.0F;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setYaw(float value)
/*    */   {
/* 48 */     this.handle.getBytes().write(3, Byte.valueOf((byte)(int)(value * 256.0F / 360.0F)));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public float getPitch()
/*    */   {
/* 56 */     return ((Byte)this.handle.getBytes().read(4)).byteValue() * 360.0F / 256.0F;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setPitch(float value)
/*    */   {
/* 64 */     this.handle.getBytes().write(4, Byte.valueOf((byte)(int)(value * 256.0F / 360.0F)));
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerEntityLook.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */