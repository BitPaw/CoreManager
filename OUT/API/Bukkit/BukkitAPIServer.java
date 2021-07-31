package de.BitFire.API.Bukkit;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

import de.BitFire.Chat.Logger;
import de.BitFire.Player.CMPlayer;
import de.BitFire.Player.PlayerState;
import de.BitFire.Player.PlayerSystem;
import net.minecraft.server.v1_15_R1.MinecraftServer;

public final class BukkitAPIServer 
{	
	private BukkitAPIServer()
	{

	}
	
	 public final static boolean IsServerCracked()
	 {
		  return !Bukkit.getOnlineMode();
	 }	 

	 public final static void ShutdownServer() 
	 {	
		 final PlayerSystem playerSystem = PlayerSystem.Instance();
		 final Logger logger = Logger.Instance();
		 final List<CMPlayer> players = playerSystem.GetPlayers();
		 final Server server = Bukkit.getServer();	

		 for(final CMPlayer cmPlayer : players)
		 {
			 final boolean isOnline = cmPlayer.State == PlayerState.Online;
			 
			 if(isOnline)
			 {
				 String message = "&7===] &6The server is now &4shutting down&7. &eThank you &6for playing. &7[===";
				 
				 message = logger.TransformToColor(message);
				 
				 cmPlayer.BukkitPlayer.kickPlayer(message);
			 }
		 }
		 
		 server.shutdown();
	 }

	 public final static void SendBroadCastMessage(final String message)
	 {
		 final Server server = Bukkit.getServer();
		 
		 server.broadcastMessage(message);
	 }

	@SuppressWarnings("deprecation")
	public final static MinecraftServer GetMineCraftServer()
	{
		return MinecraftServer.getServer();
	}
	
	public final static void SendVanillaCommand(final String command)
	{
		final Server server  = Bukkit.getServer();
		final CommandSender console = Bukkit.getConsoleSender();
		
		server.dispatchCommand(console, command);
	}
}
