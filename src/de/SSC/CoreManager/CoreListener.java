package de.SSC.CoreManager;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.SSC.CoreManagerCommand;
import de.SSC.CoreRunnable;
import de.SSC.Main;
import de.SSC.BukkitAPI.BukkitAPISystem;
import de.SSC.Chairs.ChairsSystem;
import de.SSC.CoreManager.Chat.ChatSystem;
import de.SSC.CoreManager.Chat.Logger;
import de.SSC.CoreManager.Chat.MessageTags;
import de.SSC.CoreManager.Chat.MessageType;
import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DataBaseSystem;
import de.SSC.CoreManager.Economy.EconemySystem;
import de.SSC.CoreManager.Economy.Exception.InvalidAmountParameterException;
import de.SSC.CoreManager.Economy.Exception.NegativeAmountException;
import de.SSC.CoreManager.Economy.Exception.NotEnoghMoneyException;
import de.SSC.CoreManager.Economy.Exception.NullAmountException;
import de.SSC.CoreManager.Economy.Exception.RedundantTransactionException;
import de.SSC.CoreManager.Economy.Exception.TooMuchMoneyException;
import de.SSC.CoreManager.NameTag.NameTagSystem;
import de.SSC.CoreManager.Permission.PermissionSystem;
import de.SSC.CoreManager.Player.PlayerSystem;
import de.SSC.CoreManager.Player.Exception.InvalidGameModeException;
import de.SSC.CoreManager.Player.Exception.InvalidPlayerNameException;
import de.SSC.CoreManager.Player.Exception.InvalidPlayerUUID;
import de.SSC.CoreManager.Player.Exception.PlayerDoesNotExistException;
import de.SSC.CoreManager.Player.Exception.PlayerNotFacingDirectionException;
import de.SSC.CoreManager.Player.Exception.PlayerNotFoundException;
import de.SSC.CoreManager.Player.Exception.PlayerOfflineException;
import de.SSC.CoreManager.Player.Exception.RedundantGameModeChangeException;
import de.SSC.CoreManager.Rank.RankSystem;
import de.SSC.CoreManager.Rank.Exception.RankNotFoundException;
import de.SSC.CoreManager.Rank.Exception.RedundantRankChangeException;
import de.SSC.CoreManager.Region.CMRegion;
import de.SSC.CoreManager.Region.RegionSystem;
import de.SSC.CoreManager.Sign.SignEditSystem;
import de.SSC.CoreManager.Skin.SkinSystem;
import de.SSC.CoreManager.Skin.Exception.OfflineSkinLoadException;
import de.SSC.CoreManager.Skin.Exception.SkinLoadException;
import de.SSC.CoreManager.Sound.SoundSystem;
import de.SSC.CoreManager.System.CoreSystem;
import de.SSC.CoreManager.System.SystemList;
import de.SSC.CoreManager.System.Exception.NotForConsoleException;
import de.SSC.CoreManager.System.Exception.SystemNotActiveException;
import de.SSC.CoreManager.System.Exception.TooFewParameterException;
import de.SSC.CoreManager.System.Exception.TooManyParameterException;
import de.SSC.CoreManager.Tab.TabSystem;
import de.SSC.CoreManager.Teleport.TeleportSystem;
import de.SSC.CoreManager.Warp.Warp;
import de.SSC.CoreManager.Warp.WarpSystem;
import de.SSC.CoreManager.Warp.Exception.WarpNotFoundException;
import de.SSC.CoreManager.World.WorldSystem;
import de.SSC.CoreManager.World.Exception.InvalidWorldNameException;
import de.SSC.DigitalClock.DigitalClockSystem;
import de.SSC.DoubleJump.DoubleJump;
import de.SSC.DropHead.DropHeadSystem;
import de.SSC.FireWork.FireWork;
import de.SSC.MobHealth.MobHealth;
import de.SSC.NPC.NPCSystem;
import de.SSC.Timber.Timber;

public class CoreListener implements Listener
{
	private static CoreListener _instance;
	private PluginManager _pluginManager;
	//private JavaPlugin _javaPlugin;
	
	private SystemList _systemList;
	
	private BukkitAPISystem _bukkitAPISystem;
	private Logger _logger;
	private Config _config;
	private DataBaseSystem _databaseManager;
	private ChatSystem _chatSystem;
	private CoreSystem _coreSystem;
	private NameTagSystem _nameTagSystem;
	private MessageTags _messageTags;
	private SoundSystem _soundSystem;
	private PermissionSystem _permissionSystem;
	private PlayerSystem _playerSystem;
	private RankSystem _rankSystem;
	private RegionSystem _regionSystem;
	private SignEditSystem _signEditSystem;
	private TabSystem _tabSystem;
	private WarpSystem _warpSystem;
	private WorldSystem _worldSystem;
	private TeleportSystem _teleportSystem;
	private FireWork _fireWork;
	private EconemySystem _econemySystem;
	private SkinSystem _skinSystem;
	
	
	private Timber _timber;
	private MobHealth _mobHealth;
	private DoubleJump _doubleJump;
	private DropHeadSystem _dropHeadSystem;
	private DigitalClockSystem _digitalClockSystem;
	private ChairsSystem _chairsSystem;
	private NPCSystem _npcSystem;
	
	private CoreRunnable _coreRunnable;
	
	private CoreListener()
	{
		_instance = this;		
		
		_systemList = new SystemList();
	}

	public static CoreListener Instance()
	{
		return _instance == null ? new CoreListener() : _instance;
	}

	private void LoadAllInstances()
	{		
		//---[Core]------------------------------------------------------------------
		_config = Config.Instance();
		_systemList.Add(_config);			
		
		_logger = Logger.Instance();
		_systemList.Add(_logger);	
		
		_bukkitAPISystem = BukkitAPISystem.Instance();
		_systemList.Add(_bukkitAPISystem);	
		
		_databaseManager = DataBaseSystem.Instance();
		_systemList.Add(_databaseManager);	
		
		_chatSystem = ChatSystem.Instance();
		_systemList.Add(_chatSystem);	
		
		_coreSystem = CoreSystem.Instance();
		_systemList.Add(_coreSystem);	
		
		_coreRunnable = CoreRunnable.Instance();
		_systemList.Add(_coreRunnable);	
				
		//---[Important]------------------------------------------------------------------
				
		_worldSystem = WorldSystem.Instance();
		_systemList.Add(_worldSystem);
		
		_warpSystem = WarpSystem.Instance();
		_systemList.Add(_warpSystem);
		
		_rankSystem = RankSystem.Instance();
		_systemList.Add(_rankSystem);
		
		_permissionSystem = PermissionSystem.Instance();
		_systemList.Add(_permissionSystem);
		
		_regionSystem = RegionSystem.Instance();
		_systemList.Add(_regionSystem);
				
		_econemySystem = EconemySystem.Instance();
		_systemList.Add(_econemySystem);
		
		_playerSystem = PlayerSystem.Instance();
		_systemList.Add(_playerSystem);
		
		//---[Extra]------------------------------------------------------------------
		
		_messageTags = MessageTags.Instance();
		_systemList.Add(_messageTags);
		
		_nameTagSystem = NameTagSystem.Instance();
		_systemList.Add(_nameTagSystem);
		
		_soundSystem = SoundSystem.Instance();		
		_systemList.Add(_soundSystem);
		
		_signEditSystem = SignEditSystem.Instance();
		_systemList.Add(_signEditSystem);
		
		_tabSystem = TabSystem.Instance();	
		_systemList.Add(_tabSystem);
		
		_teleportSystem = TeleportSystem.Instance();
		_systemList.Add(_teleportSystem);
		
		_fireWork = FireWork.Instance();		
		_systemList.Add(_fireWork);
		
		_timber = Timber.Instance();
		_systemList.Add(_timber);
		
		_doubleJump = DoubleJump.Instance();
		_systemList.Add(_doubleJump);
		
		_mobHealth = MobHealth.Instance();		
		_systemList.Add(_mobHealth);
		
		_dropHeadSystem = DropHeadSystem.Instance();
		_systemList.Add(_dropHeadSystem);
		
		_digitalClockSystem = DigitalClockSystem.Instance();
		_systemList.Add(_digitalClockSystem);
		
		_chairsSystem = ChairsSystem.Instance();
		_systemList.Add(_chairsSystem);	
		
		_npcSystem = NPCSystem.Instance();
		_systemList.Add(_npcSystem);
		
		_skinSystem = SkinSystem.Instance();
		_systemList.Add(_skinSystem);
	}

	public void Start()
	{			
		// Create
		LoadAllInstances();
		
		// Link
		_systemList.LoadAllReferences();   		
	
		// Execute		
		_systemList.ReloadAll(true);	
		
		//_config.Save();		
	}
	
	public void Stop()
	{
		_config.Save();	
	}	

	public void RegisterAllEvents(JavaPlugin plugin)
	{
		_pluginManager = Bukkit.getPluginManager();	
		_pluginManager.registerEvents(this, plugin);		 
	
		// runTaskTimerAsynchronously 60
		
		_coreRunnable.RegisterRunnable(_digitalClockSystem);
		_coreRunnable.RegisterRunnable(_tabSystem);
		_coreRunnable.RegisterRunnable(_npcSystem);
		
		_coreRunnable.runTaskTimer(plugin, 0, 1000);	

		_signEditSystem.SetJavaPlugin(plugin);
		_signEditSystem.setReEditSignMethod();
		
		_nameTagSystem.SetupScoreBoard();
	}

	public void CancelAllEvents()
	{
		_coreRunnable.cancel();
	}

	public void ListEverything()
	{
		CommandSender sender = Bukkit.getConsoleSender();

		_systemList.GetStatusFromAll(sender);		
		_systemList.ListAll(sender);				
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
	{
		boolean successful = true;
		
		try
		{
			final CoreManagerCommand command =  CoreManagerCommand.valueOf(commandLabel.toLowerCase());
			
			switch(command)
			{
			case cn:
			case changename:
				_playerSystem.ChangeNameCommand(sender, args);
				_nameTagSystem.UpdatePlayerTag(sender);
				break;
				
			case changeplayertag:
				break;
				
			case cr:
			case changerank:
				_rankSystem.ChangeRankCommand(sender, args);
				_nameTagSystem.UpdatePlayerTag(sender);
				break;
				
			case cs:
			case changeskin:
				_playerSystem.ChangeSkin(sender, args[0]);
				break;
				
			case cc:
			case clearchat:
				_chatSystem.ClearChat(sender);
				break;
				
			case ci:
			case clearinventory:
				_playerSystem.ClearInventory(sender, args);
				break;
				
			case cw:
			case createworld:
				_worldSystem.CreateNewWorldCommand(sender, args);
				break;
				
			case creload:
				CancelAllEvents();
				
				_systemList.ReloadAll(false);
				//_bukkitAPISystem.ReloadPlugin(_javaPlugin);
				break;
				
			case day:
				_bukkitAPISystem.Weather.Day();
				break;
				
			case dr:
			case deleterank:
				_rankSystem.ResetPlayerRank(sender, args);
				_nameTagSystem.UpdatePlayerTag(sender);
				break;
				
			case dw:
			case deleteworld:
				_worldSystem.DeleteWorldCommand(sender, args);
				break;
				
			case delwarp:
				_warpSystem.DeleteWarp(sender, args);
				break;
				
			case dc:
			case digitalclock:
				_digitalClockSystem.DigitalClockCommand(sender, args);
				break;
				
			case exit:
				_bukkitAPISystem.Server.ShutdownServer();
				break;
				
			case gm:
				_playerSystem.ChangeGameMode(sender, args);
				break;
				
			case hat:
				_playerSystem.PutItemOnHead(sender);	
				break;
				
			case head:
				_dropHeadSystem.GetHeadCommand(sender, args);
				break;
				
			case heal:
				_playerSystem.Heal(sender);
				break;
				
			case home:
				_teleportSystem.TeleportHome(sender, args);
				break;
				
			case lag:
				_coreSystem.CallGarbageCollector();
				_logger.SendToSender(Module.System, MessageType.Info, sender, "System cleaned.");
				break;
				
			case lr:
			case listranks:
				_rankSystem.ListAllRanks(sender);
				break;
				
			case lp:
			case listplayer:
				_playerSystem.PrintData(sender);
				break;
				
			case lw:
			case listworlds:
				_worldSystem.PrintData(sender);
				break;
				
			case m:
			case money:
				_econemySystem.GetAccountBalanceCommand(sender, args);
				break;
				
			case night:
				_bukkitAPISystem.Weather.Night();
				break;
				
			case npc:
				_npcSystem.NPCCommand(sender, args);
				break;
				
			case p:
			case pay:
				_econemySystem.PayCommand(sender, args);
				break;
				
			case pc:
			case printcolors:
				_logger.PrintColors(sender);
				break;
				
			case rain:
				_bukkitAPISystem.Weather.Rain();
				break;
				
			case setlocation:
				break;
				
			case setspawn:
				_worldSystem.SetGlobalSpawn(sender);				
				break;
				
			case setwarp:
				_warpSystem.SetWarp(sender, args);
				break;
				
			case sws:
			case setworldspawn:
				_worldSystem.SetWorldSpawn(sender);
				break;
				
			case spawn:
				_warpSystem.WarpToGlobalSpawn(sender);
				break;
				
			case speed:
				_playerSystem.ChangeSpeed(sender, args);		
				break;
				
			case sql:
				break;
				
			case sysi:
			case sysinfo:		
			case systeminfo:
				_coreSystem.PrintData(sender);
				break;
				
			case tp:
			case teleport:
				_teleportSystem.TeleportToPlayer(sender, args);
				break;
				
			case tpl:
			case teleporttolocation:
				_teleportSystem.TeleportToSpecificLocation(sender, args);
				break;
				
			case tpw:
			case teleporttoworld:
				_teleportSystem.TeleportToWorld(sender, args);
				break;
			
			case w:
			case warp:
				_warpSystem.WarpCommand(sender, args);
				break;
				
			case warps:
				_warpSystem.ListAllWarps(sender);
				break;
				
			case me:
			case whoami:
				_bukkitAPISystem.Player.GetPlayerInfo(sender, args);	
				break;
				
			case ws:
			case worldspawn:
				_teleportSystem.TeleportToWorldSpawn(sender);
				break;	
				
			default:
				successful = false;
				_logger.SendToSender(Module.System, MessageType.Warning, sender, "Unkown command");
				break;			

	
			}
		}
		catch(TooManyParameterException tooManyParameterException)
		{
			String message = _config.Messages.ConsoleIO.TooManyParameters;
			    		
   		 	_logger.SendToSender(Module.System, MessageType.Warning, sender, message);
		}
		catch(TooFewParameterException tooFewParameterException)
		{
			String message = "&7Too &cfew &7parameters!";
			   		
   		 	_logger.SendToSender(Module.System, MessageType.Warning, sender, message);
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

			_logger.SendToSender(Module.RankSystem, MessageType.Error, sender, message);
		}
		catch(RedundantRankChangeException redundantRankChangeException)
		{
			String message = "&7Player is &calready &7on this rank&8!";

			_logger.SendToSender(Module.RankSystem, MessageType.Error, sender,message);
		} 
		catch (PlayerNotFoundException playerNotFoundException)
		{			
			String message = "&7Player &6<&e" + playerNotFoundException.NotFoundPlayerName + "&6> &cnot &7found!";

			_logger.SendToSender(Module.PlayerSystem, MessageType.Error, sender,message);
		} 
		catch (InvalidPlayerUUID e) 
		{			
			e.printStackTrace();
		}
		catch (NotForConsoleException e) 
		{
			String message = "&7This command is &cnot &7useable in the &eConsole&7!";

			_logger.SendToSender(Module.System, MessageType.Error, sender,message);			
		}
		catch (InvalidGameModeException invalidGameModeException) 
		{
			String message = "&cInvalid &7GameMode &6<&e" + invalidGameModeException.WantedGameMode + "&6>&7.";

			_logger.SendToSender(Module.PlayerSystem, MessageType.Error, sender,message);
		}
		catch (InvalidPlayerNameException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (WarpNotFoundException warpNotFoundException) 
		{
			String message = "&eWarp &7with name &6<&e" + warpNotFoundException.MissingWarpName + "&6> &7does &cnot &7exist!";

			_logger.SendToSender(Module.WarpSystem, MessageType.Error, sender,message);
		} 
		catch (PlayerNotFacingDirectionException e)
		{
			String message = "&7You are &cnot &7facing a &edirection&7! &bCurrentdirection&3:&b" + e.CurrentlyFacingDirection;

			_logger.SendToSender(Module.PlayerSystem, MessageType.Warning, sender,message);
		} 
		catch (NotEnoghMoneyException e)
		{
			String message = "&7You have &cnot enogh &7money! Your are &6<&e" + e.MoneyDifference +  "&6> &7short";

			_logger.SendToSender(Module.System, MessageType.Warning, sender,message);
		} 
		catch (NullAmountException e) 
		{
			String message = "&7You gave nothing? Is that what you want?";

			_logger.SendToSender(Module.System, MessageType.Question, sender,message);
		} 
		catch (NegativeAmountException e) 
		{
			String message = "&7You &ccan't &7use negative values!";

			_logger.SendToSender(Module.System, MessageType.Error, sender,message);
		} 
		catch (TooMuchMoneyException e) 
		{
			String message = "&7You have &cmaxed out &7your accound! &aCongrats!";

			_logger.SendToSender(Module.System, MessageType.Warning, sender,message);
		} 
		catch (InvalidAmountParameterException e) 
		{
			String message = "&7Do you have the number wrong? &cInvalid &7number!";

			_logger.SendToSender(Module.System, MessageType.Error, sender,message);
		} 
		catch (RedundantTransactionException e)
		{
			String message = "&7You are trying to give &eyourself &7your &eown money. &8Why?";

			_logger.SendToSender(Module.System, MessageType.Error, sender,message);
		} 
		catch (InvalidWorldNameException e)
		{
			String message = "&7World &cnot &7found &6<&e" + e.getMessage() + "&6>&7.";

			_logger.SendToSender(Module.System, MessageType.Error, sender,message);
		} 
		catch (OfflineSkinLoadException offlineSkinLoadException) 
		{
			String message = "&7Skin could &cnot &7be loaded because the server is in &eoffline&7 mode!";

			_logger.SendToSender(Module.System, MessageType.Error, sender,message);
		}	
		catch(IllegalArgumentException IllegalArgumentException)
		{
			// wrong Enum constant			
		}
		catch(PlayerOfflineException PlayerOfflineException)
		{
			String message = "&7Player with name &6<&e" + PlayerOfflineException.OfflinePlayerName + "&6> &7is &coffline&7!";

			_logger.SendToSender(Module.System, MessageType.Error, sender,message);
		}
		catch (PlayerDoesNotExistException e) 
		{
			String message = "Player does not exist!";
			
			_logger.SendToSender(Module.System, MessageType.Error, sender,message);
		} 
		catch (SkinLoadException e) 
		{
			String message = "Skin could not be loaded!";
			
			_logger.SendToSender(Module.System, MessageType.Error, sender,message);
		} 
		catch (SystemNotActiveException e) 
		{
			String message = "&7This &esub-plugin &7is currently &cnot &7active. You need to &aenable &7it first to use it.";
			
			_logger.SendToSender(Module.System, MessageType.Error, sender,message);
		}
		catch(Exception exception)
		{
			_logger.SendToSender(Module.System, MessageType.Error, sender, "Unhandled Exception accured!");
			
			exception.printStackTrace();
		}	
	
		
/*			
		
				_worldSystem.SetWorldSpawn(sender);
				
	
			case "deop":
				_bukkitAPISystem.Player.SetPlayerAsOperator(sender, args);
				break;
			
			case "op" :
				_bukkitAPISystem.Player.RevertPlayerAsOperator(sender, args);
				break;

		

*/



		return successful;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent playerJoinEvent)
	{
		final Player player = playerJoinEvent.getPlayer();		
		
		_playerSystem.RegisterPlayer(player);
		
		_nameTagSystem.UpdatePlayerTag(player);
		
		_fireWork.CreateExplosion(player);
		
		_chatSystem.OnJoin(playerJoinEvent);
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e)
	{
		final Player player = e.getPlayer();
		final UUID playerUUID = player.getUniqueId();

		try
		{			
			_chatSystem.OnLeave(e);
			
			_databaseManager.Player.UpdatePlayer(player);
			
			_playerSystem.PlayerQuitEvent(playerUUID, player);
		}
		catch (Exception exception)
		{
			_logger.SendToConsole(Module.EventSystem, MessageType.Error, "Error in Event OnLeave! + " + exception.getMessage());
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e)
	{
		_chatSystem.OnChat(e);
	}
	
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
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent playerRespawnEvent)
	{		
		try 
		{
			final Warp warp = _warpSystem.GetGlobalSpawn();
			
			playerRespawnEvent.setRespawnLocation(warp.WarpLocation);
		} 
		catch (WarpNotFoundException e) 
		{
			// Do nothing
		}	
	}
	
	@EventHandler
	public void OnSignClick(PlayerInteractEvent event)
	{
		//final Player player = event.getPlayer();
		
		//_soundSystem.PlaySound(player, Sound.BLOCK_NOTE_BLOCK_BIT, 0);
	}

	@EventHandler
	public void onPing(ServerListPingEvent event) 
	{
		String playerName;
		String ip = event.getAddress().getHostName();
		String newMotd = event.getMotd();
		
		_logger.SendToConsole(Module.EventSystem, MessageType.Info, "Some pinged the Server.");
		
		playerName = _databaseManager.Player.GetPlayerNameFromIP(ip);
		
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
	public void onInteract(PlayerInteractEvent playerInteractEvent)
	{		
		if(playerInteractEvent != null)
		{
			final Action action = playerInteractEvent.getAction();
			final Player player = playerInteractEvent.getPlayer();			
			final boolean isRightClickedBlock = action == Action.RIGHT_CLICK_BLOCK;					
			final org.bukkit.Material handMaterial = player.getInventory().getItemInMainHand().getType();			
			final boolean isItemInHandCompass = handMaterial == org.bukkit.Material.COMPASS;
			
			if(isItemInHandCompass)
			{
				final Block block = player.getTargetBlock(null, 100);	
				final boolean isRightClickAir = action == Action.RIGHT_CLICK_AIR;
				final boolean isLeftClickAir =  action == Action.LEFT_CLICK_AIR ;
				
				if(isLeftClickAir)
				{				
					_teleportSystem.TeleportToFacingBlock(player, block);				
				}
				
				if(isRightClickAir)
				{
					_teleportSystem.TeleportBehindBlock(player, block);
				}
			}			
	
			
			if (isRightClickedBlock)
			{
				_signEditSystem.OnInteract(playerInteractEvent);	
			}					
		}	
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) throws InvalidWorldNameException 
	{
		EquipmentSlot e;
		
		if(event != null)
		{
			  e = event.getHand(); //Get the hand of the event and set it to 'e'.
			  
			  if(e != null)
			  {
				     if (e.equals(EquipmentSlot.HAND))  //If the event is fired by HAND (main hand)
				        { 
				        	_regionSystem.OnInteract(event);
				        }
			  }
			  
			  _chairsSystem.Event.OnPlayerInteract(event);	   
		
		}
	}
	
	@EventHandler
	public void onPlayerInteractEntityEvent ()
	{
		
	}
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) throws InvalidWorldNameException 
	{		
		_regionSystem.OnEntityExplode(event);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) throws PlayerNotFoundException, InvalidPlayerUUID, InvalidWorldNameException
	{
		final Player player = event.getPlayer();		
		final Block block = event.getBlock();
		final boolean hasPlayerPermission = _permissionSystem.HasPlayerPermissionToModifylock(player, block);
		
		if(hasPlayerPermission)
		{
			
		}
		else
		{
			event.setCancelled(true);
			
			_logger.SendToSender(Module.PermissionSystem, MessageType.Warning, player, "&7You &cdont &7have the permission to modfy this area.");
		}
	}
	
	@EventHandler
    public void onPlayerDoorOpen(PlayerInteractEvent event) throws PlayerNotFoundException, InvalidPlayerUUID, InvalidWorldNameException
    {
		final Player player = event.getPlayer();		
		final Block block = event.getClickedBlock();
		final boolean hasPlayerPermission = _permissionSystem.HasPlayerPermissionToModifylock(player,block);
		
		if(!hasPlayerPermission)
		{			
			event.setCancelled(true);
		}
    }
		
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) throws PlayerNotFoundException, InvalidPlayerUUID, InvalidWorldNameException
	{	
		final Player player = event.getPlayer();		
		final Block block = event.getBlock();
		final boolean hasPlayerPermission = _permissionSystem.HasPlayerPermissionToModifylock(player,block);
		
		if(hasPlayerPermission)
		{
			if(player.isSneaking())
			{
				try 
				{
					
					_timber.OnBlockBreak(event);
				} 
				catch (PlayerNotFoundException | InvalidPlayerUUID e)
				{
					e.printStackTrace();
				}
			}			
		}
		else
		{
			event.setCancelled(true);
			
			_logger.SendToSender(Module.PermissionSystem, MessageType.Warning, player, "&7You &cdont &7have the permission to modfy this area.");
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event)	   
	{		
		_mobHealth.OnDamage(event);
	}

	public void SetJavaPlugin(Main main)
	{
		//_javaPlugin = main;	
	}
	
	  @EventHandler
	  public void WitherProjectile(EntityExplodeEvent event) throws InvalidWorldNameException
	  {
	    if (event.isCancelled()) 
	    {
	      return;
	    }
	    
	    	_regionSystem.OnEntityExplode(event);
	  }
	  
	  
	  @EventHandler
	  public void WitherEatBlocks(EntityChangeBlockEvent event) throws InvalidWorldNameException
	  {
	    EntityType type = event.getEntity().getType();
	    
	    
	    
	    if (type == EntityType.WITHER) 
	    {
	       
				CMRegion cmRegion = _regionSystem.TryGetRegionAt(event.getBlock());
				
				if(cmRegion != null)
				{
					event.setCancelled(true);
	            }
			
	    	
	      event.setCancelled(true);
	    }
	  }
	
}