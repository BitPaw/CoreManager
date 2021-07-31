///////////////////////////////////////////////////////////////////////////////////////////////////
//-----------------------------------------------------------------------------------------------//
package de.BitFire.CoreManager;

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
import org.bukkit.plugin.java.JavaPlugin;

import de.BitFire.CoreManager.System.CommandData;
import de.BitFire.CoreManager.System.CoreManagerSystem;
//-----------------------------------------------------------------------------------------------//
public final class Main extends JavaPlugin implements Listener, Runnable
{
	private final CoreManagerSystem _coreManagerSystem;
	
    public Main()
    { 	
    	_coreManagerSystem = CoreManagerSystem.Instance();
    }

    @Override
    public final void onEnable()
    {
    	_coreManagerSystem.Start();
    	
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this, 1, 20);
    }

    @Override
    public final void onDisable()
    {    	
    	_coreManagerSystem.Stop();
    }  
    
    @Override
    public final boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args)
    {   
    	return _coreManagerSystem.OnCommand(new CommandData(sender, cmd, commandLabel, args));
    }
    
	@Override
	public void run() 
	{
		_coreManagerSystem.Update();		
	}  
    
	@EventHandler(priority = EventPriority.LOWEST) 
	public final void OnPing(final ServerListPingEvent serverListPingEvent) 
	{		
		_coreManagerSystem.OnPlayerPingingServer(serverListPingEvent);
	}    
    
    @EventHandler(priority = EventPriority.LOWEST) 
	public final void OnPlayerJoin(final PlayerJoinEvent playerJoinEvent)
	{
    	_coreManagerSystem.OnPlayerJoin(playerJoinEvent);
	}    
    
    @EventHandler(priority = EventPriority.HIGHEST) 
	public final void OnPlayerLeave(final PlayerQuitEvent playerQuitEvent)
	{
    	_coreManagerSystem.OnPlayerLeave(playerQuitEvent);
	}    
    
    @EventHandler(priority = EventPriority.LOWEST) 
	public final void OnChatMessage(final AsyncPlayerChatEvent asyncPlayerChatEvent)
	{
    	_coreManagerSystem.OnChatMessage(asyncPlayerChatEvent);
	}	
	
	@EventHandler(priority = EventPriority.LOWEST) 
	public final void OnPlayerRespawn(final PlayerRespawnEvent playerRespawnEvent)
	{
		_coreManagerSystem.OnPlayerRespawn(playerRespawnEvent);
	} 
    		
	@EventHandler(priority = EventPriority.LOWEST) 
	public final void OnInteract(final PlayerInteractEvent playerInteractEvent)
	{
		_coreManagerSystem.OnPlayerInteract(playerInteractEvent);
	}		
		
	@EventHandler(priority = EventPriority.LOWEST) 
	public final void OnPlayerMove(final PlayerMoveEvent playerMoveEvent)
	{
		_coreManagerSystem.OnPlayerMove(playerMoveEvent);
	}
	
	@EventHandler(priority = EventPriority.LOWEST) 
	public final void OnPlayerFly(final PlayerToggleFlightEvent playerToggleFlightEvent)
	{
		_coreManagerSystem.OnPlayerFly(playerToggleFlightEvent);
	}
	
	@EventHandler(priority = EventPriority.LOWEST) 
	public final void OnPlayerSneak(final PlayerToggleSneakEvent playerToggleSneakEvent)
	{
		_coreManagerSystem.OnPlayerSneak(playerToggleSneakEvent);
	}
	
	@EventHandler(priority = EventPriority.LOWEST) 
	public final void OnSignChange(final SignChangeEvent signChangeEvent)
	{
		_coreManagerSystem.OnSignChange(signChangeEvent);
	}      
    
	@EventHandler(priority = EventPriority.LOWEST) 
	public final void OnBlockPlace(final BlockPlaceEvent blockPlaceEvent)
	{			
		_coreManagerSystem.OnBlockPlace(blockPlaceEvent);
	}
		
	@EventHandler(priority = EventPriority.LOWEST) 
	public final void OnBlockBreak(final BlockBreakEvent blockBreakEvent)
	{		
		_coreManagerSystem.OnBlockBreak(blockBreakEvent);
	}
	
	@EventHandler(priority = EventPriority.LOWEST) 
	public final void OnEntityDamageEntity(final EntityDamageByEntityEvent entityDamageByEntityEvent)	   
	{		
		_coreManagerSystem.OnEntityDamageEntity(entityDamageByEntityEvent);
	}
	
	@EventHandler(priority = EventPriority.LOWEST) 
	public final void OnEntityExplode(final EntityExplodeEvent entityExplodeEvent)
	{
		_coreManagerSystem.OnEntityExplode(entityExplodeEvent);
	} 
	  
	@EventHandler(priority = EventPriority.LOWEST) 
	public final void OnEntityChangeBlock(final EntityChangeBlockEvent entityChangeBlockEvent)
	{
		_coreManagerSystem.OnEntityChangeBlock(entityChangeBlockEvent);
	}  
}
//-----------------------------------------------------------------------------------------------//
///////////////////////////////////////////////////////////////////////////////////////////////////