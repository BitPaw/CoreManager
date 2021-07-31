package de.SSC.CoreManager.World;

import org.bukkit.World;

public class CMWorld 
{	
	public WorldState State;
	public World BukkitWorld;	
	public CMWorldInformation Information;

    public CMWorld(CMWorldInformation information)
    {    	
    	State = WorldState.InActiveAndRegistered;
    	BukkitWorld = null;    	
    	Information = information;
    }
        
    public CMWorld(World bukkitWorld)
    {
    	State = WorldState.ActiveButNotRegistered;
    	BukkitWorld = bukkitWorld;
    	Information = new CMWorldInformation(bukkitWorld); 	      	
    }  
}
