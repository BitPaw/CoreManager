package de.SSC.CoreManager.Region;

import java.util.UUID;

import de.SSC.CoreManager.Geometry.Cube;

public class CMRegion 
{
	public final int ID;
	public final String Name;
	public final int WorldID;
	public final Cube RegionField;
	public final RegionInformation Information;	
	public UUID Owner;	
	
	public CMRegion(final int id, final String name, final int worldID , final Cube cube, final RegionInformation regionInformation)
	{
		ID = id;
		Name = name;
		RegionField = cube;
		
		WorldID = worldID;
		Information = regionInformation;
		
		Owner = null;
	}		
}




/*
Logger logger = Logger.Instance();
String message = _isInRegion ? "&7This region &ais &7protected." : "&7This region is &cnot &7protected.";	;
String yes = "&aYes";
String no = "&cNo";
String arrow = " &8-> ";
	
message += "\n";

message += " &7[&cX&7] " + (_isInX ? yes : no) + arrow + "&7A&8:&e" + xA + " &7B&8:&e"  + _xB + " &7P&8:&e" +  x + "\n";
	
	if(IgnoreHeight)
	{
		 message += " &7[&aY&7] " + yes + arrow + "&7Ignoring height" + "\n";
	}
	else
	{
		 message += " &7[&bY&7] " + (_isInY ? yes : no) + arrow +  "&7A&8:&e" + _yA + " &7B&8:&e"  + yB + " &7P&8:&e" +  y + "\n";
	}
	
	 message += " &7[&bZ&7] " + (_isInZ ? yes : no)  + arrow + "&7A&8:&e" +_zA + " &7B&8:&e"  + zB + " &7P&8:&e" +  z;
	 
	 logger.SendToSender(Module.RegionSystem, MessageType.None, sender, message);
*/