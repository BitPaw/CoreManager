package de.SSC.CoreManager.Manager;

import de.SSC.CoreManager.DataBase.DataTypes.CMPlayer;
import de.SSC.CoreManager.DataBase.DataTypes.CMRank;

public class PermissionManager
{
	public static PermissionManager _instance;
	
	private PermissionManager()
	{
		
		
		
		
	}
	
	
  public boolean CheckIfPlayerHasPermission(CMPlayer cmPlayer)
  {
	  return true;
  }
  
  public boolean ChangeRank(CMPlayer cmPlayer, CMRank rank)
  {
	  boolean succsessful = false;
	  
	  if(CheckIfPlayerHasPermission(cmPlayer))
	  {
		  cmPlayer.RankGroup = rank;
	  }
	  
	  return succsessful;
  }

public static PermissionManager Instance() 
{
	
	if(_instance == null)
	{
		_instance = new PermissionManager(); 
	}
	
	return _instance;
}
}
