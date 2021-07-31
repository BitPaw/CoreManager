package de.BitFire.Permission;

import java.util.List;
import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Core.Exception.SystemNotActiveException;
import de.BitFire.DataBase.DataBaseSystem;
import de.BitFire.Player.CMPlayer;
import de.BitFire.Player.PlayerSystem;
import de.BitFire.Player.Exception.BukkitPlayerMissingException;
import de.BitFire.Player.Exception.CMPlayerIsNullException;
import de.BitFire.Player.Exception.InvalidPlayerUUID;
import de.BitFire.Player.Exception.PlayerNotFoundException;
import de.BitFire.Rank.CMRank;
import de.BitFire.Rank.RankSystem;
import de.BitFire.Rank.Exception.RankNotFoundException;
import de.BitFire.World.Exception.InvalidWorldNameException;
import de.BitFire.World.Region.CMRegion;
import de.BitFire.World.Region.RegionSystem;

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
		super(Module.PermissionSystem, SystemState.Active, Priority.High);    
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
			CMRank cmRank;
			try 
			{
				cmRank = _rankSystem.GetRank(cmPermission.RankID);
				
				cmRank.Permission.Add(cmPermission);
			} 
			catch (RankNotFoundException e) 
			{
				String message = "&cCant add permission to rank, rank not found.";
				_logger.SendToConsole(Information.GetSystemModule(), MessageType.Error, message);
				e.printStackTrace();
			}			
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
	
	
	  public boolean HasPlayerPermission(Player player, String permission) throws PlayerNotFoundException, InvalidPlayerUUID, CMPlayerIsNullException, BukkitPlayerMissingException
	  {
		  final UUID playerUUID = player.getUniqueId();
		  final CMPlayer cmPlayer =  _playerSystem.GetPlayer(playerUUID);
		  
		  return HasPlayerPermission(cmPlayer, permission);
	  }
	
	  public boolean HasPlayerPermission(CMPlayer cmPlayer, String permissionName) throws CMPlayerIsNullException, BukkitPlayerMissingException
	  {
		  if(cmPlayer == null)
		  {
			  throw new CMPlayerIsNullException();
		  }
		  
		  if(cmPlayer.BukkitPlayer == null)
		  {
			  throw new BukkitPlayerMissingException();
		  }
		  
		  final boolean isPlayerOP = cmPlayer.BukkitPlayer.isOp();
		  final boolean isPlayerInGame = cmPlayer.IsPlayerInGame();
		  
		  
		  if(isPlayerInGame)
		  {
			  return false;
		  }
		  
		  if(isPlayerOP)
		  {
			  return true;
		  }
		
		  	
		  //cmPlayer.RankGroup.Permission.HasPermission(permissionName)
		  
		  return cmPlayer.BukkitPlayer.hasPermission(permissionName);	
	  }
	  
	  public boolean HasPlayerPermissionToModifylock(final Player player, final Block block) throws PlayerNotFoundException, InvalidPlayerUUID, InvalidWorldNameException, CMPlayerIsNullException, BukkitPlayerMissingException
	  {		
		  final UUID playerUUID = player.getUniqueId();		
		  final CMPlayer cmPlayer = _playerSystem.GetPlayer(playerUUID);	
		  final boolean hasPermissionToBuild = HasPlayerPermission(cmPlayer, "CoreManager.Build");		  
		  boolean canPlayerModifyBlock = false;
		  
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
		  
		  return canPlayerModifyBlock;
	  }
}