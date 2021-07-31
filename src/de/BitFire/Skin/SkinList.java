package de.BitFire.Skin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkinList 
{
	private Map<UUID, SkinData> _skinMap;
	
	public SkinList()
	{
		_skinMap = new HashMap<UUID, SkinData>();
	}
	
	public void Add(SkinData skinData) 
	{
		_skinMap.put(skinData.UUID, skinData);
	}
	
	public SkinData Get(UUID playerUUID)
	{
		return _skinMap.get(playerUUID);
	}

	public boolean IsSkinCached(UUID playerUUID) 
	{
		return _skinMap.containsKey(playerUUID);
	}
}
