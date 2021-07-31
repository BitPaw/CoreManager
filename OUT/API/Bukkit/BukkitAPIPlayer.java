package de.BitFire.API.Bukkit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.BitFire.Player.Exception.PlayerDirectionAmbiguous;

public final class BukkitAPIPlayer 
{	
	private BukkitAPIPlayer()
	{		
		
	}	

	private static final int GetDirectionNumber(final Player player)
	{
		float yaw = player.getLocation().getYaw();
		int direction;
		
	    if(yaw < 0)
	    {
	    	yaw += 360;
	    }    
	    
	    yaw %= 360;        
        
	    direction = (int)((yaw + 8) / 22.5);    
	    
	    return direction;
	}
	
	public static final BlockFace GetNarrowDirectionFromPlayer(final Player player) throws PlayerDirectionAmbiguous
	{
		final int direction = GetDirectionNumber(player);
        
        switch(direction)
        {
        
        // North
        case 3: // NORTH
        case 4: // NORTH_NORTH_WEST
        case 5: // NORTH_NORTH_EAST
        	return BlockFace.NORTH;            
        
        // East
        case 7: // EAST_NORTH_EAST
        case 8: // EAST
        case 9: // EAST_SOUTH_EAST
        	return BlockFace.EAST;  
        
        // South
        case 11: // SOUTH_SOUTH_EAST
        case 12: // SOUTH
        case 13: // SOUTH_SOUTH_WEST
        	return BlockFace.SOUTH;  
        
        // West
        case 0: // WEST
        case 1: // WEST_NORTH_WEST;  
        case 15: // WEST_SOUTH_WEST
        	return BlockFace.WEST;           
        	
        // undefined
        case 2: // NORTH_WEST
        case 6: // NORTH_EAST
        case 10: // SOUTH_EAST
        case 14: // SOUTH_WEST
        default:
        	throw new PlayerDirectionAmbiguous();
     	
        }
	}
	
	public static final BlockFace GetDirectionFromPlayer(final Player player)
	{
		final int direction = GetDirectionNumber(player);      
        
        switch(direction)
        {
        case 0: return BlockFace.WEST;           	
        case 1: return BlockFace.WEST_NORTH_WEST;   
        case 2: return BlockFace.NORTH_WEST;        	
        case 3: return BlockFace.NORTH;        	
        case 4: return BlockFace.NORTH_NORTH_WEST;        	
        case 5: return BlockFace.NORTH_NORTH_EAST;        	
        case 6: return BlockFace.NORTH_EAST;        	
        case 7: return BlockFace.EAST_NORTH_EAST;       
        case 8: return BlockFace.EAST;       
        case 9: return BlockFace.EAST_SOUTH_EAST;       
        case 10: return BlockFace.SOUTH_EAST;       
        case 11: return BlockFace.SOUTH_SOUTH_EAST;       
        case 12: return BlockFace.SOUTH;                   	
        case 13: return BlockFace.SOUTH_SOUTH_WEST;        	
        case 14: return BlockFace.SOUTH_WEST;        	
        case 15: return BlockFace.WEST_SOUTH_WEST;           	
        default: return  BlockFace.WEST;  
        }
	}
	
	public static final float GetYawFromDirection(final BlockFace direction)
	{		
		switch(direction)
		{

		case EAST:
			return -90;

		case NORTH:
			return 180;

		case SOUTH:
			return 0;
			
		case WEST:
			return 90;
			
		default:
			return 0;
		}	
	}
	
	public static final Location GetBlockInfrontOfPlayer(final Player player)
	{		
		final Location playerLocation = player.getLocation();
		final Vector direction = playerLocation.getDirection().normalize();
		final Block playerStandingInBlock = playerLocation.getBlock();
		final Location blockLocation = playerStandingInBlock.getLocation();
		
		final Location targetedBlockLocation = blockLocation;
		
		targetedBlockLocation.add(direction);
		targetedBlockLocation.setY(targetedBlockLocation.getY() +1);		
		
		return targetedBlockLocation;
	}
	
	@SuppressWarnings("deprecation")
	public final static OfflinePlayer GetOfflinePlayer(String target) 
	{
		final Server server = Bukkit.getServer();
		
		return server.getOfflinePlayer(target);
	}

	public final static OfflinePlayer GetOfflinePlayer(UUID uuid) 
	{
		final Server server = Bukkit.getServer();
		
		return server.getOfflinePlayer(uuid);
	}
		  
	public final static UUID GetOfflinePlayerUUID(String playerName)
	{
		@SuppressWarnings("deprecation")
		final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
		   		      
		return offlinePlayer.getUniqueId();
	} 	 	  
	  
	public final static List<Player> GetAllOnlinePlayers()
	{
		final Server server = Bukkit.getServer();		  
		final Collection<? extends Player> collectionPlayers = server.getOnlinePlayers();
		final List<Player> players = new ArrayList<Player>(collectionPlayers);  
		  		  
		return players;
	}
	 	  
	@SuppressWarnings("deprecation")
	public final static void PlayEffectOnPlayer(Player player, Effect effect,  int power)
	{
		player.playEffect(player.getLocation(), effect,power);
	}

	@SuppressWarnings("deprecation")
	public final static void RefreshAllSkinsForPlayer(Player player) 
	{				
	    for(Player pl : Bukkit.getOnlinePlayers())
       {	            	
           pl.hidePlayer(player);
           pl.showPlayer(player);            
       } 		
	}
	
	@SuppressWarnings("deprecation")
	public final static GameMode GetGameModePerValue(String mode) 
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