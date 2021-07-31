/*     */ package com.bobacadodl.ClickEdit.packetwrapper;
/*     */ 
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.events.PacketEvent;
/*     */ import com.comphenix.protocol.reflect.StructureModifier;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class WrapperPlayServerWorldParticles extends AbstractPacket
/*     */ {
/*  15 */   public static final com.comphenix.protocol.PacketType TYPE = com.comphenix.protocol.PacketType.Play.Server.WORLD_PARTICLES;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static enum ParticleEffect
/*     */   {
/*  22 */     HUGE_EXPLOSION("hugeexplosion"), 
/*  23 */     LARGE_EXPLODE("largeexplode"), 
/*  24 */     FIREWORKS_SPARK("fireworksSpark"), 
/*  25 */     BUBBLE("bubble"), 
/*  26 */     SUSPEND("suspend"), 
/*  27 */     DEPTH_SUSPEND("depthSuspend"), 
/*  28 */     TOWN_AURA("townaura"), 
/*  29 */     CRIT("crit"), 
/*  30 */     MAGIC_CRIT("magicCrit"), 
/*  31 */     MOB_SPELL("mobSpell"), 
/*  32 */     MOB_SPELL_AMBIENT("mobSpellAmbient"), 
/*  33 */     SPELL("spell"), 
/*  34 */     INSTANT_SPELL("instantSpell"), 
/*  35 */     WITCH_MAGIC("witchMagic"), 
/*  36 */     NOTE("note"), 
/*  37 */     PORTAL("portal"), 
/*  38 */     ENCHANTMENT_TABLE("enchantmenttable"), 
/*  39 */     EXPLODE("explode"), 
/*  40 */     FLAME("flame"), 
/*  41 */     LAVA("lava"), 
/*  42 */     FOOTSTEP("footstep"), 
/*  43 */     SPLASH("splash"), 
/*  44 */     LARGE_SMOKE("largesmoke"), 
/*  45 */     CLOUD("cloud"), 
/*  46 */     RED_DUST("reddust"), 
/*  47 */     SNOWBALL_POOF("snowballpoof"), 
/*  48 */     DRIP_WATER("dripWater"), 
/*  49 */     DRIP_LAVA("dripLava"), 
/*  50 */     SNOW_SHOVEL("snowshovel"), 
/*  51 */     SLIME("slime"), 
/*  52 */     HEART("heart"), 
/*  53 */     ANGRY_VILLAGER("angryVillager"), 
/*  54 */     HAPPY_VILLAGER("happerVillager"), 
/*  55 */     ICONCRACK("iconcrack_"), 
/*  56 */     TILECRACK("tilecrack_");
/*     */     
/*     */ 
/*     */     private final String name;
/*     */     
/*  61 */     private static volatile Map<String, ParticleEffect> LOOKUP = generateLookup();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private static Map<String, ParticleEffect> generateLookup()
/*     */     {
/*  68 */       Map<String, ParticleEffect> created = new HashMap();
/*     */       
/*     */ 
/*  71 */       for (ParticleEffect effect : values())
/*  72 */         created.put(effect.getParticleName(), effect);
/*  73 */       return created;
/*     */     }
/*     */     
/*     */     private ParticleEffect(String name) {
/*  77 */       this.name = name;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public static ParticleEffect fromName(String name)
/*     */     {
/*  86 */       return (ParticleEffect)LOOKUP.get(name);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public String getParticleName()
/*     */     {
/*  94 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public WrapperPlayServerWorldParticles()
/*     */   {
/* 102 */     super(new PacketContainer(TYPE), TYPE);
/* 103 */     this.handle.getModifier().writeDefaults();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public WrapperPlayServerWorldParticles(PacketContainer packet)
/*     */   {
/* 111 */     super(packet, TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WrapperPlayServerWorldParticles(ParticleEffect effect, int count, Location location, Vector offset)
/*     */   {
/* 122 */     this();
/* 123 */     setParticleEffect(effect);
/* 124 */     setNumberOfParticles(count);
/* 125 */     setLocation(location);
/* 126 */     setOffset(offset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getParticleName()
/*     */   {
/* 134 */     return (String)this.handle.getStrings().read(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setParticleName(String value)
/*     */   {
/* 142 */     this.handle.getStrings().write(0, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ParticleEffect getParticleEffect()
/*     */   {
/* 150 */     return ParticleEffect.fromName(getParticleName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setParticleEffect(ParticleEffect effect)
/*     */   {
/* 158 */     if (effect == null)
/* 159 */       throw new IllegalArgumentException("effect cannot be NULL.");
/* 160 */     setParticleName(effect.getParticleName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Location getLocation(PacketEvent event)
/*     */   {
/* 169 */     return getLocation(event.getPlayer().getWorld());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Location getLocation(World world)
/*     */   {
/* 178 */     return new Location(world, getX(), getY(), getZ());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLocation(Location loc)
/*     */   {
/* 186 */     if (loc == null)
/* 187 */       throw new IllegalArgumentException("Location cannot be NULL.");
/* 188 */     setX((float)loc.getX());
/* 189 */     setY((float)loc.getY());
/* 190 */     setZ((float)loc.getZ());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOffset(Vector vector)
/*     */   {
/* 198 */     if (vector == null)
/* 199 */       throw new IllegalArgumentException("Vector cannot be NULL.");
/* 200 */     setOffsetX((float)vector.getX());
/* 201 */     setOffsetY((float)vector.getY());
/* 202 */     setOffsetZ((float)vector.getZ());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector getOffset()
/*     */   {
/* 210 */     return new Vector(getX(), getY(), getZ());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getX()
/*     */   {
/* 218 */     return ((Float)this.handle.getFloat().read(0)).floatValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setX(float value)
/*     */   {
/* 226 */     this.handle.getFloat().write(0, Float.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getY()
/*     */   {
/* 234 */     return ((Float)this.handle.getFloat().read(1)).floatValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setY(float value)
/*     */   {
/* 242 */     this.handle.getFloat().write(1, Float.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getZ()
/*     */   {
/* 250 */     return ((Float)this.handle.getFloat().read(2)).floatValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setZ(float value)
/*     */   {
/* 258 */     this.handle.getFloat().write(2, Float.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getOffsetX()
/*     */   {
/* 266 */     return ((Float)this.handle.getFloat().read(3)).floatValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOffsetX(float value)
/*     */   {
/* 274 */     this.handle.getFloat().write(3, Float.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getOffsetY()
/*     */   {
/* 282 */     return ((Float)this.handle.getFloat().read(4)).floatValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOffsetY(float value)
/*     */   {
/* 290 */     this.handle.getFloat().write(4, Float.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getOffsetZ()
/*     */   {
/* 298 */     return ((Float)this.handle.getFloat().read(5)).floatValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOffsetZ(float value)
/*     */   {
/* 306 */     this.handle.getFloat().write(5, Float.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getParticleSpeed()
/*     */   {
/* 314 */     return ((Float)this.handle.getFloat().read(6)).floatValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setParticleSpeed(float value)
/*     */   {
/* 322 */     this.handle.getFloat().write(6, Float.valueOf(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getNumberOfParticles()
/*     */   {
/* 330 */     return ((Integer)this.handle.getIntegers().read(0)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setNumberOfParticles(int value)
/*     */   {
/* 338 */     this.handle.getIntegers().write(0, Integer.valueOf(value));
/*     */   }
/*     */ }


/* Location:              B:\(SSC)\[Java]\_Decompiled\ClickEdit.jar!\com\bobacadodl\ClickEdit\packetwrapper\WrapperPlayServerWorldParticles.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */