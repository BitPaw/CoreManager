package de.SSC.CoreManager.Manager;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DatabaseManager;
import de.SSC.CoreManager.DataBase.DataTypes.CMWarp;
import de.SSC.CoreManager.DataBase.DataTypes.CMWarpList;
import de.SSC.CoreManager.Messages.Logger;
import de.SSC.CoreManager.Messages.MessageType;
import de.SSC.CoreManager.Messages.Module;
import de.SSC.CoreManager.Utility.BukkitUtility;
import de.SSC.CoreManager.Utility.MessageTags;

public class TeleportManager 
{
  private static TeleportManager _instance;
  private Logger _logger;
  private Config _config;
  private BukkitUtility  _bukkitUtility;
  private DatabaseManager _databaseManager; 
  private CMWarpList _cmWarpList;
  private MessageTags _messageTags;
  
  public static TeleportManager Instance()
  {  
	  return _instance == null ? new TeleportManager() : _instance ;
  }
  
  private TeleportManager()
  {
	  _instance = this;
	  
	  _logger =  Logger.Instance();
	  _config = Config.Instance();
	  _bukkitUtility = BukkitUtility.Instance();
	  _databaseManager = DatabaseManager.Instance();	
	  _cmWarpList = CMWarpList.Instance();
	  _messageTags = MessageTags.Instance();
  }

  private void TeleportToWarp(Player player, CMWarp cmWarp)
  {
	  	String message = _config.Messages.TeleportSystem.Teleporting;
	  	
	  _logger.SendToSender(Module.TeleportSystem, MessageType.Info, player, message);
	  
		try
		{
			player.teleport(cmWarp.WarpLocation);
		}
		catch(Exception e)
		{
			message = _config.Messages.TeleportSystem.CouldNotTeleport + "\n" + e.getMessage();
			
			_logger.SendToSender(Module.TeleportSystem, MessageType.Info, player, message);
		}
  }
  
public void Warp(CommandSender sender, String[] args) 
{	
	Player player = (Player)sender;
	String warpName = args[0];
	CMWarpList cmWarpList = CMWarpList.Instance();
	CMWarp cmWarp = cmWarpList.GetWarpPerName(warpName);	

	if(cmWarp == null) 
	{
		_logger.SendToSender(Module.TeleportSystem, MessageType.Error, sender, _config.Messages.TeleportSystem.NoWarpWithThisName);
	}
	else
	{
		TeleportToWarp(player, cmWarp);
	}	
}


public void SetWarp(CommandSender sender, String[] args) 
{
	String warpName = args[0];
	String message;
	Player player;
	CMWarp cmWarp;
	
	if(_bukkitUtility.IsSenderPlayer(sender))
	{			
		player = (Player)sender;	

		if(warpName.isEmpty())
		{
			_logger.SendToSender(Module.TeleportSystem, MessageType.Warning, player, _config.Messages.ConsoleIO.NoParameters);	
		}	
		else
		{
			cmWarp = new CMWarp(warpName, player.getLocation());
			
			SetWarpPoint(cmWarp);

			message =  _config.Messages.TeleportSystem.WarpCreated;			
		
			message = _messageTags.ReplaceWarpTag(message, warpName);
			
			_logger.SendToSender(Module.TeleportSystem, MessageType.Info, player, message);	
		}
	}
	else
	{		
		_logger.SendToSender(Module.TeleportSystem, MessageType.Warning, sender, _config.Messages.ConsoleIO.NotForConsole);	
	}	
}

private void SetWarpPoint(CMWarp cmWarp)
{
	boolean doesWarpExist;
	
	doesWarpExist = _databaseManager.DoesWarpExist(cmWarp);
	
	if(doesWarpExist)
	{
		_databaseManager.UpdateWarp(cmWarp);
	}
	else
	{
		_databaseManager.RegisterWarp(cmWarp);
		_cmWarpList.AddWarp(cmWarp);
	}		
}

public void TeleportToWorldSpawn(CommandSender sender) 
{
	
	
}


public void DeleteWarp(CommandSender sender, String[] args) 
{
	
	
}


public void SetSpawn(CommandSender sender) throws Exception 
{
	Player player;
	World world;
	Location location;
	String message;
	
	if(_bukkitUtility.IsSenderPlayer(sender))
	{
		player = (Player)sender;
		world = (World) player.getWorld();
		location = player.getLocation();
	
		try
		{
		
			 world.setSpawnLocation(location);
			 
			 message = _config.Messages.TeleportSystem + _config.Messages.TeleportSystem.SpawnUpdated;
			 
			_logger.SendToSender(Module.TeleportSystem, MessageType.Info, sender, message);
		}
		catch(Exception e)
		{
			throw new Exception(_config.Messages.TeleportSystem + "World.SetSpawnLocation Error\n" + e.getMessage());
		}	
	}	
}


public void TeleportToWorld(CommandSender sender, String string) {
	// TODO Auto-generated method stub
	
}


public void TeleportToPlayer(CommandSender sender, String string) {
	// TODO Auto-generated method stub
	
}
}
