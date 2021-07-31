package de.SSC.CoreManager.Geometry;

public class Cube 
{
	public final Point PointA;
	public final Point PointB;
	
	public final boolean InfinityHeight;
	
	public Cube(final Point pointA,  final Point pointB, final boolean infinityHeight)
	{
		PointA = pointA;
		PointB = pointB;
		
		InfinityHeight = infinityHeight;
	}
	
	public boolean IsPointInCube(final Point point)
	{
		final boolean isInX = IsBetweenPoints(PointA.X, PointB.X, point.X); 
		final boolean isInY = InfinityHeight ? true : IsBetweenPoints(PointA.Y, PointB.Y, point.Y); 
		final boolean isInZ = IsBetweenPoints(PointA.Z, PointB.Z, point.Z);	
		
		return isInX && isInY && isInZ;
	}
	
	private boolean IsBetweenPoints(final double pointA, final double pointB, final double target)
	{
		double min;
		double max;
		
		if(pointA > pointB)
		{
			min = pointB;
			max = pointA;	
		}
		else
		{
			min = pointA;
			max = pointB;	
		}
		
		return target > min && target < max;
	}
}
