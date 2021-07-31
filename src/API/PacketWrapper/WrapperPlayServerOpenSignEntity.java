/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.PacketType;
/*    */ import com.comphenix.protocol.PacketType.Play.Server;
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.events.PacketEvent;
/*    */ import com.comphenix.protocol.reflect.StructureModifier;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class WrapperPlayServerOpenSignEntity
/*    */   extends AbstractPacket
/*    */ {
/* 14 */   public static final PacketType TYPE = PacketType.Play.Server.OPEN_SIGN_ENTITY;
/*    */   
/*    */   public WrapperPlayServerOpenSignEntity() {
/* 17 */     super(new PacketContainer(TYPE), TYPE);
/* 18 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayServerOpenSignEntity(PacketContainer packet) {
/* 22 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getX()
/*    */   {
/* 30 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setX(int value)
/*    */   {
/* 38 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getY()
/*    */   {
/* 46 */     return ((Integer)this.handle.getIntegers().read(1)).intValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setY(int value)
/*    */   {
/* 54 */     this.handle.getIntegers().write(1, Integer.valueOf(value));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getZ()
/*    */   {
/* 62 */     return ((Integer)this.handle.getIntegers().read(2)).intValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setZ(int value)
/*    */   {
/* 70 */     this.handle.getIntegers().write(2, Integer.valueOf(value));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Location getLocation(PacketEvent event)
/*    */   {
/* 79 */     return new Location(event.getPlayer().getWorld(), getX(), getY(), getZ());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setLocation(Location loc)
/*    */   {
/* 87 */     setX(loc.getBlockX());
/* 88 */     setY((byte)loc.getBlockY());
/* 89 */     setZ(loc.getBlockZ());
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerOpenSignEntity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */