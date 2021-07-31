package de.SSC.CoreManager;

import org.bukkit.Bukkit;
import org.bukkit.Server;

public class WeatherController 
{
	private static String commandRain = "rain";
	private static String commandDay = "day";
	
	public static boolean Check(String[] commands)
	{		
		if(commands.length == 1)
		{
			String command = commands[0];
			
			 if(command.compareToIgnoreCase(commandRain) == 0)
			   {
				 Rain();
				 return true;
			   }
			   else if(command.compareToIgnoreCase(commandDay) == 0)
			   {
				   Day();
				   return true;
			   }
			   else
			   {
				   return false;		 
			   }
		}
		else return false;		
	}
	
public static void Rain()
{
	SendCommand("toggledownfall");
}

public static void Day()
{
	SendCommand("time set 0");
}
	 
	   
	  
	   
	   public static void SendCommand(String command)
	   {
		   Server server = Bukkit.getServer();
		   
		   server.dispatchCommand(Bukkit.getConsoleSender(),command); 
	   }
}
