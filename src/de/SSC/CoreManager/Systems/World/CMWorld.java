package de.SSC.CoreManager.Systems.World;

import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Utility.DataSaveStorageType;

public class CMWorld 
{
	public DataSaveStorageType SaveStorageType;
	public boolean Active;
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
    public double SpawnX ;
    public double SpawnY       ;
    public double SpawnZ       ;
    public double SpawnPitch    ;
    public double SpawnYaw      ;
    public boolean NeedsPermission;

    public CMWorld()
    {    	
    	SaveStorageType = DataSaveStorageType.Undefined;
    	Active = true;
    	BukkitWorld = null;
    	   Name = "[N/A]";
           CustomName = "[N/A]";
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
    
    public CMWorld(String worldName)
    {
    	this();
    	  
    	Name = worldName;
        CustomName = worldName;     
    }
    
    public CMWorld(World bukkitWorld)
    {
    	this();
    	
    	Config config = Config.Instance();    	
    	Location spawn = bukkitWorld.getSpawnLocation();
    	String worldName = config.Worlds.RemoveFolderName(bukkitWorld.getName());   
    	
    	if(worldName.contains("\\"));
    	{
    		worldName = config.Worlds.RemoveFolderName(bukkitWorld.getName()); 
    	}
    	
    	SaveStorageType = DataSaveStorageType.UnRegistered;
        Name = worldName;
        CustomName = worldName;
        MapType = bukkitWorld.getWorldType();
         MapStyle = bukkitWorld.getEnvironment();
         //BorderSize  = bukkitWorld.getWorldBorder().getSize();
         WorldDifficulty = bukkitWorld.getDifficulty();
         //Hardcore = bukkitWorld.;   
         PvP   = bukkitWorld.getPVP();
         KeepInventory = bukkitWorld.getKeepSpawnInMemory();
         //MobGrief   = bukkitWorld   ;
         MobSpawning  = bukkitWorld.getAllowMonsters();
        //Weather = bukkitWorld.getWeatherDuration();
         DoFireTick  = true;
         GenerateStructures = true;
         Seed    = bukkitWorld.getSeed();  
         SpawnX = spawn.getX();
         SpawnY   = spawn.getY();
         SpawnZ     = spawn.getZ();
         SpawnPitch   = spawn.getPitch();
         SpawnYaw      = spawn.getYaw();
         NeedsPermission = false;    	
    }
  
}
