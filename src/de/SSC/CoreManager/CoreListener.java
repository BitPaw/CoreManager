package de.SSC.CoreManager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.SSC.Main;
import de.SSC.CoreManager.Addons.DoubleJump;
import de.SSC.CoreManager.Addons.FireWork;
import de.SSC.CoreManager.Addons.DigitalClock.DigitalClockSystem;
import de.SSC.CoreManager.Addons.DropHead.DropHeadSystem;
import de.SSC.CoreManager.Addons.MobHealth.MobHealth;
import de.SSC.CoreManager.Addons.Timber.Timber;
import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Systems.Chat.ChatSystem;
import de.SSC.CoreManager.Systems.Chat.Logger;
import de.SSC.CoreManager.Systems.Chat.MessageTags;
import de.SSC.CoreManager.Systems.Chat.MessageType;
import de.SSC.CoreManager.Systems.Chat.Module;
import de.SSC.CoreManager.Systems.DataBase.DataBaseSystem;
import de.SSC.CoreManager.Systems.NameTag.NameTagSystem;
import de.SSC.CoreManager.Systems.Permission.PermissionSystem;
import de.SSC.CoreManager.Systems.Player.CMPlayer;
import de.SSC.CoreManager.Systems.Player.PlayerSystem;
import de.SSC.CoreManager.Systems.Player.RedundantGameModeChangeException;
import de.SSC.CoreManager.Systems.Rank.RankNotFoundException;
import de.SSC.CoreManager.Systems.Rank.RankSystem;
import de.SSC.CoreManager.Systems.Rank.RedundantRankChangeException;
import de.SSC.CoreManager.Systems.Region.RegionSystem;
import de.SSC.CoreManager.Systems.Sign.SignEditSystem;
import de.SSC.CoreManager.Systems.Tab.TabSystem;
import de.SSC.CoreManager.Systems.Warp.WarpSystem;
import de.SSC.CoreManager.Systems.World.WorldSystem;
import de.SSC.CoreManager.Utility.BukkitUtility;
import de.SSC.CoreManager.Utility.TooFewParameterException;
import de.SSC.CoreManager.Utility.TooManyParameterException;

public class CoreListener implements Listener
{
	private static CoreListener _instance;
	private PluginManager _pluginManager;
	private JavaPlugin _javaPlugin;
	
	private BukkitUtility _bukkitUtility;
	private Logger _logger;
	private Config _config;
	private DataBaseSystem _databaseManager;
	private ChatSystem _chatSystem;
	private CoreSystem _coreSystem;
	private NameTagSystem _nameTagSystem;
	private MessageTags _messageTags;
	private PermissionSystem _permissionSystem;
	private PlayerSystem _playerSystem;
	private RankSystem _rankSystem;
	private RegionSystem _regionSystem;
	private SignEditSystem _signEditSystem;
	private TabSystem _tabSystem;
	private WarpSystem _warpSystem;
	private WorldSystem _worldSystem;	
	private FireWork _fireWork;
	
	
	private Timber _timber;
	private MobHealth _mobHealth;
	private DoubleJump _doubleJump;
	private DropHeadSystem _dropHeadSystem;
	private DigitalClockSystem _digitalClockSystem;
	
	private CoreListener()
	{
		_instance = this;
	}

	public static CoreListener Instance()
	{
		return _instance == null ? new CoreListener() : _instance;
	}

	public void LoadAllInstances()
	{
		_logger = Logger.Instance();
		_config = Config.Instance();
		_bukkitUtility = BukkitUtility.Instance();
		_databaseManager = DataBaseSystem.Instance();

		_chatSystem = ChatSystem.Instance();
		_coreSystem = CoreSystem.Instance();
		_nameTagSystem = NameTagSystem.Instance();
		_messageTags = MessageTags.Instance();
		_permissionSystem = PermissionSystem.Instance();
		_playerSystem = PlayerSystem.Instance();
		_rankSystem = RankSystem.Instance();
		_regionSystem = RegionSystem.Instance();
		_signEditSystem = SignEditSystem.Instance();
		_tabSystem = TabSystem.Instance();
		_warpSystem = WarpSystem.Instance();
		_worldSystem = WorldSystem.Instance();

		_fireWork = FireWork.Instance();
		
		_timber = Timber.Instance();
		_doubleJump = DoubleJump.Instance();
		_mobHealth = MobHealth.Instance();		
		_dropHeadSystem = DropHeadSystem.Instance();
		
		_digitalClockSystem = new DigitalClockSystem();
	}

	public void LoadAllReferences()
	{
		_worldSystem.LoadReferences();
	}

	public void RegisterAllEvents(JavaPlugin plugin)
	{
		_pluginManager = _bukkitUtility.GetPluginManager();

	
		_pluginManager.registerEvents(this, plugin);
		 
		_digitalClockSystem.RegisterTasks(plugin, Bukkit.getServer());
		

		// Check if this task is already running
		try
		{
			_tabSystem.runTaskTimerAsynchronously(plugin, 0, _config.Ping.PingTabListDelayMs);
		}
		catch(IllegalStateException illegalStateException)
		{
			_logger.SendToConsole(Module.System, MessageType.Error, "Could not Register task for TabSystem. Is it already running?\n" + illegalStateException.getMessage());
		}		

		_signEditSystem.SetJavaPlugin(plugin);
		_signEditSystem.setReEditSignMethod();

		_nameTagSystem.Init();
	}

	public void CancelAllEvents()
	{
		_tabSystem.cancel();
	}
	
	public void LoadAllData()
	{
		_worldSystem.ReloadWorlds();
		_rankSystem.ReloadRanks();
		_warpSystem.ReloadWarps();
		_regionSystem.ReloadRegions();
		_permissionSystem.ReloadPermissions();

		_config.Save();		
		
		_playerSystem.CheckForUndetectedPlayers();
	}

	public void ListEverything()
	{
		CommandSender sender = Bukkit.getConsoleSender();

		_coreSystem.GetSystemInfos(sender);
		
		_worldSystem.ListWorlds(sender);
		_rankSystem.ListAllRanks(sender);
		_warpSystem.ListAllWarps(sender);
		_regionSystem.ListAllRegions(sender);
		_permissionSystem.ListPermissions(sender);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		boolean successful = true;
		String command = commandLabel.toLowerCase();

		try
		{
			switch (command)
			{
			case "hat":
				
				break;
				
			case "head":
				_dropHeadSystem.Command.GetHead(sender, args);
				break;
				
			case "creload":
				CancelAllEvents();
				
				_bukkitUtility.ReloadPlugin(_javaPlugin);
				break;
			
				// Default
			case "lag":
				_coreSystem.CallGarbageCollector();
				_logger.SendToSender(Module.System, MessageType.Info, sender, "System cleaned.");
				break;
				
			case "me":
			case "whoisme":
			case "whoami":
				if (_bukkitUtility.PlayerUtility.IsSenderPlayer(sender))
				{
					Player player = (Player)sender;
					CMPlayer cmplayer = _playerSystem.GetPlayer(player);
					cmplayer.GetPlayerInfo(sender);
				}
				else
				{
					_logger.SendToSender(Module.System, MessageType.Warning, sender, _config.Messages.ConsoleIO.NotForConsole);
				}
				break;

			case "day":
				_bukkitUtility.WeatherUtility.Day();
				break;

			case "night":
				_bukkitUtility.WeatherUtility.Night();
				break;

			case "rain":
				_bukkitUtility.WeatherUtility.Rain();
				break;

			case "exit":
				_bukkitUtility.ShutdownServer();
				break;

			case "gm":
				_playerSystem.ChangeGameMode(sender, args);
				break;

			case "heal":
				_playerSystem.Heal(sender);
				break;
				
			case "pc":
			case "printcolors":
				_bukkitUtility.PrintColors(sender);
				break;			
				
			case "clearchat":
			case "cc":
				_chatSystem.ClearChat(sender);
				break;
				
				
				
				
				
				
				
				
				
				

				// Teleport
			case "home":
				_warpSystem.TeleportHome(sender, args);
				break;
				
			case "w":
			case "warps":
				_warpSystem.ListAllWarps(sender);
				break;

			case "warp":
				_warpSystem.Warp(sender, args);
				break;

			case "setwarp":
				_warpSystem.SetWarp(sender, args);
				break;

			case "delwarp":
				_warpSystem.DeleteWarp(sender, args);
				break;

			case "spawn":
				_warpSystem.TeleportToWorldSpawn(sender);
				break;

			case "setspawn":
				_warpSystem.SetSpawn(sender);
				break;
				
			case "ws":
			case "wspawn":
			case "worldspawn":
				_warpSystem.TeleportToWorldSpawn(sender);
				break;
						
			case "tp":
			case "teleport":
				_warpSystem.TeleportToPlayer(sender, args);
				break;
				
			case "tpl":
			case "teleporttolocation":
				_warpSystem.TeleportToSpecificLocation(sender, args);
				break;

			case "tpw":
			case "teleporttoworld":
				_warpSystem.TeleportToWorld(sender, args);
				break;

				
				
				
				
				
				
				
				
				
				// Player System
			case "cn":
			case "nick":
			case "changename":
				_playerSystem.ChangeNameCommand(sender, args);
				_nameTagSystem.UpdatePlayerTag(sender);
				break;

				// Change Skin
			case "cs":
			case "changeskin":
				_playerSystem.ChangeSkin(sender, args[0]);
				break;

			case "speed":
				_playerSystem.ChangeSpeed(sender, args);		
				break;		
				
				
				
				
				
				
				// Multiverse
			case "dw":
			case "deleteworld":
				_worldSystem.DeleteWorldCommand(sender, args);
				break;

			case "cw":
			case "createworld":
				_worldSystem.CreateNewWorldCommand(sender, args);
				break;

			case "lw":
			case "listworlds":
				_worldSystem.ListWorlds(sender);
				break;

				
				
				
				
				
				
				
				
				
				// RankSystem
			case "cr":
			case "changerank":
				_rankSystem.ChangeRankCommand(sender, args);
				_nameTagSystem.UpdatePlayerTag(sender);
				break;

			case "rm":
			case "removerank":
			case "dr":
			case "deleterank":
				_rankSystem.ResetPlayerRank(sender, args);
				_nameTagSystem.UpdatePlayerTag(sender);
				break;

			case "lr":
			case "listranks":
				_rankSystem.ListAllRanks(sender);
				break;
				
				
				
				
				
				
				
				
				
				

				// PlayerTag
			case "cpt":
			case "changeplayertag":
				break;

				
			case "deop":
				_bukkitUtility.PlayerUtility.SetPlayerAsOperator(sender, args);
				break;
			
			case "op" :
				_bukkitUtility.PlayerUtility.RevertPlayerAsOperator(sender, args);
				break;
				
				
				
				
				
				// Database
			case "sql":
				switch (args.length)
				{
				case 1:
					switch (args[0])
					{
					case "rp":
					case"registerplayer":
						break;
					default: successful = false;
					}
					break;

				default: successful = false;
				}
			default:
				successful = false;
				_logger.SendToSender(Module.System, MessageType.Warning, sender, "Unkown command");

			}
		}
		catch(TooManyParameterException tooManyParameterException)
		{
			String message = _config.Messages.ConsoleIO.TooManyParameters;
			    		
   		 	_logger.SendToSender(Module.PlayerSystem, MessageType.Warning, sender, message);
		}
		catch(TooFewParameterException tooFewParameterException)
		{
			String message = "&7Too &cfew &7parameters!";
			   		
   		 	_logger.SendToSender(Module.PlayerSystem, MessageType.Warning, sender, message);
		}
		catch(RedundantGameModeChangeException redundantGameModeChangeException)
		{
			 String message = _config.Messages.Player.YouAreAlreadyInThisGameMode;
			 
    		 message = _messageTags.ReplaceGameMode(message, redundantGameModeChangeException.PlayerGameMode);
    		
    		 _logger.SendToSender(Module.PlayerSystem, MessageType.Warning, sender, message);
		}
		catch (RankNotFoundException rankNotFoundException)
		{
			String message = "&7Rank &7<&c" + rankNotFoundException + "&7> &4not &7found!";

			_logger.SendToSender(Module.EventSystem, MessageType.Error, sender, message);
		}
		catch(RedundantRankChangeException redundantRankChangeException)
		{
			String message = "&7Player is &calready &7on this rank&8!";

			_logger.SendToSender(Module.EventSystem, MessageType.Error, sender,message);
		}
		catch (Exception e)
		{
			String message = "&cThere was an error while using a command." + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();

			_logger.SendToSender(Module.EventSystem, MessageType.Error, sender, message);

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
		String playerCustomName;
		String firstJoinMessage;
		String reJoinMessage;

		try
		{
			boolean isRegistered = _databaseManager.DoesPlayerExist(player);

			if (!isRegistered)
			{
				firstJoinMessage = _config.Messages.Chat.System + _config.Messages.Chat.FirstJoin;

				numberOfPlayers = _databaseManager.GetAmountOfRegisteredPlayers();

				players = Integer.toString(numberOfPlayers + 1);

				playerCustomName = _bukkitUtility.PlayerUtility.GetPlayerCustomName(player);

				firstJoinMessage = firstJoinMessage.replace("%player%", playerCustomName);
				firstJoinMessage = firstJoinMessage.replace("%nr%", players);

				_logger.SendToSender(Module.System, MessageType.None, player, firstJoinMessage);

				_databaseManager.RegisterNewPlayer(player);
			}
			else
			{
				reJoinMessage = _config.Messages.Chat.System + _config.Messages.Chat.ReJoin;
			
				_databaseManager.LoadPlayer(player);
				
				_databaseManager.UpdatePlayer(player);
								
				_logger.SendToSender(Module.ChatSystem, MessageType.None, player, reJoinMessage);
			}
		}
		catch (Exception e)
		{
			_logger.SendToConsole(Module.EventSystem, MessageType.Error, "&cError in event OnPlayerJoin! " + e.getMessage());
		}

		try
		{
			_nameTagSystem.UpdatePlayerTag(player);
		}
		catch (Exception e)
		{
			_logger.SendToConsole(Module.NameTagSystem, MessageType.Error, "&c nameTagManipulator.UpdatePlayerTag() has caused an error! " + e.getMessage());
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();

		try
		{
			_fireWork.CreateExplosion(player);
			_chatSystem.OnJoin(e);
		}
		catch (Exception ex)
		{
			_logger.SendToConsole(Module.EventSystem, MessageType.Error, "Error in event OnJoin! " + ex.getMessage());
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e)
	{
		Player player = e.getPlayer();

		try
		{
			_databaseManager.UpdatePlayer(player);
			_chatSystem.OnLeave(e);
			_playerSystem.RemovePlayer(player);
		}
		catch (Exception exception)
		{
			_logger.SendToConsole(Module.EventSystem, MessageType.Error, "Error in Event OnLeave! + " + exception.getMessage());
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e)
	{
		try
		{
			_chatSystem.OnChat(e);
		}
		catch (Exception exception)
		{
			String errorMessage = "Error in Event onChat! " + _config.Messages.ConsoleIO.ErrorArrow + exception.getMessage();

			_logger.SendToConsole(Module.EventSystem, MessageType.Error, errorMessage);
		}
	}

	//@EventHandler(priority= EventPriority.MONITOR)
	
	@EventHandler
	public void OnPlayerMove(PlayerMoveEvent e)
	{
		_doubleJump.OnPlayerMove(e);
	}
	
	@EventHandler
	public void OnFly(PlayerToggleFlightEvent e)
	{
		_doubleJump.OnFly(e);
	}
	
	@EventHandler
	public void OnSneak(PlayerToggleSneakEvent e)
	{
		_doubleJump.OnSneak(e);
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
	public void onPing(ServerListPingEvent event) 
	{
		String playerName;
		String ip = event.getAddress().getHostName();
		String newMotd = event.getMotd();
		
		_logger.SendToConsole(Module.EventSystem, MessageType.Info, "Some pinged the Server.");
		
		playerName = _databaseManager.GetPlayerNameFromIP(ip);
		
		if(playerName == null)
		{
			newMotd = _messageTags.ReplacePlayerTag(newMotd, "New Player");
		}
		else
		{
			newMotd = _messageTags.ReplacePlayerTag(newMotd, playerName);
		}
		
		event.setMotd(newMotd);
	}
	
	@EventHandler
	public void onSignPlace(SignChangeEvent event)
	{
		_signEditSystem.OnSignPlace(event);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{
	
		
		if(event != null)
		{
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
			{
				_signEditSystem.OnInteract(event);	
			}					
		}	
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) 
	{
		EquipmentSlot e;
		
		if(event != null)
		{
			  e = event.getHand(); //Get the hand of the event and set it to 'e'.
			  
		        if (e.equals(EquipmentSlot.HAND))  //If the event is fired by HAND (main hand)
		        { 
		        	_regionSystem.OnInteract(event);
		        }
		
		}
	}
	
	@EventHandler
	  public void onEntityExplode(EntityExplodeEvent event) 
	{
		/*
	        if (event.getEntity() instanceof Creeper) {
	            for (Block block : event.blockList().toArray(new Block[event.blockList().size()])){
	                if(block.getType() == YOURTYPE){
	                 
	                }
	            }
	        }
	        */
	    }
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{	
		Player player = event.getPlayer();
		
		if(player.isSneaking())
		{
			_timber.OnBlockBreak(event);
		}
		
	
		
		/*
		  	
		boolean hasPlayerPermission = _permissionSystem.HasPlayerPermissionToBreakBlock(player);
		  
		if(!hasPlayerPermission)
		{
			event.setCancelled(true);
			_logger.SendToSender(Module.PermissionSystem, MessageType.Warning, player, "&7You &cdont &7have the permission to modfy this area.");
		}
		else
		{
			
		}
		*/
	}
	

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event)	   
	{		
		_mobHealth.OnDamage(event);
	}

	public void SetJavaPlugin(Main main)
	{
		_javaPlugin = main;	
	}
	
}