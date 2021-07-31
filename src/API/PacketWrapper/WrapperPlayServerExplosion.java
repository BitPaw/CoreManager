/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.PacketType.Play.Server;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.reflect.StructureModifier;
/*     */ import com.comphenix.protocol.wrappers.ChunkPosition;
/*     */ import java.util.List;
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
/*     */ 
/*     */ public class WrapperPlayServerExplosion
/*     */   extends AbstractPacket
/*     */ {
/*  28 */   public static final PacketType TYPE = PacketType.Play.Server.EXPLOSION;
/*     */   
/*     */   public WrapperPlayServerExplosion() {
/*  31 */     super(new PacketContainer(TYPE), TYPE);
/*  32 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerExplosion(PacketContainer packet) {
/*  36 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getX()
/*     */   {
/*  44 */     return ((Double)this.handle.getDoubles().read(0)).doubleValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setX(double value)
/*     */   {
/*  52 */     this.handle.getDoubles().write(0, Double.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getY()
/*     */   {
/*  60 */     return ((Double)this.handle.getDoubles().read(1)).doubleValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setY(double value)
/*     */   {
/*  68 */     this.handle.getDoubles().write(1, Double.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getZ()
/*     */   {
/*  76 */     return ((Double)this.handle.getDoubles().read(2)).doubleValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setZ(double value)
/*     */   {
/*  84 */     this.handle.getDoubles().write(2, Double.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getRadius()
/*     */   {
/*  94 */     return ((Float)this.handle.getFloat().read(0)).floatValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRadius(float value)
/*     */   {
/* 104 */     this.handle.getFloat().write(0, Float.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<ChunkPosition> getRecords()
/*     */   {
/* 112 */     return (List)this.handle.getPositionCollectionModifier().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRecords(List<ChunkPosition> value)
/*     */   {
/* 120 */     this.handle.getPositionCollectionModifier().write(0, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getPlayerMotionX()
/*     */   {
/* 128 */     return ((Float)this.handle.getFloat().read(0)).floatValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPlayerMotionX(float value)
/*     */   {
/* 136 */     this.handle.getFloat().write(0, Float.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getPlayerMotionY()
/*     */   {
/* 144 */     return ((Float)this.handle.getFloat().read(1)).floatValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPlayerMotionY(float value)
/*     */   {
/* 152 */     this.handle.getFloat().write(1, Float.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getPlayerMotionZ()
/*     */   {
/* 160 */     return ((Float)this.handle.getFloat().read(2)).floatValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPlayerMotionZ(float value)
/*     */   {
/* 168 */     this.handle.getFloat().write(2, Float.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector getPlayerMotion()
/*     */   {
/* 176 */     return new Vector(getPlayerMotionX(), getPlayerMotionY(), getPlayerMotionZ());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPlayerMotion(Vector motion)
/*     */   {
/* 184 */     setPlayerMotionX((float)motion.getX());
/* 185 */     setPlayerMotionY((float)motion.getY());
/* 186 */     setPlayerMotionZ((float)motion.getZ());
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerExplosion.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */