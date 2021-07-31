package de.SSC.CoreManager.Utility;

import org.bukkit.command.CommandSender;

import de.SSC.CoreManager.Messages.Logger;
import de.SSC.CoreManager.Messages.MessageType;
import de.SSC.CoreManager.Messages.Module;

public class SystemInfo 
{
	Runtime runtime = null;
	SystemInformation systemInformation = null;
	
	
	
	public SystemInfo()
{
		runtime = Runtime.getRuntime();
		systemInformation = new SystemInformation();
}

	public void CallGarbageCollector()
	{
		runtime.gc();
	}
	
public void GetSystemInfos(CommandSender sender)
{	   
	  Logger logger = Logger.Instance();
	
	   String out = "\n";
	   
	   out += "&5=========================================================\n";
	   out += "  &3System Information\n";
	   out += "&5=========================================================\n";
	   out += "  &3CPU &5: &b{CPUModel}\n";
	   out +=     "        &3Cores &5<&b{AvailableProcessors}&5>\n";
	   out += "  &3RAM &5: &b{CurrentRAM} &3from &b{MaxRAM} &5(&b{Usage}&5) \n";
	   out += "   &3IP &5: &3Horstname &b{HostName}\n";
	   out +=     "        &3IP-Address &b{IPAddress}\n";
	   out += "&5=========================================================\n&r";
	   
	    out = out.replace("{CPUModel}",  systemInformation.CPUModel);
	    out = out.replace("{AvailableProcessors}",  systemInformation.AvailableProcessors);
	    out = out.replace("{CurrentRAM}",  systemInformation.CurrentMemory);
	    out = out.replace("{MaxRAM}",  systemInformation.MaxMemory);
	    out = out.replace("{Usage}",  systemInformation.RAMUsageIInPercent); 
	    out = out.replace("{HostName}",  systemInformation.HostName);
	    out = out.replace("{IPAddress}",  systemInformation.IPAddress); 
	   
	    
	    logger.SendToSender(Module.System, MessageType.None, sender, out);
	    
	    
}
	
	   
	   
}
