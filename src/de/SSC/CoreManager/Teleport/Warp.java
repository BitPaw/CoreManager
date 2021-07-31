package de.SSC.CoreManager.Teleport;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Warp 
{
	public String WarpName;
	public Location WarpLocation;	
	
	public Warp(Player player, String warpName)
	{
		WarpLocation = player.getLocation();
		WarpName = warpName;
	}
	
	public Warp(String name, Location location)
	{
		WarpName = name;
		WarpLocation = location;
		
		/*
		World world = location.getWorld();
		double x = location.getX();
		double y = ;
		double z;
		float yaw;
		float pitch;
		
		Location location = new Location(world, x, y, z, yaw, pitch);
		*/
	}
	
	public static Warp GetWarpPerName(String name, List<Warp> warps)
	{
		Warp warp = null;
		
		for(Warp w : warps)
		{
			if(w.WarpName.equalsIgnoreCase(name)) warp = w;
		}
		
		return warp;
	}
}
