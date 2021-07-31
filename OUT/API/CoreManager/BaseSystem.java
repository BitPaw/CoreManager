package de.BitFire.API.CoreManager;

import org.bukkit.command.CommandSender;

import de.BitFire.API.CommandCredentials;
import de.BitFire.Chat.Module;
import de.BitFire.Core.CoreListener;
import de.BitFire.Core.ModulInformation;
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

public abstract class BaseSystem implements ISystem
{
	public ModulInformation Information;
	
	protected BaseSystem(Module module, SystemState state, Priority priority)
	{
		Information = new ModulInformation(module, state, priority);
		CoreListener.RegisterSystem(this);
	}
	
	public Module GetModule()
	{
		return Information.GetSystemModule();
	}
	
	public Priority GetPriority()
	{
		return Information.GetPriority();
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
	
	public void Command(final CommandCredentials commandCredentials) throws NotForConsoleException,
																			TooFewParameterException, 
																			TooManyParameterException, 
																			NoCommandException, 
																			FaultrySyntaxException, 
																			PositionAMissingException, 
																			PositionBMissingException,
																			NoRegionMarkedException, 
																			SystemHasNoDataToPrintException, 
																			PlayerAlreadyInLobbyException,
																			LobbyFullException, 
																			PlayerNotInLobbyException, 
																			LobbyClosedException,
																			LobbyAlreadyRunningException,
																			LobbyAlreadyClosedException, 
																			LobbyAlreadyOpenException, 
																			MissingPlayerPositionException, PlayerDirectionAmbiguous
	{
		throw new NoCommandException();
	}
	
	public void PrintHelp(final CommandSender sender) throws NoHelpException
	{
		throw new NoHelpException();
	}
}