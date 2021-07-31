package de.SSC.CoreManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


import de.SSC.MySQL.DataBaseUtility.DatabaseManager;

public class CoreListener implements Listener
{
	private DatabaseManager _databaseManager = null; 

	public CoreListener(DatabaseManager databaseManager)
	{
		_databaseManager = databaseManager;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt) 
	{
		de.SSC.CoreManager.Color.Logger.WriteInfo("Player joined");		
	
		boolean nullPointer = false;
		
		if(evt == null)
		{
			nullPointer = true;	
		}
		else
		{
			Player player = evt.getPlayer();
			
			if(player == null)
			{
				nullPointer = true;
			}
			else
			{
				_databaseManager.RegisterNewPlayer(player);
			}
		}
		
		if(nullPointer)
		{
			de.SSC.CoreManager.Color.Logger.WriteWarning("Null pointer in class <CoreListener> in funktion <onPlayerJoin>");
		}		
	}
	
}