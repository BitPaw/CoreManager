package de.BitFire.DataBase;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;

import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Configuration.Config;
import de.BitFire.Warp.CMWarpTable;
import de.BitFire.Warp.Warp;
import de.BitFire.World.WorldSystem;
import de.BitFire.World.Exception.InvalidWorldNameException;

public class DataBaseWarp 
{
	private DataBaseSystem _dataBaseSystem;
	private Logger _logger;
	private Config _config;
	private WorldSystem _worldSystem;
	
	public DataBaseWarp(DataBaseSystem dataBaseSystem)
	{
		_dataBaseSystem = dataBaseSystem;
		
		_logger = Logger.Instance();
		_config = Config.Instance();
		_worldSystem = WorldSystem.Instance();
	}
	
	public void RegisterWarp(Warp warp) throws InvalidWorldNameException
	{
		String sqlCommand = _dataBaseSystem.Data.SQLMessages.GenerateWarp(warp);
		
		_logger.SendToConsole(Module.DataBase, MessageType.Command, "Registering new warp.");

		_dataBaseSystem.SendData(sqlCommand);
	}

	public void UpdateWarp(Warp warp)
	{
		String sqlCommand = _dataBaseSystem.Data.SQLMessages.UpdateWarp(warp);
		
		_logger.SendToConsole(Module.DataBase, MessageType.Command, "Updating warp.");

		_dataBaseSystem.SendData(sqlCommand);
	}

	public boolean DoesWarpExist(Warp warp)
	{
		boolean doesWarpExist;
		String mysqlMessage = _dataBaseSystem.Data.SQLMessages.DoesWarpExist(warp);

		_logger.SendToConsole(Module.DataBase, MessageType.Question, "Does warp exists?");
		
		doesWarpExist = _dataBaseSystem.GetBoolean(mysqlMessage);

		return doesWarpExist;
	}

	public List<Warp> LoadAllWarps()
	{
		String warpName;
		World warpWorld;
		double x;
		double y;
		double z;
		float yaw;
		float pitch;	
		Location location;
		ResultSet resultSet;		
		
		_logger.SendToConsole(Module.DataBase, MessageType.Info, "Load all warps.");

		ArrayList<Warp> warps = new ArrayList<Warp>();
		String sqlCommand = _dataBaseSystem.Data.SQLMessages.LoadAllWarps();

		_dataBaseSystem.CheckMySQLCommand(sqlCommand);

		_dataBaseSystem.OpenDataBase();

		_dataBaseSystem.TryExecuteCommand(sqlCommand);

		try
		{
			resultSet = _dataBaseSystem.Data.SQLStatement.getResultSet();

			while (resultSet.next())
			{
				Warp warp;
				int worldID = resultSet.getInt(CMWarpTable.WorldID.toString());		 
				
				warpName = resultSet.getString(CMWarpTable.Name.toString());			
				warpWorld = _worldSystem.GetWorld(worldID).BukkitWorld;				
				x = resultSet.getDouble(CMWarpTable.X.toString());
				y = resultSet.getDouble(CMWarpTable.Y.toString());
				z = resultSet.getDouble(CMWarpTable.Z.toString());
				yaw = resultSet.getFloat(CMWarpTable.Yaw.toString());
				pitch = resultSet.getFloat(CMWarpTable.Pitch.toString());

				location = new Location(warpWorld, x, y, z, yaw, pitch);
				warp = new Warp(warpName, location);

				warps.add(warp);
			}
		}
		catch (Exception e)
		{
			String errorMessage = "Error while loading all warps. " + _config.Message.IO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
		}

		_dataBaseSystem.CloseDataBase();

		return warps;
	}

}
