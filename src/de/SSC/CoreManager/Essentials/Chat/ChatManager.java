package de.SSC.CoreManager.Essentials.Chat;

import de.SSC.CoreManager.Messages;
import de.SSC.CoreManager.Color.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatManager implements Listener
{		
	boolean ShowWorld = true;
	boolean ShowPrefx = true;
	boolean ShowSuffix = false;
	
	String _world = "&7[&e{WORLD}&7]";
	String _prefix = "&7[&6{PREFIX}&7]";
	String _playerName= "&7[&e{NAME}&7]";
	String _suffix = "&7[&6{SUFFIX}&7]";
	String _message = " &r&f{MESSAGE}";	
		
	
	@EventHandler
	 public void onJoin(PlayerJoinEvent e)
	 {
		String playerName = e.getPlayer().getDisplayName();
		String message = Logger.TransformToColor(Messages.Join + playerName);				
		
		e.setJoinMessage(message);
	 }

	 @EventHandler
	 public void onLeave(PlayerQuitEvent e)
	 {
			String playerName = e.getPlayer().getDisplayName();
			String message = Logger.TransformToColor(Messages.Quit + playerName);				
			
			e.setQuitMessage(message);
			Logger.Write(message);
	 }	
	 
	  @EventHandler
	  public void onChat(AsyncPlayerChatEvent e)
	  {
	    Player p = e.getPlayer();		    
	    
	    String format = "";
	    
	    if(ShowWorld) format += _world;
	    if(ShowPrefx) format += _prefix;
	    format += _playerName;
	    if(ShowSuffix) format += _suffix;
	    format += _message;
	   
	    format = format.replace("{NAME}", e.getPlayer().getDisplayName());
	    format = format.replace("{WORLD}", p.getWorld().getName());
	    
	    format = format.replace("{PREFIX}", "&aI");
	    format = format.replace("{SUFFIX}", "");	    
	    
	    format = ChatColor.translateAlternateColorCodes('&', format);
	    format = format.replace("{MESSAGE}", e.getMessage());
	    e.setFormat(format);
	  }
	  
	  public String replaceVault(Player p, String message)
	  {
	    String holders = message;
	    
	    String rank = null;
	    String prefix = null;
	    String suffix = null;
	    
	  //prefix = this.plugin.chat.getPlayerPrefix(p);
	  //  suffix = this.plugin.chat.getPlayerSuffix(p);
	   // rank = this.plugin.perms.getPrimaryGroup(p);
	    
	    holders = holders.replace("{PREFIX}", prefix);
	    holders = holders.replace("{SUFFIX}", suffix);
	    holders = holders.replace("{RANK}", rank);
	    
	    return holders;
	  }
}