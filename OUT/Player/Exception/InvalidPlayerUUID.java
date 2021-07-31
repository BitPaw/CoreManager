package de.BitFire.Player.Exception;

import java.util.UUID;

public class InvalidPlayerUUID extends Exception 
{
	private static final long serialVersionUID = 1L;
	public final UUID InvalidPlayerUUID;
	
	public InvalidPlayerUUID(UUID playerUUID) 
    {
        super("Missing or invalid player UUID : " + playerUUID.toString());
        
        InvalidPlayerUUID = playerUUID;        
    }
}
