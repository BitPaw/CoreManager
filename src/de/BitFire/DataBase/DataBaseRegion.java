package de.BitFire.DataBase;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Configuration.Config;
import de.BitFire.Geometry.Cube;
import de.BitFire.Geometry.Point;
import de.BitFire.Player.PlayerSystem;
import de.BitFire.World.WorldSystem;
import de.BitFire.World.Region.CMRegion;
import de.BitFire.World.Region.CMRegionTable;
import de.BitFire.World.Region.RegionInformation;

public class DataBaseRegion
{
	private DataBaseSystem _dataBaseSystem;
	private Logger _logger;
	private Config _config;
	
	public DataBaseRegion(DataBaseSystem dataBaseSystem)
	{
		_dataBaseSystem = dataBaseSystem;
		
		_logger = Logger.Instance();
		_config = Config.Instance();
		WorldSystem.Instance();
		PlayerSystem.Instance();
	}
	
	
	public List<CMRegion> LoadAllRegions()
	{
		List<CMRegion> _cmRegions = new ArrayList<CMRegion>();		
		String sqlCommand = _dataBaseSystem.Data.SQLMessages.GetAllRegions();
		ResultSet resultSet;
		

		String regionName;
		String message;
		String playerUUIDText;
		CMRegion cmRegion;	
		int id;
		int worldID;
		int xA;
		int yA;
		int zA;
		int xB;
		int yB;
		int zB;
		boolean ignoreHight;
		boolean permissionToBuild;
		boolean permissionToAccess;
		
		_logger.SendToConsole(Module.DataBase, MessageType.Loading, "Loading all regions...");
		
		_dataBaseSystem.CheckMySQLCommand(sqlCommand);
		_dataBaseSystem.OpenDataBase();
		_dataBaseSystem.TryExecuteCommand(sqlCommand);

		try
		{
			resultSet = _dataBaseSystem.Data.SQLStatement.getResultSet();

			while (resultSet.next())
			{
				
				// Get Data
				{
					id = resultSet.getInt(CMRegionTable.ID.toString());
					regionName = resultSet.getString(CMRegionTable.Name.toString());		
					playerUUIDText = resultSet.getString(CMRegionTable.PlayerUUID.toString());			
					
					worldID = resultSet.getInt(CMRegionTable.WorldID.toString());	
					
					xA = resultSet.getInt(CMRegionTable.AX.toString());	
					yA = resultSet.getInt(CMRegionTable.AY.toString());	
					zA = resultSet.getInt(CMRegionTable.AZ.toString());	
					
					xB = resultSet.getInt(CMRegionTable.BX.toString());	
					yB = resultSet.getInt(CMRegionTable.BY.toString());	
					zB = resultSet.getInt(CMRegionTable.BZ.toString());		
					
					ignoreHight = resultSet.getBoolean(CMRegionTable.IgnoreHeight.toString());
					permissionToBuild = resultSet.getBoolean(CMRegionTable.PermissionToBuild.toString());
					permissionToAccess = resultSet.getBoolean(CMRegionTable.PermissionToAccess.toString());
				}
				
				// Process Data
				{
					if(playerUUIDText != null)
					{
						UUID.fromString(playerUUIDText);			
					}					
				}
				
				
				// Set Data
				{					
					final Point pointA = new Point(xA, yA, zA);
					final Point pointB = new Point(xB, yB, zB);
					final Cube cube = new Cube(pointA, pointB, ignoreHight);					
					final RegionInformation regionInformation = new RegionInformation(permissionToBuild, permissionToAccess);	
					
					cmRegion = new CMRegion(id, regionName, worldID, cube, regionInformation);	
					
					_cmRegions.add(cmRegion);
					
					//_logger.SendToConsole(Module.DataBase, MessageType.Warning, "World <" + cmWorld.Information.Name + "> is unloaded! Region can't be used.");
				}	
			
		
				//message = "World is not loaded! region <" + regionName + "> skipped";
				
				//_logger.SendToConsole(Module.DataBase, MessageType.Warning, message);			
			
			}
		}
		catch (Exception e)
		{
			message = "Failed to Load all regions. " + _config.Message.IO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, message);
		}

		_dataBaseSystem.CloseDataBase();				
		
		return _cmRegions;
	}
}
