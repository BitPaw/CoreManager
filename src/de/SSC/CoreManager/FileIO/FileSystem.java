package de.SSC.CoreManager.FileIO;

import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.System.BaseSystem;
import de.SSC.CoreManager.System.ISystem;
import de.SSC.CoreManager.System.SystemPriority;
import de.SSC.CoreManager.System.SystemState;

public class FileSystem extends BaseSystem implements ISystem
{
	private static FileSystem _instance;
	
	public FileSystem() 
	{
		super(Module.FileIO, SystemState.Inactive, SystemPriority.High);
		_instance = this;		
	}
	
	public static FileSystem Instance()
	{
		return _instance == null ? new FileSystem() : _instance;
	}
}