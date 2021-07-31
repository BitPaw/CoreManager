package de.SSC.CoreManager.Utility;

public enum DataSaveStorageType 
{
	Undefined, // If the world type is not set properly
	UnRegistered, // Loaded but not registered
	InActive, // Registered but not loaded
	Active, // Registered and loaded
	FailedToLoad // Registered but failed to loaded.	
}