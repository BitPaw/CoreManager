package de.SSC.CoreManager.Utility;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;

import de.SSC.CoreManager.Systems.Chat.Logger;
import de.SSC.CoreManager.Systems.Chat.MessageType;
import de.SSC.CoreManager.Systems.Chat.Module;

public class BukkitUtilityWorld 
{
	private Server _server; 
	private Logger _logger;
	
	public BukkitUtilityWorld(Server server, Logger logger)
	{
		_server = server;
		_logger = logger;
	}
	
	public List<World> GetLoadedWorlds()
	{
		if(_server == null)
		{
			_server = Bukkit.getServer();
		}
		
		if(_logger == null)
		{
			_logger = Logger.Instance();
		}
		
		List<World> worlds = _server.getWorlds();
		int numberOfWorlds = worlds.size();
		
		_logger.SendToConsole(Module.System, MessageType.Info, "&eFound &7<" + numberOfWorlds + "&7> &aloaded &7worlds.");
		
		return worlds;
	}

	public boolean UnloadWorld(World bukkitWorld)
	{
		boolean success = false;
		
		try
		{
			_server.unloadWorld(bukkitWorld, true);	
			success = true;
		
		}
		catch(Exception exception)
		{
			_logger.SendToConsole(Module.System, MessageType.Error, "Could not unload world <" + bukkitWorld.getName() + ">.");;
		}	
		
		return success;		
	}
}
