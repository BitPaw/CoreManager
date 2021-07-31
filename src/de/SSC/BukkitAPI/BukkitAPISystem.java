package de.SSC.BukkitAPI;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.SSC.CoreManager.Chat.Logger;
import de.SSC.CoreManager.Chat.MessageType;
import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.System.BaseSystem;
import de.SSC.CoreManager.System.ISystem;
import de.SSC.CoreManager.System.SystemPriority;
import de.SSC.CoreManager.System.SystemState;

public class BukkitAPISystem extends BaseSystem implements ISystem
{
	private static BukkitAPISystem _instance;
	
	private Server _server;
	private PluginManager _pluginManager;
	private Logger _logger;	 
	 
	public BukkitAPIPlayer Player;
	public BukkitAPIBlock Block;
	public BukkitAPIItem Item;
	public BukkitAPIWorld World;
	public BukkitAPIServer Server;
	public BukkitAPIWeather Weather;
	 
	private BukkitAPISystem()
	{
		super(Module.BukkitAPI, SystemState.Active, SystemPriority.Essential);
		_instance = this;
			 		 
		Player = new BukkitAPIPlayer();
		Block = new BukkitAPIBlock();
		Item = new BukkitAPIItem();
		World = new BukkitAPIWorld();
		Server = new BukkitAPIServer();
		Weather = new BukkitAPIWeather();	 
	}
	 
	public static BukkitAPISystem Instance()
	{
		return _instance == null ? new BukkitAPISystem() : _instance;
	} 		

	@Override
	public void LoadReferences() 
	{
		_logger = Logger.Instance();
		
		_server = Bukkit.getServer();
		_pluginManager = _server.getPluginManager(); 
	}

	@Override
	public void Reload(final boolean firstRun) 
	{
		
	}
		
	@Override
	public void PrintData(CommandSender sender) 
	{
			
	}
	 
	public void ReloadPlugin(JavaPlugin plugin) 
	{	
		_logger.SendToConsole(Module.System, MessageType.Info, "&cDisabling &7Plugin! &3"+ plugin.getName());
	 	
	 	_pluginManager.disablePlugin(plugin);
	 	
	 	_logger.SendToConsole(Module.System, MessageType.Info, "&aRe&2-&aEnabling &7Plugin! &3" + plugin.getName());
	 	
	 	_pluginManager.enablePlugin(plugin);	
	}
}