package de.SSC.CoreManager.Config.Messages;

import de.SSC.CoreManager.Config.IConfig;

public class MessagesChatManager implements IConfig
{
	public String FirstJoin;
	public String ReJoin;
	public String Join;
	public String Quit;
	public String PermissionChangeName;
	public String NameChanged;
	public String NameChangesWrongCommand;	
	public String AFK;
	public String UnAFK;
		
	public MessagesChatManager()
	{
		LoadDefaults();
	}
	
	public void LoadDefaults()
	{
      FirstJoin = "&7[&3System&7] &3Welcome new Player to our Server!";
		   ReJoin = "&7[&3System&7] &3Welcome back";
		   Join = "&7[&a+&7]&r&a ";
		   Quit = "&7[&c-&7]&r&c ";
		   PermissionChangeName = "changename.change";
		   NameChanged = "You are now disguised as ";
		   NameChangesWrongCommand = "Wrong usage! /changename <name>";	
		   AFK = "%prefix&%player% ist nun abwesend";
		   UnAFK = "%prefix&%player% ist wieder da";		
	}	
}