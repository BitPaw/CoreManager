package de.BitFire.CoreManager.System;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandData 
{	
	public final CommandSender Sender;
	public final Command Command;
	public final String Label;
	public final String[] Parameter;	
	
	public CommandData(final CommandSender sender, final Command command, final String label, final String[] parameter)
	{
		Sender = sender;
		Command = command;
		Label = label;
		Parameter = parameter;
	}
	
	public boolean IsSenderPlayer()
	{
		return Sender instanceof Player;
	}
	
	public int ParameterLengh()
	{
		return Parameter.length;
	}
}