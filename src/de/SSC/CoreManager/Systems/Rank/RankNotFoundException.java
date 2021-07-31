package de.SSC.CoreManager.Systems.Rank;

import de.SSC.CoreManager.Systems.Chat.ExceptionInformation;

@SuppressWarnings("serial")
public class RankNotFoundException extends Exception 
{
	public final ExceptionInformation Information;
	public final String WantedRankName;
	
	public RankNotFoundException(ExceptionInformation exceptionInformation, String wantedRank) 
    {
        super("Missing rankname : " + wantedRank);
        
        Information = exceptionInformation;
        WantedRankName = wantedRank;
    }
}
