///////////////////////////////////////////////////////////////////////////////////////////////////
//-----------------------------------------------------------------------------------------------//
package de.BitFire;
import de.BitFire.API.CommandCredentials;
import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Configuration.Config;
import de.BitFire.Core.CoreListener;
import de.BitFire.File.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
//-----------------------------------------------------------------------------------------------//
public class Main extends JavaPlugin implements Listener
{
    private static JavaPlugin _pluginInstance;
	private PluginManager _pluginManager;
    
	private CoreListener _coreListener;
	private Logger _logger;
	private Config _config;
	private FileManager _FileManager;
	
    public Main()
    {
    	_FileManager = FileManager.Instance();
    	_FileManager.CreateDataFolder();
    	
    	_logger = Logger.Instance();   
    	_logger.LoadReferences();
    	
    	_config  = Config.Instance();   
    	_config.LoadReferences();
    	
    	_coreListener = CoreListener.Instance();
    	
    	_logger.SendToConsole(Module.CoreManager, MessageType.Loading, _config.Message.IO.LoadingFiles);    	
    	
    	_config.Load();  
    	
    	_logger.SendToConsole(Module.CoreManager, MessageType.Info,_config.Message.IO.LoadedFiles);    	
    	
    }
        
    public static JavaPlugin GetPluginInstance()
    {
    	return _pluginInstance;
    }

    @Override
    public void onEnable()
    {
    	_pluginInstance = this;
      	_pluginManager = Bukkit.getPluginManager();    
    	
    	_logger.SendToConsole(Module.CoreManager, MessageType.Online,_config.Message.IO.On);  	    	
      
    	_pluginManager.registerEvents(this, this);	
    	
    	_coreListener.Start();   
    	_coreListener.RegisterAllEvents(this, _pluginManager);    	
    	
    	_coreListener.ListEverything();
    	
    	//CoreManagerUtility coreManagerUtility = new CoreManagerUtility();
    }

    @Override
    public void onDisable()
    {    	
    	_config.Save();
    	
    	_logger.SendToConsole(Module.CoreManager, MessageType.Offline,_config.Message.IO.Off);
    	
    	_pluginInstance = null;
    }  
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args)
    {    	
    	final CommandCredentials commandCredentials = new CommandCredentials(sender, cmd, commandLabel, args);
    	
        return _coreListener.onCommand(commandCredentials);
    }
    
	public void ReloadPlugin(JavaPlugin plugin) 
	{	
		_logger.SendToConsole(Module.System, MessageType.Info, "&cDisabling &7Plugin! &3"+ plugin.getName());
	 	
	 	_pluginManager.disablePlugin(plugin);
	 	
	 	_logger.SendToConsole(Module.System, MessageType.Info, "&aRe&2-&aEnabling &7Plugin! &3" + plugin.getName());
	 	
	 	_pluginManager.enablePlugin(plugin);	
	}
    
	@EventHandler(priority = EventPriority.LOWEST) 
	public void onPing(ServerListPingEvent event) 
	{
		_coreListener.onPing(event);
	}    
    
    @EventHandler(priority = EventPriority.LOWEST) 
	public void onPlayerJoin(PlayerJoinEvent playerJoinEvent)
	{
    	_coreListener.onPlayerJoin(playerJoinEvent);
	}    
    
    @EventHandler(priority = EventPriority.HIGHEST) 
	public void onLeave(PlayerQuitEvent e)
	{
		_coreListener.onLeave(e);
	}    
    
    @EventHandler(priority = EventPriority.LOWEST) 
	public void onChat(AsyncPlayerChatEvent e)
	{
    	_coreListener.onChat(e);
	}	
	
	@EventHandler(priority = EventPriority.LOWEST) 
	public void onPlayerRespawn(PlayerRespawnEvent playerRespawnEvent)
	{
		_coreListener.onPlayerRespawn(playerRespawnEvent);
	} 
    		
	@EventHandler(priority = EventPriority.LOWEST) 
	public void OnSignClick(PlayerInteractEvent event)
	{
		//_coreListener.OnSignClick(event);
	}   
	
	@EventHandler(priority = EventPriority.LOWEST) 
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		_coreListener.onPlayerInteract(event);
	}		
		
	@EventHandler(priority = EventPriority.LOWEST) 
	public void OnPlayerMove(PlayerMoveEvent e)
	{
		_coreListener.OnPlayerMove(e);
	}
	
	@EventHandler(priority = EventPriority.LOWEST) 
	public void OnFly(PlayerToggleFlightEvent e)
	{
		_coreListener.OnFly(e);
	}
	
	@EventHandler(priority = EventPriority.LOWEST) 
	public void OnSneak(PlayerToggleSneakEvent e)
	{
		_coreListener.OnSneak(e);
	}
	
	@EventHandler(priority = EventPriority.LOWEST) 
	public void onSignPlace(SignChangeEvent event)
	{
		_coreListener.onSignPlace(event);
	}

	@EventHandler(priority = EventPriority.LOWEST) 
	public void onInteract(PlayerInteractEvent playerInteractEvent)
	{
		_coreListener.onInteract(playerInteractEvent);
	}        
    
	@EventHandler(priority = EventPriority.LOWEST) 
	public void onBlockPlace(BlockPlaceEvent event)
	{			
		_coreListener.onBlockPlace(event);
	}
		
	@EventHandler(priority = EventPriority.LOWEST) 
	public void onBlockBreak(final BlockBreakEvent event)
	{		
		_coreListener.onBlockBreak(event);
	}
	
	@EventHandler(priority = EventPriority.LOWEST) 
	public void onDamage(EntityDamageByEntityEvent event)	   
	{		
		_coreListener.onDamage(event);
	}
	
	@EventHandler(priority = EventPriority.LOWEST) 
	public void onEntityExplode(EntityExplodeEvent event)
	{
		_coreListener.onEntityExplode(event);
	}

	@EventHandler(priority = EventPriority.LOWEST) 
	public void WitherProjectile(EntityExplodeEvent event)
	{    
		_coreListener.WitherProjectile(event);
	}	  
	  
	@EventHandler(priority = EventPriority.LOWEST) 
	public void WitherEatBlocks(EntityChangeBlockEvent event)
	{
		_coreListener.WitherEatBlocks(event);
	}    
	
	@EventHandler(priority = EventPriority.LOWEST) 
	public void onPlayerMove(final PlayerMoveEvent event)
	{		
		_coreListener.OnPlayerMove(event);
	}
	
    /* ERROR!! This is falulty
 	@EventHandler(priority = EventPriority.LOWEST) 
    public void onPlayerDoorOpen(PlayerInteractEvent event)
    {
		_coreListener.onPlayerDoorOpen(event);		
    } 
	*/	
}
//-----------------------------------------------------------------------------------------------//
///////////////////////////////////////////////////////////////////////////////////////////////////