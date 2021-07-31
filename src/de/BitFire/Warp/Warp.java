package de.BitFire.Warp;

import org.bukkit.Location;

import de.BitFire.Player.CMPlayer;

public class Warp 
{
	public int ID;
	public String WarpName;
	public Location WarpLocation;	
	public CMPlayer Owner;
	
	public Warp(String warpName, Location warpLocation)
	{
		WarpName = warpName;
		WarpLocation = warpLocation;
	}	
}