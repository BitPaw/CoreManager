package de.SSC.CoreManager.DataBase.DataTypes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Messages.Logger;
import de.SSC.CoreManager.Messages.MessageType;
import de.SSC.CoreManager.Messages.Module;
import de.SSC.CoreManager.Utility.MessageTags;

public class CMPlayerList 
{
	private static CMPlayerList _instance;
	private List<CMPlayer> _players;
	private Logger _logger;
	private Config _config;
	private MessageTags _messageTags;
	
	private CMPlayerList()
	{
		_instance = this;
		
		_players = new ArrayList<CMPlayer>();
		_logger = Logger.Instance();
		_config = Config.Instance();
		_messageTags = MessageTags.Instance();
	}
	
	public static CMPlayerList Instance()
	{	
		return _instance == null ? new CMPlayerList() : _instance;
	}
	
	public void AddPlayer(CMPlayer cmPlayer)
	{
		String message = _config.Messages.Player.UserAdded;
		
		message = _messageTags.ReplacePlayerTag(message, cmPlayer);
		
		_logger.SendToConsole(Module.RankList, MessageType.Info, message);
		
		_players.add(cmPlayer);
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
			  		  
			  message = _messageTags.ReplacePlayerTag(message, playerName);
			  
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
			  
			  message = _messageTags.ReplacePlayerTag(message, player);	  
				
			  _logger.SendToConsole(Module.PlayerList, MessageType.Error, message);				
		  }
		  
		  return returnCMPlayer;
	}

	public List<CMPlayer> GetList()
	{
		return _players;
	}
}
