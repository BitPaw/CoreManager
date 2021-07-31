package de.SSC.CoreManager.Systems.Region;

import de.SSC.CoreManager.Systems.Location.CMLocation;
import de.SSC.CoreManager.Systems.Player.CMPlayer;

public class CMRegion 
{
	public boolean IsActive;
	public String RegionName;
	public CMPlayer Owner;
	public CMLocation Location;
	public boolean Protected;
	public boolean AllowAccess;	
	public boolean IgnoreHight; 
	
	public CMRegion(boolean isActive, String name, CMPlayer cmPlayer, CMLocation location)
	{
		IsActive = isActive;
		  RegionName = name;
		  Owner = cmPlayer;
		  Location = location;
		  Protected = true;
		  AllowAccess = false;	
		  IgnoreHight = false; 
		  
	}
}
