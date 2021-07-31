package de.SSC.CoreManager.DataBase;

import java.util.UUID;

import org.bukkit.entity.Player;

import de.SSC.CoreManager.DataBase.DataTypes.CMWarp;
import de.SSC.CoreManager.DataBase.Tables.CMPlayerTable;
import de.SSC.CoreManager.DataBase.Tables.CMWarpTable;
import de.SSC.CoreManager.DataBase.Tables.CoreManagerTables;
import de.SSC.CoreManager.Utility.BukkitUtility;

public class CoreManagerMySQLMessages 
{
	private static CoreManagerMySQLMessages _instance;
	
	private final String _playerUUIDTag = "{playerUUID}";
	private final String _now = "Now()";
	
	private final String _key  = "{key}";
	private final String _keyValue = "{KeyValue}";
	private final String _newValue = "{NewValue}";
	private final String _atribute = "{atribute}";
	private final String _tableTag = "{table}";
	
	
	private final String _warpNameTag = "{WarpName}";
	private final String _worldTag = "{World}";
	private final String _xTag = "{X}";
	private final String _yTag = "{Y}";
	private final String _zTag = "{Z}";
	private final String _yawTag = "{Yaw}";
	private final String _pitchTag = "{Pitch}";
	
	private final String _getAllFrommTable = "select * from {table};";	
	private final String _updateSingleValue = "update {table} set {atribute} = \"{NewValue}\" where {key} = \"KeyValue\";";
	private final String _getSingleValue = "select * from {table} where {key} like \"{playerUUID}\";";;
	

	
	private final String _insertWarp = "insert into {table} values(\"{WarpName}\", \"{World}\", {X}, {Y}, {Z}, {Yaw}, {Pitch});";
	private final String _updateWarp = "update warps set {x}, {y}, {z}";
	private final String _doesValueExist = "select COUNT(*) from {table} where {key} like \"{KeyValue}\";";
	private final String _doesPlayerExists = "select COUNT(*) from {table} where PlayerUUID like \"{playerUUID}\" and Cracked = {KeyValue};";
	private final String _countValues = "select COUNT(*) from {table} ;";

	private CoreManagerMySQLMessages()
	{
		
	}
	
	public static CoreManagerMySQLMessages Instance()
	{
		if(_instance == null)
		{
			_instance = new CoreManagerMySQLMessages();
		}
		
		return _instance;
	}
	
	public String GetAllPlayersSQLCode()
	{			
		return _getAllFrommTable.replace(_tableTag, CoreManagerTables.players.toString());
	}
	
	public String DoesPlayerExist(Player player)
	{
		UUID uuid = player.getUniqueId();	
		BukkitUtility bukkitUtility = BukkitUtility.Instance();
		String sqlSyntax = _doesPlayerExists;
		String isCracked = bukkitUtility.IsServerCracked() == true ? "1" : "0";
				
		sqlSyntax  = sqlSyntax.replace(_tableTag, CoreManagerTables.players.toString());
		sqlSyntax =	sqlSyntax.replace(_playerUUIDTag, uuid.toString());	
		sqlSyntax = sqlSyntax.replace(_keyValue, isCracked);	
				
		return sqlSyntax;
	}
	
	public String UpdatePlayerLastSeen(UUID uuid)
	{
		String sqlMessage = _updateSingleValue;
		
		sqlMessage  = sqlMessage.replace(_tableTag, CoreManagerTables.players.toString());
		sqlMessage  = sqlMessage.replace(_atribute, CMPlayerTable.LastSeen.toString());
		sqlMessage  = sqlMessage.replace(_newValue, _now);
		sqlMessage  = sqlMessage.replace(_key, CMPlayerTable.PlayerUUID.toString());
		sqlMessage  = sqlMessage.replace(_keyValue, uuid.toString());
		
		return sqlMessage;
	}
	
	public String UpdatePlayerCustomName(UUID uuid, String newName)
	{
		String sqlMessage = _updateSingleValue;
		
		sqlMessage  = sqlMessage.replace(_tableTag, CoreManagerTables.players.toString());
		sqlMessage  = sqlMessage.replace(_atribute, CMPlayerTable.CustomName.toString());
		sqlMessage  = sqlMessage.replace(_newValue, newName);
		sqlMessage  = sqlMessage.replace(_key, CMPlayerTable.PlayerUUID.toString());
		sqlMessage  = sqlMessage.replace(_keyValue, uuid.toString());
		
		return sqlMessage;
	}
	
	public String LoadAllRanks()
	{
		return _getAllFrommTable.replace(_tableTag, CoreManagerTables.ranks.toString());
	}
	
	public String LoadAllWorlds()
	{
		return _getAllFrommTable.replace(_tableTag, CoreManagerTables.worlds.toString());
	}

	public String GetPlayer(UUID uuid) 
	{
		String sqlMessage = _getSingleValue;
		
		sqlMessage = sqlMessage.replace(_tableTag, CoreManagerTables.players.toString());
		sqlMessage = sqlMessage.replace(_key, CMPlayerTable.PlayerUUID.toString());
		sqlMessage = sqlMessage.replace(_playerUUIDTag, uuid.toString());		

		return sqlMessage;
	}

	public String GetAmountOfRegisteredPlayers() 
	{
	   String sqlMessage = _countValues;
		
		sqlMessage = sqlMessage.replace(_tableTag, CoreManagerTables.players.toString());

		return sqlMessage;
	}

	public String DoesWarpExist(CMWarp cmWarp) 
	{
		String sqlMessage = _doesValueExist;
		
		sqlMessage = sqlMessage.replace(_tableTag, CoreManagerTables.warps.toString());
		
		sqlMessage = sqlMessage.replace(_key, CMWarpTable.WarpName.toString());
		sqlMessage = sqlMessage.replace(_keyValue, cmWarp.WarpName);
		
		return sqlMessage;
	}

	public String UpdateWarp(CMWarp cmWarp)
	{		
		String sqlMessage = _updateWarp;
		
		sqlMessage = sqlMessage.replace(_tableTag, CoreManagerTables.warps.toString());
		
		sqlMessage = sqlMessage.replace(_key, CMWarpTable.WarpName.toString());
		sqlMessage = sqlMessage.replace(_keyValue, cmWarp.WarpName);
		
		return sqlMessage;
	}

	public String GenerateWarp(CMWarp cmWarp)
	{
		String sqlMessage = _insertWarp;		

		sqlMessage = sqlMessage.replace(_tableTag, CoreManagerTables.warps.toString());		
		
		sqlMessage = sqlMessage.replace(_warpNameTag, cmWarp.WarpName);
		sqlMessage = sqlMessage.replace(_worldTag,cmWarp.WarpLocation.getWorld().getName());
		sqlMessage = sqlMessage.replace(_xTag,  Double.toString(cmWarp.WarpLocation.getX()));
		sqlMessage = sqlMessage.replace(_yTag, Double.toString(cmWarp.WarpLocation.getY()));
		sqlMessage = sqlMessage.replace(_zTag,Double.toString(cmWarp.WarpLocation.getZ()));
		sqlMessage = sqlMessage.replace(_yawTag, Double.toString(cmWarp.WarpLocation.getYaw()));
		sqlMessage = sqlMessage.replace(_pitchTag, Double.toString(cmWarp.WarpLocation.getPitch()));		
		
		return sqlMessage;
	}

	public String LoadAllWarps() 
	{		
		return _getAllFrommTable.replace(_tableTag, CoreManagerTables.warps.toString());
	}
}
