package de.SSC.CoreManager.World;

import de.SSC.CoreManager.Config.IConfig;

public class WorldsConfig implements IConfig
{
	public String MapsFolderBackSlash;
	public String MapsFolderSlash;
	public boolean OutPutWorldGenerator;

	public void LoadDefaults() 
	{
		MapsFolderSlash = "Maps/";
		MapsFolderBackSlash = "Maps\\";
		OutPutWorldGenerator = false;
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