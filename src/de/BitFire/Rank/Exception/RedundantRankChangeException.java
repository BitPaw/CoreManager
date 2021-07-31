package de.BitFire.Rank.Exception;

import de.BitFire.Rank.CMRank;

public class RedundantRankChangeException extends Exception 
{
	private static final long serialVersionUID = -7970659784030477907L;
	public CMRank Rank;
	
	public RedundantRankChangeException(CMRank cmRank) 
    {
        super("The rank is already set this way.");
        
        Rank = cmRank;
    }
}