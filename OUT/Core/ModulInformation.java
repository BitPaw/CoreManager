package de.BitFire.Core;

import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Module;

public class ModulInformation 
{
	private SystemState _currentState; 
	private Module _module;
	private Priority _priorty;
	
	public ModulInformation(Module module, SystemState state, Priority priority) 
	{
		_currentState = state;
		_module = module;
		_priorty = priority;
	}
	
	/** 
	 * @return
	 * Current active state.
	 */
	public boolean IsActive() 
	{
		return _currentState == SystemState.Active;
	}	
	
	/**
	 * @return Priority of the sub-system.
	 */
	public Priority GetPriority()
	{
		return _priorty;
	}
	
	/**
	 * Get the system module that describes this sub-system.
	 * @return
	 */
	public Module GetSystemModule() 
	{
		return _module;
	}
	
	/**
	 * Set the current active state.
	 * 
	 * @param systemState
	 * State that will be set.
	 */
	public SystemState GetSystemState() 
	{	
		return _currentState;
	} 	
	
	/**
	 * @param The new state of this sub-system. 
	 */
	public void SetSystemState(SystemState systemState) 
	{	
		_currentState = systemState;
	}
}