/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.PacketType.Play.Server;
/*     */ import com.comphenix.protocol.events.PacketContainer;
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
/*     */ public class WrapperPlayServerMapChunkBulk
/*     */   extends AbstractPacket
/*     */ {
/*  24 */   public static final PacketType TYPE = PacketType.Play.Server.MAP_CHUNK_BULK;
/*     */   
/*     */   public WrapperPlayServerMapChunkBulk() {
/*  27 */     super(new PacketContainer(TYPE), TYPE);
/*  28 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerMapChunkBulk(PacketContainer packet) {
/*  32 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getDataLength()
/*     */   {
/*  40 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDataLength(int value)
/*     */   {
/*  48 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getSkyLightSent()
/*     */   {
/*  58 */     return ((Boolean)this.handle.getSpecificModifier(Boolean.TYPE).read(0)).booleanValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSkyLightSent(boolean value)
/*     */   {
/*  68 */     this.handle.getSpecificModifier(Boolean.TYPE).write(0, Boolean.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int[] getChunksX()
/*     */   {
/*  76 */     return (int[])this.handle.getIntegerArrays().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setChunksX(int[] value)
/*     */   {
/*  84 */     this.handle.getIntegerArrays().write(0, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int[] getChunksY()
/*     */   {
/*  92 */     return (int[])this.handle.getIntegerArrays().read(1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setChunksY(int[] value)
/*     */   {
/* 100 */     this.handle.getIntegerArrays().write(1, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int[] getChunksMask()
/*     */   {
/* 108 */     return (int[])this.handle.getIntegerArrays().read(2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setChunksMask(int[] value)
/*     */   {
/* 116 */     this.handle.getIntegerArrays().write(2, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int[] getChunksExtraMask()
/*     */   {
/* 124 */     return (int[])this.handle.getIntegerArrays().read(3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setChunksExtraMask(int[] value)
/*     */   {
/* 132 */     this.handle.getIntegerArrays().write(3, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[][] getChunksInflatedBuffers()
/*     */   {
/* 140 */     return (byte[][])this.handle.getSpecificModifier(byte[][].class).read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setChunksExtraMask(byte[][] value)
/*     */   {
/* 148 */     this.handle.getSpecificModifier(byte[][].class).write(0, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] getUncompressedData()
/*     */   {
/* 156 */     return (byte[])this.handle.getByteArrays().read(1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setUncompressedData(byte[] value)
/*     */   {
/* 164 */     this.handle.getByteArrays().write(1, value);
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerMapChunkBulk.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */