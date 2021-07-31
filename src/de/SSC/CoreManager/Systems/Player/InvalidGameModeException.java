package de.SSC.CoreManager.Systems.Player;

import de.SSC.CoreManager.Systems.Chat.ExceptionInformation;

public class InvalidGameModeException extends Exception 
{
	private static final long serialVersionUID = 8853767015451446831L;
	public ExceptionInformation Information;
	public String WantedGameMode;
	
	public InvalidGameModeException(ExceptionInformation exceptionInformation, String gamemode) 
    {
        super();
        
        Information = exceptionInformation;
        WantedGameMode = gamemode;
    }
}