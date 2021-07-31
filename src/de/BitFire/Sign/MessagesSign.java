package de.BitFire.Sign;

import de.BitFire.Configuration.IConfig;

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