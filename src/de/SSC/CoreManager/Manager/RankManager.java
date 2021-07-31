package de.SSC.CoreManager.Manager;

import org.bukkit.command.CommandSender;

public class RankManager 
{
	  private static RankManager _instance;
	  
	  
	  public static RankManager Instance()
	  {
		  if(_instance == null)
		  {
			  _instance = new RankManager();
		  }
		  
		  return _instance;
	  }


	public void ChangeRankCommand(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		
	}


	public void ResetPlayerRank(CommandSender sender) {
		// TODO Auto-generated method stub
		
	}

}
