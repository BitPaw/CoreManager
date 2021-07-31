package de.SSC.CoreManager.Systems.DataBase;

import java.util.UUID;

import org.bukkit.entity.Player;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Systems.DataBase.Tables.CMPlayerTable;
import de.SSC.CoreManager.Systems.DataBase.Tables.CMWarpTable;
import de.SSC.CoreManager.Systems.DataBase.Tables.CoreManagerTables;
import de.SSC.CoreManager.Systems.Location.CMLocation;
import de.SSC.CoreManager.Systems.Player.CMPlayer;
import de.SSC.CoreManager.Systems.Player.PlayerSystem;
import de.SSC.CoreManager.Systems.Warp.CMWarp;
import de.SSC.CoreManager.Utility.BukkitUtility;

public class CoreManagerMySQLMessages 
{
	private static CoreManagerMySQLMessages _instance;
	private BukkitUtility _bukkitUtility;
	private PlayerSystem _playerSystem;
	private Config _config;
	
	private final String _playerUUIDTag = "{playerUUID}";
	private final String _now = "Now()";
	
	private final String _firstColum = "{COLUM1}";
	//private final String _secondColum = "{COLUM2}";
	//private final String _thirdColum = "{COLUM3}";
	//private final String _firstValue = "{VALUE1}";
	//private final String _secondValue = "{VALUE2}";
	//private final String _thirdValue = "{VALUE3}";
	private final String _table = "{TABLE}";
	//private final String _keyColum = "{KEYCOLUM}";	
	private final String _key  = "{KEY}";
	
	private final String _keyValue = "{KeyValue}";
	private final String _newValue = "{NewValue}";
	private final String _atribute = "{atribute}";
	private final String _tableTag = "{table}";
	
	private final String _isCracked = "{CRACKED}";
	
	
	private final String _warpNameTag = "{WarpName}";
	private final String _worldTag = "{World}";
	private final String _xTag = "{X}";
	private final String _yTag = "{Y}";
	private final String _zTag = "{Z}";
	private final String _yawTag = "{Yaw}";
	private final String _pitchTag = "{Pitch}";
	
	
//	private final String _ip = "{IP}";
	private final String _ipValue = "{IPVALUE}";
	private final String _lastSeenVAlue = "{LASTSEENVALUE}";
	
	private final String _updatePlayer = "update {TABLE} set IP = \"{IPVALUE}\", LASTSEEN = {LASTSEENVALUE} where PlayerUUID like \"{KEY}\" and Cracked = {CRACKED};";
	
	
	
	private final String _getAllFrommTable = "select * from {table};";	
	private final String _updateSingleValue = "update {table} set {atribute} = \"{NewValue}\" where {KEY} = \"{KeyValue}\" and Cracked = {CRACKED};";
	private final String _getSingleValue = "select * from {table} where {KEY} like \"{playerUUID}\" and Cracked = {CRACKED};";;

	private final String _getPlayerPerIP = "select * from {table} where {COLUM1} like \"{KeyValue}\" and Cracked = {CRACKED};";

	
	private final String _insertWarp = "insert into {table} values(\"{WarpName}\", \"{World}\", {X}, {Y}, {Z}, {Yaw}, {Pitch});";
	private final String _updateWarp = "update warps set {x}, {y}, {z}";
	private final String _doesValueExist = "select COUNT(*) from {table} where {KEY} like \"{KeyValue}\";";
	private final String _doesPlayerExists = "select COUNT(*) from {table} where PlayerUUID like \"{playerUUID}\" and Cracked = {CRACKED};";
	private final String _countValues = "select COUNT(*) from {table} ;";
	
	private final String _getAllRegions = "select * from region left join area	on region.LocationID = area.LocationID;";

	private CoreManagerMySQLMessages()
	{
		_instance = this;
		
		_bukkitUtility = BukkitUtility.Instance();
		_playerSystem = PlayerSystem.Instance();
		_config = Config.Instance();
	}
	
	public static CoreManagerMySQLMessages Instance()
	{	
		return _instance == null ? new CoreManagerMySQLMessages() : _instance;
	}
	
	public String GetAllPlayersSQLCode()
	{			
		return _getAllFrommTable.replace(_tableTag, CoreManagerTables.player.toString());
	}
	
	public String DoesPlayerExist(Player player)
	{
		UUID uuid = player.getUniqueId();	
		String sqlMessage = _doesPlayerExists;
				
		sqlMessage = sqlMessage.replace(_tableTag, CoreManagerTables.player.toString());
		sqlMessage = sqlMessage.replace(_playerUUIDTag, uuid.toString());	
		sqlMessage = sqlMessage.replace(_isCracked, (_bukkitUtility.IsServerCracked() ? "1" : "0"));
				
		return sqlMessage;
	}
	
	public String UpdatePlayer(CMPlayer cmPlayer)
	{
		String sqlMessage = _updatePlayer;
		
		sqlMessage = sqlMessage.replace(_table, CoreManagerTables.player.toString());
		sqlMessage = sqlMessage.replace(_ipValue, _bukkitUtility.PlayerUtility.GetPlayerIP(cmPlayer.BukkitPlayer));
		sqlMessage = sqlMessage.replace(_lastSeenVAlue, _now);
		sqlMessage = sqlMessage.replace(_key, cmPlayer.BukkitPlayer.getUniqueId().toString());
		sqlMessage = sqlMessage.replace(_isCracked, (_bukkitUtility.IsServerCracked() ? "1" : "0"));
		
		return sqlMessage;
	}
	
	public String UpdatePlayer(Player player)
	{		
		CMPlayer cmPlayer;
		
		cmPlayer = _playerSystem.GetPlayer(player);
		
		return UpdatePlayer(cmPlayer);
	}
	
	public String UpdatePlayerCustomName(UUID uuid, String newName)
	{
		String sqlMessage = _updateSingleValue;		
		
		sqlMessage = sqlMessage.replace(_tableTag, CoreManagerTables.player.toString());
		sqlMessage = sqlMessage.replace(_atribute, CMPlayerTable.CustomName.toString());
		
		if(newName == null)
		{
			sqlMessage = sqlMessage.replace("\"" + _newValue + "\"", "NULL");
		}
		else
		{
			sqlMessage = sqlMessage.replace(_newValue, newName);
		}		
		
		sqlMessage = sqlMessage.replace(_key, CMPlayerTable.PlayerUUID.toString());
		sqlMessage = sqlMessage.replace(_keyValue, uuid.toString());
		sqlMessage = sqlMessage.replace(_isCracked, (_bukkitUtility.IsServerCracked() ? "1" : "0"));
		
		return sqlMessage;
	}
	
	public String LoadAllRanks()
	{
		return _getAllFrommTable.replace(_tableTag, CoreManagerTables.rank.toString());
	}
	
	public String LoadAllWorlds()
	{
		return _getAllFrommTable.replace(_tableTag, CoreManagerTables.world.toString());
	}

	public String GetPlayer(UUID uuid) 
	{
		String sqlMessage = _getSingleValue;
		
		sqlMessage = sqlMessage.replace(_tableTag, CoreManagerTables.player.toString());
		sqlMessage = sqlMessage.replace(_key, CMPlayerTable.PlayerUUID.toString());
		sqlMessage = sqlMessage.replace(_playerUUIDTag, uuid.toString());	
		sqlMessage = sqlMessage.replace(_isCracked, (_bukkitUtility.IsServerCracked() ? "1" : "0"));

		return sqlMessage;
	}

	public String GetAmountOfRegisteredPlayers() 
	{
	   String sqlMessage = _countValues;
		
		sqlMessage = sqlMessage.replace(_tableTag, CoreManagerTables.player.toString());

		return sqlMessage;
	}

	public String DoesWarpExist(CMWarp cmWarp) 
	{
		String sqlMessage = _doesValueExist;
		
		sqlMessage = sqlMessage.replace(_tableTag, CoreManagerTables.warp.toString());
		
		sqlMessage = sqlMessage.replace(_key, CMWarpTable.WarpName.toString());
		sqlMessage = sqlMessage.replace(_keyValue, cmWarp.WarpName);
		
		return sqlMessage;
	}

	public String UpdateWarp(CMWarp cmWarp)
	{		
		String sqlMessage = _updateWarp;		
		
		sqlMessage = sqlMessage.replace(_tableTag, CoreManagerTables.warp.toString());
		
		sqlMessage = sqlMessage.replace(_key, CMWarpTable.WarpName.toString());
		sqlMessage = sqlMessage.replace(_keyValue, cmWarp.WarpName);
		
		return sqlMessage;
	}

	public String GenerateWarp(CMWarp cmWarp)
	{
		String sqlMessage = _insertWarp;		
		String worldName = cmWarp.WarpLocation.getWorld().getName();
		
		worldName = _config.Worlds.RemoveFolderName(worldName);
		
		sqlMessage = sqlMessage.replace(_tableTag, CoreManagerTables.warp.toString());		
		
		sqlMessage = sqlMessage.replace(_warpNameTag, cmWarp.WarpName);
		sqlMessage = sqlMessage.replace(_worldTag,worldName);
		sqlMessage = sqlMessage.replace(_xTag,  Double.toString(cmWarp.WarpLocation.getX()));
		sqlMessage = sqlMessage.replace(_yTag, Double.toString(cmWarp.WarpLocation.getY()));
		sqlMessage = sqlMessage.replace(_zTag,Double.toString(cmWarp.WarpLocation.getZ()));
		sqlMessage = sqlMessage.replace(_yawTag, Double.toString(cmWarp.WarpLocation.getYaw()));
		sqlMessage = sqlMessage.replace(_pitchTag, Double.toString(cmWarp.WarpLocation.getPitch()));		
		
		return sqlMessage;
	}

	public String LoadAllWarps() 
	{		
		return _getAllFrommTable.replace(_tableTag, CoreManagerTables.warp.toString());
	}

	public String UpdateRank(CMPlayer cmPlayer) 
	{
		String sqlMessage = _updateSingleValue;
		UUID uuid = cmPlayer.BukkitPlayer.getUniqueId();
		
		sqlMessage  = sqlMessage.replace(_tableTag, CoreManagerTables.player.toString());
		sqlMessage  = sqlMessage.replace(_atribute, CMPlayerTable.RankName.toString());
		sqlMessage  = sqlMessage.replace(_newValue, cmPlayer.RankGroup.RankName);
		sqlMessage  = sqlMessage.replace(_key, CMPlayerTable.PlayerUUID.toString());
		sqlMessage  = sqlMessage.replace(_keyValue, uuid.toString());
		sqlMessage = sqlMessage.replace(_isCracked, (_bukkitUtility.IsServerCracked() ? "1" : "0"));
		
		return sqlMessage;
	}

	public String GetPlayerPerIP(String ip) 
	{
		String sqlMessage = _getPlayerPerIP;
		
		sqlMessage = sqlMessage.replace(_tableTag, CoreManagerTables.player.toString());
		sqlMessage = sqlMessage.replace(_firstColum, CMPlayerTable.IP.toString());
		sqlMessage = sqlMessage.replace(_keyValue, ip);
		sqlMessage = sqlMessage.replace(_isCracked, (_bukkitUtility.IsServerCracked() ? "1" : "0"));
		
		return sqlMessage;
	}

	public String CreateLocation(CMLocation cmLocation) 
	{
		String sqlMessage = "insert into {TABLE} values(";
		String worldname = cmLocation.LocationA.getWorld().toString();
		
		worldname = _config.Worlds.RemoveFolderName(worldname);
		
		sqlMessage = sqlMessage.replace(_tableTag, CoreManagerTables.location.toString());
		
		sqlMessage += "default, \n";
		sqlMessage += worldname + ", \n";
		sqlMessage += cmLocation.LocationA.getBlockX() + ",\n";
		sqlMessage += cmLocation.LocationA.getBlockY() + ",\n";
		sqlMessage += cmLocation.LocationA.getBlockZ() + ",\n";
		
		sqlMessage += cmLocation.LocationA.getBlockX() + ",\n";
		sqlMessage += cmLocation.LocationA.getBlockY() + ",\n";
		sqlMessage += cmLocation.LocationA.getBlockZ() + "\n";
		
		sqlMessage += ");";
		
		return sqlMessage;
	}

	public String GetAllRegions() 
	{		
		return _getAllRegions ;
	}

	public String GetAllPermissions() 
	{
		return _getAllFrommTable.replace(_tableTag, CoreManagerTables.permission.toString());
	}
}
