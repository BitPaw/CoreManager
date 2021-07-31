package de.BitFire.DataBase;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.BitFire.API.Bukkit.BukkitAPIServer;
import de.BitFire.Configuration.Config;
import de.BitFire.Player.CMPlayer;
import de.BitFire.Player.CMPlayerTable;
import de.BitFire.Player.PlayerSystem;
import de.BitFire.Player.Exception.InvalidPlayerUUID;
import de.BitFire.Player.Exception.PlayerNotFoundException;
import de.BitFire.Warp.CMWarpTable;
import de.BitFire.Warp.Warp;
import de.BitFire.Warp.WarpSystem;
import de.BitFire.World.CMWorld;
import de.BitFire.World.WorldSystem;
import de.BitFire.World.Exception.InvalidWorldNameException;

public class CoreManagerMySQLMessages 
{
	private static CoreManagerMySQLMessages _instance;
	private PlayerSystem _playerSystem;
	private Config _config;
	
	private final String _playerUUIDTag = "{playerUUID}";
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
	private final String _xTag = "{X}";
	private final String _yTag = "{Y}";
	private final String _zTag = "{Z}";
	private final String _yawTag = "{Yaw}";
	private final String _pitchTag = "{Pitch}";
	
	
//	private final String _ip = "{IP}";
	private final String _ipValue = "{IPVALUE}";
	private final String _lastSeenVAlue = "{LASTSEENVALUE}";
	
	
	private final String _getAllFrommTable = "select * from {table};";	
	private final String _updateSingleValue = "update {table} set {atribute} = \"{NewValue}\" where {KEY} = \"{KeyValue}\" and Cracked = {CRACKED};";
	private final String _getSingleValue = "select * from {table} where {KEY} like \"{playerUUID}\" and Cracked = {CRACKED};";;
	
	private final String _updateWarp = "update warps set {x}, {y}, {z}";
	private final String _doesValueExist = "select COUNT(*) from {table} where {KEY} like \"{KeyValue}\";";
	private final String _doesPlayerExists = "select COUNT(*) from {table} where UUID like \"{playerUUID}\" and Cracked = {CRACKED};";
	private final String _countValues = "select COUNT(*) from {table} ;";
	
	private final String _getAllRegions = "select * from region";

	private CoreManagerMySQLMessages()
	{
		_instance = this;
		
		_playerSystem = PlayerSystem.Instance();
		_config = Config.Instance();
	}
	
	public static CoreManagerMySQLMessages Instance()
	{	
		return _instance == null ? new CoreManagerMySQLMessages() : _instance;
	}
	
	public String GetAllPlayersSQLCode()
	{			
		return "select * from player where Cracked = " + (BukkitAPIServer.IsServerCracked() ? "1" : "0");
	}
	
	public String DoesPlayerExist(Player player)
	{
		UUID uuid = player.getUniqueId();	
		String sqlMessage = _doesPlayerExists;
				
		sqlMessage = sqlMessage.replace(_tableTag, CoreManagerTables.player.toString());
		sqlMessage = sqlMessage.replace(_playerUUIDTag, uuid.toString());	
		sqlMessage = sqlMessage.replace(_isCracked, (BukkitAPIServer.IsServerCracked() ? "1" : "0"));
				
		return sqlMessage;
	}
	
	public String UpdatePlayer(CMPlayer cmPlayer)
	{
		String sqlMessage = "update {TABLE} set IP = \"{IPVALUE}\", LASTSEEN = \"{LASTSEENVALUE}\" where UUID like \"{KEY}\" and Cracked = {CRACKED};";;
		Date date = new Date();
		Timestamp timeStamp = new Timestamp(date.getTime());
		
		
		sqlMessage = sqlMessage.replace(_table, CoreManagerTables.player.toString());
		sqlMessage = sqlMessage.replace(_ipValue, cmPlayer.GetPlayerIP());
		sqlMessage = sqlMessage.replace(_lastSeenVAlue, timeStamp.toString());
		sqlMessage = sqlMessage.replace(_key, cmPlayer.BukkitPlayer.getUniqueId().toString());
		sqlMessage = sqlMessage.replace(_isCracked, (BukkitAPIServer.IsServerCracked() ? "1" : "0"));
		
		return sqlMessage;
	}
	
	public String UpdatePlayer(Player player) throws PlayerNotFoundException, InvalidPlayerUUID
	{				
		UUID playerUUID = player.getUniqueId()		;
		CMPlayer cmPlayer = _playerSystem.GetPlayer(playerUUID);
		
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
		
		sqlMessage = sqlMessage.replace(_key, CMPlayerTable.UUID.toString());
		sqlMessage = sqlMessage.replace(_keyValue, uuid.toString());
		sqlMessage = sqlMessage.replace(_isCracked, (BukkitAPIServer.IsServerCracked() ? "1" : "0"));
		
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
		sqlMessage = sqlMessage.replace(_key, CMPlayerTable.UUID.toString());
		sqlMessage = sqlMessage.replace(_playerUUIDTag, uuid.toString());	
		sqlMessage = sqlMessage.replace(_isCracked, (BukkitAPIServer.IsServerCracked() ? "1" : "0"));

		return sqlMessage;
	}

	public String GetAmountOfRegisteredPlayers() 
	{
	   String sqlMessage = _countValues;
		
		sqlMessage = sqlMessage.replace(_tableTag, CoreManagerTables.player.toString());

		return sqlMessage;
	}

	public String DoesWarpExist(Warp warp) 
	{
		String sqlMessage = _doesValueExist;
		
		sqlMessage = sqlMessage.replace(_tableTag, CoreManagerTables.warp.toString());
		
		sqlMessage = sqlMessage.replace(_key, CMWarpTable.Name.toString());
		sqlMessage = sqlMessage.replace(_keyValue, warp.WarpName);
		
		return sqlMessage;
	}

	public String UpdateWarp(Warp warp)
	{		
		String sqlMessage = _updateWarp;		
		
		sqlMessage = sqlMessage.replace(_tableTag, CoreManagerTables.warp.toString());
		
		sqlMessage = sqlMessage.replace(_key, CMWarpTable.Name.toString());
		sqlMessage = sqlMessage.replace(_keyValue, warp.WarpName);
		
		return sqlMessage;
	}

	public String GenerateWarp(Warp warp) throws InvalidWorldNameException
	{
		String sqlMessage = "insert into {table} values({ID},\"{WarpName}\", {X}, {Y}, {Z}, {Yaw}, {Pitch}, {WorldID}, null)";		
		String worldName = warp.WarpLocation.getWorld().getName();
		
		worldName = _config.Worlds.RemoveFolderName(worldName);
		
		WorldSystem worldSystem = WorldSystem.Instance();
		WarpSystem warpSystem = WarpSystem.Instance();
		CMWorld cmWorld = worldSystem.GetWorld(worldName);
		
		sqlMessage = sqlMessage.replace("{ID}", Integer.toString(warpSystem.GetNextWarpID()));		
		sqlMessage = sqlMessage.replace(_tableTag, CoreManagerTables.warp.toString());				
		sqlMessage = sqlMessage.replace(_warpNameTag, warp.WarpName);
	
		sqlMessage = sqlMessage.replace(_xTag,  Double.toString(warp.WarpLocation.getX()));
		sqlMessage = sqlMessage.replace(_yTag, Double.toString(warp.WarpLocation.getY()));
		sqlMessage = sqlMessage.replace(_zTag,Double.toString(warp.WarpLocation.getZ()));
		sqlMessage = sqlMessage.replace(_yawTag, Double.toString(warp.WarpLocation.getYaw()));
		sqlMessage = sqlMessage.replace(_pitchTag, Double.toString(warp.WarpLocation.getPitch()));	
		sqlMessage = sqlMessage.replace("{WorldID}", Integer.toString(cmWorld.Information.ID));		
		
		return sqlMessage;
	}

	public String LoadAllWarps() 
	{		
		return _getAllFrommTable.replace(_tableTag, CoreManagerTables.warp.toString());
	}

	public String UpdateRank(CMPlayer cmPlayer) 
	{
		String sqlMessage = _updateSingleValue;
		UUID uuid = cmPlayer.Information.PlayerUUID;
		
		sqlMessage  = sqlMessage.replace(_tableTag, CoreManagerTables.player.toString());
		sqlMessage  = sqlMessage.replace(_atribute, CMPlayerTable.RankID.toString());
		sqlMessage  = sqlMessage.replace(_newValue, Integer.toString(cmPlayer.Information.RankGroup.ID));
		sqlMessage  = sqlMessage.replace(_key, CMPlayerTable.UUID.toString());
		sqlMessage  = sqlMessage.replace(_keyValue, uuid.toString());
		sqlMessage = sqlMessage.replace(_isCracked, (BukkitAPIServer.IsServerCracked() ? "1" : "0"));
		
		return sqlMessage;
	}

	public String GetPlayerPerIP(String ip) 
	{
		String sqlMessage = "select * from {table} where {COLUM1} like \"{KeyValue}\" and Cracked = {CRACKED};";;
		
		sqlMessage = sqlMessage.replace(_tableTag, CoreManagerTables.player.toString());
		sqlMessage = sqlMessage.replace(_firstColum, CMPlayerTable.IP.toString());
		sqlMessage = sqlMessage.replace(_keyValue, ip);
		sqlMessage = sqlMessage.replace(_isCracked, (BukkitAPIServer.IsServerCracked() ? "1" : "0"));
		
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

	public String LoadAllPortals() 
	{
		return _getAllFrommTable.replace(_tableTag, CoreManagerTables.portal.toString());
	}

	public String LoadAllArenaSigns() 
	{
		return _getAllFrommTable.replace(_tableTag, CoreManagerTables.arenasign.toString());
	}

	public String LoadAllArenas() 
	{
		return _getAllFrommTable.replace(_tableTag, CoreManagerTables.arena.toString());
	}
}
