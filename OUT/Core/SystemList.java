package de.BitFire.Core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Core.Exception.SystemHasNoDataToPrintException;
import de.BitFire.Core.Exception.SystemHasNothingToReloadException;
import de.BitFire.Core.Exception.SystemNotActiveException;

public class SystemList
{
	private int _currentSystems;
	
	private Logger _logger;
	public List<ISystem> _systemListLow;
	public List<ISystem> _systemListMid;
	public List<ISystem> _systemListHigh;
	public List<ISystem> _systemListAll;
	
	public SystemList() 
	{
		_systemListLow = new ArrayList<>();
		_systemListMid = new ArrayList<>();
		_systemListHigh = new ArrayList<>();
		_systemListAll = new ArrayList<>();
		
		Clear();
	}
	
	public void Add(ISystem system)
	{
		_systemListAll.add(system);
		
		switch(system.GetPriority())
		{
		case Essential:
			_systemListHigh.add(system);
			break;
			
		case High:
			_systemListMid.add(system);
			break;
			
		case Low:			
		case Undefined:
			_systemListLow.add(system);
			break;
			
		default:
			break;
		
		}
		
		_currentSystems = _systemListAll.size();
	}
	
	public void Clear()
	{		
		_systemListLow.clear();
		_systemListMid.clear();
		_systemListHigh.clear();
		_systemListAll.clear();
		
		_currentSystems = 0;
	}
	
	public void LoadAllReferences()
	{
		_logger = Logger.Instance();
		int currentAmount = 0;		
		String message = "&7Loading &breferences &7for every sub-system...";		
		
		_logger.SendToConsole(Module.System, MessageType.Loading, message);
					
		for(ISystem system : _systemListHigh)
		{						
			LoadReferenceForSystem(system, ++currentAmount);	
		}	
		
		for(ISystem system : _systemListMid)
		{			
			LoadReferenceForSystem(system, ++currentAmount);	
		}	
		
		for(ISystem system : _systemListLow)
		{			
			LoadReferenceForSystem(system, ++currentAmount);	
		}	
	}

	public void ReloadAll(final boolean firstRun)
	{
		_logger = Logger.Instance();
		int currentAmount = 0;		
		String message = "&7Loading &bdata &7for every sub-system...";		
		
		_logger.SendToConsole(Module.System, MessageType.Loading, message);
		
		for(ISystem system : _systemListHigh)
		{						
			LoadDataForSystem(system, ++currentAmount, firstRun);	
		}	
		
		for(ISystem system : _systemListMid)
		{			
			LoadDataForSystem(system, ++currentAmount, firstRun);	
		}	
		
		for(ISystem system : _systemListLow)
		{			
			LoadDataForSystem(system, ++currentAmount, firstRun);	
		}	
	}

	public void ListAll(CommandSender sender) 
	{
		_logger = Logger.Instance();
		
		for(ISystem system : _systemListAll)
		{
			try 
			{
				system.PrintData(sender);
			} 
			catch (SystemHasNoDataToPrintException e) 
			{
				final String message = "&cNo data&7 to show.";
				final Module module = system.GetModule(); 
				
				_logger.SendToSender(module, MessageType.Error, sender, message);
			}
		}		
	}
	
	public void GetStatusFromAll(CommandSender sender)
	{
		_logger = Logger.Instance();
		
		for(ISystem system : _systemListHigh)
		{						
			PrintStatusFromSystem(system);	
		}	
		
		for(ISystem system : _systemListMid)
		{			
			PrintStatusFromSystem(system);	
		}	
		
		for(ISystem system : _systemListLow)
		{			
			PrintStatusFromSystem(system);	
		}	
	}
	
	private void LoadDataForSystem(final ISystem system, final int ID, final boolean firstRun)
	{
		final Module module = system.GetModule(); 
		final String name = module.toString();		
		String message = "&8... &6<&e" + name + "&6>&7. &6[&e" + ID + "&6/&e" + _currentSystems + "&6]";
		
		_logger.SendToConsole(module, MessageType.Loading, message);
		
		try 
		{
			system.Reload(firstRun);
		} 
		catch(SystemNotActiveException systemNotActiveException) 
		{				
			message = "&8...&6<&e" + name + "&6> &eskipped&7! &8It is not active.";
			
			_logger.SendToConsole(module, MessageType.Warning, message);
			
		} 
		catch (SystemHasNothingToReloadException e) 
		{
			// Do nothing
		}			
	}
	
	private void LoadReferenceForSystem(final ISystem system, final int ID)
	{
		final Module module = system.GetModule();
		final String name = module.toString();;
		String message;
		
		message = "&8... &6<&e" + name + "&6>&7. &6[&e" + ID + "&6/&e" + _currentSystems + "&6]";
		
		_logger.SendToConsole(Module.System, MessageType.Loading, message);	
		
		system.LoadReferences();
	}
	
	private void PrintStatusFromSystem(ISystem system)
	{
		final Module module = system.GetModule();
		Priority priority = null;
		SystemState systemState = null;
		String moduleName;
		String systemStateName;
		String message = "";
		String moduleTag = "";
		String stateTag = "";
		String priorityTag = "";	
		
	
			priority = ((BaseSystem)system).Information.GetPriority();
			systemState = ((BaseSystem)system).Information.GetSystemState();

		

		moduleName = module.toString() ;
		systemStateName = systemState.toString();
		
			
		
		switch(priority)
		{
		case Essential:
			priorityTag = "&cIII";
			break;
			
		case High:
			priorityTag = "&eII";
			break;
			
		case Low:
			priorityTag = "&aI";
			break;
			
		case Undefined:
			priorityTag = "&b?";
			break;
			
		default:
			// nothing
			break;			
		
		}			
		
		switch(systemState)
		{
		case Active:
			moduleTag = "&d" + moduleName;
			stateTag = "&a" + systemStateName;
			break;
			
		case FailedToLoad:
			moduleTag = "&c" + moduleName;
			stateTag = "&4" + systemStateName;
			break;
			
		case Inactive:
			moduleTag = "&7" + moduleName;
			stateTag = "&c" + systemStateName;
			break;
			
		default:
			// nothing
			break;
		
		}			
		
		message = "&8[" + moduleTag + "&8][" + stateTag + "&8][" + priorityTag + "&8]";
		 
		_logger.SendToConsole(Module.System, MessageType.Info, message);	
	}
}