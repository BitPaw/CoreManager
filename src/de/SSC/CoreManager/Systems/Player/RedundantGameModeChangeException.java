package de.SSC.CoreManager.Systems.Player;

import org.bukkit.GameMode;

import de.SSC.CoreManager.Systems.Chat.ExceptionInformation;

public class RedundantGameModeChangeException extends Exception 
{
	private static final long serialVersionUID = 199528234417662072L;
	public ExceptionInformation Information;
	public GameMode PlayerGameMode;
	
	public RedundantGameModeChangeException(ExceptionInformation exceptionInformation, GameMode gamemode) 
    {
        super();
        
        Information = exceptionInformation;
        PlayerGameMode = gamemode;
    }
}