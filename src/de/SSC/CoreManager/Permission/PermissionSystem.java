package de.SSC.CoreManager.Permission;

import java.util.List;
import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Chat.Logger;
import de.SSC.CoreManager.Chat.MessageType;
import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.DataBase.DataBaseSystem;
import de.SSC.CoreManager.Player.CMPlayer;
import de.SSC.CoreManager.Player.PlayerSystem;
import de.SSC.CoreManager.Player.Exception.InvalidPlayerUUID;
import de.SSC.CoreManager.Player.Exception.PlayerNotFoundException;
import de.SSC.CoreManager.Rank.CMRank;
import de.SSC.CoreManager.Rank.RankSystem;
import de.SSC.CoreManager.Region.CMRegion;
import de.SSC.CoreManager.Region.RegionSystem;
import de.SSC.CoreManager.System.BaseSystem;
import de.SSC.CoreManager.System.ISystem;
import de.SSC.CoreManager.System.SystemPriority;
import de.SSC.CoreManager.System.SystemState;
import de.SSC.CoreManager.System.Exception.SystemNotActiveException;
import de.SSC.CoreManager.World.Exception.InvalidWorldNameException;

public class PermissionSystem extends BaseSystem implements ISystem
{
	private static PermissionSystem _instance;
	@SuppressWarnings("unused")
	private PermissionList _permissionList;
	
	private RankSystem _rankSystem;
	private DataBaseSystem _dataBaseSystem;
	private Logger _logger;
	private RegionSystem _regionSystem;
	private PlayerSystem _playerSystem;
	
	private PermissionSystem()
	{
		super(Module.PermissionSystem, SystemState.Active, SystemPriority.High);    
		_instance = this;
		
		_permissionList = new PermissionList();
	}
	
	@Override
	public void LoadReferences() 
	{
		_dataBaseSystem = DataBaseSystem.Instance();
		_logger = Logger.Instance();
		_regionSystem = RegionSystem.Instance();
		_playerSystem = PlayerSystem.Instance();		
		_rankSystem = RankSystem.Instance();
	}

	@Override
	public void Reload(final boolean firstRun) throws SystemNotActiveException 
	{
		List<CMPermission> permissions;
		_rankSystem.ClearPermission();
		
		if(!Information.IsActive())
		{
			throw new SystemNotActiveException();
		}
		
		permissions = _dataBaseSystem.Permission.LoadAllPermissions();
		
		for(CMPermission cmPermission : permissions)
		{
			CMRank cmRank = _rankSystem.GetRank(cmPermission.RankID);
			
			cmRank.Permission.Add(cmPermission);
		}		
	}	
	
	@Override
	public void PrintData(CommandSender sender) 
	{
		boolean isSenderConsole = !(sender instanceof Player);
		String message = "";
		  
		if(isSenderConsole)
		{
			message += "\n";
		}
		  
		message += "&6===[Permissions]==========\n";
		  
		/*
		for(CMPermission cmPermission: _permissions)
		{ 
			  
			message += "&7 " + cmPermission.Name + " &8for &7" + cmPermission.RankGroup.Tag +  " \n";
		}
		*/
		  
		message += "&6==========================";
		  
		_logger.SendToSender(Module.RegionSystem, MessageType.None, sender, message);
	}

	public static PermissionSystem Instance() 
	{
		return _instance == null ?  new PermissionSystem() : _instance;
	}
	
	
	  public boolean HasPlayerPermission(Player player, String permission) throws PlayerNotFoundException, InvalidPlayerUUID
	  {
		  UUID playerUUID = player.getUniqueId();
		  CMPlayer cmPlayer =  _playerSystem.GetPlayer(playerUUID);
		  
		  return HasPlayerPermission(cmPlayer, permission);
	  }
	
	  public boolean HasPlayerPermission(CMPlayer cmPlayer, String permissionName)
	  {
		  final boolean isPlayerOP = cmPlayer.BukkitPlayer.isOp();
		  
		  if(isPlayerOP)
		  {
			  return true;
		  }
		  	
		  //cmPlayer.RankGroup.Permission.HasPermission(permissionName)
		  
		  return cmPlayer.BukkitPlayer.hasPermission(permissionName);	
	  }
	  
	  public boolean HasPlayerPermissionToModifylock(final Player player, final Block block) throws PlayerNotFoundException, InvalidPlayerUUID, InvalidWorldNameException
	  {		
		  final UUID playerUUID = player.getUniqueId();		
		  final CMPlayer cmPlayer = _playerSystem.GetPlayer(playerUUID);	
		  final boolean hasPermissionToBuild = HasPlayerPermission(cmPlayer, "CoreManager.Build");		  
		  boolean canPlayerModifyBlock;
		  
		  // Check if the player is generally alowed to build
		  if(hasPermissionToBuild)
		  {	
			  final CMRegion cmRegion = _regionSystem.TryGetRegionAt(block);
			  
			  if(cmRegion == null)
			  {
				  // Player is not in a region, so he is allowed to build.
				  canPlayerModifyBlock = true;
			  }				
			  else
			  {							  
				  // There is a region, Check for any additional permission.			
				  if(cmRegion.Information.PermissionToBuild)
				  {
						// Check if the player has the needed permission to modify protected ground.
						canPlayerModifyBlock = HasPlayerPermission(cmPlayer, "CoreManager.ModifyProtectedArea");	
				  }	
				  else
				  {
						// There is not a special permission needed
						canPlayerModifyBlock = true;
				  }				  
			  }
		  }
		  else
		  {
			  // Player can not modify block in any way.
			  canPlayerModifyBlock = false;
		  }
		  
		  return canPlayerModifyBlock;
	  }
}