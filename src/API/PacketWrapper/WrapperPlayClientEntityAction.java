/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.PacketType.Play.Client;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.events.PacketEvent;
/*     */ import com.comphenix.protocol.reflect.IntEnum;
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
/*     */ 
/*     */ public class WrapperPlayClientEntityAction
/*     */   extends AbstractPacket
/*     */ {
/*  30 */   public static final PacketType TYPE = PacketType.Play.Client.ENTITY_ACTION;
/*     */   
/*     */ 
/*     */   public static class Action
/*     */     extends IntEnum
/*     */   {
/*     */     public static final int CROUCH = 1;
/*     */     
/*     */     public static final int UNCROUCH = 2;
/*     */     
/*     */     public static final int LEAVE_BED = 3;
/*     */     public static final int START_SPRINTING = 4;
/*     */     public static final int STOP_SPRINTING = 5;
/*  43 */     private static final WrapperPlayClientClientCommand.Commands INSTANCE = new WrapperPlayClientClientCommand.Commands();
/*     */     
/*     */     public static WrapperPlayClientClientCommand.Commands getInstance() {
/*  46 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */   
/*     */   public WrapperPlayClientEntityAction() {
/*  51 */     super(new PacketContainer(TYPE), TYPE);
/*  52 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayClientEntityAction(PacketContainer packet) {
/*  56 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getEntityID()
/*     */   {
/*  64 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEntityID(int value)
/*     */   {
/*  72 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getEntity(World world)
/*     */   {
/*  81 */     return (Entity)this.handle.getEntityModifier(world).read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getEntity(PacketEvent event)
/*     */   {
/*  90 */     return getEntity(event.getPlayer().getWorld());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getActionId()
/*     */   {
/*  99 */     return ((Integer)this.handle.getIntegers().read(1)).byteValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setActionId(byte value)
/*     */   {
/* 108 */     this.handle.getIntegers().write(1, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getJumpBoost()
/*     */   {
/* 116 */     return ((Integer)this.handle.getIntegers().read(2)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setJumpBoost(int value)
/*     */   {
/* 124 */     this.handle.getIntegers().write(2, Integer.valueOf(value));
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayClientEntityAction.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */