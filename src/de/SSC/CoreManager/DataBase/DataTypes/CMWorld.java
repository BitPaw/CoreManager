package de.SSC.CoreManager.DataBase.DataTypes;

import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;

public class CMWorld 
{
public World BukkitWorld;	
	
    public String Name ;
    public String CustomName ;
    public WorldType MapType ;
    public Environment MapStyle ;
    public int BorderSize   ;
    public Difficulty WorldDifficulty;
    public boolean Hardcore;   
    public boolean PvP      ;
    public boolean KeepInventory ;
    public boolean MobGrief      ;
    public boolean MobSpawning  ;
    public boolean 	Weather     ;
    public boolean DoFireTick  ;
	public boolean GenerateStructures;	
    public long Seed       ;
    public float SpawnX ;
    public float SpawnY       ;
    public float SpawnZ       ;
    public float SpawnPitch    ;
    public float SpawnYaw      ;
    public boolean NeedsPermission;


    public CMWorld()
    {
      this("[N/A]");
    }
    
    public CMWorld(String worldName)
    {
         Name = worldName;
          CustomName = worldName;
         MapType = WorldType.AMPLIFIED;
          MapStyle = World.Environment.NORMAL;
          BorderSize  = 600000 ;
          WorldDifficulty = Difficulty.HARD;
          Hardcore = false;   
          PvP   = true;
          KeepInventory = false;
          MobGrief   = true   ;
          MobSpawning  = true;
          	Weather = true    ;
          DoFireTick  = true;
          GenerateStructures = true;
          Seed    = 0   ;
          SpawnX = 0;
          SpawnY   = 0;
          SpawnZ     = 0;
          SpawnPitch   = 0;
          SpawnYaw      = 0;
          NeedsPermission = false;
    }
}
