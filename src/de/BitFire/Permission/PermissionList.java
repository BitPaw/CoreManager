package de.BitFire.Permission;

import java.util.HashMap;
import java.util.Map;

public class PermissionList 
{
	private Map<String, CMPermission> _cmPermissionList;
	
	public PermissionList()
	{
		_cmPermissionList = new HashMap<String, CMPermission>();
	}
	
	public void Add(CMPermission cmPermission)
	{
		_cmPermissionList.put(cmPermission.Name, cmPermission);
	}
	
	public void Remove(String permissionName)
	{
		_cmPermissionList.remove(permissionName);
	}
	
	public void Clear()
	{
		_cmPermissionList.clear();
	}
	
	public CMPermission GetPermission(String permissionName)
	{
		return _cmPermissionList.get(permissionName);
	}
}
