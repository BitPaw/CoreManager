package de.BitFire.CoreManager.Modules.Sign;

public class Sign 
{
	public int ID;
	public int DirectionID;
	public int SignFunctionID;
	public int WorldBlockPositionID;	
	
	public Sign(final int id, final int direcitonID, final int signFunction, final int worldBlockPosition)
	{
		ID = id;
		DirectionID = direcitonID;
		SignFunctionID = signFunction;
		WorldBlockPositionID = worldBlockPosition;
	}
	
	public SignFunction Function()
	{
		switch(SignFunctionID)
		{
		case 0:	return SignFunction.None;			
		case 1:	return SignFunction.Shop;		
		case 2:	return SignFunction.Command;		
		case 3:	return SignFunction.PvP;		
		case 4:	return SignFunction.Teleport;		
		case 5:	return SignFunction.FameFrame;		
			
			default:
				return SignFunction.Invalid;				
		}
	}
}