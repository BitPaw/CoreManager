package de.SSC.CoreManager.Config.Messages;

import de.SSC.CoreManager.Config.IConfig;

public class MessagesTeleportSystem implements IConfig
{

	public String TeleportSystemTag;
	public String Teleporting;
	public String CouldNotTeleport;
	public String NoWarpWithThisName;
	public String WarpCreated;	
	public String WarpTag;
	
	public String SpawnUpdated;
	

	public void LoadDefaults()
	{
		WarpTag = "{Warp}";
		TeleportSystemTag = "&6[&eTPS&6] ";	
		Teleporting = "&6Teleporting...";
		CouldNotTeleport = "&cCould not teleport!";
		NoWarpWithThisName = "&cNo warp with this name!";
		WarpCreated = "&7Warp &6<&f{Warp}&6> &acreated&7.";		
		
		SpawnUpdated = "&7Spawn &aupdated.";
	}
}
