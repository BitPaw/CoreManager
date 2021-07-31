package de.BitFire.Player;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.BitFire.Chat.Logger;
import de.BitFire.Configuration.Config;
import de.BitFire.DataBase.DataBaseSystem;
import de.BitFire.Player.Exception.PlayerOfflineException;
import de.BitFire.Rank.CMRank;
import de.BitFire.Rank.RankSystem;
import de.BitFire.Rank.Exception.RankNotFoundException;
import net.minecraft.server.v1_15_R1.EntityPlayer;

public class CMPlayer 
{		
	public PlayerState State;
	public Player BukkitPlayer;
	public PlayerInformation Information;
	//private PermissionAttachment _permissionAttachment;
	
	public CMPlayer(final Player player)
	{		
		State = PlayerState.Unregistered;		
		BukkitPlayer = player;	
		Information = new PlayerInformation(player);		
	}
	
	public CMPlayer(final PlayerInformation playerInformation)
	{		
		State = PlayerState.Offline;		
		BukkitPlayer = null;	
		Information = playerInformation;		
	} 
	
	
	/**
	 * 
	 * @param player : The current player
	 * @throws RankNotFoundException 
	 */
	public void AttachBukkitPlayer(final Player player)
	{
		//BukkitAPISystem bukkitUtility = BukkitAPISystem.Instance();			
		RankSystem rankSystem = RankSystem.Instance();
		
		State = PlayerState.Online;
		BukkitPlayer = player;		
		Information.Update(player);		
		
		BukkitPlayer.setOp(Information.IsOP);		
		BukkitPlayer.setGameMode(Information.PlayerGameMode);
		
		SetCustomName(Information.CustomName);

		if(Information.IsBanned)
		{			
			try 
			{
				Information.RankGroup = rankSystem.GetRank("Ban");
			}
			catch (RankNotFoundException e) 
			{
				try 
				{
					Information.RankGroup = rankSystem.GetDefaultRank();
				} 
				catch (RankNotFoundException e1) 
				{
					Information.RankGroup = rankSystem.GetFailSaveRank();
				}
			}
		}
		
		//JavaPlugin plugin = (JavaPlugin) Bukkit.getPluginManager().getPlugin("CoreManager");
		//_permissionAttachment = player.addAttachment(plugin);
	}
	
	public void DetachBukkitPlayer()
	{
		State = PlayerState.Offline;
		BukkitPlayer = null;
	}
				
	public boolean IsPlayerInGame() 
	{
		return BukkitPlayer != null;
	}
		
	public void GetPlayerInfo(CommandSender sender)
	{		
		final Logger logger = Logger.Instance();
		final Config config = Config.Instance();
		
		final String Yes = config.Message.IO.Yes;
		final String No = config.Message.IO.No;
		final String currentState = State.toString();
		final int daysSinceRegistered = Information.Registered.GetPassedDays();
		final int getDaysSinceLastSeen = Information.LastSeen.GetPassedDays();
		final boolean isFlying = BukkitPlayer == null ? false : BukkitPlayer.isFlying();
		String message;		
				
		message = "\n&6---[&e User No&6.&e" + Information.ID + "&6 ]-----[&e" + currentState + "&6]---";
		message += "\n&6 Name &7: &r" + Information.PlayerName + " &8/ &r" + (Information.CustomName == null ? "&r-" : Information.CustomName);;
		message += "\n&6 OP &7: &r" + (Information.IsOP ? Yes : No);
		message += "\n&6 GameMode &7: &r" + Information.PlayerGameMode.toString();
		message += "\n&6 Flying &7: &r" +  (isFlying ? Yes : No);
		message += "\n&6 GodMode &7: &r" +  (Information.IsGodModeActive ? Yes : No);
		message += "\n&6 Cracked &7: &r" + (Information.IsCracked ? Yes : No);
		message += "\n&6 Banned &7: &r" + (Information.IsBanned ? Yes : No);
		message += "\n&6 Money &7: &r" + Information.Money.GetValue() + " &6" +  config.Econemy.CurrencySymbol;
		message += "\n&6 Rank &7: &r" + (Information.RankGroup == null ? "&7-" : Information.RankGroup.Tag); 
		message += "\n&6 IP &7: &r" + Information.IP;
		message += "\n&6 Registered &7: &r" + Information.Registered.toString() + " &8(" + (daysSinceRegistered == 0 ? "&7Now" : ("&7"+ daysSinceRegistered + " Days ago")) + "&8)";
		message += "\n&6 Last Seen &7: &r" + Information.LastSeen.toString() + " &8(" + (getDaysSinceLastSeen == 0 ? "&7Now" : ("&7"+ getDaysSinceLastSeen + " Days ago")) + "&8)";
		message += "\n&6-----------------------------------------------";
		
		sender.sendMessage(logger.TransformToColor(message));
	}
	
	public void SetRank(CMRank cmRank)
	{
		Information.RankGroup = cmRank;	
		
		DataBaseSystem.Instance().Rank.UpdateRank(this);
	}
	
	public String GetPlayerCustomName()
	{
		final boolean isOfflinePlayer = State == PlayerState.Offline;
		String customName;
		
		if(isOfflinePlayer)
		{
			customName = Information.CustomName;
		}
		else
		{
			final String bukkitNormalName = BukkitPlayer.getName();
			final String bukkitCustomName = BukkitPlayer.getDisplayName();
			
			customName = (bukkitCustomName == null) ? bukkitNormalName : bukkitCustomName;
		}			
		
		return customName;
	}
	
	public void SetCustomName(final String customName)
	{		
		final boolean hasCustomName = customName == null ? false : customName.isEmpty(); 
		final String name = hasCustomName ? customName : Information.PlayerName;
			
		if(hasCustomName)
		{
			Information.CustomName = name;			
		}
		else
		{
			Information.CustomName = null;
		}
		
		BukkitPlayer.setDisplayName(name);
		BukkitPlayer.setCustomName(name);
		BukkitPlayer.setCustomNameVisible(hasCustomName);
		
		DataBaseSystem.Instance().Player.UpdateCustomName(this);
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
		final boolean hasRank = Information.RankGroup == null;
		String fullName = "";
		
		if(hasRank)
		{
			fullName += "&7[&r" + Information.RankGroup.Tag + "&7] " + Information.RankGroup.PlayerColor;
		}
		
		fullName += GetPlayerCustomName();
		
		return fullName;
	}
	
	
	  public final String GetCustomName() 
	  {
	  	return GetPlayerCustomName(BukkitPlayer);
	  }
	  
	
	  public final static String GetPlayerCustomName(Player player) 
	  {
	  	String playerName = player.getName();
	  	String displayName = player.getDisplayName();
	  		
	  	return displayName == null ? playerName : displayName;
	  }

	public boolean IsOffline() 
	{
		return 	State == PlayerState.Offline;
	}
	
	public final boolean IsPlayerInAir() throws PlayerOfflineException 
	{	
		if(BukkitPlayer == null)
		{
			throw new PlayerOfflineException(Information.PlayerName);
		}		
		
	  	return IsPlayerInAir(BukkitPlayer);
	}
	  
	public final static boolean IsPlayerInAir(Player player) 
	{	
	  	return  player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR;
	}
	
	public static String GetPlayerIP(final Player player)
	{
		return player.getAddress().getHostName();
	}
	
	public String GetPlayerIP()
	{
		return BukkitPlayer.getAddress().getHostName();
	}
	
	public int GetPlayerPing() 
	{		  		  
		return GetPlayerPing(BukkitPlayer);
	}	  
	
	public static int GetPlayerPing(Player player) 
	{		  
		CraftPlayer craftPlayer = (CraftPlayer) player; 		  
		EntityPlayer entityPlayer = craftPlayer.getHandle(); 
		  
		int ping = entityPlayer.ping;
		  
		return ping;
	}
}
