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
/*     */ public class WrapperPlayServerSpawnEntityExperienceOrb
/*     */   extends AbstractPacket
/*     */ {
/*  28 */   public static final PacketType TYPE = PacketType.Play.Server.SPAWN_ENTITY_EXPERIENCE_ORB;
/*     */   
/*     */   public WrapperPlayServerSpawnEntityExperienceOrb() {
/*  31 */     super(new PacketContainer(TYPE), TYPE);
/*  32 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerSpawnEntityExperienceOrb(PacketContainer packet) {
/*  36 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getEntityId()
/*     */   {
/*  44 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getEntity(World world)
/*     */   {
/*  53 */     return (Entity)this.handle.getEntityModifier(world).read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getEntity(PacketEvent event)
/*     */   {
/*  62 */     return getEntity(event.getPlayer().getWorld());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEntityId(int value)
/*     */   {
/*  70 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getX()
/*     */   {
/*  80 */     return ((Integer)this.handle.getIntegers().read(1)).intValue() / 32.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setX(double value)
/*     */   {
/*  88 */     this.handle.getIntegers().write(1, Integer.valueOf((int)Math.floor(value * 32.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getY()
/*     */   {
/*  98 */     return ((Integer)this.handle.getIntegers().read(2)).intValue() / 32.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setY(double value)
/*     */   {
/* 106 */     this.handle.getIntegers().write(2, Integer.valueOf((int)Math.floor(value * 32.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getZ()
/*     */   {
/* 116 */     return ((Integer)this.handle.getIntegers().read(3)).intValue() / 32.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setZ(double value)
/*     */   {
/* 124 */     this.handle.getIntegers().write(3, Integer.valueOf((int)Math.floor(value * 32.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getCount()
/*     */   {
/* 132 */     return ((Integer)this.handle.getIntegers().read(4)).shortValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCount(short value)
/*     */   {
/* 140 */     this.handle.getIntegers().write(4, Integer.valueOf(value));
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerSpawnEntityExperienceOrb.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */