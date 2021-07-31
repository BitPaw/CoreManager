/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.PacketType.Play.Server;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.reflect.StructureModifier;
/*     */ import com.comphenix.protocol.wrappers.nbt.NbtBase;
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
/*     */ public class WrapperPlayServerTileEntityData
/*     */   extends AbstractPacket
/*     */ {
/*  25 */   public static final PacketType TYPE = PacketType.Play.Server.TILE_ENTITY_DATA;
/*     */   
/*     */   public WrapperPlayServerTileEntityData() {
/*  28 */     super(new PacketContainer(TYPE), TYPE);
/*  29 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerTileEntityData(PacketContainer packet) {
/*  33 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getX()
/*     */   {
/*  41 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setX(int value)
/*     */   {
/*  49 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getY()
/*     */   {
/*  57 */     return ((Integer)this.handle.getIntegers().read(1)).shortValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setY(short value)
/*     */   {
/*  65 */     this.handle.getIntegers().write(1, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getZ()
/*     */   {
/*  73 */     return ((Integer)this.handle.getIntegers().read(2)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setZ(int value)
/*     */   {
/*  81 */     this.handle.getIntegers().write(2, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getAction()
/*     */   {
/*  89 */     return ((Integer)this.handle.getIntegers().read(3)).byteValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAction(byte value)
/*     */   {
/*  97 */     this.handle.getIntegers().write(3, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public NbtBase<?> getNbtData()
/*     */   {
/* 105 */     return (NbtBase)this.handle.getNbtModifier().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setNbtData(NbtBase<?> value)
/*     */   {
/* 113 */     this.handle.getNbtModifier().write(0, value);
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerTileEntityData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */