package de.SSC.DigitalClock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ClockList 
{
	private Map<Integer, Clock> _clockList;
	private Map<String, Integer> _clockListIDLookup;
	
	public ClockList()
	{
		_clockList = new HashMap<Integer, Clock>();
		_clockListIDLookup = new HashMap<String, Integer>();;
	}
	
	public int Size()
	{
		return _clockList.size();
	}
	
	public void Clear()
	{
		_clockList.clear();
		_clockListIDLookup.clear();
	}
	
	public void Add(Clock clock)
	{
		_clockList.put(clock.ID, clock);
		_clockListIDLookup.put(clock.Name.toLowerCase(), clock.ID);
	}
	
	public void Remove()
	{
		
	}
	
	public List<Clock> GetClocks()
	{
		List<Clock> clock = new ArrayList<Clock>();
		
		for(Entry<Integer, Clock> entry : _clockList.entrySet()) 		
		{
			Clock cmWarp = entry.getValue();
			
			clock.add(cmWarp);
		}
		
		return clock;
	}
}
