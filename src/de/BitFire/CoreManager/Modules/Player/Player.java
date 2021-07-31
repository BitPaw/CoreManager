package de.BitFire.CoreManager.Modules.Player;

import java.util.UUID;

public class Player 
{	
	public int ID;
	public String Name;
	public String CustomName;
	public UUID UniqueIdentifier;
	public UUID UniqueIdentifierCracked;
	
	public Player(final int id, final String name, final UUID uuid)
	{
		this(id, name, null, uuid, null);
	}
	
	public Player(final int id, final String name, final String customName, final UUID uuid, final UUID crackedUUID)
	{
		ID = id;	
		Name = name;	
		CustomName = customName;
		UniqueIdentifier = uuid;
		UniqueIdentifierCracked = crackedUUID;
	}
	
	public String GetPreferedName()
	{
		return CustomName == null ? Name : CustomName;
	}
}
