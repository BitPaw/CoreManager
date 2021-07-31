package de.BitFire.API.Bukkit;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;

import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;

public final class BukkitAPIWorld 
{	
	private BukkitAPIWorld()
	{
		
	}
	
	public final static List<World> GetLoadedWorlds()
	{
		final Server server = Bukkit.getServer();
		final Logger logger = Logger.Instance();		
		final List<World> worlds = server.getWorlds();
		final int numberOfWorlds = worlds.size();
		
		logger.SendToConsole(Module.System, MessageType.Info, "&eFound &7<" + numberOfWorlds + "&7> &aloaded &7worlds.");
		
		return worlds;
	}

	public final static boolean UnloadWorld(World bukkitWorld)
	{
		final Server server = Bukkit.getServer();
		final Logger logger = Logger.Instance();	
		boolean success = false;
		
		try
		{
			server.unloadWorld(bukkitWorld, true);	
			success = true;
		
		}
		catch(Exception exception)
		{
			logger.SendToConsole(Module.System, MessageType.Error, "Could not unload world <" + bukkitWorld.getName() + ">.");;
		}	
		
		return success;		
	}
}