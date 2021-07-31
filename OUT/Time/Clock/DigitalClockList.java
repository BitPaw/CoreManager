package de.BitFire.Time.Clock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DigitalClockList 
{
	private Map<Integer, DigitalClock> _clockList;
	private Map<String, Integer> _clockListIDLookup;
	
	public DigitalClockList()
	{
		_clockList = new HashMap<Integer, DigitalClock>();
		_clockListIDLookup = new HashMap<String, Integer>();
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
	
	public void Add(DigitalClock clock)
	{
		_clockList.put(clock.ID, clock);
		_clockListIDLookup.put(clock.Name.toLowerCase(), clock.ID);
	}
	
	public void Remove()
	{
		
	}
	
	public DigitalClock GetClock(String clockName) 
	{
		DigitalClock clock;
		Integer id;
		
		clockName = clockName.toLowerCase();	
		
		id = _clockListIDLookup.get(clockName);
		
		if(id == null)
		{
			// YEET
			System.out.print("WARNING!!!! NULL YEETEHEGT");
		}		
	
		clock = _clockList.get(id);		

		return clock;
	}
	
	public List<DigitalClock> GetClocks()
	{
		List<DigitalClock> clock = new ArrayList<DigitalClock>();
		
		for(Entry<Integer, DigitalClock> entry : _clockList.entrySet()) 		
		{
			DigitalClock digitalClock = entry.getValue();
			
			clock.add(digitalClock);
		}
		
		return clock;
	}
}