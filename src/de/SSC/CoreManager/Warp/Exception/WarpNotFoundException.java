package de.SSC.CoreManager.Warp.Exception;

public class WarpNotFoundException extends Exception 
{
	private static final long serialVersionUID = 1093210507025801862L;
	public final String MissingWarpName;
	
	public WarpNotFoundException(String warpName) 
    {
        super("Warp not found : " + warpName);
        
        MissingWarpName = warpName;
    }
}
