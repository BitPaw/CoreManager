package de.SSC.CoreManager.Utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_13_R2.EntityPlayer;

public class BukkitUtility 
{		
  private static BukkitUtility _instance;
  private static JavaPlugin _pluginInstance;
  
  private final String commandRain = "toggledownfall";
  private final String commandDay = "time set 0";
  private final String commandNight = "time set 10000";
  private final String commandShutdownServer = "stop";
    
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
  
  public String GetPlayerIP(Player player)
  {
	 return player.getAddress().getHostName();
  }
  
  public UUID GetOfflinePlayerUUID(String playerName)
  {
	   @SuppressWarnings("deprecation")
	OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
	      
	  return offlinePlayer.getUniqueId();
  } 
  
  public boolean IsSenderPlayer(CommandSender sender)
  {
	  boolean isPlayer;	  
	  
	  isPlayer = Bukkit.getConsoleSender() == sender ? false : true;
	  
	  return isPlayer;
  }
  
  public static void SetPluginInstance(JavaPlugin plugin)
  {
	  _pluginInstance = plugin;
  }
  
  public boolean IsServerCracked()
  {
	  return !Bukkit.getOnlineMode();
  }
  
  public int GetPlayerPing(Player player) 
  {		  
	  CraftPlayer craftPlayer = (CraftPlayer) player; 		  
	  EntityPlayer entityPlayer = craftPlayer.getHandle(); 
	  
	  int ping = entityPlayer.ping;
	  
	  return ping;
  }
  
  public List<Player> GetAllOnlinePlayers()
  {
	  Collection<? extends Player> collectionPlayers = _pluginInstance.getServer().getOnlinePlayers();
	  List<Player> players	= new ArrayList<Player>();  
	  
	  for(Player player : collectionPlayers)
	  {
		  players.add(player);
	  }
	  
	  return players;
  }

public PluginManager GetPluginManager() 
{
	return Bukkit.getServer().getPluginManager();		
}


public void SendVanillaCommand(String command)
{

		   Server server = Bukkit.getServer();
		   
		   server.dispatchCommand(Bukkit.getConsoleSender(),command); 

}


public void Rain()
{
	SendVanillaCommand(commandRain);
}

public void Day()
{
	SendVanillaCommand(commandDay);
}

public void Night() 
{
	SendVanillaCommand(commandNight);	
}


public void ShutdownServer() 
{

	SendVanillaCommand(commandShutdownServer);
	
}

@SuppressWarnings("deprecation")
public GameMode GetGameModePerValue(String mode) 
{
	int gameModeValue;
	GameMode gameMode; 
	
	try	
	{
		gameModeValue = Integer.parseInt(mode);		
		
		gameMode = GameMode.getByValue(gameModeValue);	
	}
	catch(NumberFormatException e)
	{
		gameMode = GameMode.SURVIVAL;
	}	
	
	return gameMode;
}

}
