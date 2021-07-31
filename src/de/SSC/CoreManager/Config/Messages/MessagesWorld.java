package de.SSC.CoreManager.Config.Messages;

import de.SSC.CoreManager.Config.IConfig;

public class MessagesWorld implements IConfig
{
	public String ErrorWhileCreatingWorld;	
	public String NoLoadedWorlds;
	public String UnkownWorld;
	
	public String WorldTag;
	public String CustomWorldTag;	
	
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
		
		WorldTag = "{World}";
		CustomWorldTag = "{CWorldTag}";
		
		ErrorWhileCreatingWorld =  "&cError while creating world <&l&e{World}&c>.";
		
		WorldListHeader = "&5===========[&dWorlds&5]===========";
		WorldListFooter = "&5=============================";		
		 
		NoLoadedWorlds = "&cNo loaded Worlds?";
		UnkownWorld = "&8Name : UnkownWorld";
		
		WorldNormal = "&aNormal";		 
		WorldNether = "&cNether";		 
		WorldEnd = "&aEnd";		  
		 
		LoadedWorld = "&f {World}&5 as &r{CWorldTag} &7[&aLoaded&7]";
		UnloadedWorld = "&8 {World}&5 as &r{CWorldTag} &7[&cUnloaded&7]";	
		
		FailedToLoadAllWorlds = "Failed to load all worlds";
		WorldIsMissing = "&cWorld is missing!";
	}
}