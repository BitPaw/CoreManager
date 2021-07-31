package de.SSC.CoreManager.Config.ConfigFiles;

import de.SSC.CoreManager.Config.IConfig;
import de.SSC.CoreManager.Config.Messages.MessagesChatManager;
import de.SSC.CoreManager.Config.Messages.MessagesConsoleIO;
import de.SSC.CoreManager.Config.Messages.MessagesDataBase;
import de.SSC.CoreManager.Config.Messages.MessagesPlayer;
import de.SSC.CoreManager.Config.Messages.MessagesRank;
import de.SSC.CoreManager.Config.Messages.MessagesSign;
import de.SSC.CoreManager.Config.Messages.MessagesTeleportSystem;
import de.SSC.CoreManager.Config.Messages.MessagesWorld;

public class MessagesConfig implements IConfig
{
   public MessagesChatManager Chat;
   public MessagesConsoleIO ConsoleIO;
   public MessagesSign Sign;
   public MessagesPlayer Player;
   public MessagesTeleportSystem TeleportSystem;
   public MessagesWorld World;
   public MessagesRank Rank;
   public MessagesDataBase DataBase;
   
   public MessagesConfig()
   {
	 Chat = new MessagesChatManager();
	 ConsoleIO = new MessagesConsoleIO();
     Sign  = new MessagesSign();
     Player = new MessagesPlayer();
     TeleportSystem = new MessagesTeleportSystem();
     World = new MessagesWorld();
     Rank = new MessagesRank();
     DataBase = new MessagesDataBase();
   }


public void LoadDefaults() 
{
	 Chat.LoadDefaults();
	 ConsoleIO.LoadDefaults();
     Sign.LoadDefaults();	
     Player.LoadDefaults();
     TeleportSystem.LoadDefaults();
     World.LoadDefaults();
     Rank.LoadDefaults();
     DataBase.LoadDefaults();
}	
   
   
}