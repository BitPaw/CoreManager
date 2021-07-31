/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.PacketType.Play.Client;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.reflect.IntEnum;
/*     */ import com.comphenix.protocol.reflect.StructureModifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WrapperPlayClientBlockDig
/*     */   extends AbstractPacket
/*     */ {
/*  25 */   public static final PacketType TYPE = PacketType.Play.Client.BLOCK_DIG;
/*     */   
/*     */   public static enum Status {
/*  28 */     STARTED_DIGGING, 
/*  29 */     CANCELLED_DIGGING, 
/*  30 */     FINISHED_DIGGING, 
/*  31 */     DROP_ITEM_STACK, 
/*  32 */     DROP_ITEM, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  37 */     SHOOT_ARROW;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private Status() {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class BlockSide
/*     */     extends IntEnum
/*     */   {
/*     */     public static final int BOTTOM = 0;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public static final int TOP = 1;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public static final int BEHIND = 2;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public static final int FRONT = 3;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public static final int LEFT = 4;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public static final int RIGHT = 5;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  86 */     private static BlockSide INSTANCE = new BlockSide();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public static BlockSide getInstance()
/*     */     {
/*  93 */       return INSTANCE;
/*     */     }
/*     */     
/*  96 */     private static final int[] xOffset = { 0, 0, 0, 0, -1, 1 };
/*  97 */     private static final int[] yOffset = { -1, 1, 0, 0, 0, 0 };
/*  98 */     private static final int[] zOffset = { 0, 0, -1, 1, 0, 0 };
/*     */     
/*     */     public static int getXOffset(int blockFace) {
/* 101 */       return xOffset[blockFace];
/*     */     }
/*     */     
/*     */     public static int getYOffset(int blockFace) {
/* 105 */       return yOffset[blockFace];
/*     */     }
/*     */     
/*     */     public static int getZOffset(int blockFace) {
/* 109 */       return zOffset[blockFace];
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WrapperPlayClientBlockDig()
/*     */   {
/* 119 */     super(new PacketContainer(TYPE), TYPE);
/* 120 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayClientBlockDig(PacketContainer packet) {
/* 124 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Status getStatus()
/*     */   {
/* 132 */     return Status.values()[((Integer)this.handle.getIntegers().read(4)).intValue()];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setStatus(Status value)
/*     */   {
/* 140 */     this.handle.getIntegers().write(4, Integer.valueOf(value.ordinal()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getX()
/*     */   {
/* 148 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setX(int value)
/*     */   {
/* 156 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getY()
/*     */   {
/* 164 */     return ((Integer)this.handle.getIntegers().read(1)).byteValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setY(byte value)
/*     */   {
/* 172 */     this.handle.getIntegers().write(1, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getZ()
/*     */   {
/* 180 */     return ((Integer)this.handle.getIntegers().read(2)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setZ(int value)
/*     */   {
/* 188 */     this.handle.getIntegers().write(2, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getFace()
/*     */   {
/* 196 */     return ((Integer)this.handle.getIntegers().read(3)).byteValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFace(int value)
/*     */   {
/* 204 */     this.handle.getIntegers().write(3, Integer.valueOf(value));
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayClientBlockDig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */