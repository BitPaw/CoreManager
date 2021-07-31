package de.SSC.CoreManager.Color;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import de.SSC.CoreManager.Messages;

public class Logger
{
    static boolean DebugMode = true;
    static boolean ColorLogs = true;
 
    public static void Write(String message) 
    {
    	message = ChatColor.translateAlternateColorCodes('&', Messages.CoreManager + "&r " + message);
        
        if (!ColorLogs) 
        {
        	message = ChatColor.stripColor(message);
        }
        
        Bukkit.getConsoleSender().sendMessage(message);
    }
    
    public static void WriteWarning(String message)
    {
    	Write(Messages.Error + message);
    }
    
    public static void WriteInfo(String message)
    {
    	Write(Messages.Info + message);
    }
    
    public static String TransformToColor(String message)
    {
    	return ChatColor.translateAlternateColorCodes('&', message);
    }
    
    public static void DataBaseWrite(String message)
    {
       message = ChatColor.translateAlternateColorCodes('&', Messages.DataBase + "&r " + message);
        
        
        Bukkit.getConsoleSender().sendMessage(message);
    }
    
    public static void DataBaseInfo(String message)
    {
    	DataBaseWrite("&7[&ei&7]&7 " + message);
    }
    
}
 