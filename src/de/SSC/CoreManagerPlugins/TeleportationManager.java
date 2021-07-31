package de.SSC.CoreManagerPlugins;

import de.SSC.CoreManager.Config.Messages;
import de.SSC.CoreManager.CoreController;
import de.SSC.CoreManager.Main;
import de.SSC.CoreManager.Multiverse.CoreManagerWorld;
import de.SSC.CoreManager.Multiverse.WorldManager;
import de.SSC.CoreManager.Teleport.Warp;
import de.SSC.CoreManager.Utility.CoreManagerPlugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class TeleportationManager extends CoreManagerPlugin
{
	// Variables
	private List<Warp> _warps = null;
	private boolean _hasChanged = false;

	// Referenzes
	private BukkitHook _bukkitHook;
	private WorldManager _worldManager;
	private DataBaseController _dataBaseController;
	private Messages _messages;

	public TeleportationManager()
	{
		_warps = new ArrayList<Warp>();
		_hasChanged = true;

		CoreController coreController = Main.coreController;
		_bukkitHook = coreController._BukkitHook;
		_worldManager = coreController._WorldManager;
		_dataBaseController = coreController._DataBaseController;
		_messages = Main.messages;
	}

	public void Warp(CommandSender sender, String[] parameter)
	{
		if(_hasChanged)
		{
			ReloadWarps();			
		}
		
		Player player = (Player)sender;
		String warpName = parameter[0];
		Warp warp = Warp.GetWarpPerName(warpName, _warps);

		if(warp == null) 
		{
			_bukkitHook.SendMessage(sender, _messages.Error + "No warp with this name.");
		}
		else
		{
			TeleportToWarp(player, warp);
		}
	}

	public void SetWarp(CommandSender sender, String[] parameter)
	{			
		String warpName = parameter[0];

		if(_bukkitHook.IsConsole(sender))
		{			
			_bukkitHook.SendMessage(sender, _messages.Error + _messages.NotForConsole);
		}
		else
		{
			Player player = (Player)sender;	

			if(warpName.isEmpty())
			{
				_bukkitHook.SendMessage(player, "No parameter, try again");		
			}	
			else
			{
				Warp warp = new Warp(player, warpName);	
				SetWarpPoint(warp);

				String message = _messages.TeleportSystem + " " + _messages.WarpCreated;
				message = message.replace(_messages.TagWorld, warpName);
				_bukkitHook.SendMessage(sender, message);

				_hasChanged = true;
			}				

		}
	}

	public void DeleteWarp(CommandSender sender, String[] parameter)
	{			
		String warpName = parameter[0];

		String message = _messages.TeleportSystem + " " + _messages.WarpDeleted;
		message = message.replace(_messages.TagWorld, warpName);
		_bukkitHook.SendMessage(sender, message);

		RemoveWarpPoint(warpName);
	}

	public void ReloadWarps()
	{
		try
		{
			_warps = _dataBaseController.LoadAllWarps();
			_hasChanged = false;
		}
		catch(Exception e)
		{
			_bukkitHook.SendConsolMessage(_messages.TeleportSystem + " Error while reloading Warps!\n" + e.getMessage());
		}

		if(_warps == null)
		{
			_warps = new ArrayList<Warp>();
			_bukkitHook.SendConsolMessage(_messages.TeleportSystem + " Error while reloading Warps! The Dataset is empty!");
		}
	}

	public void SetSpawn(CommandSender sender) throws Exception
	{
		if(_bukkitHook.CheckIfCommandSenderIsPlayer(sender))
		{
			Player player = (Player)sender;
			World world = player.getWorld();
			Location location = player.getLocation();
		
			try
			{
				world.setSpawnLocation(location);
				_bukkitHook.SendMessage(player, _messages.TeleportSystem + " " + _messages.SpawnUpdated);
			}
			catch(Exception e)
			{
				throw new Exception(_messages.TeleportSystem + "World.SetSpawnLocation Error\n" + e.getMessage());
			}	
		}
	}
	
	public void Teleport(Player player, Location location)
	{
		_bukkitHook.SendMessage(player, _messages.TeleportSystem + " " + _messages.Teleporting);

		try
		{
			player.teleport(location);
		}
		catch(Exception e)
		{
			_bukkitHook.SendMessage(player, _messages.TeleportSystem + " " + _messages.CouldNotTeleport + "\n" + e.getMessage());
		}		
	}

	public void SetWarpPoint(Warp warp)
	{
		try
		{
			_dataBaseController.SendCommand(_dataBaseController.SQLMSGBuilder.SaveWarp(warp));
			_hasChanged = true;
		}
		catch(Exception e)
		{

		}	
	}	

	public void RemoveWarpPoint(String warpName)
	{
		try
		{
			_dataBaseController.SendCommand(_dataBaseController.SQLMSGBuilder.DeleteWarp(warpName));
			_hasChanged = true;
		}
		catch(Exception e)
		{

		}	
	}

	public void PrintAllWarps(CommandSender sender)
	{
		if(_hasChanged)
		{
			ReloadWarps();			
		}

		String message = _messages.TeleportSystem + " &6Total warps <&e" + _warps.size() + "&6> : ";

		for(Warp warp : _warps)
		{
			message += "&e" + warp.WarpName + "&6, ";
		}	

		_bukkitHook.SendMessage(sender, message);
	}

	public void TeleportToPlayer(CommandSender sender, String toPlayer)
	{
		if(_bukkitHook.CheckIfCommandSenderIsPlayer(sender))
		{
			Player player = (Player)sender;
			Player targetPlayer = _bukkitHook.FindPlayer(toPlayer);

			if(targetPlayer == null)
			{
				_bukkitHook.SendMessage(player, _messages.TeleportSystem + "&7 Could &cnot &7find player <&e" + toPlayer + "&7>.");
			}
			else
			{
				if(player.equals(targetPlayer))
				{
					_bukkitHook.SendMessage(player, _messages.TeleportSystem + " &7You want to teleport to &6yourself&7? &8Why..?");
				}
				else
				{
					Teleport(player, targetPlayer.getLocation());
				}
			}
		}
	}

	public void TeleportToWorld(CommandSender sender, String worldName)
	{
		if(_bukkitHook.CheckIfCommandSenderIsPlayer(sender))
		{	
			Player player = (Player)sender;
			CoreManagerWorld coreManagerWorld = _worldManager.GetWorld(worldName);
			
			if(coreManagerWorld == null)
			{
				String message = _messages.MultiverseSystem + _messages.WorldDoesntExist;
				message = message.replace(_messages.TagWorld, worldName);
				_bukkitHook.SendMessage(sender, message);
			}
			else
			{
				if(coreManagerWorld.IsLoaded())
				{
					Location location = coreManagerWorld.BukkitWorld.getSpawnLocation();

					Teleport(player, location);
				}
				else
				{
					_bukkitHook.SendMessage(sender, "This world isnt loaded yet!");
				}

			}				
		}
	}
	
	public void TeleportToWorldSpawn(CommandSender sender) throws Exception
	{		
		if(_bukkitHook.CheckIfCommandSenderIsPlayer(sender))
		{			
			try
			{
				Player player = (Player)sender;
				World world = player.getWorld();
				Location location = world.getSpawnLocation();
				
				Teleport(player, location);
			}
			catch(Exception e)
			{
				throw new Exception("TeleportToWorldSpawn Error\n" + e.getMessage());
			}						
		}			
	}

	public void TeleportToWarp(Player player, Warp warp)
	{
		Teleport(player, warp.WarpLocation);	
	}

	public Warp GetWarpPerName(String name)
	{
		Warp returnWarp = null;

		for(Warp warp : _warps)
		{
			if(warp.WarpName.equalsIgnoreCase(name))
			{
				returnWarp = warp;
			}
		}

		return returnWarp;
	}
}