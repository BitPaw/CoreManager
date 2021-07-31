/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.PacketType.Play.Server;
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
/*     */ public class WrapperPlayServerAnimation
/*     */   extends AbstractPacket
/*     */ {
/*     */   public static class Animations
/*     */     extends IntEnum
/*     */   {
/*     */     public static final int SWING_ARM = 0;
/*     */     public static final int DAMAGE_ANIMATION = 1;
/*     */     public static final int LEAVE_BED = 2;
/*     */     public static final int EAT_FOOD = 3;
/*     */     public static final int CRITICAL_EFFECT = 4;
/*     */     public static final int MAGIC_CRITICAL_EFFECT = 5;
/*     */     public static final int UNKNOWN = 102;
/*     */     public static final int CROUCH = 104;
/*     */     public static final int UNCROUCH = 105;
/*  31 */     private static Animations INSTANCE = new Animations();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public static Animations getInstance()
/*     */     {
/*  38 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */   
/*  42 */   public static final PacketType TYPE = PacketType.Play.Server.ANIMATION;
/*     */   
/*     */   public WrapperPlayServerAnimation() {
/*  45 */     super(new PacketContainer(TYPE), TYPE);
/*  46 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerAnimation(PacketContainer packet) {
/*  50 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getEntityID()
/*     */   {
/*  58 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEntityID(int value)
/*     */   {
/*  66 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getEntity(World world)
/*     */   {
/*  75 */     return (Entity)this.handle.getEntityModifier(world).read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getEntity(PacketEvent event)
/*     */   {
/*  84 */     return getEntity(event.getPlayer().getWorld());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getAnimation()
/*     */   {
/*  93 */     return ((Integer)this.handle.getIntegers().read(1)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAnimation(int value)
/*     */   {
/* 102 */     this.handle.getIntegers().write(1, Integer.valueOf(value));
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerAnimation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */