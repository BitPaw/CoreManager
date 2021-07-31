package de.SSC.CoreManager.DataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Logger;
import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DataTypes.CMPlayer;
import de.SSC.CoreManager.DataBase.DataTypes.CMPlayerList;
import de.SSC.CoreManager.DataBase.DataTypes.CMRank;
import de.SSC.CoreManager.DataBase.DataTypes.CMRankList;
import de.SSC.CoreManager.DataBase.Tables.CMPlayerTable;
import de.SSC.CoreManager.DataBase.Tables.CMRanksTable;
import de.SSC.MySQL.MySQL;


public class DatabaseManager 
{
	private MySQL _mySQL;
	private static DatabaseManager _instance;
	private Logger _logger;
	private Config _config;	
	private static CMRankList _rankList;
	private CMPlayerList _cmPlayerList;
	private CoreManagerMySQLMessages _mySQLMessages; 
	
	private Connection connection = null;
	private Statement statement = null; 
	private ResultSet LastResult = null;
	
	private DatabaseManager()
	{
		_logger = Logger.Instance();
		_config = Config.Instance();	
		_cmPlayerList = CMPlayerList.Instance();
		_mySQLMessages = CoreManagerMySQLMessages.Instance();
		
		_mySQL = new MySQL(_config.MySQL.Hostname, _config.MySQL.Port, _config.MySQL.Database, _config.MySQL.Username, _config.MySQL.Password);		
	}
	
	public static DatabaseManager Instance()
	{
	 if(_instance == null)	
	 {
		 _instance = new DatabaseManager();
		 
		  _rankList = CMRankList.Instance();
	 }
	 
	 return _instance;
		 
	}
	
	
	private void OpenDataBase()
	{
		try
		{
			connection =_mySQL.openConnection();			
		}		
		catch(Exception e)
		{
			
			_logger.DataBaseError(_config.Messages.MySQL.SQLConnectionFailed + "\n" +	e.getMessage());
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
			_logger.DataBaseError(_config.Messages.MySQL.SQLClosingError + "\n" + e.getMessage());
		}	
	}
	
	
	
	private boolean CheckMySQLCommand(String sqlMessage)
	{
		if(sqlMessage.isEmpty())
		{
			_logger.DataBaseError("You are tring to send an empty SQL command...\nWtf are you doing?");	
			return false;
		}
		else
		{
			return true;
		}
	}
	
	
	
	
	private void TryExecuteCommand(String sqlMessage)
	{
		try
		{
			_logger.DataBaseCommand(sqlMessage);
			
			statement = connection.createStatement();		
			statement.execute(sqlMessage);	
		}
		catch(Exception e)
		{
			_logger.DataBaseError(_config.Messages.MySQL.SQLStatementError + "\n" + e.getMessage() + "\n\n" +  _config.Messages.MySQL.SQLMessageHeader + "\n" + sqlMessage + "\n"+ _config.Messages.MySQL.SQLMessageFooter);
		}
	}
	
	
	
	
	
	public int GetNumber(String sqlMessage)
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
			_logger.DataBaseError(e.getMessage() + "in LastResult.getInt()");
		}	
		
	    CloseDataBase();
	    
	    return returnValue;
	}
	

	
	public boolean GetBool(String sqlMessage)
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
			_logger.DataBaseError(e.getMessage() + "in LastResult.getInt()");
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
		String text = _config.Messages.MySQL.SQLRegisteringNewPlayer;
		
		if(player == null)
		{
			_logger.WriteWarning("Error, illegal usage. Player is null");
			return;
		}
		
		
		text = text.replace("%player%", "&6&n" + player.getDisplayName());		
		_logger.DataBaseInfo(text);		
		
		try
		{
			cmPlayer = new CMPlayer(player);
		}
		catch(Exception e)
		{
			_logger.DataBaseError("Error while creating a CMPlayer." + "\n" + e.getMessage());
			return;
		}
		
		try
		{			
			sqlCommand = cmPlayer.ToMySQLInsertMessage();
		}
		catch(Exception e)
		{
			_logger.DataBaseError("Error in RegisterNewPlayer with sqlCommand." + "\n" + e.getMessage());
			return;
		}
		
		try
		{			
			SendData(sqlCommand);	
		}
		catch(Exception e)
		{
			_logger.DataBaseError(_config.Messages.MySQL.SQLRegistrationError + "\n" + e.getMessage());
			return;
		}
		
	    _cmPlayerList.AddPlayer(cmPlayer);
	    cmPlayer.GetPlayerInfo(Bukkit.getConsoleSender());
	}


@SuppressWarnings("unused")
private ArrayList<String> GetList()
{
	ArrayList<String> list = new ArrayList<String>();
	
	
	
	
	
	return list;
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
		_logger.DataBaseError("LoadAllRanks : " + e.getMessage());
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
			cmPlayer.BukkitPlayer = player;
			cmPlayer.CustomName =  LastResult.getString(CMPlayerTable.CustomName.toString());
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
		}
	} 
	catch (Exception e) 
	{
		_logger.DataBaseError("LoadAllRanks : " + e.getMessage());
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
	      playerExists = GetBool(sqlMessage);
	
		return playerExists;
	}

	public void UpdatePlayerLastSeen(Player player) 
	{
		UUID uuid =player.getUniqueId();
		String sqlCommand = _mySQLMessages.UpdatePlayerLastSeen(uuid);
				
		SendData(sqlCommand);			
	}
}