package de.SSC.CoreManager.DataBase.DataTypes;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Messages.Logger;
import de.SSC.CoreManager.Messages.MessageType;
import de.SSC.CoreManager.Messages.Module;

public class CMPlayerList 
{
	private static CMPlayerList _instance;
	private ArrayList<CMPlayer> _players;
	private Logger _logger;
	private Config _config;
	
	private CMPlayerList()
	{
		_instance = this;
		
		_players = new ArrayList<CMPlayer>();
		_logger = Logger.Instance();
		_config = Config.Instance();
	}
	
	public static CMPlayerList Instance()
	{	
		return _instance == null ? new CMPlayerList() : _instance;
	}
	
	public void AddPlayer(CMPlayer player)
	{
		String message = _config.Messages.Player.UserAdded;
		
		message = message.replace(_config.Messages.Player.PlayerTag, player.PlayerName);
		
		_logger.SendToConsole(Module.RankList, MessageType.Info, message);
		
		_players.add(player);
	}
	
	public void RemovePlayer(Player player)
	{
		CMPlayer cmPlayer = GetPlayer(player);
		
		_players.remove(cmPlayer);
	}
	
	public void RemovePlayer(CMPlayer player)
	{
		_players.remove(player);
	}

	public CMPlayer GetPlayer(String playerName) 
	{
		CMPlayer returnCMPlayer = null;
		String target;
		
		for(CMPlayer cmPlayer : _players)
		{
			  target = cmPlayer.PlayerUUID.toString().toLowerCase();
			  playerName = playerName.toLowerCase();
			  
			  if(target.equals(playerName))
			  {
				  returnCMPlayer = cmPlayer;
				  break;
			  }
		  }
		  	  
		  if(returnCMPlayer == null)
		  {		
			  String message = _config.Messages.Player.PlayerNotFound;
			  		  
			  message = message.replace(_config.Messages.Player.PlayerTag, playerName);
			  
			  _logger.SendToConsole(Module.PlayerList, MessageType.Warning, message);				
		  }
		  
		  return returnCMPlayer;
	}
	
	public CMPlayer GetPlayer(Player player) 
	{
		CMPlayer returnCMPlayer = null;
		  
		  for(CMPlayer cmPlayer : _players)
		  {
			  String target = cmPlayer.PlayerUUID.toString().toLowerCase();
			  String searcher = player.getUniqueId().toString().toLowerCase();
			  
			  if(target.equals(searcher))
			  {
				  returnCMPlayer = cmPlayer;
				  break;
			  }
		  }
		  	  
		  if(returnCMPlayer == null)
		  {				
			  String message = _config.Messages.Player.PlayerNotFound;
			  
			  message = message.replace(_config.Messages.Player.PlayerTag, player.getName());			  
				
			  _logger.SendToConsole(Module.PlayerList, MessageType.Error, message);				
		  }
		  
		  return returnCMPlayer;
	}
}
