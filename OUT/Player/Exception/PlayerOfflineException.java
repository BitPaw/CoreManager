package de.BitFire.Player.Exception;

public class PlayerOfflineException extends Exception 
{
	private static final long serialVersionUID = 199528234417662072L;
	public String OfflinePlayerName;
	
	public PlayerOfflineException(String playerName) 
    {
        super();
        
        OfflinePlayerName = playerName;
    }
}