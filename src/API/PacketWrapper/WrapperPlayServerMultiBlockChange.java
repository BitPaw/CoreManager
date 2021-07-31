/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.PacketType.Play.Server;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.reflect.StructureModifier;
/*     */ import com.comphenix.protocol.wrappers.ChunkCoordIntPair;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WrapperPlayServerMultiBlockChange
/*     */   extends AbstractPacket
/*     */ {
/*  32 */   public static final PacketType TYPE = PacketType.Play.Server.MULTI_BLOCK_CHANGE;
/*     */   
/*     */   public WrapperPlayServerMultiBlockChange() {
/*  35 */     super(new PacketContainer(TYPE), TYPE);
/*  36 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerMultiBlockChange(PacketContainer packet) {
/*  40 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getChunkX()
/*     */   {
/*  48 */     return getChunk().getChunkX();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setChunkX(int index)
/*     */   {
/*  56 */     setChunk(new ChunkCoordIntPair(index, getChunkZ()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getChunkZ()
/*     */   {
/*  64 */     return getChunk().getChunkZ();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setChunkZ(int index)
/*     */   {
/*  72 */     setChunk(new ChunkCoordIntPair(getChunkX(), index));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChunkCoordIntPair getChunk()
/*     */   {
/*  80 */     return (ChunkCoordIntPair)this.handle.getChunkCoordIntPairs().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setChunk(ChunkCoordIntPair value)
/*     */   {
/*  88 */     this.handle.getChunkCoordIntPairs().write(0, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getRecordCount()
/*     */   {
/*  96 */     return ((Integer)this.handle.getIntegers().read(0)).shortValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRecordCount(short value)
/*     */   {
/* 104 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
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
/*     */   public byte[] getRecordData()
/*     */   {
/* 147 */     return (byte[])this.handle.getByteArrays().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRecordData(byte[] value)
/*     */   {
/* 157 */     setRecordCount((short)value.length);
/* 158 */     this.handle.getByteArrays().write(0, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRecordData(BlockChangeArray array)
/*     */   {
/* 166 */     setRecordData(array.toByteArray());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public BlockChangeArray getRecordDataArray()
/*     */   {
/* 174 */     return new BlockChangeArray(getRecordData());
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerMultiBlockChange.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */