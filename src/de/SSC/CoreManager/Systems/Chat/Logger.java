package de.SSC.CoreManager.Systems.Chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Utility.BukkitUtility;

public class Logger
{
    private static Logger _instance;
	private Config _config;
	private BukkitUtility _bukkitUtility;
    
    public Logger()
    {
    	_instance = this;
    	
    	_bukkitUtility = BukkitUtility.Instance();
    	_config = Config.Instance();    	
    	
    	SendToConsole(Module.Logger, MessageType.Online, _config.Messages.ConsoleIO.On);
    }
    
    public static Logger Instance()
    {
    	return _instance == null ? new Logger() : _instance;
    }   
    
    public String TransformToColor(String message)
    {    	
    	return ChatColor.translateAlternateColorCodes(_config.Chat.ColorCombineChar, message);
    }
    
    public String RemoveColor(String message)
    {
    	return ChatColor.stripColor(message);
    }
    
    public void SendToSender(Module module, MessageType messageType, CommandSender sender, String message)
    {    
    	if(_bukkitUtility == null)
    	{
    		return;
    	}
    	
    	String outPutMessage = "";
    	boolean isSenderPlayer =  _bukkitUtility.PlayerUtility.IsSenderPlayer(sender);
    	boolean addSystemTag =  !isSenderPlayer || (isSenderPlayer && _config.Chat.ShowUserSystemTag);
    	
    	/* Is Player	showTagsToPlayer	Do
    	 * 0			0					1
    	 * 0			1					1
    	 * 1			0					0
    	 * 1			1					1
    	 */
    	if(messageType != MessageType.None && addSystemTag)
    	{
    		switch(module)
        	{
        	
        	case Logger:
        		outPutMessage += "&7[&4Logger&7]";
        		break;
        		
        	case Config:
        		outPutMessage += "&7[&bConfig&7]";
        		break;
        		
    		case ChatSystem:
    			outPutMessage += "&7[&aChatSystem&7]";
    			break;
    			
    		case CoreManager:
    			outPutMessage += _config.Messages.ConsoleIO.CoreManager;
    			break;
    			
    		case DataBase:
    			outPutMessage += _config.Messages.DataBase.DataBaseTag;
    			break;
    			
    		case EventSystem:
    			outPutMessage += "&7[&3EventSystem&7]&r";
    			break;
    			
    		case PermissionSystem:
    			outPutMessage += "&7[&rPermissionSystem&7]";
    			break;
    			    			
    		case System:
    			outPutMessage += "&7[&3System&7]";
    			break;
    			    			
    		case WarpSystem:
    			outPutMessage += "&7[&eWarpList&7]";
    			break;
    			
    		case WorldSystem:
    			outPutMessage += _config.Messages.World.WorldSystemTag;
    			break;
    			
    		case RegionSystem:
    			outPutMessage += "&7[&rPermissionSystem&7]";
    			break;
    			
    		default:
    			outPutMessage += "&7[&rUnkown&7]&r";
    			break;    
        	    	
        	}
    	}   	
    	
    	
      	switch(messageType)
    	{
      	
		case Command:
			outPutMessage +=  _config.Messages.ConsoleIO.Command;
			break;
			
		case Error:
			outPutMessage +=  _config.Messages.ConsoleIO.Error;
			break;
			
		case Info:
			outPutMessage +=  _config.Messages.ConsoleIO.Info;
			break;
			
		case Loading:
			outPutMessage +=  _config.Messages.ConsoleIO.Loading;
			break;
			
		case None:
			break;
			
		case Offline:
			outPutMessage +=  _config.Messages.ConsoleIO.Offline;
			break;
			
		case Online:
			outPutMessage +=  _config.Messages.ConsoleIO.Online;
			break;
			
		case Question:
			outPutMessage +=  _config.Messages.ConsoleIO.Question;
			break;
			
		case Warning:
			outPutMessage +=  _config.Messages.ConsoleIO.Warning;
			break;
			
		default:
			break; 	      	
    	}
    	
      	// Add prefix to message
      	outPutMessage += message; 
    	
      	if(_bukkitUtility.PlayerUtility.IsSenderPlayer(sender))
      	{
      		if(_config.Chat.UseColor)
        	{
      			outPutMessage = TransformToColor(outPutMessage);
        	}
        	else
        	{
        		outPutMessage = RemoveColor(outPutMessage);
        	}   
      	}
      	else
      	{
     		if(_config.Chat.ColorLogs)
        	{
     			outPutMessage = TransformToColor(outPutMessage);
        	}
        	else
        	{
        		outPutMessage = RemoveColor(outPutMessage);
        	}
      	}  
    	
    	sender.sendMessage(outPutMessage); 
    }
       
    public void SendToConsole(Module module, MessageType messageType, String message)
    {
    	CommandSender sender = Bukkit.getConsoleSender();    	
    	
    	SendToSender(module, messageType, sender, message);  
    }


}