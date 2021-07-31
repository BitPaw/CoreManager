/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
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
/*     */ public class BlockChangeArray
/*     */ {
/*     */   private static final int RECORD_SIZE = 4;
/*     */   private int[] data;
/*     */   
/*     */   public class BlockChange
/*     */   {
/*     */     private final int index;
/*     */     
/*     */     private BlockChange(int index)
/*     */     {
/*  29 */       this.index = index;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public BlockChange setLocation(Location loc)
/*     */     {
/*  41 */       setRelativeX(loc.getBlockX() & 0xF);
/*  42 */       setRelativeZ(loc.getBlockZ() & 0xF);
/*  43 */       setAbsoluteY(loc.getBlockY());
/*  44 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Location getLocation(World world, int chunkX, int chunkZ)
/*     */     {
/*  57 */       if (world == null)
/*  58 */         throw new IllegalArgumentException("World cannot be NULL.");
/*  59 */       return new Location(world, (chunkX << 4) + getRelativeX(), getAbsoluteY(), (chunkZ << 4) + getRelativeZ());
/*     */     }
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
/*     */     public BlockChange setRelativeX(int relativeX)
/*     */     {
/*  73 */       setValue(relativeX, 28, -268435456);
/*  74 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public int getRelativeX()
/*     */     {
/*  82 */       return getValue(28, -268435456);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public BlockChange setRelativeZ(int relativeX)
/*     */     {
/*  91 */       setValue(relativeX, 24, 251658240);
/*  92 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public byte getRelativeZ()
/*     */     {
/* 100 */       return (byte)getValue(24, 251658240);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public BlockChange setAbsoluteY(int absoluteY)
/*     */     {
/* 109 */       setValue(absoluteY, 16, 16711680);
/* 110 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public int getAbsoluteY()
/*     */     {
/* 118 */       return getValue(16, 16711680);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public BlockChange setBlockID(int blockID)
/*     */     {
/* 127 */       setValue(blockID, 4, 65520);
/* 128 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public int getBlockID()
/*     */     {
/* 136 */       return getValue(4, 65520);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public BlockChange setMetadata(int metadata)
/*     */     {
/* 145 */       setValue(metadata, 0, 15);
/* 146 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public int getMetadata()
/*     */     {
/* 154 */       return getValue(0, 15);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public int getIndex()
/*     */     {
/* 162 */       return this.index;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private int asInteger()
/*     */     {
/* 170 */       return BlockChangeArray.this.data[this.index];
/*     */     }
/*     */     
/*     */     private void setValue(int value, int leftShift, int updateMask)
/*     */     {
/* 175 */       BlockChangeArray.this.data[this.index] = (value << leftShift & updateMask | BlockChangeArray.this.data[this.index] & (updateMask ^ 0xFFFFFFFF));
/*     */     }
/*     */     
/*     */     private int getValue(int rightShift, int updateMask) {
/* 179 */       return (BlockChangeArray.this.data[this.index] & updateMask) >> rightShift;
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
/*     */   public BlockChangeArray(int blockChanges)
/*     */   {
/* 198 */     this.data = new int[blockChanges];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public BlockChangeArray(byte[] input)
/*     */   {
/* 206 */     if (input.length % 4 != 0) {
/* 207 */       throw new IllegalArgumentException("The lenght of the input data array should be a multiple of 4.");
/*     */     }
/* 209 */     IntBuffer source = ByteBuffer.wrap(input).asIntBuffer();
/* 210 */     IntBuffer destination = IntBuffer.allocate(input.length / 4);
/* 211 */     destination.put(source);
/*     */     
/*     */ 
/* 214 */     this.data = destination.array();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BlockChange getBlockChange(int index)
/*     */   {
/* 225 */     if ((index < 0) || (index >= getSize()))
/* 226 */       throw new IllegalArgumentException("Index is out of bounds.");
/* 227 */     return new BlockChange(index, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBlockChange(int index, BlockChange change)
/*     */   {
/* 236 */     if (change == null)
/* 237 */       throw new IllegalArgumentException("Block change cannot be NULL.");
/* 238 */     this.data[index] = change.asInteger();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getSize()
/*     */   {
/* 246 */     return this.data.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] toByteArray()
/*     */   {
/* 254 */     ByteBuffer copy = ByteBuffer.allocate(this.data.length * 4);
/*     */     
/*     */ 
/* 257 */     copy.asIntBuffer().put(this.data);
/* 258 */     return copy.array();
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\BlockChangeArray.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */