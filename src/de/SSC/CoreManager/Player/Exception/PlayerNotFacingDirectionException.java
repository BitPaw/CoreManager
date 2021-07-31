package de.SSC.CoreManager.Player.Exception;

import de.SSC.CoreManager.Player.ViewDirection;

public class PlayerNotFacingDirectionException  extends Exception 
{
	private static final long serialVersionUID = 6484757550427544005L;
	public final ViewDirection CurrentlyFacingDirection;
	
	public PlayerNotFacingDirectionException(ViewDirection viewDirection) 
    {
        super("No direction, current : " + viewDirection.toString());
        
        CurrentlyFacingDirection = viewDirection;        
    }
}
