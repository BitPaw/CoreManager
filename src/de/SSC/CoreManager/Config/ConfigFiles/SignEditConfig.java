package de.SSC.CoreManager.Config.ConfigFiles;

import java.util.ArrayList;
import java.util.List;

import de.SSC.CoreManager.Config.IConfig;

public class SignEditConfig implements IConfig
{
	// This is a template config file
	// define your parameter and its value like:
	public String ErrorMessage;
	
		
	// the use of WorldGuard,
	// player needs to have WorldGuard region flag USE = allow to edit the sign
	public boolean WorldGuard;

	
	// Use specific WorldGuard Region name for allowing edit?
	public boolean UseRegionName;
	
	public List<String> Regions;
	
	
	// number of seconds the newly edited sign will be non editable.
	public int SignCoolDown;
	
	
	// number of seconds, until next time you can edit a sign.
	public int WriteCoolDown;

	// log .. if this is true, the author and changed content will be push to the log
	public boolean Logging;
	

	public void LoadDefaults() 
	{
		 ErrorMessage = "&c [ES] : Some error occured.";

			WorldGuard = true;
			UseRegionName = true;
			Regions =  new ArrayList<String>();
			SignCoolDown = 0;
			WriteCoolDown = 0;
			Logging = false;			
	}	
	
}
