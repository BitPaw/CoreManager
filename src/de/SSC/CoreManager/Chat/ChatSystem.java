package de.SSC.CoreManager.Chat;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Player.CMPlayer;
import de.SSC.CoreManager.Player.PlayerSystem;
import de.SSC.CoreManager.Player.Exception.InvalidPlayerUUID;
import de.SSC.CoreManager.Player.Exception.PlayerNotFoundException;
import de.SSC.CoreManager.System.BaseSystem;
import de.SSC.CoreManager.System.ISystem;
import de.SSC.CoreManager.System.SystemPriority;
import de.SSC.CoreManager.System.SystemState;
import de.SSC.CoreManager.World.CMWorld;
import de.SSC.CoreManager.World.WorldSystem;
import java.util.UUID;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatSystem extends BaseSystem implements ISystem
{	
	private static ChatSystem _instance;
	private Logger _logger;
	private Config _config;
	private MessageTags _messageTags;
	private WorldSystem _worldSystem;
	private PlayerSystem _playerSystem;
	
	private ChatSystem()
	{
		super(Module.ChatSystem, SystemState.Active, SystemPriority.High);
		_instance = this; 		
	}
	
	public static ChatSystem Instance() 
	{		
		return _instance == null ? new ChatSystem() : _instance;
	}	

	@Override
	public void LoadReferences() 
	{
		_logger = Logger.Instance();
		_config = Config.Instance();
		_messageTags = MessageTags.Instance();
		_worldSystem = WorldSystem.Instance();
		_playerSystem = PlayerSystem.Instance();		
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
	 
	 public void OnChat(AsyncPlayerChatEvent asyncPlayerChatEvent)
	 {
		 final Player player = asyncPlayerChatEvent.getPlayer();		 
		 final UUID playerUUID = player.getUniqueId();
		 final World world = player.getWorld();		 	 
		 
		 CMPlayer cmPlayer = null;		  	  	    
		 String worldName = world.getName();	    
		 String message = "";	     
	    
		 try 
		 {
			cmPlayer = _playerSystem.GetPlayer(playerUUID);	
		 }
		 catch (PlayerNotFoundException e)
		 {	
			 // Do nothing
		 } 
		 catch (InvalidPlayerUUID e) 
		 {		
			 // Do nothing
		 }	
		 
	    if(cmPlayer != null)
	    {
	        try
		    {        
		 	   final String searchedWorldName = _config.Worlds.RemoveFolderName(worldName);	 	    
		 	   final CMWorld cmWorld = _worldSystem.GetWorld(searchedWorldName);			    	
		    	
		    	if(cmWorld != null)		    	
		    	{		    		
		    		if(cmWorld.BukkitWorld != null)
		    		{
		    			worldName = cmWorld.Information.CustomName;  
		    		}
		    	} 	    	
		    }
		    catch(Exception ex)
		    {		    	
		    	_logger.SendToConsole(Module.ChatSystem, MessageType.Error, _config.Messages.World.WorldIsMissing);	
		    }	
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
	    message = _messageTags.ReplaceMessageTag(message, asyncPlayerChatEvent.getMessage());
	    	    
	    message = _logger.TransformToColor(message);
	    
	    asyncPlayerChatEvent.setFormat(message);
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