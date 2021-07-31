package de.SSC.CoreManager.DataBase.DataTypes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DatabaseManager;
import de.SSC.CoreManager.Messages.Logger;
import de.SSC.CoreManager.Messages.MessageType;
import de.SSC.CoreManager.Messages.Module;
import de.SSC.CoreManager.Utility.BukkitUtility;
import de.SSC.CoreManager.Utility.MessageTags;

public class CMRankList 
{
  private static CMRankList _instance;	 
  private ArrayList<CMRank> _ranks;
  private CMRank _defaultRank;	
  
  
  private Logger _logger;
  private Config _config;	
  private MessageTags _messageTags;
  private DatabaseManager _databaseManager;	
  private BukkitUtility _bukkitUtility;
  
  public static CMRankList Instance()
  {  
	  return _instance == null ? new CMRankList() : _instance;
  }
  
  public CMRankList()
  {
	  _instance = this;
	  
	  _ranks = new ArrayList<CMRank>();
	  _logger = Logger.Instance();
	  _config = Config.Instance();
	  _databaseManager = DatabaseManager.Instance();	  
	  
	  _messageTags =  MessageTags.Instance();
	  _bukkitUtility = BukkitUtility.Instance();
  }
 
  public CMRank GetRankFromName(String name)
  {
	  CMRank returnRank = null;
	  
	  for(CMRank rank : _ranks)
	  {
		  if(rank.RankName.equalsIgnoreCase(name))
		  {
			  returnRank = rank;
			  break;
		  }
	  }
	  
	  if(returnRank == null)
	  {
		  String message = "The rank " + name + " was searched but not found.";
		  
		  _logger.SendToConsole(Module.RankList, MessageType.Warning, message);
	  }
	  	  
	  return returnRank;
  }
  
  public void ListAllRanks(CommandSender sender)
  {
	  String message = _config.Messages.Rank.RankListHeader +  _config.Messages.ConsoleIO.NewLine;	  
	  String row;
	  
	  for(CMRank rank : _ranks)
	  {
		  row = _config.Messages.Rank.RankRow;
			
		  row = _messageTags.ReplaceNameTag(row, rank.RankName);
		  row = _messageTags.ReplaceRankTag(row, rank);
		  		 
		  message += row + _config.Messages.ConsoleIO.NewLine;
	  }	  
	  
	  message += _config.Messages.Rank.RankListFooter;	
	  
	  
	  _logger.SendToSender(Module.RankList, MessageType.None, sender, message);
  }
  
  public List<CMRank> ListAllRanks()
  {
	  List<CMRank> ranks = new ArrayList<CMRank>();	  
	  
	  for(CMRank rank : _ranks)
	  {
		  ranks.add(rank);
	  }	  
	  
	return ranks;
  }
  
  public CMRank GetDefaultRank()
  {
	  if(_defaultRank == null)
	  {
		  for(CMRank rank : _ranks)
		  {
			  if(rank.IsDefault)
			  {
				  _defaultRank = rank;
				  break;
			  }
		  }
	  }
		  
	  return _defaultRank;
  }

public void ResetPlayerRank(CommandSender sender, String[] parameter)
{
	int parameterLengh = parameter.length;
	boolean isSenderPlayer = _bukkitUtility.IsSenderPlayer(sender);
	CMPlayerList cmPlayerList = CMPlayerList.Instance();
	Player player;	
	CMPlayer cmPlayer;	
	String targetedPlayer;
	String message;
		
	switch(parameterLengh)
	{
	case 0:
		if(isSenderPlayer)
		{
			player = (Player)sender;
			cmPlayer = cmPlayerList.GetPlayer(player);
			
			cmPlayer.RankGroup = GetDefaultRank();
			
			  message = "Rank has been resetted.";
			  
			  _logger.SendToSender(Module.RankList, MessageType.Info, sender, message);
		}
		else
		{
			
		}
		break;
		
	case 1:
		if(isSenderPlayer)
		{
			targetedPlayer = parameter[0];
			
			cmPlayer = cmPlayerList.GetPlayer(targetedPlayer);
			
			cmPlayer.RankGroup = 	GetDefaultRank();
			
			  message = "Rank has been changed from .";
			  
			  _logger.SendToSender(Module.RankList, MessageType.Info,sender,  message);
		}
		else
		{
			
		}
		break;
		
	 default:
			
			break;
	}
}

public void ReloadRanks() 
{
	_ranks.clear();	
	_ranks = _databaseManager.LoadAllRanks();	
}

public void ChangeRankCommand(CommandSender sender, String[] parameter)
{
	int parameterLengh = parameter.length;
	boolean isSenderPlayer = _bukkitUtility.IsSenderPlayer(sender);
	CMPlayerList cmPlayerList = CMPlayerList.Instance();
	Player player;	
	CMPlayer cmPlayer;	
	//String targetedPlayer;
	String message;
	String targetedRank;
	CMRank cmRank;
		
	switch(parameterLengh)
	{
	case 0:
		if(isSenderPlayer)
		{
			player = (Player)sender;
			cmPlayer = cmPlayerList.GetPlayer(player);
			
			cmRank = GetDefaultRank();
			
			cmPlayer.SetRank(cmRank);
			
			  message = "Rank has been resetted.";
			  
			  _logger.SendToSender(Module.RankList, MessageType.Info, sender, message);
		}
		else
		{
			
		}
		break;
		
	case 1:
		if(isSenderPlayer)
		{
			targetedRank = parameter[0];
			player = (Player)sender;
			
			cmPlayer = cmPlayerList.GetPlayer(player);
			
			cmRank = GetRankFromName(targetedRank);
			
			
			if(cmRank == null)
			{
				 message = "&cRank <> does not exists!";
				  
				 _logger.SendToSender(Module.RankList, MessageType.Error,sender,  message);
			}
			else
			{
				cmPlayer.SetRank(cmRank);
				
				 message = "Rank has been changed.";
				  
				  _logger.SendToSender(Module.RankList, MessageType.Info,sender,  message);
			}			 
		}
		else
		{
			targetedRank = parameter[0];
			//targetedPlayer = parameter[1];			
		
			  message = "Rank has been changed from .";
			  
			  _logger.SendToSender(Module.RankList, MessageType.Info,sender,  message);
		}
		break;
		
	case 2:
	 default:
			
			break;	
}

}

}
