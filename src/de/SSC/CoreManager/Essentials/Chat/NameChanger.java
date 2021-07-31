package de.SSC.CoreManager.Essentials.Chat;

import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Logger;
import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DatabaseManager;

public class NameChanger 
{	
	private DatabaseManager _databaseManager;
	private Config _config;
	private Logger _logger;
	
	public NameChanger()
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
				SetPlayerName(senderplayer, args[0]);
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

	
	
	  public void SetPlayerName(Player player, String name)
	  {	
		  
	    player.setCustomName(_logger.TransformToColor(name));
 
	    
	    _databaseManager.UpdateCustomName(player);
	  }
}
