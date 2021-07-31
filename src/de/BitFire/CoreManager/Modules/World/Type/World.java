package de.BitFire.CoreManager.Modules.World.Type;

import org.bukkit.WorldType;
import org.bukkit.World.Environment;

import de.BitFire.CoreManager.Modules.DataBase.ICloneable;

public class World implements ICloneable
{
	public int ID;
	public boolean Active;
	public String Name;
	public String CustomName;
	public String Seed;	
	
	public boolean PvP;	
	public boolean SpawnAnimals;
	public boolean SpawnMobs;
	public boolean KeepItemsOnDeath;
	public boolean Structures;
	
	public int TypeID;
	public int StyleID;	
	public int BlockPositionID;	
	
	public World(final int id, final String name)
	{	
		ID = id;		
		Active = true;		
		Name = name;		
		CustomName = null;		
		Seed = "1";		
		KeepItemsOnDeath = true;
		Structures = true;		
		PvP = true;		
		SpawnAnimals = true;
		SpawnMobs = true;		
		TypeID = -1;	
		StyleID = -1;				
		BlockPositionID = -1;
	}
	
	public World
		(
				final int id,
				final boolean active,
				final String name,
				final String customName,
				final String seed,					
				final boolean pvp,
				final boolean spawnAnimals,
				final boolean spawnMobs,
				final boolean keepItemsOnDeath,
				final boolean structures,			
				final int typeID,
				final int styleID,	
				final int blockPositionID				
		)
	
	{		
		ID = id;
		Active = active;
		Name = name;
		CustomName=  customName; 
		Seed = seed;				
		PvP = pvp;
		SpawnAnimals = spawnAnimals;
		SpawnMobs = spawnMobs;
		KeepItemsOnDeath = keepItemsOnDeath;
		Structures = structures;				
		TypeID = typeID;
		StyleID = styleID;
		BlockPositionID	= blockPositionID;
	}
	
	public String GetPreferedName()
	{
		return CustomName == null ? Name : CustomName;
	}

	@Override
	public Object Clone() 
	{
		return new World
		(
				ID,
				Active,
				Name,
				CustomName,
				Seed,						
				PvP,	
				SpawnAnimals,
				SpawnMobs,
				KeepItemsOnDeath,
				Structures,						
				TypeID,
				StyleID,
				BlockPositionID	
		);
	}
	
	
	
	public static int TranslateEnvironment(final Environment environment)
	{					
		switch(environment)
		{
		case NORMAL:	return 0; 
		case NETHER: return 1; 
		case THE_END: return 2; 
			
		default:
			throw new IllegalArgumentException("Environment is out of range! ID:" + environment);		
		}
	}	
	
	public static Environment TranslateEnvironment(final int environmentID)
	{					
		switch(environmentID)
		{
		case 0:	return Environment.NORMAL; 
		case 1: return Environment.NETHER; 
		case 2: return Environment.THE_END; 
			
		default:
			throw new IllegalArgumentException("WorldTypeID is out of range! ID:" + environmentID);		
		}
	}
	
	public static int TranslateWorldType(final WorldType worldType)
	{			
		switch(worldType)
		{
		case AMPLIFIED:	
			return 0; 
			
		case BUFFET: 
			return 1; 
			
		case CUSTOMIZED: 
			return 2; 
			
		case FLAT: 
			return 3; 
			
		case LARGE_BIOMES: 
			return 4; 
			
		case NORMAL:
			return 5; 
			
		case VERSION_1_1: 
			return 6; 
			
		default:
			throw new IllegalArgumentException("WorldType is out of range! Type:" + worldType);		
		}
	}
	
	public static WorldType TranslateWorldType(final int worldTypeID)
	{			
		switch(worldTypeID)
		{
		case 0:	return WorldType.AMPLIFIED; 
		case 1: return WorldType.BUFFET; 
		case 2: return WorldType.CUSTOMIZED; 
		case 3: return WorldType.FLAT; 
		case 4: return WorldType.LARGE_BIOMES; 
		case 5: return WorldType.NORMAL; 
		case 6: return WorldType.VERSION_1_1; 
			
		default:
			throw new IllegalArgumentException("WorldTypeID is out of range! ID:" + worldTypeID);		
		}
	}
}
