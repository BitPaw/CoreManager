package de.SSC.CoreManager.DataBase.DataTypes;

public class CMPermissionList 
{
	private static CMPermissionList _instance;

	public static CMPermissionList Instance() 
	{
		if(_instance == null)
		{
			_instance = new CMPermissionList();
		}		
		
		return _instance;
	}

}
