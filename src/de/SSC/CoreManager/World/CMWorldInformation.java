package de.SSC.CoreManager.World;

import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;

import de.SSC.CoreManager.Config.Config;

public class CMWorldInformation 
{
	public int ID;
	public boolean Active;	
    public String Name ;
    public String CustomName ;
    public Environment MapEnvironment;
    public WorldType Type ;
    public long Seed;    
    public int BorderSize;
    public Difficulty WorldDifficulty;
    public boolean Hardcore;   
    public boolean PvP      ;
    public boolean LoseItems ;
    public boolean MobGrief      ;
    public boolean MobSpawn  ;
	public boolean Structures;	
    public boolean DoFireTick  ;
    public boolean 	Weather     ;   
    public double X;
    public double Y;
    public double Z;
    public double Pitch;
    public double Yaw;
    public boolean NeedsPermission;
    
    public CMWorldInformation()
    {
    	ID = -1;
        Name = "[N/A]";
        CustomName = "&cN&7/&cA";
        Type = WorldType.NORMAL;
        MapEnvironment = Environment.NORMAL;
         BorderSize  = 9999999;
         WorldDifficulty = Difficulty.EASY;
         Hardcore = false;   
         PvP   = false;
         LoseItems = false;
        MobGrief   = false   ;
         MobSpawn  = false;
         Weather = false;
         DoFireTick  = false;
         Structures = false;
         Seed    = 0;  
         X = 0;
         Y   = 0;
         Z     = 0;
         Pitch   = 0;
         Yaw      = 0;
         NeedsPermission = false;  
    }
    
    public CMWorldInformation(World world)
    {
    	this();
    	
    	Config config = Config.Instance();    	
    	Location spawn = world.getSpawnLocation();
    	String worldName = config.Worlds.RemoveFolderName(world.getName());   

        Name = worldName;
        CustomName = worldName;
        Type = world.getWorldType();
        MapEnvironment = world.getEnvironment();
         BorderSize  = (int)Math.round(world.getWorldBorder().getSize());
         WorldDifficulty = world.getDifficulty();
         PvP   = world.getPVP();
         LoseItems = world.getKeepSpawnInMemory();
         MobSpawn  = world.getAllowMonsters();
         Structures = world.canGenerateStructures();
         Seed    = world.getSeed();  
         X = spawn.getX();
         Y   = spawn.getY();
         Z     = spawn.getZ();
         Pitch   = spawn.getPitch();
         Yaw      = spawn.getYaw();
    }
}
