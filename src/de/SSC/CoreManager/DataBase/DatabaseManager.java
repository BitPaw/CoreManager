package de.SSC.CoreManager.DataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DataTypes.CMPlayer;
import de.SSC.CoreManager.DataBase.DataTypes.CMPlayerList;
import de.SSC.CoreManager.DataBase.DataTypes.CMRank;
import de.SSC.CoreManager.DataBase.DataTypes.CMRankList;
import de.SSC.CoreManager.DataBase.DataTypes.CMWarp;
import de.SSC.CoreManager.DataBase.DataTypes.CMWorld;
import de.SSC.CoreManager.DataBase.DataTypes.CMWorldList;
import de.SSC.CoreManager.DataBase.Tables.CMPlayerTable;
import de.SSC.CoreManager.DataBase.Tables.CMRanksTable;
import de.SSC.CoreManager.DataBase.Tables.CMWarpTable;
import de.SSC.CoreManager.DataBase.Tables.CMWorldTable;
import de.SSC.CoreManager.Messages.Logger;
import de.SSC.CoreManager.Messages.MessageType;
import de.SSC.CoreManager.Messages.Module;
import de.SSC.MySQL.MySQL;


public class DatabaseManager 
{
	private MySQL _mySQL;
	private static DatabaseManager _instance;
	
	private Connection connection;
	private Statement statement; 
	private ResultSet LastResult;
	
	private CMRankList _rankList;	
	private Logger _logger;
	private Config _config;	
	private CMPlayerList _cmPlayerList;
	private CMWorldList _cmWorldList;
	private CoreManagerMySQLMessages _mySQLMessages; 		
	
	private DatabaseManager()
	{
		_instance = this;
		
		_logger = Logger.Instance();		
		_config = Config.Instance();	
		_cmPlayerList = CMPlayerList.Instance();
		_mySQLMessages = CoreManagerMySQLMessages.Instance();		
		_rankList = CMRankList.Instance();
		_cmWorldList = CMWorldList.Instance();
				
		_mySQL = new MySQL(_config.MySQL.Hostname, _config.MySQL.Port, _config.MySQL.Database, _config.MySQL.Username, _config.MySQL.Password);		
	
		_logger.SendToConsole(Module.DataBase, MessageType.Online, _config.Messages.ConsoleIO.On);
	}
	
	public static DatabaseManager Instance()
	{ 
		return _instance  == null ? new DatabaseManager() : _instance;		 
	}
		
	private void OpenDataBase()
	{
	
		try
		{
			if(_mySQL == null)
			{
				throw new Exception("MySQL is missing!");
			}				
			
			connection =_mySQL.openConnection();			
		}		
		catch(Exception e)
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
		catch(Exception e)
		{
			String errorMessage = _config.Messages.DataBase.ClosingError + _config.Messages.ConsoleIO.ErrorArrow  + e.getMessage();
			
			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
		}	
	}
	
	private boolean CheckMySQLCommand(String sqlMessage)
	{
		if(sqlMessage.isEmpty())
		{			
			_logger.SendToConsole(Module.DataBase, MessageType.Error, _config.Messages.DataBase.EmptySQLMessage);
			
			return false;
		}
		else
		{
			return true;
		}
	}	
	
	private void TryExecuteCommand(String sqlMessage)
	{
		String sqlCommandMessage =  _config.Messages.ConsoleIO.NewLine + _config.Messages.DataBase.MessageHeader + _config.Messages.ConsoleIO.NewLine + sqlMessage + _config.Messages.ConsoleIO.NewLine + _config.Messages.DataBase.MessageFooter;
		
		try
		{
			_logger.SendToConsole(Module.DataBase, MessageType.Command, sqlCommandMessage);
			
			statement = connection.createStatement();		
			statement.execute(sqlMessage);	
		}
		catch(Exception e)
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

			if(LastResult.next())
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
		boolean returnValue =  false;
		
		CheckMySQLCommand(sqlMessage);				
	
		OpenDataBase();
		
		TryExecuteCommand(sqlMessage);
							
		try 
		{
			LastResult = statement.getResultSet();	

			if(LastResult.next())
			{
				int value = LastResult.getInt(1);
				
				if(value >= 1)
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
	
	public void UpdateCustomName(Player player)
	{
		String name = player.getCustomName();
		UUID uuid = player.getUniqueId();
		String sql = _mySQLMessages.UpdatePlayerCustomName(uuid, name);
			
		SendData(sql);
	}

	public void RegisterNewPlayer(Player player)
	{
	    CMPlayer cmPlayer;
		String sqlCommand;
		String text = _config.Messages.Player.RegisteringNewPlayer;
		
		if(player == null)
		{
			throw new NullPointerException(_config.Messages.Player.PlayerParameterWasNull);
		}		
		
		text = text.replace(_config.Messages.Player.PlayerTag, player.getDisplayName());	
		
		_logger.SendToConsole(Module.DataBase, MessageType.Info, text);		
		
		try
		{
			cmPlayer = new CMPlayer(player);
		}
		catch(Exception e)
		{
			String errorMessage = _config.Messages.Player.FailedToGetPlayerInformation + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();
			
			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);	
			
			return;
		}
		
					
		sqlCommand = cmPlayer.ToMySQLInsertMessage();
	
		
		try
		{			
			SendData(sqlCommand);	
		}
		catch(Exception e)
		{
			String errorMessage = _config.Messages.Player.NewPlayerRegistrationError + _config.Messages.ConsoleIO.NewLine + e.getMessage();
			
			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);	
			
			return;
		}
		
	    _cmPlayerList.AddPlayer(cmPlayer);
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
	
	CheckMySQLCommand(sqlMessage);				
	
	OpenDataBase();
	
	TryExecuteCommand(sqlMessage);
						
	try 
	{
		LastResult = statement.getResultSet();	

		while(LastResult.next())
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

		while(LastResult.next())
		{
			String customName =  LastResult.getString(CMPlayerTable.CustomName.toString());
			
			if(customName.equalsIgnoreCase("null"))
			{
				customName = null;
			}			
			
			cmPlayer.BukkitPlayer = player;
			cmPlayer.CustomName = customName;
			cmPlayer.PlayerUUID =  UUID.fromString(LastResult.getString(CMPlayerTable.PlayerUUID.toString()));
			cmPlayer.IP =  LastResult.getString(CMPlayerTable.IP.toString());
			cmPlayer.IsBanned =  LastResult.getBoolean(CMPlayerTable.Banned.toString());
			cmPlayer.IsOP =  LastResult.getBoolean(CMPlayerTable.OP.toString());
			cmPlayer.IsCracked =  LastResult.getBoolean(CMPlayerTable.Cracked.toString());
			cmPlayer.LastSeen =  LastResult.getTimestamp(CMPlayerTable.LastSeen.toString());
			cmPlayer.Money =  LastResult.getFloat(CMPlayerTable.Money.toString());
			cmPlayer.PlayerName =  LastResult.getString(CMPlayerTable.PlayerName.toString());
			cmPlayer.RankGroup =  _rankList.GetRankFromName(LastResult.getString(CMPlayerTable.RankName.toString()));
			cmPlayer.Registered =  LastResult.getTimestamp(CMPlayerTable.Registered.toString());			
			
			cmPlayer.SetDataForServerSide();
		}
	} 
	catch (Exception e) 
	{
		String errorMessage = _config.Messages.Player.FailedToLoadPlayer + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();
		
		_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);	
	}	
	
    CloseDataBase();
    
    _cmPlayerList.AddPlayer(cmPlayer);
    cmPlayer.GetPlayerInfo(Bukkit.getConsoleSender());
	
	return cmPlayer;
}

	public boolean DoesPlayerExist(Player player)
	{
		boolean playerExists;
	      String sqlMessage = _mySQLMessages.DoesPlayerExist(player);	
	      playerExists = GetBoolean(sqlMessage);
	
		return playerExists;
	}

	public void UpdatePlayerLastSeen(Player player) 
	{
		UUID uuid =player.getUniqueId();
		String sqlCommand = _mySQLMessages.UpdatePlayerLastSeen(uuid);
				
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

			while(LastResult.next())
			{
				CMWorld cmWorld = new CMWorld();	
				
				cmWorld.Name = LastResult.getString(CMWorldTable.WorldName.toString());
				cmWorld.CustomName = LastResult.getString(CMWorldTable.CustomName.toString());
				cmWorld.MapType = WorldType.getByName(LastResult.getString(CMWorldTable.MapType.toString()));
				cmWorld.MapStyle =    World.Environment.valueOf(LastResult.getString(CMWorldTable.MapStyle.toString()));
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
		
		return cmWorlds;		
	}

	public int GetAmountOfRegisteredPlayers() 
	{
		int players;
	      String sqlMessage = _mySQLMessages.GetAmountOfRegisteredPlayers();	
	      players = GetInteger(sqlMessage);
	
		return players;
	}

	public void RegisterWarp(CMWarp cmWarp) 
	{
		String sqlCommand = _mySQLMessages.GenerateWarp(cmWarp);
		
		SendData(sqlCommand);		
	}

	public void UpdateWarp(CMWarp cmWarp) 
	{
		String sqlCommand = _mySQLMessages.UpdateWarp(cmWarp);
				
		SendData(sqlCommand);				
	}

	public boolean DoesWarpExist(CMWarp cmWarp)
	{
		boolean doesWarpExist;
		String mysqlMessage = _mySQLMessages.DoesWarpExist(cmWarp);
		
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
		
		ArrayList<CMWarp> warps = new ArrayList<CMWarp>();		
		String sqlCommand = _mySQLMessages.LoadAllWarps();
		
		CheckMySQLCommand(sqlCommand);				
		
		OpenDataBase();
		
		TryExecuteCommand(sqlCommand);
							
		try 
		{
			LastResult = statement.getResultSet();	

			while(LastResult.next())
			{
				cmWarp = null;
				
				 warpName = LastResult.getString(CMWarpTable.WarpName.toString());
				 worldName = LastResult.getString(CMWarpTable.World.toString());;
				 warpWorld = _cmWorldList.GetWorldPerName(worldName).BukkitWorld;
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
			String errorMessage = /*_config.Messages.Warps.FailedToLoadAllWarps + */_config.Messages.ConsoleIO.ErrorArrow + e.getMessage();
			
			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);			
		}	
		
	    CloseDataBase();
		
		return warps;
	}
}