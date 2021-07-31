package de.SSC.CoreManager.Warp;

import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Chat.Logger;
import de.SSC.CoreManager.Chat.MessageTags;
import de.SSC.CoreManager.Chat.MessageType;
import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DataBaseSystem;
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
import de.SSC.CoreManager.System.Exception.SystemNotActiveException;
import de.SSC.CoreManager.System.Exception.TooFewParameterException;
import de.SSC.CoreManager.System.Exception.TooManyParameterException;
import de.SSC.CoreManager.Teleport.TeleportSystem;
import de.SSC.CoreManager.Warp.Exception.WarpNotFoundException;
import de.SSC.CoreManager.World.Exception.InvalidWorldNameException;

public class WarpSystem extends BaseSystem implements ISystem
{
	private static WarpSystem _instance;
	private Warp _globalSpawn;
	
	private Logger _logger;
	private Config _config;
	private DataBaseSystem _databaseManager;
	private MessageTags _messageTags;
	private PlayerSystem _playerSystem;
	private WarpList _warpList;
	private TeleportSystem _teleportSystem;
	
	private WarpSystem()
	{
		super(Module.WarpSystem, SystemState.Active, SystemPriority.High);
		_instance = this;

		_warpList = new WarpList();
	}

	public static WarpSystem Instance()
	{
		return _instance == null ? new WarpSystem() : _instance;
	}

	@Override
	public void LoadReferences() 
	{
		_logger = Logger.Instance();
		_config = Config.Instance();
		_databaseManager = DataBaseSystem.Instance();
		_messageTags = MessageTags.Instance();
		_playerSystem = PlayerSystem.Instance();
		_teleportSystem = TeleportSystem.Instance();		
	}

	@Override
	public void Reload(final boolean firstRun) throws SystemNotActiveException 
	{
		List<Warp> warps;
		_warpList.Clear();
		
		if(!Information.IsActive())
		{
			throw new SystemNotActiveException();
		}
		
		warps = _databaseManager.Warp.LoadAllWarps();
		 
		for(Warp warp : warps)
		{
			_warpList.Add(warp);
		}			
	}
	
	@Override
	public void PrintData(CommandSender sender) 
	{
		
	}
	
	public int GetNextWarpID()
	{
		return _warpList.NumberOfWarps();
	}

	public void ListAllWarps(CommandSender sender)
	{
		final boolean isSenderPlayer = sender instanceof Player;
		String message = "";
		List<Warp> warps = _warpList.GetWarps();
		int count = warps.size();
		
		if(!isSenderPlayer)
		{
			message += "\n";
		}
		
		message += "&6=====[&eAll Warps &6(&e" + _warpList.NumberOfWarps() + "&6)]=====\n";
		
		if(count == 0)
		{
			message += "&cNo loaded warps.\n";
		}
		else 
		{
			for (Warp warp : warps)
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

	public void SetWarp(CommandSender sender, String[] args) throws InvalidWorldNameException
	{
		final boolean isSenderPlayer = sender instanceof Player;
		String warpName = args[0];
		String message;
		Player player;
		Warp warp;

		if (isSenderPlayer)
		{
			player = (Player)sender;

			if (warpName.isEmpty())
			{
				_logger.SendToSender(Module.WarpSystem, MessageType.Warning, player, _config.Messages.ConsoleIO.NoParameters);
			}
			else
			{
				warp = new Warp(warpName, player.getLocation());

				SetWarpPoint(warp);

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

	private void SetWarpPoint(Warp warp) throws InvalidWorldNameException
	{
		boolean doesWarpExist;

		doesWarpExist = _databaseManager.Warp.DoesWarpExist(warp);

		if (doesWarpExist)
		{
			_databaseManager.Warp.UpdateWarp(warp);
		}
		else
		{
			_databaseManager.Warp.RegisterWarp(warp);
			_warpList.Add(warp);
		}
	}

	public void WarpCommand(CommandSender sender, String[] args) throws TooManyParameterException, 
																		TooFewParameterException, 
																		NotForConsoleException, 
																		InvalidPlayerNameException,
																		PlayerNotFoundException, 
																		InvalidPlayerUUID, 
																		WarpNotFoundException, PlayerOfflineException
	{
		final boolean isSenderPlayer = sender instanceof Player;
		final int parameterLengh = args.length;
		CMPlayer targetCMPlayer;
		Warp warp;
		Player player;		
		String warpName;		
		String targetPlayerName;		

		switch(parameterLengh)
		{
		case 0:
			throw new TooFewParameterException();	

		case 1:
			// Normal warp aka /warp <location>
			warpName = args[0];
			warp = _warpList.GetWarp(warpName);

			if(isSenderPlayer)
			{
				player = (Player)sender;	

			}
			else
			{
				// Console can't warp to anything.
				throw new NotForConsoleException();
			}
			break;

		case 2:
			// command /warp <location> <player>	

			// Get targeted player
			warpName = args[0];
			targetPlayerName = args[1];
			warp = _warpList.GetWarp(warpName);

			// Get player via playerName
			targetCMPlayer = _playerSystem.GetPlayer(targetPlayerName);	
			
			if(targetCMPlayer.IsOffline())
			{
				throw new PlayerOfflineException(targetPlayerName);
			}

			// Get bukkitplayer from CMPlayer
			player = targetCMPlayer.BukkitPlayer;

			break;

		default:
			throw new TooManyParameterException();
		}


		_teleportSystem.TeleportToLocation(player, warp.WarpLocation);
}
	


	public void DeleteWarp(CommandSender sender, String[] parameter)
	{

	}

	public Warp GetGlobalSpawn() throws WarpNotFoundException 
	{
		if(_globalSpawn == null)
		{
			Warp warp = _warpList.GetWarp("spawn");
			
			_globalSpawn = warp;
		}
		
		return _globalSpawn;
	}

	public void WarpToGlobalSpawn(CommandSender sender) throws NotForConsoleException, WarpNotFoundException 
	{
		final boolean isSenderPlayer = sender instanceof Player;
		
		if(isSenderPlayer)
		{
			final Warp warp = GetGlobalSpawn();
			final Player player = (Player)sender;
						
			_teleportSystem.TeleportToLocation(player, warp.WarpLocation);			
		}
		else
		{
			throw new NotForConsoleException();
		}			
	}
}