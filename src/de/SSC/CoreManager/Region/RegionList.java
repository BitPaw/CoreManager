package de.SSC.CoreManager.Region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.block.Block;

import de.SSC.CoreManager.Chat.Logger;
import de.SSC.CoreManager.Chat.MessageType;
import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Geometry.Point;
import de.SSC.CoreManager.World.CMWorld;
import de.SSC.CoreManager.World.WorldSystem;
import de.SSC.CoreManager.World.Exception.InvalidWorldNameException;

public class RegionList 
{	
	
	private static RegionList _instance;
	/**
	 * World - Region Index. Every world has an ID that is linked with a list that contains every region ID.<br>
	 * [IN] World ID<br>
	 * [OUT] List of every Region in this world.
	 */
	private Map<Integer, List<Integer>> _worldRegionIndex;	// World ID, Region ID
	private Map<Integer, CMRegion> _regionIndex;	 // RegionID, Region	
	
	private WorldSystem _worldSystem;
	private Logger _logger;
	private Config _config;
	
	private RegionList()
	{
		_instance = this;
		
		_worldRegionIndex = new HashMap<Integer, List<Integer>>();
		_regionIndex = new HashMap<Integer, CMRegion>();			
		_worldSystem = WorldSystem.Instance();
		_config = Config.Instance();	
		_logger = Logger.Instance();
	}	

	public static RegionList Instance()
	{
		return _instance == null ? new RegionList() : _instance;
	}
	
	public void Clear() 
	{
		_worldRegionIndex.clear();
		_regionIndex.clear();
	}
	
	public void ReloadWorldData()
	{		
		List<CMWorld> worlds = _worldSystem.GetWorlds();
		
		_logger.SendToConsole(Module.RegionSystem, MessageType.Info, "&6<&e" + worlds.size() + "&6> &7worlds detected");
		
		for(CMWorld cmWorld : worlds)
		{
			_worldRegionIndex.put(cmWorld.Information.ID, new ArrayList<Integer>());
		}
	}
	
	/**
	 * 
	 * @param cmRegion the region you want to add.
	 */
	public void Add(CMRegion cmRegion)
	{		
		final int worldID = cmRegion.WorldID;
		final int regionID = cmRegion.ID;
		final boolean thereIsAlreadyARegionInThisWorld = _worldRegionIndex.containsKey(worldID);
		List<Integer> regionIDList;
		
		_regionIndex.put(regionID, cmRegion);
		
		if(thereIsAlreadyARegionInThisWorld)
		{
			regionIDList = _worldRegionIndex.get(worldID);	
			
			if(regionIDList == null)
			{
				System.out.print("\n\nIlligal state !!!!");
			}
				
			regionIDList.add(regionID);		
			
			_worldRegionIndex.replace(worldID, regionIDList);		
			
			_logger.SendToConsole(Module.RegionSystem, MessageType.Info, "&7World ID &6<&e" + worldID + "&6> &7merged. Region &6<&e" +regionID + "&6> &7added.");
		}
		else
		{
			regionIDList = new ArrayList<Integer>();
			
			regionIDList.add(regionID);		
			
			_worldRegionIndex.put(worldID, regionIDList);
			
			_logger.SendToConsole(Module.RegionSystem, MessageType.Info, "&7World ID &6<&e" + worldID + "&6> &7added.");
		}	
	}
	
	public CMRegion IsRegionInLocation(Block block) throws InvalidWorldNameException
	{		
		if(block == null)
		{
			return null;
		}		
		
		final String rawWorldName = block.getWorld().getName();		
		final String worldName = _config.Worlds.RemoveFolderName(rawWorldName);
		final CMWorld cmWorld = _worldSystem.GetWorld(worldName);
		final int worldID = cmWorld.Information.ID;		
		List<Integer> regionIDList =  _worldRegionIndex.get(worldID);
		
		//_logger.SendToConsole(Module.RegionSystem, MessageType.Info, "&e" + worldName + " &6ID &7" + worldID + " in worldindex with size " + regionIDList.size());
		
			if(regionIDList.size() == 0)
			{
				// There are no Regions in this world. 		
			}
			else
			{
				// There are regions in this world.
				// Go thru every region to check id the user in is there
				for(int regionID : regionIDList)
				{
					// Get region from region ID
					CMRegion cmRegion = _regionIndex.get(regionID);							
					Point point = new Point(block.getX(),block.getY(), block.getZ());					
					
					 if(cmRegion.RegionField.IsPointInCube(point))
					 {
						 return cmRegion;
					 }
				}				
			}	
			
		
		return null;
	}

	public Map<CMWorld, List<CMRegion>> GetAllRegions() 
	{
		Map<CMWorld, List<CMRegion>> worldRegionMap = new HashMap<CMWorld, List<CMRegion>>();
		
		for(Map.Entry<Integer, List<Integer>> entry : _worldRegionIndex.entrySet()) 
		{
			final int worldID = entry.getKey();
			final List<Integer> regionIDList = entry.getValue();
			final CMWorld cmWorld = _worldSystem.GetWorld(worldID);
			List<CMRegion> regionList = new ArrayList<CMRegion>();			
			
			// Get all regions from the region IDs
			for(int regionID : regionIDList)
			{
				CMRegion cmRegion = _regionIndex.get(regionID);
				
				regionList.add(cmRegion);
			}
			
			worldRegionMap.put(cmWorld, regionList);
		}
		
		return worldRegionMap;
	}

}
