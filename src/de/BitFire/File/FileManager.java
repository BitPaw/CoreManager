package de.BitFire.File;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Module;

public class FileManager extends BaseSystem implements ISystem
{
	private static FileManager _instance;
	private final String _pluginFolder = "plugins";
	private final String _pluginName = "CoreManager";
	
	private FileManager() 
	{
		super(Module.FileIO, SystemState.Inactive, Priority.High);
		_instance = this;		
	}
	
	public static FileManager Instance()
	{
		return _instance == null ? new FileManager() : _instance;
	}
	
    public void CreateDataFolder()
    {
       Path path = Paths.get(_pluginFolder, _pluginName); 
    	
    	CreateFolderIfNotExist(path.toString());
    }
	
    public void CreateFolderIfNotExist(final String string)
    {
        File dataFolder = new File(string);
     
        if (!dataFolder.exists()) 
        {
            dataFolder.mkdirs();
        }
    }
}