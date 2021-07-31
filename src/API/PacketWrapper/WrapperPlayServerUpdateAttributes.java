/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.events.PacketEvent;
/*    */ import com.comphenix.protocol.reflect.StructureModifier;
/*    */ import com.comphenix.protocol.wrappers.WrappedAttribute;
/*    */ import java.util.List;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class WrapperPlayServerUpdateAttributes extends AbstractPacket
/*    */ {
/* 14 */   public static final com.comphenix.protocol.PacketType TYPE = com.comphenix.protocol.PacketType.Play.Server.UPDATE_ATTRIBUTES;
/*    */   
/*    */   public WrapperPlayServerUpdateAttributes() {
/* 17 */     super(new PacketContainer(TYPE), TYPE);
/* 18 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayServerUpdateAttributes(PacketContainer packet) {
/* 22 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getEntityId()
/*    */   {
/* 30 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setEntityId(int value)
/*    */   {
/* 38 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Entity getEntity(World world)
/*    */   {
/* 47 */     return (Entity)this.handle.getEntityModifier(world).read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Entity getEntity(PacketEvent event)
/*    */   {
/* 56 */     return getEntity(event.getPlayer().getWorld());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public List<WrappedAttribute> getAttributes()
/*    */   {
/* 64 */     return (List)this.handle.getAttributeCollectionModifier().read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setAttributes(List<WrappedAttribute> value)
/*    */   {
/* 72 */     this.handle.getAttributeCollectionModifier().write(0, value);
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerUpdateAttributes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */