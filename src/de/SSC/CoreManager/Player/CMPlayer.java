package de.SSC.CoreManager.Player;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.SSC.CoreManager.Chat.Logger;
import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DataBaseSystem;
import de.SSC.CoreManager.Economy.EconemyAccount;
import de.SSC.CoreManager.Rank.CMRank;
import de.SSC.CoreManager.Rank.RankSystem;
import de.SSC.BukkitAPI.BukkitAPISystem;

public class CMPlayer 
{	
	//private PermissionAttachment _permissionAttachment;
	
	public PlayerState State;
	public Player BukkitPlayer;
	public final UUID PlayerUUID;
	public int ID;
	public boolean IsOP;
	public boolean IsCracked;
	public boolean IsBanned;
	public boolean IsGodModeActive;
	public String PlayerName;
	public String CustomName;
	public EconemyAccount Money;
	public CMRank RankGroup;
	public String IP; 
	public Timestamp Registered;
	public Timestamp LastSeen;	
	
	public CMPlayer(final UUID playerUUID)
	{
		RankSystem rankSystem = RankSystem.Instance();
		Config config = Config.Instance();
		BukkitAPISystem bukkitUtility = BukkitAPISystem.Instance();
		
		State = PlayerState.Undefined;
		ID = -1;
		BukkitPlayer = null;
		PlayerUUID = playerUUID;
		IsOP = false;
		IsCracked = bukkitUtility.Server.IsServerCracked();
		IsBanned = false;
		PlayerName = "[N/A]";
		CustomName = "&7[&cN&8/&cA&7]";
		IsGodModeActive = false;
		Money = new EconemyAccount(config.Econemy.StartMoney);
		RankGroup = rankSystem.GetDefaultRank();
		IP = null; 
		Registered = Timestamp.valueOf(LocalDateTime.now());
		LastSeen = Timestamp.valueOf(LocalDateTime.now());	  
	}
		
	public void MergeBukkitPlayer(Player player)
	{
		BukkitAPISystem bukkitUtility = BukkitAPISystem.Instance();			
		
		State = PlayerState.Online;
		BukkitPlayer = player;
		IsOP = player.isOp();
		
		PlayerName = player.getName();
		
		IP = bukkitUtility.Player.GetPlayerIP(player); 
		
		//JavaPlugin plugin = (JavaPlugin) Bukkit.getPluginManager().getPlugin("CoreManager");
		//_permissionAttachment = player.addAttachment(plugin);
	}
		
	public boolean IsPlayerInGame() 
	{
		return BukkitPlayer != null;
	}
		
	private int GetPassedDays(Timestamp timestamp)
	{
		Timestamp now = Timestamp.valueOf(LocalDateTime.now());
		long diffInMillies = Math.abs(timestamp.getTime() - now.getTime());       
		long x = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS); 
		int y = Math.toIntExact(x);
		
		return y;
	}
	
	public void GetPlayerInfo(CommandSender sender)
	{
		final String Yes =  "&aYes";
		final String No = "&cNo";
		final int daysSinceRegistered = GetPassedDays(Registered);
		final int getDaysSinceLastSeen = GetPassedDays(LastSeen);
		final boolean isOnline = State != PlayerState.Offline;
		final boolean isFlying = BukkitPlayer == null ? false : BukkitPlayer.isFlying();
		String message;
		Logger logger = Logger.Instance();
		Config config = Config.Instance();
				
		message = "\n&6---[&e User No&6.&e" + ID + "&6 ]-----[ &eOnline &7: " + (isOnline ? Yes : No) + "&6 ]----------------------";
		message += "\n&6 Name &7: &r" + PlayerName + " &8/ &r" + (CustomName == null ? "&r-" : CustomName);;
		message += "\n&6 OP &7: &r" + (IsOP ? Yes : No);
		message += "\n&6 Flying &7: &r" +  (isFlying ? Yes : No);
		message += "\n&6 GodMode &7: &r" +  (IsGodModeActive ? Yes : No);
		message += "\n&6 Cracked &7: &r" + (IsCracked ? Yes : No);
		message += "\n&6 Banned &7: &r" + (IsBanned ? Yes : No);
		message += "\n&6 Money &7: &r" + Money.GetValue() + " &6" +  config.Econemy.CurrencySymbol;
		message += "\n&6 Rank &7: &r" + RankGroup.Tag; 
		message += "\n&6 IP &7: &r" + IP;
		message += "\n&6 Registered &7: &r" + DateFormatter(Registered) + " &8(" + (daysSinceRegistered == 0 ? "&7Now" : ("&7"+ daysSinceRegistered + " Days ago")) + "&8)";
		message += "\n&6 Last Seen &7: &r" + DateFormatter(LastSeen) + " &8(" + (getDaysSinceLastSeen == 0 ? "&7Now" : ("&7"+ getDaysSinceLastSeen + " Days ago")) + "&8)";
		message += "\n&6-----------------------------------------------------";
		
		sender.sendMessage(logger.TransformToColor(message));
	}

	@SuppressWarnings("deprecation")
	private String DateFormatter(Timestamp timestamp)
	{
		//(0 = Sunday, 1 = Monday, 2 = Tuesday, 3 = Wednesday, 4 = Thursday, 5 = Friday, 6 = Saturday) 
		int day = timestamp.getDate();
		int month = timestamp.getMonth() +1;
		int year = (timestamp.getYear() + 1900);
		int hour = timestamp.getHours();
		int minutes = timestamp.getMinutes();		
		
		return String.format("%02d", day) + 
				"." + 
				String.format("%02d",month) + 
				"." + 
				String.format("%04d",year)+
				" &7at&r " + 
				String.format("%02d",hour) + 
				":" + 
				String.format("%02d",minutes);
	}
	
	public void SetRank(CMRank cmRank)
	{
		RankGroup = cmRank;	
		
		DataBaseSystem.Instance().Rank.UpdateRank(this);
	}
	
	public String GetPlayerCustomName()
	{
		final boolean isOfflinePlayer = State == PlayerState.Offline;
		String customName;
		
		if(isOfflinePlayer)
		{
			customName = CustomName;
		}
		else
		{
			final String bukkitNormalName = BukkitPlayer.getName();
			final String bukkitCustomName = BukkitPlayer.getDisplayName();
			
			customName = (bukkitCustomName == null) ? bukkitNormalName : bukkitCustomName;
		}			
		
		return customName;
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
		
		DataBaseSystem.Instance().Player.UpdateCustomName(this);
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
	
	public void SetPermissions()
	{
		/*
		List<String> permissions = RankGroup.Permission.GetPermissions();		
		
		for(String permission : permissions)
		{
			//_permissionAttachment.setPermission(permission, true);
		}
		*/
	}

	public String GetFullName() 
	{
		return "&7[&r" + RankGroup.Tag + "&7] " + RankGroup.PlayerColor + GetPlayerCustomName();
	}

	public boolean IsOffline() 
	{
		return 	State == PlayerState.Offline;
	}
}
