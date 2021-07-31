package de.BitFire.World.Manipulation;

import java.text.DecimalFormat;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.BitFire.API.CommandCredentials;
import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Core.Exception.NotForConsoleException;
import de.BitFire.Core.Exception.TooFewParameterException;
import de.BitFire.Core.Exception.TooManyParameterException;
import de.BitFire.Geometry.Cube;
import de.BitFire.Geometry.Point;
import de.BitFire.World.Manipulation.Exception.NoRegionMarkedException;
import de.BitFire.World.Manipulation.Exception.PositionAMissingException;
import de.BitFire.World.Manipulation.Exception.PositionBMissingException;

public class WorldEditSystem extends BaseSystem implements ISystem
{
	private static WorldEditSystem _instance;
	private WordEditUserInfromationList _userList;
	
	private final DecimalFormat decimalFormat = new DecimalFormat("####");	
	
	private Logger _logger;
	
	public WorldEditSystem() 
	{
		super(Module.WorldEdit, SystemState.Active, Priority.High);
		_instance = this;
		
		_userList = new WordEditUserInfromationList();
		_logger = Logger.Instance();
	}
	
	public static WorldEditSystem Instance()
	{
		return _instance == null ? new WorldEditSystem() : _instance;
	}

	@Override
	public void Command(CommandCredentials commandCredentials) throws NotForConsoleException,
			 TooFewParameterException, TooManyParameterException 
	{
		
	}	
	
	public void SetPosition(final UUID playerUUID, final Point point, PositionType positionType)
	{
		final boolean isRegistered = _userList.IsRegistered(playerUUID);
		
		if(isRegistered)
		{
			Cube cube = _userList.GetCube(playerUUID);
						
			switch(positionType)
			{
			case A:
				cube.PointA = point;
				break;
				
			case B:
				cube.PointB = point;
				break;
			
			}
			
			_userList.Update(playerUUID, cube);
		}
		else
		{
			final Cube cube = new Cube(point, positionType);
						
			_userList.Add(playerUUID, cube);
		}		
	}
	
	public void PrintPositions(final Player player, PositionType positionType)
	{
		final UUID playerUUID = player.getUniqueId();
		final Cube cube = _userList.GetCube(playerUUID);
		final Point a = cube.PointA;
		final Point b = cube.PointB;
		String message = "&5";
		final String notSet = "&dnot set";
		
		switch(positionType)
		{
		case A:
			
			message += "Pos1&7: ";	 
			
			if(a == null)
			{
				message += notSet;
			}		
			else
			{
				message += "&cX&8:&7" + decimalFormat.format(a.X) + 
						   " &aY&8:&7" + decimalFormat.format(a.Y) + 
						   " &bZ&8:&7" + decimalFormat.format(a.Z);			
			}
			break;
			
		case B:
			message += "Pos2&7: ";
			
			if(b == null)
			{
				message += notSet;
			}
			else
			{
				message += "&cX&8:&7" + decimalFormat.format(b.X) + 
						   " &aY&8:&7" + decimalFormat.format(b.Y) + 
						   " &bZ&8:&7" + decimalFormat.format(b.Z);	
			}
			break;
			
			
			
		default:
			break;		
		}
		
		if(a != null && b != null)
		{					
			final String volumenTag = decimalFormat.format(cube.GetVolumen());
			message += " &8[&dV&7=&d" + volumenTag + "&8]";
		}
		
		_logger.SendToSender(Module.WorldEdit, MessageType.None, player, message);
	}
	
	public Cube GetCube(final UUID playerUUID) throws  PositionAMissingException, PositionBMissingException, NoRegionMarkedException
	{
		final boolean doesPlayerExist = _userList.IsRegistered(playerUUID);
		Cube cube;
		
		if(!doesPlayerExist)
		{
			throw new NoRegionMarkedException();
		}
		
		cube = _userList.GetCube(playerUUID);
		
		if(!cube.IsPositionASet())
		{
			throw new PositionAMissingException();
		}
		
		if(!cube.IsPositionBSet())
		{
			throw new PositionBMissingException();
		}	
		
		System.out.print("sdgsfdsf");
		
		return cube;
	}
	
	public void Interact(final PlayerInteractEvent event) 
	{
		final Player player = event.getPlayer();
		final UUID playerUUID = player.getUniqueId();		
		final Action action = event.getAction();					
		final Material handMaterial = player.getInventory().getItemInMainHand().getType();		
		PositionType position;
		
		final Block block = event.getClickedBlock();
		
		if(block != null && handMaterial == Material.GOLDEN_HOE)
		{
			
			final Location location = block.getLocation();
			final Point point = new Point(location);
			
			switch(action)
			{
			case RIGHT_CLICK_BLOCK:
				position = PositionType.B;
				break;
				
			case LEFT_CLICK_BLOCK:
				position = PositionType.A;		
				
				event.setCancelled(true);	
				
				break;
					
				default:
					return;					
			}			
			
			SetPosition(playerUUID, point, position);
			PrintPositions(player, position);			
		}		
	}	
}