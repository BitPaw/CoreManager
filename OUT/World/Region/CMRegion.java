package de.BitFire.World.Region;

import java.util.UUID;

import de.BitFire.Geometry.Cube;

public class CMRegion 
{
	public final int ID;
	public final String Name;
	public final int WorldID;
	public final Cube RegionField;
	public final RegionInformation Information;	
	public UUID Owner;	
	
	public CMRegion(final int id, final String name, final int worldID , final Cube cube, final RegionInformation regionInformation)
	{
		ID = id;
		Name = name;
		RegionField = cube;
		
		WorldID = worldID;
		Information = regionInformation;
		
		Owner = null;
	}		
}