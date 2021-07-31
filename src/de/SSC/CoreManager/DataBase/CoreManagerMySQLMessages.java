package de.SSC.CoreManager.DataBase;

import java.util.UUID;

import org.bukkit.entity.Player;

import de.SSC.CoreManager.DataBase.Tables.CMPlayerTable;
import de.SSC.CoreManager.DataBase.Tables.CoreManagerTables;

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
	
	private final String _getAllFrommTable = "select * from {table};";	
	private final String _updateSingleValue = "update {table} set {atribute} = \"{NewValue}\" where {key} = \"KeyValue\";";
	private final String _getSingleValue = "select * from {table} where {key} like \"{playerUUID}\";";;
	
	private final String _doesPlayerExists = "select COUNT(*) from {table} where PlayerUUID like \"{playerUUID}\";";

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
		String sqlSyntax = _doesPlayerExists;
				
		sqlSyntax  = sqlSyntax.replace(_tableTag, CoreManagerTables.players.toString());
		sqlSyntax =	sqlSyntax.replace(_playerUUIDTag, uuid.toString());		
				
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
}
