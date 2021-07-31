package de.SSC.CoreManager.Economy.Exception;

public class InvalidAmountParameterException extends Exception 
{
	private static final long serialVersionUID = 8853767015451446831L;
	public final String InvalidValue;
	
	public InvalidAmountParameterException(final String invalidValue) 
    {
        super();
        
        InvalidValue = invalidValue;
    }
}