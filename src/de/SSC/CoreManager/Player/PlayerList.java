package de.SSC.CoreManager.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.SSC.CoreManager.Player.Exception.InvalidPlayerNameException;
import de.SSC.CoreManager.Player.Exception.InvalidPlayerUUID;
import de.SSC.CoreManager.Player.Exception.PlayerNotFoundException;

public class PlayerList 
{
	private Map<UUID, CMPlayer> _playerList;
	private Map<String, UUID> _playerUUIDList;
	private int _onlinePlayerCounter;
	
	public PlayerList()
	{
		_playerList = new HashMap<UUID, CMPlayer>();
		_playerUUIDList = new HashMap<String, UUID>();
		
		_onlinePlayerCounter = 0;
	}	
	
	public int GetNumberOfPlayers()
	{
		return _playerList.size();
	}
	
	public int GetAmountOfOnlinePlayers()
	{
		return _onlinePlayerCounter;
	}
	
	public void Add(CMPlayer cmPlayer)
	{
		String playerName = cmPlayer.PlayerName.toLowerCase();
		UUID uuid = cmPlayer.PlayerUUID;
		
		_playerList.put(uuid, cmPlayer);
		_playerUUIDList.put(playerName, uuid);
	}
	
	public void Remove(CMPlayer cmPlayer)
	{
		UUID playerUUID = cmPlayer.PlayerUUID;
		String playerName = cmPlayer.PlayerName;
		
		_playerList.remove(playerUUID);
		_playerUUIDList.remove(playerName);
	}
	
	public void Remove(UUID playerUUID) throws PlayerNotFoundException, InvalidPlayerUUID
	{
		CMPlayer cmPlayer = GetPlayer(playerUUID);
		
		Remove(cmPlayer);
	}
	
	public void Clear()
	{
		_playerList.clear();
		_playerUUIDList.clear();
	}
	
	public void Swap(final UUID playerUUID, final CMPlayer cmPlayer)
	{
		_playerList.replace(playerUUID, cmPlayer);
	}
	
	public CMPlayer GetPlayer(UUID uuid) throws PlayerNotFoundException, 
												InvalidPlayerUUID
	{
		CMPlayer cmPlayer;
		
		if(uuid == null)
		{
			throw new InvalidPlayerUUID(uuid);
		}
		
		cmPlayer = _playerList.get(uuid);
		
		if(cmPlayer == null)
		{
			throw new PlayerNotFoundException(uuid);			
		}
		
		return cmPlayer;
	}
	
	public CMPlayer GetPlayer(String playerName) throws	InvalidPlayerNameException,
														PlayerNotFoundException, 
														InvalidPlayerUUID
	{
		UUID uuid;
		CMPlayer cmPlayer;				
		
		if(playerName == null)
		{
			throw new InvalidPlayerNameException(playerName);
		}		
		
		playerName = playerName.toLowerCase();
		
		uuid = _playerUUIDList.get(playerName);
		
		if(uuid == null)
		{
			throw new PlayerNotFoundException(playerName);
		}
		
		cmPlayer = GetPlayer(uuid);
				
		return cmPlayer;
	}

	public List<CMPlayer> GetPlayers() 
	{
		List<CMPlayer> players = new ArrayList<CMPlayer>();
		
		for(Map.Entry<UUID, CMPlayer> entry : _playerList.entrySet()) 
		{
			CMPlayer cmPlayer = entry.getValue();

			players.add(cmPlayer);
		}
		
		return players; 
	}

	public boolean IsRegistered(UUID playerUUID) 
	{
		return _playerList.containsKey(playerUUID);
	}

	public CMPlayer UpdatePlayer(final PlayerState playerState, final UUID playerUUID, final Player player) throws PlayerNotFoundException, InvalidPlayerUUID 
	{
		CMPlayer cmPlayer = GetPlayer(playerUUID);
		
		cmPlayer.State = playerState;
		
		if(playerState == PlayerState.Online)
		{
			_onlinePlayerCounter++;
			cmPlayer.MergeBukkitPlayer(player);
		}
		else
		{
			_onlinePlayerCounter--;
			cmPlayer.BukkitPlayer = null;
		}		
		
		Swap(playerUUID, cmPlayer);		
		
		return cmPlayer;
	}
}