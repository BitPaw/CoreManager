package de.SSC.DigitalClock;

import org.bukkit.Location;
import org.bukkit.Material;

import de.SSC.CoreManager.Player.ViewDirection;

public class Clock 
{
	public int ID;
	public String Name;	
	public Material NumberMaterial;
	public Material FillMaterial;	
	public Location AncerLocation;
	public ViewDirection ClockViewDirection;
	
	public Clock()
	{
		ID = -1;
		Name = "[N/A]";
		NumberMaterial = Material.RED_WOOL;
		FillMaterial = Material.WHITE_WOOL;
		AncerLocation = null;
	}
	
	public Clock(int id, String name, Location location)
	{
		this();
		
		ID = id;
		Name = name;
		AncerLocation = location;
	}
	
	public Clock(int id, String name, Material number, Material fill, Location location, ViewDirection viewDirection)
	{
		this();
		
		ID = id;
		Name = name;
		NumberMaterial =number;
		FillMaterial = fill;
		AncerLocation = location;
		ClockViewDirection = viewDirection;
	}
}
