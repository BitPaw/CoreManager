package de.SSC.CoreManager.Config.Messages;

import de.SSC.CoreManager.Config.IConfig;

public class MessagesRank implements IConfig
{
	public String RankListHeader;
	public String RankListFooter;
	public String RankRow;
	
	public String RankTag;
	public String RankColorTag;
	public String FailedToLoadAllRanks;


	
	public void LoadDefaults() 
	{
		RankTag = "{Rank}";
		RankColorTag = "{RankColorTag}";
		
		RankListHeader = "&6======[&eAll Ranks&6]======";
		RankRow = "&r {Rank} &6as {RankColorTag}";
		RankListFooter = "&6======================";		
		
		FailedToLoadAllRanks = "Failed to load all ranks!";

	}
}
