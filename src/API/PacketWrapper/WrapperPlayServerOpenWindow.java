/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.PacketType.Play.Server;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.reflect.StructureModifier;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.bukkit.event.inventory.InventoryType;
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
/*     */ public class WrapperPlayServerOpenWindow
/*     */   extends AbstractPacket
/*     */ {
/*  29 */   public static final PacketType TYPE = PacketType.Play.Server.OPEN_WINDOW;
/*     */   
/*     */ 
/*  32 */   private static List<InventoryType> inventoryByID = Arrays.asList(new InventoryType[] { InventoryType.CHEST, InventoryType.WORKBENCH, InventoryType.FURNACE, InventoryType.DISPENSER, InventoryType.ENCHANTING, InventoryType.BREWING, InventoryType.MERCHANT, InventoryType.BEACON, InventoryType.ANVIL });
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
/*     */   public WrapperPlayServerOpenWindow()
/*     */   {
/*  45 */     super(new PacketContainer(TYPE), TYPE);
/*  46 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerOpenWindow(PacketContainer packet) {
/*  50 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getWindowId()
/*     */   {
/*  60 */     return ((Integer)this.handle.getIntegers().read(0)).byteValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setWindowId(byte value)
/*     */   {
/*  68 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public InventoryType getInventoryType()
/*     */   {
/*  76 */     int id = ((Integer)this.handle.getIntegers().read(1)).intValue();
/*     */     
/*  78 */     if ((id >= 0) && (id <= inventoryByID.size())) {
/*  79 */       return (InventoryType)inventoryByID.get(id);
/*     */     }
/*  81 */     throw new IllegalArgumentException("Cannot find inventory type " + id);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setInventoryType(InventoryType value)
/*     */   {
/*  89 */     int id = inventoryByID.indexOf(value);
/*     */     
/*  91 */     if (id > 0) {
/*  92 */       this.handle.getIntegers().write(1, Integer.valueOf(id));
/*     */     } else {
/*  94 */       throw new IllegalArgumentException("Cannot find the ID of " + value);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getWindowTitle()
/*     */   {
/* 102 */     return (String)this.handle.getStrings().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setWindowTitle(String value)
/*     */   {
/* 110 */     this.handle.getStrings().write(0, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getNumberOfSlots()
/*     */   {
/* 120 */     return ((Integer)this.handle.getIntegers().read(2)).byteValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setNumberOfSlots(byte value)
/*     */   {
/* 130 */     this.handle.getIntegers().write(2, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTitleExact(boolean value)
/*     */   {
/* 140 */     this.handle.getSpecificModifier(Boolean.TYPE).write(0, Boolean.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isTitleExact()
/*     */   {
/* 150 */     return ((Boolean)this.handle.getSpecificModifier(Boolean.TYPE).read(0)).booleanValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getEntityId()
/*     */   {
/* 160 */     return ((Integer)this.handle.getIntegers().read(3)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEntityId(int value)
/*     */   {
/* 170 */     this.handle.getIntegers().write(3, Integer.valueOf(value));
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerOpenWindow.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */