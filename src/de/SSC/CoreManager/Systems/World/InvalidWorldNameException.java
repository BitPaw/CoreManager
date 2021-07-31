package de.SSC.CoreManager.Systems.World;

@SuppressWarnings("serial")
public class InvalidWorldNameException extends Exception 
{
	public InvalidWorldNameException(String message) 
    {
        super(message);
    }
}
