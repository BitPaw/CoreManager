package de.SSC.CoreManager.System;

import org.bukkit.command.CommandSender;

import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.System.Exception.SystemHasNoDataToPrintException;
import de.SSC.CoreManager.System.Exception.SystemHasNothingToReloadException;
import de.SSC.CoreManager.System.Exception.SystemNotActiveException;

public abstract class BaseSystem 
{
	public ModulInformation Information;
	
	protected BaseSystem(Module module, SystemState state, SystemPriority priority)
	{
		Information = new ModulInformation(module, state, priority);
	}
	
	public void LoadReferences()
	{
		// Do nothing
	}
	
	public void PrintData(CommandSender sender) throws SystemHasNoDataToPrintException 
	{
		throw new SystemHasNoDataToPrintException();
	}	
	
	public void Reload(final boolean firstRun) throws SystemHasNothingToReloadException, SystemNotActiveException 
	{
		throw new SystemHasNothingToReloadException();
	}
}