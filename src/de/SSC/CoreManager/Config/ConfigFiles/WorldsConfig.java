package de.SSC.CoreManager.Config.ConfigFiles;

import de.SSC.CoreManager.Config.IConfig;

public class WorldsConfig implements IConfig
{
public String MapsFolder;

	
	
	
	public void LoadDefaults() 
	{
		MapsFolder = "Maps/";

	}
	
	public String AddFolderName(String worlsName)
	{
		return MapsFolder + worlsName;
	}

public String RemoveFolderName(String worlsName)
{
	return worlsName.replace(MapsFolder, "");
}


}
