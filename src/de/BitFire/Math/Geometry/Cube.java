package de.BitFire.Math.Geometry;

public class Cube 
{	
	public Position PointA;
	public Position PointB;
	
	public boolean InfinityHeight;
			
	public Cube(final Position pointA,  final Position pointB)
	{
		this(pointA, pointB, false);
	}
	
	public Cube(final Position pointA,  final Position pointB, final boolean infinityHeight)
	{
		PointA = pointA;
		PointB = pointB;
		
		InfinityHeight = infinityHeight;
	}
	
	public Cube(final double aX, final double aZ, final double bX, final double bZ) 
	{
		PointA = new Position(aX, aZ);
		PointB = new Position(aX, aZ);
		
		InfinityHeight = true;
	}

	public boolean IsPositionASet()
	{
		return PointA != null;
	}
	
	public boolean IsPositionBSet()
	{
		return PointB != null;
	}
	
	public boolean IsXInCube(final double x)
	{
		return IsBetweenPoints(PointA.X, PointB.X, x); 
	}
	
	public boolean IsYInCube(final double y)
	{
		return InfinityHeight ? true : IsBetweenPoints(PointA.Y, PointB.Y, y); 
	}
	
	public boolean IsZInCube(final double z)
	{
		return IsBetweenPoints(PointA.Z, PointB.Z, z);	
	}
	
	public boolean IsPointInCube(final Position point)
	{
		final boolean isInX = IsXInCube(point.X);
		final boolean isInY = IsYInCube(point.Y);
		final boolean isInZ = IsZInCube(point.Z);
		
		return isInX && isInY && isInZ;
	}
	
	private boolean IsBetweenPoints(final double pointA, final double pointB, final double target)
	{
		boolean IsBetweenPoints;
		double min;
		double max;

		if(pointA == pointB)
		{			
			IsBetweenPoints = target == pointA;
		}
		else
		{
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
			
			IsBetweenPoints = target >= min && target <= max;
		}
		
		return IsBetweenPoints;
	}
	
	private double CalcLength(double a, double b)
	{				
		return Math.abs(a - b) +1;
	}
	
	public double GetLengh()
	{		
		return CalcLength(PointA.X, PointB.X);
	}
	
	public double GetHight()
	{
		return CalcLength(PointA.Y, PointB.Y);
	}
	
	public double GetWidth()
	{
		return CalcLength(PointA.Z, PointB.Z);
	}

	public double GetVolumen() 
	{
		// +1 because blocks have an underflow. 1x1x1 block is always 1 not 0.
		double a = GetLengh();
		double b = GetWidth();
		double h = GetHight();
		
		return a * b * h;
	}

	public Position GetMiddlePoint() 
	{
		double x, y, z, w;
		
		w = PointA.X <= PointB.X ? PointA.X : PointB.X;		
		x = w + GetLengh() / (double)2;
		
		w = PointA.Y <= PointB.Y ? PointA.Y : PointB.Y;
		y = w;
		
		w = PointA.Z <= PointB.Z ? PointA.Z : PointB.Z;
		z = w + GetWidth() / (double)2;		
		
		return new Position(x, y, z);
	}
	
	@Override
	public String toString()
	{
		String message = "";
		
		message += "A: " + PointA.toString() + "\n";
		message += "B: " + PointB.toString() + "\n";
		message += "T: " + GetMiddlePoint().toString();
		
		return message;
	}
}
