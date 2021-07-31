package de.BitFire.Core;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
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

import de.BitFire.API.CommandCredentials;
import de.BitFire.API.Bukkit.BukkitAPIServer;
import de.BitFire.API.Bukkit.BukkitAPIWeather;
import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.Chair.ChairsSystem;
import de.BitFire.Chat.ChatSystem;
import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageTags;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Chest.ChestShopSign;
import de.BitFire.Chest.ChestShopSystem;
import de.BitFire.Configuration.Config;
import de.BitFire.Core.Exception.FaultrySyntaxException;
import de.BitFire.Core.Exception.NoCommandException;
import de.BitFire.Core.Exception.NotForConsoleException;
import de.BitFire.Core.Exception.SystemHasNoDataToPrintException;
import de.BitFire.Core.Exception.SystemNotActiveException;
import de.BitFire.Core.Exception.TooFewParameterException;
import de.BitFire.Core.Exception.TooManyParameterException;
import de.BitFire.DataBase.DataBaseSystem;
import de.BitFire.Economy.EconemySystem;
import de.BitFire.Economy.Exception.InvalidAmountParameterException;
import de.BitFire.Economy.Exception.NegativeAmountException;
import de.BitFire.Economy.Exception.NotEnoghMoneyException;
import de.BitFire.Economy.Exception.NullAmountException;
import de.BitFire.Economy.Exception.RedundantTransactionException;
import de.BitFire.Economy.Exception.TooMuchMoneyException;
import de.BitFire.Head.DropHeadSystem;
import de.BitFire.Mob.MobHealth;
import de.BitFire.NPC.NPCSystem;
import de.BitFire.Permission.PermissionSystem;
import de.BitFire.Player.PlayerSystem;
import de.BitFire.Player.DoubleJump.DoubleJump;
import de.BitFire.Player.Exception.BukkitPlayerMissingException;
import de.BitFire.Player.Exception.CMPlayerIsNullException;
import de.BitFire.Player.Exception.InvalidGameModeException;
import de.BitFire.Player.Exception.InvalidPlayerNameException;
import de.BitFire.Player.Exception.InvalidPlayerUUID;
import de.BitFire.Player.Exception.PlayerDirectionAmbiguous;
import de.BitFire.Player.Exception.PlayerDoesNotExistException;
import de.BitFire.Player.Exception.PlayerNotFoundException;
import de.BitFire.Player.Exception.PlayerOfflineException;
import de.BitFire.Player.Exception.RedundantGameModeChangeException;
import de.BitFire.Player.Login.LoginSystem;
import de.BitFire.Player.Tag.NameTagSystem;
import de.BitFire.PvP.PvPSystem;
import de.BitFire.PvP.Lobby.Exception.LobbyAlreadyClosedException;
import de.BitFire.PvP.Lobby.Exception.LobbyAlreadyOpenException;
import de.BitFire.PvP.Lobby.Exception.LobbyAlreadyRunningException;
import de.BitFire.PvP.Lobby.Exception.LobbyClosedException;
import de.BitFire.PvP.Lobby.Exception.LobbyFullException;
import de.BitFire.PvP.Lobby.Exception.PlayerAlreadyInLobbyException;
import de.BitFire.PvP.Lobby.Exception.PlayerNotInLobbyException;
import de.BitFire.Rank.RankSystem;
import de.BitFire.Rank.Exception.RankNotFoundException;
import de.BitFire.Rank.Exception.RedundantRankChangeException;
import de.BitFire.Sign.SignEditSystem;
import de.BitFire.Skin.SkinSystem;
import de.BitFire.Skin.Exception.OfflineSkinLoadException;
import de.BitFire.Skin.Exception.SkinLoadException;
import de.BitFire.Sound.SoundSystem;
import de.BitFire.Tab.TabSystem;
import de.BitFire.Teleport.TeleportSystem;
import de.BitFire.Teleport.Exception.MissingPlayerPositionException;
import de.BitFire.Teleport.Portal.Portal;
import de.BitFire.Teleport.Portal.PortalSystem;
import de.BitFire.Teleport.Portal.Exception.PortalHasNoTargetException;
import de.BitFire.Teleport.Portal.Exception.PortalNotFoundException;
import de.BitFire.Time.MinuteTickTimer;
import de.BitFire.Time.SecondTickTimer;
import de.BitFire.Time.Clock.DigitalClockSystem;
import de.BitFire.Tree.Timber;
import de.BitFire.VFX.FireWork;
import de.BitFire.Warp.Warp;
import de.BitFire.Warp.WarpSystem;
import de.BitFire.Warp.Exception.WarpNotFoundException;
import de.BitFire.World.WorldSystem;
import de.BitFire.World.Exception.InvalidWorldNameException;
import de.BitFire.World.Manipulation.WorldEditSystem;
import de.BitFire.World.Manipulation.Exception.NoRegionMarkedException;
import de.BitFire.World.Manipulation.Exception.PositionAMissingException;
import de.BitFire.World.Manipulation.Exception.PositionBMissingException;
import de.BitFire.World.Region.CMRegion;
import de.BitFire.World.Region.RegionSystem;

public class CoreListener
{
	private static CoreListener _instance;
	//private PluginManager _pluginManager;
	//private JavaPlugin _javaPlugin;
	
	private static SystemList _systemList;
	
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
	@SuppressWarnings("unused")
	private SkinSystem _skinSystem;	
	
	private Timber _timber;
	private MobHealth _mobHealth;
	private DoubleJump _doubleJump;
	private DropHeadSystem _dropHeadSystem;
	private DigitalClockSystem _digitalClockSystem;
	private ChairsSystem _chairsSystem;
	private NPCSystem _npcSystem;
	private LoginSystem _loginSystem;
	private WorldEditSystem _worldEditSystem;
	private PortalSystem _portalSystem;
	@SuppressWarnings("unused")
	private ChestShopSystem _chestShopSystem;
	private PvPSystem _pvpSystem;
	
	private SecondTickTimer _secondTickTimer;
	private MinuteTickTimer _minuteTickTimer;
	
	private CoreListener()
	{
		_instance = this;		
	}

	public static CoreListener Instance()
	{
		return _instance == null ? new CoreListener() : _instance;
	}
	
	private void LoadAllInstances()
	{		
		//---[Core]------------------------------------------------------------------
		_config = Config.Instance();		
		_logger = Logger.Instance();
		_databaseManager = DataBaseSystem.Instance();
		_chatSystem = ChatSystem.Instance();		
		_coreSystem = CoreSystem.Instance();
				
		//---[Important]------------------------------------------------------------------
				
		_worldSystem = WorldSystem.Instance();
		_warpSystem = WarpSystem.Instance();
		_rankSystem = RankSystem.Instance();
		_permissionSystem = PermissionSystem.Instance();
		_regionSystem = RegionSystem.Instance();
		_econemySystem = EconemySystem.Instance();
		_playerSystem = PlayerSystem.Instance();
		_loginSystem = LoginSystem.Instance();
		
		//---[Extra]------------------------------------------------------------------
		
		_messageTags = MessageTags.Instance();
		_nameTagSystem = NameTagSystem.Instance();
		_soundSystem = SoundSystem.Instance();		
		_signEditSystem = SignEditSystem.Instance();
		_tabSystem = TabSystem.Instance();	
		_teleportSystem = TeleportSystem.Instance();
		_fireWork = FireWork.Instance();				
		_timber = Timber.Instance();
		_doubleJump = DoubleJump.Instance();
		_mobHealth = MobHealth.Instance();				
		_dropHeadSystem = DropHeadSystem.Instance();
		_digitalClockSystem = DigitalClockSystem.Instance();
		_chairsSystem = ChairsSystem.Instance();	
		_npcSystem = NPCSystem.Instance();
		_skinSystem = SkinSystem.Instance();
		_worldEditSystem = WorldEditSystem.Instance();
		_portalSystem = PortalSystem.Instance();
		_chestShopSystem = ChestShopSystem.Instance();
		_pvpSystem = PvPSystem.Instance();
		
		// Timer
		_secondTickTimer = SecondTickTimer.Instance();
		_minuteTickTimer = MinuteTickTimer.Instance();
	}	

	public static void RegisterSystem(ISystem system)
	{
		_systemList.Add(system);		
	}
	
	public static void RegisterSystem(BaseSystem system)
	{		
		if(_systemList == null)
		{
			_systemList = new SystemList();
		}
		
		_systemList.Add((ISystem)system);		
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

	public void RegisterAllEvents(JavaPlugin plugin, PluginManager pluginManager)
	{		
		_nameTagSystem.SetupScoreBoard();
		
		Bukkit.getScheduler().runTaskTimer(plugin, _secondTickTimer, 0, _secondTickTimer.GetTickIntervalInMS());
		Bukkit.getScheduler().runTaskTimer(plugin, _minuteTickTimer, 0, _minuteTickTimer.GetTickIntervalInMS());
		
		_minuteTickTimer.RegisterSystem(_digitalClockSystem);
		_minuteTickTimer.RegisterSystem(_npcSystem);
		
		_secondTickTimer.RegisterSystem(_tabSystem);
		_secondTickTimer.RegisterSystem(_portalSystem);				
		
		_signEditSystem.setReEditSignMethod();	
	}

	public void CancelAllEvents()
	{
		_minuteTickTimer.ReleaseAll();
		_secondTickTimer.ReleaseAll();
	}

	public void ListEverything()
	{
		CommandSender sender = Bukkit.getConsoleSender();

		_systemList.GetStatusFromAll(sender);		
		_systemList.ListAll(sender);				
	}

	public boolean onCommand(final CommandCredentials commandCredentials) 
	{
		boolean successful = true;
		
		try
		{			
			switch(commandCredentials.CMCommand)
			{
			case b:
			case back:
				if(commandCredentials.IsSenderPlayer)
				{
					final Player player = commandCredentials.TryGetPlayer();
					_teleportSystem.TeleportToLastLocation(player);
				}
				else
				{
					throw new NotForConsoleException();
				}	
				break;
				
			case cn:
			case changename:
				_playerSystem.ChangeNameCommand(commandCredentials);
				_nameTagSystem.UpdatePlayerTag(commandCredentials.Sender);
				break;
				
			case changeplayertag:
				break;
				
			case cr:
			case changerank:
				_rankSystem.ChangeRankCommand(commandCredentials.Sender, commandCredentials.Parameter);
				_nameTagSystem.UpdatePlayerTag(commandCredentials.Sender);
				break;
				
			case cs:
			case changeskin:
				_playerSystem.ChangeSkin(commandCredentials.Sender, commandCredentials.Parameter[0]);
				break;
				
			case cc:
			case clearchat:
				_chatSystem.ClearChat(commandCredentials.Sender);
				break;
				
			case ci:
			case clearinventory:
				_playerSystem.ClearInventory(commandCredentials.Sender, commandCredentials.Parameter);
				break;
				
			case cw:
			case createworld:
				_worldSystem.CreateNewWorldCommand(commandCredentials.Sender, commandCredentials.Parameter);
				break;
				
			case creload:
				CancelAllEvents();
				
				_systemList.ReloadAll(false);
				//_bukkitAPISystem.ReloadPlugin(_javaPlugin);
				break;
				
			case day:
				BukkitAPIWeather.Day();
				break;
				
			case dr:
			case deleterank:
				_rankSystem.ResetPlayerRank(commandCredentials.Sender, commandCredentials.Parameter);
				_nameTagSystem.UpdatePlayerTag(commandCredentials.Sender);
				break;
				
			case dw:
			case deleteworld:
				_worldSystem.DeleteWorldCommand(commandCredentials.Sender, commandCredentials.Parameter);
				break;
				
			case delwarp:
				_warpSystem.DeleteWarp(commandCredentials.Sender, commandCredentials.Parameter);
				break;
				
			case dc:
			case digitalclock:
				_digitalClockSystem.Command(commandCredentials);
				break;
				
			case exit:
				BukkitAPIServer.ShutdownServer();
				break;
				
			case gm:
				_playerSystem.ChangeGameMode(commandCredentials.Sender, commandCredentials.Parameter);
				break;
				
			case hat:
				_playerSystem.PutItemOnHead(commandCredentials.Sender);	
				break;
				
			case head:
				_dropHeadSystem.GetHeadCommand(commandCredentials.Sender, commandCredentials.Parameter);
				break;
				
			case heal:
				_playerSystem.Heal(commandCredentials.Sender);
				break;
				
			case home:
				_teleportSystem.TeleportHome(commandCredentials.Sender, commandCredentials.Parameter);
				break;
				
			case lag:
				_coreSystem.CallGarbageCollector();
				_logger.SendToSender(Module.System, MessageType.Info, commandCredentials.Sender, "System cleaned.");
				break;
				
			case lr:
			case listranks:
				_rankSystem.PrintData(commandCredentials.Sender);
				break;
				
			case lp:
			case listplayer:
				_playerSystem.PrintData(commandCredentials.Sender);
				break;
				
			case lw:
			case listworlds:
				_worldSystem.PrintData(commandCredentials.Sender);
				break;
				
			case m:
			case money:
				_econemySystem.GetAccountBalanceCommand(commandCredentials.Sender, commandCredentials.Parameter);
				break;
				
			case night:
				BukkitAPIWeather.Night();
				break;
				
			case npc:
				_npcSystem.NPCCommand(commandCredentials.Sender, commandCredentials.Parameter);
				break;
				
			case p:
			case pay:
				_econemySystem.PayCommand(commandCredentials.Sender, commandCredentials.Parameter);
				break;
				
			case pc:
			case printcolors:
				_logger.PrintColors(commandCredentials.Sender);
				break;
				
			case pt:
			case portal:
				_portalSystem.Command(commandCredentials);
				break;
				
			case pvp:
				_pvpSystem.Command(commandCredentials);
				break;
				
			case rain:
				BukkitAPIWeather.Rain();
				break;
				
			case setlocation:
				break;
				
			case setspawn:
				_worldSystem.SetGlobalSpawn(commandCredentials.Sender);				
				break;
				
			case setwarp:
				_warpSystem.SetWarp(commandCredentials.Sender, commandCredentials.Parameter);
				break;
				
			case sws:
			case setworldspawn:
				_worldSystem.SetWorldSpawn(commandCredentials.Sender);
				break;
				
			case spawn:
				_warpSystem.WarpToGlobalSpawn(commandCredentials.Sender);
				break;
				
			case speed:
				_playerSystem.ChangeSpeed(commandCredentials.Sender, commandCredentials.Parameter);		
				break;
				
			case sql:
				break;
				
			case sysi:
			case sysinfo:		
			case systeminfo:
				_coreSystem.PrintData(commandCredentials.Sender);
				break;
				
			case tp:
			case teleport:
				_teleportSystem.TeleportToPlayer(commandCredentials.Sender, commandCredentials.Parameter);
				break;
				
			case tpl:
			case teleporttolocation:
				_teleportSystem.TeleportToSpecificLocation(commandCredentials.Sender, commandCredentials.Parameter);
				break;
				
			case tpw:
			case teleporttoworld:
				_teleportSystem.TeleportToWorld(commandCredentials.Sender, commandCredentials.Parameter);
				break;
			
			case w:
			case warp:
				_warpSystem.WarpCommand(commandCredentials.Sender, commandCredentials.Parameter);
				break;
				
			case warps:
				_warpSystem.ListAllWarps(commandCredentials.Sender);
				break;
				
			case me:
			case whoisme:
			case whoami:
				_playerSystem.GetPlayerInfo(commandCredentials.Sender, commandCredentials.Parameter);	
				break;
				
			case ws:
			case worldspawn:
				_teleportSystem.TeleportToWorldSpawn(commandCredentials.Sender);
				break;
												
			default:
				successful = false;
				_logger.SendToSender(Module.System, MessageType.Warning, commandCredentials.Sender, "Unkown command");
				break;			
			}
		}
		catch(TooManyParameterException tooManyParameterException)
		{
			String message = _config.Message.IO.TooManyParameters;
			    		
   		 	_logger.SendToSender(Module.System, MessageType.Warning, commandCredentials.Sender, message);
		}
		catch(TooFewParameterException tooFewParameterException)
		{
			String message = "&7Too &cfew &7parameters!";
			   		
   		 	_logger.SendToSender(Module.System, MessageType.Warning, commandCredentials.Sender, message);
		}
		catch(RedundantGameModeChangeException redundantGameModeChangeException)
		{
			 String message = _config.Message.Player.YouAreAlreadyInThisGameMode;
			 
    		 message = _messageTags.ReplaceGameMode(message, redundantGameModeChangeException.PlayerGameMode);
    		
    		 _logger.SendToSender(Module.PlayerSystem, MessageType.Warning, commandCredentials.Sender, message);
		}
		catch (RankNotFoundException rankNotFoundException)
		{
			String message = "&7Rank &7<&c" + rankNotFoundException + "&7> &4not &7found!";

			_logger.SendToSender(Module.RankSystem, MessageType.Error, commandCredentials.Sender, message);
		}
		catch(RedundantRankChangeException redundantRankChangeException)
		{
			String message = "&7Player is &calready &7on this rank&8!";

			_logger.SendToSender(Module.RankSystem, MessageType.Error, commandCredentials.Sender,message);
		} 
		catch (PlayerNotFoundException playerNotFoundException)
		{			
			String message = "&7Player &6<&e" + playerNotFoundException.NotFoundPlayerName + "&6> &cnot &7found!";

			_logger.SendToSender(Module.PlayerSystem, MessageType.Error, commandCredentials.Sender,message);
		} 
		catch (InvalidPlayerUUID e) 
		{			
			e.printStackTrace();
		}
		catch (NotForConsoleException notForConsoleException) 
		{
			final String message = _config.Message.IO.NotForConsole;

			_logger.SendToSender(Module.System, MessageType.Error, commandCredentials.Sender, message);			
		}
		catch (InvalidGameModeException invalidGameModeException) 
		{
			String message = "&cInvalid &7GameMode &6<&e" + invalidGameModeException.WantedGameMode + "&6>&7.";

			_logger.SendToSender(Module.PlayerSystem, MessageType.Error, commandCredentials.Sender,message);
		}
		catch (InvalidPlayerNameException e)
		{
			e.printStackTrace();
		} 
		catch (WarpNotFoundException warpNotFoundException) 
		{
			String message = "&eWarp &7with name &6<&e" + warpNotFoundException.MissingWarpName + "&6> &7does &cnot &7exist!";

			_logger.SendToSender(Module.WarpSystem, MessageType.Error, commandCredentials.Sender,message);
		} 
		catch (NotEnoghMoneyException e)
		{
			String message = "&7You have &cnot enogh &7money! Your are &6<&e" + e.MoneyDifference +  "&6> &7short";

			_logger.SendToSender(Module.System, MessageType.Warning, commandCredentials.Sender,message);
		} 
		catch (NullAmountException e) 
		{
			String message = "&7You gave nothing? Is that what you want?";

			_logger.SendToSender(Module.System, MessageType.Question, commandCredentials.Sender,message);
		} 
		catch (NegativeAmountException e) 
		{
			String message = "&7You &ccan't &7use negative values!";

			_logger.SendToSender(Module.System, MessageType.Error, commandCredentials.Sender,message);
		} 
		catch (TooMuchMoneyException e) 
		{
			String message = "&7You have &cmaxed out &7your accound! &aCongrats!";

			_logger.SendToSender(Module.System, MessageType.Warning, commandCredentials.Sender,message);
		} 
		catch (InvalidAmountParameterException e) 
		{
			String message = "&7Do you have the number wrong? &cInvalid &7number!";

			_logger.SendToSender(Module.System, MessageType.Error, commandCredentials.Sender,message);
		} 
		catch (RedundantTransactionException e)
		{
			String message = "&7You are trying to give &eyourself &7your &eown money. &8Why?";

			_logger.SendToSender(Module.System, MessageType.Error, commandCredentials.Sender,message);
		} 
		catch (InvalidWorldNameException e)
		{
			String message = "&7World &cnot &7found &6<&e" + e.getMessage() + "&6>&7.";

			_logger.SendToSender(Module.System, MessageType.Error, commandCredentials.Sender,message);
		} 
		catch (OfflineSkinLoadException offlineSkinLoadException) 
		{
			String message = "&7Skin could &cnot &7be loaded because the server is in &eoffline&7 mode!";

			_logger.SendToSender(Module.System, MessageType.Error, commandCredentials.Sender,message);
		}	
		catch(IllegalArgumentException IllegalArgumentException)
		{
			// wrong Enum constant			
		}
		catch(PlayerOfflineException PlayerOfflineException)
		{
			String message = "&7Player with name &6<&e" + PlayerOfflineException.OfflinePlayerName + "&6> &7is &coffline&7!";

			_logger.SendToSender(Module.System, MessageType.Error, commandCredentials.Sender,message);
		}
		catch (PlayerDoesNotExistException e) 
		{
			String message = "Player does not exist!";
			
			_logger.SendToSender(Module.System, MessageType.Error, commandCredentials.Sender,message);
		} 
		catch (SkinLoadException e) 
		{
			String message = "Skin could not be loaded!";
			
			_logger.SendToSender(Module.System, MessageType.Error, commandCredentials.Sender,message);
		} 
		catch (SystemNotActiveException e) 
		{
			String message = "&7This &esub-plugin &7is currently &cnot &7active. You need to &aenable &7it first to use it.";
			
			_logger.SendToSender(Module.System, MessageType.Error, commandCredentials.Sender,message);
		}
		catch (FaultrySyntaxException e) 
		{
			String message = "&cSyntax error! &7Please check your input. &8What did you mean?";
			
			_logger.SendToSender(Module.System, MessageType.Error, commandCredentials.Sender,message);
		} 
		catch (PositionAMissingException e) 
		{
			String message = "&7Position &6<&eA&6> &7is not set.";
			
			_logger.SendToSender(Module.System, MessageType.Error, commandCredentials.Sender,message);
		} 
		catch (PositionBMissingException e) 
		{
			String message = "&7Position &6<&eB&6> &7is not set.";
			
			_logger.SendToSender(Module.System, MessageType.Error, commandCredentials.Sender,message);
		} 
		catch (NoRegionMarkedException e) 
		{
			String message = "&7You have &cno &7region selected! &8Make a selection first.";
			
			_logger.SendToSender(Module.System, MessageType.Error, commandCredentials.Sender,message);
		} 
		catch (NoCommandException e) 
		{
			String message = "&7There is no command to execute!?";
			
			_logger.SendToSender(Module.System, MessageType.Warning, commandCredentials.Sender,message);
		} 
		catch (SystemHasNoDataToPrintException e) 
		{
			String message = "&7No data to show.";
			
			_logger.SendToSender(Module.System, MessageType.Warning, commandCredentials.Sender,message);
		} 
		catch (PlayerAlreadyInLobbyException e) 
		{
			String message = "&7You are &aalready &7in this &elobby&7.";
			
			_logger.SendToSender(Module.System, MessageType.Warning, commandCredentials.Sender,message);
		} 
		catch (LobbyFullException e) 
		{
			String message = "&7The &elobby &7is &cfull&7! The can only be &6<&r" + e.GetMaximalPlayers() + "&6> &7player";
			
			_logger.SendToSender(Module.System, MessageType.Error, commandCredentials.Sender,message);
		} 
		catch (PlayerNotInLobbyException e) 
		{
			String message = "&7Player is not in the lobby!";
			
			_logger.SendToSender(Module.System, MessageType.Error, commandCredentials.Sender,message);
		} 
		catch (LobbyClosedException e) 
		{
			String message = "&7You can't join, the lobby is closed!";
			
			_logger.SendToSender(Module.System, MessageType.Error, commandCredentials.Sender,message);
		} 
		catch (LobbyAlreadyRunningException e) 
		{
			String message = "&7You can't join, the lobby is already running!";
			
			_logger.SendToSender(Module.System, MessageType.Error, commandCredentials.Sender,message);
		} 
		catch (LobbyAlreadyClosedException e) 
		{
			String message = "&7Lobby is already closed!";
			
			_logger.SendToSender(Module.System, MessageType.Error, commandCredentials.Sender,message);
		} 
		catch (LobbyAlreadyOpenException e)
		{
			String message = "&7Lobby is already opened!";
			
			_logger.SendToSender(Module.System, MessageType.Error, commandCredentials.Sender,message);
		} 
		catch (final MissingPlayerPositionException missingPlayerPositionException) 
		{
			String message = "&7There is &cno &eposition &7to return to.";
			
			_logger.SendToSender(Module.WarpSystem, MessageType.Warning, commandCredentials.Sender,message);
		} 
		catch (PlayerDirectionAmbiguous e) 
		{
			String message = "&7You are standing between two directions, i dont know where you want it to go.";
			
			_logger.SendToSender(Module.System, MessageType.Warning, commandCredentials.Sender,message);
		}
		catch(Exception exception)
		{
			_logger.SendToSender(Module.System, MessageType.Error, commandCredentials.Sender, "Unhandled Exception accured!");
			
			exception.printStackTrace();
		}	

		return successful;
	}

	public void onPlayerJoin(PlayerJoinEvent playerJoinEvent)
	{
		final Player player = playerJoinEvent.getPlayer();			
		final boolean isServerCracked = BukkitAPIServer.IsServerCracked();
		
		_playerSystem.RegisterPlayer(player);
		
		_nameTagSystem.UpdatePlayerTag(player);
		
		_chatSystem.OnJoin(playerJoinEvent);
		
		if(isServerCracked)
		{
			_loginSystem.ClawPlayer(player);
		}
		
		_fireWork.CreateExplosion(player);		
	}

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

	public void onChat(AsyncPlayerChatEvent e)
	{
		_chatSystem.OnChat(e);
	}
	
	public void OnPlayerMove(PlayerMoveEvent event)
	{
		final Player player = event.getPlayer();
		final UUID playerUUID = player.getUniqueId();
		
		Location targetlocation = event.getTo().getBlock().getLocation();	
	
		try 
		{						
			final Portal sourcePortal = _portalSystem.TryGetPortalInLocation(targetlocation);			
			
			if(sourcePortal != null)
			{
				final boolean canPlayerTeleport = _portalSystem.CanPlayerTeleport(playerUUID);
				
				if(canPlayerTeleport)
				{
					if(sourcePortal.LinkedPortalID == -1)
					{
						throw new PortalHasNoTargetException();
					}
					else
					{
						final Portal targetPortal = _portalSystem.Get(sourcePortal.LinkedPortalID);
						targetlocation = targetPortal.GetLocation();
						
						_portalSystem.SetPlayerCoolDown(playerUUID);
						
						event.setTo(targetlocation);
						
						_soundSystem.PlaySoundTeleport(player);
					}	
				}		
			}
		} 
		catch (PortalNotFoundException e) 
		{
			// There is no Portal so you are not in one. Ignore this call
		} 
		catch (PortalHasNoTargetException e) 
		{			
			String message = "&7This &ePortal &7has &cno &7target attached!";
			_logger.SendToSender(Module.Portal, MessageType.Warning, player, message);
		}		
		
		_doubleJump.OnPlayerMove(event);
	}
	
	public void OnFly(PlayerToggleFlightEvent e)
	{
		_doubleJump.OnFly(e);
	}
	
	public void OnSneak(PlayerToggleSneakEvent e)
	{
		_doubleJump.OnSneak(e);
	}
	
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
	
	public void OnSignClick(PlayerInteractEvent event)
	{
		final Player player = event.getPlayer();
		
		_soundSystem.PlaySoundTrue(player);
	}

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
	
	public void onSignPlace(SignChangeEvent event)
	{
		final Block block = event.getBlock();
		final Player player = event.getPlayer();
		String[] lines = event.getLines();
		final boolean isChestShopSign = ChestShopSign.IsChestShopSign(block);
				
		if(isChestShopSign)
		{
			lines = ChestShopSign.ParseSign(lines, player);
			
			event.setLine(0, lines[0]);
			event.setLine(1, lines[1]);
			event.setLine(2, lines[2]);
			event.setLine(3, lines[3]);
		}
		
		_signEditSystem.OnSignPlace(event);
	}
	
	public void onInteract(PlayerInteractEvent playerInteractEvent)
	{		
		if(playerInteractEvent != null)
		{
			final Action action = playerInteractEvent.getAction();
			final Player player = playerInteractEvent.getPlayer();			
			final boolean isRightClickedBlock = action == Action.RIGHT_CLICK_BLOCK;					
			final Material handMaterial = player.getInventory().getItemInMainHand().getType();			
			final boolean isItemInHandCompass = handMaterial == Material.COMPASS;
			
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
	
	public void onPlayerInteract(PlayerInteractEvent event)
	{		
		if(event != null)
		{
			final EquipmentSlot e = event.getHand(); //Get the hand of the event and set it to 'e'.
			  
			if(e != null)
			{
				if (e.equals(EquipmentSlot.HAND))  //If the event is fired by HAND (main hand)
				{ 
					try 
					{
						_regionSystem.OnInteract(event);								
						_worldEditSystem.Interact(event);				        	
					} 
					catch (InvalidWorldNameException e1) 
					{								
						e1.printStackTrace();
					}
				}
			}
			  
			_chairsSystem.Event.OnPlayerInteract(event);   
		}
	}		

    public void onPlayerDoorOpen(PlayerInteractEvent event)
    {
		final Player player = event.getPlayer();		
		final Block block = event.getClickedBlock();		
		
		try 
		{
			final boolean hasPlayerPermission = _permissionSystem.HasPlayerPermissionToModifylock(player,block);
			
			if(!hasPlayerPermission)
			{			
				event.setCancelled(true);
			}			
		} 
		catch (CMPlayerIsNullException e) 
		{
			e.printStackTrace();
		} 
		catch (BukkitPlayerMissingException e) 
		{
			e.printStackTrace();
		}
		catch (PlayerNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (InvalidPlayerUUID e) 
		{
			e.printStackTrace();
		} 
		catch (InvalidWorldNameException e) 
		{	
			e.printStackTrace();
		}	
    }

	public void onEntityExplode(EntityExplodeEvent event) 
	{		
		try 
		{
			_regionSystem.OnEntityExplode(event);
		} 
		catch (InvalidWorldNameException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void onBlockPlace(BlockPlaceEvent event) 
	{
		final Player player = event.getPlayer();		
		final Block block = event.getBlock();
				
		try 
		{
			final boolean hasPlayerPermission = _permissionSystem.HasPlayerPermissionToModifylock(player, block);
			
			if(!hasPlayerPermission)
			{
				event.setCancelled(true);
				
				_logger.SendToSender(Module.PermissionSystem, MessageType.Warning, player, "&7You &cdont &7have the permission to modfy this area.");
			}
		} 
		catch (PlayerNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (InvalidPlayerUUID e) 
		{
			e.printStackTrace();
		}
		catch (InvalidWorldNameException e) 
		{
			e.printStackTrace();
		}
		catch (CMPlayerIsNullException e) 
		{
			e.printStackTrace();
		} 
		catch (BukkitPlayerMissingException e)
		{
			e.printStackTrace();
		}
	}	
	 
	public void onBlockBreak(BlockBreakEvent event)
	{			
		if(event == null)
		{
			return;
		}
		
		if(event.isCancelled())
		{
			return;
		}			
				
		try 
		{
			final Player player = event.getPlayer();		
			final Block block = event.getBlock();		
					
			final boolean hasPlayerPermission = _permissionSystem.HasPlayerPermissionToModifylock(player, block);	
			
			if(hasPlayerPermission)
			{				
				if(player.isSneaking())
				{
					_timber.OnBlockBreak(event);
				}			
			}
			else
			{
				_logger.SendToSender(Module.PermissionSystem, MessageType.Warning, player, "&7You &cdont &7have the permission to modfy this area.");
				
				event.setCancelled(true);
			}
		} 
		
		catch (PlayerNotFoundException e1) 
		{
			e1.printStackTrace();
		} 
		catch (InvalidPlayerUUID e1)
		{
			e1.printStackTrace();
		} 		
		catch (InvalidWorldNameException e1) 
		{
			e1.printStackTrace();
		} 
		catch (CMPlayerIsNullException e) 
		{
			e.printStackTrace();
		} 
		catch (BukkitPlayerMissingException e) 
		{
			e.printStackTrace();
		}		
	}
	
	public void onDamage(EntityDamageByEntityEvent event)	   
	{		
		_mobHealth.OnDamage(event);
	}

	public void WitherProjectile(EntityExplodeEvent event) 
	{
		if (event.isCancelled()) 
		{
			return;
	    }
	    
	    try 
	    {
	    	_regionSystem.OnEntityExplode(event);
	    } 
	    catch (InvalidWorldNameException e) 
	    {
	    	e.printStackTrace();	
	    }
	}	  
	  
	public void WitherEatBlocks(EntityChangeBlockEvent event) 
	{
		final EntityType type = event.getEntity().getType();
	    
	    if (type == EntityType.WITHER) 
	    {			
	    	try 
			{
				final CMRegion cmRegion = _regionSystem.TryGetRegionAt(event.getBlock());
				
				if(cmRegion != null)
				{
					event.setCancelled(true);
	            }
			}
			catch (InvalidWorldNameException e) 
			{
				e.printStackTrace();
			}	
	    	
	    	event.setCancelled(true);
	    }
	}	
}