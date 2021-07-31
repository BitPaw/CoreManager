package de.BitFire.Player;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.mojang.authlib.GameProfile;

import de.BitFire.API.CommandCredentials;
import de.BitFire.API.Bukkit.BukkitAPIPlayer;
import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageTags;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Configuration.Config;
import de.BitFire.Core.Exception.NotForConsoleException;
import de.BitFire.Core.Exception.SystemNotActiveException;
import de.BitFire.Core.Exception.TooFewParameterException;
import de.BitFire.Core.Exception.TooManyParameterException;
import de.BitFire.DataBase.DataBaseSystem;
import de.BitFire.Player.Exception.InvalidGameModeException;
import de.BitFire.Player.Exception.InvalidPlayerNameException;
import de.BitFire.Player.Exception.InvalidPlayerUUID;
import de.BitFire.Player.Exception.PlayerDoesNotExistException;
import de.BitFire.Player.Exception.PlayerNotFoundException;
import de.BitFire.Player.Exception.PlayerOfflineException;
import de.BitFire.Player.Exception.RedundantGameModeChangeException;
import de.BitFire.Skin.SkinData;
import de.BitFire.Skin.SkinSystem;
import de.BitFire.Skin.Exception.OfflineSkinLoadException;
import de.BitFire.Skin.Exception.SkinLoadException;

public class PlayerSystem extends BaseSystem implements ISystem
{
	private static PlayerSystem _instance;
	
	private Logger _logger;
	private Config _config;
	private MessageTags _messageTags;
	private DataBaseSystem _dataBaseSystem;	
	private PlayerList _playerList;
	private SkinSystem _skinSystem;
		
	private PlayerSystem()
	{
		super(Module.PlayerSystem, SystemState.Active, Priority.High);
		_instance = this;
	
		_playerList = new PlayerList();			
	}
	
	public static PlayerSystem Instance()
	{	
		return _instance == null ? new PlayerSystem() : _instance;
	}	

	@Override
	public void LoadReferences() 
	{
		_logger = Logger.Instance();
		_config = Config.Instance();
		_messageTags = MessageTags.Instance();
		_dataBaseSystem = DataBaseSystem.Instance();
		_skinSystem = SkinSystem.Instance();
	}

	@Override
	public void Reload(final boolean firstRun) throws SystemNotActiveException 
	{	
		if(!Information.IsActive())
		{
			throw new SystemNotActiveException();
		}
		
		final List<Player> players = BukkitAPIPlayer.GetAllOnlinePlayers();
		
		// Load players from Database
		_playerList.Add(_dataBaseSystem.Player.LoadAllPlayers());
			
		// Merge BukkitPlayer that are already online
		for(final Player player : players)
		{
			final UUID playerUUID = player.getUniqueId();
			final boolean isRegistered = _playerList.IsRegistered(playerUUID);	
			
			try 
			{
				if(isRegistered)
				{				
					_playerList.UpdatePlayer(true, playerUUID, player);				
				}
				else
				{
					final int numberOfRegisteredPlayers = _playerList.GetNumberOfPlayers();
					CMPlayer cmPlayer = _dataBaseSystem.Player.RegisterNewPlayer(player, numberOfRegisteredPlayers);
			
					_playerList.Add(cmPlayer);
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
		}			
	
	}			
	
	
	@Override
	public void PrintData(CommandSender sender) 
	{
		final boolean isSenderConsole = !(sender instanceof Player);
		final int nrOfRealPlayers = Bukkit.getOnlinePlayers().size();
		final int nrOfOnlinePlayers = _playerList.GetAmountOfOnlinePlayers();
		final int nrOfOfflinePlayers = _playerList.GetNumberOfPlayers();
		
		String message = "";
		
		if(isSenderConsole)
		{
			message += "\n";			
		}
		
		message +=		
				"&6===[&ePlayers&6]=========\n" +
				" &eServer &7: &7Online &6: &r" + nrOfRealPlayers + "\n" +  
				" &eDatabase &7: &7Online &8(&a" + nrOfOnlinePlayers + "&8/&c" + nrOfOfflinePlayers + "&8)\n" +  
				"&6=====================\n";
		
		_logger.SendToSender(Module.PlayerSystem, MessageType.Info, sender, message);
	}
	
	public void ChangePlayerState(final UUID playerUUID, final PlayerState playerState) 
	{
		try 
		{
			final CMPlayer cmPlayer = _playerList.GetPlayer(playerUUID);
			
			cmPlayer.State = playerState;
			
			_playerList.Swap(playerUUID, cmPlayer);
		} 
		catch (PlayerNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (InvalidPlayerUUID e) 
		{
			e.printStackTrace();
		}		
	}
	
    private void ModifyGameMode(Player player, GameMode toGameMode) throws RedundantGameModeChangeException
    {
    	GameMode currentGameMode = player.getGameMode();
    	String message;
    	
        if(currentGameMode == toGameMode)
    	{          	
        	throw new RedundantGameModeChangeException(currentGameMode);
    	}
    	else
    	{
    		message =  _config.Message.Player.GameModeChanged;   
            
            message = _messageTags.ReplaceGameModeOld(message, currentGameMode);
            message = _messageTags.ReplaceGameModeNew(message, toGameMode);   
                        
            _logger.SendToSender(Module.PlayerSystem, MessageType.Info, player, message);
    	}     	 
    	
    	player.setGameMode(toGameMode);
    }
    
	public void RegisterPlayer(Player player)
	{
		final UUID playerUUID = player.getUniqueId();
		final boolean isRegistered = _playerList.IsRegistered(playerUUID);					
		
		try 
		{
			if (isRegistered)
			{
				final String joinMessage = _config.Message.Chat.System + _config.Message.Chat.ReJoin;		
														
				_logger.SendToSender(Information.GetSystemModule(), MessageType.None, player, joinMessage);
			}
			else
			{			
				final int numberOfRegisteredPlayers = _playerList.GetNumberOfPlayers();
				final String numberOfRegisteredPlayersText = Integer.toString(numberOfRegisteredPlayers);
				String joinMessage = _config.Message.Chat.System + _config.Message.Chat.FirstJoin;
				
				joinMessage = joinMessage.replace("{PLAYER}", player.getName());
				joinMessage = joinMessage.replace("{NUMBER}", numberOfRegisteredPlayersText);

				_logger.SendToSender(Information.GetSystemModule(), MessageType.None, player, joinMessage);

				CMPlayer newcmPlayer = _dataBaseSystem.Player.RegisterNewPlayer(player, numberOfRegisteredPlayers);
					
				_playerList.Add(newcmPlayer);
			}					
				
			CMPlayer cmPlayer = _playerList.UpdatePlayer(true, playerUUID, player);
			
			_dataBaseSystem.Player.UpdatePlayer(player);
				
			_logger.SendToConsole(Information.GetSystemModule(), MessageType.Info, "&7Updating &6<&e" + cmPlayer.Information.PlayerName + "&6> &7permissions.");						
		}
		catch (PlayerNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (InvalidPlayerUUID e) 
		{
			e.printStackTrace();
		}
	}
		   		      		 
	public void ClearInventory(CommandSender sender, String[] args) throws 	NotForConsoleException, 
																			TooManyParameterException, 
																			InvalidPlayerNameException,
																			PlayerNotFoundException,
																			InvalidPlayerUUID, 
																			PlayerOfflineException
	{
		Player player;
		CMPlayer cmPlayer;
		String targetedPlayerName;
		boolean isSenderPlayer;
		int parameterLengh = args.length;

		switch(parameterLengh)
		{
		case 0:
			isSenderPlayer = sender instanceof Player;

			if(isSenderPlayer)
			{
				// Clear Inventory
				player = (Player)sender;

				player.getInventory().clear(); 		

				_logger.SendToSender(Module.PlayerSystem, MessageType.Info, sender, "&7Inventory &ecleared&7!");
			}
			else
			{
				// The console doesn't have a console
				throw new NotForConsoleException();
			}
			break;

		case 1:		
			// Delete Inventory from other player 
			targetedPlayerName = args[0];
			cmPlayer = GetPlayer(targetedPlayerName);

			if(cmPlayer.IsOffline())
			{
				throw new PlayerOfflineException(targetedPlayerName);
			}

			cmPlayer.BukkitPlayer.getInventory().clear(); 		

			_logger.SendToSender(Module.PlayerSystem, MessageType.Info, sender, "&7Inventory &ecleared&7 for user &6<&e" + cmPlayer.GetPlayerCustomName() + "&6>&7!");

			break;

		default:
			throw new TooManyParameterException();
		}	
	}

	public void ChangeNameCommand(final CommandCredentials commandCredentials) throws 	PlayerNotFoundException, 
																						InvalidPlayerUUID, 
																						NotForConsoleException, 
																						TooManyParameterException, 
																						InvalidPlayerNameException
	{	
		String message = "";		

		switch(commandCredentials.ParameterLengh)
		{
		case 0 :
			if(commandCredentials.IsSenderPlayer)
			{
				final Player player = commandCredentials.TryGetPlayer();
				final UUID playerUUID = player.getUniqueId();

				CMPlayer cmPlayer = _playerList.GetPlayer(playerUUID);
				cmPlayer.SetCustomName(null);

				_logger.SendToSender(Information.GetSystemModule(), MessageType.Info, player, "Your name has been resetted!");
			}
			else
			{		
				throw new NotForConsoleException();
			}
			break;

		case 1 :			
			if(commandCredentials.IsSenderPlayer)
			{			
				final String targetedName = commandCredentials.Parameter[0];
				final Player player = commandCredentials.TryGetPlayer();
				final UUID playerUUID = player.getUniqueId();

				CMPlayer cmPlayer = GetPlayer(playerUUID);
				cmPlayer.SetCustomName(targetedName);				

				message = _config.Message.Chat.NameChanged;
				message = _messageTags.ReplaceNameTag(message, targetedName);

				_logger.SendToSender(Information.GetSystemModule(), MessageType.Info, player, message);
			}
			else
			{
				throw new NotForConsoleException();
			}
			break;

		case 2 :
			final String targetedPlayerName = commandCredentials.Parameter[0];
			final String targetedPlayer = commandCredentials.Parameter[1];

			CMPlayer cmPlayer = GetPlayer(targetedPlayer);
			cmPlayer.SetCustomName(targetedPlayerName);	
			
			message = _config.Message.Chat.NameChanged;
			message = _messageTags.ReplaceNameTag(message, targetedPlayerName);

			_logger.SendToSender(Information.GetSystemModule(), MessageType.Info, commandCredentials.Sender, message);
			
			break;

		default:				
			throw new TooManyParameterException();
		}
	}


	public void ChangeGameMode(CommandSender sender,  String[] parameter) throws 	NotForConsoleException, 
																					TooFewParameterException,
																					RedundantGameModeChangeException,
																					InvalidGameModeException,
																					PlayerNotFoundException, 
																					TooManyParameterException, 
																					InvalidPlayerNameException,
																					InvalidPlayerUUID, 
																					PlayerOfflineException
	{
		int parameterSize = parameter.length;
		final boolean isSenderPlayer = sender instanceof Player;
		Player player;
		GameMode toGameMode;
		String gameModeText;
		CMPlayer targetedCMPlayer;
		String targetedPlayerName;

		switch(parameterSize)
		{
		case 0 :
			if(isSenderPlayer)
			{         			
				throw new TooFewParameterException();
			}
			else
			{      			
				throw new NotForConsoleException();
			}

		case 1 :
			if(isSenderPlayer)
			{        	    
				player = (Player)sender;        			
				gameModeText = parameter[0];        			
				toGameMode = BukkitAPIPlayer.GetGameModePerValue(gameModeText);

				if(toGameMode == null)
				{
					throw new InvalidGameModeException(gameModeText);
				}

				ModifyGameMode(player, toGameMode);        		
			}
			else
			{
				throw new NotForConsoleException();
			}
			break;

		case 2 : 
			gameModeText = parameter[0];
			targetedPlayerName = parameter[1];

			toGameMode = BukkitAPIPlayer.GetGameModePerValue(gameModeText);
			targetedCMPlayer = _playerList.GetPlayer(targetedPlayerName);

			if(targetedCMPlayer.IsOffline())
			{
				throw new PlayerOfflineException(targetedPlayerName);
			}

			if(toGameMode == null)
			{
				throw new InvalidGameModeException(gameModeText);
			}

			ModifyGameMode(targetedCMPlayer.BukkitPlayer, toGameMode);

			break;

		default :  
			throw new TooManyParameterException();
		}
	}

	public void ChangeSkin(CommandSender sender, String userName) throws NotForConsoleException, OfflineSkinLoadException, PlayerDoesNotExistException, SkinLoadException
	{
		final boolean isSenderPlayer = sender instanceof Player;

		UUID uuid;
		SkinData skinData;

		if(isSenderPlayer)
		{	        	
			final Player player = (Player) sender;
			final CraftPlayer craftPlayer = (CraftPlayer)player;	            
			GameProfile gp = craftPlayer.getProfile();
			String message = "&6Skin &7changed as <&e" + userName + "&7>\n&8Remember you &8&ocan't &8see it yourself!";
			String skinName;

			uuid = BukkitAPIPlayer.GetOfflinePlayerUUID(userName);

			gp.getProperties().clear();

			skinData = _skinSystem.GetSkin(uuid);

			skinName = skinData.Name;

			if(skinName != null)
			{	            	
				gp.getProperties().put(skinName, skinData.GetProperty());
			}

			BukkitAPIPlayer.RefreshAllSkinsForPlayer(player);

			_logger.SendToSender(Module.PlayerSystem, MessageType.Info, sender, message); 
		}
		else
		{
			throw new NotForConsoleException();
		}
	}

	public void Heal(CommandSender sender)
	{
		int value; 

		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			String message =  _config.Message.Player.HealedByAmount;
			double healthDifferenz = 0;

			// Health
			{
				double oldHealth = player.getHealth();
				double maxHealth = player.getHealthScale();
				healthDifferenz = maxHealth - oldHealth;

				player.setHealth(maxHealth);
			}

			// Food
			{
				int maxFoodLevel = 20;

				player.setFoodLevel(maxFoodLevel);
			}

			value = (int)Math.round(healthDifferenz); 

			message = _messageTags.ReplaceValueTag(message, Double.toString(value));

			_logger.SendToSender(Module.PlayerSystem, MessageType.Info, player, message);
		}
	}

	public void ChangeSpeed(CommandSender sender,  String[] parameter) throws NotForConsoleException
	{
		boolean isSenderPlayer = sender instanceof Player;
		int parameterLengh = parameter.length;
		float speed;
		boolean isPlayerInAir;
		Player player;
		String message;
		String speedType;

		switch(parameterLengh)
		{
		case 0:
			// ResetSpeed    	    	
			if(isSenderPlayer)
			{
				player = (Player)sender;
				speed = Integer.parseInt(parameter[0]);

				player.setWalkSpeed(speed);  

				message = "&7Speed &6resetted";

				_logger.SendToSender(Module.PlayerSystem, MessageType.Warning, player, message);
			}
			else
			{
				throw new NotForConsoleException();
			}  

		case 1:
			if(isSenderPlayer)
			{
				player = (Player)sender;
				message = "&e<&7{SPEEDTYPE}&e> &6changed &7to &e<&7{VALUE}&e>&7.";   	

				try
				{
					speed = Float.parseFloat(parameter[0]);        	    		
					isPlayerInAir = CMPlayer.IsPlayerInAir(player);

					if(speed > 1)
					{
						speed = 1;
						message += "\n" + "&7Speed got capped, it was &ctoo high";
					}

					if(speed < -1)
					{
						speed = -1;
						message += "\n" + "&7Speed got capped, it was &ctoo low";
					}        	    		

					if(isPlayerInAir)
					{
						player.setFlySpeed(speed);  
						speedType = "Flying-Speed";
					}
					else
					{
						player.setWalkSpeed(speed);  
						speedType = "Walking-Speed";
					}       	    		

					message = _messageTags.ReplaceValueTag(message, speed);    
					message = _messageTags.ReplaceSpeedTypeTag(message, speedType);    

					_logger.SendToSender(Module.PlayerSystem, MessageType.Info, player, message);
				}
				catch(NumberFormatException numberFormatException)
				{
					message = "&7The speed was &cnot &7a number!";

					_logger.SendToSender(Module.PlayerSystem, MessageType.Error, player, message);
				}        	 
				catch(Exception exception)
				{
					message = "&7Error while chaging speed >> "  + exception.getMessage();

					_logger.SendToSender(Module.PlayerSystem, MessageType.Error, player, message);
				}   
			}
			else
			{
				throw new NotForConsoleException();
			}    	
		}
	}  

	@SuppressWarnings("deprecation")
	public void PutItemOnHead(CommandSender sender) 
	{
		Player player;
		final boolean isSenderPlayer = sender instanceof Player;

		if(isSenderPlayer)
		{
			player = (Player)sender;

			PlayerInventory inv = player.getInventory();

			ItemStack hand = player.getItemInHand();

			if (inv.getHelmet() != null) 
			{
				inv.addItem(new ItemStack[] { inv.getHelmet() });
			}
			inv.setHelmet(hand);
			inv.remove(hand);

		}				
	}

	public CMPlayer GetPlayer(UUID playerUUID) throws PlayerNotFoundException, InvalidPlayerUUID 
	{
		return _playerList.GetPlayer(playerUUID);
	}
	
	public CMPlayer GetPlayer(String playerName) throws InvalidPlayerNameException, PlayerNotFoundException, InvalidPlayerUUID 
	{
		return _playerList.GetPlayer(playerName);
	}

	public void Remove(UUID playerUUID) throws PlayerNotFoundException, InvalidPlayerUUID 
	{
		_playerList.Remove(playerUUID);			
	}

	public List<CMPlayer> GetPlayers() 
	{
		return _playerList.GetPlayers();
	}

	public void PlayerQuitEvent(UUID playerUUID, Player player) 
	{		
		_playerList.UpdatePlayer(false, playerUUID, player);			
	}		 
		
	public void GetPlayerInfo(CommandSender sender, String[] parameter) throws 	PlayerNotFoundException, 
																				InvalidPlayerUUID, 
																				NotForConsoleException, 
																				TooManyParameterException, 
																				InvalidPlayerNameException 
	{
		final boolean isSenderPlayer = sender instanceof Player;
		final int parameterLengh = parameter.length;

		switch(parameterLengh)
		{
		case 0:
			if(isSenderPlayer)
			{
				final Player player = (Player)sender;
				final UUID playerUUID = player.getUniqueId();
				final CMPlayer cmplayer = GetPlayer(playerUUID);
				cmplayer.GetPlayerInfo(sender);
			}
			else
			{
				throw new NotForConsoleException();
			}		
			break;

		case 1:
			final String targetedPlayerName = parameter[0];			
			final CMPlayer cmPlayer = GetPlayer(targetedPlayerName);

			cmPlayer.GetPlayerInfo(sender);

			break;

		default:
			throw new TooManyParameterException();			

		}		
	}
}