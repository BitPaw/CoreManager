package de.SSC.CoreManager.Systems.Permission;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Systems.Chat.Logger;
import de.SSC.CoreManager.Systems.Chat.MessageType;
import de.SSC.CoreManager.Systems.Chat.Module;
import de.SSC.CoreManager.Systems.DataBase.DataBaseSystem;
import de.SSC.CoreManager.Systems.Player.CMPlayer;
import de.SSC.CoreManager.Systems.Player.PlayerSystem;
import de.SSC.CoreManager.Systems.Region.RegionLocationInfromation;
import de.SSC.CoreManager.Systems.Region.RegionSystem;
import de.SSC.CoreManager.Utility.BukkitUtility;

public class PermissionSystem
{
	private static PermissionSystem _instance;
	private List<CMPermission> _permissions;
	
	//private RankSystem _rankSystem;
	private DataBaseSystem _dataBaseSystem;
	private Logger _logger;
	private BukkitUtility _bukkitUtility;
	private RegionSystem _regionSystem;
	private PlayerSystem _playerSystem;
	
	private PermissionSystem()
	{
		_instance = this;
		
		_permissions = new ArrayList<CMPermission>();
		
		//_rankSystem = RankSystem.Instance();
		_dataBaseSystem = DataBaseSystem.Instance();
		_logger = Logger.Instance();
		_bukkitUtility = BukkitUtility.Instance();
		_regionSystem = RegionSystem.Instance();
		_playerSystem = PlayerSystem.Instance();
	}
	
	public static PermissionSystem Instance() 
	{
		return _instance == null ?  new PermissionSystem() : _instance;
	}

	public void AddPermission(CMPermission cmPermission)
	{
		_permissions.add(cmPermission);
	}
	
	public void DeletePermission(CMPermission cmPermission)
	{
		_permissions.remove(cmPermission);		
	}
	
	public void ReloadPermissions()
	{
		_permissions = _dataBaseSystem.LoadAllPermissions();
	}
		
	
	  public boolean HasPlayerPermission(Player player, String permission)
	  {
		  CMPlayer cmPlayer =  _playerSystem.GetPlayer(player);
		  
		  return HasPlayerPermission(cmPlayer, permission);
	  }
	
	  public boolean HasPlayerPermission(CMPlayer player, String permission)
	  {
		  boolean hasPlayerPermission;
		  
		  if(player.BukkitPlayer.isOp())
		  {
			  hasPlayerPermission = true;
		  }
		  else
		  {
			  hasPlayerPermission = false;
			  
			  for(CMPermission cmPermission : _permissions)
			  {
				  if(cmPermission.RankGroup == player.RankGroup)
				  {
					  if(cmPermission.PermissionName.equalsIgnoreCase(permission))
					  {
						  hasPlayerPermission = true;
					  }
				  }			
			  } 
		  }	
		  
		  return hasPlayerPermission;
	  }
	  
	  public void ListPermissions(CommandSender sender)
	  {
		  boolean isSenderConsole = !_bukkitUtility.PlayerUtility.IsSenderPlayer(sender);
		  String message = "";
		  
		  if(isSenderConsole)
		  {
			  message += "\n";
		  }
		  
		  message += "&6===[Permissions]==========\n";
		  
		  for(CMPermission cmPermission: _permissions)
		  {
			  message += "&7 " + cmPermission.PermissionName + " &8for &7" + cmPermission.RankGroup.ColorTag +  " \n";
		  }
		  
		  message += "&6==========================";
		  
		  _logger.SendToSender(Module.RegionSystem, MessageType.None, sender, message);
	  }
	  
	  public boolean HasPlayerPermissionToBreakBlock(Player player)
	  {
		  boolean hasPermission;
		  boolean isInRegion;
		  RegionLocationInfromation regionLocationInfromation = _regionSystem.IsPlayerInRegion(player);
		  CMPlayer cmPlayer = _playerSystem.GetPlayer(player);
		
		  // Can player place and remove blocks
		  hasPermission = HasPlayerPermission(cmPlayer, "CoreManager.Build");
		  
		 // If the player is in a protected area, is he allowed to modify it?
		  
		  if(hasPermission)
		  {
				if(regionLocationInfromation == null)
				{
					 isInRegion = false;
				}
				else
				{
					 isInRegion = regionLocationInfromation.IsInRegion();
				}
			  
				if(isInRegion)
				{
					 
					hasPermission = HasPlayerPermission(cmPlayer, "CoreManager.ModifyProtectedArea");		
					
					  _logger.SendToConsole(Module.RegionSystem, MessageType.None,  "Check if region i accesable-");
				} 
				else
				{
					  _logger.SendToConsole(Module.RegionSystem, MessageType.None,  "No region");
					hasPermission = true;
				}
		  }
		  
		  return hasPermission;
	  }
}