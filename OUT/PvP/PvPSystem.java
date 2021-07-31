package de.BitFire.PvP;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.BitFire.API.CommandCredentials;
import de.BitFire.API.Bukkit.BukkitAPIBlock;
import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Core.Exception.FaultrySyntaxException;
import de.BitFire.Core.Exception.NoCommandException;
import de.BitFire.Core.Exception.NotForConsoleException;
import de.BitFire.Core.Exception.SystemHasNoDataToPrintException;
import de.BitFire.Core.Exception.SystemHasNothingToReloadException;
import de.BitFire.Core.Exception.SystemNotActiveException;
import de.BitFire.Core.Exception.TooFewParameterException;
import de.BitFire.Core.Exception.TooManyParameterException;
import de.BitFire.DataBase.DataBaseSystem;
import de.BitFire.Geometry.Point;
import de.BitFire.PvP.Arena.Arena;
import de.BitFire.PvP.Arena.ArenaList;
import de.BitFire.PvP.Lobby.Exception.LobbyAlreadyClosedException;
import de.BitFire.PvP.Lobby.Exception.LobbyAlreadyOpenException;
import de.BitFire.PvP.Lobby.Exception.LobbyAlreadyRunningException;
import de.BitFire.PvP.Lobby.Exception.LobbyClosedException;
import de.BitFire.PvP.Lobby.Exception.LobbyFullException;
import de.BitFire.PvP.Lobby.Exception.PlayerAlreadyInLobbyException;
import de.BitFire.PvP.Lobby.Exception.PlayerNotInLobbyException;
import de.BitFire.PvP.Sign.ArenaSign;
import de.BitFire.PvP.Sign.ArenaSignList;
import de.BitFire.Teleport.PlayerPositionCache;
import de.BitFire.Teleport.TeleportSystem;
import de.BitFire.Teleport.Exception.MissingPlayerPositionException;
import de.BitFire.World.CMWorld;
import de.BitFire.World.WorldSystem;
import de.BitFire.World.Manipulation.Exception.NoRegionMarkedException;
import de.BitFire.World.Manipulation.Exception.PositionAMissingException;
import de.BitFire.World.Manipulation.Exception.PositionBMissingException;

public class PvPSystem extends BaseSystem implements ISystem
{
	private static PvPSystem _instance;
	
	private ArenaSignList _arenaSignList;
	private PlayerPositionCache _playerPositionCache;
	private ArenaList _arenaList;	
	
	private Logger _logger;
	private DataBaseSystem _database;
	private WorldSystem _worldSystem;
	private TeleportSystem _teleportSystem;
	
	public static PvPSystem Instance()
	{
		return _instance == null ? new PvPSystem() : _instance;
	}
	
	private PvPSystem()
	{
		super(Module.PvPSystem, SystemState.Active, Priority.Low);
		
		_instance = this;
		_arenaSignList = new ArenaSignList();
		_arenaList = new ArenaList();
		_playerPositionCache = new PlayerPositionCache();
	}
	
	public void LoadReferences()
	{
		_database = DataBaseSystem.Instance();
		_logger = Logger.Instance();
		_worldSystem = WorldSystem.Instance(); 
		_teleportSystem = TeleportSystem.Instance();
	}
	
	public void Reload(final boolean firstRun) throws SystemHasNothingToReloadException, SystemNotActiveException 
	{
		_arenaSignList.Add(_database.PvP.GetAllArenaSigns());
		_arenaList.Add(_database.PvP.GetAllArenas());
	}
	
	public void PrintData(CommandSender sender) throws SystemHasNoDataToPrintException 
	{
		_logger.SendToSender(Information.GetSystemModule(), MessageType.None, sender, _arenaSignList.PrintData());
		_logger.SendToSender(Information.GetSystemModule(), MessageType.None, sender, _arenaList.PrintData());
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
																			LobbyAlreadyClosedException, LobbyAlreadyOpenException, MissingPlayerPositionException
	{
		
		String subCommand;
		
		switch(commandCredentials.ParameterLengh)
		{
		
		
		case 0:
			throw new TooFewParameterException();
			
		case 1:
			subCommand = commandCredentials.Parameter[0].toLowerCase();
			
			switch(subCommand)
			{
			case "quit":
				if(commandCredentials.IsSenderPlayer)
				{
					final Player player = commandCredentials.TryGetPlayer();
					final UUID playerUUID = player.getUniqueId();
					final Arena arena = _arenaList.GetArena(playerUUID);
					String message = "&7You &eleft &7the arena.";	
					Location oldlocation;					
					
					if(arena == null)
					{
						throw new PlayerNotInLobbyException();
					}	
					
					arena.Lobby.RemovePlayer(playerUUID);
					arena.SpawnList.Free(playerUUID);
					
					try 
					{
						oldlocation = _playerPositionCache.Pull(playerUUID);					
						
						_logger.SendToSender(Information.GetSystemModule(), MessageType.Info, player, message);
						_teleportSystem.TeleportToLocation(player, oldlocation);
						
					} 
					catch (final MissingPlayerPositionException e) 
					{
						// Couln't place the player to the old position, there was no last position saved!
						message = "There went something wrong! Failed to teleport to the last position.";
						_logger.SendToSender(Information.GetSystemModule(), MessageType.Error, player, message);
						e.printStackTrace();				
					}											 
				}
				else
				{
					throw new NotForConsoleException();
				}					
				break;
				
			default:
				throw new FaultrySyntaxException();
			}
			break;
			
		case 2:
			subCommand = commandCredentials.Parameter[0].toLowerCase();
			
			switch(subCommand)
			{
			case "info":
				String message;
				subCommand = commandCredentials.Parameter[1].toLowerCase();
				
				switch(subCommand)
				{
				case "s":
				case "sign":
					message = _arenaSignList.PrintData();
					_logger.SendToSender(Information.GetSystemModule(), MessageType.None, commandCredentials.Sender, message);
					break;
					
				case "a":
				case "arena":
					message = _arenaList.PrintData();
					_logger.SendToSender(Information.GetSystemModule(), MessageType.None, commandCredentials.Sender, message);
					break;
					
					default:
						throw new FaultrySyntaxException();
					
				}
				
				break;
				
			case "join":	
				if(commandCredentials.IsSenderPlayer)
				{
					subCommand = commandCredentials.Parameter[1].toLowerCase();		
					
					final Player player = commandCredentials.TryGetPlayer();
					final UUID playerUUID = player.getUniqueId();	
					final Location oldLocation = player.getLocation();					
					final int x = Integer.parseInt(subCommand);					
					final Arena arena = _arenaList.GetArena(x);					
					arena.Lobby.AddPlayer(playerUUID);					
					final Point point = arena.SpawnList.GetRandomeSpawn(playerUUID);
									
					System.out.print(point.toString());
					
					final CMWorld cmWorld = _worldSystem.GetWorld(arena.WorldID);					
					final Location location = new Location(cmWorld.BukkitWorld, point.X, point.Y, point.Z);
					
					// Save old position
					_playerPositionCache.Stack(playerUUID, oldLocation);
					
					_teleportSystem.TeleportToLocation(player, location);
					
					message = "Joined pvp Arena.";
					_logger.SendToSender(Information.GetSystemModule(), MessageType.Info, player, message);
				}
				else
				{
					throw new NotForConsoleException();
				}					
				
				break;
				
			case "open":
				subCommand = commandCredentials.Parameter[1].toLowerCase();	
				int x = Integer.parseInt(subCommand);				
				Arena arena = _arenaList.GetArena(x);
				
				arena.Lobby.Open();
				
				break;
				
			case "close":
				
		
				subCommand = commandCredentials.Parameter[1].toLowerCase();	
				
				int a = Integer.parseInt(subCommand);
				
				Arena aarena = _arenaList.GetArena(a);
				
				aarena.Lobby.Close();
				
				
				break;
				
			default:
				throw new FaultrySyntaxException();
					
		
			}
			
	
		}
	}
	
	public void Update()
	{
		List<Arena> arenas = _arenaList.GetAllArenas();
	
		//System.out.print("Updating arenaSigns... (" + arenas.size() + ")");
		
		for(final Arena arena : arenas)
		{
			final int signID = arena.SignID;
			final boolean hasSign = signID != -1;
			
			//System.out.print("ID " + signID);
			
			if(hasSign)
			{
				final ArenaSign arenaSign = _arenaSignList.GetAreaSign(signID);		
				final Point position = arenaSign.Position;
				final CMWorld cmWorld = _worldSystem.GetWorld(arenaSign.WorldID);
				final Location location = new Location(cmWorld.BukkitWorld, position.X, position.Y, position.Z);				
				final Block block = location.getBlock();
				final boolean isSign = BukkitAPIBlock.IsBockSign(block);
				
				//System.out.print(position.toString() + "  " + cmWorld.Information.CustomName +  " is sign? " + isSign);
				
				if(isSign)
				{
					final Sign sign = (Sign) block.getState();
					final String[] lines = arena.GetSignText();
					
					sign.setLine(0, _logger.TransformToColor(lines[0]));
					sign.setLine(1, _logger.TransformToColor(lines[1]));
					sign.setLine(2, _logger.TransformToColor(lines[2]));
					sign.setLine(3, _logger.TransformToColor(lines[3]));
					sign.update();
				}				
			}
		}		
	}
	
	/*
	public List<UUID> GetPlayerUUIDsInFromLobby(final int lobbyID)
	{
		return _playerList.get(lobbyID);
	}
	

	
	public boolean IsPlayerInLobby(final UUID playerUUID)
	{
		final Set<Integer> lobbyIDs = _playerList.keySet();
		
		for(final Integer lobbyID : lobbyIDs)
		{
			final boolean isInLobby = IsPlayerInLobby(lobbyID, playerUUID);
			
			if(isInLobby)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean IsPlayerInLobby(final int lobbyID, final UUID playerUUID)
	{
		final List<UUID> playerrUUIDs = GetPlayerUUIDsInFromLobby(lobbyID);
		
		for(final UUID uuid : playerrUUIDs)
		{
			if(uuid.equals(playerUUID))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void Add(final int lobbyID, final UUID playerUUID) throws LobbyDoesNotExsistException, PlayerAlreadyInLobbyException
	{	
		if(DoesLobbyExist(lobbyID))
		{
			if(IsPlayerInLobby(lobbyID, playerUUID))
			{
				throw new PlayerAlreadyInLobbyException();
			}
			else
			{
				List<UUID> players = GetPlayerUUIDsInFromLobby(lobbyID);
				
				players.add(playerUUID);
				
				_playerList.replace(lobbyID, players);
				
				_playerSystem.ChangePlayerState(playerUUID, PlayerState.InGame);
			}
		}
		else
		{
			throw new LobbyDoesNotExsistException();
		}	
	}
	
	public void Remove(final UUID playerUUID) throws PlayerNotInLobbyException
	{	
		final int lobbyID = GetPlayerLobby(playerUUID);

		if(lobbyID == -1)
		{
			throw new PlayerNotInLobbyException();
		}
		else
		{
			List<UUID> players = GetPlayerUUIDsInFromLobby(lobbyID);
			
			players.remove(playerUUID);
			
			_playerList.replace(lobbyID, players);
			
			_playerSystem.ChangePlayerState(playerUUID, PlayerState.Offline);	
		}		
	}
	*/
}