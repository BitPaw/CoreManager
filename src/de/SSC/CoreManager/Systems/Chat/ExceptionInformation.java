package de.SSC.CoreManager.Systems.Chat;

import org.bukkit.command.CommandSender;

public class ExceptionInformation 
{
	private static final String NotSet = "[N/A]";
	public final CommandSender Sender;
	public String ClassName;
	public String FuncionName;
	
	public ExceptionInformation(Object source, CommandSender commandSender)
	{
		if(source == null)
		{
			ClassName = NotSet;
			FuncionName = NotSet;
		}
		else
		{	try
		{
			ClassName = source.getClass().getSimpleName();
			FuncionName = source.getClass().getEnclosingMethod().getName();
		}
		catch(Exception e)
		{
			if(ClassName == null)
			{
				ClassName = NotSet;
			}
			if(FuncionName == null)
			{
				FuncionName = NotSet;
			}
		}
	
		}		
	
		Sender = commandSender;
	}	
}
