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
/*     */ public class WrapperPlayServerMapChunk
/*     */   extends AbstractPacket
/*     */ {
/*  24 */   public static final PacketType TYPE = PacketType.Play.Server.MAP_CHUNK;
/*     */   
/*     */   public WrapperPlayServerMapChunk() {
/*  27 */     super(new PacketContainer(TYPE), TYPE);
/*  28 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerMapChunk(PacketContainer packet) {
/*  32 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getChunkX()
/*     */   {
/*  40 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setChunkX(int value)
/*     */   {
/*  48 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getChunkZ()
/*     */   {
/*  56 */     return ((Integer)this.handle.getIntegers().read(1)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setChunkZ(int value)
/*     */   {
/*  64 */     this.handle.getIntegers().write(1, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getGroundUpContinuous()
/*     */   {
/*  74 */     return ((Boolean)this.handle.getSpecificModifier(Boolean.TYPE).read(0)).booleanValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setGroundUpContinuous(boolean value)
/*     */   {
/*  84 */     this.handle.getSpecificModifier(Boolean.TYPE).write(0, Boolean.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getPrimaryBitMap()
/*     */   {
/*  92 */     return ((Integer)this.handle.getIntegers().read(2)).shortValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPrimaryBitMap(short value)
/*     */   {
/* 100 */     this.handle.getIntegers().write(2, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getAddBitMap()
/*     */   {
/* 108 */     return ((Integer)this.handle.getIntegers().read(3)).shortValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAddBitMap(short value)
/*     */   {
/* 116 */     this.handle.getIntegers().write(3, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getCompressedSize()
/*     */   {
/* 124 */     return ((Integer)this.handle.getIntegers().read(4)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCompressedSize(int value)
/*     */   {
/* 132 */     this.handle.getIntegers().write(4, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] getCompressedData()
/*     */   {
/* 142 */     return (byte[])this.handle.getByteArrays().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCompressedData(byte[] value)
/*     */   {
/* 152 */     this.handle.getByteArrays().write(0, (byte[])value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] getUncompressedData()
/*     */   {
/* 162 */     return (byte[])this.handle.getByteArrays().read(1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setUncompressedData(byte[] value)
/*     */   {
/* 172 */     this.handle.getByteArrays().write(1, (byte[])value);
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerMapChunk.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */