package de.SSC.CoreManager.DataBase;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Chat.Logger;
import de.SSC.CoreManager.Chat.MessageType;
import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Economy.EconemyAccount;
import de.SSC.CoreManager.Player.CMPlayer;
import de.SSC.CoreManager.Player.CMPlayerTable;
import de.SSC.CoreManager.Player.PlayerState;
import de.SSC.CoreManager.Player.Exception.InvalidPlayerUUID;
import de.SSC.CoreManager.Player.Exception.PlayerNotFoundException;
import de.SSC.CoreManager.Rank.RankSystem;

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
		String errorMessage = /*_config.Messages.Warps.FailedToLoadAllWarps + */_config.Messages.ConsoleIO.ErrorArrow + e.getMessage();

		_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
	}

	_dataBaseSystem.CloseDataBase();

	return playerName;
}

	public void ChangePlayerOperatorStatus(CMPlayer cmPlayer, boolean op)
	{
		String sqlmessage = "update player set `OP` = " + (op ? 1 : 0) + " where `UUID` like " + cmPlayer.PlayerUUID.toString();
	
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
				
				cmPlayer = new CMPlayer(playerUUID);				
					
				cmPlayer.State = PlayerState.Online;
				cmPlayer.ID = playerID;
				cmPlayer.IsOP = resultSet.getBoolean(CMPlayerTable.OP.toString());
				cmPlayer.IsCracked = resultSet.getBoolean(CMPlayerTable.Cracked.toString());
				cmPlayer.IsBanned = resultSet.getBoolean(CMPlayerTable.Banned.toString());
				cmPlayer.PlayerName = resultSet.getString(CMPlayerTable.Name.toString());				
				cmPlayer.CustomName = resultSet.getString(CMPlayerTable.CustomName.toString());				
				cmPlayer.Money = new EconemyAccount(resultSet.getFloat(CMPlayerTable.Money.toString()));
				cmPlayer.IP = resultSet.getString(CMPlayerTable.IP.toString());					
				cmPlayer.Registered = resultSet.getTimestamp(CMPlayerTable.Registered.toString());
				cmPlayer.LastSeen = resultSet.getTimestamp(CMPlayerTable.LastSeen.toString());
				cmPlayer.RankGroup = _rankSystem.GetRank(resultSet.getString(CMPlayerTable.RankID.toString()));
				
				cmPlayer.MergeBukkitPlayer(player);
				
				cmPlayer.SetDataForServerSide();
				
				cmPlayer.GetPlayerInfo(Bukkit.getConsoleSender());
			}
		}
		catch (Exception e)
		{
			String errorMessage = _config.Messages.Player.FailedToLoadPlayer + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();

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
		String name = cmPlayer.CustomName;
		UUID uuid = cmPlayer.BukkitPlayer.getUniqueId();
		String sql = _dataBaseSystem.Data.SQLMessages.UpdatePlayerCustomName(uuid, name);

		_logger.SendToConsole(Module.DataBase, MessageType.Info, "&eUpdating &7player &6<&e" + cmPlayer.PlayerName + "&6>&7.");
		
		_dataBaseSystem.SendData(sql);
	}

	public CMPlayer RegisterNewPlayer(Player player, int nrOfPlayers) throws PlayerNotFoundException, InvalidPlayerUUID
	{
		
		CMPlayer cmPlayer;
		String sqlCommand;
		//String text = _config.Messages.Player.RegisteringNewPlayer;
		
		
		if (player == null)
		{
			throw new NullPointerException(_config.Messages.Player.PlayerParameterWasNull);
		}	

		final UUID playerUUID = player.getUniqueId();
		
		_logger.SendToConsole(Module.DataBase, MessageType.Info, "&7Registering &enew &7player &6<&e" + player.getName() + "&6>&7...");
			
		cmPlayer = new CMPlayer(playerUUID);		
		cmPlayer.ID = nrOfPlayers;
		cmPlayer.MergeBukkitPlayer(player);
		
		// Create SQL statement
		{
			sqlCommand = "insert into " + CoreManagerTables.player.toString() + " values \n (\n";

			sqlCommand += MySQLUtillity.StringToMySQLText(cmPlayer.PlayerUUID.toString());
			sqlCommand += MySQLUtillity.IntegerToMySQLText(cmPlayer.ID);
			sqlCommand += MySQLUtillity.BooleanToMySQLText(cmPlayer.IsOP);
			sqlCommand += MySQLUtillity.BooleanToMySQLText(cmPlayer.IsCracked);
			sqlCommand += MySQLUtillity.BooleanToMySQLText(cmPlayer.IsBanned);
			sqlCommand += MySQLUtillity.StringToMySQLText(cmPlayer.PlayerName);
			sqlCommand += MySQLUtillity.StringToMySQLText(cmPlayer.CustomName);
			sqlCommand += MySQLUtillity.FloatToMySQLText(cmPlayer.Money.GetValue());			
			sqlCommand += MySQLUtillity.StringToMySQLText(cmPlayer.IP);
			sqlCommand += MySQLUtillity.StringToMySQLText(cmPlayer.Registered.toString()); 
			sqlCommand += MySQLUtillity.StringToMySQLText(cmPlayer.LastSeen.toString());			
			sqlCommand += MySQLUtillity.LastInt(cmPlayer.RankGroup.ID);
			
			sqlCommand += ");";
		}
				
		_dataBaseSystem.SendData(sqlCommand);	
		
		cmPlayer.GetPlayerInfo(Bukkit.getConsoleSender());
		cmPlayer.SetDataForServerSide();
		
		return cmPlayer;
	}
	
	public void UpdateAccountBalance(CMPlayer player)
	{
		String sqlCommand = "update player set `Money` = " + player.Money.GetValue() + " where UUID like \"" + player.PlayerUUID + "\"";

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
				final UUID playerUUID =  UUID.fromString(resultSet.getString(CMPlayerTable.UUID.toString()));
				final String lastSeenTimeStamp = resultSet.getString(CMPlayerTable.LastSeen.toString());
				final String registeredTimeStamp = resultSet.getString(CMPlayerTable.Registered.toString());
				final int rankID = resultSet.getInt(CMPlayerTable.RankID.toString());
				
				CMPlayer cmPlayer = new CMPlayer(playerUUID);				
					
				cmPlayer.State = PlayerState.Offline;
				cmPlayer.ID = playerID;
				cmPlayer.IsOP = resultSet.getBoolean(CMPlayerTable.OP.toString());
				cmPlayer.IsCracked = resultSet.getBoolean(CMPlayerTable.Cracked.toString());
				cmPlayer.IsBanned = resultSet.getBoolean(CMPlayerTable.Banned.toString());
				cmPlayer.PlayerName = resultSet.getString(CMPlayerTable.Name.toString());				
				cmPlayer.CustomName = resultSet.getString(CMPlayerTable.CustomName.toString());				
				cmPlayer.Money = new EconemyAccount(resultSet.getFloat(CMPlayerTable.Money.toString()));
				cmPlayer.IP = resultSet.getString(CMPlayerTable.IP.toString());					
				cmPlayer.Registered = Timestamp.valueOf(registeredTimeStamp);
				cmPlayer.LastSeen = Timestamp.valueOf(lastSeenTimeStamp);
				cmPlayer.RankGroup = _rankSystem.GetRank(rankID);
				
				//cmPlayer.SetDataForServerSide();
				
				cmPlayer.GetPlayerInfo(Bukkit.getConsoleSender());
				
				cmPlayerList.add(cmPlayer);
			}
		}
		catch (Exception e)
		{
			String errorMessage = "&cFailedToLoadAllPlayers" + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
			
			e.printStackTrace();
		}

		_dataBaseSystem.CloseDataBase();		
				
		return cmPlayerList;
	}
}
