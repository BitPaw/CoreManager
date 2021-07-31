package de.SSC.CoreManager.Config.ConfigFiles;

import de.SSC.CoreManager.Config.IConfig;

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
	  public String[] AllowedTools;

	public void LoadDefaults() 
	{			  
		  UseAnything = false;
		  AllowedTools = ("WOOD_AXE,STONE_AXE,IRON_AXE,GOLD_AXE,DIAMOND_AXE").split(",");
		  MoreDamageToTools = false;
		  InterruptIfToolBreaks = false;
		  LogsMoveDown = false;
		  OnlyTrees = true;
		  PopLeaves = false;
		  LeafRadius = 3;		  
	}		
}