package de.SSC.CoreManager.Systems.Region;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.SSC.CoreManager.Systems.Chat.Logger;
import de.SSC.CoreManager.Systems.Chat.MessageType;
import de.SSC.CoreManager.Systems.Chat.Module;
import de.SSC.CoreManager.Systems.DataBase.DataBaseSystem;
import de.SSC.CoreManager.Systems.Permission.PermissionSystem;
import de.SSC.CoreManager.Systems.Player.CMPlayer;
import de.SSC.CoreManager.Systems.Player.PlayerSystem;
import de.SSC.CoreManager.Utility.BukkitUtility;

public class RegionSystem 
{
	boolean Debug = false;
	private static RegionSystem _instance;
	private List<CMRegion> _regions;
	
	private PlayerSystem _playerSystem;
	private DataBaseSystem _dataBaseSystem;
	private Logger _logger;
	private BukkitUtility _bukkitUtility; 
	private RegionSystem()
	{
		_regions = new ArrayList<CMRegion>();
		
		_playerSystem = PlayerSystem.Instance();
		_dataBaseSystem = DataBaseSystem.Instance();
		_logger = Logger.Instance();
		_bukkitUtility = BukkitUtility.Instance();
		PermissionSystem.Instance();
	}
	
	public static RegionSystem Instance()
	{
		return _instance == null ? new RegionSystem() : _instance;
	}
	
	public void AddRegion(CMRegion cmRegion)
	{
		_regions.add(cmRegion);
	}
	
	public void ReloadRegions()
	{
		_regions = _dataBaseSystem.LoadAllRegions();
	}
	
	public RegionLocationInfromation IsPlayerInRegion(Player player)
	{
		RegionLocationInfromation regionLocationInfromation = null;	
		World currentWorld = player.getWorld();
		
		if(Debug)
		{
			_logger.SendToConsole(Module.RegionSystem, MessageType.Info, "Looking for saved location...");
		}
		
		// Get regions in this world
		for(CMRegion cmRegion : _regions)
		{	
			if(Debug)
			{
				_logger.SendToConsole(Module.RegionSystem, MessageType.Question, "is Region <" + cmRegion.RegionName + "> in world <" + currentWorld.getName() + ">?");
			}
			
			if(cmRegion.Location.LocationA.getWorld() == currentWorld)
			{
				 regionLocationInfromation = new RegionLocationInfromation(player, cmRegion.Location.LocationA, cmRegion.Location.LocationB, cmRegion.IgnoreHight);
			
				 if(regionLocationInfromation.IsInRegion())
				 {
					 break;
				 }
			}
		}			
		
		return regionLocationInfromation;
	}	

		
	public boolean IsPlayerAllowedToModifyTheRegion(CMPlayer cmPlayer)
	{
		boolean isPlayerAllowedToModifyTheRegion;
		
		isPlayerAllowedToModifyTheRegion = true;	
		
		return isPlayerAllowedToModifyTheRegion;
	}
	
	public boolean IsPlayerAllowedToModifyTheRegion(Player player)
	{
		boolean isPlayerAllowedToModifyTheRegion;
		CMPlayer cmPlayer = _playerSystem.GetPlayer(player);		
		
		isPlayerAllowedToModifyTheRegion = IsPlayerAllowedToModifyTheRegion(cmPlayer);	
		
		return isPlayerAllowedToModifyTheRegion;
	}
	
	public boolean IsPlayerAllowedToAccessTheRegion()
	{
		boolean isPlayerAllowedToAccessTheRegion;
		
		isPlayerAllowedToAccessTheRegion = true;	
		
		return isPlayerAllowedToAccessTheRegion;
	}

	public void ListAllRegions(CommandSender sender)
	{
		boolean isSenderConsole = !_bukkitUtility.PlayerUtility.IsSenderPlayer(sender);
		String message = ""; 
		
		if(isSenderConsole)
		{
			message += "\n";
		}
		
		message += "&6===[&eRegions&6]==========\n";

		for(CMRegion cmRegion : _regions)
		{
			String worldName;
			
			if(cmRegion.IsActive)
			{
				 worldName = cmRegion.Location.LocationA.getWorld().getName();
			}
			else
			{
				 worldName = "Unloaded";				 
			}	
			
			message +=  " &eName&6:&e " + cmRegion.RegionName + " &7- &eWorld&6:&e <" + worldName  + "> \n";
		}
		
		message += "&6======================";
		
		_logger.SendToSender(Module.System, MessageType.None, sender, message);
	}
	
	public void OnInteract(PlayerInteractEvent event)
	{
		Action currentAction = event.getAction();
		Action wantedAction = Action.RIGHT_CLICK_BLOCK;
		Player player = event.getPlayer();		
		ItemStack itemStack = player.getInventory().getItemInMainHand();
		Material wantedMaterial = Material.LEATHER;
		Material cuurentMaterial = itemStack.getType();
		RegionLocationInfromation regionLocationInfromation;
		regionLocationInfromation = IsPlayerInRegion(player);		
		
		if((currentAction == wantedAction) && (wantedMaterial == cuurentMaterial))
		{			
			if(regionLocationInfromation == null)
			{
				_logger.SendToSender(Module.RegionSystem, MessageType.Warning, player, "&7No region in this world.");
			}
			else
			{
				regionLocationInfromation.PrintCoordinates(player);
			}		
		}
	}
}
