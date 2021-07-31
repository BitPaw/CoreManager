package de.BitFire.CoreManager.Modules.Player;

import java.net.InetAddress;
import java.util.UUID;

import org.bukkit.GameMode;
import de.BitFire.CoreManager.Cache.PlayerCache;

public class PlayerManager 
{
	private static PlayerCache _cache;
	
	static 
	{
		_cache = new PlayerCache();
	}		
		 
	public static boolean IsPlayerRegistered(final UUID playerUUID)
	{
		return _cache.GetPlayer(playerUUID) != null;
	}

	public static Player LoadPlayer(final org.bukkit.entity.Player bukkitPlayer) 
	{
		return _cache.CachePlayer(bukkitPlayer);
	}

	public static void UnloadPlayer(final UUID playerUUID) 
	{
		//_cache.DeCache(playerUUID);
	}

	public static Player GetPlayer(final InetAddress inetAddress) 
	{		
		return _cache.GetPlayer(inetAddress);
	}

	public static Player GetPlayer(final UUID playerUUID) 
	{		
		return _cache.GetPlayer(playerUUID);
	}
	
	public static Player GetPlayer(final int ownerID) 
	{
		return _cache.GetPlayer(ownerID);		
	}
	
	public static void ChangeGameMode(final org.bukkit.entity.Player player, final GameMode gameMode)
	{
		player.setGameMode(gameMode);
	}
	
	
	public static void ChangeGameMode(final org.bukkit.entity.Player player, final int gameModeID)
	{
		@SuppressWarnings("deprecation")
		GameMode gameMode = GameMode.getByValue(gameModeID);
		
		player.setGameMode(gameMode);
	}

	public static org.bukkit.entity.Player GetBukkitPlayer(String targetedPlayerName) 
	{
		return _cache.GetBukkitPlayer(targetedPlayerName);
	}
}