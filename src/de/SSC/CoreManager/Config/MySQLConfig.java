package de.SSC.CoreManager.Config;

public class MySQLConfig implements IConfig
{
	  public String Hostname;
	  public String Port; 
	  public String Database;
	  public String Username;
	  public String Password; 
	
	public void LoadDefaults() 
	{
		Hostname = "localhost";
		Port = "3306"; 
		Database = "coremanager";
		Username = "coremanager";
		Password = "minecraft";		
	}
}
