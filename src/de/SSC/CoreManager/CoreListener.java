package de.SSC.CoreManager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.SSC.CoreManager.Changer.NameChanger;
import de.SSC.CoreManager.Changer.NameTagManipulator;
import de.SSC.CoreManager.Changer.SignEdit;
import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DatabaseManager;
import de.SSC.CoreManager.DataBase.DataTypes.CMPlayer;
import de.SSC.CoreManager.DataBase.DataTypes.CMPlayerList;
import de.SSC.CoreManager.DataBase.DataTypes.CMRankList;
import de.SSC.CoreManager.DataBase.DataTypes.CMWarpList;
import de.SSC.CoreManager.DataBase.DataTypes.CMWorldList;
import de.SSC.CoreManager.Editor.PlayerEditor;
import de.SSC.CoreManager.Editor.PlayerSkinChanger;
import de.SSC.CoreManager.Manager.ChatManager;
import de.SSC.CoreManager.Manager.TeleportManager;
import de.SSC.CoreManager.Manager.WorldManager;
import de.SSC.CoreManager.Messages.Logger;
import de.SSC.CoreManager.Messages.MessageType;
import de.SSC.CoreManager.Messages.Module;
import de.SSC.CoreManager.Tab.PingTabList;
import de.SSC.CoreManager.Utility.BukkitUtility;
import de.SSC.CoreManager.Utility.FireWork;
import de.SSC.CoreManager.Utility.MessageTags;

public class CoreListener implements Listener
{
    private static CoreListener _instance;
    private PluginManager _pluginManager ;
    private BukkitUtility _bukkitUtility;
   
	private Logger _logger;
	private Config _config;
    
    private DatabaseManager _databaseManager;
    
	private CMWorldList _cmWorldList;
	private CMRankList _cmRankList;
	//private CMPermissionList _cmPermissionList;
	private CMPlayerList _cmPlayerList;
	private CMWarpList _cmWarpList;
	
    private ChatManager _chatManager;
   // private PermissionManager _permissionManager;
    private PlayerEditor _playerEditor;
    private WorldManager _worldManager;

    // I - Extra
    private PingTabList _pingTabList;
    private NameChanger _nameChanger;
    private NameTagManipulator _nameTagManipulator;
    //private EditableSign _editableSign;
    private SignEdit _signEdit;
    private TeleportManager _teleportManager;
   // private PlayerSkinChanger _playerSkinChanger;
    
    // 0 - Just because
    private FireWork _fireWork;

	private CoreListener()
	{
		_instance = this;
	}	

	public static CoreListener Instance()
	{
		return _instance == null ? new CoreListener() : _instance;
	}    
	
	public void LoadAllData()
	{
		_cmWorldList.ReloadWorlds();
		_cmRankList.ReloadRanks();
		_cmWarpList.ReloadWarps();
	}
	
	public void ListEverything()
	{
		CommandSender sender = Bukkit.getConsoleSender();
		
		_cmWorldList.ListWorlds(sender); 
		_cmRankList.ListAllRanks(sender); 
		_cmWarpList.ListAllWarps(sender);		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		boolean successful = true;
		String command = commandLabel.toLowerCase();

		try
		{
			switch(command)
			{
				// Default
				case "me" :
				case "whoisme" :
				case "whoami" :
					if(_bukkitUtility.IsSenderPlayer(sender))
					{
						Player player = (Player)sender;
						CMPlayerList cmPlayerList = CMPlayerList.Instance();
						CMPlayer cmplayer = cmPlayerList.GetPlayer(player);
						cmplayer.GetPlayerInfo(sender);
					}
					else
					{										
						_logger.SendToSender(Module.System, MessageType.Warning, sender, _config.Messages.ConsoleIO.NotForConsole);
					}
					break;
					
				case "day" : 
					_bukkitUtility.Day(); 
				     break;
				     
				case "night" :  
					_bukkitUtility.Night();
				     break;
				
				case "rain" :  
					_bukkitUtility.Rain();
				     break;
				
				case "exit" :
					_bukkitUtility.ShutdownServer();
				      break;
				
				case "gm" :
					_playerEditor.ChangeGameMode(sender, args);	
				    break;
				
				case "home" : 
					break;
				
				case "heal" :
					_playerEditor.Heal(sender);			
				   break;

				// Teleport
				case "w" :
				case "warps" : 
					_cmWarpList.ListAllWarps(sender);
					break;
					
				case "warp":
					_teleportManager.Warp(sender, args); 
					break;
					
				case "setwarp":
					_teleportManager.SetWarp(sender, args);
					break;
					
				case "delwarp":
					_teleportManager.DeleteWarp(sender, args);
					break;
					
				case "spawn": 
					_teleportManager.TeleportToWorldSpawn(sender); 
					break;
					
				case "setspawn": 
					_teleportManager.SetSpawn(sender);
					break;
					
				case "tp" :
				case "teleport" :
					_teleportManager.TeleportToPlayer(sender, args[0]); 
					break;
					
				case "tpw" :
				case "teleporttoworld" :
					_teleportManager.TeleportToWorld(sender, args[0]);
					break;

				// Namechanger
				case "cn" :
				case "nick" :
				case "changename" : 
					_nameChanger.ChangeNameCommand(sender, args);
					_nameTagManipulator.UpdatePlayerTag(sender);						
					break;

				// Change Skin
				case "cs" :
				case "changeskin" :
					if(_bukkitUtility.IsSenderPlayer(sender))
					{
						PlayerSkinChanger playerSkinChanger = new PlayerSkinChanger();
						playerSkinChanger.ChangeSkin(sender, args[0]);
					}					
					break;

				// Multiverse
				case "dw" :
				case "deleteworld" : 
					_worldManager.DeleteWorldCommand(sender, args); 
					break;
				
				case "cw" :
				case "createworld" : 
					_worldManager.CreateNewWorldCommand(sender, args);
					break;
				
				case "lw" :
				case "listworlds" : 
					_cmWorldList.ListWorlds(sender); 
					break;				

				// RankSystem
				case "cr" :
				case "changerank" : 
					_cmRankList.ChangeRankCommand(sender, args);
					_nameTagManipulator.UpdatePlayerTag(sender);
					break;
					
				case "rm" :
				case "removerank" :
				case "dr" :
				case "deleterank" : 
					_cmRankList.ResetPlayerRank(sender, args);
					_nameTagManipulator.UpdatePlayerTag(sender);
					break;

				case "lr" :
				case "listranks" : 
					_cmRankList.ListAllRanks(sender); 
					break;

				// PlayerTag
				case "cpt" :
				case "changeplayertag" :  
					break;


				// Database
				case "sql" :
					switch (args.length)
					{
						case 1 :
							switch (args[0])
							{
								case "rp" :
								case"registerplayer" :
								break;
								default: successful = false;
							}
							break;

						default: successful = false;
					}
				default :
					successful = false;
					_logger.SendToSender(Module.System, MessageType.Warning, sender, "Unkown command");

			}

		}
		catch(Exception e)
		{
			String message = "&cThere was an error while using a command." + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();
			
			_logger.SendToConsole(Module.EventSystem, MessageType.Error, message);
			
			e.printStackTrace();
		}

		return successful;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt)
	{
		int numberOfPlayers;
		Player player = evt.getPlayer();
		String players;
		String playerName;
		String playerRalName = player.getName();
		String playerCustomName;
		String firstJoinMessage;
		String reJoinMessage;
		
		try
		{
			boolean isRegistered =  _databaseManager.DoesPlayerExist(player);

			if(!isRegistered)
			{
				firstJoinMessage =  _config.Messages.Chat.System + _config.Messages.Chat.FirstJoin;
				
				_logger.SendToConsole(Module.PlayerList, MessageType.Info, "&aNew playerdetected!\n" +
						"UUID : " + player.getUniqueId() + "\n" +
						"Name : " + player.getName());
				

				numberOfPlayers = _databaseManager.GetAmountOfRegisteredPlayers();

				players = Integer.toString( numberOfPlayers+ 1);

				playerCustomName = player.getCustomName();
				
				playerName = playerCustomName == null ? playerRalName : playerCustomName;

				firstJoinMessage = firstJoinMessage.replace("%player%", playerName);
				firstJoinMessage = firstJoinMessage.replace("%nr%", players);

				_logger.SendToSender(Module.System, MessageType.None, player, firstJoinMessage);
				
				_databaseManager.RegisterNewPlayer(player);
			}
			else
			{	
				reJoinMessage = _config.Messages.Chat.System + _config.Messages.Chat.ReJoin;
				
				_logger.SendToConsole(Module.EventSystem, MessageType.Info, "&8Old playerdetected!\n" +
						"UUID : " + player.getUniqueId() + "\n" +
						"Name : " + player.getName());
				
				_databaseManager.LoadPlayer(player);

				_logger.SendToSender(Module.ChatSystem, MessageType.None, player, reJoinMessage);
			}
		}
		catch(Exception e)
		{
			_logger.SendToConsole(Module.EventSystem, MessageType.Error,"&cError in event PlayerJoin! " + e.getMessage());
		}

		try
		{
			_nameTagManipulator.UpdatePlayerTag(player);
		}
		catch (Exception e)
		{
			_logger.SendToConsole(Module.NameTagManipulator, MessageType.Error,"&c nameTagManipulator.UpdatePlayerTag() has caused an error! " + e.getMessage());
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();

		try
		{
			_fireWork.CreateExplosion(player);
			_chatManager.OnJoin(e);
		}
		catch(Exception ex)
		{
			_logger.SendToConsole(Module.EventSystem, MessageType.Error,"Error in event OnJoin! " + ex.getMessage());
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e)
	{
		Player player = e.getPlayer();

		try
		{
			_databaseManager.UpdatePlayerLastSeen(player);
			_chatManager.OnLeave(e);
			_cmPlayerList.RemovePlayer(player);
		}
		catch (Exception exception)
		{
			_logger.SendToConsole(Module.EventSystem, MessageType.Error, "Error in Event OnLeave! + "  + exception.getMessage());
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e)
	{		
		try
		{
			_chatManager.OnChat(e);
		}
		catch (Exception exception)
		{
			String errorMessage = "Error in Event onChat! "  +  _config.Messages.ConsoleIO.ErrorArrow + exception.getMessage();
			
			_logger.SendToConsole(Module.EventSystem, MessageType.Error, errorMessage);
		}
	}

	/*
	@EventHandler
	public void onSignChangeEvent(SignChangeEvent e)
	{
		//_editableSign.OnSignChangeEvent(e);
	}

	@EventHandler
	public void OnSignClick(PlayerInteractEvent event)
	{

	}

	@EventHandler
	public void OnPlayerInteract(PlayerInteractEvent event)
	{

	}

	*/
	@EventHandler
	public void onSignPlace(SignChangeEvent event)
	{
		_signEdit.OnSignPlace(event);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{
		_signEdit.OnInteract(event);
	}	

	public void LoadAllInstances()
	{
		_logger = Logger.Instance();
		_config = Config.Instance();	
		MessageTags.Instance();
		_bukkitUtility = BukkitUtility.Instance();		
		_databaseManager = DatabaseManager.Instance();		
    	_cmWorldList = CMWorldList.Instance();
    	_cmRankList = CMRankList.Instance();
    	//_cmPermissionList = CMPermissionList.Instance();
    	_cmPlayerList = CMPlayerList.Instance();
    	_cmWarpList = CMWarpList.Instance();  
    	//_permissionManager = PermissionManager.Instance(); 	
    	_pingTabList = PingTabList.Instance();		
    	_nameChanger = NameChanger.Instance();
    	_playerEditor = PlayerEditor.Instance();
    	_teleportManager = TeleportManager.Instance();
    	//_playerSkinChanger = PlayerSkinChanger.Instance();
    	_nameTagManipulator = NameTagManipulator.Instance();
    	_chatManager = ChatManager.Instance();
    	_signEdit = SignEdit.Instance(); 
    	_fireWork = FireWork.Instance();    	
	}


	public void RegisterAllEvents(JavaPlugin plugin) 
	{		 
		BukkitUtility.SetPluginInstance(plugin);
		_pluginManager = _bukkitUtility.GetPluginManager();			
		
		_pluginManager.registerEvents(this, plugin);
		   
	   _pingTabList.runTaskTimerAsynchronously(plugin, 0, _config.Ping.PingTabListDelayMs);	 
	   
	   _signEdit.SetJavaPlugin(plugin);	   
	   _signEdit.setReEditSignMethod();	   
	   
	   _nameTagManipulator.Init();
	}
}