package de.SSC.CoreManager;

import org.bukkit.command.CommandSender;

import de.SSC.CoreManager.Systems.Chat.Logger;
import de.SSC.CoreManager.Systems.Chat.MessageType;
import de.SSC.CoreManager.Systems.Chat.Module;
import de.SSC.CoreManager.Utility.SystemInformation;

public class CoreSystem
{
	private static CoreSystem _instance;
	private Runtime _runtime;
	private SystemInformation _systemInformation;

	private CoreSystem()
	{
		_runtime = Runtime.getRuntime();
		_systemInformation = new SystemInformation();
	}

	public static CoreSystem Instance()
	{
		return _instance == null ? new CoreSystem() : _instance;
	}

	public void CallGarbageCollector()
	{
		_runtime.gc();
	}

	public void GetSystemInfos(CommandSender sender)
	{
		Logger logger = Logger.Instance();

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


		logger.SendToSender(Module.System, MessageType.None, sender, out);
	}
}
