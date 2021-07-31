package de.BitFire.World;

import de.BitFire.Configuration.IConfig;

public class MessagesWorld implements IConfig
{
	public String ErrorWhileCreatingWorld;	
	public String NoLoadedWorlds;
	public String UnkownWorld;
	
	public String WorldListHeader;
	public String WorldListFooter;
	
	public String LoadedWorld;
	public String UnloadedWorld;
	public String FailedToLoadWorld;
	public String UnRegisteredWorld;
	public String UndefinedWorld;
	
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
		 
		LoadedWorld = "&7{ID} {NAME}&5 as &r{WORLD} &7[{MAPTYPE}&7][{MAPENVIROMENT}&7][&aLoaded&7]";
		UnloadedWorld = "&8{ID} {NAME}&5 as &r{WORLD} &7[{MAPTYPE}&7][{MAPENVIROMENT}&7][&cUnloaded&7]";	
		FailedToLoadWorld = "&c{ID} {NAME}&5 as &r{WORLD} &7[{MAPTYPE}&7][{MAPENVIROMENT}&7][&4Error&7]";
		UnRegisteredWorld = "&e{ID} {NAME}&5 as &r{WORLD} &7[{MAPTYPE}&7][{MAPENVIROMENT}&7][&eUnRegistered&7]";
		UndefinedWorld = "&8{ID} {NAME}&5 as &r{WORLD} &7[{MAPTYPE}&7][{MAPENVIROMENT}&7][&8Undefined&7]";
		
		FailedToLoadAllWorlds = "Failed to load all worlds";
		WorldIsMissing = "&cWorld is missing!";
	}
}