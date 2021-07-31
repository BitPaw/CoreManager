package de.SSC.CoreManager.System;

import de.SSC.CoreManager.Chat.Module;

public class ModulInformation 
{
	private SystemState _currentState; 
	private Module _module;
	private SystemPriority _priorty;
	
	protected ModulInformation(Module module, SystemState state, SystemPriority priority) 
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
	public SystemPriority GetPriority()
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