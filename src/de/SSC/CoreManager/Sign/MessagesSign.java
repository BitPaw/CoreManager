package de.SSC.CoreManager.Sign;

import de.SSC.CoreManager.Config.IConfig;

public class MessagesSign implements IConfig 
{
	public String SignLine;	
	public String SignLineTrigger;
	
	public void LoadDefaults()
	{	
		   SignLine = "&l-=-=-=-=-=-=-=-";	
		   SignLineTrigger = ":l:";
	}
}