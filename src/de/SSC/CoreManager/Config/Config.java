package de.SSC.CoreManager.Config;

public class Config 
{
	private static Config _instance;

	public ChatConfig Chat;
	public PingConfig Ping;
  public MessagesConfig Messages;
  public MySQLConfig MySQL;
  public EconemyConfig Econemy;
  public RankConfig Rank;

  private Config()
  {
	  MySQL = new MySQLConfig();	
	  Messages = new MessagesConfig();
	  Chat = new ChatConfig();
	  Ping = new PingConfig();
	  Econemy = new EconemyConfig();
	  Rank = new RankConfig();
	  
	  LoadAllDefaults();
  }
  
  public static Config Instance() 
  {
	  if(_instance == null)
	  {
		  _instance = new Config();
	  }
	  
	  return _instance;
  }
  
  public void LoadAllDefaults()
  {
	  MySQL.LoadDefaults(); 	
	  Messages.LoadDefaults(); 
	  Chat.LoadDefaults(); 
	  Ping.LoadDefaults();
	  Econemy.LoadDefaults();
	  Rank.LoadDefaults();
  }
}