package de.BitFire.API;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.BitFire.API.CoreManager.CoreManagerCommand;
import de.BitFire.API.CoreManager.SubCommand;

public class CommandCredentials 
{
	public final CommandSender Sender;
	public final Command BukkitCommand;
	public final boolean IsSenderPlayer;
	public final int ParameterLengh;
	public final String CommandLabel;
	public final String[] Parameter;
	public final CoreManagerCommand CMCommand; 
	public SubCommand SubCMD;
	
	public CommandCredentials(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) 
	{
		Sender = sender;
		BukkitCommand = cmd;
		CommandLabel = commandLabel;
		Parameter = args;
		IsSenderPlayer = sender instanceof Player;
		ParameterLengh = args.length;		
		CMCommand = CoreManagerCommand.valueOf(commandLabel.toLowerCase());
		
		if(ParameterLengh >= 1)
		{
			String rawCommand = Parameter[0];
			
			try
			{
				SubCMD = SubCommand.valueOf(rawCommand);
			}
			catch(Exception e)
			{				
				rawCommand = rawCommand.toLowerCase();
				
				switch(rawCommand)
				{
				case "h":
				case "help":
					SubCMD = SubCommand.Help;
					break;
					
				case "l":
				case "list":
					SubCMD = SubCommand.ListData;
					break;
					
				case "a":
				case "add":
				case "c":
				case "create":
					SubCMD = SubCommand.Create;
					break;
					
				case "r":
				case "remove":
				case "d":
				case "delete":
					SubCMD = SubCommand.Remove;
					break;
					
				case "w":
				case "warp":
				case "t":
				case "teleport":
					SubCMD = SubCommand.Warp;
					break;	
					
					default:
						SubCMD = SubCommand.Undefined;
						break;
				}
			}
		}
	}
	
	public Player TryGetPlayer()
	{
		return (Player)Sender;
	}
}