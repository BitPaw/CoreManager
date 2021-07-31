package de.SSC.CoreManager.DataBase.DataTypes;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Logger;
import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.Tables.CoreManagerTables;
import de.SSC.CoreManager.Utility.MySQLUtillity;

public class CMPlayer 
{
	public Player BukkitPlayer;
	public UUID PlayerUUID;
	public boolean IsOP;
	public boolean IsCracked;
	public boolean IsBanned;
	public String PlayerName;
	public String CustomName;
	public float Money;
	public CMRank RankGroup;
	public String IP; 
	public Timestamp Registered;
	public Timestamp LastSeen;
	
	public CMPlayer()
	{
		
	}
	
	public CMPlayer(Player player)
	{		
		Config config = Config.Instance();
		CMRankList rankList = CMRankList.Instance();
		
		BukkitPlayer = player;
		PlayerUUID = player.getUniqueId();
		IsOP = player.isOp();
		IsCracked = false;
		IsBanned = false;
		PlayerName = player.getDisplayName();
		CustomName = player.getCustomName();
		Money = config.Econemy.StartMoney;
		RankGroup = rankList.GetDefaultRank();
		IP = player.getAddress().getHostName(); 
		Registered = Timestamp.valueOf(LocalDateTime.now());
		LastSeen = Timestamp.valueOf(LocalDateTime.now());
	}
	
	public String ToMySQLInsertMessage()
	{
		String out = "insert into " + CoreManagerTables.players.toString() + " values \n (\n";

			out += MySQLUtillity.StringToMySQLText(PlayerUUID.toString());
			out += MySQLUtillity.BooleanToMySQLText(IsOP);
			out += MySQLUtillity.BooleanToMySQLText(IsCracked);
			out += MySQLUtillity.BooleanToMySQLText(IsBanned);
			out += MySQLUtillity.StringToMySQLText(PlayerName);
			out += MySQLUtillity.StringToMySQLText(CustomName);
			out += MySQLUtillity.FloatToMySQLText(Money);
			out += MySQLUtillity.StringToMySQLText(RankGroup.RankName);
			out += MySQLUtillity.StringToMySQLText(IP);
			out += MySQLUtillity.StringToMySQLText(Registered.toString()); 
			out += MySQLUtillity.LastStringToMySQLText(LastSeen.toString());
			out += ");";
			
		return out;
	}
	
	public void GetPlayerInfo(CommandSender sender)
	{
		String message;
		Logger logger = Logger.Instance();
		
		String Yes =  "&aYes";
		String No = "&cNo";
		
		message = "\n&6---&eUser No&6.&e??&6---";
		message += "\n&6Name &7: &f" + PlayerName;
		message += "\n&6aka &7: &f" + (CustomName == "null" ? "&8-" : CustomName);;
		message += "\n&6Is OP &7: &f" + (IsOP ? Yes : No);
		message += "\n&6Cracked &7: &f" +(IsCracked ? Yes : No);
		message += "\n&6Banned &7: &f" +(IsBanned ? Yes : No);
		message += "\n&6Money &7: &f" + Money;
		message += "\n&6Rank  &7: &f" +	RankGroup.ColorTag; 
		message += "\n&6IP &7: &f" +IP;
		message += "\n&6Registered &7: &f" +Registered ;
		message += "\n&6Last Seen &7: &f" + LastSeen ;
		message += "\n&6--------------";
		
		sender.sendMessage(logger.TransformToColor(message));
	}
}
