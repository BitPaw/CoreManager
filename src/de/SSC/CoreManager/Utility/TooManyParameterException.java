package de.SSC.CoreManager.Utility;

import de.SSC.CoreManager.Systems.Chat.ExceptionInformation;

public class TooManyParameterException extends Exception 
{
	private static final long serialVersionUID = 1253870955281090739L;
	public final ExceptionInformation Information;
	
	public TooManyParameterException(ExceptionInformation exceptionInformation) 
    {		
        Information = exceptionInformation;
    }
}