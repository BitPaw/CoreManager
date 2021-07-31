package de.BitFire.DataBase;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Configuration.Config;
import de.BitFire.Economy.EconemyAccount;
import de.BitFire.Player.CMPlayer;
import de.BitFire.Player.CMPlayerTable;
import de.BitFire.Player.PlayerInformation;
import de.BitFire.Player.Exception.InvalidPlayerUUID;
import de.BitFire.Player.Exception.PlayerNotFoundException;
import de.BitFire.Rank.RankSystem;
import de.BitFire.Time.TimePoint;

public class DataBasePlayer 
{

	private DataBaseSystem _dataBaseSystem;
	private Logger _logger;
	private Config _config;
	//private MessageTags _messageTags;
	private RankSystem _rankSystem;

	public DataBasePlayer(DataBaseSystem dataBaseSystem)
	{
		_dataBaseSystem = dataBaseSystem;
		
		_logger = Logger.Instance();
		_config = Config.Instance();
		//_messageTags = MessageTags.Instance();
		_rankSystem = RankSystem.Instance();
	}
public String GetPlayerNameFromIP(String ip) 
{
	int results = 0;
	String playerName = null;
	String sqlCommand = _dataBaseSystem.Data.SQLMessages.GetPlayerPerIP(ip);
	ResultSet resultSet;

	_dataBaseSystem.CheckMySQLCommand(sqlCommand);
	_dataBaseSystem.OpenDataBase();
	_dataBaseSystem.TryExecuteCommand(sqlCommand);

	try
	{
		resultSet = _dataBaseSystem.Data.SQLStatement.getResultSet();

		while (resultSet.next())
		{
			results++;

			playerName = resultSet.getString(CMPlayerTable.Name.toString());
			
			if(results >= 2)
			{
				playerName = null;
				break;
			}
		}
	}
	catch (Exception e)
	{
		String errorMessage = /*_config.Messages.Warps.FailedToLoadAllWarps + */_config.Message.IO.ErrorArrow + e.getMessage();

		_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
	}

	_dataBaseSystem.CloseDataBase();

	return playerName;
}

	public void ChangePlayerOperatorStatus(CMPlayer cmPlayer, boolean op)
	{
		String sqlmessage = "update player set `OP` = " + (op ? 1 : 0) + " where `UUID` like " + cmPlayer.Information.PlayerUUID.toString();
	
		_dataBaseSystem.SendData(sqlmessage);
	}
	
	public void UpdatePlayer(Player player) throws PlayerNotFoundException, InvalidPlayerUUID
	{
		String sqlCommand = _dataBaseSystem.Data.SQLMessages.UpdatePlayer(player);

		_dataBaseSystem.SendData(sqlCommand);
	}

	public boolean DoesPlayerExist(Player player)
	{
		boolean playerExists;
		String sqlMessage = _dataBaseSystem.Data.SQLMessages.DoesPlayerExist(player);
		String message;

		_logger.SendToConsole(Module.DataBase, MessageType.Question, "Does player exist?");

		playerExists = _dataBaseSystem.GetBoolean(sqlMessage);
		

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

	public int GetAmountOfRegisteredPlayers()
	{
		int players;
		String sqlMessage = _dataBaseSystem.Data.SQLMessages.GetAmountOfRegisteredPlayers();
		
		players = _dataBaseSystem.GetInteger(sqlMessage);
		
		_logger.SendToConsole(Module.DataBase, MessageType.Question, "How many players are registered?");

		return players;
	}
	
	public CMPlayer LoadPlayer(Player player) throws Exception
	{
		CMPlayer cmPlayer = null;	
		UUID uuid = player.getUniqueId();
		String sqlMessage = _dataBaseSystem.Data.SQLMessages.GetPlayer(uuid);
		ResultSet resultSet;		
		
		_dataBaseSystem.CheckMySQLCommand(sqlMessage);
		_dataBaseSystem.OpenDataBase();
		_dataBaseSystem.TryExecuteCommand(sqlMessage);

		try
		{
			resultSet = _dataBaseSystem.Data.SQLStatement.getResultSet();

			while (resultSet.next())
			{				
				final int playerID = resultSet.getInt(CMPlayerTable.ID.toString());
				final UUID playerUUID = UUID.fromString(resultSet.getString(CMPlayerTable.UUID.toString()));
				final boolean isOP = resultSet.getBoolean(CMPlayerTable.OP.toString());
				final boolean isCracked = resultSet.getBoolean(CMPlayerTable.Cracked.toString());
				final TimePoint registered = new TimePoint(resultSet.getTimestamp(CMPlayerTable.Registered.toString()));
				final String playerName = resultSet.getString(CMPlayerTable.Name.toString());		
				
				PlayerInformation playerInformation = new PlayerInformation(playerID, playerUUID, isOP,isCracked, playerName, registered);				
				
				playerInformation.IsBanned = resultSet.getBoolean(CMPlayerTable.Banned.toString());	
				playerInformation.CustomName = resultSet.getString(CMPlayerTable.CustomName.toString());				
				playerInformation.Money = new EconemyAccount(resultSet.getFloat(CMPlayerTable.Money.toString()));
				playerInformation.IP = resultSet.getString(CMPlayerTable.IP.toString());					
				playerInformation.LastSeen = new TimePoint(resultSet.getTimestamp(CMPlayerTable.LastSeen.toString()));
				playerInformation.RankGroup = _rankSystem.GetRank(resultSet.getInt(CMPlayerTable.RankID.toString()));
				
				
				cmPlayer = new CMPlayer(playerInformation);
				cmPlayer.AttachBukkitPlayer(player);	
				
			}
		}
		catch (Exception e)
		{
			String errorMessage = _config.Message.Player.FailedToLoadPlayer + _config.Message.IO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
		}

		_dataBaseSystem.CloseDataBase();		
		
		if(cmPlayer == null)
		{
			throw new Exception("Player creation Error");
		}
		
		return cmPlayer;
	}
	
	public void UpdateCustomName(CMPlayer cmPlayer)
	{
		String name = cmPlayer.Information.CustomName;
		UUID uuid = cmPlayer.BukkitPlayer.getUniqueId();
		String sql = _dataBaseSystem.Data.SQLMessages.UpdatePlayerCustomName(uuid, name);

		_logger.SendToConsole(Module.DataBase, MessageType.Info, "&eUpdating &7player &6<&e" + cmPlayer.Information.PlayerName + "&6>&7.");
		
		_dataBaseSystem.SendData(sql);
	}

	public CMPlayer RegisterNewPlayer(Player player, int nrOfPlayers) throws PlayerNotFoundException, InvalidPlayerUUID
	{
		
		CMPlayer cmPlayer;
		String sqlCommand;
		//String text = _config.Messages.Player.RegisteringNewPlayer;
		
		
		if (player == null)
		{
			throw new NullPointerException(_config.Message.Player.PlayerParameterWasNull);
		}	
		
		_logger.SendToConsole(Module.DataBase, MessageType.Info, "&7Registering &enew &7player &6<&e" + player.getName() + "&6>&7...");
			
		cmPlayer = new CMPlayer(player);		
		cmPlayer.Information.ID = nrOfPlayers;
		
		// Create SQL statement
		{
			sqlCommand = "insert into " + CoreManagerTables.player.toString() + " values \n (\n";

			sqlCommand += MySQLUtillity.StringToMySQLText(cmPlayer.Information.PlayerUUID.toString());
			sqlCommand += MySQLUtillity.IntegerToMySQLText(cmPlayer.Information.ID);
			sqlCommand += MySQLUtillity.BooleanToMySQLText(cmPlayer.Information.IsOP);
			sqlCommand += MySQLUtillity.BooleanToMySQLText(cmPlayer.Information.IsCracked);
			sqlCommand += MySQLUtillity.BooleanToMySQLText(cmPlayer.Information.IsBanned);
			sqlCommand += MySQLUtillity.StringToMySQLText(cmPlayer.Information.PlayerName);
			sqlCommand += MySQLUtillity.StringToMySQLText(cmPlayer.Information.CustomName);
			sqlCommand += MySQLUtillity.FloatToMySQLText(cmPlayer.Information.Money.GetValue());			
			sqlCommand += MySQLUtillity.StringToMySQLText(cmPlayer.Information.IP);
			sqlCommand += MySQLUtillity.StringToMySQLText(cmPlayer.Information.Registered.ToSQLCompatibleString()); 
			sqlCommand += MySQLUtillity.StringToMySQLText(cmPlayer.Information.LastSeen.ToSQLCompatibleString());			
			sqlCommand += MySQLUtillity.LastInt(cmPlayer.Information.RankGroup.ID);
			
			sqlCommand += ");";
		}
				
		_dataBaseSystem.SendData(sqlCommand);	
		
		cmPlayer.GetPlayerInfo(Bukkit.getConsoleSender());
		
		return cmPlayer;
	}
	
	public void UpdateAccountBalance(CMPlayer player)
	{
		String sqlCommand = "update player set `Money` = " + player.Information.Money.GetValue() + " where UUID like \"" + player.Information.PlayerUUID + "\"";

		_dataBaseSystem.SendData(sqlCommand);
	}
	public List<CMPlayer> LoadAllPlayers() 
	{
		List<CMPlayer> cmPlayerList = new ArrayList<CMPlayer>();
		String sqlMessage = _dataBaseSystem.Data.SQLMessages.GetAllPlayersSQLCode();					
		
		_dataBaseSystem.CheckMySQLCommand(sqlMessage);
		_dataBaseSystem.OpenDataBase();
		_dataBaseSystem.TryExecuteCommand(sqlMessage);

		try
		{
			ResultSet resultSet = _dataBaseSystem.Data.SQLStatement.getResultSet();

			while (resultSet.next())
			{						
				final int playerID = resultSet.getInt(CMPlayerTable.ID.toString());
				final UUID playerUUID = UUID.fromString(resultSet.getString(CMPlayerTable.UUID.toString()));
				final boolean isOP = resultSet.getBoolean(CMPlayerTable.OP.toString());
				final boolean isCracked = resultSet.getBoolean(CMPlayerTable.Cracked.toString());
				
				final String lastSeenString = resultSet.getString(CMPlayerTable.LastSeen.toString());
				final String regsieterdString = resultSet.getString(CMPlayerTable.Registered.toString());				
				
				final Timestamp lastSeenTime = Timestamp.valueOf(lastSeenString);
				final Timestamp regsieterdTime = Timestamp.valueOf(regsieterdString);
				
				final TimePoint registered = new TimePoint(regsieterdTime);
				final TimePoint lastSeen = new TimePoint(lastSeenTime);
				
				final String playerName = resultSet.getString(CMPlayerTable.Name.toString());	
				
				final String gameModeText = resultSet.getString(CMPlayerTable.GameMode.toString());	
				final GameMode gameMode = GameMode.valueOf(gameModeText);
				
				PlayerInformation playerInformation = new PlayerInformation(playerID, playerUUID, isOP,isCracked, playerName, registered);								
				playerInformation.IsBanned = resultSet.getBoolean(CMPlayerTable.Banned.toString());	
				playerInformation.CustomName = resultSet.getString(CMPlayerTable.CustomName.toString());				
				playerInformation.Money = new EconemyAccount(resultSet.getFloat(CMPlayerTable.Money.toString()));
				playerInformation.IP = resultSet.getString(CMPlayerTable.IP.toString());					
				playerInformation.LastSeen = lastSeen;
				playerInformation.RankGroup = _rankSystem.GetRank(resultSet.getInt(CMPlayerTable.RankID.toString()));
				playerInformation.PlayerGameMode = gameMode;
				
				CMPlayer cmPlayer = new CMPlayer(playerInformation);		
				
				cmPlayerList.add(cmPlayer);
			}
		}
		catch (Exception e)
		{
			String errorMessage = "&cFailedToLoadAllPlayers" + _config.Message.IO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
			
			e.printStackTrace();
		}

		_dataBaseSystem.CloseDataBase();		
				
		return cmPlayerList;
	}
}
