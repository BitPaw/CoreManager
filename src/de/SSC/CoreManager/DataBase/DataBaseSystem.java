package de.SSC.CoreManager.DataBase;

import java.sql.ResultSet;

import org.bukkit.command.CommandSender;

import de.SSC.CoreManager.Chat.Logger;
import de.SSC.CoreManager.Chat.MessageType;
import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.System.BaseSystem;
import de.SSC.CoreManager.System.ISystem;
import de.SSC.CoreManager.System.SystemPriority;
import de.SSC.CoreManager.System.SystemState;
import de.SSC.CoreManager.System.Exception.SystemNotActiveException;

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
	
	private DataBaseSystem() 
	{
		super(Module.DataBase, SystemState.Active, SystemPriority.Essential);
		_instance = this;
		
		Player = new DataBasePlayer(this);
		Warp = new DataBaseWarp(this);
		World = new DataBaseWorld(this);
		Region = new DataBaseRegion(this);
		Rank = new DataBaseRank(this);
		Permission = new DataBasePermission(this);		
		NPC = new DataBaseNPC(this);				
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
	
	@Override
	public void PrintData(CommandSender sender) 
	{
		
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
			String errorMessage = _config.Messages.DataBase.ConnectionFailed + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();

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
			String errorMessage = _config.Messages.DataBase.ClosingError + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();

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
			_logger.SendToConsole(Module.DataBase, MessageType.Error, _config.Messages.DataBase.EmptySQLMessage);
		}		
	
		return !isEmptySQLMessage;
	}

	public void TryExecuteCommand(String sqlMessage)
	{
		String sqlCommandMessage = _config.Messages.ConsoleIO.NewLine + _config.Messages.DataBase.MessageHeader + _config.Messages.ConsoleIO.NewLine + sqlMessage + _config.Messages.ConsoleIO.NewLine + _config.Messages.DataBase.MessageFooter;

		try
		{
			_logger.SendToConsole(Module.DataBase, MessageType.Command, sqlCommandMessage);

			Data.SQLStatement = Data.SQLConnection.createStatement();
			Data.SQLStatement.execute(sqlMessage);
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
			String errorMessage = _config.Messages.DataBase.FailedToGetInteger + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();

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
}