package de.SSC.CoreManager.Utility;

public class BukkitUtilityWeather 
{
	  private final String commandRain = "weather clear";
	  private final String commandDay = "time set 0";
	  private final String commandNight = "time set 10000";

	  private BukkitUtility _bukkitUtility;
	  
	  public BukkitUtilityWeather(BukkitUtility bukkitUtility)
	  {
		  _bukkitUtility = bukkitUtility;
	  }
	  
	public void Rain()
	{
		_bukkitUtility.SendVanillaCommand(commandRain);
	}

	public void Day()
	{
		_bukkitUtility.SendVanillaCommand(commandDay);
	}

	public void Night() 
	{
		_bukkitUtility.SendVanillaCommand(commandNight);	
	}

}
