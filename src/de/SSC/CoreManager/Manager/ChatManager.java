package de.SSC.CoreManager.Manager;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DataTypes.CMPlayer;
import de.SSC.CoreManager.DataBase.DataTypes.CMPlayerList;
import de.SSC.CoreManager.DataBase.DataTypes.CMWorld;
import de.SSC.CoreManager.DataBase.DataTypes.CMWorldList;
import de.SSC.CoreManager.Messages.Logger;
import de.SSC.CoreManager.Messages.MessageType;
import de.SSC.CoreManager.Messages.Module;

import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatManager
{	
	private static ChatManager _instance;
	private Logger _logger;
	private Config _config;
	
	private ChatManager()
	{
		_logger = Logger.Instance();
		_config = Config.Instance();
		
		_logger.SendToConsole(Module.ChatSystem, MessageType.Online, _config.Messages.ConsoleIO.On);
	}
	
	 public void OnJoin(PlayerJoinEvent e)
	 {
		 Player player = e.getPlayer();
		String displayName = player.getDisplayName();
		String message;
		
		if(displayName == null)
		{
			player.setDisplayName(player.getName());
			displayName = player.getName();
		}		
		
		message = _logger.TransformToColor(_config.Messages.Chat.Join + displayName);				
		
		e.setJoinMessage(message);
	 }

	 public void OnLeave(PlayerQuitEvent e)
	 {
			String playerName = e.getPlayer().getDisplayName();
			String message = _logger.TransformToColor(_config.Messages.Chat.Quit + playerName);				
			
			e.setQuitMessage(message);
			
			_logger.SendToConsole(Module.ChatSystem, MessageType.Info, message);
	 }	
	 
	  public void OnChat(AsyncPlayerChatEvent e)
	  {
	    Player player = e.getPlayer();	
	    
	    CMPlayerList  cmPlayerList = CMPlayerList.Instance();
	    CMPlayer cmPlayer = cmPlayerList.GetPlayer(player);
	    
	    CMWorldList cmWorldList = CMWorldList.Instance();
	    String searchedWorldName = player.getWorld().getName();
	    
	    CMWorld cmWorld = cmWorldList.GetWorldPerName(searchedWorldName);	   
	    
	    String worldName;	    
	    String message = "";	  
	    String[] splittProduct;
	    
	    try
	    {   	
	    	if(cmWorld == null)
	    	{
	    		worldName = searchedWorldName;
	    	}
	    	else
	    	{
	    		if(cmWorld.BukkitWorld == null)
	    		{
	    			worldName = "Unkown";
	    		}
	    		else
	    		{
	    			worldName = cmWorld.CustomName;	
	    			
	    			splittProduct =  worldName.split("/");
	    			
	    			if(splittProduct.length > 1)
	    			{
	    				worldName = splittProduct[1];
	    			}	    			
	    		} 
	    	} 	    	
	    }
	    catch(Exception ex)
	    {
	    	worldName = player.getWorld().getName();
	    	
	    	_logger.SendToConsole(Module.ChatSystem, MessageType.Error, _config.Messages.World.WorldIsMissing);	
	    }	 	    
	    
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

	public static ChatManager Instance() 
	{
		if(_instance == null)
		{
			_instance = new ChatManager();
		}
		
		return _instance;
	}
}