package de.BitFire.CoreManager.Cache;

import java.util.HashMap;
import java.util.Map;

import de.BitFire.CoreManager.Modules.World.Type.WorldPosition;

public class WorldPositionCache 
{
	private Map<Integer, WorldPosition> _worldPositions;
		
	public WorldPositionCache()
	{
		_worldPositions = new HashMap<Integer, WorldPosition>();
	}
	
	public void AddToCache(final WorldPosition worldPosition)
	{
		_worldPositions.put(worldPosition.ID, worldPosition);
	}
}
