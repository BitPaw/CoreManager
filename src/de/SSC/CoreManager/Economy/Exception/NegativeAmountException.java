package de.SSC.CoreManager.Economy.Exception;

public class NegativeAmountException extends Exception 
{
	private static final long serialVersionUID = 8853767015451446831L;
	public final float NegativeValue;
	
	public NegativeAmountException(float negativeValue) 
    {
        super();
        
        NegativeValue = negativeValue;
    }
}