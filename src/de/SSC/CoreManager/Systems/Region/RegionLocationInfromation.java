package de.SSC.CoreManager.Systems.Region;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Systems.Chat.Logger;
import de.SSC.CoreManager.Systems.Chat.MessageType;
import de.SSC.CoreManager.Systems.Chat.Module;

public class RegionLocationInfromation 
{	
	boolean _ignoreHeight;
	
	public int PlayerX;
	public int PlayerY;
	public int PlayerZ;		
	public int XA;
	public int YA;
	public int ZA;
	public int XB;
	public int YB;
	public int ZB;	
	
	public boolean IsInX;
	public boolean IsInY;
	public boolean IsInZ;		
	
	public RegionLocationInfromation(Player player, Location locationA, Location locationB, boolean ignoreHeight)
	{
		_ignoreHeight = ignoreHeight;
		
		PlayerX = player.getLocation().getBlock().getX();
		PlayerY = player.getLocation().getBlock().getY();
		PlayerZ = player.getLocation().getBlock().getZ();			
		
		XA = locationA.getBlockX();
		YA = locationA.getBlockY();
		ZA = locationA.getBlockZ();
		XB = locationB.getBlockX();
		YB = locationB.getBlockY();
		ZB = locationB.getBlockZ();
				
		// Check X
		IsInX  = IsBetweenPoints(XA, XB, PlayerX); 

		if(ignoreHeight)
		{
			IsInY = true;

		}
		else
		{
			IsInY = IsBetweenPoints(YA, YB, PlayerY); 
		}
		
		// CHack Z
		IsInZ = IsBetweenPoints(ZA, ZB, PlayerZ);		
	}
	
	public boolean IsInRegion()
	{
		return IsInX && IsInY && IsInZ;
	}
	
	private boolean IsBetweenPoints(int pointA, int pointB, int target)
	{
		int min;
		int max;
		
		if(pointA >= pointB)
		{
			min = pointB;
			max = pointA;	
		}
		else
		{
			min = pointA;
			max = pointB;	
		}
		
		return target >= min && target <= max;
	}
	
	public void PrintCoordinates(CommandSender sender)
	{
		Logger logger = Logger.Instance();
		String message = IsInRegion() ? "&7This region &ais &7protected." : "&7This region is &cnot &7protected.";	;
		String yes = "&aYes";
		String no = "&cNo";
		String arrow = " &8-> ";
			
		message += "\n";
		
		message += " &7[&cX&7] " + (IsInX ? yes : no) + arrow + "&7A&8:&e" + XA + " &7B&8:&e"  + XB + " &7P&8:&e" +  PlayerX + "\n";
			
			if(_ignoreHeight)
			{
				 message += " &7[&aY&7] " + yes + arrow + "&7Ignoring height" + "\n";
			}
			else
			{
				 message += " &7[&bY&7] " + (IsInY ? yes : no) + arrow +  "&7A&8:&e" + YA + " &7B&8:&e"  + YB + " &7P&8:&e" +  PlayerY + "\n";
			}
			
			 message += " &7[&bZ&7] " + (IsInZ ? yes : no)  + arrow + "&7A&8:&e" + ZA + " &7B&8:&e"  + ZB + " &7P&8:&e" +  PlayerZ;
			 
			 logger.SendToSender(Module.RegionSystem, MessageType.None, sender, message);
	}
}
