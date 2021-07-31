package de.SSC.CoreManager.Addons.DropHead;

public class DropHeadSystem 
{	
	private static DropHeadSystem _instance;
	public DropHeadCommand Command;
	public DropHeadEvent Event;	
	
	private DropHeadSystem()
	{	
		_instance = this;
		Command = new DropHeadCommand();
		Event = new DropHeadEvent();
	}
	  
	public static DropHeadSystem Instance()
	{
		return _instance == null ? new DropHeadSystem() : _instance;
	}	  
}