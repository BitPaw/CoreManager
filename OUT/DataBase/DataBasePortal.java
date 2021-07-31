package de.BitFire.DataBase;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.BlockFace;

import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Configuration.Config;
import de.BitFire.Geometry.Cube;
import de.BitFire.Geometry.Point;
import de.BitFire.Player.CMPlayer;
import de.BitFire.Teleport.Portal.Portal;
import de.BitFire.Teleport.Portal.PortalTable;

public class DataBasePortal 
{
	private DataBaseSystem _dataBaseSystem;
	private Logger _logger;
	private Config _config;

	public DataBasePortal(DataBaseSystem dataBaseSystem)
	{
		_dataBaseSystem = dataBaseSystem;
		
		_logger = Logger.Instance();
		_config = Config.Instance();
	}
	
	public void UpdatePortal(CMPlayer cmPlayer)
	{
	
	}	

	public List<Portal> LoadAllPortals()
	{
		List<Portal> portals = new ArrayList<Portal>();
		String sqlMessage = _dataBaseSystem.Data.SQLMessages.LoadAllPortals();
		ResultSet resultSet;
		
		_logger.SendToConsole(Module.DataBase, MessageType.Info, "Load all portals...");

		_dataBaseSystem.CheckMySQLCommand(sqlMessage);

		_dataBaseSystem.OpenDataBase();

		_dataBaseSystem.TryExecuteCommand(sqlMessage);

		try
		{
			resultSet = _dataBaseSystem.Data.SQLStatement.getResultSet();

			while (resultSet.next())
			{				
				final int id = resultSet.getInt(PortalTable.ID.toString());
				final String name = resultSet.getString(PortalTable.Name.toString());
				final double aX = resultSet.getDouble(PortalTable.AX.toString());
				final double aY = resultSet.getDouble(PortalTable.AY.toString());
				final double aZ = resultSet.getDouble(PortalTable.AZ.toString());
				final double bX = resultSet.getDouble(PortalTable.BX.toString());
				final double bY = resultSet.getDouble(PortalTable.BY.toString());
				final double bZ = resultSet.getDouble(PortalTable.BZ.toString());
				final BlockFace blockface = BlockFace.valueOf(resultSet.getString(PortalTable.Direction.toString()));
				final int worldID = resultSet.getInt(PortalTable.WorldID.toString());;
				final int linkedPortalID = resultSet.getInt(PortalTable.LinkedPortalID.toString());;
				
				final Point a = new Point(aX, aY, aZ);
				final Point b = new Point(bX, bY, bZ);
				final Cube field = new Cube(a, b);	
				
				Portal portal = new Portal(id, field, name);;

				portal.Direction = blockface;
				portal.WorldID = worldID;
				portal.LinkedPortalID = linkedPortalID;
				
				portals.add(portal);
			}
		}
		catch (Exception e)
		{
			String errorMessage = _config.Message.Rank.FailedToLoadAllRanks + _config.Message.IO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
		}

		_dataBaseSystem.CloseDataBase();

		_logger.SendToConsole(Module.DataBase, MessageType.Info, "&aFound &6<" + portals.size() + "&6> &7portals.");		
		
		return portals;
	}
}
