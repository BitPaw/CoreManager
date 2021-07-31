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
/*     */ public class WrapperPlayClientArmAnimation
/*     */   extends AbstractPacket
/*     */ {
/*  29 */   public static final PacketType TYPE = PacketType.Play.Client.ARM_ANIMATION;
/*     */   
/*     */ 
/*     */   public static class Animations
/*     */     extends IntEnum
/*     */   {
/*     */     public static final int NO_ANIMATION = 0;
/*     */     
/*     */     public static final int SWING_ARM = 1;
/*     */     
/*     */     public static final int DAMAGE_ANIMATION = 2;
/*     */     
/*     */     public static final int LEAVE_BED = 3;
/*     */     
/*     */     public static final int EAT_FOOD = 5;
/*     */     
/*     */     public static final int UNKNOWN = 102;
/*     */     
/*     */     public static final int CROUCH = 104;
/*     */     public static final int UNCROUCH = 105;
/*  49 */     private static Animations INSTANCE = new Animations();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public static Animations getInstance()
/*     */     {
/*  56 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */   
/*     */   public WrapperPlayClientArmAnimation() {
/*  61 */     super(new PacketContainer(TYPE), TYPE);
/*  62 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayClientArmAnimation(PacketContainer packet) {
/*  66 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getEntityID()
/*     */   {
/*  74 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEntityID(int value)
/*     */   {
/*  82 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getEntity(World world)
/*     */   {
/*  91 */     return (Entity)this.handle.getEntityModifier(world).read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getEntity(PacketEvent event)
/*     */   {
/* 100 */     return getEntity(event.getPlayer().getWorld());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getAnimation()
/*     */   {
/* 108 */     return ((Integer)this.handle.getIntegers().read(1)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAnimation(int value)
/*     */   {
/* 116 */     this.handle.getIntegers().write(1, Integer.valueOf(value));
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayClientArmAnimation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */