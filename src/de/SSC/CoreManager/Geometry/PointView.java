package de.SSC.CoreManager.Geometry;

import org.bukkit.Location;

public class PointView extends Point 
{
	public final float Yaw;
	public final float Pitch;
	
	public PointView(final double x, final double y, final double z, final float yaw, final float pitch)
	{
		super(x, y, z);
		
		Yaw = yaw;
		Pitch = pitch;
	}

	public PointView(final Location location) 
	{
		super(location);
		
		Yaw = location.getYaw();
		Pitch = location.getPitch();
	}
}
