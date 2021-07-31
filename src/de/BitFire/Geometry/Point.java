package de.BitFire.Geometry;

import org.bukkit.Location;

import com.sun.istack.internal.NotNull;

public class Point 
{
	public double X;
	public double Y;
	public double Z;
	
	public Point(final double x, final double z)
	{
		X = x;
		Y = -1;
		Z = z;
	}
	
	public Point(final double x, final double y , final double z)
	{
		X = x;
		Y = y;
		Z = z;
	}
	
	public Point(@NotNull final Location location)
	{
		X = location.getX();
		Y = location.getY();
		Z = location.getZ();
	}
	
	@Override
	public String toString()
	{
		return "[X:" + X + " Y:" + Y + " Z:" + Z + "]";
	}

	/**
	 * Looks if a two points have the exact same coordinates.
	 */
	@Override
	public boolean equals(final Object object)
	{
		if(object instanceof Point)
		{
			final Point point = (Point)object;			
			
			return equals(point);
		}
		
		return false;
	}
	
	/**
	 * Looks if a two points have the exact same coordinates.
	 */
	public boolean equals(final Point point)
	{				
		final boolean xIdentical = point.X == X; 
		
		System.out.print("A: " + this.toString());
		System.out.print("B: " + point.toString());
		
		if(xIdentical)
		{
			final boolean yIdentical = point.Y == Y; 
			
			if(yIdentical)
			{
				final boolean zIdentical = point.Z == Z;
				
				if(zIdentical)
				{
					return true;
				}
			}
		}	
	
		return false;
	}
	
	public void RoundCoordinates() 
	{
		 X = (int)Math.round(X);
		 Y = (int)Math.round(Y);
		 Z = (int)Math.round(Z);	
	}
}