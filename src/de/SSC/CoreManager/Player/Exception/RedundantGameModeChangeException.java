package de.SSC.CoreManager.Player.Exception;

import org.bukkit.GameMode;

public class RedundantGameModeChangeException extends Exception 
{
	private static final long serialVersionUID = 199528234417662072L;
	public GameMode PlayerGameMode;
	
	public RedundantGameModeChangeException(GameMode gamemode) 
    {
        super();
        
        PlayerGameMode = gamemode;
    }
}