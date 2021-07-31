package de.SSC.CoreManager.Region;

import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.SSC.CoreManager.Chat.Logger;
import de.SSC.CoreManager.Chat.MessageType;
import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.DataBase.DataBaseSystem;
import de.SSC.CoreManager.System.BaseSystem;
import de.SSC.CoreManager.System.ISystem;
import de.SSC.CoreManager.System.SystemPriority;
import de.SSC.CoreManager.System.SystemState;
import de.SSC.CoreManager.System.Exception.SystemNotActiveException;
import de.SSC.CoreManager.World.CMWorld;
import de.SSC.CoreManager.World.Exception.InvalidWorldNameException;

public class RegionSystem extends BaseSystem implements ISystem
{
	private static RegionSystem _instance;
	private RegionList _regionList;
	
	private Logger _logger;
	private DataBaseSystem _dataBaseSystem;	
	
	private RegionSystem()
	{
		super(Module.RegionSystem, SystemState.Active, SystemPriority.High);
		_instance = this;
	}
	
	public static RegionSystem Instance()
	{
		return _instance == null ? new RegionSystem() : _instance;
	}
	
	@Override
	public void LoadReferences() 
	{
		_regionList = RegionList.Instance();		
		_dataBaseSystem = DataBaseSystem.Instance();
		_logger = Logger.Instance();	
	}

	@Override
	public void Reload(final boolean firstRun) throws SystemNotActiveException 
	{
		List<CMRegion> regions;
		_regionList.Clear();
		
		if(!Information.IsActive())
		{
			throw new SystemNotActiveException();
		}
		
		regions = _dataBaseSystem.Region.LoadAllRegions();	
		_regionList.ReloadWorldData();
		
		for(CMRegion cmRegion : regions)
		{
			_regionList.Add(cmRegion);			
		}
	}
	
	@Override
	public void PrintData(CommandSender sender) 
	{
		
	}
	
	public void ListAllRegions(CommandSender sender)
	{
		 final boolean isSenderPlayer = sender instanceof Player;
		final Map<CMWorld, List<CMRegion>> regions = _regionList.GetAllRegions();
		String message = ""; 
		
		if(!isSenderPlayer)
		{
			message += "\n";
		}
		
		message += "&6===[&eRegions&6]==========\n";

		for(Map.Entry<CMWorld, List<CMRegion>> entry : regions.entrySet()) 
		{
			CMWorld cmWorld = entry.getKey();
			List<CMRegion> cmRegions = entry.getValue();
			
			message += " &6World &7: &r" + cmWorld.Information.CustomName + " &7with &6ID &7: &e" + cmWorld.Information.ID + "\n";
			
			if(!cmRegions.isEmpty())
			{				
				for(CMRegion cmRegion : cmRegions)
				{
					message += " &eID &7: &e" + cmRegion.ID + " &6Name &e" + cmRegion.Name + "&7, ";
				}		
				
				message += "\n";
			}	
			else
			{
				message += " &cNo Regions in this world.\n";
			}
		}
		
		message += "&6======================";
		
		_logger.SendToSender(Module.System, MessageType.None, sender, message);
	}

	public CMRegion TryGetRegionAt(Block block) throws InvalidWorldNameException
	{
		return _regionList.IsRegionInLocation(block);
	}

	public boolean IsBlockProtected(Block block) throws InvalidWorldNameException
	{
		CMRegion cmRegion = TryGetRegionAt(block);
		
		return cmRegion != null;
	}
	
	public void OnEntityExplode(EntityExplodeEvent event) throws InvalidWorldNameException 
	{
		final Entity entity = event.getEntity();
		final EntityType entityType = event.getEntityType();
		final boolean ifEntityIsCreeper = entity instanceof Creeper;
		final boolean ifEntityIsTNT = entityType == EntityType.PRIMED_TNT;
		final boolean isEnityEnderCrystal = entityType == EntityType.ENDER_CRYSTAL;
		final boolean isEnityWither = entityType == EntityType.WITHER;
		final boolean isEntityTNTCart = entityType == EntityType.MINECART_TNT;
		final boolean shouldBePrevented = ifEntityIsCreeper || ifEntityIsTNT || isEnityEnderCrystal || isEnityWither || isEntityTNTCart;
		
		if (shouldBePrevented) 
		{
			for (Block block : event.blockList().toArray(new Block[event.blockList().size()]))
			{  
				if(IsBlockProtected(block))
				{
					event.setCancelled(true);
	            }
			}
	    } 
	}
	
	public void OnInteract(PlayerInteractEvent event) throws InvalidWorldNameException
	{		 
		final Action currentAction = event.getAction();
		final Action wantedAction = Action.RIGHT_CLICK_BLOCK;
		final Material wantedMaterial = Material.LEATHER;
		final boolean isCorrectAction = currentAction == wantedAction;
		final Player player = event.getPlayer();	
		final Block block = event.getClickedBlock();
		boolean correctMaterial;		
		ItemStack itemStack;	
		CMRegion cmRegion;
		
		
		if(isCorrectAction)
		{				
			itemStack = player.getInventory().getItemInMainHand();
			correctMaterial = wantedMaterial == itemStack.getType();
			
			if(correctMaterial)
			{
				cmRegion = TryGetRegionAt(block);
				
				if(cmRegion == null)
				{
					_logger.SendToSender(Module.RegionSystem, MessageType.Info, player, "&7No region in this world.");
				}
				else
				{
					
					_logger.SendToSender(Module.RegionSystem, MessageType.Info, player, "&7Region found.");
					
					//cmRegion.PrintCoordinates(player);
				}					
			}
		}
	}
}
