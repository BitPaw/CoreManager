package de.BitFire.CoreManager.Modules.Player;

public class PlayerNotFoundException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public final String MissingPlayerName;
	
	public PlayerNotFoundException(final String playerName)
	{
		MissingPlayerName = playerName;
	}
}