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

public class CMRankList 
{
  private static CMRankList _instance;	  
  private DatabaseManager _databaseManager;	
  private CMRank _defaultRank;	
  private ArrayList<CMRank> _ranks;
  private Logger _logger;
  private Config _config;	
  
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
			 	  
		  row = row.replace(_config.Messages.Rank.RankTag,  rank.RankName);
		  row = row.replace(_config.Messages.Rank.RankColorTag,  rank.ColorTag);
		 
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

public void ResetPlayerRank(Player player)
{
	CMPlayerList cmPlayerList = CMPlayerList.Instance();
	CMPlayer cmPlayer = cmPlayerList.GetPlayer(player);
	
	ResetPlayerRank(cmPlayer);
}

public void ResetPlayerRank(CMPlayer player)
{
	player.RankGroup = 	GetDefaultRank();
}

public void ReloadRanks() 
{
	_ranks.clear();	
	_ranks = _databaseManager.LoadAllRanks();
	
}

}
