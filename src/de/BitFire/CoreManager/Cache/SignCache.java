package de.BitFire.CoreManager.Cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.BitFire.CoreManager.Modules.Sign.Sign;
import de.BitFire.CoreManager.Modules.Sign.SignShop;

public class SignCache 
{
	// Default sign
	private Map<Integer, Sign> _signLookUpTable;
	
	// worldidex
	private Map<Integer, List<Integer>> _worldIDSignLookUpTable;
	
	
	// Types of Sign
	private Map<Integer, Integer> _commandSignLookUpTable;
	private Map<Integer, Integer> _fameFrameSignLookUpTable;
	private Map<Integer, SignShop> _shopSignLookUpTable;
	private Map<Integer, Integer> _pvpSignLookUpTable;
	private Map<Integer, Integer> _teleportSignLookUpTable;
	
	public SignCache()
	{			
		_signLookUpTable = new HashMap<Integer, Sign>();
		
		_worldIDSignLookUpTable = new HashMap<Integer, List<Integer>>();
		
		_commandSignLookUpTable = new HashMap<Integer, Integer>();
		_fameFrameSignLookUpTable = new HashMap<Integer, Integer>();
		_shopSignLookUpTable = new HashMap<Integer, SignShop>();
		_pvpSignLookUpTable = new HashMap<Integer, Integer>();
		_teleportSignLookUpTable = new HashMap<Integer, Integer>();
	}
	
	public void AddToCache(final SignShop signShop)
	{
		
	}
	
	public void AddToCache(final Sign sign)
	{				
		final int id = sign.ID;		
		_signLookUpTable.put(id, sign);
	}
	
	public Sign GetSign(int id)
	{		
		return _signLookUpTable.get(id);
	}

	public Sign GetSign(int worldID, final int WorldPositionID) 
	{
		final List<Integer> signIDs = _worldIDSignLookUpTable.get(worldID);
		
		if(signIDs != null) // Is there even a world?
		{
			if(signIDs.size() > 0) // Has it atleast one ID?
			{
				for(int signID : signIDs) // Go thru every ID
				{
					Sign sign = GetSign(signID); // Get the sign from the ID
					
					if(sign != null) // Check if the ID was valid
					{
						if(sign.WorldBlockPositionID == WorldPositionID) // Check if same
						{ 
							return sign; // Found Sign
						}
					}
				}
			}
		}
		
		return null; // There was no sign
	}
}