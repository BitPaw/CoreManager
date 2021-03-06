package de.BitFire.DataBase;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Configuration.Config;
import de.BitFire.Permission.CMPermission;
import de.BitFire.Permission.CMPermissionTable;

public class DataBasePermission
{
	private DataBaseSystem _dataBaseSystem;
	private Logger _logger;
	private Config _config;

	public DataBasePermission(DataBaseSystem dataBaseSystem)
	{
		_dataBaseSystem = dataBaseSystem;
		
		_logger = Logger.Instance();
		_config = Config.Instance();
	}	
	
	public List<CMPermission> LoadAllPermissions() 
	{
		List<CMPermission> permissions = new ArrayList<CMPermission>();		
		String sqlCommand = _dataBaseSystem.Data.SQLMessages.GetAllPermissions();
		
		int id;
		int rankID;
		String permissionName;
		CMPermission cmPermission;
		ResultSet resultSet;
		
		_logger.SendToConsole(Module.DataBase, MessageType.Info, "Loading all permissions...");		
		
		_dataBaseSystem.CheckMySQLCommand(sqlCommand);
		_dataBaseSystem.OpenDataBase();
		_dataBaseSystem.TryExecuteCommand(sqlCommand);

		try
		{
			resultSet = _dataBaseSystem.Data.SQLStatement.getResultSet();

			while (resultSet.next())
			{
				id = resultSet.getInt(CMPermissionTable.ID.toString());
				rankID = resultSet.getInt(CMPermissionTable.RankID.toString());
				permissionName = resultSet.getString(CMPermissionTable.Name.toString());

				cmPermission = new CMPermission(id, permissionName, rankID);
				
				permissions.add(cmPermission);
				
			}
		}
		catch (Exception e)
		{
			String errorMessage = /*_config.Messages.Warps.FailedToLoadAllWarps + */_config.Message.IO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
		}

		_dataBaseSystem.CloseDataBase();
		
		return permissions;
	}	
}
