package de.BitFire.Configuration;

public class MessageIO implements IConfig
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
	
	public String Yes;
	public String No;
	
	public String NoParameters;
	public String LoadingFiles;
	public String LoadedFiles;
	public String TooManyParameters;
	public String NotEnoughParameters;
	
	public String Arrow;
	
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
		
		   NotForConsole = "&7This command is &cnot &7useable in the &eConsole&7!";	
		   TooManyParameters = "&cToo many &7parameters!";
		   NotEnoughParameters = "&cNot enough &7parameters!";
		   
		   ErrorArrow = " &4>>&c ";
		   
		   LoadingFiles = "Loading files...";
		   LoadedFiles = "Files Loaded.";
		
		   Yes = "&aYes";
		   No = "&cNo";
		   
		   NewLine = "\n";
		   
		   NoParameters = "&cNo parameters, try again.";
		   
		   Arrow = " &8-> ";
	}

}
