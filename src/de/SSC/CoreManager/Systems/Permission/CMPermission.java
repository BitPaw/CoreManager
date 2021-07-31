package de.SSC.CoreManager.Systems.Permission;

import de.SSC.CoreManager.Systems.Rank.CMRank;

public class CMPermission 
{
	public String PermissionName;
	public CMRank RankGroup;	
	
	public CMPermission(String name, CMRank rank)
	{
		PermissionName = name;
		RankGroup = rank;
	}
}
