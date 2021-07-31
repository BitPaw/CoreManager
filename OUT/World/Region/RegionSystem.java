package de.BitFire.World.Region;

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

import de.BitFire.API.Bukkit.BukkitAPIEntity;
import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Core.Exception.SystemNotActiveException;
import de.BitFire.DataBase.DataBaseSystem;
import de.BitFire.Geometry.IntegerPoint;
import de.BitFire.Sound.SoundSystem;
import de.BitFire.World.CMWorld;
import de.BitFire.World.Exception.InvalidWorldNameException;

public class RegionSystem extends BaseSystem implements ISystem
{
	private static RegionSystem _instance;
	private RegionList _regionList;
	
	private Logger _logger;
	private DataBaseSystem _dataBaseSystem;	
	private SoundSystem _soundSystem;
	
	private RegionSystem()
	{
		super(Module.RegionSystem, SystemState.Active, Priority.High);
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
		_soundSystem = SoundSystem.Instance();
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
		final boolean shouldBePrevented = BukkitAPIEntity.IsExplosive(entityType) || ifEntityIsCreeper;
				
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
		
		if(isCorrectAction)
		{				
			final ItemStack itemStack = player.getInventory().getItemInMainHand();
			final boolean correctMaterial = wantedMaterial == itemStack.getType();
			
			if(correctMaterial)
			{
				final CMRegion cmRegion = TryGetRegionAt(block);
				
				PrintRegionInfromation(player, block, cmRegion);				
			}
		}
	}
	
	public void PrintRegionInfromation(final Player player, final Block block, final CMRegion cmRegion)
	{		
		final IntegerPoint p = new IntegerPoint(block.getLocation());
		final String formatter = "%04d";
		String message = "";	
		
		if(cmRegion == null)
		{
			message += "&7This region is &cnot &7protected.\n";
		
			message += "&7[&cX&7] &7T&8:&e" + String.format(formatter, p.X) + "\n";	
			message += "&7[&aY&7] &7T&8:&e" + String.format(formatter, p.Y) + "\n";			
			message += "&7[&bZ&7] &7T&8:&e" + String.format(formatter, p.Z);
			
			_soundSystem.PlaySoundFalse(player);
		}
		else
		{			
			final IntegerPoint a = new IntegerPoint(cmRegion.RegionField.PointA);
			final IntegerPoint b = new IntegerPoint(cmRegion.RegionField.PointB);
			
			message += "&7This region &ais &7protected. Name &6<&e" + cmRegion.Name + "&6>&7.\n";
			
			message += "&7[&cX&7] &7A&8:&e" + String.format(formatter, a.X) + " &7B&8:&e"  +  String.format(formatter, b.X) + " &7T&8:&e" +  String.format(formatter, p.X) + "\n";
			
			if(cmRegion.RegionField.InfinityHeight)
			{
				message += "&7[&aY&7] &7Ignoring height" + "\n";
			}
			else
			{
				message += "&7[&aY&7] &7A&8:&e" +  String.format(formatter, a.Y) + " &7B&8:&e"  +  String.format(formatter, b.Y) + " &7T&8:&e" + String.format(formatter, p.Y) + "\n";
			}
				
			message += "&7[&bZ&7] &7A&8:&e" + String.format(formatter, a.Z) + " &7B&8:&e"  + String.format(formatter, b.Z) + " &7T&8:&e" +  String.format(formatter, p.Z);
		
			_soundSystem.PlaySoundTrue(player);
		}	
		 
		_logger.SendToSender(Information.GetSystemModule(), MessageType.None, player, message);
	}
}