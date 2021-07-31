package de.BitFire.API.CoreManager;

public class CMException extends Exception
{
	private static final long serialVersionUID = 1L;
    public static final String DefaultCMErrorMessage = "CoreManager threw an exeption.";
	
	public CMException() 
	{
		super();
	}
}