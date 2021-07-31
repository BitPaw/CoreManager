package de.SSC.CoreManager.Player.Exception;


public class InvalidPlayerNameException extends Exception 
{
	private static final long serialVersionUID = -8608762864556826277L;
	public final String NotFoundPlayerName;
	
	public InvalidPlayerNameException() 
    { 
		this(null);
    }
	
	public InvalidPlayerNameException(String playerName) 
    { 
        super("Invalid playername : " + (playerName == null ? "null" : playerName));
        
        NotFoundPlayerName = playerName;        
    }
}
