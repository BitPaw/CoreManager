package de.SSC.BukkitAPI;

import org.bukkit.Bukkit;
import org.bukkit.Server;

import net.minecraft.server.v1_15_R1.MinecraftServer;

public class BukkitAPIServer 
{
	private Server _server;
	
	public BukkitAPIServer()
	{
		_server = Bukkit.getServer();
	}
	
	 public boolean IsServerCracked()
	 {
		  return !Bukkit.getOnlineMode();
	 }	 

	 public void ShutdownServer() 
	 {		 
		 _server.shutdown();
	 }

	 public void  SendBroadCastMessage(String message)
	 {
		 _server.broadcastMessage(message);
	 }

	@SuppressWarnings("deprecation")
	public MinecraftServer GetMineCraftServer()
	{
		return MinecraftServer.getServer();
	}
}
