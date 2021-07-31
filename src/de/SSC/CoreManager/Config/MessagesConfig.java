package de.SSC.CoreManager.Config;

import de.SSC.CoreManager.Config.Messages.MessagesChatManager;
import de.SSC.CoreManager.Config.Messages.MessagesConsoleIO;
import de.SSC.CoreManager.Config.Messages.MessagesMySQL;
import de.SSC.CoreManager.Config.Messages.MessagesSign;

public class MessagesConfig implements IConfig
{
   public MessagesChatManager Chat;
   public MessagesConsoleIO ConsoleIO;
   public MessagesMySQL MySQL;
   public MessagesSign Sign;
   
   public MessagesConfig()
   {
	 Chat = new MessagesChatManager();
	 ConsoleIO = new MessagesConsoleIO();
	 MySQL  = new MessagesMySQL();
     Sign  = new MessagesSign();
   }


public void LoadDefaults() 
{
	 Chat.LoadDefaults();
	 ConsoleIO.LoadDefaults();
	 MySQL.LoadDefaults();
     Sign.LoadDefaults();	
}	
   
   
}