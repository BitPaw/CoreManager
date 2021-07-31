///////////////////////////////////////////////////////////////////////////////////////////////////
//-----------------------------------------------------------------------------------------------//
 package de.SSC.CoreManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
//-----------------------------------------------------------------------------------------------//	
 import org.bukkit.plugin.java.JavaPlugin;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DataTypes.CMRankList;
import de.SSC.CoreManager.Essentials.Chat.ChatManager;
import de.SSC.CoreManager.Essentials.Chat.NameChanger;
import de.SSC.CoreManager.SignEdit.SignEdit;
import de.SSC.CoreManager.Tab.PingTabList;
import de.SSC.ExtraUtility.DoubleJump.DoubleJump;
//-----------------------------------------------------------------------------------------------//	
 public class Main extends JavaPlugin 
 {
   // --- Variables ---
   private Main _instance = null;
   private PluginManager pluginManager = null;	
   private Config _config;
   private Logger _logger;
   
   boolean _isDoubleJumpActive = false; 
   
   // --- Objects ---
   private CoreListener _coreListener = null;
   private SignEdit signEdit = null;
   private ChatManager chatManager = null;
   private NameChanger nameChanger = null;
   private SystemInfo systemInfo = null;
    
   
   // Extra Objects
   private DoubleJump doubleJump = null;
  
   // --- Constructor ---
   public Main()
   {
	   _logger = Logger.Instance();
	   _config = Config.Instance();
	   BukkitUtility.SetPluginInstance(this); 	   
	   CMRankList.Instance();	  
	   
	 
	   
	   if(_instance == null)
	   {
		   _instance = this;
	   }
	   else
	   {
		   _logger.Write(_config.Messages.ConsoleIO.Error + "This plugin als already running!");
		   return;
	   }
	   
	  // Implement
	   {
		   signEdit = new SignEdit();	
		     
		   chatManager = new ChatManager();
		  
		   if(_isDoubleJumpActive)  
		   {
			   doubleJump = new DoubleJump();
		   }
		   systemInfo = new SystemInfo();
	     
		   _coreListener = new CoreListener();
		   nameChanger = new NameChanger();
		   
		   pluginManager = Bukkit.getServer().getPluginManager();		
	   }    
   }

   // --- Functions ---
   public void onEnable()
   {	
	   try
	   {
		   pluginManager.registerEvents(_coreListener, this);
		   pluginManager.registerEvents(signEdit, this); 
		   pluginManager.registerEvents(chatManager, this);  
		   
		   if(_isDoubleJumpActive)
		   {
			   pluginManager.registerEvents(doubleJump, this);
		   }
		 
		   PingTabList pingTabList = PingTabList.Instance();  
		   pingTabList.runTaskTimerAsynchronously(this, 0, _config.Ping.PingTabListDelayMs);	  
	   }
	   catch(Exception e)
	   {
		   _logger.Write(_config.Messages.ConsoleIO.Error + _config.Messages.ConsoleIO.Shutdown); 
	   }
	   
	   systemInfo.GetSystemInfos();
	   
	   

	   CMRankList rankList =  CMRankList.Instance();
	   rankList.ListAllRanks(Bukkit.getConsoleSender());
	   
	   _logger.Write(_config.Messages.ConsoleIO.On); 
   }



 

   
   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
   {
	   boolean successful = false;
	   
	   if(successful == false)
		{
		   successful = WeatherController.Check(args);
		}

	   if(successful == false)
		{
		   successful = nameChanger.CheckCommand(sender, args);
	}

	   return successful;
   }
	
   
   public void onDisable()
   {
	   _logger.Write(_config.Messages.ConsoleIO.Off);
   }
 }
//-----------------------------------------------------------------------------------------------//
///////////////////////////////////////////////////////////////////////////////////////////////////