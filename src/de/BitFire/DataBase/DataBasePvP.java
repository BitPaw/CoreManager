package de.BitFire.DataBase;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Geometry.Cube;
import de.BitFire.Geometry.Point;
import de.BitFire.PvP.ArenaSignTable;
import de.BitFire.PvP.ArenaTable;
import de.BitFire.PvP.Arena.Arena;
import de.BitFire.PvP.PlayMode.PvPMode;
import de.BitFire.PvP.Sign.ArenaSign;

public class DataBasePvP 
{
	private DataBaseSystem _dataBaseSystem;
	private Logger _logger;
	
	public DataBasePvP(DataBaseSystem dataBaseSystem)
	{
		_dataBaseSystem = dataBaseSystem;
		
		_logger = Logger.Instance();
	}
	
	public List<ArenaSign> GetAllArenaSigns()
	{
		List<ArenaSign> arenaSigns = new ArrayList<ArenaSign>();
		String sqlMessage = _dataBaseSystem.Data.SQLMessages.LoadAllArenaSigns();
		ResultSet resultSet;
		
		_logger.SendToConsole(Module.DataBase, MessageType.Info, "Load all arenaSigns...");

		_dataBaseSystem.CheckMySQLCommand(sqlMessage);

		_dataBaseSystem.OpenDataBase();

		_dataBaseSystem.TryExecuteCommand(sqlMessage);

		try
		{
			resultSet = _dataBaseSystem.Data.SQLStatement.getResultSet();

			while (resultSet.next())
			{					
				final int id = resultSet.getInt(ArenaSignTable.ID.toString());
				final int worldID = resultSet.getInt(ArenaSignTable.WorldID.toString());
				final int x = resultSet.getInt(ArenaSignTable.X.toString());
				final int y = resultSet.getInt(ArenaSignTable.Y.toString());
				final int z = resultSet.getInt(ArenaSignTable.Z.toString());				
				final Point position = new Point(x, y, z);				
				final ArenaSign arenaSign = new ArenaSign(id, worldID, position);
				
				arenaSigns.add(arenaSign);
			}
		}
		catch (Exception e)
		{
			String errorMessage = "Failed to load all arenasigns!";

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
		}

		_dataBaseSystem.CloseDataBase();

		_logger.SendToConsole(Module.DataBase, MessageType.Info, "&aFound &6<" + arenaSigns.size() + "&6> &7arena signs.");		
		
		return arenaSigns;
	}
	
	public List<Arena> GetAllArenas()
	{
		List<Arena> arenas = new ArrayList<Arena>();
		String sqlMessage = _dataBaseSystem.Data.SQLMessages.LoadAllArenas();
		ResultSet resultSet;
		
		_logger.SendToConsole(Module.DataBase, MessageType.Info, "Load all arenas...");

		_dataBaseSystem.CheckMySQLCommand(sqlMessage);
		_dataBaseSystem.OpenDataBase();
		_dataBaseSystem.TryExecuteCommand(sqlMessage);

		try
		{
			resultSet = _dataBaseSystem.Data.SQLStatement.getResultSet();

			while (resultSet.next())
			{					
				final int id = resultSet.getInt(ArenaTable.ID.toString());
				final int worldID = resultSet.getInt(ArenaTable.WorldID.toString());
				final int aX = resultSet.getInt(ArenaTable.AX.toString());
				final int aZ = resultSet.getInt(ArenaTable.AZ.toString());
				final int bX = resultSet.getInt(ArenaTable.BX.toString());				
				final int bZ = resultSet.getInt(ArenaTable.BZ.toString());				
				final int arenaSignID = resultSet.getInt(ArenaTable.ArenaSignID.toString());			
				final int maximalPlayers = resultSet.getInt(ArenaTable.MaxPlayer.toString());
				final String name = resultSet.getString(ArenaTable.Name.toString());;
				final PvPMode mode = PvPMode.valueOf(resultSet.getString(ArenaTable.Type.toString()));
				
				final Cube field = new Cube(aX, aZ, bX, bZ);
				final Arena arena = new Arena(id, name, field, worldID, mode, maximalPlayers);
				
				arena.SignID = arenaSignID;
				
				arenas.add(arena);
			}
		}
		catch (Exception e)
		{
			String errorMessage = "Failed to load all arenas! " + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
			
			e.printStackTrace();
		}

		_dataBaseSystem.CloseDataBase();

		_logger.SendToConsole(Module.DataBase, MessageType.Info, "&aFound &6<" + arenas.size() + "&6> &7arenas.");		
		
		return arenas;
	}
}
