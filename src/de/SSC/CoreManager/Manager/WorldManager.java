package de.SSC.CoreManager.Manager;

import org.bukkit.command.CommandSender;

public class WorldManager 
{
	
	
	
private static WorldManager _instance;

public static WorldManager Instance()
{
	if(_instance == null)
	{
		_instance = new WorldManager();
	}
	
	return _instance;
}

public void CreateNewWorldCommand(CommandSender sender, String[] args)
{
	// TODO Auto-generated method stub
	
}

public void DeleteWorldCommand(CommandSender sender, String[] args)
{
	// TODO Auto-generated method stub
	
}
}
