package de.SSC.CoreManager.System;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;

import de.SSC.CoreManager.Chat.Logger;
import de.SSC.CoreManager.Chat.MessageType;
import de.SSC.CoreManager.Chat.Module;

public class CoreSystem extends BaseSystem implements ISystem
{
	private static CoreSystem _instance;
	private Runtime _runtime;
	private SystemInformation _systemInformation;
	private Logger _logger;
	
	private CoreSystem()
	{	
		super(Module.System, SystemState.Active, SystemPriority.Essential);
		_instance = this;		
	
		_systemInformation = new SystemInformation();
	}

	public static CoreSystem Instance()
	{
		return _instance == null ? new CoreSystem() : _instance;
	}
	
	@Override
	public void LoadReferences() 
	{
		_logger = Logger.Instance();		
		_runtime = Runtime.getRuntime();
	}
	
	@Override
	public void PrintData(CommandSender sender) 
	{
		String out = "\n";

		out += "&5=========================================================\n";
		out += "  &3System Information\n";
		out += "&5=========================================================\n";
		out += "  &3CPU &5: &b{CPUModel}\n";
		out += "        &3Cores &5<&b{AvailableProcessors}&5>\n";
		out += "  &3RAM &5: &b{CurrentRAM} &3from &b{MaxRAM} &5(&b{Usage}&5) \n";
		out += "   &3IP &5: &3Horstname &b{HostName}\n";
		out += "        &3IP-Address &b{IPAddress}\n";
		out += "&5=========================================================\n&r";

		out = out.replace("{CPUModel}", _systemInformation.CPUModel);
		out = out.replace("{AvailableProcessors}", _systemInformation.AvailableProcessors);
		out = out.replace("{CurrentRAM}", _systemInformation.CurrentMemory);
		out = out.replace("{MaxRAM}", _systemInformation.MaxMemory);
		out = out.replace("{Usage}", _systemInformation.RAMUsageIInPercent);
		out = out.replace("{HostName}", _systemInformation.HostName);
		out = out.replace("{IPAddress}", _systemInformation.IPAddress);


		_logger.SendToSender(Information.GetSystemModule(), MessageType.None, sender, out);
	}

	public void CallGarbageCollector()
	{
		_runtime.gc();
	}
	
	public void SetMaxPlayers(ServerListPingEvent event, int maxPlayers)
	{
		 event.setMaxPlayers(maxPlayers);
	}	
	  
	public void onPlayerLogin(PlayerLoginEvent event, int maxPlayers)
	{
		if (maxPlayers >= Bukkit.getServer().getOnlinePlayers().size() && (event.getResult().equals(PlayerLoginEvent.Result.KICK_FULL))) {
	      event.allow();
	    }
	}
}