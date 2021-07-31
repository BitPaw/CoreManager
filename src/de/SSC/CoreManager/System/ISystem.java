package de.SSC.CoreManager.System;

import org.bukkit.command.CommandSender;

import de.SSC.CoreManager.System.Exception.SystemHasNoDataToPrintException;
import de.SSC.CoreManager.System.Exception.SystemHasNothingToReloadException;
import de.SSC.CoreManager.System.Exception.SystemNotActiveException;

/** 
 * Shall be used for every sub-system / sub-plugin that can than be registered in the core system.
 * 
 * @author BitPaw 
 */
public interface ISystem 
{	
	/**
	 * Load all references that are needed. 
	 */
	void LoadReferences();
	
	/**
	 * Reload all data from the database (if needed).
	 * @param firstRun
	 * @throws SystemNotActiveException 
	 */
	void Reload(final boolean isFirstRun) throws SystemNotActiveException, SystemHasNothingToReloadException;
	
	/**
	 * Print data that is contained in this sub-system.
	 * @param sender
	 */
	void PrintData(CommandSender commandSender) throws SystemHasNoDataToPrintException; 
}
