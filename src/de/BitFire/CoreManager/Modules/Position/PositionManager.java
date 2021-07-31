package de.BitFire.CoreManager.Modules.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.BitFire.CoreManager.Cache.WorldBlockPositionCache;
import de.BitFire.CoreManager.Cache.WorldPositionCache;
import de.BitFire.CoreManager.Cache.WorldViewPositionCache;
import de.BitFire.CoreManager.Modules.DataBase.DataBaseManager;
import de.BitFire.CoreManager.Modules.World.Type.World;
import de.BitFire.CoreManager.Modules.World.Type.WorldBlockPosition;
import de.BitFire.CoreManager.Modules.World.Type.WorldViewPosition;
import de.BitFire.Math.Geometry.PositionBlock;

public class PositionManager 
{			
	private static WorldPositionCache _worldPositionCache;
	private static WorldBlockPositionCache _worldBlockPositionCache;
	private static WorldViewPositionCache _worldViewPositionCache;
	
	private static Map<Integer, WorldViewPosition> _playerLastPositionCache;
	private static Map<Integer, WorldBlockPosition> _playerFirstBlockPositionCache;
	private static Map<Integer, WorldBlockPosition> _playerSecondBlockPositionCache;
	
	static 
	{				
		_worldPositionCache = new 	WorldPositionCache();
		_worldBlockPositionCache = new WorldBlockPositionCache();
		_worldViewPositionCache = new WorldViewPositionCache();	
				
		_playerLastPositionCache = new HashMap<Integer, WorldViewPosition>();
		_playerFirstBlockPositionCache = new HashMap<Integer, WorldBlockPosition>();
		_playerSecondBlockPositionCache = new HashMap<Integer, WorldBlockPosition>();
	}
	
	/*
	public static WorldBlockPosition IsRegisteredBlock(final int worldID, final PositionBlock blockPosition)
	{
		return _worldBlockPositionCache.GetPosition(worldID, blockPosition);
	}
	*/
	
	public static void LoadAllPositions()
	{		
		// WorldBlockPositions
		{
			WorldBlockPosition worldBlockPosition = new WorldBlockPosition();
			List<WorldBlockPosition> blockPositionList = DataBaseManager.LoadObject(worldBlockPosition);
						
			for(WorldBlockPosition position : blockPositionList)
			{				
				_worldBlockPositionCache.CachePosition(position);
			}	
		}
		
		// Block Positons
		{
				
		}		
	}
	
	public static void SetPlayerLastPosition(final int playerID, final WorldViewPosition worldViewPosition)
	{
		boolean hasRegisteredAlready = _playerLastPositionCache.containsKey(playerID);
		
		if(hasRegisteredAlready)
		{
			_playerLastPositionCache.replace(playerID, worldViewPosition);
		}
		else
		{
			_playerLastPositionCache.put(playerID, worldViewPosition);
		}	
	}
	
	public static void SetFirstPosition(final int playerID, final WorldBlockPosition position)
	{
		boolean hasRegisteredAlready = _playerFirstBlockPositionCache.containsKey(playerID);
		
		if(hasRegisteredAlready)
		{
			_playerFirstBlockPositionCache.replace(playerID, position);
		}
		else
		{
			_playerFirstBlockPositionCache.put(playerID, position);
		}	
	}
	
	public static void SetSecondPosition(final int playerID, final WorldBlockPosition position)
	{
		boolean hasRegisteredAlready = _playerSecondBlockPositionCache.containsKey(playerID);
		
		if(hasRegisteredAlready)
		{
			_playerSecondBlockPositionCache.replace(playerID, position);
		}
		else
		{
			_playerSecondBlockPositionCache.put(playerID, position);
		}	
	}

	public static WorldBlockPosition GetWorldBlockPosition(final int blockPositionID) 
	{
		return _worldBlockPositionCache.GetPosition(blockPositionID);
	}
	
	public static String PrintPositions()
	{
		String message = "===[Positions]===[?]\n";
		
		for(int i = 0; true ; i++)
		{
			WorldBlockPosition position = _worldBlockPositionCache.GetPosition(i);
					
					if(position == null)
					{
						break;
					}
					message += 	" ID:" + position.ID + " X:" + position.X + " Y:" + position.Y + " Z:" + position.Z + "\n";
					
		}
		
		message += "===================";
		
		return message;
	}
}