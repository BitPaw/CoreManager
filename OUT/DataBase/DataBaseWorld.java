package de.BitFire.DataBase;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldType;

import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Configuration.Config;
import de.BitFire.World.CMWorld;
import de.BitFire.World.CMWorldInformation;
import de.BitFire.World.CMWorldTable;

public class DataBaseWorld 
{
	private DataBaseSystem _dataBaseSystem;
	private Logger _logger;
	private Config _config;
	
	public DataBaseWorld(DataBaseSystem dataBaseSystem)
	{
		_dataBaseSystem = dataBaseSystem;
		
		_logger = Logger.Instance();
		_config = Config.Instance();
	}
	
	public List<CMWorld> LoadAllWorlds()
	{
		List<CMWorld> cmWorlds = new ArrayList<CMWorld>();
		String sqlCommand = _dataBaseSystem.Data.SQLMessages.LoadAllWorlds();
		ResultSet resultSet;
		
		_dataBaseSystem.CheckMySQLCommand(sqlCommand);

		_dataBaseSystem.OpenDataBase();

		_dataBaseSystem.TryExecuteCommand(sqlCommand);

		try
		{
			resultSet = _dataBaseSystem.Data.SQLStatement.getResultSet();

			while (resultSet.next())
			{
				CMWorld cmWorld;
				CMWorldInformation cmWorldInformation = new CMWorldInformation();
				
				cmWorldInformation.ID = resultSet.getInt(CMWorldTable.ID.toString());
				cmWorldInformation.Active = resultSet.getBoolean(CMWorldTable.Active.toString());
				cmWorldInformation.Name = resultSet.getString(CMWorldTable.Name.toString());
				cmWorldInformation.CustomName = resultSet.getString(CMWorldTable.CustomName.toString());
				cmWorldInformation.MapEnvironment = World.Environment.valueOf(resultSet.getString(CMWorldTable.Enviroment.toString()));	
				cmWorldInformation.Type = WorldType.getByName(resultSet.getString(CMWorldTable.Type.toString()));
				cmWorldInformation.Seed = resultSet.getLong(CMWorldTable.Seed.toString());
				cmWorldInformation.WorldDifficulty = Difficulty.valueOf(resultSet.getString(CMWorldTable.Difficulty.toString()));				
				cmWorldInformation.BorderSize = resultSet.getInt(CMWorldTable.BorderSize.toString());
				cmWorldInformation.Hardcore = resultSet.getBoolean(CMWorldTable.HardCore.toString());
				cmWorldInformation.PvP = resultSet.getBoolean(CMWorldTable.PvP.toString());
				cmWorldInformation.LoseItems = resultSet.getBoolean(CMWorldTable.LoseItems.toString());
				cmWorldInformation.MobGrief = resultSet.getBoolean(CMWorldTable.MobGrief.toString());
				cmWorldInformation.MobSpawn = resultSet.getBoolean(CMWorldTable.MobSpawn.toString());
				cmWorldInformation.Structures = resultSet.getBoolean(CMWorldTable.Structures.toString());;				
				cmWorldInformation.DoFireTick = resultSet.getBoolean(CMWorldTable.DoFireTick.toString());
				cmWorldInformation.Weather = resultSet.getBoolean(CMWorldTable.Weather.toString());
				cmWorldInformation.X = resultSet.getFloat(CMWorldTable.X.toString());
				cmWorldInformation.Y = resultSet.getFloat(CMWorldTable.Y.toString());
				cmWorldInformation.Z = resultSet.getFloat(CMWorldTable.Z.toString());
				cmWorldInformation.Pitch = resultSet.getFloat(CMWorldTable.Pitch.toString());
				cmWorldInformation.Yaw = resultSet.getFloat(CMWorldTable.Yaw.toString());
				cmWorldInformation.NeedsPermission = resultSet.getBoolean(CMWorldTable.NeedsPermission.toString());

				cmWorld = new CMWorld(cmWorldInformation);
				
				cmWorlds.add(cmWorld);
			}
		}
		catch (Exception e)
		{
			String errorMessage = _config.Message.World.FailedToLoadAllWorlds + _config.Message.IO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
		}

		_dataBaseSystem.CloseDataBase();

		_logger.SendToConsole(Module.DataBase, MessageType.Info, "Found <" + cmWorlds.size() + "> worlds.");
		
		return cmWorlds;
	}
}
