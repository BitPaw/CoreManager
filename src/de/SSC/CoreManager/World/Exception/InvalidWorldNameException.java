package de.SSC.CoreManager.World.Exception;

@SuppressWarnings("serial")
public class InvalidWorldNameException extends Exception 
{
	public InvalidWorldNameException(String message) 
    {
        super("Invalid world name : " + message);
    }
	
	public InvalidWorldNameException(int id)
	{
		 super("Invalid world ID : " + id);
	}
}
