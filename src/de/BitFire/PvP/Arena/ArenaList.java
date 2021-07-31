package de.BitFire.PvP.Arena;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ArenaList 
{
	private Map<Integer, Arena> _arenaMap;
	
	public ArenaList()
	{
		_arenaMap = new HashMap<Integer, Arena>();
	}
	
	public void Add(List<Arena> getAllArenas)
	{
		for(Arena arena : getAllArenas)
		{
			Add(arena);
		}
	}
	
	public void Add(Arena arena)
	{
		_arenaMap.put(arena.ID, arena);
	}

	public Arena GetArena(final int id)
	{
		return _arenaMap.get(id);
	}	

	/**
	 * Searches in every arena for the playerUUID.
	 * @param playerUUID : Unique identification of the player.
	 * @return Null or the Arena the player is in.
	 */
	public Arena GetArena(UUID playerUUID) 
	{
		final Collection<Arena> arenas = _arenaMap.values();
		
		for(final Arena arena : arenas)
		{			
			if(arena.Lobby.IsPlayerInLobby(playerUUID))
			{
				return arena;
			}
		}
		
		return null;
	}
	
	/*
	*
	 * @param playerUUID : The unique identification of the player
	 * @return '-1' means player is not in any Lobby.<br>
	 * Any other number, the <b>LobbyID</b> that the player is in. 
	 *
	public int GetPlayerLobby(final UUID playerUUID)
	{
		final Set<Integer> lobbyIDs = _playerList.keySet();
		
		for(final Integer lobbyID : lobbyIDs)
		{			
			if(IsPlayerInLobby(lobbyID, playerUUID))
			{
				return lobbyID;
			}
		}
		
		return -1;
	}
	*/
	public String PrintData() 
	{
		String message = "&6=====[&eArenas&6]=====";
		
		for (Map.Entry<Integer, Arena> entry : _arenaMap.entrySet()) 
		{
			final Integer id = entry.getKey(); 
			final Arena arena = entry.getValue();
			
			message += "\n&eID&6:&r" +  id + " &eName&6:&r" + arena.Name + " &8[&e" + arena.Mode + "&8]";
		}
		
		message += "&6==================";
		
		return message;
	}

	public List<Arena> GetAllArenas() 
	{
		return new ArrayList<Arena>(_arenaMap.values());
	}
}