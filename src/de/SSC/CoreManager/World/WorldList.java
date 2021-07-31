package de.SSC.CoreManager.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.SSC.CoreManager.Chat.Logger;
import de.SSC.CoreManager.Chat.MessageType;
import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.World.Exception.InvalidWorldNameException;

public class WorldList 
{
	private Map<Integer ,CMWorld> _worlds;
	private Map<String, Integer> _worldNameLookUp;
	private Logger _logger;
	
	public WorldList()
	{
		_worlds = new HashMap<Integer, CMWorld>();
		_worldNameLookUp = new HashMap<String, Integer>();
		
		_logger = Logger.Instance();
	}
	
	public boolean IsEmpty()
	{
		return _worlds.isEmpty();
	}
	
	public void Add(CMWorld cmWorld)
	{
		String worldName = cmWorld.Information.Name;
		int worldID = cmWorld.Information.ID;
		
		_logger.SendToConsole(Module.WorldSystem, MessageType.Info, "Adding &7world &6<&e" + worldName + "&6>&7 with ID &6<&e" + worldID + "&6> &7to world list.");
		
		_worlds.put(worldID, cmWorld);
		_worldNameLookUp.put(worldName.toLowerCase(),worldID);
	}
	
	public void Clear()
	{
		_logger.SendToConsole(Module.WorldSystem, MessageType.Warning, "&cResseting &7world list.");
		
		_worlds.clear();
		_worldNameLookUp.clear();
	}
	
	public void Remove(String worldName) throws InvalidWorldNameException
	{
		int worldID = GetWorldID(worldName);
		
		_logger.SendToConsole(Module.WorldSystem, MessageType.Warning, "&cRemoving &7world &4<&c" + worldName + "&4>&7.");
		
		_worlds.remove(worldID);
		_worldNameLookUp.remove(worldName);
	}
	
	public List<CMWorld> GetWorlds()
	{
		List<CMWorld> worlds = new ArrayList<CMWorld>();
		
		for(Map.Entry<Integer, CMWorld> entry : _worlds.entrySet()) 
		{
			CMWorld cmWorld = entry.getValue();

			worlds.add(cmWorld);
		}
		
		return worlds;
	}
		
	public void Swap(int replaceWorldID, CMWorld cmWorld) throws InvalidWorldNameException
	{
		boolean isNRValid = replaceWorldID != -1;
		boolean isWorldInList;
		
		if(cmWorld == null)
		{
			throw new NullPointerException("WorldList.Swap() > CMWorld is null.");
		}		
		
		if(isNRValid)
		{
			
		isWorldInList = _worlds.containsKey(replaceWorldID);
		
			
			if(isWorldInList)
			{
				_worlds.replace(replaceWorldID, cmWorld);
				//Remove(replaceWorldName);
				//Add(cmWorld);
			
			}
			else
			{
				throw new InvalidWorldNameException(replaceWorldID);
			}
			
	
		}
		else
		{
			isWorldInList = _worldNameLookUp.containsKey(cmWorld.Information.Name);
			
			if(isWorldInList)
			{
				int id = _worldNameLookUp.get(cmWorld.Information.Name);
				
				_worlds.replace(id, cmWorld);
				//Remove(replaceWorldName);
				//Add(cmWorld);
			
			}
			else
			{
				throw new InvalidWorldNameException(replaceWorldID);
			}
		}		
	}
	
	public int GetWorldID(String worldName) throws InvalidWorldNameException
	{		
		Integer worldID = _worldNameLookUp.get(worldName.toLowerCase());
				
		if(worldID == null)
		{
			throw new InvalidWorldNameException(worldName);
		}
		
		return worldID;
	}
	
	public CMWorld GetWorld(String worldName) throws InvalidWorldNameException
	{
		int worldID =  GetWorldID(worldName);
		
		return _worlds.get(worldID);
	}
	
	public CMWorld GetWorld(int worldID)
	{
		/*
		CMWorld cmWorld;
		
		if(worldID == -1)
		{
			cmWorld = GetWorldID();
		}
		else
		{
			cmWorld = 
		}
		*/		
		
		return _worlds.get(worldID);
	}
}
