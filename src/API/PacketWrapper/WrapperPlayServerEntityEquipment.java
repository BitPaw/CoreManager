/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.PacketType.Play.Server;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.events.PacketEvent;
/*     */ import com.comphenix.protocol.reflect.StructureModifier;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
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
/*     */ public class WrapperPlayServerEntityEquipment
/*     */   extends AbstractPacket
/*     */ {
/*  29 */   public static final PacketType TYPE = PacketType.Play.Server.ENTITY_EQUIPMENT;
/*     */   
/*     */   public WrapperPlayServerEntityEquipment() {
/*  32 */     super(new PacketContainer(TYPE), TYPE);
/*  33 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerEntityEquipment(PacketContainer packet) {
/*  37 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getEntityId()
/*     */   {
/*  45 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getEntity(World world)
/*     */   {
/*  54 */     return (Entity)this.handle.getEntityModifier(world).read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getEntity(PacketEvent event)
/*     */   {
/*  63 */     return getEntity(event.getPlayer().getWorld());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEntityId(int value)
/*     */   {
/*  71 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getSlot()
/*     */   {
/*  82 */     return ((Integer)this.handle.getIntegers().read(1)).shortValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSlot(short value)
/*     */   {
/*  93 */     this.handle.getIntegers().write(1, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack getItem()
/*     */   {
/* 101 */     return (ItemStack)this.handle.getItemModifier().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setItem(ItemStack value)
/*     */   {
/* 109 */     this.handle.getItemModifier().write(0, value);
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerEntityEquipment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */