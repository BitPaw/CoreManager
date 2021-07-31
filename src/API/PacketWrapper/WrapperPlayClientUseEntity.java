/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.PacketType;
/*    */ import com.comphenix.protocol.PacketType.Play.Client;
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.events.PacketEvent;
/*    */ import com.comphenix.protocol.reflect.StructureModifier;
/*    */ import com.comphenix.protocol.wrappers.EnumWrappers.EntityUseAction;
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
/*    */ public class WrapperPlayClientUseEntity
/*    */   extends AbstractPacket
/*    */ {
/* 29 */   public static final PacketType TYPE = PacketType.Play.Client.USE_ENTITY;
/*    */   
/*    */   public WrapperPlayClientUseEntity() {
/* 32 */     super(new PacketContainer(TYPE), TYPE);
/* 33 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayClientUseEntity(PacketContainer packet) {
/* 37 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getTargetID()
/*    */   {
/* 45 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Entity getTarget(World world)
/*    */   {
/* 54 */     return (Entity)this.handle.getEntityModifier(world).read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Entity getTarget(PacketEvent event)
/*    */   {
/* 63 */     return getTarget(event.getPlayer().getWorld());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setTargetID(int value)
/*    */   {
/* 71 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public EnumWrappers.EntityUseAction getMouse()
/*    */   {
/* 79 */     return (EnumWrappers.EntityUseAction)this.handle.getEntityUseActions().read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setMouse(EnumWrappers.EntityUseAction value)
/*    */   {
/* 87 */     this.handle.getEntityUseActions().write(0, value);
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayClientUseEntity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */