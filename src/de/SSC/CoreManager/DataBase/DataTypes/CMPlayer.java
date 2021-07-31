package de.SSC.CoreManager.DataBase.DataTypes;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DatabaseManager;
import de.SSC.CoreManager.DataBase.MySQLUtillity;
import de.SSC.CoreManager.DataBase.Tables.CoreManagerTables;
import de.SSC.CoreManager.Messages.Logger;
import de.SSC.CoreManager.Utility.BukkitUtility;

public class CMPlayer 
{
	private static DatabaseManager _databaseManager;
	
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
		  BukkitPlayer = null;
		  PlayerUUID = null;
		  IsOP = false;
		  IsCracked = true;
		  IsBanned = false;
		  PlayerName = null;
		  CustomName = null;
		  Money = 0;
		  RankGroup = null;
		  IP = null; 
		  Registered = null;
		  LastSeen = null;
		  
		  if(_databaseManager == null)
		  {
			  _databaseManager = DatabaseManager.Instance(); 
		  }
	}
	
	public CMPlayer(Player player)
	{		
		Config config = Config.Instance();
		BukkitUtility bukkitUtility = BukkitUtility.Instance();
		CMRankList rankList = CMRankList.Instance();
		
		BukkitPlayer = player;
		PlayerUUID = player.getUniqueId();
		IsOP = player.isOp();
		IsCracked = bukkitUtility.IsServerCracked();
		IsBanned = false;
		PlayerName = player.getName();
		CustomName = null;
		Money = config.Econemy.StartMoney;
		RankGroup = rankList.GetDefaultRank();
		IP = bukkitUtility.GetPlayerIP(player); 
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
		Config config = Config.Instance();
		
		String Yes =  "&aYes";
		String No = "&cNo";
		int userID = 0;
		
		message = "\n&6---[&eUser No&6.&e" +userID + "&6]--------------------------------";
		message += "\n&6 Name &7: &f" + PlayerName;
		message += "\n&6 aka &7: &f" + (CustomName == null ? "&8-" : CustomName);;
		message += "\n&6 OP &7: &f" + (IsOP ? Yes : No);
		message += "\n&6 Cracked &7: &f" + (IsCracked ? Yes : No);
		message += "\n&6 Banned &7: &f" + (IsBanned ? Yes : No);
		message += "\n&6 Money &7: &f" + Money + " " +  config.Econemy.CurrencySymbol;
		message += "\n&6 Rank &7: &f" + RankGroup.ColorTag; 
		message += "\n&6 IP &7: &f" + IP;
		message += "\n&6 Registered &7: &f" + Registered ;
		message += "\n&6 Last Seen  &7: &f" + LastSeen ;
		message += "\n&6---------------------------------------------";
		
		sender.sendMessage(logger.TransformToColor(message));
	}

	public void SetRank(CMRank cmRank)
	{
		RankGroup = cmRank;
		
		_databaseManager.UpdateRank(this);
	}
	
	public String GetPlayerCustomName()
	{
		String normalName = BukkitPlayer.getName();
		String customName = BukkitPlayer.getDisplayName();
		String playerName;
		
		if(customName == null)
		{
			playerName = normalName;
		}
		else
		{
			playerName = customName;
		}
		
		return playerName;
	}
	
	public void SetCustomName(String customName)
	{
		
		boolean hasCustomName;
		
		if(customName == null)
		{
			hasCustomName = false;
		}
		else
		{
			if(customName.isEmpty())
			{
				hasCustomName = false;
			}	
			else
			{
				hasCustomName = true;
			}
		}			
		
		if(hasCustomName)
		{
			CustomName = customName;
			BukkitPlayer.setDisplayName(CustomName);
			BukkitPlayer.setCustomName(CustomName);
			BukkitPlayer.setCustomNameVisible(true);
		}
		else
		{
			CustomName = null;
			BukkitPlayer.setDisplayName(PlayerName);
			BukkitPlayer.setCustomName(PlayerName);
			BukkitPlayer.setCustomNameVisible(false);
		}
		
		_databaseManager.UpdateCustomName(this);
	}
		
	public void SetDataForServerSide() 
	{
		CMRankList rankList;
		
		BukkitPlayer.setOp(IsOP);
		
		SetCustomName(CustomName);
		
		if(IsBanned)
		{
			rankList = CMRankList.Instance();
			
			RankGroup = rankList.GetRankFromName("Ban");
		}		
	}
}
