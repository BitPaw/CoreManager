package de.BitFire.NPC;

import de.BitFire.Geometry.PointView;

public class NPC 
{
	public final int ID;
	public final String Name;
	public final int WorldID;
	public final PointView Position;
	public final int PlayerID;
	public final boolean LookAtPlayer;
	
	public NPC(final int id, final String Name, final int WorldID, final PointView Position, final boolean lookAtPlayer)
	{
		this(id, Name, WorldID, Position, lookAtPlayer, -1);
	}
	
	public NPC(final int id, final String name, final int worldID, final PointView position, final boolean lookAtPlayer, final int playerID)
	{
		ID = id;
		Name = name;
		WorldID = worldID;
		Position = position;
		PlayerID = playerID;
		LookAtPlayer = lookAtPlayer;
	}
}