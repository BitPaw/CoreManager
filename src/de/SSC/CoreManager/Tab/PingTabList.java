package de.SSC.CoreManager.Tab;

import java.util.Collection;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DataTypes.CMPlayer;
import de.SSC.CoreManager.DataBase.DataTypes.CMPlayerList;
import de.SSC.CoreManager.Messages.Logger;
import de.SSC.CoreManager.Messages.MessageType;
import de.SSC.CoreManager.Messages.Module;
import de.SSC.CoreManager.Utility.BukkitUtility;

public class PingTabList extends BukkitRunnable
{
	private static PingTabList _instance;
	private Config _config;	
	private Logger _logger;
	private CMPlayerList _cmPlayerList;	
	private BukkitUtility _bukkitUtility;	
	
	  private  PingTabList()
	  {	    
	    _config = Config.Instance();
	    _logger = Logger.Instance();
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
		Collection<? extends Player> players =  _bukkitUtility.GetAllOnlinePlayers();  
		int ping;
		int currentPlayers= players.size();
		String pingTag;	  	 
        String pingTabSytax;
		CMPlayer cmPlayer;
		
		if(currentPlayers <= 0)
		{
			return;
		}

		try
		{	
		    for (Player player : players)
		    {
		    	ping = _bukkitUtility.GetPlayerPing(player);
		    	pingTag = SetColorTag(ping);	 
	            pingTabSytax = _config.Chat.PrefixSyntax + _config.Chat.PlayerNameSyntax + pingTag;
		        cmPlayer  = _cmPlayerList.GetPlayer(player);
		        
		        pingTabSytax = _config.Chat.SetOPTag(player.isOp(), pingTabSytax); 		    			        
			    pingTabSytax = _config.Chat.SetNameTag(cmPlayer, pingTabSytax);
                pingTabSytax = _config.Chat.SetRankTag(cmPlayer.RankGroup, pingTabSytax);
		      
				player.setPlayerListName(_logger.TransformToColor(pingTabSytax));
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
		  	
		  	pingTag = pingTag.replace(_config.Ping.PingTag, String.valueOf(ping));
		  	
		  	return pingTag;
	  } 
}