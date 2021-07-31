package de.SSC.CoreManager.Multiverse;

import org.bukkit.World;
import org.bukkit.WorldType;

public class WorldCredentials
{
    public String Name ;
    public String CustomName ;
    public WorldType MapType ;
    public World.Environment MapStyle ;
    public int BorderSize   ;
    public boolean PvP      ;
    public boolean KeepInventory ;
    public boolean MobGrief      ;
    public boolean MobSpaning  ;
    public boolean 	Weather     ;
    public boolean DoFireTick  ;
    public long Seed       ;
    public float SpawnX ;
    public float Spawny       ;
    public float Spawnz       ;
    public float SpawnPitch    ;
    public float SpawnYaw      ;

    public WorldCredentials()
    {
         Name = "[N/A]";
          CustomName = "[N/A]";
         MapType = WorldType.NORMAL;
          MapStyle = World.Environment.NORMAL;
          BorderSize  = 600000 ;
          PvP   = true;
          KeepInventory = false;
          MobGrief   = true   ;
          MobSpaning  = true;
          	Weather = true    ;
          DoFireTick  = true;
          Seed    = 0   ;
          SpawnX = 0;
          Spawny   = 0;
          Spawnz     = 0;
          SpawnPitch   = 0;
          SpawnYaw      = 0;
    }
}
