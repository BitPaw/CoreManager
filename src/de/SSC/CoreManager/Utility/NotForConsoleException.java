package de.SSC.CoreManager.Utility;

import de.SSC.CoreManager.Systems.Chat.ExceptionInformation;

@SuppressWarnings("serial")
public class NotForConsoleException extends Exception 
{
	public ExceptionInformation Information;
	
	public NotForConsoleException(ExceptionInformation exceptionInformation) 
    {
        super();
        
        Information = exceptionInformation;
    }
}
