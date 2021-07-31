package de.SSC.CoreManager.Systems.Player;

import de.SSC.CoreManager.Systems.Chat.ExceptionInformation;

public class PlayerNotFoundException extends Exception 
{
	private static final long serialVersionUID = 67707350094471229L;
	public final ExceptionInformation Information;
	public final String NotFoundPlayerName;
	
	public PlayerNotFoundException(ExceptionInformation exceptionInformation, String playerName) 
    {
        super("Missing playername : " + playerName);
        
        Information = exceptionInformation;
        NotFoundPlayerName = playerName;        
    }
}
