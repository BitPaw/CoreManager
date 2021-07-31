package de.SSC.CoreManager.Systems.Warp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class WarpList 
{
	private Map<String, CMWarp> _warps;
	
	public WarpList()
	{
		_warps = new HashMap<String, CMWarp>();
	}
	
	public int NumberOfWarps()
	{
		return _warps.size();
	}
	
	public void Clear()
	{
		_warps.clear();
	}
	
	public void Add(CMWarp cmWarp)
	{
		_warps.put(cmWarp.WarpName, cmWarp);
	}
	
	public void Remove(String warpName)
	{
		_warps.remove(warpName);
	}
	
	public CMWarp GetWarp(String warpName)
	{
		return _warps.get(warpName);
	}
	
	public List<CMWarp> GetWarps()
	{
		List<CMWarp> warps = new ArrayList<CMWarp>();
		
		for(Entry<String, CMWarp> entry : _warps.entrySet()) 		
		{
			CMWarp cmWarp = entry.getValue();
			
			warps.add(cmWarp);
		}
		
		return warps;
	}
}
