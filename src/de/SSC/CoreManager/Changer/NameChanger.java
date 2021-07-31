package de.SSC.CoreManager.Changer;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DataTypes.CMPlayer;
import de.SSC.CoreManager.DataBase.DataTypes.CMPlayerList;
import de.SSC.CoreManager.Messages.Logger;
import de.SSC.CoreManager.Messages.MessageType;
import de.SSC.CoreManager.Messages.Module;
import de.SSC.CoreManager.Utility.BukkitUtility;
import de.SSC.CoreManager.Utility.MessageTags;

public class NameChanger 
{	
	private static NameChanger _instance;
	private Config _config;
	private Logger _logger;
	private BukkitUtility _bukkitUtility;
	private CMPlayerList _cmPlayerList;
	private MessageTags _messageTags;
	
	private NameChanger()
	{
		_instance = this;
		
		_config = Config.Instance();
		_logger = Logger.Instance();
		_bukkitUtility = BukkitUtility.Instance();
		_cmPlayerList = CMPlayerList.Instance();
		_messageTags = MessageTags.Instance();
	}
	
	public static NameChanger Instance() 
	{
		return _instance ==  null ? new NameChanger() : _instance;
	}
	
	public void ChangeNameCommand(CommandSender sender, String[] parameter)
	{	
		int parameterLengh = parameter.length;
		boolean isSenderPlayer = _bukkitUtility.IsSenderPlayer(sender);;
		Player player;			
		CMPlayer cmPlayer;
		String message = "";
		String targetedName;
		switch(parameterLengh)
		{
		case 0 :
			if(isSenderPlayer)
			{
				player = (Player)sender;
				
				cmPlayer = _cmPlayerList.GetPlayer(player);
				
				cmPlayer.SetCustomName(null);
				
				_logger.SendToSender(Module.NameChanger, MessageType.Info, sender, "Your name has been resetted!");
			}
			else
			{		
				_logger.SendToSender(Module.NameChanger, MessageType.Warning, sender, _config.Messages.ConsoleIO.NotForConsole);
			}
			break;
			
		case 1 :
			targetedName = parameter[0];
			
			if(isSenderPlayer)
			{			
				player = (Player)sender;
				
				cmPlayer = _cmPlayerList.GetPlayer(player);
				
				cmPlayer.SetCustomName(targetedName);				
				
				message = _config.Messages.Chat.NameChanged;
				
				message = _messageTags.ReplaceNameTag(message, targetedName);
				
				_logger.SendToSender(Module.NameChanger, MessageType.Warning, sender, message);
			}
			else
			{
				_logger.SendToSender(Module.NameChanger, MessageType.Warning, sender, message);
			}
			break;
			
		case 2 :
			targetedName = parameter[0];
			
			if(isSenderPlayer)
			{				
				message = _config.Messages.Chat.NameChanged +  targetedName;
				
				message = _messageTags.ReplaceNameTag(message, targetedName);
						
				_logger.SendToSender(Module.NameChanger, MessageType.Warning, sender, message);
			}
			else
			{
				_logger.SendToSender(Module.NameChanger, MessageType.Warning, sender, message);
			}
			break;
			
			
		default:				
				_logger.SendToSender(Module.NameChanger, MessageType.Warning, sender, _config.Messages.Chat.NameChangesWrongCommand);
				
				break;
		}
	}

}
