package de.SSC.CoreManager.DataBase;

import de.SSC.CoreManager.Config.IConfig;

public class MessagesDataBase implements IConfig
{
	public String DataBaseTag;
	
	public String ConnectionSuccessfull;
	public String ConnectionFailed;
	public String StatementError;
	public String ClosingError;
	public String MessageHeader;
	public String MessageFooter; 	
	public String EmptySQLMessage;

	public String FailedToGetInteger;
	public String FailedToGetBoolean;

	
	public void LoadDefaults() 
	{
		DataBaseTag = "&7[&eDataBase&7]";		


		ConnectionSuccessfull = "connection successfully.";
		   ConnectionFailed = "connection failed!";
		   StatementError = "statement error!";
		   ClosingError = "closing error!";
		   MessageHeader = "&5----------[&dMessage&5]----------&d";
		   MessageFooter = "&5-----------------------------"; 

		   
		   EmptySQLMessage = "&cYou are tring to send an empty SQL command!";
		   
		   FailedToGetInteger = "&cFailed to get integer!";
		   FailedToGetBoolean = "&cFailed to get boolean!";
		
	}	
}