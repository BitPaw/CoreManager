package de.BitFire.Player.Exception;

import java.util.UUID;

public class PlayerDoesNotExistException extends Exception 
{
	private static final long serialVersionUID = 1L;
	public final UUID NotExistingPlayerUUID;
	
	public PlayerDoesNotExistException(final UUID playerUUID) 
    {
        super("Player with a name " + playerUUID.toString() + " does not exist");
        
        NotExistingPlayerUUID = playerUUID;        
    }
}
