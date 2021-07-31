package de.BitFire.Teleport.Portal.Exception;

import org.bukkit.Location;

public class PortalNotFoundException extends Exception 
{
	private static final long serialVersionUID = 1L;
	public final String MissingPortal; 
	public final Location TargetedLocation;
	
	public PortalNotFoundException(final String name) 
	{
		MissingPortal = name;
		TargetedLocation = null;
	}
	
	public PortalNotFoundException(final Location location) 
	{
		MissingPortal = "[N/A]";
		TargetedLocation = location;
	}
}