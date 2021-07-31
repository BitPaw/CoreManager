package de.BitFire.CoreManager.Modules.World.Exception;

public class WorldAlreadyLoadedExeption extends Exception 
{
	private static final long serialVersionUID = 1L;
	public final String WorldName;
	
	public WorldAlreadyLoadedExeption(final String worldName)
	{
		WorldName = worldName;
	}
}
