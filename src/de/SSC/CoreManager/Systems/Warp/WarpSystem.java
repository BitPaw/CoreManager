package de.SSC.CoreManager.Systems.Warp;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Systems.Chat.Logger;
import de.SSC.CoreManager.Systems.Chat.MessageTags;
import de.SSC.CoreManager.Systems.Chat.MessageType;
import de.SSC.CoreManager.Systems.Chat.Module;
import de.SSC.CoreManager.Systems.DataBase.DataBaseSystem;
import de.SSC.CoreManager.Systems.Player.CMPlayer;
import de.SSC.CoreManager.Systems.Player.PlayerSystem;
import de.SSC.CoreManager.Systems.World.CMWorld;
import de.SSC.CoreManager.Systems.World.WorldSystem;
import de.SSC.CoreManager.Utility.BukkitUtility;
import de.SSC.CoreManager.Utility.DataSaveStorageType;

public class WarpSystem
{
	private static WarpSystem _instance;
	
	private Logger _logger;
	private Config _config;
	private DataBaseSystem _databaseManager;
	private BukkitUtility _bukkitUtility;
	private MessageTags _messageTags;
	private WorldSystem _worldSystem;
	private PlayerSystem _playerSystem;
	private WarpList _warpList;
	
	private WarpSystem()
	{
		_instance = this;

		_warpList = new WarpList();

		_logger = Logger.Instance();
		_config = Config.Instance();
		_databaseManager = DataBaseSystem.Instance();
		_bukkitUtility = BukkitUtility.Instance();
		_messageTags = MessageTags.Instance();
		_worldSystem = WorldSystem.Instance();
		_playerSystem = PlayerSystem.Instance();
	}

	public static WarpSystem Instance()
	{
		return _instance == null ? new WarpSystem() : _instance;
	}


	public void ListAllWarps(CommandSender sender)
	{
		String message = "";
		List<CMWarp> warps = _warpList.GetWarps();
		int count = warps.size();
		
		if(!_bukkitUtility.PlayerUtility.IsSenderPlayer(sender))
		{
			message += "\n";
		}
		
		message += "&6=====[&eAll Warps &6(&e" + _warpList.NumberOfWarps() + "&6)]=====\n";
		
		if(count == 0)
		{
			message += "No loaded warps.";
		}
		else 
		{
			for (CMWarp warp : warps)
			{		
				
				if(count == 1)
				{
					message += "&e" + warp.WarpName + "\n";
				}
				else
				{
					message += "&e" + warp.WarpName + "&6, ";
				}
				
				count--;	
			}
		}		

		message += "&6=====================";

		_logger.SendToSender(Module.WarpSystem, MessageType.None, sender, message);
	}	

	public void ReloadWarps()
	{
		_warpList.Clear();
		List<CMWarp> warps = _databaseManager.LoadAllWarps();
		 
		for(CMWarp cmWarp : warps)
		{
			_warpList.Add(cmWarp);
		}		 
	}
	
	private void TeleportToLocation(Player player, Location location)
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
	
	public void Warp(CommandSender sender, String[] args)
	{
		Player player = (Player)sender;
		String warpName = args[0];
		CMWarp cmWarp = _warpList.GetWarp(warpName);

		if (cmWarp == null)
		{
			_logger.SendToSender(Module.WarpSystem, MessageType.Error, sender, _config.Messages.TeleportSystem.NoWarpWithThisName);
		}
		else
		{
			TeleportToLocation(player, cmWarp.WarpLocation);
		}
	}

	public void SetWarp(CommandSender sender, String[] args)
	{
		String warpName = args[0];
		String message;
		Player player;
		CMWarp cmWarp;

		if (_bukkitUtility.PlayerUtility.IsSenderPlayer(sender))
		{
			player = (Player)sender;

			if (warpName.isEmpty())
			{
				_logger.SendToSender(Module.WarpSystem, MessageType.Warning, player, _config.Messages.ConsoleIO.NoParameters);
			}
			else
			{
				cmWarp = new CMWarp(warpName, player.getLocation());

				SetWarpPoint(cmWarp);

				message = _config.Messages.TeleportSystem.WarpCreated;

				message = _messageTags.ReplaceWarpTag(message, warpName);

				_logger.SendToSender(Module.WarpSystem, MessageType.Info, player, message);
			}
		}
		else
		{
			_logger.SendToSender(Module.WarpSystem, MessageType.Warning, sender, _config.Messages.ConsoleIO.NotForConsole);
		}
	}

	private void SetWarpPoint(CMWarp cmWarp)
	{
		boolean doesWarpExist;

		doesWarpExist = _databaseManager.DoesWarpExist(cmWarp);

		if (doesWarpExist)
		{
			_databaseManager.UpdateWarp(cmWarp);
		}
		else
		{
			_databaseManager.RegisterWarp(cmWarp);
			_warpList.Add(cmWarp);
		}
	}

	public void TeleportToWorldSpawn(CommandSender sender)
	{
		boolean isSenderPlayer = _bukkitUtility.PlayerUtility.IsSenderPlayer(sender);
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

	public void DeleteWarp(CommandSender sender, String[] parameter)
	{

	}
	
	public void TeleportToSpawn(CommandSender sender, String[] parameter)
	{
		
	}

	public void SetSpawn(CommandSender sender) throws Exception
	{
		Player player;
		World world;
		Location location;
		
		if (_bukkitUtility.PlayerUtility.IsSenderPlayer(sender))
		{
			player = (Player)sender;
			world = (World)player.getWorld();
			location = player.getLocation();

			try
			{
				world.setSpawnLocation(location);

				_logger.SendToSender(Module.WarpSystem, MessageType.Info, sender, _config.Messages.TeleportSystem.SpawnUpdated);
			}
			catch (Exception e)
			{
				throw new Exception(_config.Messages.TeleportSystem + "World.SetSpawnLocation Error\n" + e.getMessage());
			}
		}
	}

	public void TeleportToWorld(CommandSender sender, String[] parameter)
	{
		int parameterLengh = parameter.length;
		boolean isSenderPlayer = _bukkitUtility.PlayerUtility.IsSenderPlayer(sender);
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
				
				cmWorld = _worldSystem.GetWorldPerName(worldName);				
				
				isSaveToTeleport = CanUserTeleportToWorld(cmWorld);
				
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
			worldName = parameter[0];
			targetedPlayer = parameter[1];					
			
			cmWorld = _worldSystem.GetWorldPerName(worldName);	
			cmPlayer = _playerSystem.GetPlayer(targetedPlayer);
						
			if(cmPlayer == null)
			{
				message = "&7Player &e<&f{PLAYER}&e> &cnot &7found!";
				
				message = _messageTags.ReplacePlayerTag(message, cmPlayer);	
				
				_logger.SendToSender(Module.WarpSystem, MessageType.Error, sender, message);
			}	
			else
			{
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
					
					if(cmWorld.SaveStorageType == DataSaveStorageType.UnRegistered || cmWorld.SaveStorageType == DataSaveStorageType.Active)
					{
						_logger.SendToSender(Module.WarpSystem, MessageType.Info, sender, message);
						TeleportToLocation(cmPlayer.BukkitPlayer, cmWorld.BukkitWorld.getSpawnLocation());	
					}
					else
					{
						_logger.SendToSender(Module.WarpSystem, MessageType.Error, sender, "Could not Teleport. World is not loaded!");
					}
				}	
			}	
			break;

		default:
			break;
		}
	}

	private boolean CanUserTeleportToWorld(CMWorld cmWorld)
	{
		return (cmWorld.SaveStorageType == DataSaveStorageType.UnRegistered || 
				cmWorld.SaveStorageType == DataSaveStorageType.Active) 
				
				&& cmWorld.BukkitWorld != null;
	}
	
	public void TeleportToPlayer(CommandSender sender, String[] parameter) 
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
			
			if(cmPlayer == null || targedtedCMPlayer == null)
			{		
				message = _config.Messages.Player.PlayerNotFound;				
				
				_logger.SendToSender(Module.WarpSystem, MessageType.Error, sender, message);
			}
			else
			{
				location = targedtedCMPlayer.BukkitPlayer.getLocation();
				
				TeleportToLocation(cmPlayer.BukkitPlayer, location);
			}
			
			
			break;
			
			default:
				_logger.SendToSender(Module.WarpSystem, MessageType.Error, sender, _config.Messages.ConsoleIO.TooManyParameters);
				break;
				
		}
	}

	public void TeleportHome(CommandSender sender, String[] args) 
	{
				
	}

	public void TeleportToSpecificLocation(CommandSender sender, String[] parameter) 
	{
		boolean isSenderPlayer = _bukkitUtility.PlayerUtility.IsSenderPlayer(sender);
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
			
			if(cmPlayer == null)
			{				
				_logger.SendToSender(Module.WarpSystem, MessageType.Error, sender, _config.Messages.Player.PlayerNotFound);
			}
			else
			{
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
			}		
		
			break;	
			
			default:
			
				_logger.SendToSender(Module.WarpSystem, MessageType.Error, sender, _config.Messages.ConsoleIO.TooManyParameters);
				break;
		}
	}
}