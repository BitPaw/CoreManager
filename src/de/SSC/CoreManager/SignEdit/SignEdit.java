package de.SSC.CoreManager.SignEdit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import de.SSC.CoreManager.Config.Config;

public class SignEdit implements Listener
{
	private Config _config;
	
	public SignEdit()
	{
		_config = Config.Instance();
	}
	
	  @EventHandler
	  public void onSignChangeEvent(SignChangeEvent e)
	  {   
	    for (int i = 0; i < 4; i++) 
	    {
	    	String line = e.getLine(i);
	    	String coloredLine = "";
	    	
	    	if(line.contains(_config.Messages.Sign.SignLineTrigger))
	    	{
	    		coloredLine = _config.Messages.Sign.SignLine;
	    	}
	    	else
	    	{
	    		coloredLine = line;
	    	}	
	    	
	    	 e.setLine(i,  ChatColor.translateAlternateColorCodes(_config.Chat.ColorCombineChar , coloredLine));
	    }
	  } 
	  
	  @EventHandler
	  public void onPlayerInteract(PlayerInteractEvent event)
	  {
	    	if (event.getAction() == Action.RIGHT_CLICK_BLOCK) 
	    	{
	    		Player player = event.getPlayer(); 
	  		    Block block = event.getClickedBlock(); 
	    		Material blockMaterial = block.getType();
	    		
	            if (((blockMaterial == Material.SIGN) || (blockMaterial == Material.WALL_SIGN)) && player.isSneaking()) 
	            {
	            	
	            	try
	            	 {
	            		// OpenSignMenu(player, sign);	
	            	 }
	            	 catch(Exception e)
	            	 {
	            		player.sendMessage("Error");
	            	 }            	 
	            	                  
	            }    	
	    }
	  }
	    
}
