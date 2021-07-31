package de.SSC.CoreManager.Systems.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Systems.Chat.ExceptionInformation;
import de.SSC.CoreManager.Systems.Chat.Logger;
import de.SSC.CoreManager.Systems.Chat.MessageTags;
import de.SSC.CoreManager.Systems.Chat.MessageType;
import de.SSC.CoreManager.Systems.Chat.Module;
import de.SSC.CoreManager.Systems.DataBase.DataBaseSystem;
import de.SSC.CoreManager.Systems.SkinChanger.SkinLoader;
import de.SSC.CoreManager.Utility.BukkitUtility;
import de.SSC.CoreManager.Utility.NotForConsoleException;
import de.SSC.CoreManager.Utility.TooFewParameterException;
import de.SSC.CoreManager.Utility.TooManyParameterException;

public class PlayerSystem 
{
	private static PlayerSystem _instance;
	private List<CMPlayer> _players;
	
	private Logger _logger;
	private Config _config;
	private MessageTags _messageTags;
	private BukkitUtility _bukkitUtility;
	private DataBaseSystem _dataBaseSystem;
	
	private PlayerSystem()
	{
		_instance = this;
		
		_players = new ArrayList<CMPlayer>();
		_logger = Logger.Instance();
		_config = Config.Instance();
		_messageTags = MessageTags.Instance();
		_bukkitUtility = BukkitUtility.Instance();
		_dataBaseSystem = DataBaseSystem.Instance();
	}
	
	public static PlayerSystem Instance()
	{	
		return _instance == null ? new PlayerSystem() : _instance;
	}
	
	public void CheckForUndetectedPlayers()
	{
		List<Player> players = _bukkitUtility.PlayerUtility.GetAllOnlinePlayers();
		
		for(Player player : players)
		{
			CMPlayer cmPlayer = GetPlayer(player);
			
			if(cmPlayer == null)
			{
				cmPlayer = _dataBaseSystem.LoadPlayer(player);
				
				AddPlayer(cmPlayer);
			}
		}
	}
	
	public void AddPlayer(CMPlayer cmPlayer)
	{
		String message = _config.Messages.Player.UserAdded;
		
		message = _messageTags.ReplacePlayerTag(message, cmPlayer);
		
		_logger.SendToConsole(Module.PlayerSystem, MessageType.Info, message);
		
		_players.add(cmPlayer);
	}
	
	public void RemovePlayer(Player player)
	{
		CMPlayer cmPlayer = GetPlayer(player);
		
		_players.remove(cmPlayer);
	}
	
	public void RemovePlayer(CMPlayer player)
	{
		_players.remove(player);
	}

	public CMPlayer GetPlayer(String playerName) 
	{
		CMPlayer returnCMPlayer = null;
		String targetPlayerName;
		
		for(CMPlayer cmPlayer : _players)
		{
			targetPlayerName = cmPlayer.BukkitPlayer.getName();
			  
			  if(targetPlayerName.compareToIgnoreCase(playerName) == 0)
			  {
				  returnCMPlayer = cmPlayer;
				  break;
			  }
		  }
		  	  
		  if(returnCMPlayer == null)
		  {		
			  String message = _config.Messages.Player.PlayerNotFound;
			  		  
			  message = _messageTags.ReplacePlayerTag(message, playerName);
			  
			  _logger.SendToConsole(Module.PlayerSystem, MessageType.Warning, message);				
		  }
		  
		  return returnCMPlayer;
	}
	
	public CMPlayer GetPlayer(UUID playerUUID)
	{		
		CMPlayer returnCMPlayer = null;
		  
		  for(CMPlayer cmPlayer : _players)
		  {
			  String target = cmPlayer.PlayerUUID.toString().toLowerCase();
			  String searcher = playerUUID.toString().toLowerCase();
			  
			  if(target.equals(searcher))
			  {
				  returnCMPlayer = cmPlayer;
				  break;
			  }
		  }
		  	  
		  if(returnCMPlayer == null)
		  {				
			  String message = _config.Messages.Player.PlayerNotFound;
						  
			  message = _messageTags.ReplacePlayerTag(message, playerUUID.toString());	  
				
			  _logger.SendToConsole(Module.PlayerSystem, MessageType.Error, message);				
		  }
		  
		  return returnCMPlayer;
	}
	
	public CMPlayer GetPlayer(Player player) 
	{
		CMPlayer returnCMPlayer = null;
		  
		  for(CMPlayer cmPlayer : _players)
		  {
			  String target = cmPlayer.PlayerUUID.toString().toLowerCase();
			  String searcher = player.getUniqueId().toString().toLowerCase();
			  
			  if(target.equals(searcher))
			  {
				  returnCMPlayer = cmPlayer;
				  break;
			  }
		  }
		  	  
		  if(returnCMPlayer == null)
		  {				
			  String message = _config.Messages.Player.PlayerNotFound;
			  String playerName = _bukkitUtility.PlayerUtility.GetPlayerCustomName(player);
			  
			  message = _messageTags.ReplacePlayerTag(message, playerName);	  
				
			  _logger.SendToConsole(Module.PlayerSystem, MessageType.Error, message);				
		  }
		  
		  return returnCMPlayer;
	}

	public List<CMPlayer> GetList()
	{
		return _players;
	}
	
	public void ChangeNameCommand(CommandSender sender, String[] parameter)
	{	
		int parameterLengh = parameter.length;
		boolean isSenderPlayer = _bukkitUtility.PlayerUtility.IsSenderPlayer(sender);;
		Player player;			
		CMPlayer cmPlayer;
		String message = "";
		String targetedName;
		switch(parameterLengh)
		{
		case 0 :
			if(isSenderPlayer)
			{
				player = (Player)sender;
				
				cmPlayer = GetPlayer(player);
				
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
				
				cmPlayer = GetPlayer(player);
				
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
	
    public void Heal(CommandSender sender)
    {
    	int value; 
    	
    	 if(_bukkitUtility.PlayerUtility.IsSenderPlayer(sender))
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
    	boolean isSenderPlayer = _bukkitUtility.PlayerUtility.IsSenderPlayer(sender);
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
        	    		isPlayerInAir = _bukkitUtility.PlayerUtility.IsPlayerInAir(player);
        	    		
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
    
    private void ModifyGameMode(CommandSender sender, Player player, GameMode toGameMode) throws RedundantGameModeChangeException
    {
    	GameMode currentGameMode = player.getGameMode();
    	String message;
    	
        if(currentGameMode == toGameMode)
    	{
        	ExceptionInformation exceptionInformation = new ExceptionInformation(this, sender);
        	
        	throw new RedundantGameModeChangeException(exceptionInformation, currentGameMode);
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
    
	public void ChangeGameMode(CommandSender sender,  String[] parameter) 
	throws 	NotForConsoleException, 
			TooFewParameterException,
			RedundantGameModeChangeException,
			InvalidGameModeException,
			PlayerNotFoundException, 
			TooManyParameterException
    {
		int parameterSize = parameter.length;
		boolean isSenderPlayer = _bukkitUtility.PlayerUtility.IsSenderPlayer(sender);
        Player player;
        GameMode toGameMode;
        String gameModeText;
        CMPlayer targetedCMPlayer;
        String targetedPlayerName;
        ExceptionInformation exceptionInformation = new ExceptionInformation(this, sender);
        
        switch(parameterSize)
        {
        	case 0 :
        		if(isSenderPlayer)
        	    {         			
        			throw new TooFewParameterException(exceptionInformation);
        	    }
        		else
        		{      			
        			throw new NotForConsoleException(exceptionInformation);
        		}
        		
        	case 1 :
        		if(isSenderPlayer)
        	    {        	    
        			player = (Player)sender;        			
        			gameModeText = parameter[0];        			
        			toGameMode = _bukkitUtility.GetGameModePerValue(gameModeText);
        			
        			if(toGameMode == null)
        			{
        				throw new InvalidGameModeException(exceptionInformation, gameModeText);
        			}
        			
        			ModifyGameMode(sender, player, toGameMode);        		
        	    }
        		else
        		{
        			throw new NotForConsoleException(exceptionInformation);
        		}
        		break;
        		
        	case 2 : 
        		gameModeText = parameter[0];
        		targetedPlayerName = parameter[1];
        		
        		toGameMode = _bukkitUtility.GetGameModePerValue(gameModeText);
        		targetedCMPlayer = GetPlayer(targetedPlayerName);
        		
        		if(toGameMode == null)
    			{
    				throw new InvalidGameModeException(exceptionInformation, gameModeText);
    			}
        		
        		if(targetedCMPlayer == null)
        		{
        			throw new PlayerNotFoundException(exceptionInformation, targetedPlayerName);
        		}
        		else
        		{
        			ModifyGameMode(sender, targetedCMPlayer.BukkitPlayer, toGameMode);
        		}
        		        		
        		break;
        		
        	default :  
        		throw new TooManyParameterException(exceptionInformation);
        }
    }
	
	
	   @SuppressWarnings("deprecation")
		public void ChangeSkin(CommandSender sender, String userName)
	    {
		   SkinLoader skinLoader;
       		String skingName;
       	 UUID uuid;
       		
	        if(_bukkitUtility.PlayerUtility.IsSenderPlayer(sender))
	        {
	        	
	            Player player = (Player) sender;
	            CraftPlayer craftPlayer = ((CraftPlayer)player);	            
	            GameProfile gp = craftPlayer.getProfile();
	            String message = "&6Skin &7changed as <&e" + userName + "&7>\n&8Remember you &8&ocan't &8see it yourself!";
	            
	            uuid = _bukkitUtility.PlayerUtility.GetOfflinePlayerUUID(userName);
	            
	            gp.getProperties().clear();

	            skinLoader = new SkinLoader(uuid);

	            skingName = skinLoader.Data.Name;
	            
	            if(skingName != null)
	            {	            	
	                gp.getProperties().put(skingName, skinLoader.Data.GetProperty());
	            }
	          
	            for(Player pl : Bukkit.getOnlinePlayers())
                {	            	
                    pl.hidePlayer(player);
                }
           
                for(Player pl : Bukkit.getOnlinePlayers())
                {
                    pl.showPlayer(player);
                }

	            _logger.SendToSender(Module.PlayerSystem, MessageType.Info, sender, message);           

	        }
	        else
	        {
	        	// Not for console
	        }
	    }
}
