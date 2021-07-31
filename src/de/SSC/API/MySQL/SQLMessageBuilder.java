package de.SSC.API.MySQL;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Main;
import de.SSC.CoreManager.Teleport.Warp;
import de.SSC.CoreManager.Players.CoreManagerPlayer;
import org.bukkit.entity.Player;

import java.net.InetSocketAddress;
import java.util.UUID;

public class SQLMessageBuilder 
{
	private final String PlayerTableName = "playerstest";
	private final String WarpTableName = "warps";

	private String _commas= "\"";
	private String _end = ";";
	private String _nullValue = " null ";
	private String _defaultValue = " default ";
	private String _tab = "\t";
	private String _newLine = "\n";
	private String _nextValue = ",\n  ";

	public String RegisterNewPlayer(DataSetPlayer player)
	{
		String message = null;

		try
		{
			message = "insert into " + PlayerTableName + " values \n(\n";

			String uuid = player.UUID == null ? _nullValue : player.UUID.toString();
			String isOP =  player.IsOP ? " true " : " false ";
			String money = Integer.toString(Math.round(player.Money));
			String customName = player.CustomName == null ? " null " : _commas + player.CustomName + _commas;

			message +=  _commas + uuid + _commas + _nextValue;
			message +=  isOP + _nextValue;
			message +=  _commas + player.Name  + _commas + _nextValue;
			message +=  customName + _nextValue;
			message +=  money +  _nextValue;
			message +=  _commas + player.RankGroup + _commas + _nextValue;
			message +=  _commas + player.IP  + _commas + _nextValue;
			message +=  _defaultValue + _nextValue;
			message +=   _defaultValue + _newLine;
			message +=  ")" + _end;
		}
		catch(Exception e)
		{
			throw new IllegalArgumentException("Could not create a RegisterNewPlayer message.\n" + e.getMessage());
		}

		return message;
	}

	public String GetAllPlayersSQLCode()
	{
		String message = "select * from " + PlayerTableName + ";";

		return message;
	}

	public String DoesPlayerExist(Player player)
	{
		String uuid = _commas + player.getUniqueId().toString() + _commas;
		String message = "select * from " + PlayerTableName + " where UUID like " + uuid + ";";

		return message;
	}

	public String UpdateRank(CoreManagerPlayer cmPlayer)
	{
		String message = "";
		String uuid = _commas + cmPlayer.BukkitPlayer.getUniqueId() + _commas;
		String rank = _commas + cmPlayer.GroupRank.RankName + _commas;
				
		 message += "update " + PlayerTableName + " set `RankGroup` = " + rank + " where UUID like " + uuid + ";";
		
		return message;
	}
	
	public String ChangeCustomName(Player player)
	{
		String message;
		String customName = player.getCustomName();
		UUID uuid = player.getUniqueId();
		
		message = "update " + PlayerTableName + " set CustomName = " + _commas + customName + _commas +" where UUID like " + _commas + uuid + _commas + _end;	

		return message;
	}
	
	public String RemoveCustomName(Player player)
	{
		String message;
		UUID uuid = player.getUniqueId();
		
		message = "update " + PlayerTableName + " set CustomName = null where UUID like " + _commas + uuid + _commas + _end;	

		return message;
	}

	public String GetCustomName(Player player)
	{	
		String message;
		UUID uuid = player.getUniqueId();
		
		message = "select CustomName from " + PlayerTableName + " where UUID = " + _commas + uuid + _commas + _end;

		return message;
	}

	public String GetAllRegestratedPlayers()
	{
		String message = "select count(*) as `Total Players` from " + PlayerTableName +";";

		return message;
	}
	
	public String UpdatePlayer(Player player)
	{
		InetSocketAddress inetadress = player.getAddress();
		String ipAdress = inetadress.getAddress().toString();
		ipAdress = ipAdress.replace("/", "");

		String ip =_commas + ipAdress + _commas ;
		String uuid = _commas + player.getUniqueId() + _commas;
		String message = "update " + PlayerTableName + " set IP = " + ip +  " , LastSeen = NOW() where UUID like " + uuid + ";";

		return message;
	}

	public String GetPlayer(Player player)
	{
		String message = "Select * from " + PlayerTableName + " where UUID like " + _commas + player.getUniqueId() +  _commas +";";	
		
		return message;	
	}



































	public String LoadAllWarps()
	{
		String message = "Select * from " + WarpTableName + ";";	
		
		return message;		
	}
	
	public String SaveWarp(Warp warp)
	{
		Config config = Main.config;
		String message = "insert into " + WarpTableName + " values \n(\n";	
		
		message += _tab + _commas + warp.WarpName + _commas + _nextValue;

		String worldName = warp.WarpLocation.getWorld().getName();
		worldName = worldName.replace(config.WorldsFolder + "\\", "");

		message += _commas + worldName + _commas + _nextValue;
		message += warp.WarpLocation.getX()+ _nextValue; 
		message += warp.WarpLocation.getY()+ _nextValue; 
		message += warp.WarpLocation.getZ()+ _nextValue; 
		message += warp.WarpLocation.getYaw()+ _nextValue; 
		message += warp.WarpLocation.getPitch();
		message += ");";
		
		return message;		
	}
	
	public String LoadWarp(String warpName)
	{
		String message = "Select * from " + WarpTableName + " where Name like " + _commas + warpName + _commas + ";" ;
		
		return message;		
	}
	
	public String DeleteWarp(String warpName)
	{
		String message = "delete from " + WarpTableName + " where Name like " + _commas + warpName + _commas + ";" ;	
		
		return message;		
	}





















	public String GetAllWorlds()
	{
		return "select * from worlds;";
	}

}