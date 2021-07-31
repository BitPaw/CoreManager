package de.SSC.CoreManager.Systems.Player;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Systems.Chat.Logger;
import de.SSC.CoreManager.Systems.DataBase.DataBaseSystem;
import de.SSC.CoreManager.Systems.DataBase.MySQLUtillity;
import de.SSC.CoreManager.Systems.DataBase.Tables.CoreManagerTables;
import de.SSC.CoreManager.Systems.Rank.CMRank;
import de.SSC.CoreManager.Systems.Rank.RankSystem;
import de.SSC.CoreManager.Utility.BukkitUtility;

public class CMPlayer 
{
	private static DataBaseSystem _databaseManager;
	
	public Player BukkitPlayer;
	public UUID PlayerUUID;
	public boolean IsOP;
	public boolean IsCracked;
	public boolean IsBanned;
	public boolean IsGodModeActive;
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
		  IsGodModeActive = false;
		  Money = 0;
		  RankGroup = null;
		  IP = null; 
		  Registered = null;
		  LastSeen = null;
		  
		  if(_databaseManager == null)
		  {
			  _databaseManager = DataBaseSystem.Instance(); 
		  }
	}
	
	public CMPlayer(Player player)
	{		
		Config config = Config.Instance();
		BukkitUtility bukkitUtility = BukkitUtility.Instance();
		RankSystem rankSystem = RankSystem.Instance();
		
		BukkitPlayer = player;
		PlayerUUID = player.getUniqueId();
		IsOP = player.isOp();
		IsCracked = bukkitUtility.IsServerCracked();
		IsBanned = false;
		PlayerName = player.getName();
		CustomName = null;
		Money = config.Econemy.StartMoney;
		RankGroup = rankSystem.GetDefaultRank();
		IP = bukkitUtility.PlayerUtility.GetPlayerIP(player); 
		Registered = Timestamp.valueOf(LocalDateTime.now());
		LastSeen = Timestamp.valueOf(LocalDateTime.now());
	}
	
	public String ToMySQLInsertMessage()
	{
		String out = "insert into " + CoreManagerTables.player.toString() + " values \n (\n";

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
	
	private int GetDaysSinceRegistered(Timestamp a, Timestamp b)
	{		
		long diffInMillies = Math.abs(a.getTime() - b.getTime());       
		long x = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS); 
		int y = Math.toIntExact(x);
		
		return y;
	}
	
	public void GetPlayerInfo(CommandSender sender)
	{
		String message;
		Logger logger = Logger.Instance();
		Config config = Config.Instance();
		
		String Yes =  "&aYes";
		String No = "&cNo";
		int userID = 0;
		int daysSinceRegistered = GetDaysSinceRegistered(Registered, LastSeen);
		
		message = "\n&6---[&eUser No&6.&e" + userID + "&6]--------------------------------";
		message += "\n&6 Name &7: &r" + PlayerName + " &7/ &6aka &7: &r" + (CustomName == null ? "&8-" : CustomName);;
		message += "\n&6 OP &7: &r" + (IsOP ? Yes : No);
		message += "\n&6 Flying &7: &r" +  (BukkitPlayer.isFlying() ? Yes : No);
		message += "\n&6 GodMode &7: &r" +  (IsGodModeActive ? Yes : No);
		message += "\n&6 Cracked &7: &r" + (IsCracked ? Yes : No);
		message += "\n&6 Banned &7: &r" + (IsBanned ? Yes : No);
		message += "\n&6 Money &7: &r" + Money + " " +  config.Econemy.CurrencySymbol;
		message += "\n&6 Rank &7: &r" + RankGroup.ColorTag; 
		message += "\n&6 IP &7: &r" + IP;
		message += "\n&6 Registered &7: &r" + Registered + " &8(&7"+ daysSinceRegistered + " Days ago&8)";
		message += "\n&6 Last Seen  &7: &r" + LastSeen ;
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
		RankSystem rankSystem = RankSystem.Instance();
		
		BukkitPlayer.setOp(IsOP);
		
		SetCustomName(CustomName);
		
		if(IsBanned)
		{			
			RankGroup = rankSystem.GetRank("Ban");
		}		
	}
}
