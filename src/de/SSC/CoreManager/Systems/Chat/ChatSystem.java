package de.SSC.CoreManager.Systems.Chat;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Systems.Player.CMPlayer;
import de.SSC.CoreManager.Systems.Player.PlayerSystem;
import de.SSC.CoreManager.Systems.World.CMWorld;
import de.SSC.CoreManager.Systems.World.WorldSystem;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatSystem
{	
	private static ChatSystem _instance;
	private Logger _logger;
	private Config _config;
	private MessageTags _messageTags;
	private WorldSystem _worldSystem;
	private PlayerSystem _playerSystem;
	
	private ChatSystem()
	{
		_instance = this; 
		
		_logger = Logger.Instance();
		_config = Config.Instance();
		_messageTags = MessageTags.Instance();
		_worldSystem = WorldSystem.Instance();
		_playerSystem = PlayerSystem.Instance();
		
		_logger.SendToConsole(Module.ChatSystem, MessageType.Online, _config.Messages.ConsoleIO.On);
	}
	
	public static ChatSystem Instance() 
	{		
		return _instance == null ? new ChatSystem() : _instance;
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
	    CMPlayer cmPlayer;	    
	    String searchedWorldName;	    
	    CMWorld cmWorld;	  	    
	    String worldName;	    
	    String message = "";	     
	    
	    cmPlayer = _playerSystem.GetPlayer(player);	    
	    searchedWorldName = player.getWorld().getName();
	    
	    searchedWorldName = _config.Worlds.RemoveFolderName(searchedWorldName);
	    
	    cmWorld = _worldSystem.GetWorldPerName(searchedWorldName);	  	   
	    
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
	    /*
	    if(_config.Chat.ShowPrefx) 
	    {
	    	message += _config.Chat.PrefixSyntax;	  
	    }	    
	*/
	    message += _config.Chat.PlayerDisplaySyntax;			    
	    
	    /*
	    if(_config.Chat.ShowSuffix) 
	    {
	    	message += _config.Chat.SuffixSyntax;
	    }	    
	    */
	    message += _config.Chat.MessageSyntax;
	    
	   
	    message = _messageTags.ReplaceWorldTag(message, worldName);
	    message = _messageTags.ReplaceRankTag(message, cmPlayer.RankGroup);
	    message = _messageTags.ReplaceOPTag(message, player.isOp());
	    message = _messageTags.ReplacePlayerTag(message, cmPlayer);
	    message = _messageTags.ReplaceMessageTag(message, e.getMessage());
	    	    
	    message = _logger.TransformToColor(message);
	    
	    e.setFormat(message);
	  }
	  
	  public void ClearChat(CommandSender sender)
	  {
		  int lines = 20;		  
		  
		  for(int i = 0 ; i <= lines; i++)
		  {
			  sender.sendMessage("");
		  }
	  }
}