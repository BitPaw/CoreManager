package de.BitFire.CoreManager.Modules.Chat;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import de.BitFire.CoreManager.Modules.Player.Player;
import de.BitFire.CoreManager.Modules.Player.PlayerManager;
import de.BitFire.CoreManager.Modules.World.WorldManager;
import de.BitFire.CoreManager.Modules.World.Type.World;

public class ChatManager 
{
	private static CommandSender _console;
	
	static
	{
		_console = Bukkit.getConsoleSender();
	}
	
	public static void Send(MessageType type, String message, CommandSender sender) 
	{		
		Send(type, null, message, sender);
	}
	

	public static void SendToConsole(MessageType type, String message) 
	{
		SendToConsole(type, null, message);		
	}
	
	public static void Send(final MessageType type, final MessageSouceInfromation information, String message,  final CommandSender sender)
	{		
		message = TransformMessage(type, information, message);
		
		message = ColorManager.AddColor(message);
		
		sender.sendMessage(message);
	}
	
	public static void SendToConsole(final MessageType type, final MessageSouceInfromation information, String message)
	{
		message = TransformMessage(type, information, message);
		
		message = ColorManager.AddColor(message);
		
		_console.sendMessage(message);		
	}
	
	private static String TransformMessage(final MessageType type, final MessageSouceInfromation information, final String message)
	{
		String typeTag;
		
		switch(type)
		{
		case Error:
			typeTag = "&4x";
			break;
			
		case Info:
			typeTag = "&ei";
			break;
			
		case Loaded:
			typeTag = "&a^";
			break;
			
		case Loading:
			typeTag = "&6>";
			break;
			
		case None:
			typeTag = "&0-";
			break;
			
		case Question:
			typeTag = "&b?";
			break;
			
		case Saved:
			typeTag = "&5°";
			break;
			
		case Unloaded:
			typeTag = "&cv";
			break;
			
		case Warning:
			typeTag = "&6!";
			break;	
			
			default:
				typeTag = "&r-";
		}
		
	
		if(information == null)
		{			
			return  "&8[&7System&8][" + typeTag + "&8] " + message;
		}
		else
		{
			final String colorTag =  ColorManager.GetColorCode(information.Color);			
			
			
			return "&8[" + colorTag + information.Name + "&8][" + typeTag + "&8] " + message;
		}				
	}
	
	public static String FormatChatMessage(final org.bukkit.entity.Player bukkitPlayer, final String message)
	{
		final String syntax = "&8[&r{W}&8][&r{R}&8][&r{N}&8]&r {T}";
		final String WorldTag = "{W}";
		final String RankTag = "{R}";
		final String NameTag = "{N}";
		final String TextTag = "{T}";
		
		final UUID playerUUID = bukkitPlayer.getUniqueId();
		String chatMessage;			
		
		// WorldName
		{
			String worldName = bukkitPlayer.getWorld().getName();
			worldName = WorldManager.RemoveFolderPath(worldName);
			final World world = WorldManager.GetWorld(worldName);
			
			if(world != null)
			{
				worldName = world.GetPreferedName();
			}
	
			chatMessage = syntax.replace(WorldTag, worldName);
		}		
		
		// Rank
		{
			chatMessage = chatMessage.replace(RankTag, "R");
		}	
		
		// PlayerName
		{
			final String playerName = PlayerManager.GetPlayer(playerUUID).GetPreferedName();
			
			chatMessage = chatMessage.replace(NameTag, playerName);
		}
		
		// Message
		{
			chatMessage = chatMessage.replace(TextTag, message);
		}	

		return chatMessage;
	}
	
	public static String GetCustomWelcomeMessage(Player player)
	{		
		String message = "";
		
		if(player == null)
		{
			message = "&6CakeCraft\n&7Do i know you?";
		}
		else
		{
			message = "&6CakeCraft\n&7ahh yes you are &r" +  player.GetPreferedName();
		}
		
		return  message;
	}

	public static String GetGlobalJoinMessage(final Player player, final boolean isNew) 
	{		
		String message;
		
		if(isNew)
		{
			message = "&7A &6New Player &7joined the server! &7Meet &e&l" + player.GetPreferedName();
		}
		else
		{
			message = "&8[&2+&8] &2" + player.GetPreferedName();
		}
		
		return message;
	}

	public static String GetPrivateJoinMessage(final Player player, final boolean isNew) 
	{
		String message;
		
		if(isNew)
		{
			message = "&7Welcome &b&l" +  player.GetPreferedName() + " &7as our &bnew &7player!";
		}
		else
		{
			message = "&3Welcome Back!";
		}
		
		return message;
	}

	public static String GetPublicQuitMessage(UUID playerUUID)
	{
		Player player = PlayerManager.GetPlayer(playerUUID);
		String message = "&8[&4-&8] &4" + player.GetPreferedName();
				
		return message;
	}

	public static String GetFullPlayerName(UUID playerUUID) 
	{
		final Player player = PlayerManager.GetPlayer(playerUUID);
		final String playerName = player.GetPreferedName();		
		
		return playerName;
	}
}