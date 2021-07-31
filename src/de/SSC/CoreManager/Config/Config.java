package de.SSC.CoreManager.Config;

import de.SSC.CoreManager.Config.ConfigFiles.ChatConfig;
import de.SSC.CoreManager.Config.ConfigFiles.EconemyConfig;
import de.SSC.CoreManager.Config.ConfigFiles.MessagesConfig;
import de.SSC.CoreManager.Config.ConfigFiles.MySQLConfig;
import de.SSC.CoreManager.Config.ConfigFiles.PingConfig;
import de.SSC.CoreManager.Config.ConfigFiles.RankConfig;
import de.SSC.CoreManager.Config.ConfigFiles.SignEditConfig;
import de.SSC.CoreManager.Config.ConfigFiles.WorldsConfig;
import de.SSC.CoreManager.Messages.Logger;
import de.SSC.CoreManager.Messages.MessageType;
import de.SSC.CoreManager.Messages.Module;

public class Config 
{
	private static Config _instance;
	private Logger _logger;

	public ChatConfig Chat;
	public PingConfig Ping;
  public MessagesConfig Messages;
  public MySQLConfig MySQL;
  public EconemyConfig Econemy;
  public RankConfig Rank;
  public WorldsConfig Worlds;
  public SignEditConfig SignEdit;
  


  private Config()
  {
	  _instance = this;
	  
	  MySQL = new MySQLConfig();	
	  Messages = new MessagesConfig();
	  Chat = new ChatConfig();
	  Ping = new PingConfig();
	  Econemy = new EconemyConfig();
	  Rank = new RankConfig();
	  Worlds = new WorldsConfig();
	  SignEdit = new SignEditConfig();
	  
	  LoadAllDefaults();
	  
	  //_logger = Logger.Instance();
	  
	 // _logger.SendToConsole(Module.Logger, MessageType.Online, Messages.ConsoleIO.On);
  }
  
  public static Config Instance() 
  {
	  return _instance == null ?  new Config() : _instance;
  }
  
  public void LoadAllDefaults()
  {
	  MySQL.LoadDefaults(); 	
	  Messages.LoadDefaults(); 
	  Chat.LoadDefaults(); 
	  Ping.LoadDefaults();
	  Econemy.LoadDefaults();
	  Rank.LoadDefaults();
	  Worlds.LoadDefaults();
	  SignEdit.LoadDefaults();
  }
}