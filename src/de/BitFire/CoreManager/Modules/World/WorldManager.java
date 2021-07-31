package de.BitFire.CoreManager.Modules.World;

import org.bukkit.World.Environment;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.Server;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

import de.BitFire.CoreManager.Modules.Chat.ChatManager;
import de.BitFire.CoreManager.Modules.Chat.MessageSouceInfromation;
import de.BitFire.CoreManager.Modules.Chat.MessageType;
import de.BitFire.CoreManager.Modules.DataBase.DataBaseManager;
import de.BitFire.CoreManager.Modules.Position.PositionManager;
import de.BitFire.CoreManager.Modules.World.Exception.WorldAlreadyLoadedExeption;
import de.BitFire.CoreManager.Modules.World.Exception.WorldIsNotMarkedAsActiveException;
import de.BitFire.CoreManager.Modules.World.Type.World;
import de.BitFire.CoreManager.Modules.World.Type.WorldBlockPosition;
import de.BitFire.Math.Geometry.PositionBlock;
import de.BitFire.CoreManager.Cache.WorldCache;

public class WorldManager
{
	private static WorldCache _cache;
	private static MessageSouceInfromation _messageInfromation;
	
	static
	{
		_cache = new WorldCache();
		_messageInfromation = new MessageSouceInfromation(ChatColor.AQUA, "World");
	}
	
	public static World GetWorld(String worldName)
	{
		return _cache.GetWorld(worldName);
	}
	
	public static int GetWorldID(org.bukkit.World bukkitWorld) 
	{		
		final String worldName = RemoveFolderPath(bukkitWorld.getName());
		final World world = _cache.GetWorld(worldName);
		int id = -1;
		
		if(world != null)
		{
			id = world.ID;	
		}	
		
		return id;
	}	
	
	public static String AddFolderPath(final String worldName)
	{		
		return "Maps/" + worldName;
	}
	
	public static String RemoveFolderPath(String worldName)
	{
		final String removerKey = "!";
		int lastindex;		
		
		worldName = worldName.replace("\\", removerKey);
		worldName = worldName.replace("/", removerKey);
		
		lastindex = worldName.lastIndexOf(removerKey);
		
		if(lastindex != -1)
		{
			lastindex += 1;
			worldName = worldName.substring(lastindex);
		}
		
		return worldName;
	}
	
	public static org.bukkit.World GetBukkitWorld(String worldName) 
	{
		return _cache.GetBukkitWorld(worldName);
	}
	
	public static List<org.bukkit.World> GetCurrentlyLoadedWorlds()
	{
		final Server server = Bukkit.getServer();
		final List<org.bukkit.World> worlds = server.getWorlds();
		
		return worlds;
	}
	
	public static void LoadAllWorlds()
	{
		ChatManager.SendToConsole(MessageType.Loading, _messageInfromation, "&3Loading all Worlds...");		
		ChatManager.SendToConsole(MessageType.Loading, _messageInfromation, "&3[Detecting currend loaded Worlds]");
		
		// Cache all currently Loaded worlds		
		{
			final List<org.bukkit.World> bukkitWorlds = GetCurrentlyLoadedWorlds();
			
			for(org.bukkit.World bukkitWorld : bukkitWorlds)
			{
				_cache.CacheWorld(bukkitWorld);
				ChatManager.SendToConsole(MessageType.Info, _messageInfromation, "&3Active &3World &7detected &6<&e" +  bukkitWorld.getName() +"&6>&7.");
			}
		}
		
		ChatManager.SendToConsole(MessageType.Loading, _messageInfromation, "&3[Loading worlds from the database]");
		
		// Load worlds from the database
		{
			World world = new World(-1, "[N/A]");
			List<World> worlds = DataBaseManager.LoadObject(world);
						
			for(World worldsss : worlds)
			{				
				_cache.CacheWorld(worldsss);
				ChatManager.SendToConsole(MessageType.Info, _messageInfromation, PrintWorldInformation(worldsss));
			}
		}
		
		ChatManager.SendToConsole(MessageType.Info, _messageInfromation, "&3[Creating worlds]");
		
		// Load all left worlds
		{
			int[] worldIDs = _cache.GetWorldIDs();
			
			for(int worldID : worldIDs)
			{
				try 
				{
					ChatManager.SendToConsole(MessageType.Info, _messageInfromation, "&3[Beginning of creating world <ID:" + worldID + ">]");
					LoadWorld(worldID);
				} 
				catch (WorldAlreadyLoadedExeption e) 
				{
					ChatManager.SendToConsole(MessageType.Warning, _messageInfromation, "&7Creation of world got &ccanceled&7. World is &ealready &7loaded.");
				} 
				catch (WorldIsNotMarkedAsActiveException e) 
				{
					ChatManager.SendToConsole(MessageType.Warning, _messageInfromation, "&7Creation of world got &ccanceled&7. The World is &cnot &7marked as &aactive&7.");
				}
			}
		}	
	}
	
	public static void LoadWorld(final int worldID) throws WorldAlreadyLoadedExeption, WorldIsNotMarkedAsActiveException
	{
		final World world = _cache.GetWorld(worldID);
		
		if(world == null)
		{
			return;
		}
		
		// Check - Should Load?
		{
			final boolean shouldLoad = world.Active;	
			
			if(!shouldLoad)
			{
				throw new WorldIsNotMarkedAsActiveException();
			}
		}
		
		// Check is already loaded?
		{
			final boolean isAlreadyLoaded = _cache.IsWorldLoaded(worldID);
			
			if(isAlreadyLoaded)
			{				
				throw new WorldAlreadyLoadedExeption(world.GetPreferedName());
			}
		}	
		
		final WorldBlockPosition worldBlockPosition = PositionManager.GetWorldBlockPosition(world.BlockPositionID);
		
		int x;
		int y;
		int z;
		
		if(worldBlockPosition == null)
		{
			final String message = "&eWorldBlockPosition &7is &cmissing &8<&cID&4:&c" + world.BlockPositionID + "&8>"; 
			
			ChatManager.SendToConsole(MessageType.Warning, _messageInfromation, message);
			
			x = 0;
			y = 0;
			z = 0;			
		}
		else
		{
			x = worldBlockPosition.X;
			y = worldBlockPosition.Y;
			z = worldBlockPosition.Z;
		}
		
		final String worldPathName = AddFolderPath(world.Name);
		final Environment worldEnvironment = World.TranslateEnvironment(world.StyleID);
		final WorldType worldType = World.TranslateWorldType(world.TypeID);
		final Difficulty worldDiffictulty = Difficulty.HARD;
		final long seed = Long.parseLong(world.Seed);
		final boolean generateStructures = world.Structures;
		final boolean enablePvP = world.PvP;
		final boolean looseItemsOnDeath = world.KeepItemsOnDeath;

		final WorldCreator worldCreator = new WorldCreator(worldPathName)
				.environment(worldEnvironment)
				.generateStructures(generateStructures)
				.seed(seed)
				.type(worldType);
			
		final org.bukkit.World bukkitWorld = Bukkit.createWorld(worldCreator);
		bukkitWorld.setAutoSave(true);
		bukkitWorld.setDifficulty(worldDiffictulty);
		bukkitWorld.setKeepSpawnInMemory(looseItemsOnDeath);
		bukkitWorld.setPVP(enablePvP);
		bukkitWorld.setSpawnFlags(false, false);
		bukkitWorld.setSpawnLocation(x, y, z);
		bukkitWorld.setTime(0);
		bukkitWorld.setStorm(false);
		bukkitWorld.setWeatherDuration(0);
		
		if(!world.SpawnAnimals)
		{
			bukkitWorld.setAnimalSpawnLimit(0);
		}
		
		if(!world.SpawnMobs)
		{
			bukkitWorld.setMonsterSpawnLimit(0);
		}	
		
		_cache.CacheWorld(bukkitWorld);	
	}
	
	public static void UnLoadWorld()
	{
		
	}
	
	public static String PrintWorldInformation(World world)
	{				
		String styleTag = "";
		String typeTag = "";
		
		
		// Environment
		try
		{
			final Environment environment = World.TranslateEnvironment(world.StyleID);
			
			switch(environment)
			{
			case NETHER:
				styleTag = "&cN";
				break;
				
			case NORMAL:
				styleTag = "&aN";
				break;
				
			case THE_END:
				styleTag = "&eN";
				break;		
			}
		}
		catch(Exception e)
		{
			styleTag = "&4Err:" + world.StyleID;
		}
		
		
		// WorldType
		try
		{
			
			final WorldType worldType = World.TranslateWorldType(world.TypeID);
			
			
			switch(worldType)
			{
			case AMPLIFIED:
				typeTag = "&4A";
				break;
				
			case BUFFET:
				typeTag = "&cB";
				break;
				
			case CUSTOMIZED:
				typeTag = "&dC";
				break;
				
			case FLAT:
				typeTag = "&3F";
				break;
				
			case LARGE_BIOMES:
				typeTag = "&bL";
				break;
				
			case NORMAL:
				typeTag = "&2N";
				break;
				
			case VERSION_1_1:
				typeTag = "&51";
				break;								
			}
		}
		catch(Exception e)
		{
			typeTag = "&4Err:" + world.TypeID;
		}
		
		return " &6ID&8:&e" + world.ID + " &6Name&8:&e" + world.GetPreferedName() + " &7S:" + styleTag  + " &7T:" + typeTag ;
	} 
	
	public static String PrintWorldInformation()
	{
		final int[] worldIDs = _cache.GetWorldIDs();
		final int size = worldIDs.length;
		String message = "\n";
		
		message += "&6===[&eWorlds&6]===[&e" + size + "&6]\n";
		
		for(int worldID : worldIDs)
		{			
			final World world = _cache.GetWorld(worldID);			
			message += PrintWorldInformation(world) + "\n";
		}
		
		message += "&6=================";
		
		return message;
	}
}