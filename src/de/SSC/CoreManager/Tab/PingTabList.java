package de.SSC.CoreManager.Tab;

import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DataTypes.CMPlayer;
import de.SSC.CoreManager.DataBase.DataTypes.CMPlayerList;
import de.SSC.CoreManager.Messages.Logger;
import de.SSC.CoreManager.Messages.MessageType;
import de.SSC.CoreManager.Messages.Module;
import de.SSC.CoreManager.Utility.BukkitUtility;
import de.SSC.CoreManager.Utility.MessageTags;

public class PingTabList extends BukkitRunnable
{
	private static PingTabList _instance;
	private Config _config;	
	private Logger _logger;
	private MessageTags _messageTags;
	private CMPlayerList _cmPlayerList;	
	private BukkitUtility _bukkitUtility;	
	
	  private  PingTabList()
	  {	    
	    _config = Config.Instance();
	    _logger = Logger.Instance();
	    _messageTags = MessageTags.Instance();
	    _cmPlayerList = CMPlayerList.Instance();	
	    _bukkitUtility =  BukkitUtility.Instance();
	    
		_logger.SendToConsole(Module.PingTab, MessageType.Online, _config.Messages.ConsoleIO.On);
	  }	  
	
	  public static PingTabList Instance()
	  {
		  if(_instance == null)
		  {
			  _instance = new PingTabList();
			  
		  }
		  
		  return _instance;
	  }	  
	  
	  public void run()
	  {
		List<CMPlayer> cmPlayers = _cmPlayerList.GetList();  
		int ping;
		int currentPlayers= cmPlayers.size();
		String pingTag;	  	 
        String pingTabSytax;
		
		if(currentPlayers <= 0)
		{
			return;
		}

		try
		{	
		    for (CMPlayer cmPlayer : cmPlayers)
		    {
		    	ping = _bukkitUtility.GetPlayerPing(cmPlayer.BukkitPlayer);
		    	pingTag = SetColorTag(ping);	
		    	
	            pingTabSytax = _config.Chat.PlayerDisplaySyntax + pingTag;
		        
		        pingTabSytax = _messageTags.ReplaceOPTag(pingTabSytax, cmPlayer.BukkitPlayer.isOp()); 		    			        
		        pingTabSytax = _messageTags.ReplaceRankTag(pingTabSytax, cmPlayer.RankGroup);
		        pingTabSytax = _messageTags.ReplacePlayerTag(pingTabSytax, cmPlayer);
		        pingTabSytax = _messageTags.ReplacePingTag(pingTabSytax, ping);	      
                
				cmPlayer.BukkitPlayer.setPlayerListName(_logger.TransformToColor(pingTabSytax));
		    }
		}
		catch(Exception exeption)
		{
			String message = "Ping Tab could not do its work. " + exeption.getMessage();
			
		   _logger.SendToConsole(Module.PingTab, MessageType.Error, message);
		}
	} 
	  
	  private String SetColorTag(int ping)
	  {
		  String pingTag = "";
		  
		  	if(ping >= _config.Ping.DeathPing)
	    	{
		  		pingTag = _config.Ping.DeathPingSyntax;
	    	}
	    	else 	    		
	    	{
	    		if(ping >= _config.Ping.HiPing)
	    		{
	    			pingTag = _config.Ping.HiPingSyntax;
	    		}
	    		else
	    		{
	    			if(ping >= _config.Ping.MidPing)
		    		{
	    				pingTag = _config.Ping.MidPingSyntax;
		    		}
	    			else
	    			{
	    				if(ping >= _config.Ping.NormalPing)
	    				{
	    					pingTag = _config.Ping.NormalPingSyntax;
	    				}
	    				else
	    				{
	    					if(ping >= _config.Ping.LowPing)
	    					{
	    						pingTag = _config.Ping.LowPingSyntax;
	    					}
	    					else
	    					{
	    						pingTag = _config.Ping.LocalPingSyntax; 
	    					}
	    					
	    				}
	    			}
	    		}	    	   
	    	}		  	
		  	
		  	return pingTag;
	  } 
}