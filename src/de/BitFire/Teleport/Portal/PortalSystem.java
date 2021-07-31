package de.BitFire.Teleport.Portal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.BitFire.API.CommandCredentials;
import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SubCommand;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Core.Exception.FaultrySyntaxException;
import de.BitFire.Core.Exception.NotForConsoleException;
import de.BitFire.Core.Exception.SystemHasNoDataToPrintException;
import de.BitFire.Core.Exception.SystemHasNothingToReloadException;
import de.BitFire.Core.Exception.SystemNotActiveException;
import de.BitFire.Core.Exception.TooFewParameterException;
import de.BitFire.Core.Exception.TooManyParameterException;
import de.BitFire.DataBase.DataBaseSystem;
import de.BitFire.File.FlagParser;
import de.BitFire.Geometry.Cube;
import de.BitFire.Teleport.TeleportSystem;
import de.BitFire.Teleport.Portal.Exception.PortalNotFoundException;
import de.BitFire.Time.IUpdateable;
import de.BitFire.World.Manipulation.WorldEditSystem;
import de.BitFire.World.Manipulation.Exception.NoRegionMarkedException;
import de.BitFire.World.Manipulation.Exception.PositionAMissingException;
import de.BitFire.World.Manipulation.Exception.PositionBMissingException;

public class PortalSystem extends BaseSystem implements ISystem, IUpdateable
{
	private static PortalSystem _instance;
	private PortalList _portalList;
	
	private WorldEditSystem _worldEditSystem;
	private TeleportSystem _teleportSystem;
	private DataBaseSystem _dataBaseSystem;
	private Map<UUID, Integer> _portalPlayerCooldown;
	private Logger _logger;
	
	public PortalSystem() 
	{
		super(Module.Portal, SystemState.Active, Priority.Low);
		_instance = this;
		
		_portalList = new PortalList();
		_portalPlayerCooldown = new HashMap<UUID, Integer> ();
	}
	
	public static PortalSystem Instance()
	{
		return _instance == null ? new PortalSystem() : _instance;
	}
	
	@Override
	public void LoadReferences()
	{
		_worldEditSystem = WorldEditSystem.Instance();
		_logger = Logger.Instance();
		_dataBaseSystem = DataBaseSystem.Instance();
		_teleportSystem = TeleportSystem.Instance();
	}
	
	@Override
	public void Reload(final boolean firstRun) throws SystemHasNothingToReloadException, SystemNotActiveException 
	{
		List<Portal> portals = _dataBaseSystem.Portal.LoadAllPortals();
		
		for(Portal portal : portals)
		{
			_portalList.Add(portal);			
		}
	}
	
	@Override
	public void PrintData(CommandSender sender) throws SystemHasNoDataToPrintException 
	{
		final List<Portal> portals = _portalList.GetAllPortals();
		String message = "";
		
		if(!(sender instanceof Player))
		{
			message += "\n";
		}
		
		message += "&6===[Portals]=================\n";
		
		for(Portal portal : portals)
		{
			message += " &6ID&7:&r" + portal.ID + "&e->&r" + (portal.LinkedPortalID == -1 ? "&cx" : portal.LinkedPortalID)  + " &6Name&7:&r" + portal.Name + " &6D&7:&r" + portal.Direction +  "\n";
		}
		
		message += "&6====================";
		
		_logger.SendToSender(Information.GetSystemModule(), MessageType.Info, sender, message);
	}	
	
	@Override
	public void Command(final CommandCredentials commandCredentials) throws NotForConsoleException,																			 
																			TooFewParameterException, 
																			TooManyParameterException, 																		FaultrySyntaxException, 
																			 
																			PositionAMissingException, 
																			PositionBMissingException, NoRegionMarkedException, SystemHasNoDataToPrintException
	{
		switch(commandCredentials.ParameterLengh)
		{
		case 0:
			// portal
			//throw new TooFewParameterException();
			
			Map<Character, Boolean> reeee = new HashMap<Character, Boolean>();
			
			reeee.put('A', true);
			reeee.put('B', false);
			reeee.put('C', true);
			reeee.put('D', false);
			reeee.put('E', true);			
			
			String wwww = FlagParser.ToString(reeee);
			
			reeee.clear();
			
			reeee = FlagParser.ToList(wwww);
			
			break;
			
		case 1:
			// portal <command>
			SubCommand subCommand1 = commandCredentials.SubCMD;
			
			switch(subCommand1)
			{				
			case Help:
				PrintHelp(commandCredentials.Sender);
				break;
				
			case ListData:
				PrintData(commandCredentials.Sender);
				break;
								
			default:
				throw new FaultrySyntaxException();
			}
			break;
			
		case 2:
			// portal <command> <parameter>
			SubCommand subCommand = commandCredentials.SubCMD;
			final String name = commandCredentials.Parameter[1];
			
			switch(subCommand)
			{
			case Create:
				if(commandCredentials.IsSenderPlayer)
				{
					final Player player = commandCredentials.TryGetPlayer();
					final UUID playerUUID = player.getUniqueId();					
					final Cube cube = _worldEditSystem.GetCube(playerUUID);

					final String message = "&7Portal &6<&e" + name + "&6> &acreated&7.";
					
					_logger.SendToSender(Information.GetSystemModule(), MessageType.Info, player, message);
					
					Create(cube, name);					
				}
				else
				{
					throw new NotForConsoleException();
				}
			
				break;
				
			case Remove:
				final String message = "&7Portal &6<&e" + name + "&6> &cremoved&7.";
				
				_logger.SendToSender(Information.GetSystemModule(), MessageType.Info, commandCredentials.Sender, message);
				
				Remove(name);
				break;
				
			case Warp:
				if(commandCredentials.IsSenderPlayer)
				{
					final Player player = commandCredentials.TryGetPlayer();
					final String portalNameOrID = commandCredentials.Parameter[1];
					Portal portal;
					Location location;
					
					try
					{
						final int id = Integer.parseInt(portalNameOrID);
						portal = _portalList.Get(id);
					}
					catch(NumberFormatException numberFormatException)
					{
						portal = _portalList.Get(portalNameOrID);
					}					
		
					 location = portal.GetLocation();
										
					_teleportSystem.TeleportToLocation(player, location);			
				}
				else
				{
					throw new NotForConsoleException();
				}
				break;
				
			default:
				throw new FaultrySyntaxException();
			}
			break;
			
		default:
			throw new TooManyParameterException();
		}
	}	
	
	@Override
	public void PrintHelp(final CommandSender sender)
	{
		final String message = "Help";		
		_logger.SendToSender(Information.GetSystemModule(), MessageType.None, sender, message);
	}	
	
	@Override
	public void OnTickUpdate()
	{
		TickPlayerCoolDown();		
	}
	
	public void Create(final Cube cube, final String name)
	{
		final int newID = _portalList.GetNextID();
		final Portal portal = new Portal(newID, cube, name);
		
		_portalList.Add(portal);
	}
	
	public void Remove(final String name)
	{
		_portalList.Remove(name);
	}

	public Portal TryGetPortalInLocation(Location targetlocation) throws PortalNotFoundException 
	{
		return _portalList.GetPortalFromLocation(targetlocation);
	}

	public Portal Get(int iD) 
	{
		return _portalList.Get(iD);
	}
	
	public void SetPlayerCoolDown(final UUID playerUUID)
	{
		_portalPlayerCooldown.put(playerUUID, 3);		
	}
	
	private void TickPlayerCoolDown()
	{
		int index = 0;
		int zeroValueIndexes = 0;
		final int size = _portalPlayerCooldown.size();
		UUID[] zeroValues = new UUID[size];
		
		for (Map.Entry<UUID, Integer> entry : _portalPlayerCooldown.entrySet()) 
		{
			final int newValue = entry.getValue() - 1;
			
			if(newValue == 0)
			{
				zeroValues[index] = entry.getKey();
				zeroValueIndexes++;
			}
			else
			{
				entry.setValue(newValue);
			}							
			
			index++;
		}
		
		for(index = 0 ; index < zeroValueIndexes ; index++)
		{
			_portalPlayerCooldown.remove(zeroValues[index]);
		}
	}
	
	public boolean CanPlayerTeleport(final UUID playerUUID)
	{		
		return !_portalPlayerCooldown.containsKey(playerUUID);
	}
	
	public int GetCoolDown(final UUID playerUUID)
	{
		Integer value = _portalPlayerCooldown.get(playerUUID);
		
		if(value == null)
		{
			value = 0;
		}
		
		return value;
	}
}