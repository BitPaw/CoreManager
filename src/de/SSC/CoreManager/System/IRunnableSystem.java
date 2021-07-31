package de.SSC.CoreManager.System;

import de.SSC.CoreManager.System.Exception.SystemNotActiveException;

public interface IRunnableSystem 
{
	void Update() throws SystemNotActiveException;
}
