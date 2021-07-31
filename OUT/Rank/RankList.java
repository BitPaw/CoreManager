package de.BitFire.Rank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.BitFire.Rank.Exception.RankNotFoundException;

public class RankList 
{
	private Map<Integer, CMRank> _rank;
	private Map<String, Integer> _rankIDLookUp;	
	
	public RankList()
	{
		_rank = new HashMap<Integer, CMRank>();
		_rankIDLookUp = new HashMap<String, Integer>();
	}
	
	public void Add(CMRank cmRank)
	{
		_rank.put(cmRank.ID, cmRank);
		_rankIDLookUp.put(cmRank.Name, cmRank.ID);
	}
	
	public void Clear()
	{
		_rank.clear();
		_rankIDLookUp.clear();
	}
	
	public CMRank GetRank(final String rankName) throws RankNotFoundException
	{
		final Integer rankID = _rankIDLookUp.get(rankName);		
		
		if(rankID == null) 
		{
			throw new RankNotFoundException(rankName);
		}
		
		final CMRank rank = GetRank(rankID);
			
		return rank;
	}
	
	public CMRank GetRank(int rankID) throws RankNotFoundException
	{
		final CMRank cmRank = _rank.get(rankID);
		
		if(cmRank == null)
		{
			throw new RankNotFoundException("ID:" + rankID);
		}
		
		return cmRank;
	}
	
	public CMRank GetDefaultRank() throws RankNotFoundException
	{
		return GetRank(0);
	}

	public List<CMRank> GetAllRanks() 
	{
		List<CMRank> ranks = new ArrayList<CMRank>();
		
		for(Map.Entry<Integer, CMRank> entry : _rank.entrySet()) 
		{
			CMRank cmrank = entry.getValue();

			ranks.add(cmrank);
		}
		
		return ranks;
	}

	public void ClearPermission() 
	{
		for(Map.Entry<Integer, CMRank> entry : _rank.entrySet()) 
		{
			CMRank cmrank = entry.getValue();
			
			cmrank.Permission.Clear();
		}		
	}
}
