package de.BitFire.API.Bukkit;

public final class BukkitAPIWeather 
{
	private final static String commandRain = "weather clear";
	private final static String commandDay = "time set 0";
	private final static String commandNight = "time set 10000";
	  	  
	private BukkitAPIWeather()
	{
		 
	}
	
	public final static void Rain()
	{		
		BukkitAPIServer.SendVanillaCommand(commandRain);
	}

	public final static void Day()
	{		
		BukkitAPIServer.SendVanillaCommand(commandDay);
	}

	public final static void Night() 
	{		
		BukkitAPIServer.SendVanillaCommand(commandNight);
	}
}