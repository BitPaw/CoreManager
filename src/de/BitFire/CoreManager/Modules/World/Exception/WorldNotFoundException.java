package de.BitFire.CoreManager.Modules.World.Exception;

public class WorldNotFoundException extends Exception
{
	private static final long serialVersionUID = 1L;
	public final String MissingWorldName;
	
	public WorldNotFoundException(String missingWorldName)
	{
		MissingWorldName = missingWorldName;
	}
}