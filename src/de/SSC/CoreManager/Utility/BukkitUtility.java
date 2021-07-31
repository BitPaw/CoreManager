package de.SSC.CoreManager.Utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.SSC.CoreManager.Systems.Chat.Logger;
import de.SSC.CoreManager.Systems.Chat.MessageType;
import de.SSC.CoreManager.Systems.Chat.Module;
import net.minecraft.server.v1_14_R1.MinecraftServer;


public class BukkitUtility 
{		
  private static BukkitUtility _instance;
  private Server _server;
  private PluginManager _pluginManager;
  private Logger _logger;
  
  public BukkitUtilityBlock BlockUtility;
  public BukkitUtilityWorld WorldUtility;
  public BukkitUtilityPlayer PlayerUtility;
  public BukkitUtilityWeather WeatherUtility;
  public BukkitUtilityItem ItemUtility;
  
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
  
  private final String commandShutdownServer = "stop";
    
  private BukkitUtility()
  {
	  _instance = this;  
	  
	  BlockUtility = new BukkitUtilityBlock();
	  WorldUtility = new BukkitUtilityWorld(_server, _logger);
	  PlayerUtility = new BukkitUtilityPlayer(_server);
	  WeatherUtility = new BukkitUtilityWeather(this);
	  ItemUtility = new BukkitUtilityItem();
	  
	  _server =  Bukkit.getServer();
	  _pluginManager = _server.getPluginManager();
	  _logger = Logger.Instance();	  
	  
	
  }
  
  public static BukkitUtility Instance()
  {
	  return _instance == null ? new BukkitUtility() : _instance;
  }  
    
  public boolean IsServerCracked()
  {
	  return !Bukkit.getOnlineMode();
  }
  

public PluginManager GetPluginManager() 
{
	return	_pluginManager;	
}

public void SendVanillaCommand(String command)
{
	_server.dispatchCommand(Bukkit.getConsoleSender(),command); 
}




public void ShutdownServer() 
{

	SendVanillaCommand(commandShutdownServer);
	
}

public void  SendBroadCastMessage(String message)
{
	_server.broadcastMessage(message);
}

@SuppressWarnings("deprecation")
public void ChangeMotd(String modt)
{
	MinecraftServer.getServer().setMotd(modt);
}

public void GetMotd(CommandSender sender)
{
	_logger.SendToSender(Module.System, MessageType.None, sender, _server.getMotd());
}



@SuppressWarnings("deprecation")
public GameMode GetGameModePerValue(String mode) 
{
	int gameModeValue;
	GameMode gameMode; 
	
	try	
	{
		gameModeValue = Integer.parseInt(mode);		
		
		gameMode = GameMode.getByValue(gameModeValue);	
	}
	catch(NumberFormatException e)
	{
		gameMode = null;
	}	
	
	return gameMode;
}



public void PrintColors(CommandSender sender)
{
	boolean isSenderConsole = !PlayerUtility.IsSenderPlayer(sender);
	String message = "";
	
	if(isSenderConsole)
	{
		message += "\n";
	}
	
	message += "&e===[&eColors&6]==========\n";	
	message += "&7[& \b0&7]&0 Black\n";	
	message += "&7[& \b1&7]&1 Dark-Blue\n";	
	message += "&7[& \b2&7]&2 Dark-Green\n";	
	message += "&7[& \b3&7]&3 Dark-Cyan\n";	
	message += "&7[& \b4&7]&4 Dark-Red\n";	
	message += "&7[& \b5&7]&5 Purple\n";	
	message += "&7[& \b6&7]&6 Gold\n";	
	message += "&7[& \b7&7]&7 Light-Gray\n";	
	message += "&7[& \b8&7]&8 Gray\n";	
	message += "&7[& \b9&7]&9 Blue\n";		
	message += "&6=====================";
	
	_logger.SendToSender(Module.System, MessageType.None, sender, message);
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
		_logger.SendToConsole(Module.System, MessageType.Error, "Can't resolve Color : " + colorTag);
		
		chatColor = ChatColor.WHITE;
		break;
	
	}				
	
	return chatColor;
}

public void ReloadPlugin(JavaPlugin plugin) 
{	
	_logger.SendToConsole(Module.System, MessageType.Info, "&cDisabling &7Plugin! &3"+ plugin.getName());
	
	_pluginManager.disablePlugin(plugin);
	
	_logger.SendToConsole(Module.System, MessageType.Info, "&aRe&2-&aEnabling &7Plugin! &3" + plugin.getName());
	
	_pluginManager.enablePlugin(plugin);	
}




	


}
