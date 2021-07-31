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
/*     */ public class WrapperPlayServerAttachEntity
/*     */   extends AbstractPacket
/*     */ {
/*  28 */   public static final PacketType TYPE = PacketType.Play.Server.ATTACH_ENTITY;
/*     */   
/*     */   public WrapperPlayServerAttachEntity() {
/*  31 */     super(new PacketContainer(TYPE), TYPE);
/*  32 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerAttachEntity(PacketContainer packet) {
/*  36 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getLeached()
/*     */   {
/*  44 */     return ((Integer)this.handle.getIntegers().read(0)).intValue() != 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLeached(boolean value)
/*     */   {
/*  52 */     this.handle.getIntegers().write(0, Integer.valueOf(value ? 1 : 0));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getEntityId()
/*     */   {
/*  60 */     return ((Integer)this.handle.getIntegers().read(1)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEntityId(int value)
/*     */   {
/*  68 */     this.handle.getIntegers().write(1, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getEntity(World world)
/*     */   {
/*  77 */     return (Entity)this.handle.getEntityModifier(world).read(1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getEntity(PacketEvent event)
/*     */   {
/*  86 */     return getEntity(event.getPlayer().getWorld());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getVehicleId()
/*     */   {
/*  94 */     return ((Integer)this.handle.getIntegers().read(2)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setVehicleId(int value)
/*     */   {
/* 102 */     this.handle.getIntegers().write(2, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getVehicle(World world)
/*     */   {
/* 111 */     return (Entity)this.handle.getEntityModifier(world).read(2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getVehicle(PacketEvent event)
/*     */   {
/* 120 */     return getVehicle(event.getPlayer().getWorld());
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerAttachEntity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */