package de.SSC.CoreManager.DataBase.DataTypes;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class CMPlayerList 
{
	private static CMPlayerList _instance;
	private ArrayList<CMPlayer> _players;
	
	private CMPlayerList()
	{
		_players = new ArrayList<CMPlayer>();
	}
	
	public static CMPlayerList Instance()
	{
		if(_instance == null)
		{
			_instance = new CMPlayerList();
		}
		
		return _instance;
	}
	
	public void AddPlayer(CMPlayer player)
	{
		_players.add(player);
	}
	
	public void RemovePlayer(CMPlayer player)
	{
		_players.remove(player);
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
		  	  
		  return returnCMPlayer;
	}
}
