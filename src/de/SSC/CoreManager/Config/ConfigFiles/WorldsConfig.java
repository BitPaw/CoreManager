package de.SSC.CoreManager.Config.ConfigFiles;

import de.SSC.CoreManager.Config.IConfig;

public class WorldsConfig implements IConfig
{
	public String MapsFolderBackSlash;
	public String MapsFolderSlash;

	public void LoadDefaults() 
	{
		MapsFolderSlash = "Maps/";
		MapsFolderBackSlash = "Maps\\";
	}
	
	public String AddFolderName(String worlsName)
	{
		return MapsFolderSlash + worlsName;
	}

	public String RemoveFolderName(String worlsName)
	{
		if(worlsName.contains("/"))
		{
			worlsName = worlsName.replace(MapsFolderSlash, "");
		}
		
		if(worlsName.contains("\\"))
		{
			worlsName = worlsName.replace(MapsFolderBackSlash, "");
		}
		
		return worlsName;
	}
}