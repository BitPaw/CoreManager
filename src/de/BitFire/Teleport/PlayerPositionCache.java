package de.BitFire.Teleport;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;

import de.BitFire.Teleport.Exception.MissingPlayerPositionException;

public class PlayerPositionCache 
{
	private Map<UUID, Location> _positionMap;
	
	public PlayerPositionCache()
	{
		_positionMap = new HashMap<UUID, Location>();
	}
	
	public void Stack(final UUID playerUUID, final Location location)
	{
		final boolean exists = _positionMap.containsKey(playerUUID);
		
		if(exists)
		{
			_positionMap.replace(playerUUID, location);
		}
		else
		{
			_positionMap.put(playerUUID, location);
		}	
	}
	
	public Location Pull(final UUID playerUUID) throws MissingPlayerPositionException
	{
		final Location location = _positionMap.get(playerUUID);
		
		if(location == null)
		{
			throw new MissingPlayerPositionException();
		}
		
		_positionMap.remove(playerUUID);		
		
		return location;
	}
}
