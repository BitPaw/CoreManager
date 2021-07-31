/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.PacketType.Play.Server;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.reflect.IntEnum;
/*     */ import com.comphenix.protocol.reflect.StructureModifier;
/*     */ import java.util.Collection;
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
/*     */ public class WrapperPlayServerScoreboardTeam
/*     */   extends AbstractPacket
/*     */ {
/*  27 */   public static final PacketType TYPE = PacketType.Play.Server.SCOREBOARD_TEAM;
/*     */   
/*     */ 
/*     */   public static class Modes
/*     */     extends IntEnum
/*     */   {
/*     */     public static final int TEAM_CREATED = 0;
/*     */     
/*     */     public static final int TEAM_REMOVED = 1;
/*     */     
/*     */     public static final int TEAM_UPDATED = 2;
/*     */     
/*     */     public static final int PLAYERS_ADDED = 3;
/*     */     public static final int PLAYERS_REMOVED = 4;
/*  41 */     private static final Modes INSTANCE = new Modes();
/*     */     
/*     */     public static Modes getInstance() {
/*  44 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */   
/*     */   public WrapperPlayServerScoreboardTeam() {
/*  49 */     super(new PacketContainer(TYPE), TYPE);
/*  50 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */   public WrapperPlayServerScoreboardTeam(PacketContainer packet) {
/*  54 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTeamName()
/*     */   {
/*  62 */     return (String)this.handle.getStrings().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTeamName(String value)
/*     */   {
/*  70 */     this.handle.getStrings().write(0, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getPacketMode()
/*     */   {
/*  80 */     return ((Integer)this.handle.getIntegers().read(0)).byteValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPacketMode(byte value)
/*     */   {
/*  90 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTeamDisplayName()
/*     */   {
/* 100 */     return (String)this.handle.getStrings().read(1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTeamDisplayName(String value)
/*     */   {
/* 110 */     this.handle.getStrings().write(1, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTeamPrefix()
/*     */   {
/* 120 */     return (String)this.handle.getStrings().read(2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTeamPrefix(String value)
/*     */   {
/* 130 */     this.handle.getStrings().write(2, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTeamSuffix()
/*     */   {
/* 140 */     return (String)this.handle.getStrings().read(3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTeamSuffix(String value)
/*     */   {
/* 150 */     this.handle.getStrings().write(3, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getFriendlyFire()
/*     */   {
/* 160 */     return ((Integer)this.handle.getIntegers().read(1)).byteValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFriendlyFire(byte value)
/*     */   {
/* 170 */     this.handle.getIntegers().write(1, Integer.valueOf(value));
/*     */   }
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
/*     */   public Collection<String> getPlayers()
/*     */   {
/* 186 */     return (Collection)this.handle.getSpecificModifier(Collection.class).read(0);
/*     */   }
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
/*     */   public void setPlayers(Collection<String> players)
/*     */   {
/* 201 */     this.handle.getSpecificModifier(Collection.class).write(0, players);
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerScoreboardTeam.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */