package de.BitFire.CoreManager.Cache;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.BitFire.CoreManager.Modules.Player.Player;

public class PlayerCache 
{
	// Bukkit
	public Map<UUID, org.bukkit.entity.Player> _bukkitPlayerLookUpTable; // PlayerUUID - BukkitPlayer
	
	// Database
	public Map<Integer, Player> _playerLookUpTable;	// PlayerID - Player
	
	// Utility
	public Map<String, Integer> _playerIPLookUpTable; // IP - PlayerID
	public Map<String, Integer> _playerNameLookUpTable; // PlayerName - PlayerID
	public Map<UUID, Integer> _playerIDLookUpTable; // PlayerUUID - PlayerID
	
	public PlayerCache()
	{
		ClearAndReserve(0);
	}
	
	public int GetNextPlayerID()
	{
		final int lengh = _playerLookUpTable.size();
		int index = 0;
		
		while(index < lengh)
		{
			final Player player = GetPlayer(index);
			
			if(player == null)
			{
				return index; 
			}
			
			index++;
		}
		
		return index;
	}
	
	public void ClearAndReserve(final int amount)
	{
		_bukkitPlayerLookUpTable = new HashMap<UUID, org.bukkit.entity.Player>(amount);
		_playerLookUpTable = new HashMap<Integer, Player>(amount);
		_playerIPLookUpTable = new HashMap<String, Integer>(amount);
		_playerNameLookUpTable = new HashMap<String, Integer>(amount);
		_playerIDLookUpTable = new HashMap<UUID, Integer>(amount);
	}
	
	public int AmountOfCachedPlayers()
	{
		return _playerLookUpTable.size();
	}
	
	public Player CachePlayer(final org.bukkit.entity.Player bukkitPlayer)
	{
		final UUID playerUUID = bukkitPlayer.getUniqueId();		
		Player player = GetPlayer(playerUUID);
		
		if(player == null)
		{
			final String ipAdress = bukkitPlayer.getAddress().getHostName();
			final String playerName = bukkitPlayer.getName();
			final int playerID = GetNextPlayerID();
			
			player = new Player(playerID, playerName, playerUUID);
			
			_bukkitPlayerLookUpTable.put(playerUUID, bukkitPlayer);
			_playerIDLookUpTable.put(playerUUID, playerID);
			_playerNameLookUpTable.put(playerName.toLowerCase(), playerID);
			_playerIPLookUpTable.put(ipAdress, playerID);
			_playerLookUpTable.put(playerID, player);
		}	
		
		return player;
	}
	
	public Player GetPlayer(final int id) 
	{
		return _playerLookUpTable.get(id);
	}
	
	public Player GetPlayer(final InetAddress inetAddress) 
	{
		final String hostName = inetAddress.getHostName();
		final Integer playerID = _playerIPLookUpTable.get(hostName);
		
		if(playerID != null)
		{
			return GetPlayer(playerID);
		}
		
		return null;
	}

	public Player GetPlayer(final UUID playerUUID) 
	{
		final Integer playerID = _playerIDLookUpTable.get(playerUUID);
		
		if(playerID != null)
		{			
			return GetPlayer(playerID);
		}
		
		return null;
	}
	
	public Player GetPlayer(final String playerName) 
	{				
		final Integer playerID = _playerNameLookUpTable.get(playerName.toLowerCase());
		
		if(playerID != null)
		{			
			return GetPlayer(playerID);
		}
		
		return null;
	}

	public org.bukkit.entity.Player GetBukkitPlayer(final String targetedPlayerName) 
	{
		final Player player = GetPlayer(targetedPlayerName.toLowerCase());
		final org.bukkit.entity.Player bukkitPlayer = _bukkitPlayerLookUpTable.get(player.UniqueIdentifierCracked);
		
		return bukkitPlayer;
	}
}