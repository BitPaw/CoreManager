/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.PacketType;
/*    */ import com.comphenix.protocol.PacketType.Play.Server;
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.reflect.StructureModifier;
/*    */ import com.google.common.primitives.Ints;
/*    */ import java.util.List;
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
/*    */ public class WrapperPlayServerEntityDestroy
/*    */   extends AbstractPacket
/*    */ {
/* 27 */   public static final PacketType TYPE = PacketType.Play.Server.ENTITY_DESTROY;
/*    */   
/*    */   public WrapperPlayServerEntityDestroy() {
/* 30 */     super(new PacketContainer(TYPE), TYPE);
/* 31 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayServerEntityDestroy(PacketContainer packet) {
/* 35 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public List<Integer> getEntities()
/*    */   {
/* 43 */     return Ints.asList((int[])this.handle.getIntegerArrays().read(0));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setEntities(int[] entities)
/*    */   {
/* 51 */     this.handle.getIntegerArrays().write(0, entities);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setEntities(List<Integer> entities)
/*    */   {
/* 59 */     setEntities(Ints.toArray(entities));
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerEntityDestroy.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */