package de.BitFire.Teleport.Portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;

import de.BitFire.Geometry.Point;
import de.BitFire.Teleport.Portal.Exception.PortalNotFoundException;
import de.BitFire.World.CMWorld;
import de.BitFire.World.WorldSystem;

public class PortalList 
{
	private Map<Integer, Portal> _portalList;
	private Map<String, Integer> _portalListIDLookUp;
	private Map<Integer, List<Integer>> _worldIDToPortalIDLookup;
	
	public PortalList() 
	{
		_portalList = new HashMap<Integer, Portal>();
		_portalListIDLookUp = new HashMap<String, Integer>(); 
		_worldIDToPortalIDLookup = new HashMap<Integer, List<Integer>>(); 
	}
	
	public void Add(final Portal portal)
	{
		List<Integer> portalIDs;
		final int id = portal.ID;
		final int worldID = portal.WorldID;
				
		_portalList.put(id, portal);
		_portalListIDLookUp.put(portal.Name.toLowerCase(), id);	
		
		
		if(_worldIDToPortalIDLookup.containsKey(worldID))
		{
			portalIDs = _worldIDToPortalIDLookup.get(worldID);			
			portalIDs.add(id);
			
			_worldIDToPortalIDLookup.replace(worldID, portalIDs);
			
		}
		else
		{
			portalIDs = new ArrayList<Integer>(id);	
			portalIDs.add(id);
			
			_worldIDToPortalIDLookup.put(portal.WorldID, portalIDs);						
		}	
	}

	public void Remove(final String name) 
	{
		
	}

	public int GetNextID() 
	{
		return _portalList.size();
	}

	public List<Portal> GetAllPortals() 
	{
		return new ArrayList<Portal>(_portalList.values());
	}

	public Portal GetPortalFromLocation(final Location targetlocation) throws PortalNotFoundException 
	{	
		WorldSystem worldSystem = WorldSystem.Instance();
		final Point point = new Point(targetlocation);
		int id = -1;
		List<Integer> portalIDs;
				
		if(_portalList.isEmpty())
		{			
			throw new PortalNotFoundException(targetlocation);
		}				
	
		try
		{			
			final String worldName = targetlocation.getWorld().getName();
			final CMWorld cmWorld = worldSystem.GetWorld(worldName);	
			
			id = cmWorld.Information.ID;
		}
		catch(final Exception exception)
		{						
			throw new PortalNotFoundException(targetlocation);
		}
		
		// Check if Portal is in this world
		portalIDs = _worldIDToPortalIDLookup.get(id);
				
		if(portalIDs == null)
		{
			throw new PortalNotFoundException(targetlocation);
		}
		
		for(final Integer portalID : portalIDs)
		{
			final Portal portal = Get(portalID);
			final boolean isPlayerInPortal = portal.Field.IsPointInCube(point);
						
			if(isPlayerInPortal)
			{				
				return portal;			
			}
		}
		
		throw new PortalNotFoundException(targetlocation);
	}

	public Portal Get(final int iD) 
	{		
		return _portalList.get(iD);
	}

	public Portal Get(final String portalNameOrID) 
	{
		final int id = _portalListIDLookUp.get(portalNameOrID);
		return Get(id);
	}
}