package de.SSC.CoreManagerPlugins;

import de.SSC.API.Bukkit.WeatherController;
import de.SSC.CoreManager.Config.Messages;
import de.SSC.CoreManager.Main;
import de.SSC.CoreManager.Utility.CoreManagerPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BukkitHook extends CoreManagerPlugin
{
	public final char ColorCode = '&';

	
	public Server server = null;
	static boolean DebugMode = true;	
	static boolean Color = true;	     		
	public CommandSender console = null;	

    public WeatherController Weather;

	public BukkitHook()
	{
        Weather = new WeatherController(this);

		server = Bukkit.getServer();
		console = Bukkit.getConsoleSender();
	}

	public void ChangeCustomPlayerName()
	{
		
	}
	
	public List<Player> GetOnlinePlayers()
	{
		Collection<? extends Player> players = Bukkit.getOnlinePlayers();
		List<Player> playerList = new ArrayList<Player>();

		for(Player player : players)
		{
			playerList.add(player);
		}

		return playerList;
	}



	public String TransformColor(String message)
	{		 
		
		if(Color)
		{
			message = ChatColor.translateAlternateColorCodes(ColorCode, message);
		}
		else
		{
			message = ChatColor.stripColor(message);
		}
		
		return message;
	}
		
	public void SendCommand(String command)
	{
		server.dispatchCommand(console,command); 		
	}


	public boolean CheckIfCommandSenderIsPlayer(CommandSender sender)
	{
		if(IsConsole(sender))
		{
			Messages messages = Main.messages;
			SendMessage(sender, messages.NotForConsole);
			return false;
		}
		else return true;
	}
	
	public void ShutdownServer()
	{
		server.broadcastMessage("The Server will be Closed");
		server.shutdown();
	}

	public Player FindPlayer(String playerName)
	{
		Collection<? extends Player> players = server.getOnlinePlayers();
		Player returnPlayer = null;

		for(Player player : players)
		{
			String name = player.getName().toLowerCase();

			if(playerName.equalsIgnoreCase(name))
			{
				returnPlayer = player;
			}
		}

		return returnPlayer;
	}

	public boolean IsConsole(CommandSender sender)
	{
		return sender instanceof ConsoleCommandSender;
	}
	
	public boolean isPlayer (CommandSender sender)
	{
		return sender instanceof Player;
	}
	
	public void SendConsolMessage(String message)
	{
		console.sendMessage(TransformColor(message));
	}
	
	public void SendMessage(CommandSender sender, String message)
	{
		sender.sendMessage(TransformColor(message));
	}
	
	public PluginManager GetPluginManager()
	{
		PluginManager pluginManager = Bukkit.getServer().getPluginManager();
		
		return pluginManager;
	}	
}
