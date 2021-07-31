package de.SSC.CoreManager.DataBase.DataTypes;

import java.util.ArrayList;

public class CMWorldList 
{
  private static CMWorldList _instance;
  private ArrayList<CMWorld> _worlds;
	
  private CMWorldList()
  {
	  _worlds  = new ArrayList<CMWorld>();
  }
  
  public static CMWorldList Instance()
  {
	  if(_instance == null)
	  {
		  _instance = new CMWorldList();
	  }
	  
	  return _instance;
  }
  
	public CMWorld GetWorldPerName(String worldName)
	{
		CMWorld cmWorld;
		
		cmWorld = null;
		
		return cmWorld;
	}
	
	public void AddWorld(CMWorld cmWorld)
	{
		_worlds.add(cmWorld);
	}
}
