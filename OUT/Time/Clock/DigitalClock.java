package de.BitFire.Time.Clock;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class DigitalClock 
{
	public int ID;
	public String Name;	
	public Material NumberMaterial;
	public Material FillMaterial;	
	public Location AncerLocation;
	public BlockFace ClockViewDirection;
	
	public DigitalClock()
	{
		ID = -1;
		Name = "[N/A]";
		NumberMaterial = Material.RED_WOOL;
		FillMaterial = Material.WHITE_WOOL;
		AncerLocation = null;
	}
	
	public DigitalClock(int id, String name, Location location)
	{
		this();
				
		ID = id;
		Name = name;
		AncerLocation = location;
	}
	
	public DigitalClock(int id, String name, Location location, Material number, Material fill)
	{
		this(id, name, location);
		
		NumberMaterial =number;
		FillMaterial = fill;
	}
}