package de.SSC;

import java.util.ArrayList;
import java.util.List;

import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.System.BaseRunnableSystem;
import de.SSC.CoreManager.System.IRunnableSystem;
import de.SSC.CoreManager.System.ISystem;
import de.SSC.CoreManager.System.SystemPriority;
import de.SSC.CoreManager.System.SystemState;
import de.SSC.CoreManager.System.Exception.SystemNotActiveException;

public class CoreRunnable extends BaseRunnableSystem implements ISystem
{
	public static CoreRunnable _instance;
	private List<IRunnableSystem> _runnableSystemList;
	
	//private Logger _logger;
	
	private CoreRunnable() 
	{
		super(Module.CoreRunnable, SystemState.Active, SystemPriority.Essential);
		_instance = this;
		
		_runnableSystemList = new ArrayList<IRunnableSystem>();
		
		//_logger = Logger.Instance();
	}
	
	public static CoreRunnable Instance()
	{
		return _instance == null ? new CoreRunnable() : _instance;
	}
	
	@Override
	public void run() 
	{
		for(IRunnableSystem runnableSystem : _runnableSystemList)
		{
			try 
			{
				runnableSystem.Update();
			} 
			catch (SystemNotActiveException e)
			{
				// Do nothing, ignore.
			}
		}
	}
	
	public void RegisterRunnable(IRunnableSystem runnableSystem)
	{
		_runnableSystemList.add(runnableSystem);
	}
}