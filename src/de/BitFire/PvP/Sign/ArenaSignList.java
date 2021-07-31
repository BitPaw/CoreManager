package de.BitFire.PvP.Sign;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.BitFire.Geometry.Point;

public class ArenaSignList 
{
	private Map<Integer, ArenaSign> _signsMap;
	private Map<Point, Integer> _signsLookUpTable;
	
	public ArenaSignList()
	{
		_signsMap = new HashMap<Integer, ArenaSign>();
		_signsLookUpTable = new HashMap<Point, Integer>();
	}
	
	public ArenaSign GetAreaSign(final int id)
	{		
		return  _signsMap.get(id);
	}
	
	public ArenaSign GetAreaSign(final Point point)
	{
		final Integer id = _signsLookUpTable.get(point);
		
		if(id != null)
		{
			return GetAreaSign(id);
		}
		
		return null;
	}
		
	public void Add(final List<ArenaSign> signs)
	{
		for(ArenaSign arenaSign : signs)
		{
			Add(arenaSign);
		}
	}
	
	public void Add(final ArenaSign sign)
	{
		_signsMap.put(sign.ID, sign);
		_signsLookUpTable.put(sign.Position, sign.ID);
	}
	
	public void Remove()
	{
		
	}
	
	public String PrintData() 
	{
		String message = "&6=====[&eArenas&6]=====";
		
		for (Map.Entry<Integer, ArenaSign> entry : _signsMap.entrySet()) 
		{
			final Integer id = entry.getKey(); 
			final ArenaSign arenaSign = entry.getValue();
			
			message += "\n&eID&6:&r" +  id + " " + arenaSign.Position.toString();
		}
		
		message += "&6==================";
		
		return message;
	}
}