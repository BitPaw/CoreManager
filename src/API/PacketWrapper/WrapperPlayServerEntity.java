/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.PacketType;
/*    */ import com.comphenix.protocol.PacketType.Play.Server;
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.events.PacketEvent;
/*    */ import com.comphenix.protocol.reflect.StructureModifier;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WrapperPlayServerEntity
/*    */   extends AbstractPacket
/*    */ {
/* 28 */   public static final PacketType TYPE = PacketType.Play.Server.ENTITY;
/*    */   
/*    */   public WrapperPlayServerEntity() {
/* 31 */     super(new PacketContainer(TYPE), TYPE);
/* 32 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayServerEntity(PacketContainer packet) {
/* 36 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */   protected WrapperPlayServerEntity(PacketContainer packet, PacketType type) {
/* 40 */     super(packet, type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getEntityID()
/*    */   {
/* 48 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setEntityID(int value)
/*    */   {
/* 56 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Entity getEntity(World world)
/*    */   {
/* 65 */     return (Entity)this.handle.getEntityModifier(world).read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Entity getEntity(PacketEvent event)
/*    */   {
/* 74 */     return getEntity(event.getPlayer().getWorld());
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerEntity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */