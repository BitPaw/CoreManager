package de.SSC.CoreManager.Rank;

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
		RankRow = " &6Rank &r{NAME} &6as&r {RANK} &6with &e {VALUE} &6Permissions";
		RankListFooter = "&6======================";		
		
		FailedToLoadAllRanks = "Failed to load all ranks!";
	}
}
