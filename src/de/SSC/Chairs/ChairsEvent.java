package de.SSC.Chairs;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ChairsEvent 
{
	private ChairsSystem _chairsSystem;
	
	public ChairsEvent(ChairsSystem chairsSystem)
	{
		_chairsSystem = chairsSystem;
	}
	
    public void OnPlayerInteract(final PlayerInteractEvent event) 
    {
    	final Player player = event.getPlayer();
        final Block block = event.getClickedBlock();
    	final boolean  isAlreadySitting = _chairsSystem.getPlayerSitData().isSitting(player);
    	
        if(isAlreadySitting && player.isSneaking())
        {
        	_chairsSystem.getPlayerSitData().unsitPlayer(player);
        }
    	
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getHand() == EquipmentSlot.HAND) 
        {
            
       
            final Location sitLocation = _chairsSystem.utils.calculateSitLocation(player, block);
            
           
            
        
           
            	  if (sitLocation != null && _chairsSystem.getPlayerSitData().sitPlayer(player, block, sitLocation)) 
                  {
                      event.setCancelled(true);
                  }
            
          
        }
    }
    
    public void OnPlayerJoin(final PlayerJoinEvent event) 
    {
        final Player player = event.getPlayer();
        
        if (Double.isNaN(player.getLocation().getY())) 
        {
            player.teleport(player.getWorld().getSpawnLocation());
        }
    }
}
