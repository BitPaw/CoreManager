package de.BitFire.World.Manipulation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.BitFire.Geometry.Cube;

public class WordEditUserInfromationList 
{
	private Map<UUID, Cube> _positionLookUp; 
	
	public WordEditUserInfromationList()
	{
		_positionLookUp = new HashMap<UUID, Cube>();
	}

	public Cube GetCube(UUID playerUUID) 
	{
		return _positionLookUp.get(playerUUID);
	}

	public void Update(UUID playerUUID, Cube cube)
	{
		_positionLookUp.replace(playerUUID, cube);		
	}

	public void Add(UUID playerUUID, Cube cube) 
	{
		_positionLookUp.put(playerUUID, cube);		
	}

	public boolean IsRegistered(UUID playerUUID) 
	{
		return _positionLookUp.containsKey(playerUUID);
	}
}
