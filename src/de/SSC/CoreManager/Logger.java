package de.SSC.CoreManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import de.SSC.CoreManager.Config.Config;

public class Logger
{
    private static Logger _instance;
	private Config _config;
    
    public Logger()
    {
    	_config = Config.Instance();
    }
    
    public static Logger Instance()
    {
    	if(_instance == null)
    	{
    		_instance = new Logger();
    	}
    	
    	return _instance;
    }
    
    public String TransformToColor(String message)
    {    	
    	return ChatColor.translateAlternateColorCodes(_config.Chat.ColorCombineChar, message);
    }
    
    private void SendMessage(String message)
    {
    	if(_config.Chat.ColorLogs)
    	{
    		message = TransformToColor(message);
    	}
    	else
    	{
    		String colorValues[] = 
    			{
    					"&0",
    					"&1",
    					"&2",
    					"&3",
    					"&4",
    					"&5",
    					"&6",
    					"&7",
    					"&8",
    					"&9",

    					"&a",
    					"&b",
    					"&c",
    					"&d",
    					"&e",
    					"&f",

    					"&o",
    					"&l",
    					"&k",
    					"&r"
    			};
    		
    		for(String color : colorValues)
    		{
    			message.replaceAll(color, "");
    		}	    	
    	}
    	
    	Bukkit.getConsoleSender().sendMessage(message);
    }
    
    // Normal
    public void Write(String message) 
    {
    	message = _config.Messages.ConsoleIO.CoreManager + "&r " + message;
        
    	SendMessage(message);
    }
    
    public void WriteInfo(String message)
    {
    	Write(_config.Messages.ConsoleIO.Info + message);
    }
    
    public void WriteWarning(String message)
    {
    	Write(_config.Messages.ConsoleIO.Error + message);
    }
    
 
    // SQL    
    public void DataBaseWrite(String message)
    {
       message = _config.Messages.ConsoleIO.DataBase + "&r " + message;        
        
       SendMessage(message);
    }
    
    public void DataBaseCommand(String message)
    {
    	DataBaseWrite(_config.Messages.ConsoleIO.Command + message);
    }
    
    public void DataBaseInfo(String message)
    {
    	DataBaseWrite(_config.Messages.ConsoleIO.Info + message);
    }
    
    public void DataBaseError(String message)
    {
    	DataBaseWrite(_config.Messages.ConsoleIO.Error + message);
    }    
}