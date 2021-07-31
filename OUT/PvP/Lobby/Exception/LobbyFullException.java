package de.BitFire.PvP.Lobby.Exception;

public class LobbyFullException extends Exception 
{
	private static final long serialVersionUID = 1L;
	private final int _maximalPlayer;
	
	public LobbyFullException(int maximalPlayers) 
    {
        super(); 
        
        _maximalPlayer = maximalPlayers;
    }
	
	public int GetMaximalPlayers()
	{
		return _maximalPlayer;
	}
}
