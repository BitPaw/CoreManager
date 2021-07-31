package de.SSC.CoreManager.Geometry;

import org.bukkit.Location;

public class Point 
{
	public final double X;
	public final double Y;
	public final double Z;
	
	public Point(final double x, final double y , final double z)
	{
		X = x;
		Y = y;
		Z = z;
	}
	
	public Point(final Location location)
	{
		X = location.getX();
		Y = location.getY();
		Z = location.getZ();
	}
}
