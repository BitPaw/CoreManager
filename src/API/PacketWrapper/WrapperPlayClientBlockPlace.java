/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.PacketType.Play.Client;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.reflect.StructureModifier;
/*     */ import org.bukkit.inventory.ItemStack;
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
/*     */ public class WrapperPlayClientBlockPlace
/*     */   extends AbstractPacket
/*     */ {
/*  26 */   public static final PacketType TYPE = PacketType.Play.Client.BLOCK_PLACE;
/*     */   
/*     */   public WrapperPlayClientBlockPlace() {
/*  29 */     super(new PacketContainer(TYPE), TYPE);
/*  30 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayClientBlockPlace(PacketContainer packet) {
/*  34 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getX()
/*     */   {
/*  42 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setX(int value)
/*     */   {
/*  50 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getY()
/*     */   {
/*  58 */     return ((Integer)this.handle.getIntegers().read(1)).byteValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setY(byte value)
/*     */   {
/*  66 */     this.handle.getIntegers().write(1, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getZ()
/*     */   {
/*  74 */     return ((Integer)this.handle.getIntegers().read(2)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setZ(int value)
/*     */   {
/*  82 */     this.handle.getIntegers().write(2, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getDirection()
/*     */   {
/*  90 */     return ((Integer)this.handle.getIntegers().read(3)).byteValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDirection(byte value)
/*     */   {
/*  98 */     this.handle.getIntegers().write(3, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack getHeldItem()
/*     */   {
/* 106 */     return (ItemStack)this.handle.getItemModifier().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setHeldItem(ItemStack value)
/*     */   {
/* 114 */     this.handle.getItemModifier().write(0, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getCursorPositionX()
/*     */   {
/* 122 */     return ((Float)this.handle.getFloat().read(0)).floatValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCursorPositionX(float value)
/*     */   {
/* 130 */     this.handle.getFloat().write(0, Float.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getCursorPositionY()
/*     */   {
/* 138 */     return ((Float)this.handle.getFloat().read(1)).floatValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCursorPositionY(float value)
/*     */   {
/* 146 */     this.handle.getFloat().write(1, Float.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getCursorPositionZ()
/*     */   {
/* 154 */     return ((Float)this.handle.getFloat().read(2)).byteValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCursorPositionZ(byte value)
/*     */   {
/* 162 */     this.handle.getFloat().write(2, Float.valueOf(value));
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayClientBlockPlace.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */