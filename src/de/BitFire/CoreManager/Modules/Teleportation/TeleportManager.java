package de.BitFire.CoreManager.Modules.Teleportation;

import java.util.UUID;

import org.bukkit.Location;

import de.BitFire.CoreManager.Modules.Player.Player;
import de.BitFire.CoreManager.Modules.Player.PlayerManager;
import de.BitFire.CoreManager.Modules.Player.PlayerNotFoundException;
import de.BitFire.CoreManager.Modules.World.WorldManager;
import de.BitFire.CoreManager.Modules.World.Exception.WorldNotFoundException;
import de.BitFire.CoreManager.System.CommandData;
import de.BitFire.CoreManager.System.CommandNotForConsoleException;
import de.BitFire.CoreManager.System.TooFewParametersException;
import de.BitFire.CoreManager.System.TooManyParametersException;

public class TeleportManager 
{	
	public static void TeleportToLocation(final org.bukkit.entity.Player player, final Location location)
	{
		player.teleport(location);
	}
	
	public static void WarpCommand(final CommandData commandData) throws TooFewParametersException, TooManyParametersException, CommandNotForConsoleException 
	{
		final int parameterLengh = commandData.ParameterLengh();
		
		switch(parameterLengh)
		{
		case 0: // /warp
			throw new TooFewParametersException();
			
		case 1: // /warp ???
			if(commandData.IsSenderPlayer())
			{
				 
				//Warp			
			}	
			else
			{
				throw new CommandNotForConsoleException();
			}
		case 2: // warp source target
					
			default:
				throw new TooManyParametersException();		

		}
		
	}

	
	public static void TeleportCommand(CommandData commandData) throws TooFewParametersException, CommandNotForConsoleException, TooManyParametersException, PlayerNotFoundException 
	{
		final int parameterLengh = commandData.ParameterLengh();
		
		switch(parameterLengh)
		{
		case 0: // /tp
			throw new TooFewParametersException();
			
		case 1: // /tp 
			if(commandData.IsSenderPlayer())
			{				 
				String targetedPlayerName = commandData.Parameter[0];
				
				final org.bukkit.entity.Player targetPlayer = PlayerManager.GetBukkitPlayer(targetedPlayerName);
				final org.bukkit.entity.Player sourcePlayer = (org.bukkit.entity.Player)commandData.Sender;
								
				if(targetPlayer == null)
				{
					throw new PlayerNotFoundException(targetedPlayerName);
				}
				else
				{
					TeleportToLocation(sourcePlayer, targetPlayer.getLocation());
				}				
			}	
			else
			{
				throw new CommandNotForConsoleException();
			}
			break;
			
		case 2: // warp source target
			String sourcePlayerName = commandData.Parameter[0];
			String targetedPlayerName = commandData.Parameter[1];
			
			final org.bukkit.entity.Player sourcePlayer = PlayerManager.GetBukkitPlayer(sourcePlayerName);
			final org.bukkit.entity.Player targetPlayer = PlayerManager.GetBukkitPlayer(targetedPlayerName);
			
			// Check
			{
				if(sourcePlayer == null)
				{
					throw new PlayerNotFoundException(sourcePlayerName);
				}
				
				if(targetPlayer == null)
				{
					throw new PlayerNotFoundException(targetedPlayerName);
				}				
			}	
		
			TeleportToLocation(sourcePlayer, targetPlayer.getLocation());							
			
			break;
			
			
			default:
				throw new TooManyParametersException();		

		}		
	}

	public static void TeleportToWorldCommand(final CommandData commandData) throws TooFewParametersException, TooManyParametersException, CommandNotForConsoleException, WorldNotFoundException 
	{
		final int parameterLengh = commandData.ParameterLengh();
		
		switch(parameterLengh)
		{
		case 0: // /tpw
			throw new TooFewParametersException();
			
		case 1: // /tpw ???
			if(commandData.IsSenderPlayer())
			{
				 final String worldName = commandData.Parameter[0];
				 final org.bukkit.World bukkitWorld = WorldManager.GetBukkitWorld(worldName);
				 final org.bukkit.entity.Player bukkitPlayer = (org.bukkit.entity.Player)commandData.Sender;
				 
				 if(bukkitWorld == null)
				 {
					 throw new WorldNotFoundException(worldName);
				 }			 
				 
				 TeleportToLocation(bukkitPlayer, bukkitWorld.getSpawnLocation());
			}	
			else
			{
				throw new CommandNotForConsoleException();
			}
			break;
			
		case 2: //tpw source target
					
			default:
				throw new TooManyParametersException();		

		}
		
	}

	public static void TeleportToLocationCommand(CommandData commandData) {
		// TODO Auto-generated method stub
		
	}
}