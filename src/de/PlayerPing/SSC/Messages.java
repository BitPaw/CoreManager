package de.PlayerPing.SSC;

import net.md_5.bungee.api.ChatColor;

public class Messages 
{
  static String OnEnable = "&7[&2ON&7]";
  static String OnDisable = "&7[&cOFF&7]";
  
  public static void Send(String message)
  {	  
	  String out = ChatColor.translateAlternateColorCodes('&', message);
	  
	  System.out.print(out);
  }
}
