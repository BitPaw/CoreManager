package de.BitFire.CoreManager.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.BitFire.CoreManager.Modules.World.WorldManager;
import de.BitFire.CoreManager.Modules.World.Type.World;

public class WorldCache 
{
	private Map<Integer, org.bukkit.World> _bukkitWorldPositionsLookUp; // Get via ID
	private Map<Integer, World> _worldPositionsLookUp; // Get via ID
	private Map<String, Integer> _worldNameLookUp;  // Get via Name
	
	public WorldCache()
	{
		_bukkitWorldPositionsLookUp = new HashMap<Integer, org.bukkit.World>();
		_worldPositionsLookUp = new HashMap<Integer, World>();
		_worldNameLookUp = new HashMap<String, Integer>();
	}
		
	public World CacheWorld(org.bukkit.World bukkitWorld) 
	{
		String worldName = bukkitWorld.getName();
		worldName = WorldManager.RemoveFolderPath(worldName);		
		World world = GetWorld(worldName); 
		//org.bukkit.World foundBukktiWorld = GetBukkitWorld(worldName);
		final boolean hasNotAlreadyCached = world == null;
		
		if(hasNotAlreadyCached)
		{			
			int worldID = GetWorldNextID();
			boolean active = true;
			String customName = null;
			String seed = String.valueOf(bukkitWorld.getSeed());				
			boolean pvp = bukkitWorld.getPVP();	
			boolean spawnAnimals = bukkitWorld.getAnimalSpawnLimit() > 0;
			boolean spawnMobs = bukkitWorld.getMonsterSpawnLimit() > 0;
			boolean keepItemsOnDeath = !bukkitWorld.getKeepSpawnInMemory();
			boolean structures = bukkitWorld.canGenerateStructures();			
			int typeID = World.TranslateWorldType(bukkitWorld.getWorldType());
			int styleID = World.TranslateEnvironment(bukkitWorld.getEnvironment());	
			int blockPositionID = -1;				
			
			world = new World
					(
							worldID,
							active,
							worldName,
							customName,
							seed,								
							pvp,
							spawnAnimals,
							spawnMobs,
							keepItemsOnDeath,
							structures,							
							typeID,
							styleID,
							blockPositionID	
					);
			
			
			_bukkitWorldPositionsLookUp.put(worldID, bukkitWorld);
			_worldPositionsLookUp.put(worldID, world);
			_worldNameLookUp.put(worldName.toLowerCase(), worldID);
			
			CacheWorld(world);
		}	
		else
		{
			_bukkitWorldPositionsLookUp.put(world.ID, bukkitWorld);
		}
		
		return world;
	}
	
	
	
	public void CacheWorld(final World world)
	{
		final int id = world.ID;
		final String worldName = world.Name.toLowerCase();		
		
		_worldPositionsLookUp.put(id, world);
		_worldNameLookUp.put(worldName, id);
	}
	
	public void MergeBukkitWorld(final int worldID, org.bukkit.World bukkitWorld)
	{
		_bukkitWorldPositionsLookUp.replace(worldID, bukkitWorld);
	}
	
	public int GetWorldNextID()
	{
		return _worldPositionsLookUp.size();
	}
	
	public int[] GetWorldIDs()
	{
		final Set<Integer> worldKeySetIDs = _worldPositionsLookUp.keySet();
		final int size = worldKeySetIDs.size();
		final int[] worldIDs = new int[size];
		int counter = 0;
		
		for(final Integer worldID : worldKeySetIDs)
		{
			worldIDs[counter] = worldID;
			
			counter++;
		}
		
		return worldIDs;
	}
	
	public World GetWorld(final int id)
	{
		return _worldPositionsLookUp.get(id);
	}
	
	public World GetWorld(String worldName) 
	{
		worldName = worldName.toLowerCase();
		World world = null;
		
		final Integer id = _worldNameLookUp.get(worldName);
		
		if(id != null)
		{
			world = GetWorld(id);
		}
		
		return world;
	}

	public boolean IsWorldLoaded(int worldID) 
	{	
		return _bukkitWorldPositionsLookUp.containsKey(worldID);
	}

	public org.bukkit.World GetBukkitWorld(final String worldName) 
	{
		final Integer worldID = _worldNameLookUp.get(worldName.toLowerCase());
		
		if(worldID != null)
		{
			return _bukkitWorldPositionsLookUp.get(worldID);	
		}
				
		return null;
	}
}