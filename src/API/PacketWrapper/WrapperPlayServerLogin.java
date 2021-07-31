/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.events.PacketEvent;
/*     */ import com.comphenix.protocol.reflect.StructureModifier;
/*     */ import com.comphenix.protocol.wrappers.EnumWrappers.Difficulty;
/*     */ import com.comphenix.protocol.wrappers.EnumWrappers.NativeGameMode;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.WorldType;
/*     */ import org.bukkit.entity.Entity;
/*     */ 
/*     */ public class WrapperPlayServerLogin extends AbstractPacket
/*     */ {
/*  14 */   public static final com.comphenix.protocol.PacketType TYPE = com.comphenix.protocol.PacketType.Play.Server.LOGIN;
/*     */   
/*     */   public WrapperPlayServerLogin() {
/*  17 */     super(new PacketContainer(TYPE), TYPE);
/*  18 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerLogin(PacketContainer packet) {
/*  22 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getEntityId()
/*     */   {
/*  30 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEntityId(int value)
/*     */   {
/*  38 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getEntity(World world)
/*     */   {
/*  47 */     return (Entity)this.handle.getEntityModifier(world).read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getEntity(PacketEvent event)
/*     */   {
/*  56 */     return getEntity(event.getPlayer().getWorld());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public EnumWrappers.NativeGameMode getGamemode()
/*     */   {
/*  64 */     return (EnumWrappers.NativeGameMode)this.handle.getGameModes().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setGamemode(EnumWrappers.NativeGameMode value)
/*     */   {
/*  72 */     this.handle.getGameModes().write(0, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isHardcore()
/*     */   {
/*  80 */     return ((Boolean)this.handle.getBooleans().read(0)).booleanValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setHardcore(boolean value)
/*     */   {
/*  88 */     this.handle.getBooleans().write(0, Boolean.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getDimension()
/*     */   {
/*  96 */     return ((Integer)this.handle.getIntegers().read(1)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDimension(int value)
/*     */   {
/* 104 */     this.handle.getIntegers().write(1, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public EnumWrappers.Difficulty getDifficulty()
/*     */   {
/* 112 */     return (EnumWrappers.Difficulty)this.handle.getDifficulties().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDifficulty(EnumWrappers.Difficulty difficulty)
/*     */   {
/* 120 */     this.handle.getDifficulties().write(0, difficulty);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getMaxPlayers()
/*     */   {
/* 130 */     return ((Integer)this.handle.getIntegers().read(2)).byteValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMaxPlayers(byte value)
/*     */   {
/* 138 */     this.handle.getIntegers().write(2, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WorldType getLevelType()
/*     */   {
/* 148 */     return (WorldType)this.handle.getWorldTypeModifier().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLevelType(WorldType type)
/*     */   {
/* 158 */     this.handle.getWorldTypeModifier().write(0, type);
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerLogin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */