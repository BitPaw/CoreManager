package de.SSC.CoreManager.Player.Exception;


public class InvalidGameModeException extends Exception 
{
	private static final long serialVersionUID = 8853767015451446831L;
	public String WantedGameMode;
	
	public InvalidGameModeException(String gamemode) 
    {
        super();
        
        WantedGameMode = gamemode;
    }
}