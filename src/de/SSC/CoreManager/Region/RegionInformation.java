package de.SSC.CoreManager.Region;

public class RegionInformation 
{
	public final boolean PermissionToBuild;
	public final boolean PermissionToAccess;	
	
	public RegionInformation( final boolean permissionToBuild, final boolean permissionToAccess)
	{
		PermissionToBuild = permissionToBuild;
		PermissionToAccess = permissionToAccess;
	}
}
