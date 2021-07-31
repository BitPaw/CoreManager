package de.SSC.CoreManager.Chat;

import de.SSC.CoreManager.Config.IConfig;

public class MessagesConsoleIO implements IConfig
{
	public String CoreManager;
	public String On;
	public String Off;	
	public String Shutdown;	
	public String Error;
	public String Question;
	public String Command;
	public String Info;	
	public String NotForConsole;
	public String Warning;
	public String Online;
	public String Offline;
	public String Loading;
	
	public String NewLine;
	
	public String ErrorArrow;
	
	public String NoParameters;
	public String LoadingFiles;
	public String LoadedFiles;
	public String TooManyParameters;
	public String NotEnoughParameters;
	
	public void LoadDefaults() 
	{
		  
		   CoreManager = "&7[&bCoreManager&7]";
		   On = "&7[&aON&7]";
		   Off = "&7[&cOFF&7]";	

		   Shutdown = "&cThis plugin will be disabled do to an critical error!";
		
		   Error = "&7[&c!&7]&c ";
		   Question = "&7[&b?&7]&b ";
		   Command = "&7[&5#&7]&d ";
		   Info = "&7[&ei&7]&e ";
		   Warning = "&7[&6!&7]&c ";			 
		   Online = "&7[&2^&7]&a ";			
		   Offline = "&7[&4X&7]&c ";			
		   Loading = "&7[&6>&7]&6 ";
		
		   NotForConsole = "&cThis command is not for the console!";	
		   TooManyParameters = "&cToo many &7parameters!";
		   NotEnoughParameters = "&cNot enough &7parameters!";
		   
		   ErrorArrow = " &4>>&c ";
		   
		   LoadingFiles = "Loading files...";
		   LoadedFiles = "Files Loaded.";
		
		   NewLine = "\n";
		   
		   NoParameters = "&cNo parameters, try again.";
	}

}
