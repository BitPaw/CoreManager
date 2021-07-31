/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.PacketType.Play.Server;
/*     */ import com.comphenix.protocol.ProtocolLibrary;
/*     */ import com.comphenix.protocol.ProtocolManager;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.events.PacketEvent;
/*     */ import com.comphenix.protocol.injector.PacketConstructor;
/*     */ import com.comphenix.protocol.reflect.StructureModifier;
/*     */ import com.comphenix.protocol.wrappers.WrappedDataWatcher;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
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
/*     */ public class WrapperPlayServerSpawnEntityLiving
/*     */   extends AbstractPacket
/*     */ {
/*  32 */   public static final PacketType TYPE = PacketType.Play.Server.SPAWN_ENTITY_LIVING;
/*     */   private static PacketConstructor entityConstructor;
/*     */   
/*     */   public WrapperPlayServerSpawnEntityLiving()
/*     */   {
/*  37 */     super(new PacketContainer(TYPE), TYPE);
/*  38 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerSpawnEntityLiving(PacketContainer packet) {
/*  42 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */   public WrapperPlayServerSpawnEntityLiving(Entity entity) {
/*  46 */     super(fromEntity(entity), TYPE);
/*     */   }
/*     */   
/*     */   private static PacketContainer fromEntity(Entity entity)
/*     */   {
/*  51 */     if (entityConstructor == null)
/*  52 */       entityConstructor = ProtocolLibrary.getProtocolManager().createPacketConstructor(TYPE, new Object[] { entity });
/*  53 */     return entityConstructor.createPacket(new Object[] { entity });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getEntityID()
/*     */   {
/*  61 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getEntity(World world)
/*     */   {
/*  70 */     return (Entity)this.handle.getEntityModifier(world).read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getEntity(PacketEvent event)
/*     */   {
/*  79 */     return getEntity(event.getPlayer().getWorld());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEntityID(int value)
/*     */   {
/*  87 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public EntityType getType()
/*     */   {
/*  95 */     return EntityType.fromId(((Integer)this.handle.getIntegers().read(1)).intValue());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setType(EntityType value)
/*     */   {
/* 103 */     this.handle.getIntegers().write(1, Integer.valueOf(value.getTypeId()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getX()
/*     */   {
/* 113 */     return ((Integer)this.handle.getIntegers().read(2)).intValue() / 32.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setX(double value)
/*     */   {
/* 121 */     this.handle.getIntegers().write(2, Integer.valueOf((int)Math.floor(value * 32.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getY()
/*     */   {
/* 131 */     return ((Integer)this.handle.getIntegers().read(3)).intValue() / 32.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setY(double value)
/*     */   {
/* 139 */     this.handle.getIntegers().write(3, Integer.valueOf((int)Math.floor(value * 32.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getZ()
/*     */   {
/* 149 */     return ((Integer)this.handle.getIntegers().read(4)).intValue() / 32.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setZ(double value)
/*     */   {
/* 157 */     this.handle.getIntegers().write(4, Integer.valueOf((int)Math.floor(value * 32.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getYaw()
/*     */   {
/* 165 */     return ((Byte)this.handle.getBytes().read(0)).byteValue() * 360.0F / 256.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setYaw(float value)
/*     */   {
/* 173 */     this.handle.getBytes().write(0, Byte.valueOf((byte)(int)(value * 256.0F / 360.0F)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getHeadPitch()
/*     */   {
/* 181 */     return ((Byte)this.handle.getBytes().read(1)).byteValue() * 360.0F / 256.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setHeadPitch(float value)
/*     */   {
/* 189 */     this.handle.getBytes().write(1, Byte.valueOf((byte)(int)(value * 256.0F / 360.0F)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getHeadYaw()
/*     */   {
/* 197 */     return ((Byte)this.handle.getBytes().read(2)).byteValue() * 360.0F / 256.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setHeadYaw(float value)
/*     */   {
/* 205 */     this.handle.getBytes().write(2, Byte.valueOf((byte)(int)(value * 256.0F / 360.0F)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getVelocityX()
/*     */   {
/* 213 */     return ((Integer)this.handle.getIntegers().read(5)).intValue() / 8000.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setVelocityX(double value)
/*     */   {
/* 221 */     this.handle.getIntegers().write(5, Integer.valueOf((int)(value * 8000.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getVelocityY()
/*     */   {
/* 229 */     return ((Integer)this.handle.getIntegers().read(6)).intValue() / 8000.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setVelocityY(double value)
/*     */   {
/* 237 */     this.handle.getIntegers().write(6, Integer.valueOf((int)(value * 8000.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getVelocityZ()
/*     */   {
/* 245 */     return ((Integer)this.handle.getIntegers().read(7)).intValue() / 8000.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setVelocityZ(double value)
/*     */   {
/* 253 */     this.handle.getIntegers().write(7, Integer.valueOf((int)(value * 8000.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WrappedDataWatcher getMetadata()
/*     */   {
/* 263 */     return (WrappedDataWatcher)this.handle.getDataWatcherModifier().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMetadata(WrappedDataWatcher value)
/*     */   {
/* 271 */     this.handle.getDataWatcherModifier().write(0, value);
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerSpawnEntityLiving.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */