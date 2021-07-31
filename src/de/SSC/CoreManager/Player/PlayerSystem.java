package de.SSC.CoreManager.Player;

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

import de.SSC.CoreManager.Chat.Logger;
import de.SSC.CoreManager.Chat.MessageTags;
import de.SSC.CoreManager.Chat.MessageType;
import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DataBaseSystem;
import de.SSC.CoreManager.Player.Exception.InvalidGameModeException;
import de.SSC.CoreManager.Player.Exception.InvalidPlayerNameException;
import de.SSC.CoreManager.Player.Exception.InvalidPlayerUUID;
import de.SSC.CoreManager.Player.Exception.PlayerDoesNotExistException;
import de.SSC.CoreManager.Player.Exception.PlayerNotFoundException;
import de.SSC.CoreManager.Player.Exception.PlayerOfflineException;
import de.SSC.CoreManager.Player.Exception.RedundantGameModeChangeException;
import de.SSC.CoreManager.Skin.SkinData;
import de.SSC.CoreManager.Skin.SkinSystem;
import de.SSC.CoreManager.Skin.Exception.OfflineSkinLoadException;
import de.SSC.CoreManager.Skin.Exception.SkinLoadException;
import de.SSC.CoreManager.System.BaseSystem;
import de.SSC.CoreManager.System.ISystem;
import de.SSC.CoreManager.System.SystemPriority;
import de.SSC.CoreManager.System.SystemState;
import de.SSC.CoreManager.System.Exception.NotForConsoleException;
import de.SSC.CoreManager.System.Exception.SystemNotActiveException;
import de.SSC.CoreManager.System.Exception.TooFewParameterException;
import de.SSC.CoreManager.System.Exception.TooManyParameterException;
import de.SSC.BukkitAPI.BukkitAPISystem;

public class PlayerSystem extends BaseSystem implements ISystem
{
	private static PlayerSystem _instance;
	
	private Logger _logger;
	private Config _config;
	private MessageTags _messageTags;
	private BukkitAPISystem _bukkitUtility;
	private DataBaseSystem _dataBaseSystem;	
	private PlayerList _playerList;
	private SkinSystem _skinSystem;
		
	private PlayerSystem()
	{
		super(Module.PlayerSystem, SystemState.Active, SystemPriority.Essential);
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
		_bukkitUtility = BukkitAPISystem.Instance();
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
		
		final List<CMPlayer> cmPlayerList = _dataBaseSystem.Player.LoadAllPlayers();		
		
		for(CMPlayer cmPlayer : cmPlayerList)
		{
			_playerList.Add(cmPlayer);
		}
		
		if(!firstRun)
		{
			// After the coremanager reload players can
			
			List<Player> players = _bukkitUtility.Player.GetAllOnlinePlayers();
			
			for(Player player : players)
			{
				final UUID playerUUID = player.getUniqueId();
				final boolean isRegistered = _playerList.IsRegistered(playerUUID);	
				
				try 
				{
					if(isRegistered)
					{				
						_playerList.UpdatePlayer(PlayerState.Online, playerUUID, player);				
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
    		message =  _config.Messages.Player.GameModeChanged;   
            
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
					final String joinMessage = _config.Messages.Chat.System + _config.Messages.Chat.ReJoin;		
														
					_logger.SendToSender(Module.ChatSystem, MessageType.None, player, joinMessage);
				}
				else
				{			
					final int numberOfRegisteredPlayers = _playerList.GetNumberOfPlayers();
					final String numberOfRegisteredPlayersText = Integer.toString(numberOfRegisteredPlayers);
					String joinMessage = _config.Messages.Chat.System + _config.Messages.Chat.FirstJoin;
		
					joinMessage = joinMessage.replace("{PLAYER}", player.getName());
					joinMessage = joinMessage.replace("{NUMBER}", numberOfRegisteredPlayersText);

					_logger.SendToSender(Module.PlayerSystem, MessageType.None, player, joinMessage);

					CMPlayer newcmPlayer = _dataBaseSystem.Player.RegisterNewPlayer(player, numberOfRegisteredPlayers);
					
					_playerList.Add(newcmPlayer);
				}					
				
				CMPlayer cmPlayer = _playerList.UpdatePlayer(PlayerState.Online, playerUUID, player);
				
				_logger.SendToConsole(Module.PlayerSystem, MessageType.Info, "&7Updating &6<&e" + cmPlayer.PlayerName + "&6> &7permissions.");
				
				cmPlayer.SetPermissions();	
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


		   
		    public boolean IsLookingInADirection(ViewDirection viewDirection)
		    {		    	
				boolean isPlayerLookingInADirection;
		    	
				switch(viewDirection)
				{
				case South:			
				case East:			
				case North:			
				case West:	
					isPlayerLookingInADirection = true;
					break;
					
				case NorthEast:
				case NorthWest:
				case SouthEast:			
				case SouthWest:	
				default:
					isPlayerLookingInADirection = false;
					break;
					
				}
				
				return isPlayerLookingInADirection;
		    }   
		      
		 public ViewDirection GetViewDirection(Player player) 
		 {
		        double rotation = (player.getLocation().getYaw() - 90) % 360;
		        
		        if (rotation < 0) 
		        {
		            rotation += 360.0;
		        }
		        if (0 <= rotation && rotation < 22.5)
		        {
		            return ViewDirection.North;
		        } else if (22.5 <= rotation && rotation < 67.5) 
		        {
		            return ViewDirection.NorthEast;
		        } else if (67.5 <= rotation && rotation < 112.5)
		        {
		            return ViewDirection.East;
		        } else if (112.5 <= rotation && rotation < 157.5) 
		        {
		            return ViewDirection.SouthEast;
		        } else if (157.5 <= rotation && rotation < 202.5) 
		        {
		            return ViewDirection.South;
		        } else if (202.5 <= rotation && rotation < 247.5)
		        {
		            return ViewDirection.SouthWest;
		        } else if (247.5 <= rotation && rotation < 292.5) 
		        {
		            return ViewDirection.West;
		        } else if (292.5 <= rotation && rotation < 337.5) 
		        {
		            return ViewDirection.NorthWest;
		        } else if (337.5 <= rotation && rotation < 360.0) 
		        {
		            return ViewDirection.North;
		        } else 
		        {
		            return null;
		        }
		    }
		 
		 public void ClearInventory(CommandSender sender, String[] args) throws 	NotForConsoleException, 
			TooManyParameterException, 
			InvalidPlayerNameException,
			PlayerNotFoundException,
			InvalidPlayerUUID, PlayerOfflineException
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

public void ChangeNameCommand(CommandSender sender, String[] parameter) throws PlayerNotFoundException, InvalidPlayerUUID
{	
int parameterLengh = parameter.length;
boolean isSenderPlayer = sender instanceof Player;
Player player;		
UUID playerUUID;
CMPlayer cmPlayer;
String message = "";
String targetedName;

switch(parameterLengh)
{
case 0 :
if(isSenderPlayer)
{
player = (Player)sender;
playerUUID = player.getUniqueId();

cmPlayer = _playerList.GetPlayer(playerUUID);

cmPlayer.SetCustomName(null);

_logger.SendToSender(Module.PlayerSystem, MessageType.Info, sender, "Your name has been resetted!");
}
else
{		
_logger.SendToSender(Module.PlayerSystem, MessageType.Warning, sender, _config.Messages.ConsoleIO.NotForConsole);
}
break;

case 1 :
targetedName = parameter[0];

if(isSenderPlayer)
{			
player = (Player)sender;
playerUUID = player.getUniqueId();

cmPlayer = GetPlayer(playerUUID);

cmPlayer.SetCustomName(targetedName);				

message = _config.Messages.Chat.NameChanged;

message = _messageTags.ReplaceNameTag(message, targetedName);

_logger.SendToSender(Module.PlayerSystem, MessageType.Warning, sender, message);
}
else
{
_logger.SendToSender(Module.PlayerSystem, MessageType.Warning, sender, message);
}
break;

case 2 :
targetedName = parameter[0];

if(isSenderPlayer)
{				
message = _config.Messages.Chat.NameChanged +  targetedName;

message = _messageTags.ReplaceNameTag(message, targetedName);

_logger.SendToSender(Module.PlayerSystem, MessageType.Warning, sender, message);
}
else
{
_logger.SendToSender(Module.PlayerSystem, MessageType.Warning, sender, message);
}
break;


default:				
_logger.SendToSender(Module.PlayerSystem, MessageType.Warning, sender, _config.Messages.Chat.NameChangesWrongCommand);

break;
}
}


public void ChangeGameMode(CommandSender sender,  String[] parameter) 
throws 	NotForConsoleException, 
TooFewParameterException,
RedundantGameModeChangeException,
InvalidGameModeException,
PlayerNotFoundException, 
TooManyParameterException, 
InvalidPlayerNameException,
InvalidPlayerUUID, PlayerOfflineException
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
toGameMode = _bukkitUtility.Player.GetGameModePerValue(gameModeText);

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

toGameMode = _bukkitUtility.Player.GetGameModePerValue(gameModeText);
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

uuid = _bukkitUtility.Player.GetOfflinePlayerUUID(userName);

gp.getProperties().clear();

skinData = _skinSystem.GetSkin(uuid);

skinName = skinData.Name;

if(skinName != null)
{	            	
gp.getProperties().put(skinName, skinData.GetProperty());
}

_bukkitUtility.Player.RefreshAllSkinsForPlayer(player);

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
String message =  _config.Messages.Player.HealedByAmount;
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

public void ChangeSpeed(CommandSender sender,  String[] parameter)
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
_logger.SendToConsole(Module.PlayerSystem, MessageType.Warning, _config.Messages.ConsoleIO.NotForConsole);
}  

case 1:
if(isSenderPlayer)
{
player = (Player)sender;
message = "&e<&7{SPEEDTYPE}&e> &6changed &7to &e<&7{VALUE}&e>&7.";   	

try
{
speed = Float.parseFloat(parameter[0]);        	    		
isPlayerInAir = _bukkitUtility.Player.IsPlayerInAir(player);

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
_logger.SendToConsole(Module.PlayerSystem, MessageType.Warning, _config.Messages.ConsoleIO.NotForConsole);
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
			try {
				_playerList.UpdatePlayer(PlayerState.Offline, playerUUID, player);
			} catch (PlayerNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidPlayerUUID e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
		}		 
}