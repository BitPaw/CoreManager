package de.SSC.CoreManager.Chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Sound.SoundSystem;
import de.SSC.CoreManager.System.BaseSystem;
import de.SSC.CoreManager.System.ISystem;
import de.SSC.CoreManager.System.SystemPriority;
import de.SSC.CoreManager.System.SystemState;

public class Logger extends BaseSystem implements ISystem
{
    private static Logger _instance;
	private Config _config;
	private SoundSystem _soundSystem;
	
	  private final String _colorBlack = "&0";
	  private final String _colorDarkBlue = "&1";
	  private final String _colorDarKGreen = "&2";
	  private final String _colorDarkCyan = "&3";
	  private final String _colorDarkRed = "&4";
	  private final String _colorPurple = "&5";
	  private final String _colorGold = "&6";
	  private final String _colorLightGray = "&7";
	  private final String _colorGray = "&8";
	  private final String _colorAqua = "&9";

	  private final String _colorLightGreen = "&a";
	  private final String _colorLightBlue = "&b";
	  private final String _colorLightRed = "&c";
	  private final String _colorPink = "&d";
	  private final String _colorYellow = "&e";
	  private final String _colorWhite = "&f";

	  private final String _colorBold = "&l";
	  private final String _colorMagic = "&k";
	  private final String _colorReset = "&r";
	  private final String _colorItalic = "&o";
	  private final String _colorUnderline = "&n";
	  private final String _colorStrikethrough = "&m";
    
    private Logger()
    {
    	super(Module.Logger, SystemState.Active, SystemPriority.Essential);    	
    	_instance = this;    
    }
    
    public static Logger Instance()
    {
    	return _instance == null ? new Logger() : _instance;
    }   
        
	@Override
	public void LoadReferences() 
	{
    	_config = Config.Instance();    
    	_soundSystem = SoundSystem.Instance();
	}

	@Override
	public void Reload(final boolean firstRun) 
	{
		
	}
	
	@Override
	public void PrintData(CommandSender sender) 
	{
		
	}
    
    public String TransformToColor(String message)
    {    	
    	return ChatColor.translateAlternateColorCodes(_config.Chat.ColorCombineChar, message);
    }
    
    public String RemoveColor(String message)
    {
    	return ChatColor.stripColor(message);
    }
    
    public void PrintColors(CommandSender sender)
    {
    	boolean isSenderConsole = !(sender instanceof Player);
    	String message = "";
    	
    	if(isSenderConsole)
    	{
    		message += "\n";
    	}
    	
    	message += "&6===[&eColors&6]============================\n";	
    	message += "&7[&r& 0&7] &0Black      &8| &7[&r& a&7] &aGreen\n";	
    	message += "&7[&r& 1&7] &1Dark-Blue  &8| &7[&r& b&7] &bCyan\n";	
    	message += "&7[&r& 2&7] &2Dark-Green &8| &7[&r& c&7] &cRed\n";	
    	message += "&7[&r& 3&7] &3Dark-Cyan  &8| &7[&r& d&7] &dPink\n";	
    	message += "&7[&r& 4&7] &4Dark-Red   &8| &7[&r& e&7] &eYellow\n";	
    	message += "&7[&r& 5&7] &5Purple     &8| &7[&r& f&7] &rWhite\n";	
    	message += "&7[&r& 6&7] &6Gold       &8| &7[&r& o&7] &oItalic\n";	
    	message += "&7[&r& 7&7] &7Light-Gray &8| &7[&r& l&7] &lBold\n";	
    	message += "&7[&r& 8&7] &8Gray       &8| &7[&r& m&7] &mStrikethrough\n";	
    	message += "&7[&r& 9&7] &9Blue       &8| &7[&r& n&7] &nUnderline\n";	
    	message	+= "&7[&r& r&7] &rReset      &8| &7[&r& k&7] &kRANDOME \n";			
    	message += "&6=============================================";
    	
    	SendToSender(Module.System, MessageType.None, sender, message);
    }
    
    public void SendToSender(Module module, MessageType messageType, CommandSender sender, String message)
    {    
    	String outPutMessage = "";
    	Sound sound = null;
    	boolean isSenderPlayer =  sender instanceof Player;
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
    			outPutMessage += "&7[&cRegionSystem&7]";
    			break;
    			
    		default:
    			outPutMessage += "&7[&b"+ module.toString() + "&7]&r";
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
			sound = Sound.BLOCK_NOTE_BLOCK_BASEDRUM;
			break;
			
		case Info:
			outPutMessage +=  _config.Messages.ConsoleIO.Info;
			sound = Sound.BLOCK_NOTE_BLOCK_BELL;
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
			sound = Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE;
			break;
			
		default:
			break; 	      	
    	}
    	
      	// Add prefix to message
      	outPutMessage += message; 
    	
      	if(isSenderPlayer)
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
    	
    	if(isSenderPlayer && sound != null)
    	{
    		_soundSystem.PlaySound((Player)sender, sound, 0);
    	}    	
    }
       
    public void SendToConsole(Module module, MessageType messageType, String message)
    {
    	CommandSender sender = Bukkit.getConsoleSender();    	
    	
    	SendToSender(module, messageType, sender, message);  
    }

    public ChatColor GetColorCode(String colorTag)
    {
    	ChatColor chatColor;
    	
    	switch(colorTag)
    	{
    	case _colorLightBlue:
    		chatColor = ChatColor.AQUA;
    		break;
    		
    	case _colorBlack:
    		chatColor = ChatColor.BLACK;
    		break;
    		
    	case _colorAqua:
    		chatColor = ChatColor.BLUE;
    		break;
    		
    	case _colorBold:
    		chatColor = ChatColor.BOLD;
    		break;
    		
    	case _colorDarkCyan:
    		chatColor = ChatColor.DARK_AQUA;
    		break;
    		
    	case _colorDarkBlue:
    		chatColor = ChatColor.WHITE;
    		break;
    		
    	case _colorGray:
    		chatColor = ChatColor.DARK_GRAY;
    		break;
    		
    	case _colorDarKGreen:
    		chatColor = ChatColor.DARK_GREEN;
    		break;
    		
    	case _colorPurple:
    		chatColor = ChatColor.DARK_PURPLE;
    		break;
    		
    	case _colorDarkRed:
    		chatColor = ChatColor.DARK_RED;
    		break;
    		
    	case _colorGold:
    		chatColor = ChatColor.GOLD;
    		break;
    		
    	case _colorLightGray:
    		chatColor = ChatColor.GRAY;
    		break;
    		
    	case _colorLightGreen:
    		chatColor = ChatColor.GREEN;
    		break;
    		
    	case _colorItalic:
    		chatColor = ChatColor.ITALIC;
    		break;
    		
    	case _colorPink:			
    		chatColor = ChatColor.LIGHT_PURPLE;
    		break;
    		
    	case _colorMagic:			
    		chatColor = ChatColor.MAGIC;
    		break;
    		
    	case _colorLightRed:
    		chatColor = ChatColor.RED;
    		break;
    		
    	case _colorReset:
    		chatColor = ChatColor.RESET;
    		break;
    		
    	case _colorStrikethrough:
    		chatColor = ChatColor.STRIKETHROUGH;
    		break;
    		
    	case _colorUnderline:
    		chatColor = ChatColor.UNDERLINE;
    		break;
    		
    	case _colorWhite:
    		chatColor = ChatColor.WHITE;
    		break;
    		
    	case _colorYellow:
    		chatColor = ChatColor.YELLOW;
    		break;
    		
    	default:
    		SendToConsole(Module.System, MessageType.Error, "Can't resolve Color : " + colorTag);
    		
    		chatColor = ChatColor.WHITE;
    		break;
    	
    	}				
    	
    	return chatColor;
    }
}