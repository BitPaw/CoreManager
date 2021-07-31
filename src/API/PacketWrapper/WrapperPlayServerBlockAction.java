/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.PacketType.Play.Server;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.reflect.IntEnum;
/*     */ import com.comphenix.protocol.reflect.StructureModifier;
/*     */ import org.bukkit.Instrument;
/*     */ import org.bukkit.Material;
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
/*     */ public class WrapperPlayServerBlockAction
/*     */   extends AbstractPacket
/*     */ {
/*  28 */   public static final PacketType TYPE = PacketType.Play.Server.BLOCK_ACTION;
/*     */   
/*     */   public WrapperPlayServerBlockAction() {
/*  31 */     super(new PacketContainer(TYPE), TYPE);
/*  32 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerBlockAction(PacketContainer packet) {
/*  36 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */   public static class BlockFaceDirection
/*     */     extends IntEnum
/*     */   {
/*     */     public static final int DOWN = 0;
/*     */     
/*     */     public static final int UP = 1;
/*     */     public static final int SOUTH = 2;
/*     */     public static final int WEST = 3;
/*     */     public static final int NORTH = 4;
/*     */     public static final int EAST = 5;
/*  50 */     private static BlockFaceDirection INSTANCE = new BlockFaceDirection();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public static BlockFaceDirection getInstance()
/*     */     {
/*  57 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getX()
/*     */   {
/*  66 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setX(int value)
/*     */   {
/*  74 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getY()
/*     */   {
/*  82 */     return ((Integer)this.handle.getIntegers().read(1)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setY(short value)
/*     */   {
/*  90 */     this.handle.getIntegers().write(1, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getZ()
/*     */   {
/*  98 */     return ((Integer)this.handle.getIntegers().read(2)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setZ(int value)
/*     */   {
/* 106 */     this.handle.getIntegers().write(2, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getBlockId()
/*     */   {
/* 114 */     return (short)getBlockType().getId();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBlockId(short value)
/*     */   {
/* 122 */     setBlockType(Material.getMaterial(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Material getBlockType()
/*     */   {
/* 130 */     return (Material)this.handle.getBlocks().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBlockType(Material value)
/*     */   {
/* 138 */     this.handle.getBlocks().write(0, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getByte1()
/*     */   {
/* 146 */     return ((Integer)this.handle.getIntegers().read(3)).byteValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setByte1(byte value)
/*     */   {
/* 154 */     this.handle.getIntegers().write(3, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getByte2()
/*     */   {
/* 162 */     return ((Integer)this.handle.getIntegers().read(4)).byteValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setByte2(byte value)
/*     */   {
/* 170 */     this.handle.getIntegers().write(4, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */   public NoteBlockData getNoteBlockData() {
/* 174 */     return new NoteBlockData();
/*     */   }
/*     */   
/*     */   public PistionData getPistonData() {
/* 178 */     return new PistionData();
/*     */   }
/*     */   
/*     */   public ChestData getChestData() {
/* 182 */     return new ChestData();
/*     */   }
/*     */   
/*     */   public class NoteBlockData
/*     */   {
/*     */     public NoteBlockData() {}
/*     */     
/*     */     public Instrument getInstrument()
/*     */     {
/* 191 */       return Instrument.getByType(WrapperPlayServerBlockAction.this.getByte1());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public void setInstrument(Instrument value)
/*     */     {
/* 199 */       WrapperPlayServerBlockAction.this.setByte1(value.getType());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public byte getPitch()
/*     */     {
/* 209 */       return WrapperPlayServerBlockAction.this.getByte2();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void setPitch(byte value)
/*     */     {
/* 219 */       WrapperPlayServerBlockAction.this.setByte2(value);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public class PistionData
/*     */   {
/*     */     public PistionData() {}
/*     */     
/*     */ 
/*     */     public byte getState()
/*     */     {
/* 231 */       return WrapperPlayServerBlockAction.this.getByte1();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void setState(byte value)
/*     */     {
/* 241 */       WrapperPlayServerBlockAction.this.setByte1(value);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public int getDirection()
/*     */     {
/* 251 */       return WrapperPlayServerBlockAction.this.getByte2();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 261 */     public void setDirection(int value) { WrapperPlayServerBlockAction.this.setByte2((byte)value); }
/*     */   }
/*     */   
/*     */   public class ChestData {
/*     */     public ChestData() {}
/*     */     
/* 267 */     public boolean isOpen() { return WrapperPlayServerBlockAction.this.getByte2() != 0; }
/*     */     
/*     */     public void setOpen(boolean open)
/*     */     {
/* 271 */       WrapperPlayServerBlockAction.this.setByte2((byte)(open ? 1 : 0));
/*     */     }
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerBlockAction.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */