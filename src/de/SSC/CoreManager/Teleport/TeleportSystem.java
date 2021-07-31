package de.SSC.CoreManager.Teleport;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Chat.Logger;
import de.SSC.CoreManager.Chat.MessageTags;
import de.SSC.CoreManager.Chat.MessageType;
import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Player.CMPlayer;
import de.SSC.CoreManager.Player.PlayerSystem;
import de.SSC.CoreManager.Player.Exception.InvalidPlayerNameException;
import de.SSC.CoreManager.Player.Exception.InvalidPlayerUUID;
import de.SSC.CoreManager.Player.Exception.PlayerNotFoundException;
import de.SSC.CoreManager.Player.Exception.PlayerOfflineException;
import de.SSC.CoreManager.System.BaseSystem;
import de.SSC.CoreManager.System.ISystem;
import de.SSC.CoreManager.System.SystemPriority;
import de.SSC.CoreManager.System.SystemState;
import de.SSC.CoreManager.System.Exception.NotForConsoleException;
import de.SSC.CoreManager.System.Exception.TooManyParameterException;
import de.SSC.CoreManager.World.CMWorld;
import de.SSC.CoreManager.World.WorldSystem;
import de.SSC.CoreManager.World.Exception.InvalidWorldNameException;

public class TeleportSystem extends BaseSystem implements ISystem
{
	private static TeleportSystem _instance;
	private Logger _logger;
	private Config _config;
	private PlayerSystem _playerSystem;
	private MessageTags _messageTags;
	private WorldSystem _worldSystem;	
	
	private TeleportSystem()
	{
		super(Module.Teleport, SystemState.Active, SystemPriority.High);
		_instance = this;	
	}
	
	public static TeleportSystem Instance()
	{
		return _instance == null ? new TeleportSystem() : _instance;
	}

	@Override
	public void LoadReferences() 
	{
		_logger = Logger.Instance();
		_config = Config.Instance();
		_playerSystem = PlayerSystem.Instance();
		_messageTags = MessageTags.Instance();
		_worldSystem = WorldSystem.Instance();			
	}

	public void TeleportToPlayer(CommandSender sender, String[] parameter) throws InvalidPlayerNameException, PlayerNotFoundException, InvalidPlayerUUID, PlayerOfflineException 
	{
		int parameterSize = parameter.length;
		String playerName;
		String message;
		String targetedPlayerName;
		Player player;
		CMPlayer cmPlayer;
		CMPlayer targedtedCMPlayer;
		Location location;
		
		switch(parameterSize)
		{
		case 0:			
			_logger.SendToSender(Module.WarpSystem, MessageType.Error, sender, _config.Messages.ConsoleIO.NoParameters);
			break;
		case 1:
			player = (Player)sender;
			
			//teleport yourself to a another player		
			targetedPlayerName = parameter[0];
			
			targedtedCMPlayer = _playerSystem.GetPlayer(targetedPlayerName);
			
			if(targedtedCMPlayer == null)
			{				
				message = _config.Messages.Player.PlayerNotFound;
				
				message = _messageTags.ReplacePlayerTag(message, targetedPlayerName);
				
				_logger.SendToSender(Module.WarpSystem, MessageType.Error, sender, message);
			}
			else
			{				
				location = targedtedCMPlayer.BukkitPlayer.getLocation();
				
				TeleportToLocation(player, location);
			}
			
			break;
			
		case 2:	
			// TP otherplayer to anotherPlayer
			playerName =  parameter[0];
			targetedPlayerName = parameter[1];
			
			cmPlayer = _playerSystem.GetPlayer(playerName);
			targedtedCMPlayer = _playerSystem.GetPlayer(targetedPlayerName);
			
			if(cmPlayer.IsOffline())
			{
				throw new PlayerOfflineException(playerName);
			}			
	
			location = targedtedCMPlayer.BukkitPlayer.getLocation();
			
			TeleportToLocation(cmPlayer.BukkitPlayer, location);
			
			break;
			
			default:
				_logger.SendToSender(Module.WarpSystem, MessageType.Error, sender, _config.Messages.ConsoleIO.TooManyParameters);
				break;
				
		}
	}

	public void TeleportHome(CommandSender sender, String[] args) throws NotForConsoleException, TooManyParameterException 
	{
		final boolean isSenderPlayer = sender instanceof Player;	
		final int parameterLengh = args.length;
		
		if(isSenderPlayer)
		{
			final Player player = (Player)sender;
			
			switch(parameterLengh)
			{
			case 0:
				final Location location = player.getBedLocation();
								
				TeleportToLocation(player, location);	
				
				break;
				
			default:
				throw new TooManyParameterException();
			}
		}
		else
		{
			throw new NotForConsoleException();
		}
	}

	public void TeleportToSpecificLocation(CommandSender sender, String[] parameter) throws InvalidPlayerNameException, PlayerNotFoundException, InvalidPlayerUUID, PlayerOfflineException 
	{
		final boolean isSenderPlayer = sender instanceof Player;
		int parameterLengh = parameter.length;
		int x;
		int y;
		int z;		
		World world;
		Player player;
		CMPlayer cmPlayer;
		String playerName;
		String message;
		Location location;
		
		switch(parameterLengh)
		{
		case 0:		
			_logger.SendToSender(Module.WarpSystem, MessageType.Error, sender, _config.Messages.ConsoleIO.NoParameters);
			break;
		case 1:		
		case 2:		
			_logger.SendToSender(Module.WarpSystem, MessageType.Error, sender, _config.Messages.ConsoleIO.NotEnoughParameters);
			break;
			
		case 3:
			if(isSenderPlayer)
			{
				player = (Player)sender;
				world = player.getWorld();
				
				try
				{
					x = Integer.parseInt(parameter[0]);
					y = Integer.parseInt(parameter[1]);
					z = Integer.parseInt(parameter[2]);
					
					location = new Location(world, x, y, z);
					
					TeleportToLocation(player, location);
				}
				catch(Exception execption)
				{					
					message = "The coordinates are not numbers!";
					
					_logger.SendToSender(Module.WarpSystem, MessageType.Error, sender, message);
				}				
			}
			else
			{
				_logger.SendToSender(Module.WarpSystem, MessageType.Error, sender, _config.Messages.ConsoleIO.NotForConsole);
			}
			break;
			
		case 4:
			playerName = parameter[0];
			
			cmPlayer = _playerSystem.GetPlayer(playerName);
			
			if(cmPlayer.IsOffline())
			{
				throw new PlayerOfflineException(playerName);
			}
			
			player = cmPlayer.BukkitPlayer;
			world = player.getWorld();
			
			try
			{				
				x = Integer.parseInt(parameter[1]);
				y = Integer.parseInt(parameter[2]);
				z = Integer.parseInt(parameter[3]);
				
				location = new Location(world, x, y, z);
				
				TeleportToLocation(player, location);
			}
			catch(Exception execption)
			{					
				message = "The coordinates are not numbers!";
				
				_logger.SendToSender(Module.WarpSystem, MessageType.Error, sender, message);
			}	
		
			break;	
			
			default:
			
				_logger.SendToSender(Module.WarpSystem, MessageType.Error, sender, _config.Messages.ConsoleIO.TooManyParameters);
				break;
		}
	}
	
	public void TeleportToWorld(CommandSender sender, String[] parameter) throws 	InvalidPlayerNameException, 
	PlayerNotFoundException, 
	InvalidPlayerUUID, InvalidWorldNameException, PlayerOfflineException
{
int parameterLengh = parameter.length;
final boolean isSenderPlayer = sender instanceof Player;
String message;
String worldName;
String targetedPlayer;
CMWorld cmWorld;
CMPlayer cmPlayer;
Player player;

switch (parameterLengh)
{
case 0:
message = "Wrong command usage!" + "No parameters setted!";

_logger.SendToSender(Module.WarpSystem, MessageType.Warning, sender, message);
break;
case 1:
if (isSenderPlayer)
{
boolean isSaveToTeleport;

player = (Player)sender;	
worldName = parameter[0];

cmWorld = _worldSystem.GetWorld(worldName);				

isSaveToTeleport = _worldSystem.IsWorldLoaded(cmWorld.Information.ID);

if(isSaveToTeleport)
{
TeleportToLocation(player, cmWorld.BukkitWorld.getSpawnLocation());	
}
else
{
_logger.SendToSender(Module.WarpSystem, MessageType.Error, sender, "Couldn't Teleport. World is not loaded!");
}						
}
else
{
message = _config.Messages.ConsoleIO.NotForConsole;

_logger.SendToSender(Module.WarpSystem, MessageType.Warning, sender, message);
}
break;
case 2:
	
	boolean canUserTeleportTpWorld;
worldName = parameter[0];
targetedPlayer = parameter[1];				



cmWorld = _worldSystem.GetWorld(worldName);	
cmPlayer = _playerSystem.GetPlayer(targetedPlayer);

if(cmPlayer.IsOffline())
{
	throw new PlayerOfflineException(targetedPlayer);
}

	if(cmWorld == null)
	{
	message = "&World &e<&f{WORLD}&e> &cnot &7found!";
	
	message = _messageTags.ReplaceWorldTag(message, worldName);	
	
	_logger.SendToSender(Module.WarpSystem, MessageType.Error, sender, message);
	}
	else
	{
	message = "&7Teleporting &e<&f{PLAYER}&e> &7to &e<&f{WORLD}&e>&7.";
	
	message = _messageTags.ReplacePlayerTag(message, cmPlayer);	
	message = _messageTags.ReplaceWorldTag(message, cmWorld);	
	
	canUserTeleportTpWorld = _worldSystem.IsWorldLoaded(cmWorld.Information.ID);
	
	if(canUserTeleportTpWorld)
	{
	_logger.SendToSender(Module.WarpSystem, MessageType.Info, sender, message);
	TeleportToLocation(cmPlayer.BukkitPlayer, cmWorld.BukkitWorld.getSpawnLocation());	
	}
	else
	{
	_logger.SendToSender(Module.WarpSystem, MessageType.Error, sender, "Could not Teleport. World is not loaded!");
	}
	}	
break;

default:
break;
}
}
	
	public void TeleportToSpawn(CommandSender sender, String[] parameter)
	{
		
	}
	
	public void TeleportToLocation(Player player, Location location)
	{
		String message = _config.Messages.TeleportSystem.Teleporting;

		if(player == null)
		{
			throw new NullPointerException("WarpSystem.TeleportToLocation() > Player is null!");
		}
		
		if(location == null)
		{
			throw new NullPointerException("WarpSystem.TeleportToLocation() > Location is null!");
		}	
		
		/*
		if(location.isWorldLoaded())
		{
			throw new Exception("");
		}
		*/
		
		_logger.SendToSender(Module.WarpSystem, MessageType.Info, player, message);
		
		try
		{
			player.teleport(location);
		}
		catch (Exception e)
		{
			message = _config.Messages.TeleportSystem.CouldNotTeleport + "\n" + e.getMessage();

			_logger.SendToSender(Module.WarpSystem, MessageType.Info, player, message);
		}
	}
	
	public void TeleportToWorldSpawn(CommandSender sender)
	{
		final boolean isSenderPlayer = sender instanceof Player;
		Player player;
		World world;
		Location location;
		
		if(isSenderPlayer)
		{
			player = (Player)sender;
			
			world = player.getWorld();
			
			location = world.getSpawnLocation();
			
			TeleportToLocation(player, location);			
		}
		else
		{
			_logger.SendToSender(Module.WarpSystem, MessageType.Warning, sender, _config.Messages.ConsoleIO.NotForConsole);
		}
	}
	
	public void TeleportBehindBlock(CommandSender sender, Block block)
	{
		final boolean isSenderPlayer = sender instanceof Player;
		
		if(isSenderPlayer)
		{
			final Player player = (Player)sender;
			final Location location = block.getLocation();
			
			TeleportToLocation(player, location);
		}
		else
		{
			// Illigal usage
		}
	}

	public void TeleportToFacingBlock(final Player player, final Block block) 
	{		
		if(block != null)
		{
			final Location location = block.getLocation();
			
			TeleportToLocation(player, location);
		}	
	}
}