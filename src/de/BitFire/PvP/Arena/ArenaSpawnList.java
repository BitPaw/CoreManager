package de.BitFire.PvP.Arena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Location;

import de.BitFire.Geometry.Point;
import de.BitFire.Teleport.Exception.MissingPlayerPositionException;

public class ArenaSpawnList 
{
	private Map<Integer, Point> _spawns;
	private List<Integer> _freeSpaces;
	private Map<UUID, Integer> _playerUUIDToSpawnIDLookUp;
	
	public ArenaSpawnList(final int maxPlayers)
	{
		_spawns = new HashMap<Integer, Point>(maxPlayers);
		_playerUUIDToSpawnIDLookUp = new HashMap<UUID, Integer>();
		_freeSpaces = new ArrayList<Integer>(maxPlayers);
	}
	
	public int GetTotalAmountOfSpawns()
	{
		return _spawns.size();
	}
	
	public int GetAmountOfFreeSpawns()
	{
		return _freeSpaces.size();
	}
	
	public void AddSpawn(final Point point)
	{		
		final int index = GetTotalAmountOfSpawns();
		
		_spawns.put(index, point);
		_freeSpaces.add(index);
	}
	
	public Point GetRandomeSpawn(final UUID playerUUID) throws MissingPlayerPositionException
	{		
		final int failSaveCap = 100;
		final int freeSpawns = GetAmountOfFreeSpawns();
		
		System.out.print("\n\nThere are <" + freeSpawns + "> Free spawns\n\n");
		
		int id = -1; 
		int failSaveCounter = 0;
		int freeSpaceIndex = -1;
		Point point = null;
		
		if(freeSpawns > 0)
		{
			while(point == null)
			{
				final Random random = new Random();
				freeSpaceIndex = random.nextInt(freeSpawns);	
				System.out.print("Randome Position ID:" + freeSpaceIndex);
				
				final Integer rawID = _freeSpaces.get(freeSpaceIndex);
								
				if(rawID == null)
				{
					continue;
				}
				
				id = rawID;			
				point = _spawns.get(id);	
				
				if(failSaveCounter++ == failSaveCap)
				{
					System.out.print("\n\nRandome search took way to long!!!\n\n");
					
					break;
				}
			}
			
			if(point != null)
			{
				_playerUUIDToSpawnIDLookUp.put(playerUUID, id);
				_freeSpaces.remove(freeSpaceIndex);
			}			
		}	
		else
		{
			throw new MissingPlayerPositionException();
		}
			
		return point;
	}
	
	public void FreeAllSpawns()
	{
		for(final Integer id :_spawns.keySet())
		{
			_freeSpaces.add(id);
		}
	}

	public void Free(final int index) 
	{
		_freeSpaces.add(index);		
	}

	@Deprecated
	public void Free(final Location location) 
	{
		final Point searchPoint = new Point(location);
		int index = -1;
		
		for(Map.Entry<Integer, Point> entry : _spawns.entrySet()) 
		{
			Integer integer = entry.getKey();
			Point currentPoint = entry.getValue();
			
			if(searchPoint.equals(currentPoint))
			{
				index = integer;
				break;
			}
		}
		
		if(index != -1)
		{
			Free(index);
		}
		else
		{
			System.out.print("\n\nWarning! Freeing Spawn location failed!!!\n");
			System.out.print("\n\n There are <" + _freeSpaces.size() + "> things\n");
		}
	}

	public void Free(final UUID playerUUID) 
	{
		final int index = _playerUUIDToSpawnIDLookUp.get(playerUUID);
		
		Free(index);
		
		_playerUUIDToSpawnIDLookUp.remove(playerUUID);
	}
}
