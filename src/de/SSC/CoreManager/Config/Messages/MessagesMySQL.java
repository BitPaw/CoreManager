package de.SSC.CoreManager.Config.Messages;

import de.SSC.CoreManager.Config.IConfig;

public class MessagesMySQL implements IConfig
{
	public String SQLConnectionSuccessfully;
	public String SQLConnectionFailed;
	public String SQLStatementError;
	public String SQLClosingError;
	public String SQLMessageHeader;
	public String SQLMessageFooter; 	
	public String SQLRegisteringNewPlayer;
	public String SQLRegistrationError;	
	
	public void LoadDefaults() 
	{
		   SQLConnectionSuccessfully = "connection successfully.";
		   SQLConnectionFailed = "";
		   SQLStatementError = "statement error!";
		   SQLClosingError = "closing error!";
		   SQLMessageHeader = "&5----------[&dMessage&5]----------&d";
		   SQLMessageFooter = "&5-----------------------------"; 
		
		   SQLRegisteringNewPlayer = "Register new Player %player%";
		   SQLRegistrationError = "RegisterNewPlayer Error!";
	}
}