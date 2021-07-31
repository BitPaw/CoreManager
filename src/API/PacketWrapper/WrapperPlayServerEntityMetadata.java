/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.PacketType;
/*    */ import com.comphenix.protocol.PacketType.Play.Server;
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.events.PacketEvent;
/*    */ import com.comphenix.protocol.reflect.StructureModifier;
/*    */ import com.comphenix.protocol.wrappers.WrappedWatchableObject;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ public class WrapperPlayServerEntityMetadata
/*    */   extends AbstractPacket
/*    */ {
/* 32 */   public static final PacketType TYPE = PacketType.Play.Server.ENTITY_METADATA;
/*    */   
/*    */   public WrapperPlayServerEntityMetadata() {
/* 35 */     super(new PacketContainer(TYPE), TYPE);
/* 36 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayServerEntityMetadata(PacketContainer packet) {
/* 40 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getEntityId()
/*    */   {
/* 48 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setEntityId(int value)
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
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public List<WrappedWatchableObject> getEntityMetadata()
/*    */   {
/* 84 */     return (List)this.handle.getWatchableCollectionModifier().read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setEntityMetadata(List<WrappedWatchableObject> value)
/*    */   {
/* 92 */     this.handle.getWatchableCollectionModifier().write(0, value);
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerEntityMetadata.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */