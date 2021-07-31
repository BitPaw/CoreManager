package de.SSC.CoreManager.Config.ConfigFiles;

import de.SSC.CoreManager.Config.IConfig;

public class ChatConfig implements IConfig
{	  
	public char ColorCombineChar;
	public char ColorChar;
	public String OPPermissionSymbol;  
	
	public boolean DebugMode;	
	public boolean ColorLogs;	
	public boolean UseColor;  
	
	public boolean ShowWorld;		
	public boolean ShowPrefx;		
	public boolean ShowSuffix;		
	
	public boolean ShowUserSystemTag;
	
	public String WorldSyntax;
	public String PlayerDisplaySyntax;
	public String MessageSyntax;;		 
	
	public void LoadDefaults()	
	{		 
		ColorCombineChar = '&';		 
		ColorChar = '§';		
		OPPermissionSymbol = "&4$";
		
		DebugMode = true;			 
		ColorLogs = true;		 
		UseColor= true;		  
			
		ShowWorld = true;			
		ShowPrefx = true;
		ShowSuffix = false;			
			 
		ShowUserSystemTag = false;
		WorldSyntax = "&7[&e{WORLD}&7]";					
		PlayerDisplaySyntax= "&7[&6{RANK}&7][{OP}{RANKCOLOR}{PLAYER}&7]";			
		MessageSyntax = " &r&f{MESSAGE}";				 			 
	} 
}
