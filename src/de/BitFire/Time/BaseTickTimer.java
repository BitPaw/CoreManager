package de.BitFire.Time;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseTickTimer 
{
	private List<IUpdateable> _iUpdateableList;
	protected final int _tickTimeInMS;	
	
	public BaseTickTimer(final int tickTimeInMS)
	{
		_tickTimeInMS = tickTimeInMS;
		_iUpdateableList = new ArrayList<IUpdateable>();
	}
	
	public void run() 
	{
		for(final IUpdateable updateable : _iUpdateableList)
		{
			updateable.OnTickUpdate();
		}
	}
	
	public int GetTickIntervalInMS()
	{
		return _tickTimeInMS;
	}
	
	public void RegisterSystem(final IUpdateable updateable)
	{
		_iUpdateableList.add(updateable);
	}
	
	public void ReleaseSystem(final IUpdateable updateable)
	{
		_iUpdateableList.remove(updateable);
	}
	
	public void ReleaseAll()
	{
		_iUpdateableList.clear();
	}
}
