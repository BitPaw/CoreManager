package de.SSC.CoreManager.Utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Systems.DataBase.DataBaseSystem;
import de.SSC.CoreManager.Systems.Player.CMPlayer;
import de.SSC.CoreManager.Systems.Player.PlayerSystem;
import net.minecraft.server.v1_14_R1.EntityPlayer;

public class BukkitUtilityPlayer 
{
	private Server _server;

	 private PlayerSystem _playerSystem;
	  private DataBaseSystem _dataBaseSystem;
	
	public BukkitUtilityPlayer(Server server)
	{
		
		
		
		_server = server;
			
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
	  
	  public void SetPlayerAsOperator(CommandSender sender, String[] args)
	  {
		  boolean isSenderPlayer = IsSenderPlayer(sender);
		  
		  if(isSenderPlayer)
		  {
				_playerSystem = PlayerSystem.Instance();
				_dataBaseSystem = DataBaseSystem.Instance();
			  
			  Player player = (Player)sender;
			  CMPlayer cmPlayer = _playerSystem.GetPlayer(player);				  
			  
			  _dataBaseSystem.ChangePlayerOperatorStatus(cmPlayer, true);	
		  }			
	  }
	  
	  public void RevertPlayerAsOperator(CommandSender sender, String[] args)
	  {
  boolean isSenderPlayer = IsSenderPlayer(sender);
		  
		  if(isSenderPlayer)
		  {
		  
				_playerSystem = PlayerSystem.Instance();
				_dataBaseSystem = DataBaseSystem.Instance();
			  
		  Player player = (Player)sender;
		  CMPlayer cmPlayer = _playerSystem.GetPlayer(player);
		  
		  _dataBaseSystem.ChangePlayerOperatorStatus(cmPlayer, false);
		  }
				  
	  }
	  
	  public boolean IsSenderPlayer(CommandSender sender)
	  {
		  boolean isPlayer;	  
		  
		  isPlayer = Bukkit.getConsoleSender() == sender ? false : true;
		  
		  return isPlayer;
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
		  if(_server == null)
		  {
			  _server = Bukkit.getServer();
		  }
		  
		  Collection<? extends Player> collectionPlayers = _server.getOnlinePlayers();
		  List<Player> players	= new ArrayList<Player>();  
		  
		  for(Player player : collectionPlayers)
		  {
			  players.add(player);
		  }
		  
		  return players;
	  }
	  
	  public boolean IsPlayerInAir(Player player) 
	  {	
	  	return  player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR;
	  }
	  
	  public String GetPlayerCustomName(Player player) 
	  {
	  	String playerName = player.getName();
	  	String displayName = player.getDisplayName();
	  		
	  	return displayName == null ? playerName : displayName;
	  }
	  
	  @SuppressWarnings("deprecation")
	  public void PlayEffectOnPlayer(Player player, Effect effect,  int power)
	  {
	  	 player.playEffect(player.getLocation(), effect,power);
	  }

	@SuppressWarnings("deprecation")
	public OfflinePlayer GetOfflinePlayer(String target) 
	{
		return _server.getOfflinePlayer(target);
	}

	public OfflinePlayer GetOfflinePlayer(UUID uuid) 
	{
		return _server.getOfflinePlayer(uuid);
	}
}
