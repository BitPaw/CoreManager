/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.reflect.StructureModifier;
/*     */ 
/*     */ public class ChunkPacketProcessor { protected static final int BYTES_PER_NIBBLE_PART = 2048;
/*     */   protected static final int CHUNK_SEGMENTS = 16;
/*     */   protected static final int NIBBLES_REQUIRED = 4;
/*     */   public static final int BLOCK_ID_LENGHT = 4096;
/*     */   public static final int DATA_LENGHT = 2048;
/*     */   public static final int BIOME_ARRAY_LENGTH = 256;
/*     */   private int chunkX;
/*     */   private int chunkZ;
/*     */   private int chunkMask;
/*     */   private int extraMask;
/*     */   private int chunkSectionNumber;
/*     */   private int extraSectionNumber;
/*     */   
/*     */   public static abstract interface ChunkletProcessor { public abstract void processChunklet(org.bukkit.Location paramLocation, byte[] paramArrayOfByte, ChunkPacketProcessor.ChunkOffsets paramChunkOffsets);
/*     */     
/*     */     public abstract void processBiomeArray(org.bukkit.Location paramLocation, byte[] paramArrayOfByte, int paramInt);
/*     */   }
/*     */   
/*     */   public static class ChunkOffsets { private int blockIdOffset;
/*     */     private int dataOffset;
/*     */     private int lightOffset;
/*     */     private int skylightOffset;
/*     */     private int extraOffset;
/*     */     
/*  29 */     private ChunkOffsets(int blockIdOffset, int dataOffset, int lightOffset, int skylightOffset, int extraOffset) { this.blockIdOffset = blockIdOffset;
/*  30 */       this.dataOffset = dataOffset;
/*  31 */       this.lightOffset = lightOffset;
/*  32 */       this.skylightOffset = skylightOffset;
/*  33 */       this.extraOffset = extraOffset;
/*     */     }
/*     */     
/*     */     private void incrementIdIndex() {
/*  37 */       this.blockIdOffset += 4096;
/*  38 */       this.dataOffset += 2048;
/*  39 */       this.dataOffset += 2048;
/*     */       
/*  41 */       if (this.skylightOffset >= 0) {
/*  42 */         this.skylightOffset += 2048;
/*     */       }
/*     */     }
/*     */     
/*     */     private void incrementExtraIndex() {
/*  47 */       if (this.extraOffset >= 0) {
/*  48 */         this.extraOffset += 2048;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public int getBlockIdOffset()
/*     */     {
/*  59 */       return this.blockIdOffset;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public int getDataOffset()
/*     */     {
/*  69 */       return this.dataOffset;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public int getLightOffset()
/*     */     {
/*  79 */       return this.lightOffset;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public int getSkylightOffset()
/*     */     {
/*  90 */       return this.skylightOffset;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean hasSkylightOffset()
/*     */     {
/*  98 */       return this.skylightOffset >= 0;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public int getExtraOffset()
/*     */     {
/* 108 */       return this.extraOffset;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean hasExtraOffset()
/*     */     {
/* 116 */       return this.extraOffset > 0;
/*     */     }
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
/*     */ 
/* 159 */   private boolean hasContinous = true;
/*     */   
/*     */ 
/*     */   private int startIndex;
/*     */   
/*     */ 
/*     */   private int size;
/*     */   
/*     */ 
/*     */   private byte[] data;
/*     */   
/*     */ 
/*     */   private org.bukkit.World world;
/*     */   
/*     */ 
/*     */ 
/*     */   public static ChunkPacketProcessor fromMapPacket(com.comphenix.protocol.events.PacketContainer packet, org.bukkit.World world)
/*     */   {
/* 177 */     if (!packet.getType().equals(com.comphenix.protocol.PacketType.Play.Server.MAP_CHUNK)) {
/* 178 */       throw new IllegalArgumentException(packet + " must be a MAP_CHUNK packet.");
/*     */     }
/* 180 */     StructureModifier<Integer> ints = packet.getIntegers();
/* 181 */     StructureModifier<byte[]> byteArray = packet.getByteArrays();
/*     */     
/*     */ 
/* 184 */     ChunkPacketProcessor processor = new ChunkPacketProcessor();
/* 185 */     processor.world = world;
/* 186 */     processor.chunkX = ((Integer)ints.read(0)).intValue();
/* 187 */     processor.chunkZ = ((Integer)ints.read(1)).intValue();
/* 188 */     processor.chunkMask = ((Integer)ints.read(2)).intValue();
/* 189 */     processor.extraMask = ((Integer)ints.read(3)).intValue();
/* 190 */     processor.data = ((byte[])byteArray.read(1));
/* 191 */     processor.startIndex = 0;
/*     */     
/* 193 */     if (packet.getBooleans().size() > 0) {
/* 194 */       processor.hasContinous = ((Boolean)packet.getBooleans().read(0)).booleanValue();
/*     */     }
/* 196 */     return processor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ChunkPacketProcessor[] fromMapBulkPacket(com.comphenix.protocol.events.PacketContainer packet, org.bukkit.World world)
/*     */   {
/* 205 */     if (!packet.getType().equals(com.comphenix.protocol.PacketType.Play.Server.MAP_CHUNK_BULK)) {
/* 206 */       throw new IllegalArgumentException(packet + " must be a MAP_CHUNK_BULK packet.");
/*     */     }
/* 208 */     StructureModifier<int[]> intArrays = packet.getIntegerArrays();
/* 209 */     StructureModifier<byte[]> byteArrays = packet.getByteArrays();
/*     */     
/* 211 */     int[] x = (int[])intArrays.read(0);
/* 212 */     int[] z = (int[])intArrays.read(1);
/*     */     
/* 214 */     ChunkPacketProcessor[] processors = new ChunkPacketProcessor[x.length];
/*     */     
/* 216 */     int[] chunkMask = (int[])intArrays.read(2);
/* 217 */     int[] extraMask = (int[])intArrays.read(3);
/* 218 */     int dataStartIndex = 0;
/*     */     
/* 220 */     for (int chunkNum = 0; chunkNum < processors.length; chunkNum++)
/*     */     {
/* 222 */       ChunkPacketProcessor processor = new ChunkPacketProcessor();
/* 223 */       processors[chunkNum] = processor;
/* 224 */       processor.world = world;
/* 225 */       processor.chunkX = x[chunkNum];
/* 226 */       processor.chunkZ = z[chunkNum];
/* 227 */       processor.chunkMask = chunkMask[chunkNum];
/* 228 */       processor.extraMask = extraMask[chunkNum];
/* 229 */       processor.hasContinous = true;
/* 230 */       processor.data = ((byte[])byteArrays.read(1));
/*     */       
/*     */ 
/* 233 */       if ((processor.data == null) || (processor.data.length == 0)) {
/* 234 */         processor.data = ((byte[][])packet.getSpecificModifier(byte[][].class).read(0))[chunkNum];
/*     */       } else {
/* 236 */         processor.startIndex = dataStartIndex;
/*     */       }
/* 238 */       dataStartIndex += processor.size;
/*     */     }
/* 240 */     return processors;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void process(ChunkletProcessor processor)
/*     */   {
/* 249 */     for (int i = 0; i < 16; i++) {
/* 250 */       if ((this.chunkMask & 1 << i) > 0) {
/* 251 */         this.chunkSectionNumber += 1;
/*     */       }
/* 253 */       if ((this.extraMask & 1 << i) > 0) {
/* 254 */         this.extraSectionNumber += 1;
/*     */       }
/*     */     }
/*     */     
/* 258 */     int skylightCount = getSkylightCount();
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
/* 274 */     this.size = (2048 * ((4 + skylightCount) * this.chunkSectionNumber + this.extraSectionNumber) + (this.hasContinous ? 256 : 0));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 279 */     if (getOffset(2) - this.startIndex > this.data.length) {
/* 280 */       return;
/*     */     }
/*     */     
/*     */ 
/* 284 */     if (isChunkLoaded(this.world, this.chunkX, this.chunkZ)) {
/* 285 */       translate(processor);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int getSkylightCount()
/*     */   {
/* 298 */     return this.world.getEnvironment() == org.bukkit.World.Environment.NORMAL ? 1 : 0;
/*     */   }
/*     */   
/*     */   private int getOffset(int nibbles) {
/* 302 */     return this.startIndex + nibbles * this.chunkSectionNumber * 2048;
/*     */   }
/*     */   
/*     */   private void translate(ChunkletProcessor processor)
/*     */   {
/* 307 */     int current = 4;
/* 308 */     ChunkOffsets offsets = new ChunkOffsets(getOffset(0), getOffset(2), getOffset(3), getSkylightCount() > 0 ? getOffset(current++) : -1, this.extraSectionNumber > 0 ? getOffset(current++) : -1, null);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 316 */     for (int i = 0; i < 16; i++)
/*     */     {
/* 318 */       if ((this.chunkMask & 1 << i) > 0)
/*     */       {
/* 320 */         org.bukkit.Location origin = new org.bukkit.Location(this.world, this.chunkX << 4, i * 16, this.chunkZ << 4);
/*     */         
/* 322 */         processor.processChunklet(origin, this.data, offsets);
/* 323 */         offsets.incrementIdIndex();
/*     */       }
/* 325 */       if ((this.extraMask & 1 << i) > 0) {
/* 326 */         offsets.incrementExtraIndex();
/*     */       }
/*     */     }
/*     */     
/* 330 */     if (this.hasContinous) {
/* 331 */       processor.processBiomeArray(new org.bukkit.Location(this.world, this.chunkX << 4, 0.0D, this.chunkZ << 4), this.data, this.startIndex + this.size - 256);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isChunkLoaded(org.bukkit.World world, int x, int z)
/*     */   {
/* 337 */     return world.isChunkLoaded(x, z);
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\ChunkPacketProcessor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */