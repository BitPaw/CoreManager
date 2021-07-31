package de.SSC.CoreManager.World;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Chat.Logger;
import de.SSC.CoreManager.Chat.MessageTags;
import de.SSC.CoreManager.Chat.MessageType;
import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DataBaseSystem;
import de.SSC.CoreManager.System.BaseSystem;
import de.SSC.CoreManager.System.ISystem;
import de.SSC.CoreManager.System.SystemPriority;
import de.SSC.CoreManager.System.SystemState;
import de.SSC.CoreManager.System.Exception.SystemNotActiveException;
import de.SSC.CoreManager.World.Exception.InvalidWorldNameException;
import de.SSC.BukkitAPI.BukkitAPISystem;

public class WorldSystem extends BaseSystem implements ISystem
{
	private static WorldSystem _instance;
	//private boolean Debug = true;
	private WorldList _worldList;
	private Logger _logger;
	private Config _config;
	private DataBaseSystem _databaseManager;
	private MessageTags _messageTags;
	private BukkitAPISystem _bukkitUtility;

	private WorldSystem() 
	{
		super(Module.WorldSystem, SystemState.Active, SystemPriority.Essential);
		_instance = this;
		
		_worldList = new WorldList();
	}	
	
	private void MergeAndLoadIntoList(List<CMWorld> worldsDataBase,  List<CMWorld> worldsBukkit) 
	{		
		for(CMWorld cmWorld : worldsDataBase)
		{			
			_worldList.Add(cmWorld);
		}
		
		for(CMWorld cmWorld : worldsBukkit)
		{			
			try
			{
				int worldID = cmWorld.Information.ID;
				String worldName = cmWorld.Information.Name;
				CMWorld cmWorldData = _worldList.GetWorld(worldName);				
				
				if(cmWorldData == null)
				{
					// Its a new World that is not registered.
					
					cmWorld.State = WorldState.ActiveButNotRegistered;					
					
					_worldList.Add(cmWorld);
				}
				else
				{
					// Dublicat found					
					
					_logger.SendToConsole(Module.WorldSystem, MessageType.Info, "&6Dublicat &edetected&6! &eMerging world &6<&e" + worldName + "&6>&e.");
					
					cmWorldData.State = WorldState.ActiveAndRegistered;					
					cmWorldData.BukkitWorld = cmWorld.BukkitWorld;
										
					_worldList.Swap(worldID, cmWorldData);	
				}
				
			}
			catch(Exception e)
			{
				_logger.SendToConsole(Module.WorldSystem, MessageType.Error, "WorldSystem.MergeAndLoadIntoList() > Error while mergin worldlists. " + e.getMessage());
			}
		}
	}
	
	private CMWorld CreateWorld(final CMWorld cmWorld)
	{		
		int x = (int)Math.round(cmWorld.Information.X);
		int y = (int)Math.round(cmWorld.Information.Y);
		int z = (int)Math.round(cmWorld.Information.Z);
		boolean isAlreadyLoaded = IsWorldLoaded(cmWorld);
		String worldName = cmWorld.Information.Name;
		String worldPathName = _config.Worlds.AddFolderName(worldName);		
		CMWorld newCMWorld = cmWorld;
		WorldCreator worldCreator;
		World newWorld;		

		if(isAlreadyLoaded)
		{
			// Dont create something new that does already exist.
			
			_logger.SendToConsole(Module.WorldSystem, MessageType.Warning, "&7World already &aloaded&7! Skipping loading world &6<&e" + worldName + "&6>.");
		}
		else
		{
			try
			{
				worldCreator = new WorldCreator(worldPathName)
					.environment(cmWorld.Information.MapEnvironment)
					.generateStructures(cmWorld.Information.Structures)
					.seed(cmWorld.Information.Seed)
					.type(cmWorld.Information.Type);
				
				newWorld = Bukkit.createWorld(worldCreator);
				newWorld.setAutoSave(true);
				newWorld.setDifficulty(cmWorld.Information.WorldDifficulty);
				newWorld.setKeepSpawnInMemory(cmWorld.Information.LoseItems);
				newWorld.setPVP(cmWorld.Information.PvP);
				newWorld.setSpawnFlags(false, false);
				newWorld.setSpawnLocation(x, y, z);
						
				newCMWorld.BukkitWorld = newWorld;					
				newCMWorld.State = WorldState.ActiveAndRegistered;
				
				_logger.SendToConsole(Module.WorldSystem, MessageType.Warning, "&7World &6<&e" + worldName + "&6> &7succesully &acreated&7!");
			}
			catch (Exception e)
			{
				String message = _config.Messages.World.ErrorWhileCreatingWorld + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();

				newCMWorld.State = WorldState.FailedToLoad;
				
				message = _messageTags.ReplaceWorldTag(message, worldName);

				_logger.SendToConsole(Module.WorldSystem, MessageType.Error, message);
				
				e.printStackTrace();
			}
		}
		
		return newCMWorld;
	}
	
	private List<CMWorld> GetAllCurrentlyLoadedWorlds()
	{
		List<World> loadedWorlds = _bukkitUtility.World.GetLoadedWorlds();
		List<CMWorld> worlds = new ArrayList<CMWorld>();
		
		for(World world : loadedWorlds)
		{
			CMWorld cmWorld = new CMWorld(world);
			
			worlds.add(cmWorld);
		}
		
		return worlds;
	}	
	
	public static WorldSystem Instance()
	{
		return _instance == null ? new WorldSystem() : _instance;
	}		
	
	@Override
	public void LoadReferences()
	{
		_databaseManager = DataBaseSystem.Instance();
		_logger = Logger.Instance();
		_config = Config.Instance();
		_messageTags = MessageTags.Instance();
		_bukkitUtility = BukkitAPISystem.Instance();
	}
	
	@Override
	public void Reload(final boolean firstRun) throws SystemNotActiveException 
	{
		 List<CMWorld> worldsDataBase;
		 List<CMWorld> worldsBukkit;
		 List<CMWorld> halfLoadedWorlds;	
	
		 _worldList.Clear();
		 
		 if(!Information.IsActive())
		 {
			 throw new SystemNotActiveException();
		 }		
		 
		try
		{	
			worldsDataBase = _databaseManager.World.LoadAllWorlds();
			worldsBukkit = GetAllCurrentlyLoadedWorlds();
			
			MergeAndLoadIntoList(worldsDataBase, worldsBukkit);		
			
			halfLoadedWorlds = _worldList.GetWorlds();			
			
			for(CMWorld haldCMWorld : halfLoadedWorlds)
			{
				CMWorld cmWorld = LoadWorld(haldCMWorld, Bukkit.getConsoleSender());
				
				_worldList.Swap(cmWorld.Information.ID, cmWorld);
			}
		}
		catch(Exception exception)
		{
			_logger.SendToConsole(Module.WorldSystem, MessageType.Error, "Error while reloading worlds. " + exception.getMessage());
			exception.printStackTrace();
		}		
	}
	
	@Override
	public void PrintData(CommandSender sender) 
	{
		boolean noWorldsLoaded = _worldList.IsEmpty();
		String message = _config.Messages.ConsoleIO.NewLine + _config.Messages.World.WorldListHeader + _config.Messages.ConsoleIO.NewLine;
		String worldMessage = "";
		String worldTypeText = "";
		String worldEnviromentText = "";
		List<CMWorld> worlds;

		if (noWorldsLoaded)
		{
			message += _config.Messages.World.NoLoadedWorlds + _config.Messages.ConsoleIO.NewLine;
		}
		else
		{
			worlds = _worldList.GetWorlds();
			
			for (CMWorld cmWorld : worlds)
			{
				if(cmWorld.Information.Name == null)
				{
					throw new NullPointerException("WorldSystem.ListWorlds() cmWorld.Name is null!");
				}
				
				if(cmWorld.State == null)
				{
					throw new NullPointerException("WorldSystem.ListWorlds() cmWorld.SaveStorageType is null!");
				}
				
				if(cmWorld.Information.Type == null)
				{
					throw new NullPointerException("WorldSystem.ListWorlds() cmWorld.MapType is null!");
				}				
				
				
				if(cmWorld.Information.MapEnvironment == null)
				{
					throw new NullPointerException("WorldSystem.ListWorlds() cmWorld.MapStyle is null!");
				}				
				
			
					switch(cmWorld.State)
					{
				
					/*
					 * 
					 * 			case InActive:
						worldMessage = _config.Messages.World.UnloadedWorld;
						break;
						
					case UnRegistered:
						worldMessage = _config.Messages.World.UnRegisteredWorld;
						break;
					 */
						
					case FailedToLoad:
						worldMessage = _config.Messages.World.FailedToLoadWorld;
						break;
						
		
						
					case Undefined:
						worldMessage = _config.Messages.World.UndefinedWorld;
						break;
						
					case ActiveAndRegistered:
						worldMessage = _config.Messages.World.LoadedWorld;
						break;
						
					case ActiveButNotRegistered:
						worldMessage = "&3Loaded & &cnot Registered";
						break;
						
					default:
						break;			
					}
					
					switch(cmWorld.Information.Type)
					{
					case AMPLIFIED:
						worldTypeText = "&4Amplified";
						break;
						
					case BUFFET:
						worldTypeText = "&cBuffet";
						break;
						
					case CUSTOMIZED:
						worldTypeText = "&dCustom";
						break;
						
					case FLAT:
						worldTypeText = "&bFlat";
						break;
						
					case LARGE_BIOMES:
						worldTypeText = "&2Large Biomes";
						break;
						
					case NORMAL:
						worldTypeText = "&aNormal";
						break;
						
					case VERSION_1_1:
						worldTypeText = "&3v.1.1";
						break;
						
					default:
						worldTypeText = "&eUnkown";
						break;
					
					}
					
					switch(cmWorld.Information.MapEnvironment)
					{
					case NETHER:
						worldEnviromentText = "&cNether";
						break;
						
					case NORMAL:
						worldEnviromentText = "&aNormal";
						break;
						
					case THE_END:
						worldEnviromentText = "&eEnd";
						break;
						
					default:
						worldEnviromentText = "&dUndefined";
						break;
					
					}	
					
					worldMessage = _messageTags.ReplaceNameTag(worldMessage, cmWorld.Information.Name);
					worldMessage = _messageTags.ReplaceWorldTag(worldMessage, cmWorld);
					worldMessage = _messageTags.MapType(worldMessage, worldTypeText);
					worldMessage = _messageTags.MapEnviroment(worldMessage, worldEnviromentText);
					
					message += worldMessage + _config.Messages.ConsoleIO.NewLine;
				
				
			
					//message += _config.Messages.World.UnkownWorld + _config.Messages.ConsoleIO.NewLine;
				
			}
		}

		message += _config.Messages.World.WorldListFooter;

		message = message.replace(Environment.NORMAL.toString(), _config.Messages.World.WorldNormal);
		message = message.replace(Environment.NETHER.toString(), _config.Messages.World.WorldNether);
		message = message.replace(Environment.THE_END.toString(), _config.Messages.World.WorldEnd);

		_logger.SendToSender(Module.WorldSystem, MessageType.None, sender, message);		
	}
	
	public CMWorld GetWorld(String worldName) throws InvalidWorldNameException
	{		
		return _worldList.GetWorld(worldName);
	}
	
	public CMWorld GetWorld(int worldID)
	{
		return _worldList.GetWorld(worldID);
	}

	public CMWorld LoadWorld(final CMWorld cmWorld, CommandSender commandSender)
	{		
		CMWorld changedCMWorld = cmWorld; 
		
		_logger.SendToSender(Module.WorldSystem, MessageType.Loading, commandSender, "&7World &6<&e" + cmWorld.Information.Name + "&6> &7loading...");
		
		if(cmWorld.BukkitWorld == null)
		{
			// World is not loaded
			
			if(cmWorld.Information.Active)
			{
				// Load World
				changedCMWorld = CreateWorld(cmWorld);
			}
			else
			{
				// Dont load Map
				_logger.SendToSender(Module.WorldSystem, MessageType.Warning, commandSender, "&7World &6<&e" + cmWorld.Information.Name + "&6> &7will &cnot &7be loaded. It's set to &cinactive");	
								
				changedCMWorld.State = WorldState.InActiveAndRegistered;							
			}			
		}
		else
		{
			// World is loaded			
			if(cmWorld.Information.Active)
			{
				// World already loaded
				_logger.SendToSender(Module.WorldSystem, MessageType.Info, commandSender, "&7World &6<&e" + cmWorld.Information.Name + "&6> &7is &ealready &7loaded.");	
			
				// Do nothing, world is already loaded
				changedCMWorld.State = WorldState.ActiveAndRegistered;	 
			}
			else
			{
				// World should not be loaded, Unload map				
				changedCMWorld.State = WorldState.InActiveAndRegistered;	 
				
				UnloadMap(changedCMWorld);	
			}		
		}	
	
		return changedCMWorld;
	}
	
	public void UnloadMap(CMWorld cmWorld)
	{
		boolean isWorldUnloaded = false;
		CMWorld unloadedCMWorld = cmWorld;
		
		_logger.SendToConsole(Module.WorldSystem, MessageType.Info, "Unloading world <" + cmWorld.Information.Name + ">.");
		
		isWorldUnloaded = _bukkitUtility.World.UnloadWorld(cmWorld.BukkitWorld);
		
		if(isWorldUnloaded)
		{
			_bukkitUtility.World.UnloadWorld(cmWorld.BukkitWorld);
			
			unloadedCMWorld.BukkitWorld = null;
			unloadedCMWorld.State = WorldState.InActiveAndRegistered;
			
			//_worldList.Swap(cmWorld.Name, unloadedCMWorld);
		}		
	}

	public void SetWorldSpawn(CommandSender sender) throws SystemNotActiveException
	{
		final boolean isSenderPlayer = sender instanceof Player;
		Player player;
		Location location;
		World world;
		String message;
		
		if(!Information.IsActive())
		{
			throw new SystemNotActiveException();
		}	

		if (!isSenderPlayer)
		{
			player = (Player)sender;
			location = player.getLocation();
			world = player.getWorld();

			world.setSpawnLocation(location);

			message = "Worldspawn changed.";

			_logger.SendToSender(Module.WorldSystem, MessageType.Info, sender, message);
		}
		else
		{
			message = "not for consol.";

			_logger.SendToSender(Module.WorldSystem, MessageType.Warning, sender, message);
		}
	}

	public void GetSpawnLocation()
	{

	}

	public void SetSpawnLocation(CommandSender sender) throws Exception
	{
		final boolean isSenderPlayer = sender instanceof Player;
		Player player;
		World world;
		Location location;
		
		if(!Information.IsActive())
		{
			throw new SystemNotActiveException();
		}	
		
		if (isSenderPlayer)
		{
			player = (Player)sender;
			world = (World)player.getWorld();
			location = player.getLocation();

			try
			{
				world.setSpawnLocation(location);

				_logger.SendToSender(Module.WarpSystem, MessageType.Info, sender, _config.Messages.TeleportSystem.SpawnUpdated);
			}
			catch (Exception e)
			{
				throw new Exception(_config.Messages.TeleportSystem + "World.SetSpawnLocation Error\n" + e.getMessage());
			}
		}
	}
	

	public void CreateNewWorldCommand(CommandSender sender, String[] args) throws SystemNotActiveException
	{
		if(!Information.IsActive())
		{
			throw new SystemNotActiveException();
		}	
	}

	public void DeleteWorldCommand(CommandSender sender, String[] args) throws SystemNotActiveException
	{
		if(!Information.IsActive())
		{
			throw new SystemNotActiveException();
		}	
	}

	public void SetGlobalSpawn(CommandSender sender) throws SystemNotActiveException 
	{ 
		if(!Information.IsActive())
		{
			throw new SystemNotActiveException();
		}		
	}

	public boolean IsWorldLoaded(int iD) 
	{		
		final CMWorld cmWorld = _worldList.GetWorld(iD);
		
		return IsWorldLoaded(cmWorld);
	}	
	
	public boolean IsWorldLoaded(CMWorld cmWorld) 
	{				
		final WorldState state = cmWorld.State;
		
		return (state == WorldState.ActiveAndRegistered || state == WorldState.ActiveButNotRegistered) || cmWorld.BukkitWorld != null;
	}

	public List<CMWorld> GetWorlds() 
	{
		return _worldList.GetWorlds();
	}
}