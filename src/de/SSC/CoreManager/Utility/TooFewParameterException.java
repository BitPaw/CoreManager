package de.SSC.CoreManager.Utility;

import de.SSC.CoreManager.Systems.Chat.ExceptionInformation;

public class TooFewParameterException extends Exception 
{
	private static final long serialVersionUID = -2292940369671715952L;
	public final ExceptionInformation Information;
	
	public TooFewParameterException(ExceptionInformation exceptionInformation) 
    {		
        Information = exceptionInformation;
    }
}