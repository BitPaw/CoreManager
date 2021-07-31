package de.BitFire.CoreManager.Cache;

import java.util.HashMap;
import java.util.Map;

import de.BitFire.CoreManager.Modules.World.Type.WorldBlockPosition;

public class WorldBlockPositionCache 
{
	private Map<Integer, WorldBlockPosition> _worldPositions;

	public WorldBlockPositionCache()
	{
		_worldPositions = new HashMap<Integer, WorldBlockPosition>();
	}
	
	public void  CachePosition(final WorldBlockPosition worldBlockPosition)
	{
		final int signID = worldBlockPosition.ID;
		
		_worldPositions.put(signID, worldBlockPosition);	
	}
	
	public WorldBlockPosition GetPosition(final int id)
	{
		return _worldPositions.get(id);
	}
}