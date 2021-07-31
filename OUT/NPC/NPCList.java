package de.BitFire.NPC;

import java.util.HashMap;
import java.util.Map;

public class NPCList 
{
	private Map<Integer, NPC> _npcMap;

	public NPCList()
	{
		_npcMap = new HashMap<Integer, NPC>();
	}
	
	public int GetNextID()
	{
		return _npcMap.size();
	}
	
	public void Add(NPC npc) 
	{
		_npcMap.put(npc.ID, npc);
	}
	
	public NPC Get(int npcID) 
	{
		return _npcMap.get(npcID);
	}

	public void Clear() 
	{
		_npcMap.clear();		
	}
}