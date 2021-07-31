package de.BitFire.Tree;

import de.BitFire.Configuration.IConfig;

public class TimberConfig implements IConfig
{
	  public boolean DefaultActive;
	  public boolean UseAnything;
	  public boolean MoreDamageToTools;
	  public boolean InterruptIfToolBreaks;
	  public boolean LogsMoveDown;
	  public boolean OnlyTrees;
	  public boolean PopLeaves;
	  public int LeafRadius;

	public void LoadDefaults() 
	{			  
		  UseAnything = false;
		  MoreDamageToTools = false;
		  InterruptIfToolBreaks = false;
		  LogsMoveDown = false;
		  OnlyTrees = true;
		  PopLeaves = false;
		  LeafRadius = 3;		  
	}		
}