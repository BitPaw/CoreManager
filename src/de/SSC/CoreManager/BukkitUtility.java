package de.SSC.CoreManager;

import java.util.Collection;

import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_13_R2.EntityPlayer;

public class BukkitUtility 
{	
  private static BukkitUtility _instance;
  private static JavaPlugin _pluginInstance;
  
  private BukkitUtility()
  {
	  
  }
  
  public static BukkitUtility Instance()
  {
	  if(_instance == null)
	  {
		  _instance = new BukkitUtility();
	  }
	  
	  return _instance;
  }
  
  public static void SetPluginInstance(JavaPlugin plugin)
  {
	  _pluginInstance = plugin;
  }
  
  public boolean IsServerCracked()
  {
	  return true;
  }
  
  public int GetPlayerPing(Player player) 
  {		  
	  CraftPlayer craftPlayer = (CraftPlayer) player; 		  
	  EntityPlayer entityPlayer = craftPlayer.getHandle(); 
	  
	  int ping = entityPlayer.ping;
	  
	  return ping;
  }
  
  public Collection<? extends Player> GetAllOnlinePlayers()
  {
	  return _pluginInstance.getServer().getOnlinePlayers();
  }
}
