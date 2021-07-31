package de.SSC.CoreManager.Config.Messages;

import de.SSC.CoreManager.Config.IConfig;

public class MessagesRank implements IConfig
{
	public String RankListHeader;
	public String RankListFooter;
	public String RankRow;
	

	
	public String FailedToLoadAllRanks;


	
	public void LoadDefaults() 
	{

		
		RankListHeader = "&6======[&eAll Ranks&6]======";
		RankRow = "&r {NAME} &6as {RANK}";
		RankListFooter = "&6======================";		
		
		FailedToLoadAllRanks = "Failed to load all ranks!";

	}
}
