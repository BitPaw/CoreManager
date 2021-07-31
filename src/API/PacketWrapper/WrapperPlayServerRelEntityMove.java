/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ 
/*    */ public class WrapperPlayServerRelEntityMove extends WrapperPlayServerEntity
/*    */ {
/*  7 */   public static final com.comphenix.protocol.PacketType TYPE = com.comphenix.protocol.PacketType.Play.Server.REL_ENTITY_MOVE;
/*    */   
/*    */   public WrapperPlayServerRelEntityMove() {
/* 10 */     super(new PacketContainer(TYPE), TYPE);
/* 11 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayServerRelEntityMove(PacketContainer packet) {
/* 15 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public double getDx()
/*    */   {
/* 25 */     return ((Byte)this.handle.getBytes().read(0)).byteValue() / 32.0D;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setDx(double value)
/*    */   {
/* 35 */     if (Math.abs(value) > 4.0D)
/* 36 */       throw new IllegalArgumentException("Displacement cannot exceed 4 meters.");
/* 37 */     this.handle.getBytes().write(0, Byte.valueOf((byte)(int)Math.min(Math.floor(value * 32.0D), 127.0D)));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public double getDy()
/*    */   {
/* 47 */     return ((Byte)this.handle.getBytes().read(1)).byteValue() / 32.0D;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setDy(double value)
/*    */   {
/* 57 */     if (Math.abs(value) > 4.0D)
/* 58 */       throw new IllegalArgumentException("Displacement cannot exceed 4 meters.");
/* 59 */     this.handle.getBytes().write(1, Byte.valueOf((byte)(int)Math.min(Math.floor(value * 32.0D), 127.0D)));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public double getDz()
/*    */   {
/* 69 */     return ((Byte)this.handle.getBytes().read(2)).byteValue() / 32.0D;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setDz(double value)
/*    */   {
/* 79 */     if (Math.abs(value) > 4.0D)
/* 80 */       throw new IllegalArgumentException("Displacement cannot exceed 4 meters.");
/* 81 */     this.handle.getBytes().write(2, Byte.valueOf((byte)(int)Math.min(Math.floor(value * 32.0D), 127.0D)));
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerRelEntityMove.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */