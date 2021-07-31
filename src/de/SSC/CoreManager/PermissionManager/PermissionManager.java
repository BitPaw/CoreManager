package de.SSC.CoreManager.PermissionManager;

import de.SSC.CoreManager.DataBase.DataTypes.CMPlayer;
import de.SSC.CoreManager.DataBase.DataTypes.CMRank;

public class PermissionManager
{
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
}
