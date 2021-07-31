package de.SSC.CoreManager.DataBase.DataTypes;

import org.bukkit.Location;

public class CMWarp 
{
	public String WarpName;
	public Location WarpLocation;	
	
	public CMWarp(String warpName, Location warpLocation)
	{
		WarpName = warpName;
		WarpLocation = warpLocation;
	}
}
