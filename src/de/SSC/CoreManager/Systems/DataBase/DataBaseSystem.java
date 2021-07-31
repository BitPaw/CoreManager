package de.SSC.CoreManager.Systems.DataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

import com.DataBase.MySQL;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Systems.Chat.Logger;
import de.SSC.CoreManager.Systems.Chat.MessageTags;
import de.SSC.CoreManager.Systems.Chat.MessageType;
import de.SSC.CoreManager.Systems.Chat.Module;
import de.SSC.CoreManager.Systems.DataBase.Tables.CMAreaTable;
import de.SSC.CoreManager.Systems.DataBase.Tables.CMPermissionTable;
import de.SSC.CoreManager.Systems.DataBase.Tables.CMPlayerTable;
import de.SSC.CoreManager.Systems.DataBase.Tables.CMRanksTable;
import de.SSC.CoreManager.Systems.DataBase.Tables.CMRegionTable;
import de.SSC.CoreManager.Systems.DataBase.Tables.CMWarpTable;
import de.SSC.CoreManager.Systems.DataBase.Tables.CMWorldTable;
import de.SSC.CoreManager.Systems.Location.CMLocation;
import de.SSC.CoreManager.Systems.Permission.CMPermission;
import de.SSC.CoreManager.Systems.Player.CMPlayer;
import de.SSC.CoreManager.Systems.Player.PlayerSystem;
import de.SSC.CoreManager.Systems.Rank.CMRank;
import de.SSC.CoreManager.Systems.Rank.RankSystem;
import de.SSC.CoreManager.Systems.Region.CMRegion;
import de.SSC.CoreManager.Systems.Warp.CMWarp;
import de.SSC.CoreManager.Systems.World.CMWorld;
import de.SSC.CoreManager.Systems.World.WorldSystem;

public class DataBaseSystem
{
	private MySQL _mySQL;
	private static DataBaseSystem _instance;

	private Connection connection;
	private Statement statement;
	private ResultSet LastResult;

	
	private Logger _logger;
	private Config _config;
	private CoreManagerMySQLMessages _mySQLMessages;
	private MessageTags _messageTags;
	private PlayerSystem _playerSystem;
	private RankSystem _rankSystem;
	private WorldSystem _worldSystem;

	private DataBaseSystem()
	{
		_instance = this;

		_logger = Logger.Instance();
		_config = Config.Instance();
		_mySQLMessages = CoreManagerMySQLMessages.Instance();
		_messageTags = MessageTags.Instance();
		_playerSystem = PlayerSystem.Instance();
		_rankSystem = RankSystem.Instance();
		_worldSystem = WorldSystem.Instance();

		_mySQL = new MySQL(_config.MySQL.Hostname, _config.MySQL.Port, _config.MySQL.Database, _config.MySQL.Username, _config.MySQL.Password);

		_logger.SendToConsole(Module.DataBase, MessageType.Online, _config.Messages.ConsoleIO.On);
	}

	public static DataBaseSystem Instance()
	{
		return _instance == null ? new DataBaseSystem() : _instance;
	}

	private void OpenDataBase()
	{

		try
		{
			if (_mySQL == null)
			{
				throw new Exception("MySQL is missing!");
			}

			connection = _mySQL.openConnection();
		}
		catch (Exception e)
		{
			String errorMessage = _config.Messages.DataBase.ConnectionFailed + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
			return;
		}
	}

	private void CloseDataBase()
	{
		try
		{
			_mySQL.closeConnection();
		}
		catch (Exception e)
		{
			String errorMessage = _config.Messages.DataBase.ClosingError + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
		}
	}

	private boolean CheckMySQLCommand(String sqlMessage)
	{
		boolean isEmptySQLMessage = false;
		
		if(sqlMessage == null)
		{
			isEmptySQLMessage = true;
		}
		else
		{
			if (sqlMessage.isEmpty())
			{
				isEmptySQLMessage = true;
			}
		}	
		
		if(isEmptySQLMessage)
		{
			_logger.SendToConsole(Module.DataBase, MessageType.Error, _config.Messages.DataBase.EmptySQLMessage);
		}		
	
		return !isEmptySQLMessage;
	}

	private void TryExecuteCommand(String sqlMessage)
	{
		String sqlCommandMessage = _config.Messages.ConsoleIO.NewLine + _config.Messages.DataBase.MessageHeader + _config.Messages.ConsoleIO.NewLine + sqlMessage + _config.Messages.ConsoleIO.NewLine + _config.Messages.DataBase.MessageFooter;

		try
		{
			_logger.SendToConsole(Module.DataBase, MessageType.Command, sqlCommandMessage);

			statement = connection.createStatement();
			statement.execute(sqlMessage);
		}
		catch (Exception e)
		{
			String errorMessage = _config.Messages.DataBase.StatementError + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
		}
	}

	public int GetInteger(String sqlMessage)
	{
		int returnValue = 0;

		CheckMySQLCommand(sqlMessage);

		OpenDataBase();

		TryExecuteCommand(sqlMessage);

		try
		{
			LastResult = statement.getResultSet();

			if (LastResult.next())
			{
				returnValue = LastResult.getInt(1);
			}
		}
		catch (Exception e)
		{
			String errorMessage = _config.Messages.DataBase.FailedToGetInteger + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
		}

		CloseDataBase();

		return returnValue;
	}

	public boolean GetBoolean(String sqlMessage)
	{
		boolean returnValue = false;

		CheckMySQLCommand(sqlMessage);

		OpenDataBase();

		TryExecuteCommand(sqlMessage);

		try
		{
			LastResult = statement.getResultSet();

			if (LastResult.next())
			{
				int value = LastResult.getInt(1);

				if (value >= 1)
				{
					returnValue = true;
				}
			}
		}
		catch (Exception e)
		{
			String errorMessage = _config.Messages.DataBase.FailedToGetBoolean + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
		}


		CloseDataBase();

		return returnValue;

	}

	public void SendData(String sqlMessage)
	{
		CheckMySQLCommand(sqlMessage);

		OpenDataBase();

		TryExecuteCommand(sqlMessage);

		CloseDataBase();
	}

	public void UpdateCustomName(CMPlayer cmPlayer)
	{
		String name = cmPlayer.CustomName;
		UUID uuid = cmPlayer.BukkitPlayer.getUniqueId();
		String sql = _mySQLMessages.UpdatePlayerCustomName(uuid, name);

		SendData(sql);
	}

	public void RegisterNewPlayer(Player player)
	{
		CMPlayer cmPlayer;
		String sqlCommand;
		String text = _config.Messages.Player.RegisteringNewPlayer;

		_logger.SendToConsole(Module.DataBase, MessageType.Info, "Registering new player...");
		
		if (player == null)
		{
			throw new NullPointerException(_config.Messages.Player.PlayerParameterWasNull);
		}	

		try
		{
			text = _messageTags.ReplacePlayerTag(text, player);

			_logger.SendToConsole(Module.DataBase, MessageType.Info, text);
			
			cmPlayer = new CMPlayer(player);
			
			sqlCommand = cmPlayer.ToMySQLInsertMessage();
		}
		catch (Exception e)
		{
			String errorMessage = _config.Messages.Player.FailedToGetPlayerInformation + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);

			return;
		}

		try
		{
			SendData(sqlCommand);
		}
		catch (Exception e)
		{
			String errorMessage = _config.Messages.Player.NewPlayerRegistrationError + _config.Messages.ConsoleIO.NewLine + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);

			return;
		}

		_playerSystem.AddPlayer(cmPlayer);
		
		cmPlayer.GetPlayerInfo(Bukkit.getConsoleSender());
		cmPlayer.SetDataForServerSide();
	}


	public ArrayList<CMRank> LoadAllRanks()
	{
		ArrayList<CMRank> ranks = new ArrayList<CMRank>();
		String sqlMessage = _mySQLMessages.LoadAllRanks();

		String rankName;
		String colorTag;
		String playerColor;
		boolean isDefault;
		
		_logger.SendToConsole(Module.DataBase, MessageType.Info, "Load all ranks...");

		CheckMySQLCommand(sqlMessage);

		OpenDataBase();

		TryExecuteCommand(sqlMessage);

		try
		{
			LastResult = statement.getResultSet();

			while (LastResult.next())
			{
				rankName = LastResult.getString(CMRanksTable.RankName.toString());
				colorTag = LastResult.getString(CMRanksTable.ColorTag.toString());
				playerColor = LastResult.getString(CMRanksTable.PlayerColor.toString());
				isDefault = LastResult.getBoolean(CMRanksTable.IsDefault.toString());

				ranks.add(new CMRank(rankName, colorTag, playerColor, isDefault));
			}
		}
		catch (Exception e)
		{
			String errorMessage = _config.Messages.Rank.FailedToLoadAllRanks + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
		}

		CloseDataBase();

		return ranks;
	}

	public CMPlayer LoadPlayer(Player player)
	{
		CMPlayer cmPlayer = new CMPlayer();
		UUID uuid = player.getUniqueId();
		String sqlMessage = _mySQLMessages.GetPlayer(uuid);

		CheckMySQLCommand(sqlMessage);

		OpenDataBase();

		TryExecuteCommand(sqlMessage);

		try
		{
			LastResult = statement.getResultSet();

			while (LastResult.next())
			{
				String customName = LastResult.getString(CMPlayerTable.CustomName.toString());

				cmPlayer.BukkitPlayer = player;
				cmPlayer.CustomName = customName;
				cmPlayer.PlayerUUID = UUID.fromString(LastResult.getString(CMPlayerTable.PlayerUUID.toString()));
				cmPlayer.IP = LastResult.getString(CMPlayerTable.IP.toString());
				cmPlayer.IsBanned = LastResult.getBoolean(CMPlayerTable.Banned.toString());
				cmPlayer.IsOP = LastResult.getBoolean(CMPlayerTable.OP.toString());
				cmPlayer.IsCracked = LastResult.getBoolean(CMPlayerTable.Cracked.toString());
				cmPlayer.LastSeen = LastResult.getTimestamp(CMPlayerTable.LastSeen.toString());
				cmPlayer.Money = LastResult.getFloat(CMPlayerTable.Money.toString());
				cmPlayer.PlayerName = LastResult.getString(CMPlayerTable.PlayerName.toString());
				cmPlayer.RankGroup = _rankSystem.GetRank(LastResult.getString(CMPlayerTable.RankName.toString()));
				cmPlayer.Registered = LastResult.getTimestamp(CMPlayerTable.Registered.toString());

				cmPlayer.SetDataForServerSide();
			}
		}
		catch (Exception e)
		{
			String errorMessage = _config.Messages.Player.FailedToLoadPlayer + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
		}

		CloseDataBase();

		_playerSystem.AddPlayer(cmPlayer);
		
		cmPlayer.GetPlayerInfo(Bukkit.getConsoleSender());

		return cmPlayer;
	}

	public boolean DoesPlayerExist(Player player)
	{
		boolean playerExists;
		String sqlMessage = _mySQLMessages.DoesPlayerExist(player);
		String message;

		_logger.SendToConsole(Module.DataBase, MessageType.Question, "Does player exist?");

		playerExists = GetBoolean(sqlMessage);
		

		if (playerExists)
		{			
			message = "&9Old &8player detected! &eName&8 : &e" + player.getName() +	"\n&eUUID &8: " + player.getUniqueId();		
		}
		else
		{
			message = "&aNew &7player detected! &eName&7 : &e" + player.getName() +	"\n&eUUID &7: " + player.getUniqueId();				
		}

		_logger.SendToConsole(Module.DataBase, MessageType.Info, message);

		return playerExists;
	}

	public void UpdatePlayer(Player player)
	{
		String sqlCommand = _mySQLMessages.UpdatePlayer(player);

		SendData(sqlCommand);
	}

	public ArrayList<CMWorld> LoadAllWorlds()
	{
		ArrayList<CMWorld> cmWorlds = new ArrayList<CMWorld>();
		String sqlCommand = _mySQLMessages.LoadAllWorlds();

		CheckMySQLCommand(sqlCommand);

		OpenDataBase();

		TryExecuteCommand(sqlCommand);

		try
		{
			LastResult = statement.getResultSet();

			while (LastResult.next())
			{
				CMWorld cmWorld = new CMWorld();

				cmWorld.Active = LastResult.getBoolean(CMWorldTable.Active.toString());
				cmWorld.Name = LastResult.getString(CMWorldTable.WorldName.toString());
				cmWorld.CustomName = LastResult.getString(CMWorldTable.CustomName.toString());
				cmWorld.MapType = WorldType.getByName(LastResult.getString(CMWorldTable.MapType.toString()));
				cmWorld.MapStyle = World.Environment.valueOf(LastResult.getString(CMWorldTable.MapStyle.toString()));
				cmWorld.BorderSize = LastResult.getInt(CMWorldTable.BorderSize.toString());
				cmWorld.PvP = LastResult.getBoolean(CMWorldTable.PvP.toString());
				cmWorld.KeepInventory = LastResult.getBoolean(CMWorldTable.KeepInventory.toString());
				cmWorld.MobGrief = LastResult.getBoolean(CMWorldTable.MobGrief.toString());
				cmWorld.MobSpawning = LastResult.getBoolean(CMWorldTable.MobSpawning.toString());
				cmWorld.Weather = LastResult.getBoolean(CMWorldTable.Weather.toString());
				cmWorld.DoFireTick = LastResult.getBoolean(CMWorldTable.DoFireTick.toString());
				cmWorld.Seed = LastResult.getLong(CMWorldTable.Seed.toString());
				cmWorld.SpawnX = LastResult.getFloat(CMWorldTable.SpawnX.toString());
				cmWorld.SpawnY = LastResult.getFloat(CMWorldTable.SpawnY.toString());
				cmWorld.SpawnZ = LastResult.getFloat(CMWorldTable.SpawnZ.toString());
				cmWorld.SpawnPitch = LastResult.getFloat(CMWorldTable.SpawnPitch.toString());
				cmWorld.SpawnYaw = LastResult.getFloat(CMWorldTable.SpawnYaw.toString());
				cmWorld.NeedsPermission = LastResult.getBoolean(CMWorldTable.NeedsPermission.toString());

				cmWorlds.add(cmWorld);
			}
		}
		catch (Exception e)
		{
			String errorMessage = _config.Messages.World.FailedToLoadAllWorlds + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
		}

		CloseDataBase();

		_logger.SendToConsole(Module.DataBase, MessageType.Info, "Found <" + cmWorlds.size() + "> worlds.");
		
		return cmWorlds;
	}

	public int GetAmountOfRegisteredPlayers()
	{
		int players;
		String sqlMessage = _mySQLMessages.GetAmountOfRegisteredPlayers();
		
		players = GetInteger(sqlMessage);
		
		_logger.SendToConsole(Module.DataBase, MessageType.Question, "How many players are registered?");

		return players;
	}

	public void RegisterWarp(CMWarp cmWarp)
	{
		String sqlCommand = _mySQLMessages.GenerateWarp(cmWarp);
		
		_logger.SendToConsole(Module.DataBase, MessageType.Command, "Registering new warp.");

		SendData(sqlCommand);
	}

	public void UpdateWarp(CMWarp cmWarp)
	{
		String sqlCommand = _mySQLMessages.UpdateWarp(cmWarp);
		
		_logger.SendToConsole(Module.DataBase, MessageType.Command, "Updating warp.");

		SendData(sqlCommand);
	}

	public boolean DoesWarpExist(CMWarp cmWarp)
	{
		boolean doesWarpExist;
		String mysqlMessage = _mySQLMessages.DoesWarpExist(cmWarp);

		_logger.SendToConsole(Module.DataBase, MessageType.Question, "Does warp exists?");
		
		doesWarpExist = GetBoolean(mysqlMessage);

		return doesWarpExist;
	}

	public ArrayList<CMWarp> LoadAllWarps()
	{
		String warpName;
		String worldName;
		World warpWorld;
		double x;
		double y;
		double z;
		float yaw;
		float pitch;
		CMWarp cmWarp;
		Location location;
		
		_logger.SendToConsole(Module.DataBase, MessageType.Info, "Load all warps.");

		ArrayList<CMWarp> warps = new ArrayList<CMWarp>();
		String sqlCommand = _mySQLMessages.LoadAllWarps();

		CheckMySQLCommand(sqlCommand);

		OpenDataBase();

		TryExecuteCommand(sqlCommand);

		try
		{
			LastResult = statement.getResultSet();

			while (LastResult.next())
			{
				cmWarp = null;

				warpName = LastResult.getString(CMWarpTable.WarpName.toString());
				worldName = LastResult.getString(CMWarpTable.World.toString());
				
				worldName = _config.Worlds.RemoveFolderName(worldName);
				
				warpWorld = _worldSystem.GetWorldPerName(worldName).BukkitWorld;
				x = LastResult.getDouble(CMWarpTable.X.toString());
				y = LastResult.getDouble(CMWarpTable.Y.toString());
				z = LastResult.getDouble(CMWarpTable.Z.toString());
				yaw = LastResult.getFloat(CMWarpTable.Yaw.toString());
				pitch = LastResult.getFloat(CMWarpTable.Pitch.toString());

				location = new Location(warpWorld, x, y, z, yaw, pitch);
				cmWarp = new CMWarp(warpName, location);

				warps.add(cmWarp);
			}
		}
		catch (Exception e)
		{
			String errorMessage = "Error while loading all warps. " + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
		}

		CloseDataBase();

		return warps;
	}

	public void UpdateRank(CMPlayer cmPlayer)
	{
		_logger.SendToConsole(Module.DataBase, MessageType.Info, "Update rank.");
		
		SendData(_mySQLMessages.UpdateRank(cmPlayer));
	}

	
	public List<CMRegion> LoadAllRegions()
	{
		List<CMRegion> _cmRegions = new ArrayList<CMRegion>();		
		String sqlCommand = _mySQLMessages.GetAllRegions();
		
		boolean isActive;
		String worldName;
		String regionName;
		String message;
		String playerUUIDText;
		UUID playerUUID;
		CMPlayer cmPlayer;
		CMLocation cmLocation;
		CMRegion cmRegion;	
		CMWorld cmWorld;
		Location locationA;
		Location locationB;
		int xA;
		int yA;
		int zA;
		int xB;
		int yB;
		int zB;
		boolean ignoreHight;
		boolean protectedRegion;
		boolean protectAccess;
		
		_logger.SendToConsole(Module.DataBase, MessageType.Loading, "Loading all regions...");
		
		CheckMySQLCommand(sqlCommand);

		OpenDataBase();

		TryExecuteCommand(sqlCommand);

		try
		{
			LastResult = statement.getResultSet();

			while (LastResult.next())
			{
				cmLocation = null;
				
				// Region
				regionName = LastResult.getString(CMRegionTable.RegionName.toString());		
				playerUUIDText = LastResult.getString(CMRegionTable.PlayerUUID.toString());
				
				if(playerUUIDText != null)
				{
					playerUUID = UUID.fromString(playerUUIDText);			
				
					cmPlayer = _playerSystem.GetPlayer(playerUUID);		
				}
				else
				{
					cmPlayer = null;
				}
				
			
				isActive = LastResult.getBoolean(CMRegionTable.IsActive.toString());
				ignoreHight = LastResult.getBoolean(CMRegionTable.IgnoreHight.toString());
				protectedRegion = LastResult.getBoolean(CMRegionTable.Protected.toString());
				protectAccess = LastResult.getBoolean(CMRegionTable.ProtectAccess.toString());
								
				
				// Location
				worldName = LastResult.getString(CMAreaTable.WorldName.toString());	
								
				xA = LastResult.getInt(CMAreaTable.XA.toString());	
				yA = LastResult.getInt(CMAreaTable.YA.toString());	
				zA = LastResult.getInt(CMAreaTable.ZA.toString());	
				
				xB = LastResult.getInt(CMAreaTable.XB.toString());	
				yB = LastResult.getInt(CMAreaTable.YB.toString());	
				zB = LastResult.getInt(CMAreaTable.ZB.toString());	
				
				cmWorld = _worldSystem.GetWorldPerName(worldName);

						if(cmWorld != null)
						{
							if(cmWorld.BukkitWorld == null)
							{
								_logger.SendToConsole(Module.DataBase, MessageType.Warning, "World <" + cmWorld.Name + "> is unloaded! Region can't be used.");
							
								isActive = false;
							}
												
							locationA = new Location(cmWorld.BukkitWorld, xA, yA, zA);
							locationB = new Location(cmWorld.BukkitWorld, xB, yB, zB);
							
							cmLocation = new CMLocation(locationA, locationB);								
									
							
							cmRegion = new CMRegion(isActive, regionName, cmPlayer, cmLocation);

							// Add after Values
							{								
								cmRegion.AllowAccess = protectAccess;
								cmRegion.IgnoreHight = ignoreHight;
								cmRegion.Protected = protectedRegion;
							}
							
							_cmRegions.add(cmRegion);
						
						}
						else
						{
							message = "World is not loaded! region <" + regionName + "> skipped";
							
							_logger.SendToConsole(Module.DataBase, MessageType.Warning, message);
						} 			
			}
		}
		catch (Exception e)
		{
			message = "Failed to Load all regions. " + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, message);
		}

		CloseDataBase();				
		
		return _cmRegions;
	}
	
	public void SaveLocation(CMLocation cmLocation)
	{		
		SendData(_mySQLMessages.CreateLocation(cmLocation));	
	}
	
	public String GetPlayerNameFromIP(String ip) 
	{
		int results = 0;
		String playerName = null;
		String sqlCommand = _mySQLMessages.GetPlayerPerIP(ip);

		CheckMySQLCommand(sqlCommand);

		OpenDataBase();

		TryExecuteCommand(sqlCommand);

		try
		{
			LastResult = statement.getResultSet();

			while (LastResult.next())
			{
				results++;

				playerName = LastResult.getString(CMPlayerTable.PlayerName.toString());
				
				if(results >= 2)
				{
					playerName = null;
					break;
				}
			}
		}
		catch (Exception e)
		{
			String errorMessage = /*_config.Messages.Warps.FailedToLoadAllWarps + */_config.Messages.ConsoleIO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
		}

		CloseDataBase();

		return playerName;
	}

	public List<CMPermission> LoadAllPermissions() 
	{
		List<CMPermission> permissions = new ArrayList<CMPermission>();		
		String sqlCommand = _mySQLMessages.GetAllPermissions();
				
		String rankName;
		String permissionName;
		CMPermission cmPermission;
		CMRank cmRank;		
		
		_logger.SendToConsole(Module.DataBase, MessageType.Info, "Loading all permissions...");		
		
		CheckMySQLCommand(sqlCommand);

		OpenDataBase();

		TryExecuteCommand(sqlCommand);

		try
		{
			LastResult = statement.getResultSet();

			while (LastResult.next())
			{
				rankName = LastResult.getString(CMPermissionTable.RankName.toString());
				permissionName = LastResult.getString(CMPermissionTable.PermissionName.toString());

				cmRank = _rankSystem.GetRank(rankName);
				
				cmPermission = new CMPermission(permissionName, cmRank);
				
				permissions.add(cmPermission);
				
			}
		}
		catch (Exception e)
		{
			String errorMessage = /*_config.Messages.Warps.FailedToLoadAllWarps + */_config.Messages.ConsoleIO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
		}

		CloseDataBase();
		
		return permissions;
	}	
	
	public void ChangePlayerOperatorStatus(CMPlayer cmPlayer, boolean op)
	{
		String sqlmessage = "update player set `OP` = " + (op ? 1 : 0) + " where `UUID` like " + cmPlayer.PlayerUUID.toString();
	
		SendData(sqlmessage);
	}
}