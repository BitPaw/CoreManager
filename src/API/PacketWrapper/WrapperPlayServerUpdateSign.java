/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.PacketType.Play.Server;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.events.PacketEvent;
/*     */ import com.comphenix.protocol.reflect.StructureModifier;
/*     */ import javax.annotation.Nonnull;
/*     */ import org.bukkit.Location;
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
/*     */ public class WrapperPlayServerUpdateSign
/*     */   extends AbstractPacket
/*     */ {
/*  29 */   public static final PacketType TYPE = PacketType.Play.Server.UPDATE_SIGN;
/*     */   
/*     */   public WrapperPlayServerUpdateSign() {
/*  32 */     super(new PacketContainer(TYPE), TYPE);
/*  33 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerUpdateSign(PacketContainer packet) {
/*  37 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getX()
/*     */   {
/*  45 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setX(int value)
/*     */   {
/*  53 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getY()
/*     */   {
/*  61 */     return ((Integer)this.handle.getIntegers().read(1)).shortValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setY(short value)
/*     */   {
/*  69 */     this.handle.getIntegers().write(1, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getZ()
/*     */   {
/*  77 */     return ((Integer)this.handle.getIntegers().read(2)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setZ(int value)
/*     */   {
/*  85 */     this.handle.getIntegers().write(2, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Location getLocation(PacketEvent event)
/*     */   {
/*  94 */     return new Location(event.getPlayer().getWorld(), getX(), getY(), getZ());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLocation(Location loc)
/*     */   {
/* 102 */     if (loc == null)
/* 103 */       throw new IllegalArgumentException("Location cannot be NULL.");
/* 104 */     setX(loc.getBlockX());
/* 105 */     setY((short)loc.getBlockY());
/* 106 */     setZ(loc.getBlockZ());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getLines()
/*     */   {
/* 114 */     return (String[])this.handle.getStringArrays().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLines(@Nonnull String[] lines)
/*     */   {
/* 122 */     if (lines == null)
/* 123 */       throw new IllegalArgumentException("Array cannot be NULL.");
/* 124 */     if (lines.length != 4)
/* 125 */       throw new IllegalArgumentException("The lines array must be four elements long.");
/* 126 */     this.handle.getStringArrays().write(0, lines);
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerUpdateSign.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */