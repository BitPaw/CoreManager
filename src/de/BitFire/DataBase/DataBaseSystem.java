package de.BitFire.DataBase;

import java.sql.ResultSet;

import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Configuration.Config;
import de.BitFire.Core.Exception.SystemNotActiveException;

public class DataBaseSystem extends BaseSystem implements ISystem
{
	private static DataBaseSystem _instance;
	public DataBaseData Data;
	
	private Logger _logger;
	private Config _config;
	
	public DataBasePlayer Player;
	public DataBaseWarp Warp;
	public DataBaseWorld World;
	public DataBaseRegion Region;
	public DataBaseRank Rank;
	public DataBasePermission Permission;	
	public DataBaseNPC NPC;
	public DataBasePortal Portal;
	public DataBasePvP PvP;
	
	private DataBaseSystem() 
	{
		super(Module.DataBase, SystemState.Active, Priority.Essential);
		_instance = this;
		
		Player = new DataBasePlayer(this);
		Warp = new DataBaseWarp(this);
		World = new DataBaseWorld(this);
		Region = new DataBaseRegion(this);
		Rank = new DataBaseRank(this);
		Permission = new DataBasePermission(this);		
		NPC = new DataBaseNPC(this);	
		Portal = new DataBasePortal(this);
		PvP = new DataBasePvP(this);
		Data = new DataBaseData();
	}
	
	public static DataBaseSystem Instance()
	{
		return _instance == null ? new DataBaseSystem() : _instance;
	}

	@Override
	public void LoadReferences() 
	{
		_logger = Logger.Instance();
		_config = Config.Instance();		
	}

	@Override
	public void Reload(final boolean firstRun) throws SystemNotActiveException 
	{
		if(!Information.IsActive())
		{
			throw new SystemNotActiveException();
		}		
	}
	
	public boolean IsConnected()
	{
		try 
		{
			return Data.SQLDatabase.checkConnection();
		}
		catch (Exception e) 
		{
			return false;
		}
	}
	
	public void OpenDataBase()
	{
		try
		{
			if (Data.SQLDatabase == null)
			{
				throw new Exception("MySQL is missing!");
			}

			Data.SQLConnection = Data.SQLDatabase.openConnection();
		}
		catch (Exception e)
		{
			String errorMessage = _config.Message.DataBase.ConnectionFailed + _config.Message.IO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
			return;
		}
	}

	public void CloseDataBase()
	{
		try
		{
			Data.SQLDatabase.closeConnection();
		}
		catch (Exception e)
		{
			String errorMessage = _config.Message.DataBase.ClosingError + _config.Message.IO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
		}
	}

	public boolean CheckMySQLCommand(String sqlMessage)
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
			_logger.SendToConsole(Module.DataBase, MessageType.Error, _config.Message.DataBase.EmptySQLMessage);
		}		
	
		return !isEmptySQLMessage;
	}

	public void TryExecuteCommand(String sqlMessage)
	{
		String sqlCommandMessage = _config.Message.IO.NewLine + _config.Message.DataBase.MessageHeader + _config.Message.IO.NewLine + sqlMessage + _config.Message.IO.NewLine + _config.Message.DataBase.MessageFooter;

		try
		{
			_logger.SendToConsole(Module.DataBase, MessageType.Command, sqlCommandMessage);

			Data.SQLStatement = Data.SQLConnection.createStatement();
			Data.SQLStatement.execute(sqlMessage);
		}
		catch (Exception e)
		{
			String errorMessage = _config.Message.DataBase.StatementError + _config.Message.IO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
		}
	}

	public int GetInteger(String sqlMessage)
	{
		int returnValue = 0;
		ResultSet resultSet;
		
		CheckMySQLCommand(sqlMessage);

		OpenDataBase();

		TryExecuteCommand(sqlMessage);

		try
		{
			resultSet = Data.SQLStatement.getResultSet();

			if (resultSet.next())
			{
				returnValue = resultSet.getInt(1);
			}
		}
		catch (Exception e)
		{
			String errorMessage = _config.Message.DataBase.FailedToGetInteger + _config.Message.IO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
		}

		CloseDataBase();

		return returnValue;
	}

	public boolean GetBoolean(String sqlMessage)
	{
		boolean returnValue = false;
		ResultSet resultSet;

		CheckMySQLCommand(sqlMessage);

		OpenDataBase();

		TryExecuteCommand(sqlMessage);

		try
		{
			resultSet = Data.SQLStatement.getResultSet();

			if (resultSet.next())
			{
				int value = resultSet.getInt(1);

				if (value >= 1)
				{
					returnValue = true;
				}
			}
		}
		catch (Exception e)
		{
			String errorMessage = _config.Message.DataBase.FailedToGetBoolean + _config.Message.IO.ErrorArrow + e.getMessage();

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
}