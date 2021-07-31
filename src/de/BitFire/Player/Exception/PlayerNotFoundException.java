package de.BitFire.Player.Exception;

import java.util.UUID;

public class PlayerNotFoundException extends Exception 
{
	private static final long serialVersionUID = 67707350094471229L;
	public final String NotFoundPlayerName;
	
	public PlayerNotFoundException(UUID playerUUID) 
    {
        super("Missing player UUID : " + playerUUID.toString());
        
        NotFoundPlayerName = playerUUID.toString();        
    }
	
	public PlayerNotFoundException(String playerName) 
    {
        super("Missing playername : " + playerName);
        
        NotFoundPlayerName = playerName;        
    }
}
