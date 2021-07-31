package de.SSC.CoreManager.Rank.Exception;

public class RankNotFoundException extends Exception 
{
	private static final long serialVersionUID = 1L;
	public final String WantedRankName;
	
	public RankNotFoundException(String wantedRank) 
    {
        super("Missing rankname : " + wantedRank);
        
        WantedRankName = wantedRank;
    }
}
