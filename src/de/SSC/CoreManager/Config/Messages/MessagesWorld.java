package de.SSC.CoreManager.Config.Messages;

import de.SSC.CoreManager.Config.IConfig;

public class MessagesWorld implements IConfig
{
	public String ErrorWhileCreatingWorld;	
	public String NoLoadedWorlds;
	public String UnkownWorld;
	
	public String WorldListHeader;
	public String WorldListFooter;
	
	public String LoadedWorld;
	public String UnloadedWorld;
	
	public String WorldNormal;
	public String WorldNether;
	public String WorldEnd;
	
	public String FailedToLoadAllWorlds;
	public String WorldIsMissing;
	public String WorldSystemTag;
	
	public void LoadDefaults() 
	{
		WorldSystemTag = "&7[&aWorld&7]&r";		
	
		
		ErrorWhileCreatingWorld =  "&cError while creating world <&l&e{WORLD}&c>.";
		
		WorldListHeader = "&5===========[&dWorlds&5]===========";
		WorldListFooter = "&5=============================";		
		 
		NoLoadedWorlds = "&cNo loaded Worlds?";
		UnkownWorld = "&8Name : UnkownWorld";
		
		WorldNormal = "&aNormal";		 
		WorldNether = "&cNether";		 
		WorldEnd = "&aEnd";		  
		 
		LoadedWorld = "&7 {NAME}&5 as &r{WORLD} &7[&aLoaded&7]";
		UnloadedWorld = "&8 {NAME}&5 as &r{WORLD} &7[&cUnloaded&7]";	
		
		FailedToLoadAllWorlds = "Failed to load all worlds";
		WorldIsMissing = "&cWorld is missing!";
	}
}