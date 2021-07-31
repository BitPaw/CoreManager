/*    */ package com.bobacadodl.ClickEdit.packetwrapper;
/*    */ 
/*    */ import com.comphenix.protocol.PacketType;
/*    */ import com.comphenix.protocol.PacketType.Play.Client;
/*    */ import com.comphenix.protocol.events.PacketContainer;
/*    */ import com.comphenix.protocol.reflect.IntEnum;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WrapperPlayClientClientCommand
/*    */   extends AbstractPacket
/*    */ {
/* 29 */   public static final PacketType TYPE = PacketType.Play.Client.CLIENT_COMMAND;
/*    */   
/*    */ 
/*    */   public static class Commands
/*    */     extends IntEnum
/*    */   {
/*    */     public static final int INITIAL_SPAWN = 0;
/*    */     
/*    */     public static final int RESPAWN_AFTER_DEATH = 1;
/*    */     
/*    */     public static final int OPEN_INVENTORY_ACHIEVEMENT = 2;
/*    */     
/* 41 */     private static final Commands INSTANCE = new Commands();
/*    */     
/*    */     public static Commands getInstance() {
/* 44 */       return INSTANCE;
/*    */     }
/*    */   }
/*    */   
/*    */   public WrapperPlayClientClientCommand() {
/* 49 */     super(new PacketContainer(TYPE), TYPE);
/* 50 */     this.handle.getModifier().writeDefaults();
/*    */   }
/*    */   
/*    */   public WrapperPlayClientClientCommand(PacketContainer packet) {
/* 54 */     super(packet, TYPE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getCommand()
/*    */   {
/* 63 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setCommand(int value)
/*    */   {
/* 72 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*    */   }
/*    */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayClientClientCommand.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */