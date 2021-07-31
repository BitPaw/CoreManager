package de.BitFire.PvP.Sign;

import de.BitFire.Geometry.Point;

public class ArenaSign 
{
	public final int ID;
	public final int WorldID;	
	public final Point Position;	
	
	/**
	 * @param id : Unique identification of this sign.
	 * @param worldID : World ID that is used in the CMWorldSystem.
	 * @param position : Position in the world.
	 */
	public ArenaSign(final int id, final int worldID, final Point position)
	{
		ID = id;
		WorldID = worldID;
		Position = position;
	}
}