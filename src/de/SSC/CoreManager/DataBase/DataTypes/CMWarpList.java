package de.SSC.CoreManager.DataBase.DataTypes;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import de.SSC.CoreManager.DataBase.DatabaseManager;
import de.SSC.CoreManager.Messages.Logger;
import de.SSC.CoreManager.Messages.MessageType;
import de.SSC.CoreManager.Messages.Module;

public class CMWarpList 
{
	private static CMWarpList _instance;
	private ArrayList<CMWarp> _warps;	
	private Logger _logger;
	private DatabaseManager _databaseManager;



private CMWarpList()
{
	_instance = this;
	
	_warps = new ArrayList<CMWarp>();
	
	_logger = Logger.Instance();
	_databaseManager = DatabaseManager.Instance();
}

public static CMWarpList Instance()
{
	return _instance == null ? new CMWarpList() : _instance;
}
	
	public CMWarp GetWarpPerName(String name)
	{
		CMWarp returnWarp = null;
		
		for(CMWarp warp : _warps)
		{
			if(warp.WarpName.equalsIgnoreCase(name))
				{
				returnWarp = warp;
				}
		}
		
		return returnWarp;
	}

	public void ListAllWarps(CommandSender sender) 
	{
        String message = "&6===All Warps (&e" + _warps.size() + "&6)===\n";

    	for(CMWarp warp : _warps)
		{
    		message += "&e" + warp.WarpName + "&6,\n";
		}        
        
    	message += "&6=================";
    	
    	_logger.SendToSender(Module.WarpList, MessageType.None, sender, message);
	}

	public void AddWarp(CMWarp cmWarp) 
	{
		_warps.add(cmWarp);		
	}

	public void ReloadWarps() 
	{
		_warps.clear();		
		_warps = _databaseManager.LoadAllWarps();
	}
}
