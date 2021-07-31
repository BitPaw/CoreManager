package de.SSC.CoreManager.Editor;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DataTypes.CMPlayer;
import de.SSC.CoreManager.DataBase.DataTypes.CMPlayerList;
import de.SSC.CoreManager.Messages.Logger;
import de.SSC.CoreManager.Messages.MessageType;
import de.SSC.CoreManager.Messages.Module;
import de.SSC.CoreManager.Utility.BukkitUtility;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerEditor
{
    private static PlayerEditor _instance;
	private Logger _logger;
    private BukkitUtility _bukkitUtility;
    private Config _config;
    private CMPlayerList _cmPlayerList;

    public PlayerEditor()
    {
    	_instance = this;
    	
    	_logger = Logger.Instance();
    	_bukkitUtility = BukkitUtility.Instance();
    	_config = Config.Instance();
    	_cmPlayerList = CMPlayerList.Instance();
    }

	public static PlayerEditor Instance() 
	{
		return _instance == null ?  new PlayerEditor() : _instance;
	}
    
    public void Heal(CommandSender sender)
    {
    	 if(_bukkitUtility.IsSenderPlayer(sender))
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

            message = message.replace(_config.Messages.Player.ValueTag, Double.toString(healthDifferenz));

            _logger.SendToSender(Module.PlayerManiPulator, MessageType.Info, player, message);
        }
    }

    public void ChangeSpeed(CommandSender sender,  int value)
    {
    	 if(_bukkitUtility.IsSenderPlayer(sender))
        {
            try
            {

            } catch (Exception e)
            {
               // _messager.MessageToPlayer(player, _messages.GameModeChangeError);
            }
        }
    }

    private String GetGameModeName(GameMode gamemode)
    {
    	String gameModeString = "Error";
    	
        switch(gamemode)
        {
			case ADVENTURE:
				gameModeString = _config.Messages.Player.Adventure;
				break;
				
			case CREATIVE:
				gameModeString = _config.Messages.Player.Creative;
				break;
			
			case SPECTATOR:
				gameModeString = _config.Messages.Player.Spectator;
				break;
			
			case SURVIVAL:
				gameModeString = _config.Messages.Player.Survival;
				break;  
			
        }   
    	
    	return gameModeString;
    }
    
    private void ModifyGameMode(CommandSender sender, Player player, GameMode toGameMode)
    {
    	GameMode currentGameMode = player.getGameMode();
        String fromGameModeText;
        String toGameModeText; 
    	String message;
    	
        if(currentGameMode == toGameMode)
    	{
    		 message = _config.Messages.Player.YouAreAlreadyInThisGameMode;
    		
    		 fromGameModeText = GetGameModeName(toGameMode);
    		 
    	     message = message.replace(_config.Messages.Player.GameModeTag, fromGameModeText);
    		
    		 _logger.SendToSender(Module.PlayerManiPulator, MessageType.Warning, player, message);
    	}
    	else
    	{
    		message =  _config.Messages.Player.GameModeChanged;   
    		
    		fromGameModeText = GetGameModeName(toGameMode);
            toGameModeText = GetGameModeName(currentGameMode);      
            
            message = message.replace(_config.Messages.Player.GameModeTagNew, fromGameModeText);
            message = message.replace(_config.Messages.Player.GameModeTagOld, toGameModeText);     
                        
            _logger.SendToSender(Module.GameModeChanger, MessageType.Info, player, message);
    	} 
    	  
    	/*
    	try
        { 	           
                        
        }
        catch (Exception e)
        {
        	message = _config.Messages.PlayerEditor.GameModeChangeError + gameModeText + " unkown gamemode.";
        	
        	 _logger.SendToSender(MessageType.Info, player, message);
        }*/
    	
    	player.setGameMode(toGameMode);
    }
    
	public void ChangeGameMode(CommandSender sender,  String[] parameter)
    {
		int parameterSize = parameter.length;
		boolean isSenderPlayer = _bukkitUtility.IsSenderPlayer(sender);
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
        			
        	    }
        		else
        		{
        			_logger.SendToSender(Module.PlayerManiPulator, MessageType.Error, sender, "You cant reset your GameMode as Console!");
        		}
        		break;
        		
        	case 1 :
        		if(isSenderPlayer)
        	    {        	    
        			player = (Player)sender;        			
        			gameModeText = parameter[0];        			
        			toGameMode = _bukkitUtility.GetGameModePerValue(gameModeText);
        			
        			ModifyGameMode(sender, player, toGameMode);        		
        	    }
        		break;
        		
        	case 2 : 
        		gameModeText = parameter[0];
        		targetedPlayerName = parameter[1];
        		
        		toGameMode = _bukkitUtility.GetGameModePerValue(gameModeText);
        		targetedCMPlayer = _cmPlayerList.GetPlayer(targetedPlayerName);
        		
        		if(targetedCMPlayer == null)
        		{
        			_logger.SendToSender(Module.PlayerManiPulator, MessageType.Error, sender, "&cPlayer not found!");
        		}
        		else
        		{
        			ModifyGameMode(sender, targetedCMPlayer.BukkitPlayer, toGameMode);
        		}
        		        		
        		break;
        		
        	default :  
        		
        		break;
        }
    }


}
