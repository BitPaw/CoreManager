package de.SSC.BukkitAPI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.DataBase.DataBaseSystem;
import de.SSC.CoreManager.Player.CMPlayer;
import de.SSC.CoreManager.Player.PlayerSystem;
import de.SSC.CoreManager.Player.Exception.InvalidPlayerNameException;
import de.SSC.CoreManager.Player.Exception.InvalidPlayerUUID;
import de.SSC.CoreManager.Player.Exception.PlayerNotFoundException;
import de.SSC.CoreManager.System.Exception.NotForConsoleException;
import de.SSC.CoreManager.System.Exception.TooManyParameterException;
import net.minecraft.server.v1_15_R1.EntityPlayer;

public class BukkitAPIPlayer 
{
	private Server _server;

	 private PlayerSystem _playerSystem;
	  private DataBaseSystem _dataBaseSystem;
	
	public BukkitAPIPlayer()
	{		
		_server = Bukkit.getServer();
		
		_playerSystem = PlayerSystem.Instance();
		_dataBaseSystem = DataBaseSystem.Instance();	  			
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
	  
	  public void SetPlayerAsOperator(CommandSender sender, String[] args) throws PlayerNotFoundException, InvalidPlayerUUID
	  {
		  boolean isSenderPlayer = sender instanceof Player;
		  
		  if(isSenderPlayer)
		  {
				
			  Player player = (Player)sender;
			  UUID playerUUID = player.getUniqueId();
			  CMPlayer cmPlayer = _playerSystem.GetPlayer(playerUUID);				  
			  
			  _dataBaseSystem.Player.ChangePlayerOperatorStatus(cmPlayer, true);	
		  }			
	  }
	  
	  public void RevertPlayerAsOperator(CommandSender sender, String[] args) throws PlayerNotFoundException, InvalidPlayerUUID
	  {
		  boolean isSenderPlayer = sender instanceof Player;
		  
		  if(isSenderPlayer)
		  {
			  

			  
				Player player = (Player)sender;
				UUID playerUUID = player.getUniqueId();
				CMPlayer cmPlayer = _playerSystem.GetPlayer(playerUUID);
		  
				_dataBaseSystem.Player.ChangePlayerOperatorStatus(cmPlayer, false);
		  }
				  
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

	@SuppressWarnings("deprecation")
	public void RefreshAllSkinsForPlayer(Player player) 
	{				
	    for(Player pl : Bukkit.getOnlinePlayers())
       {	            	
           pl.hidePlayer(player);
           pl.showPlayer(player);            
       } 		
	}

	public void GetPlayerInfo(CommandSender sender, String[] parameter) throws 	PlayerNotFoundException, 
																				InvalidPlayerUUID, 
																				NotForConsoleException, 
																				TooManyParameterException, 
																				InvalidPlayerNameException 
	{
		final boolean isSenderPlayer = sender instanceof Player;
		final int parameterLengh = parameter.length;
		
		switch(parameterLengh)
		{
		case 0:
			if(isSenderPlayer)
			{
				final Player player = (Player)sender;
				final UUID playerUUID = player.getUniqueId();
				final CMPlayer cmplayer = _playerSystem.GetPlayer(playerUUID);
				cmplayer.GetPlayerInfo(sender);
			}
			else
			{
				throw new NotForConsoleException();
			}		
			break;
			
		case 1:
			final String targetedPlayerName = parameter[0];			
			final CMPlayer cmPlayer = _playerSystem.GetPlayer(targetedPlayerName);
			
			cmPlayer.GetPlayerInfo(sender);
			
			break;
			
		default:
			throw new TooManyParameterException();			
			
		}		
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
			gameMode = null;
		}	
		
		return gameMode;
	}
}
