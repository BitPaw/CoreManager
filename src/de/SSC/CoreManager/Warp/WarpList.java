package de.SSC.CoreManager.Warp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.SSC.CoreManager.Warp.Exception.WarpNotFoundException;

public class WarpList 
{
	private Map<String, Warp> _warps;
	
	public WarpList()
	{
		_warps = new HashMap<String, Warp>();
	}
	
	public int NumberOfWarps()
	{
		return _warps.size();
	}
	
	public void Clear()
	{
		_warps.clear();
	}
	
	public void Add(Warp warp)
	{
		_warps.put(warp.WarpName.toLowerCase(), warp);
	}
	
	public boolean DoesWarExist(String warpName)
	{		
		return _warps.containsKey(warpName.toLowerCase());
	}
	
	public void Remove(String warpName) throws WarpNotFoundException
	{
		boolean doesWarpExist = DoesWarExist(warpName);
		
		if(doesWarpExist)
		{
			_warps.remove(warpName);
		}
		else
		{
			throw new WarpNotFoundException(warpName);
		}
	}
	
	public Warp GetWarp(String warpName) throws WarpNotFoundException
	{
		warpName = warpName.toLowerCase();
		
		Warp cmWarp = _warps.get(warpName);
		
		if(cmWarp == null)
		{
			throw new WarpNotFoundException(warpName);
		}
						
		return cmWarp;
	}
	
	public List<Warp> GetWarps()
	{
		List<Warp> warps = new ArrayList<Warp>();
		
		for(Entry<String, Warp> entry : _warps.entrySet()) 		
		{
			Warp warp = entry.getValue();
			
			warps.add(warp);
		}
		
		return warps;
	}
}
