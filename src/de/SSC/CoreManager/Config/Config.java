package de.SSC.CoreManager.Config;

public class Config 
{
  public char ColorCombineChar = '&';
  public String OPPermission = "&4$&r";
  
  public boolean ShowPlayerCommands = true;


  public boolean ChatColor = true;
  public boolean ConsoleColor = true;



  // MySQL
  public boolean ShowSQLCommands = true;
  public String Hostname = "localhost";
  public String Port = "3306";
  public String Database = "cakecraft";
  public String Username = "root";
  public String Password = "";

  //
  public int PingTabListDelayMs = 10; // x * 50ms = 1 Tick

  // Money
  public char Currency = '€';
  public int StartMoney = 200;

  // Multiverse
  public String WorldsFolder = "Maps";

  public void Load()
  {

  }

  public void Save()
  {

  }

  public void Test()
  {

  }
}