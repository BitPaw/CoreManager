package de.SSC.CoreManager.Essentials.Chat;

import de.SSC.CoreManager.Logger;
import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DataTypes.CMPlayer;
import de.SSC.CoreManager.DataBase.DataTypes.CMPlayerList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatManager implements Listener
{	
	private Logger _logger;
	private Config _config;
	
	public ChatManager()
	{
		_logger = Logger.Instance();
		_config = Config.Instance();
	}
	
	@EventHandler
	 public void onJoin(PlayerJoinEvent e)
	 {
		String playerName = e.getPlayer().getDisplayName();
		String message = _logger.TransformToColor(_config.Messages.Chat.Join + playerName);				
		
		e.setJoinMessage(message);
	 }

	 @EventHandler
	 public void onLeave(PlayerQuitEvent e)
	 {
			String playerName = e.getPlayer().getDisplayName();
			String message = _logger.TransformToColor(_config.Messages.Chat.Quit + playerName);				
			
			e.setQuitMessage(message);
			_logger.Write(message);
	 }	
	 
	  @EventHandler
	  public void onChat(AsyncPlayerChatEvent e)
	  {
	    Player player = e.getPlayer();	
	    CMPlayerList  cmPlayerList = CMPlayerList.Instance();
	    CMPlayer cmPlayer = cmPlayerList.GetPlayer(player);
	    String worldName =player.getWorld().getName();	    
	    String message = "";
	    
	    worldName = worldName.split("/")[1];
	    
	    if(_config.Chat.ShowWorld)
	    {
	    	message += _config.Chat.WorldSyntax;
	    }
	    
	    if(_config.Chat.ShowPrefx) 
	    {
	    	message += _config.Chat.PrefixSyntax;	  
	    }	    
	
	    message += _config.Chat.PlayerNameSyntax;			    
	    
	    if(_config.Chat.ShowSuffix) 
	    {
	    	message += _config.Chat.SuffixSyntax;
	    }	    
	    
	    message += _config.Chat.MessageSyntax;
	    
	    
	    message = _config.Chat.SetWorldTag(worldName, message);
	    message = _config.Chat.SetRankTag(cmPlayer.RankGroup, message);
	    message = _config.Chat.SetOPTag(player.isOp(), message);
	    message = _config.Chat.SetNameTag(cmPlayer, message);
	    message = _config.Chat.SetMessageTag(e.getMessage(), message);	    
	    
	    message = _logger.TransformToColor(message);
	    
	    e.setFormat(message);
	  }
}