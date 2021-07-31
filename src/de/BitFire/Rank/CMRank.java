package de.BitFire.Rank;

import de.BitFire.Permission.PermissionContainer;

public class CMRank 
{
	public final int ID;
	public final String Name;
	public final String Tag;
	public final String PlayerColor;
	public PermissionContainer Permission;
	
	public CMRank(int id, String name, String colorTag, String playerColor)
	{
	  ID = id;
	   Name = name;
	    Tag = colorTag;
	    PlayerColor = playerColor;
	    
	    Permission = new PermissionContainer();
	}
}
