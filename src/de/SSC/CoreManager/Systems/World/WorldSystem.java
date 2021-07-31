package de.SSC.CoreManager.Systems.World;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Systems.Chat.Logger;
import de.SSC.CoreManager.Systems.Chat.MessageTags;
import de.SSC.CoreManager.Systems.Chat.MessageType;
import de.SSC.CoreManager.Systems.Chat.Module;
import de.SSC.CoreManager.Systems.DataBase.DataBaseSystem;
import de.SSC.CoreManager.Utility.BukkitUtility;
import de.SSC.CoreManager.Utility.DataSaveStorageType;

public class WorldSystem
{
	private static WorldSystem _instance;
	//private boolean Debug = true;
	private WorldList _worldList;
	private Logger _logger;
	private Config _config;
	private DataBaseSystem _databaseManager;
	private MessageTags _messageTags;
	private BukkitUtility _bukkitUtility;

	private WorldSystem()
	{
		_instance = this;
		_worldList = new WorldList();
	}
	
	public void LoadReferences()
	{
		_databaseManager = DataBaseSystem.Instance();
		_logger = Logger.Instance();
		_config = Config.Instance();
		_messageTags = MessageTags.Instance();
		_bukkitUtility = BukkitUtility.Instance();
	}
	
	private void MergeAndLoadIntoList(List<CMWorld> worldsDataBase,  List<CMWorld> worldsBukkit) 
	{		
		for(CMWorld cmWorld : worldsDataBase)
		{
			cmWorld.SaveStorageType = DataSaveStorageType.InActive;
			
			_worldList.Add(cmWorld);
		}
		
		for(CMWorld cmWorld : worldsBukkit)
		{			
			try
			{
				String worldName = cmWorld.Name;
				CMWorld cmWorldData = _worldList.GetPerName(worldName);				
				
				if(cmWorldData == null)
				{
					// Its a new World that is not registered.
					
					cmWorld.SaveStorageType = DataSaveStorageType.UnRegistered;					
					
					_worldList.Add(cmWorld);
				}
				else
				{
					// Dublicat found					
					
					_logger.SendToConsole(Module.WorldSystem, MessageType.Info, "&6Dublicat &edetected&6! &eMerging world &6<&e" + worldName + "&6>&e.");
					
					cmWorldData.SaveStorageType = DataSaveStorageType.Active;					
					cmWorldData.BukkitWorld = cmWorld.BukkitWorld;
										
					_worldList.Swap(worldName, cmWorldData);	
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
		int x = (int)Math.round(cmWorld.SpawnX);
		int y = (int)Math.round(cmWorld.SpawnY);
		int z = (int)Math.round(cmWorld.SpawnZ);
		boolean isAlreadyLoaded = cmWorld.SaveStorageType == DataSaveStorageType.UnRegistered || cmWorld.SaveStorageType == DataSaveStorageType.Active;
		String worldName = cmWorld.Name;
		String worldPathName = _config.Worlds.AddFolderName(worldName);		
		CMWorld newCMWorld = cmWorld;
		WorldCreator worldCreator;
		World newWorld;		

		if(isAlreadyLoaded)
		{
			// Dont create something new that does already exist.
		}
		else
		{
			try
			{
				worldCreator = new WorldCreator(worldPathName)
					.environment(cmWorld.MapStyle)
					.generateStructures(cmWorld.GenerateStructures)
					.seed(cmWorld.Seed)
					.type(cmWorld.MapType);
				
				newWorld = Bukkit.createWorld(worldCreator);
				newWorld.setAutoSave(true);
				newWorld.setDifficulty(cmWorld.WorldDifficulty);
				newWorld.setKeepSpawnInMemory(cmWorld.KeepInventory);
				newWorld.setPVP(cmWorld.PvP);
				newWorld.setSpawnFlags(false, false);
				newWorld.setSpawnLocation(x, y, z);
						
				newCMWorld.BukkitWorld = newWorld;					
				newCMWorld.SaveStorageType = DataSaveStorageType.Active;
			}
			catch (Exception e)
			{
				String message = _config.Messages.World.ErrorWhileCreatingWorld + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();

				newCMWorld.SaveStorageType = DataSaveStorageType.FailedToLoad;
				
				message = _messageTags.ReplaceWorldTag(message, worldName);

				_logger.SendToConsole(Module.WorldSystem, MessageType.Error, message);
			}
		}
		
		return newCMWorld;
	}
	
	private List<CMWorld> GetAllCurrentlyLoadedWorlds()
	{
		List<World> loadedWorlds = _bukkitUtility.WorldUtility.GetLoadedWorlds();
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
	
	public CMWorld GetWorldPerName(String worldName)
	{
		return _worldList.GetPerName(worldName);
	}
	
	public void ReloadWorlds()
	{	
		 List<CMWorld> worldsDataBase;
		 List<CMWorld> worldsBukkit;
		 List<CMWorld> halfLoadedWorlds;	
	
		 _worldList.Clear();
		 
		try
		{	
			worldsDataBase = _databaseManager.LoadAllWorlds();
			worldsBukkit = GetAllCurrentlyLoadedWorlds();
			
			MergeAndLoadIntoList(worldsDataBase, worldsBukkit);		
			
			halfLoadedWorlds = _worldList.GetWorlds();			
			
			for(CMWorld haldCMWorld : halfLoadedWorlds)
			{
				CMWorld cmWorld = LoadWorld(haldCMWorld, Bukkit.getConsoleSender());
				
				_worldList.Swap(cmWorld.Name, cmWorld);
			}
		}
		catch(Exception exception)
		{
			_logger.SendToConsole(Module.WorldSystem, MessageType.Error, "Error while reloading worlds. " + exception.getMessage());
			exception.printStackTrace();
		}		
	}
	
	public CMWorld LoadWorld(final CMWorld cmWorld, CommandSender commandSender)
	{		
		CMWorld changedCMWorld = cmWorld; 
		
		_logger.SendToSender(Module.WorldSystem, MessageType.Loading, commandSender, "World <" + cmWorld.Name + "> loading...");
		
		if(cmWorld.BukkitWorld == null)
		{
			// World is not loaded
			
			if(cmWorld.Active)
			{
				// Load World
				changedCMWorld = CreateWorld(cmWorld);
			}
			else
			{
				// Dont load Map
				_logger.SendToSender(Module.WorldSystem, MessageType.Warning, commandSender, "World <" + cmWorld.Name + "> will not be loaded. It's set to inactive");	
								
				changedCMWorld.SaveStorageType = DataSaveStorageType.InActive;							
			}			
		}
		else
		{
			// World is loaded			
			if(cmWorld.Active)
			{
				// World already loaded
				_logger.SendToSender(Module.WorldSystem, MessageType.Info, commandSender, "World <" + cmWorld.Name + "> is already loaded.");	
			
				// Do nothing, world is already loaded
				changedCMWorld.SaveStorageType = DataSaveStorageType.UnRegistered;	 
			}
			else
			{
				// World should not be loaded, Unload map				
				changedCMWorld.SaveStorageType = DataSaveStorageType.InActive;	 
				
				UnloadMap(changedCMWorld);	
			}		
		}	
	
		return changedCMWorld;
	}
	
	
	public void UnloadMap(CMWorld cmWorld)
	{
		boolean isWorldUnloaded = false;
		CMWorld unloadedCMWorld = cmWorld;
		
		_logger.SendToConsole(Module.WorldSystem, MessageType.Info, "Unloading world <" + cmWorld.Name + ">.");
		
		isWorldUnloaded = _bukkitUtility.WorldUtility.UnloadWorld(cmWorld.BukkitWorld);
		
		if(isWorldUnloaded)
		{
			_bukkitUtility.WorldUtility.UnloadWorld(cmWorld.BukkitWorld);
			
			unloadedCMWorld.BukkitWorld = null;
			unloadedCMWorld.SaveStorageType = DataSaveStorageType.InActive;
			
			//_worldList.Swap(cmWorld.Name, unloadedCMWorld);
		}		
	}
	
	public void ListWorlds(CommandSender commandSender)
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
				if(cmWorld.Name == null)
				{
					throw new NullPointerException("WorldSystem.ListWorlds() cmWorld.Name is null!");
				}
				
				if(cmWorld.SaveStorageType == null)
				{
					throw new NullPointerException("WorldSystem.ListWorlds() cmWorld.SaveStorageType is null!");
				}
				
				if(cmWorld.MapType == null)
				{
					throw new NullPointerException("WorldSystem.ListWorlds() cmWorld.MapType is null!");
				}				
				
				
				if(cmWorld.MapStyle == null)
				{
					throw new NullPointerException("WorldSystem.ListWorlds() cmWorld.MapStyle is null!");
				}				
				
			
					switch(cmWorld.SaveStorageType)
					{
					case Active:
						worldMessage = _config.Messages.World.LoadedWorld;
						break;
						
					case FailedToLoad:
						worldMessage = _config.Messages.World.FailedToLoadWorld;
						break;
						
					case InActive:
						worldMessage = _config.Messages.World.UnloadedWorld;
						break;
						
					case UnRegistered:
						worldMessage = _config.Messages.World.UnRegisteredWorld;
						break;
						
					case Undefined:
						worldMessage = _config.Messages.World.UndefinedWorld;
						break;			
					}
					
					switch(cmWorld.MapType)
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
					
					switch(cmWorld.MapStyle)
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
					
					worldMessage = _messageTags.ReplaceNameTag(worldMessage, cmWorld.Name);
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

		_logger.SendToSender(Module.WorldSystem, MessageType.None, commandSender, message);
	}

	public void SetWorldSpawn(CommandSender sender)
	{
		boolean isSenderConsole = _bukkitUtility.PlayerUtility.IsSenderPlayer(sender);
		Player player;
		Location location;
		World world;
		String message;

		if (isSenderConsole)
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

	public void SetSpawnLocation()
	{

	}

	public void CreateNewWorldCommand(CommandSender sender, String[] args)
	{

	}

	public void DeleteWorldCommand(CommandSender sender, String[] args)
	{

	}
	
	
	
	
}