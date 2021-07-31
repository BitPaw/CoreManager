package de.SSC.CoreManager.Editor;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DataTypes.CMPlayer;
import de.SSC.CoreManager.DataBase.DataTypes.CMPlayerList;
import de.SSC.CoreManager.Messages.Logger;
import de.SSC.CoreManager.Messages.MessageType;
import de.SSC.CoreManager.Messages.Module;
import de.SSC.CoreManager.Utility.BukkitUtility;
import de.SSC.CoreManager.Utility.MessageTags;

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
    private MessageTags _messageTags;

    public PlayerEditor()
    {
    	_instance = this;
    	
    	_logger = Logger.Instance();
    	_bukkitUtility = BukkitUtility.Instance();
    	_config = Config.Instance();
    	_cmPlayerList = CMPlayerList.Instance();
    	_messageTags = MessageTags.Instance();
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

            message = _messageTags.ReplaceValueTag(message, Double.toString(healthDifferenz));

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
    
    private void ModifyGameMode(CommandSender sender, Player player, GameMode toGameMode)
    {
    	GameMode currentGameMode = player.getGameMode();
    	String message;
    	
        if(currentGameMode == toGameMode)
    	{
    		 message = _config.Messages.Player.YouAreAlreadyInThisGameMode;
    		    		
    		 message = _messageTags.ReplaceGameMode(message, toGameMode);
    		
    		 _logger.SendToSender(Module.PlayerManiPulator, MessageType.Warning, player, message);
    	}
    	else
    	{
    		message =  _config.Messages.Player.GameModeChanged;   
            
            message = _messageTags.ReplaceGameModeOld(message, currentGameMode);
            message = _messageTags.ReplaceGameModeNew(message, toGameMode);   
                        
            _logger.SendToSender(Module.GameModeChanger, MessageType.Info, player, message);
    	}     	 
    	
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
