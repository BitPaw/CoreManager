package de.SSC.CoreManager.Rank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public CMRank GetRank(String rankName)
	{
		int rankID = _rankIDLookUp.get(rankName);
		
		return _rank.get(rankID);
	}
	
	public CMRank GetRank(int rankID)
	{
		return _rank.get(rankID);
	}
	
	public CMRank GetDefaultRank()
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
