package de.BitFire.Teleport;

import de.BitFire.Configuration.IConfig;

public class TeleportSystemMessageContainer implements IConfig
{
	public String TeleportSystemTag;
	public String Teleporting;
	public String CouldNotTeleport;
	public String NoWarpWithThisName;
	public String WarpCreated;	
	public String SpawnUpdated;
	public String CantTeleportBlockOutOfReach;	

	public void LoadDefaults()
	{		
		TeleportSystemTag = "&6[&eTPS&6] ";	
		Teleporting = "&7Teleporting...";
		CouldNotTeleport = "&cCould not teleport!";
		NoWarpWithThisName = "&cNo warp with this name!";
		WarpCreated = "&7Warp &6<&f{WARP}&6> &acreated&7.";				
		SpawnUpdated = "&7Spawn &aupdated.";		
		CantTeleportBlockOutOfReach = "&cCan't &7teleport&8, &eblock&7 too far away!";
	}
}
