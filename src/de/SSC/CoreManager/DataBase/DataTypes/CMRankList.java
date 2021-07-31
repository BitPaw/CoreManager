package de.SSC.CoreManager.DataBase.DataTypes;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import de.SSC.CoreManager.Logger;
import de.SSC.CoreManager.DataBase.DatabaseManager;

public class CMRankList 
{
  private static CMRankList _instance;	
   private DatabaseManager _databaseManager;
	private CMRank _defaultRank;	
	private ArrayList<CMRank> _ranks;
	private Logger _logger;
  
  public static CMRankList Instance()
  {
	  if(_instance == null)
	  {
		  _instance = new CMRankList();
	  }
	  
	  return _instance;
  }
  
  public CMRankList()
  {
	  _ranks = new ArrayList<CMRank>();
	  _logger = Logger.Instance();
	  _databaseManager = DatabaseManager.Instance();
	  
	  _ranks = _databaseManager.LoadAllRanks();
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
		  _logger.WriteInfo("The rank " + name + " was searched but not found.");
	  }
	  	  
	  return returnRank;
  }
  
  public void ListAllRanks(CommandSender sender)
  {
	  String message = "\n&6-----[All Ranks]-----\n";	  
	  
	  for(CMRank rank : _ranks)
	  {
		  message += "&r  " + rank.RankName + " &6as " + rank.ColorTag + "&r\n";
	  }	  
	  
	  message += "&6------------";	
	  
	  sender.sendMessage(_logger.TransformToColor(message)); 
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
}
