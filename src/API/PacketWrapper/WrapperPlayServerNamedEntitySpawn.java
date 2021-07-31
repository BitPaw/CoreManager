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
/*     */ import com.comphenix.protocol.wrappers.WrappedGameProfile;
/*     */ import com.google.common.base.Preconditions;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.util.Vector;
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
/*     */ public class WrapperPlayServerNamedEntitySpawn
/*     */   extends AbstractPacket
/*     */ {
/*  35 */   public static final PacketType TYPE = PacketType.Play.Server.NAMED_ENTITY_SPAWN;
/*     */   
/*     */   private static PacketConstructor entityConstructor;
/*     */   
/*     */   public WrapperPlayServerNamedEntitySpawn()
/*     */   {
/*  41 */     super(new PacketContainer(TYPE), TYPE);
/*  42 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerNamedEntitySpawn(PacketContainer packet) {
/*  46 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */   public WrapperPlayServerNamedEntitySpawn(Player player) {
/*  50 */     super(fromPlayer(player), TYPE);
/*     */   }
/*     */   
/*     */   private static PacketContainer fromPlayer(Player player)
/*     */   {
/*  55 */     if (entityConstructor == null)
/*  56 */       entityConstructor = ProtocolLibrary.getProtocolManager().createPacketConstructor(TYPE, new Object[] { player });
/*  57 */     return entityConstructor.createPacket(new Object[] { player });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getEntityID()
/*     */   {
/*  65 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEntityID(int value)
/*     */   {
/*  73 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getEntity(World world)
/*     */   {
/*  82 */     return (Entity)this.handle.getEntityModifier(world).read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getEntity(PacketEvent event)
/*     */   {
/*  91 */     return getEntity(event.getPlayer().getWorld());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getPlayerName()
/*     */   {
/* 101 */     WrappedGameProfile profile = getProfile();
/* 102 */     return profile != null ? profile.getName() : null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPlayerName(String value)
/*     */   {
/* 112 */     if ((value != null) && (value.length() > 16))
/* 113 */       throw new IllegalArgumentException("Maximum player name lenght is 16 characters.");
/* 114 */     setProfile(new WrappedGameProfile(getPlayerUUID(), value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getPlayerUUID()
/*     */   {
/* 122 */     WrappedGameProfile profile = getProfile();
/* 123 */     return profile != null ? profile.getId() : null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPlayerUUID(String uuid)
/*     */   {
/* 131 */     setProfile(new WrappedGameProfile(uuid, getPlayerName()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public WrappedGameProfile getProfile()
/*     */   {
/* 139 */     return (WrappedGameProfile)this.handle.getGameProfiles().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setProfile(WrappedGameProfile value)
/*     */   {
/* 147 */     this.handle.getGameProfiles().write(0, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector getPosition()
/*     */   {
/* 155 */     return new Vector(getX(), getY(), getZ());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPosition(Vector position)
/*     */   {
/* 163 */     setX(position.getX());
/* 164 */     setY(position.getY());
/* 165 */     setZ(position.getZ());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getX()
/*     */   {
/* 175 */     return ((Integer)this.handle.getIntegers().read(1)).intValue() / 32.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setX(double value)
/*     */   {
/* 183 */     this.handle.getIntegers().write(1, Integer.valueOf((int)Math.floor(value * 32.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getY()
/*     */   {
/* 193 */     return ((Integer)this.handle.getIntegers().read(2)).intValue() / 32.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setY(double value)
/*     */   {
/* 201 */     this.handle.getIntegers().write(2, Integer.valueOf((int)Math.floor(value * 32.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getZ()
/*     */   {
/* 211 */     return ((Integer)this.handle.getIntegers().read(3)).intValue() / 32.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setZ(double value)
/*     */   {
/* 219 */     this.handle.getIntegers().write(3, Integer.valueOf((int)Math.floor(value * 32.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getYaw()
/*     */   {
/* 227 */     return ((Byte)this.handle.getBytes().read(0)).byteValue() * 360.0F / 256.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setYaw(float value)
/*     */   {
/* 235 */     this.handle.getBytes().write(0, Byte.valueOf((byte)(int)(value * 256.0F / 360.0F)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getPitch()
/*     */   {
/* 243 */     return ((Byte)this.handle.getBytes().read(1)).byteValue() * 360.0F / 256.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPitch(float value)
/*     */   {
/* 251 */     this.handle.getBytes().write(1, Byte.valueOf((byte)(int)(value * 256.0F / 360.0F)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getCurrentItem()
/*     */   {
/* 261 */     return ((Integer)this.handle.getIntegers().read(4)).shortValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCurrentItem(short value)
/*     */   {
/* 271 */     this.handle.getIntegers().write(4, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WrappedDataWatcher getMetadata()
/*     */   {
/* 282 */     return (WrappedDataWatcher)this.handle.getDataWatcherModifier().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMetadata(WrappedDataWatcher value)
/*     */   {
/* 293 */     this.handle.getDataWatcherModifier().write(0, value);
/*     */   }
/*     */   
/*     */   public PacketContainer getHandle()
/*     */   {
/* 298 */     Preconditions.checkNotNull(getPlayerName(), "Must specify a player name.");
/* 299 */     Preconditions.checkNotNull(getPlayerUUID(), "Must specify a player UUID.");
/* 300 */     return super.getHandle();
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerNamedEntitySpawn.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */