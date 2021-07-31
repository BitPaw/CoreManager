///////////////////////////////////////////////////////////////////////////////////////////////////
//-----------------------------------------------------------------------------------------------//
 package de.SSC.CoreManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
//-----------------------------------------------------------------------------------------------//	
 import org.bukkit.plugin.java.JavaPlugin;

import de.SSC.CoreManager.Color.Logger;
import de.SSC.CoreManager.Essentials.Chat.ChatManager;
import de.SSC.CoreManager.Essentials.Chat.NameChanger;
import de.SSC.CoreManager.SignEdit.SignEdit;
import de.SSC.CoreManager.Tab.PingTabList;
import de.SSC.ExtraUtility.DoubleJump.DoubleJump;
import de.SSC.MySQL.DatabaseManager;
//-----------------------------------------------------------------------------------------------//	
 public class Main extends JavaPlugin 
 {
   // --- Variables ---
   private Main instance = null;
   private PluginManager pluginManager = null;	
   
   boolean _isDoubleJumpActive = false; 
   
   // --- Objects ---
   private SignEdit signEdit = null;
   private PingTabList pingTabList = null;
   private ChatManager chatManager = null;
   private NameChanger nameChanger = null;
   private SystemInfo systemInfo = null;
   private DatabaseManager databaseManager = null;
   
   // Extra Objects
   private DoubleJump doubleJump = null;
   
   int PingTabListDelayMs = 10; // x * 50ms = 1 Tick
  

   // --- Constructor ---
   public Main()
   {
	   if(instance == null)
	   {
		   instance = this;
	   }
	   else
	   {
		   Logger.Write(Messages.Error + "This plugin als already running!");
		   return;
	   }
	   
	   signEdit = new SignEdit();	
     pingTabList = new PingTabList(this);     
     chatManager = new ChatManager();
     nameChanger = new NameChanger();
     if(_isDoubleJumpActive) doubleJump = new DoubleJump();
     systemInfo = new SystemInfo();
   
     databaseManager = new DatabaseManager();
     
     pluginManager = Bukkit.getServer().getPluginManager();
    
   
   }

   // --- Functions ---
   public void onEnable()
   {	
	   try
	   {
		   pluginManager.registerEvents(signEdit, this); 
		   pluginManager.registerEvents(chatManager, this);  
		   if(_isDoubleJumpActive) pluginManager.registerEvents(doubleJump, this);
		 
		   pingTabList.runTaskTimerAsynchronously(this, 0, PingTabListDelayMs);	  
	   }
	   catch(Exception e)
	   {
		   Logger.Write(Messages.Error + Messages.Shutdown); 
	   }
	   
	   databaseManager.Write("create table if not exists Player(Name varchar(20),ChangedName varchar(40));");
	   systemInfo.GetSystemInfos();
       Logger.Write(Messages.On); 
   }

   
   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
 {
	   boolean successful = false;
	   
	   
	   
	   if(successful == false) successful = WeatherController.Check(args);


	   if(successful == false) successful = nameChanger.CheckCommand(sender, args);
	   
	   return successful;
	  }
	
   
   public void onDisable()
   {
     Logger.Write(Messages.Off);
   }
 }
//-----------------------------------------------------------------------------------------------//
///////////////////////////////////////////////////////////////////////////////////////////////////