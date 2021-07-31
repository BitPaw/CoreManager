package de.SSC.CoreManager.Systems.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.SSC.CoreManager.Systems.Chat.Logger;
import de.SSC.CoreManager.Systems.Chat.MessageType;
import de.SSC.CoreManager.Systems.Chat.Module;

public class WorldList 
{
	private Map<String,CMWorld> _worlds;
	private Logger _logger;
	
	public WorldList()
	{
		_worlds = new HashMap<String,CMWorld>();
		_logger = Logger.Instance();
	}
	
	public boolean IsEmpty()
	{
		return _worlds.isEmpty();
	}
	
	public void Add(CMWorld cmWorld)
	{
		String worldName = cmWorld.Name;
		
		_logger.SendToConsole(Module.WorldSystem, MessageType.Info, "Adding &7world &6<&e" + worldName + "&6> &7to world list.");
		
		_worlds.put(worldName, cmWorld);
	}
	
	public void Clear()
	{
		_logger.SendToConsole(Module.WorldSystem, MessageType.Warning, "&cResseting &7world list.");
		
		_worlds.clear();
	}
	
	public void Remove(String worldName)
	{
		_logger.SendToConsole(Module.WorldSystem, MessageType.Warning, "&cRemoving &7world &4<&c" + worldName + "&4>&7.");
		
		_worlds.remove(worldName);
	}
	
	public List<CMWorld> GetWorlds()
	{
		List<CMWorld> worlds = new ArrayList<CMWorld>();
		
		for(Map.Entry<String, CMWorld> entry : _worlds.entrySet()) 
		{
			CMWorld cmWorld = entry.getValue();

			worlds.add(cmWorld);
		}
		
		return worlds;
	}
		
	public void Swap(String replaceWorldName, CMWorld cmWorld) throws InvalidWorldNameException
	{
		boolean isWorldInList = _worlds.containsKey(replaceWorldName);

		if(replaceWorldName == null)
		{
			throw new NullPointerException("WorldList.Swap() > ReplaceWorldName is null.");
		}
		
		if(cmWorld == null)
		{
			throw new NullPointerException("WorldList.Swap() > CMWorld is null.");
		}
		
		if(isWorldInList)
		{
			_worlds.replace(replaceWorldName, cmWorld);
			//Remove(replaceWorldName);
			//Add(cmWorld);
		
		}
		else
		{
			throw new InvalidWorldNameException(replaceWorldName);
		}
	}
	
	public CMWorld GetPerName(String worldName)
	{
		return _worlds.get(worldName);
	}
}
