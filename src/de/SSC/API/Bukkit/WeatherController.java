package de.SSC.API.Bukkit;

import de.SSC.CoreManager.Config.Messages;
import de.SSC.CoreManager.Main;
import de.SSC.CoreManagerPlugins.BukkitHook;
import org.bukkit.command.CommandSender;

public class WeatherController 
{	
	private BukkitHook _bukkitHook;
	private Messages _messages;
	
	public WeatherController(BukkitHook bukkitHook)
	{
		_bukkitHook = bukkitHook;
		_messages = Main.messages;
	}
	
	public void Rain(CommandSender sender)
	{
		_bukkitHook.SendMessage(sender , _messages.Rain);
		_bukkitHook.SendCommand("toggledownfall");
	}

	public void Day(CommandSender sender)
	{
		_bukkitHook.SendMessage(sender , _messages.Day);
		_bukkitHook.SendCommand("time set 3000");
	}
	public void Night(CommandSender sender)
	{
		_bukkitHook.SendMessage(sender , _messages.Night);
		_bukkitHook.SendCommand("time set 15000");
	}
}