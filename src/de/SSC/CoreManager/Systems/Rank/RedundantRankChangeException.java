package de.SSC.CoreManager.Systems.Rank;

import de.SSC.CoreManager.Systems.Chat.ExceptionInformation;

public class RedundantRankChangeException extends Exception 
{
	private static final long serialVersionUID = -7970659784030477907L;
	public ExceptionInformation Information;
	public CMRank Rank;
	
	public RedundantRankChangeException(ExceptionInformation exceptionInformation, CMRank cmRank) 
    {
        super("The rank is already set this way.");
        
        Information = exceptionInformation;
        Rank = cmRank;
    }
}