package de.BitFire;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
 
public class Main extends JavaPlugin 
{	 	
	public void onEnable()   
	{	
		Bukkit.getConsoleSender().sendMessage("Hello World"); 
   	}	   
   
	public void onDisable()    
	{
		Bukkit.getConsoleSender().sendMessage("Goodbye World");   
	}
}