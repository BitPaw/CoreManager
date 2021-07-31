package de.SSC.CoreManager.System;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import de.SSC.CoreManager.Chat.Logger;
import de.SSC.CoreManager.Chat.MessageType;
import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.System.Exception.SystemHasNoDataToPrintException;
import de.SSC.CoreManager.System.Exception.SystemHasNothingToReloadException;
import de.SSC.CoreManager.System.Exception.SystemNotActiveException;

public class SystemList
{
	Logger _logger;
	public List<ISystem> _systemList;
	
	public SystemList() 
	{
		_systemList = new ArrayList<>();
		_logger = Logger.Instance();
	}
	
	public void Add(ISystem system)
	{
		_systemList.add(system);
	}
	
	public void Clear()
	{
		_systemList.clear();
	}
	
	public void LoadAllReferences()
	{
		final int maxAmount = _systemList.size();
		int currentAmount = 0;		
		String message = "&7Loading &breferences &7for every sub-system...";		
		
		_logger.SendToConsole(Module.System, MessageType.Loading, message);
		
			
		for(ISystem system : _systemList)
		{			
			String name;
			Module module = null;
			
			if(system instanceof BaseSystem)
			{
				module = ((BaseSystem)system).Information.GetSystemModule();
			}
			else if(system instanceof BaseRunnableSystem)
			{
				module = ((BaseRunnableSystem)system).Information.GetSystemModule();
			}
			else
			{
				// Exception
			}			
				
			name = module.toString();
			message = "&8... &6<&e" + name + "&6>&7. &6[&e" + (++currentAmount) + "&6/&e" + maxAmount + "&6]";
			
			_logger.SendToConsole(Module.System, MessageType.Loading, message);	
			
			system.LoadReferences();				
		}	
	}

	public void ReloadAll(final boolean firstRun)
	{
		final int maxAmount = _systemList.size();
		int currentAmount = 0;		
		String message = "&7Loading &bdata &7for every sub-system...";		
		
		_logger.SendToConsole(Module.System, MessageType.Loading, message);
		
		for(ISystem system : _systemList)
		{				
			Module module = null; 
			String name;
			
			if(system instanceof BaseSystem)
			{
				module = ((BaseSystem)system).Information.GetSystemModule();
			}
			else if(system instanceof BaseRunnableSystem)
			{
				module = ((BaseRunnableSystem)system).Information.GetSystemModule();
			}
			else
			{
				// Exception
			}
			
			name = module.toString();
			message = "&8... &6<&e" + name + "&6>&7. &6[&e" + (++currentAmount) + "&6/&e" + maxAmount + "&6]";
			
			_logger.SendToConsole(Module.System, MessageType.Loading, message);
			
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
	}

	public void ListAll(CommandSender sender) 
	{
		for(ISystem system : _systemList)
		{
			try 
			{
				system.PrintData(sender);
			} 
			catch (SystemHasNoDataToPrintException e) 
			{
				final String message = "&cNo data&7 to show.";
				
				_logger.SendToSender(Module.System, MessageType.Error, sender, message);
			}
		}		
	}
	
	public void GetStatusFromAll(CommandSender sender)
	{
		for(ISystem system : _systemList)
		{			
			Module module = null;
			SystemPriority priority = null;
			SystemState systemState = null;
			String moduleName;
			String systemStateName;
			String message = "";
			String moduleTag = "";
			String stateTag = "";
			String priorityTag = "";	
			
			if(system instanceof BaseSystem)
			{
				module = ((BaseSystem)system).Information.GetSystemModule();
				priority = ((BaseSystem)system).Information.GetPriority();
				systemState = ((BaseSystem)system).Information.GetSystemState();
			}
			else if(system instanceof BaseRunnableSystem)
			{
				module = ((BaseRunnableSystem)system).Information.GetSystemModule();
				priority = ((BaseRunnableSystem)system).Information.GetPriority();
				systemState = ((BaseRunnableSystem)system).Information.GetSystemState();
			}
			else
			{
				// Exception
			}
			
	
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
			 
			_logger.SendToSender(Module.System, MessageType.Info, sender, message);			
		}
	}
}