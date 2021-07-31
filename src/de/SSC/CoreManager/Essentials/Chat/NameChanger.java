package de.SSC.CoreManager.Essentials.Chat;

import de.SSC.CoreManager.Config.Messages;
import de.SSC.CoreManager.CoreController;
import de.SSC.CoreManager.Main;
import de.SSC.CoreManagerPlugins.BukkitHook;
import de.SSC.CoreManagerPlugins.DataBaseController;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NameChanger
{	
	private BukkitHook _bukkitHook ;
	private DataBaseController _dataBaseController;

	public NameChanger()
	{
		CoreController coreController = CoreController.GetInstance();

		_bukkitHook = coreController._BukkitHook;
		_dataBaseController = coreController._DataBaseController;
	}	

	public void SetPlayerName(CommandSender sender, String[] parameter)
	{			  
		if(_bukkitHook.CheckIfCommandSenderIsPlayer(sender))
		{
			Messages messages = Main.messages;
			Player player = (Player)sender;		
			String name = parameter[0];
			String customName = "";


			if(name.isEmpty() || name.equalsIgnoreCase(player.getName()))
			{
				customName = player.getName();
			}
			else
			{
				customName = _bukkitHook.TransformColor(name);
			}	
			
			player.setCustomName(customName);
			_bukkitHook.SendMessage(player, messages.Info + "Name changed to " + customName);
			_dataBaseController.UpdateCustomName(player);
		}		
	}

	public void LoadPlayerName(Player player, DataBaseController dataBaseController)
	{
		// Defaultname
		String name = dataBaseController.GetCustomName(player);

		if(name == null)
		{
			// Sets
			player.setCustomName(player.getName());
		}
		else
		{
			// Sets
			player.setCustomName(_bukkitHook.TransformColor(name));
		}		
	}
}
