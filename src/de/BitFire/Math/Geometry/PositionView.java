package de.BitFire.Math.Geometry;

import org.bukkit.Location;

public class PositionView extends Position 
{
	public final float Yaw;
	public final float Pitch;
	
	public PositionView(final double x, final double y, final double z, final float yaw, final float pitch)
	{
		super(x, y, z);
		
		Yaw = yaw;
		Pitch = pitch;
	}

	public PositionView(final Location location) 
	{
		super(location);
		
		Yaw = location.getYaw();
		Pitch = location.getPitch();
	}
}
