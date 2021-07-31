/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.PacketType.Play.Server;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.reflect.StructureModifier;
/*     */ import com.comphenix.protocol.wrappers.EnumWrappers.Difficulty;
/*     */ import com.comphenix.protocol.wrappers.EnumWrappers.NativeGameMode;
/*     */ import org.bukkit.WorldType;
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
/*     */ public class WrapperPlayServerRespawn
/*     */   extends AbstractPacket
/*     */ {
/*  28 */   public static final PacketType TYPE = PacketType.Play.Server.RESPAWN;
/*     */   
/*     */   public WrapperPlayServerRespawn() {
/*  31 */     super(new PacketContainer(TYPE), TYPE);
/*  32 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerRespawn(PacketContainer packet) {
/*  36 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getDimension()
/*     */   {
/*  44 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDimension(int value)
/*     */   {
/*  52 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public EnumWrappers.Difficulty getDifficulty()
/*     */   {
/*  60 */     return (EnumWrappers.Difficulty)this.handle.getDifficulties().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDifficulty(EnumWrappers.Difficulty value)
/*     */   {
/*  68 */     this.handle.getDifficulties().write(0, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public EnumWrappers.NativeGameMode getGameMode()
/*     */   {
/*  76 */     return (EnumWrappers.NativeGameMode)this.handle.getGameModes().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setGameMode(EnumWrappers.NativeGameMode mode)
/*     */   {
/*  84 */     this.handle.getGameModes().write(0, mode);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public WorldType getLevelType()
/*     */   {
/*  92 */     return (WorldType)this.handle.getWorldTypeModifier().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLevelType(WorldType value)
/*     */   {
/* 100 */     this.handle.getWorldTypeModifier().write(0, value);
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerRespawn.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */