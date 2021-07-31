/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.PacketType;
/*    */ import com.comphenix.protocol.PacketType.Play.Server;
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.reflect.StructureModifier;
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
/*    */ public class WrapperPlayServerTabComplete
/*    */   extends AbstractPacket
/*    */ {
/* 24 */   public static final PacketType TYPE = PacketType.Play.Server.TAB_COMPLETE;
/*    */   
/*    */   public WrapperPlayServerTabComplete() {
/* 27 */     super(new PacketContainer(TYPE), TYPE);
/* 28 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String[] getText()
/*    */   {
/* 36 */     return (String[])this.handle.getStringArrays().read(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setText(String[] value)
/*    */   {
/* 44 */     this.handle.getStringArrays().write(0, value);
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerTabComplete.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */