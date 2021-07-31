package de.BitFire.CoreManager.Modules.World.Type;

import de.BitFire.CoreManager.Modules.DataBase.ICloneable;

public class WorldBlockPosition implements ICloneable
{
	public int ID;
	public int X;
	public int Y;
	public int Z;
	
	public WorldBlockPosition()
	{
		this(-1, -1, -1, -1);
	}
	
	public WorldBlockPosition(final int id, final int x, final int y, final int z)
	{
		ID = id;
		X = x;	
		Y = y;
		Z = z;
	}

	@Override
	public Object Clone() 
	{	
		return new WorldBlockPosition(ID, X, Y, Z);
	}
}