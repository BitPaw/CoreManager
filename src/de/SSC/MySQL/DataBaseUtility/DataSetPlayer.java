package de.SSC.MySQL.DataBaseUtility;

import org.bukkit.entity.Player;

public class DataSetPlayer
{
	private String _tableName = "players";
	
	private String _uuid;
	private String _name;
	private String _customName;
	private String _money;
	private String RankGroup;
	private String _ip; 
	private String _registered;
	private String _lastSeen;
	private String HomeLocationX;
	private String HomeLocationY;
	private String HomeLocationZ;
	
	public DataSetPlayer(Player player, float money, String rank)
	{
		String uuid = player.getUniqueId().toString();
		String name = player.getName();
		String customname = player.getCustomName();				
		
		_uuid = uuid == null ? "null, \n" : "\"" + uuid + "\", \n";
		_name = name == null ? "null, \n" : "\"" + name + "\", \n";
		_customName = customname == null ? "null, \n" : "\"" + customname + "\", \n";
		_money = money == 0 ? "200, \n" : "\"" + Float.toString(money) + "\", \n";
		RankGroup = rank == null ? "default, \n" : "\"" + rank + "\", \n";
		_ip = "\"" + player.getAddress().getAddress().toString().replace('/', ' ').trim() + "\", \n";
		_registered = "default, \n";
		_lastSeen = "default, \n";
		HomeLocationX = "null, \n";
		HomeLocationY = "null, \n";
		HomeLocationZ = "null \n";	
	}
	
	public String GenerateSQLCode()
	{
		String out = "insert into " + _tableName + " values\n(\n";
						
        out += _uuid.toString(); // UUID       varchar(36) not null primary key,
		out += _name; // Name       varchar(20) not null,
		out += _customName; // CustomName varchar(40), 
		out += _money; // Money      float    not null default 150,
		out += RankGroup; // RankGroup  varchar(15) not null default "I",
		out += _ip; // IP		 varchar(15), 
		out += _registered; // Registered DATETIME not null DEFAULT CURRENT_TIMESTAMP,
		out += _lastSeen; // LastSeen   DATETIME not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
		out += HomeLocationX; // HomeLocationX float, 
		out += HomeLocationY; // HomeLocationY float,
		out += HomeLocationZ; // HomeLocationZ float
		out += ");";
				
		return out;
	}
}