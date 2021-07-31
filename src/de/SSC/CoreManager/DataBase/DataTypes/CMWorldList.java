package de.SSC.CoreManager.DataBase.DataTypes;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DatabaseManager;
import de.SSC.CoreManager.Messages.Logger;
import de.SSC.CoreManager.Messages.MessageType;
import de.SSC.CoreManager.Messages.Module;
import de.SSC.CoreManager.Utility.MessageTags;

public class CMWorldList 
{
  private static CMWorldList _instance;
  private boolean Debug = true;
  private ArrayList<CMWorld> _worlds;
  private DatabaseManager _databaseManager;
  private MessageTags _messageTags;
  private Logger _logger;
  private Config _config;
	
  private CMWorldList()
  {
	  _instance = this;
	  _worlds  = new ArrayList<CMWorld>();
	  
	  _databaseManager = DatabaseManager.Instance();
	  _logger = Logger.Instance();
	  _config = Config.Instance();	 
	  _messageTags = MessageTags.Instance();
  }
  
  public static CMWorldList Instance()
  {
	  if(_instance == null)
	  {
		  _instance = new CMWorldList();		 
	  }
	  
	  return _instance;
  }
  
	public CMWorld GetWorldPerName(String worldName)
	{
		CMWorld returnCMWorld = null;
		
		for(CMWorld cmWorld : _worlds)
		{
			if(cmWorld.BukkitWorld != null)
			{
				String cmWorldName = cmWorld.BukkitWorld.getName();
				
				if(Debug)
				{
					String message = "is <" + worldName + "> == <" + cmWorldName + ">";
					_logger.SendToConsole(Module.WorldList, MessageType.Question, message);
				}
				
			
					if(worldName.equalsIgnoreCase(cmWorldName))
					{
						returnCMWorld = cmWorld;
						break;			
					}
					
			}
		}	
		
		if(returnCMWorld == null)
		{
			String message = "World <" + worldName + "> was searched but not found.";
			_logger.SendToConsole(Module.WorldList, MessageType.Warning, message);
		}
		
		return returnCMWorld;
	}
	
	public void AddWorld(CMWorld cmWorld)
	{
		_worlds.add(cmWorld);
	}
	
	public void ReloadWorlds()
	{
		_worlds = _databaseManager.LoadAllWorlds();
		
		for(CMWorld cmWorld : _worlds)
		{
			LoadWorld(cmWorld, Bukkit.getConsoleSender());	
		}	
		
		ListWorlds(Bukkit.getConsoleSender());
	}
	
	public CMWorld LoadWorld(CMWorld cmWorld, CommandSender commandSender)
	{
		WorldCreator worldCreator;
		String worldName = _config.Worlds.AddFolderName(cmWorld.Name);		
		int x = Math.round(cmWorld.SpawnX);
		int y = Math.round(cmWorld.SpawnY);
		int z = Math.round(cmWorld.SpawnZ);	          
		
		try
		{
	          worldCreator = new WorldCreator(worldName)
	                  .environment(cmWorld.MapStyle)
	                  .generateStructures(cmWorld.GenerateStructures)
	                  .seed(cmWorld.Seed)
	                  .type(cmWorld.MapType);
	    
					    
		cmWorld.BukkitWorld = Bukkit.createWorld(worldCreator);	
		cmWorld.BukkitWorld.setAutoSave(true);
		cmWorld.BukkitWorld.setDifficulty(cmWorld.WorldDifficulty);
		cmWorld.BukkitWorld.setKeepSpawnInMemory(cmWorld.KeepInventory);
		cmWorld.BukkitWorld.setPVP(cmWorld.PvP);
		cmWorld.BukkitWorld.setSpawnFlags(false, false);
		cmWorld.BukkitWorld.setSpawnLocation(x, y, z);		
		}
		catch(Exception e)
		{
			String message = _config.Messages.World.ErrorWhileCreatingWorld + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();
			
			message = _messageTags.ReplaceWorldTag(message,  worldName);
			
			_logger.SendToConsole(Module.WorldList, MessageType.Error, message);			
		}
		
		return cmWorld;
	}
	
	  public void ListWorlds(CommandSender commandSender)
	  {
	        String message = _config.Messages.ConsoleIO.NewLine +  _config.Messages.World.WorldListHeader + _config.Messages.ConsoleIO.NewLine;
	        String worldMessage;
	        
	        if(_worlds.size() <= 0)
	        {
	            message += _config.Messages.World.NoLoadedWorlds + _config.Messages.ConsoleIO.NewLine;
	        }
	        else
	        {
	        	for(CMWorld cmWorld : _worlds)
	            {
	                if(cmWorld.Name != null)
	                {
	                	worldMessage  = cmWorld.BukkitWorld != null ? _config.Messages.World.LoadedWorld : _config.Messages.World.UnloadedWorld;
                   	                    
	                    worldMessage = _messageTags.ReplaceNameTag(worldMessage, cmWorld.Name);
	                    worldMessage = _messageTags.ReplaceWorldTag(worldMessage,  cmWorld);
	                    
	                    message += worldMessage + _config.Messages.ConsoleIO.NewLine;
	                }
	                else
	                {
	                    message += _config.Messages.World.UnkownWorld + _config.Messages.ConsoleIO.NewLine;
	                }
	            }
	        }

	        message += _config.Messages.World.WorldListFooter;

	        message = message.replace(Environment.NORMAL.toString(), _config.Messages.World.WorldNormal);
	        message = message.replace(Environment.NETHER.toString(), _config.Messages.World.WorldNether);
	        message = message.replace(Environment.THE_END.toString(), _config.Messages.World.WorldEnd);

	        _logger.SendToSender(Module.WorldList, MessageType.None, commandSender, message);
	    }
}
