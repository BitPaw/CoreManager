package de.SSC.CoreManager.System;

/**
 * Current state of a system.
 * @author BitPaw
 *
 */
public enum SystemState 
{
	/**
	 * System if not active and does not respond to commands.
	 */
	Inactive,
	
	/**
	 * System is active and is listening to commands.
	 */
	Active,
	
	/**
	 * System could not be started, maybe there was an exception in the initialization.
	 */
	FailedToLoad
}
