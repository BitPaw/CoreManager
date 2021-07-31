package de.SSC.CoreManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DatabaseManager;

public class CoreListener implements Listener
{
	private DatabaseManager _databaseManager; 
    private Config _config;
    private Logger _logger;
	
    public CoreListener()
    {
    	_config = Config.Instance();
    	_logger = Logger.Instance();
    	_databaseManager = DatabaseManager.Instance();
    }
    
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt) 
	{
		Player player = evt.getPlayer();
		String playerName = player.getName();
		boolean doesPlayerExist;
		
		if(_databaseManager == null)
		{
			player.sendMessage(_logger.TransformToColor("&4DataBaseManager is null in CoreListener"));
		}
		else
		{
			 doesPlayerExist = _databaseManager.DoesPlayerExist(player);
			
			 if(!doesPlayerExist)
			 {			 
				 player.sendMessage(_logger.TransformToColor(_config.Messages.Chat.FirstJoin));
				 _logger.WriteInfo("&6New Player detected <" + playerName + ">");
				 _databaseManager.RegisterNewPlayer(player);		 
			 }	
			 else
			 {
				 player.sendMessage(_logger.TransformToColor(_config.Messages.Chat.ReJoin));
				 _logger.WriteInfo("&eOld Player detected <" + playerName + ">");
				 _databaseManager.UpdatePlayerLastSeen(player);
				 _databaseManager.LoadPlayer(player);				 
			 }	
		}			
	}	
}