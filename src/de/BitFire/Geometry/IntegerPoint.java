package de.BitFire.Geometry;

import org.bukkit.Location;

public class IntegerPoint 
{
	public final int X;
	public final int Y;
	public final int Z;
	
	public IntegerPoint(final int x, final int y , final int z)
	{
		X = x;
		Y = y;
		Z = z;
	}
	
	public IntegerPoint(final double x, final double y , final double z)
	{
		X = (int)Math.round(x);
		Y = (int)Math.round(y);
		Z = (int)Math.round(z);
	}	
	
	public IntegerPoint(final Location location)
	{
		this(location.getX(), location.getY(), location.getZ());
	}
	
	public IntegerPoint(final Point point)
	{
		this(point.X, point.Y, point.Z);
	}
}