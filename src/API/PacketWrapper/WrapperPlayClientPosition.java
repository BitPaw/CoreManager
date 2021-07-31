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
/*    */ public class WrapperPlayClientPosition
/*    */   extends WrapperPlayClientFlying
/*    */ {
/* 23 */   public static final PacketType TYPE = PacketType.Play.Client.POSITION;
/*    */   
/*    */   public WrapperPlayClientPosition() {
/* 26 */     super(new PacketContainer(TYPE), TYPE);
/* 27 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayClientPosition(PacketContainer packet) {
/* 31 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public double getX()
/*    */   {
/* 39 */     return ((Double)this.handle.getDoubles().read(0)).doubleValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setX(double value)
/*    */   {
/* 47 */     this.handle.getDoubles().write(0, Double.valueOf(value));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public double getY()
/*    */   {
/* 55 */     return ((Double)this.handle.getDoubles().read(1)).doubleValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setY(double value)
/*    */   {
/* 63 */     this.handle.getDoubles().write(1, Double.valueOf(value));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public double getStance()
/*    */   {
/* 71 */     return ((Double)this.handle.getDoubles().read(3)).doubleValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setStance(double value)
/*    */   {
/* 79 */     this.handle.getDoubles().write(3, Double.valueOf(value));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public double getZ()
/*    */   {
/* 87 */     return ((Double)this.handle.getDoubles().read(2)).doubleValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setZ(double value)
/*    */   {
/* 95 */     this.handle.getDoubles().write(2, Double.valueOf(value));
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayClientPosition.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */