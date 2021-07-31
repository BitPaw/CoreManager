package de.SSC.CoreManager.Changer;

import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DatabaseManager;
import de.SSC.CoreManager.Messages.Logger;

public class NameChanger 
{	
	private static NameChanger _instance;
	private DatabaseManager _databaseManager;
	private Config _config;
	private Logger _logger;
	
	private NameChanger()
	{
		_config = Config.Instance();
		_logger = Logger.Instance();
		_databaseManager = DatabaseManager.Instance();
	}
	
	public boolean CheckCommand(CommandSender sender, String[] args)
	{	
		if (!(sender instanceof CraftPlayer))
		{
			sender.sendMessage(_config.Messages.ConsoleIO.Error + _config.Messages.ConsoleIO.NotForConsole);
			return false;
		}

		if (sender.hasPermission(_config.Messages.Chat.PermissionChangeName))
		{			
			Player senderplayer = (Player)sender;

			if (args.length > 0)
			{
				String out = _config.Messages.ConsoleIO.Info + _config.Messages.Chat.NameChanged +  args[0];

				sender.sendMessage(_logger.TransformToColor(out));
				senderplayer.setDisplayName(args[0]);
				SetPlayerName(sender, args);
			}
			else
			{		 
				String out = _config.Messages.ConsoleIO.Error + _config.Messages.Chat.NameChangesWrongCommand;

				sender.sendMessage(_logger.TransformToColor(out));
			}

			return true;
		}
		sender.sendMessage("Nope, can't do that!");
		return false;
	}

	
	
	  public void SetPlayerName(CommandSender sender, String[] args)
	  {	
		  Player player = (Player)sender;
		  
	    player.setCustomName(_logger.TransformToColor(args[1]));
 
	    
	    _databaseManager.UpdateCustomName(player);
	  }

	public static NameChanger Instance() 
	{
		if(_instance == null)
		{
			_instance = new NameChanger();
		}
		
		return _instance;
	}
}
