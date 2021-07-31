package de.BitFire.Math.Geometry;

import org.bukkit.Location;

public class PositionBlock
{
	public final int X;
	public final int Y;
	public final int Z;
	
	public PositionBlock(final int x, final int y , final int z)
	{
		X = x;
		Y = y;
		Z = z;
	}
	
	public PositionBlock(final double x, final double y , final double z)
	{
		X = (int)Math.round(x);
		Y = (int)Math.round(y);
		Z = (int)Math.round(z);
	}	
	
	public PositionBlock(final Location location)
	{
		this(location.getX(), location.getY(), location.getZ());
	}
	
	public PositionBlock(final Position point)
	{
		this(point.X, point.Y, point.Z);
	}
	
	public static boolean SamePosition(final PositionBlock posA, final PositionBlock posB)
	{		
		if(posA.X == posB.X)
		{			
			if(posA.Y == posB.Y)
			{				
				if(posA.Z == posB.Z)
				{
					return true;
				}	
			}
		}
		
		return false;
	}
	
	public boolean SamePosition(final PositionBlock positionBlock)
	{					
		return PositionBlock.SamePosition(this, positionBlock);
	}
}