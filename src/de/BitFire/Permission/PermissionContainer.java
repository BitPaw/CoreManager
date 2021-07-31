package de.BitFire.Permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionContainer 
{
	private Map<Integer, String> _permission;
	
	public PermissionContainer()
	{
		_permission = new HashMap<Integer, String>();
	}

	public void Add(CMPermission cmPermission) 
	{
		_permission.put(cmPermission.ID, cmPermission.Name.toLowerCase());			
	}
	
	public void Clear()
	{
		_permission.clear();
	}
	
	public boolean HasPermission(String permissionName)
	{
		return _permission.containsValue(permissionName.toLowerCase());
	}

	public int GetAmount() 
	{
		return _permission.size();
	}

	public List<String> GetPermissions() 
	{
		List<String> permissions = new ArrayList<String>();

		for(Map.Entry<Integer, String> entry : _permission.entrySet()) 
		{
			String permission = entry.getValue();
			
			permissions.add(permission);
		}				
		
		return permissions;
	}
}