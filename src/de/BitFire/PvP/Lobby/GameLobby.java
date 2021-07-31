package de.BitFire.PvP.Lobby;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.BitFire.PvP.Lobby.Exception.LobbyAlreadyClosedException;
import de.BitFire.PvP.Lobby.Exception.LobbyAlreadyOpenException;
import de.BitFire.PvP.Lobby.Exception.LobbyAlreadyRunningException;
import de.BitFire.PvP.Lobby.Exception.LobbyClosedException;
import de.BitFire.PvP.Lobby.Exception.LobbyFullException;
import de.BitFire.PvP.Lobby.Exception.PlayerAlreadyInLobbyException;
import de.BitFire.PvP.Lobby.Exception.PlayerNotInLobbyException;

/**
 * A lobby system that can be used for pvp game lobbys.
 * @author BitPaw
 */
public class GameLobby
{
	private Map<UUID, Integer> _playerList;
	private LobbyState _state;
	private final int _maximalPlayer;
	private int _currentPlayer;
		
	public GameLobby(final int maximalPlayerAmount)
	{
		_state = LobbyState.Closed;
		_currentPlayer = 0;
		_maximalPlayer = maximalPlayerAmount;
		_playerList = new HashMap<UUID, Integer>(maximalPlayerAmount);		
	}
	
	public int GetMaximalPlayer()
	{
		return _maximalPlayer;
	}
	
	public int GetCurrentPlayers()
	{
		return _currentPlayer;
	}
	
	public LobbyState GetState()
	{
		return _state;
	}
	
	public boolean IsPlayerInLobby(final UUID playerUUID)
	{
		return _playerList.containsKey(playerUUID);
	}
	
	public boolean IsLobbyFull()
	{
		return _currentPlayer >= _maximalPlayer;
	}
		
	public boolean IsLobbyOpen()
	{
		return !_state.equals(LobbyState.Closed);
	}
	
	public boolean IsAlreadyRunning()
	{
		return _state.equals(LobbyState.Running) || _state.equals(LobbyState.Finished);
	}
	
	private void UpdateState()
	{
		if(_state.equals(LobbyState.Empty) || _state.equals(LobbyState.Waiting))
		{
			_state = GetCurrentPlayers() > 0 ? LobbyState.Waiting : LobbyState.Empty;
		}
	}
	
	public void AddPlayer(final UUID playerUUID) throws PlayerAlreadyInLobbyException, LobbyFullException, LobbyClosedException, LobbyAlreadyRunningException
	{	
		// Is the Lobby open?
		if(!IsLobbyOpen())
		{
			throw new LobbyClosedException();
		}	
		
		// Is the lobby already running?
		if(IsAlreadyRunning())
		{
			throw new LobbyAlreadyRunningException();
		}
		
		// Is player already in a lobby?
		if(IsPlayerInLobby(playerUUID))
		{
			throw new PlayerAlreadyInLobbyException();
		}		
		
		// Is there any slot left?
		if(IsLobbyFull())
		{
			throw new LobbyFullException(_maximalPlayer);
		}		
		
		_currentPlayer++;
		_playerList.put(playerUUID, GetCurrentPlayers());		
		
		UpdateState();
	}
	
	/**
	 * Remove a player from the game lobby.
	 * @param playerUUID : The unique identification of the player.
	 * @throws PlayerNotInLobbyException : Player is not in the lobby, so he can't be removed.
	 */
	public void RemovePlayer(final UUID playerUUID) throws PlayerNotInLobbyException
	{
		if(!IsPlayerInLobby(playerUUID))
		{
			throw new PlayerNotInLobbyException();
		}
		
		_playerList.remove(playerUUID);
		_currentPlayer--;
		
		UpdateState();
	}

	public void Open() throws LobbyAlreadyOpenException 
	{		
		if(IsLobbyOpen())
		{
			throw new LobbyAlreadyOpenException();
		}
	
		_state = LobbyState.Empty;
	}

	public void Close() throws LobbyAlreadyClosedException 
	{
		if(!IsLobbyOpen())
		{
			throw new LobbyAlreadyClosedException();
		}
		
		_state = LobbyState.Closed;	
	}
}