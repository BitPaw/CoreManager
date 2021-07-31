package de.SSC.UnlimitedSlots;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class UnlimitedSlotsSystem 
{
	private static UnlimitedSlotsSystem _instance;
	private Server _server;
	public int MaxPlayer;
	  
	private UnlimitedSlotsSystem()
	{
		_server = Bukkit.getServer();
		MaxPlayer = 420;
	}	  
	
	public static UnlimitedSlotsSystem Instance()
	{
		return _instance == null ? new UnlimitedSlotsSystem() : _instance;
	}
	
	public void OnServerListPing(ServerListPingEvent event)
	{
	    event.setMaxPlayers(MaxPlayer);
	}	  
	 
	public void OnPlayerLogin(PlayerLoginEvent event)
	{
		int amountOfPlayers = _server.getOnlinePlayers().size();
		boolean eventResultIsAKick = event.getResult().equals(PlayerLoginEvent.Result.KICK_FULL);
		  
	    if (eventResultIsAKick) 
	    {
	    	if(MaxPlayer >= amountOfPlayers)
	    	{
	    		event.allow();
	    	}	    	
	    }
	}
}
