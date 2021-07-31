package de.SSC.CoreManager.Config.Messages;

import de.SSC.CoreManager.Config.IConfig;

public class MessagesConsoleIO implements IConfig
{
	public String DataBase;
	public String CoreManager;
	public String On;
	public String Off;	
	public String Shutdown;	
	public String Error;
	public String Question;
	public String Command;
	public String Info;	
	public String NotForConsole;
	
	public void LoadDefaults() 
	{
		  DataBase = "&7[&eDataBase&7]";
		   CoreManager = "&7[&bCoreManager&7]";
		   On = "&7[&aON&7]";
		   Off = "&7[&cOFF&7]";	

		   Shutdown = "This plugin will be disabled do to an critical error!";
		
		   Error = "&7[&c!&7]&c ";
		   Question = "&7[&b?&7]&b ";
		   Command = "&7[&a#&7]&a ";
		   Info = "&7[&ei&7]&e ";
		
		   NotForConsole = "This command is not for the console!";	
		
	}

}
