package de.SSC.CoreManager.Systems.SkinChanger;

import de.SSC.CoreManager.Systems.Chat.ExceptionInformation;

public class SkinLoadException extends Exception 
{
	private static final long serialVersionUID = 7967255671530824203L;
	public ExceptionInformation Information;
	
	public SkinLoadException(ExceptionInformation exceptionInformation) 
    {
        super();
        
        Information = exceptionInformation;
    }
}