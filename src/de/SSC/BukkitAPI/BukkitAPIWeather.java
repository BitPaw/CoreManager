package de.SSC.BukkitAPI;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

public class BukkitAPIWeather 
{
	  private final String commandRain = "weather clear";
	  private final String commandDay = "time set 0";
	  private final String commandNight = "time set 10000";

	  private Server _server;
	  private CommandSender _console;
	  	  
	  public BukkitAPIWeather()
	  {
		  _server  = Bukkit.getServer();
		  _console = Bukkit.getConsoleSender();
	  }
	  
	public void Rain()
	{
		_server.dispatchCommand(_console, commandRain);
	}

	public void Day()
	{
		_server.dispatchCommand(_console, commandDay);
	}

	public void Night() 
	{
		_server.dispatchCommand(_console, commandNight);
	}
}
