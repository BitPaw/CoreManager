package de.SSC.CoreManager.Config.Messages;

import de.SSC.CoreManager.Config.IConfig;

public class MessagesChatManager implements IConfig
{
	public String System;
	public String FirstJoin;
	public String ReJoin;
	public String Join;
	public String Quit;
	public String PermissionChangeName;
	public String NameChanged;
	public String NameChangedFrom;
	public String NameChangesWrongCommand;	
	public String AFK;
	public String UnAFK;
		
	public MessagesChatManager()
	{

	}
	
	public void LoadDefaults()
	{
		System = "&7[&3System&7] ";
           FirstJoin = "&3Welcome new Player to our Server!";
		   ReJoin = "&3Welcome back";
		   Join = "&7[&a+&7]&r&a ";
		   Quit = "&7[&c-&7]&r&c ";
		   PermissionChangeName = "changename.change";
		   NameChanged = "&7You are now disguised as &6<&f{NAME}&6>&7.";
		   NameChangedFrom = "&7Your name got changed from {Source}";
		   NameChangesWrongCommand = "Wrong usage! /changename <name>";	
		   AFK = "%prefix&%player% ist nun abwesend";
		   UnAFK = "%prefix&%player% ist wieder da";	
	}	
}