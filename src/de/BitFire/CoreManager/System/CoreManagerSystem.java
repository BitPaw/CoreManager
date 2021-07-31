package de.BitFire.CoreManager.System;

import java.net.InetAddress;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
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
import de.BitFire.CoreManager.Modules.Chat.ChatManager;
import de.BitFire.CoreManager.Modules.Chat.ColorManager;
import de.BitFire.CoreManager.Modules.Chat.MessageType;
import de.BitFire.CoreManager.Modules.Player.Player;
import de.BitFire.CoreManager.Modules.Player.PlayerManager;
import de.BitFire.CoreManager.Modules.Player.PlayerNotFoundException;
import de.BitFire.CoreManager.Modules.Position.PositionManager;
import de.BitFire.CoreManager.Modules.Sign.Sign;
import de.BitFire.CoreManager.Modules.Sign.SignFunction;
import de.BitFire.CoreManager.Modules.Sign.SignManager;
import de.BitFire.CoreManager.Modules.Tab.TabSystem;
import de.BitFire.CoreManager.Modules.Teleportation.TeleportManager;
import de.BitFire.CoreManager.Modules.Utility.BlockUtility;
import de.BitFire.CoreManager.Modules.Utility.FireWorkUtility;
import de.BitFire.CoreManager.Modules.World.WorldManager;
import de.BitFire.CoreManager.Modules.World.Exception.WorldNotFoundException;
import de.BitFire.CoreManager.Modules.World.Type.WorldBlockPosition;
import de.BitFire.Math.Geometry.PositionBlock;

public final class CoreManagerSystem 
{
	private static CoreManagerSystem _instance;
	private static CoreManagerSystemState _state = CoreManagerSystemState.NotInitialized;
	
	private CoreManagerSystem()
	{
		_instance = this;
		
		// Initializing
		{
			_state = CoreManagerSystemState.Initializing;
		}
		
		
		// Initialized
		{
			_state = CoreManagerSystemState.Initialized;
			
			
		}		
	}
		
	public static final CoreManagerSystem Instance()
	{
		return _instance == null ? new CoreManagerSystem() : _instance;
	}	
	
	public static final CoreManagerSystemState State()
	{
		return _state;
	}	
	
	public void Start()
	{	
		//_state = CoreManagerSystemState.Enabling;
		ChatManager.SendToConsole(MessageType.Loading, "&7CoreManager &aenabling&7...");	
		
		
		// Positions
		{
			ChatManager.SendToConsole(MessageType.Loading, "&3Loading all positions");		
			
			PositionManager.LoadAllPositions();
			
			ChatManager.SendToConsole(MessageType.Loaded,PositionManager.PrintPositions());
		}
		
		
		// Worlds
		{
			ChatManager.SendToConsole(MessageType.Loading, "&3Loading all worlds");			
			
			WorldManager.LoadAllWorlds();
			
			ChatManager.SendToConsole(MessageType.Loaded, WorldManager.PrintWorldInformation());	
		}
		

		
		_state = CoreManagerSystemState.Running;
		
		ChatManager.SendToConsole(MessageType.Loaded, "&2CoreManager is now active");
	}
	
	public void Stop()
	{
		_state = CoreManagerSystemState.Disabled;	
		
		ChatManager.SendToConsole(MessageType.Unloaded, "&4CoreManager is now inactive");
		
	}
	
	public void Update() 
	{
		TabSystem.OnTickUpdate();	
	}
	
	public void OnPlayerPingingServer(final ServerListPingEvent serverListPingEvent) 
	{
		final InetAddress inetAddress = serverListPingEvent.getAddress();
		final Player player = PlayerManager.GetPlayer(inetAddress);
		String welcomeMessage = ChatManager.GetCustomWelcomeMessage(player);				
					
		// Set Icon ? 
		// serverListPingEvent.setServerIcon(icon);
		
		// Set maxPlayers
		// serverListPingEvent.setMaxPlayers(maxPlayers);
		
		welcomeMessage = ColorManager.AddColor(welcomeMessage);
		
		serverListPingEvent.setMotd(welcomeMessage); // Set welcomeMessage
	}

	public void OnPlayerJoin(final PlayerJoinEvent playerJoinEvent) 
	{
		final org.bukkit.entity.Player bukkitPlayer = playerJoinEvent.getPlayer();		
		final UUID playerUUID = bukkitPlayer.getUniqueId();
		final boolean isServerCracked = !Bukkit.getOnlineMode();		
		final boolean isNew = !PlayerManager.IsPlayerRegistered(playerUUID);
		
		Player player = PlayerManager.LoadPlayer(bukkitPlayer);	
		
		String globalJoinMessage = ChatManager.GetGlobalJoinMessage(player, isNew);
		String privateJoinMessage = ChatManager.GetPrivateJoinMessage(player, isNew);
		
		if(isServerCracked)
		{
			//_loginSystem.ClawPlayer(player);
		}
		
		//_chatSystem.OnJoin(playerJoinEvent);
		//_nameTagSystem.UpdatePlayerTag(player);
		FireWorkUtility.CreateExplosion(bukkitPlayer);			
		ChatManager.Send(MessageType.None, globalJoinMessage, bukkitPlayer);
		
		privateJoinMessage = ColorManager.AddColor(privateJoinMessage);
		
		playerJoinEvent.setJoinMessage(privateJoinMessage);
	}

	public void OnChatMessage(final AsyncPlayerChatEvent asyncPlayerChatEvent) 
	{
		final org.bukkit.entity.Player bukkitPlayer = asyncPlayerChatEvent.getPlayer();
		String message = asyncPlayerChatEvent.getMessage();
		
		message = ChatManager.FormatChatMessage(bukkitPlayer, message);
		message = ColorManager.AddColor(message);
		
		asyncPlayerChatEvent.setFormat(message);
	}

	public void OnPlayerLeave(final PlayerQuitEvent playerQuitEvent) 
	{
		final org.bukkit.entity.Player player = playerQuitEvent.getPlayer();
		final UUID playerUUID = player.getUniqueId();
		String message = 	ChatManager.GetPublicQuitMessage(playerUUID);
		
		PlayerManager.UnloadPlayer(playerUUID);
	
		message = ColorManager.AddColor(message);
		
		playerQuitEvent.setQuitMessage(message);
	}

	public void OnPlayerRespawn(PlayerRespawnEvent playerRespawnEvent) 
	{
		
	}

	public void OnPlayerFly(PlayerToggleFlightEvent playerToggleFlightEvent) 
	{
		
	}

	public void OnPlayerMove(PlayerMoveEvent playerMoveEvent)
	{
		
	}

	public void OnPlayerInteract(final PlayerInteractEvent playerInteractEvent) 
	{
		Action action = playerInteractEvent.getAction();
				
		switch(action)
		{
		case LEFT_CLICK_AIR:
			break;
			
		case LEFT_CLICK_BLOCK:
			Block block = playerInteractEvent.getClickedBlock();
			org.bukkit.entity.Player bukkitPlayer = playerInteractEvent.getPlayer();
			boolean isSign = BlockUtility.IsBockSign(block);
			boolean isSneaking = bukkitPlayer.isSneaking();
						
			if(isSign && isSneaking) 
			{
				final PositionBlock position = new PositionBlock(block.getLocation());
				final int worldID = WorldManager.GetWorldID(block.getWorld());
				//final WorldBlockPosition worldBlockPosition = PositionManager.IsRegisteredBlock(worldID, position);
				final WorldBlockPosition worldBlockPosition = null;
				
				
				if(worldBlockPosition != null)
				{
					final Sign sign = SignManager.GetSign(worldID, worldBlockPosition.ID);
					final SignFunction function = sign.Function();
					
					switch(function)
					{
					case Command:
						break;
						
					case FameFrame:
						break;
						
					case Invalid:
						break;
						
					case None:
						// Does player want to add fucntion?
						//SignManager.ShouldRegister()
						break;
						
					case PvP:
						break;
						
					case Shop:
						//final SignShop signShop = SignManager.GetRegisteredSignShop(sign);								
								
						//SignManager.BuyFromSign(signShop, player);					
						
						break;
						
					case Teleport:
						break;
						
					default:
						break;
					
					}
				}
			}
			break;
			
		case PHYSICAL:
			break;
			
		case RIGHT_CLICK_AIR:
			break;
			
		case RIGHT_CLICK_BLOCK:
			break;
			
		default:
			break;
			
		}
	}

	public void OnPlayerSneak(PlayerToggleSneakEvent playerToggleSneakEvent) 
	{
		
	}

	public void OnEntityExplode(EntityExplodeEvent entityExplodeEvent)
	{
		
	}

	public void OnEntityChangeBlock(EntityChangeBlockEvent entityChangeBlockEvent) 
	{
		
	}

	public void OnEntityDamageEntity(EntityDamageByEntityEvent entityDamageByEntityEvent) 
	{
				
	}

	public void OnBlockBreak(BlockBreakEvent blockBreakEvent) 
	{	
		
	}

	public void OnBlockPlace(BlockPlaceEvent blockPlaceEvent) 
	{
		
		
	}

	public void OnSignChange(SignChangeEvent signChangeEvent) 
	{
			
	}	

	public boolean OnCommand(final CommandData commandData) 
	{
		CoreManagerCommand command;
		
		try
		{
			command = CoreManagerCommand.valueOf(commandData.Label.toLowerCase());
		}
		catch(Exception exc)
		{
			return false;
		}
		
		try
		{
			switch(command)
			{
			case b:
			case back:
				if(commandData.IsSenderPlayer())
				{
					//ChatManager.Send("Back", commandData.Sender);
				}
				else
				{
					throw new CommandNotForConsoleException();
				}	
				break;
				
			case c:
			case clock:
				if(commandData.IsSenderPlayer())
				{
					//ChatManager.Send("Back", commandData.Sender);
				}
				else
				{
					throw new CommandNotForConsoleException();
				}
				break;
				
			case cc:				
			case clearchat:
				// Clear the chat
				break;				
				
			case day:
				// Make day
				break;
				
			case exit:
				// Stops the server
				break;
				
			case gm:
				// GameMode
				if(commandData.IsSenderPlayer())
				{
					//ChatManager.Send("GameMode", commandData.Sender);
				}
				else
				{
					throw new CommandNotForConsoleException();
				}
				break;
				
			case h:
			case home:
				// Teleport home
				break;
				
			case hat:
				if(commandData.IsSenderPlayer())
				{
					//ChatManager.Send("GameMode", commandData.Sender);
				}
				else
				{
					throw new CommandNotForConsoleException();
				}
				break;
				
			case head:
				break;
				
			case i:				
			case inventory:
				break;
				
			case m:
			case money:
				// Money management
				break;
				
			case night:
				// Make night
				break;
								
			case pad:
				// LanchPads
				break;
				
			case player:
				// Player management
				break;
				
			case portal:
				// Portal management
				break;
				
			case printcolors:
				// PrintColors
				break;
				
			case P:
			case PvP:
				// PvP management
				break;
				
			case rain:
				// Make it rain or not
				break;
				
			case r:
			case rank:
				// Rank Stuff				
				break;				
			
			case s:
			case spawn:
				// Teleport to spawn
				break;
				
			case sys:				
			case system:
				
				break;
				
			case tp:
			case teleport:
				TeleportManager.TeleportCommand(commandData);
				break;
				
			case tpw:
			case teleporttoworld:
				TeleportManager.TeleportToWorldCommand(commandData);
				break;
				
			case tpl:
			case teleporttplocation:
				TeleportManager.TeleportToLocationCommand(commandData);
				break;	
				
			case w:				
			case warp:
				TeleportManager.WarpCommand(commandData);
				break;
				
			case warps:
				// List all warps
				break;
				
			case world:
				// World stuff				
				break;			
			}
		}
		catch(final CommandNotForConsoleException commandNotForConsoleException)
		{
			ChatManager.Send(MessageType.Error, "&7This &4Command &7is &4not &7useable in the &3console&7.", commandData.Sender);
		} 
		catch (final TooFewParametersException e) 
		{
			ChatManager.Send(MessageType.Error, "&7Too &cfew &7parameters.", commandData.Sender);
		}
		catch (final TooManyParametersException e) 
		{
			ChatManager.Send(MessageType.Error, "&7Too &cmany &7parameters.", commandData.Sender);
		}
		catch (PlayerNotFoundException e) 
		{
			ChatManager.Send(MessageType.Error, "&7Player &4<&c" + e.MissingPlayerName + "&4> &7was &cnot &7found.", commandData.Sender);
		} 
		catch (final WorldNotFoundException e) 
		{			
			ChatManager.Send(MessageType.Error, "&7World <" + e.MissingWorldName + "> was not found.", commandData.Sender);
		}	
		
		return true;
	}
}