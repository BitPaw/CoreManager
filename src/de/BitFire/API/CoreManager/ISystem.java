package de.BitFire.API.CoreManager;

import org.bukkit.command.CommandSender;

import de.BitFire.API.CommandCredentials;
import de.BitFire.Chat.Module;
import de.BitFire.Core.Exception.FaultrySyntaxException;
import de.BitFire.Core.Exception.NoCommandException;
import de.BitFire.Core.Exception.NoHelpException;
import de.BitFire.Core.Exception.NotForConsoleException;
import de.BitFire.Core.Exception.SystemHasNoDataToPrintException;
import de.BitFire.Core.Exception.SystemHasNothingToReloadException;
import de.BitFire.Core.Exception.SystemNotActiveException;
import de.BitFire.Core.Exception.TooFewParameterException;
import de.BitFire.Core.Exception.TooManyParameterException;
import de.BitFire.Player.Exception.PlayerDirectionAmbiguous;
import de.BitFire.Player.Exception.PlayerNotFoundException;
import de.BitFire.PvP.Lobby.Exception.LobbyAlreadyClosedException;
import de.BitFire.PvP.Lobby.Exception.LobbyAlreadyOpenException;
import de.BitFire.PvP.Lobby.Exception.LobbyAlreadyRunningException;
import de.BitFire.PvP.Lobby.Exception.LobbyClosedException;
import de.BitFire.PvP.Lobby.Exception.LobbyFullException;
import de.BitFire.PvP.Lobby.Exception.PlayerAlreadyInLobbyException;
import de.BitFire.PvP.Lobby.Exception.PlayerNotInLobbyException;
import de.BitFire.Teleport.Exception.MissingPlayerPositionException;
import de.BitFire.World.Manipulation.Exception.NoRegionMarkedException;
import de.BitFire.World.Manipulation.Exception.PositionAMissingException;
import de.BitFire.World.Manipulation.Exception.PositionBMissingException;

/** 
 * Shall be used for every sub-system / sub-plugin that can than be registered in the core system.
 * 
 * @author BitPaw 
 */
public interface ISystem 
{	
	
	/**
	 * Get the module that describes this sub-System. 
	 * @return Module
	 */
	Module GetModule();
	
	/**
	 * Get the priority of this sub-system.
	 * @return Priority
	 */
	Priority GetPriority();
	
	/**
	 * Handle commands for this specific sub-system.
	 * @param commandCredentials
	 * @throws TooManyParameterException 
	 * @throws TooFewParameterException 
	 * @throws PlayerNotFacingDirectionException 
	 * @throws NotForConsoleException 
	 * @throws NoCommandException 
	 * @throws FaultrySyntaxException 
	 * @throws PositionBMissingException 
	 * @throws PositionAMissingException 
	 * @throws PlayerNotFoundException 
	 * @throws NoRegionMarkedException 
	 * @throws SystemHasNoDataToPrintException 
	 * @throws LobbyFullException 
	 * @throws PlayerAlreadyInLobbyException 
	 * @throws PlayerNotInLobbyException 
	 * @throws LobbyAlreadyRunningException 
	 * @throws LobbyClosedException 
	 * @throws LobbyAlreadyClosedException 
	 * @throws LobbyAlreadyOpenException 
	 * @throws MissingPlayerPositionException 
	 * @throws PlayerDirectionAmbiguous 
	 */
	void Command(final CommandCredentials commandCredentials) throws 	NotForConsoleException,																
																TooFewParameterException, 
																TooManyParameterException, NoCommandException, FaultrySyntaxException, PositionAMissingException, PositionBMissingException, NoRegionMarkedException, SystemHasNoDataToPrintException, PlayerAlreadyInLobbyException, LobbyFullException, PlayerNotInLobbyException, LobbyClosedException, LobbyAlreadyRunningException, LobbyAlreadyClosedException, LobbyAlreadyOpenException, MissingPlayerPositionException, PlayerDirectionAmbiguous;
	
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
	void PrintData(final CommandSender commandSender) throws SystemHasNoDataToPrintException;
	
	void PrintHelp(final CommandSender sender) throws NoHelpException;
}
