package de.SSC.CoreManager.World;

public enum WorldState 
{	
	Undefined, // If the world type is not set properly
	InActiveAndRegistered, // Registered but not loaded
	ActiveButNotRegistered, // Loaded but not registered
	ActiveAndRegistered,// Registered and loaded
	FailedToLoad // Registered but failed to loaded.		
}