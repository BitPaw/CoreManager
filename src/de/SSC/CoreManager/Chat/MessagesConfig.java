package de.SSC.CoreManager.Chat;

import de.SSC.CoreManager.Config.IConfig;
import de.SSC.CoreManager.DataBase.MessagesDataBase;
import de.SSC.CoreManager.Player.MessagesPlayer;
import de.SSC.CoreManager.Rank.MessagesRank;
import de.SSC.CoreManager.Sign.MessagesSign;
import de.SSC.CoreManager.Teleport.TeleportSystemMessageContainer;
import de.SSC.CoreManager.World.MessagesWorld;

public class MessagesConfig implements IConfig
{
   public MessagesChatManager Chat;
   public MessagesConsoleIO ConsoleIO;
   public MessagesSign Sign;
   public MessagesPlayer Player;
   public TeleportSystemMessageContainer TeleportSystem;
   public MessagesWorld World;
   public MessagesRank Rank;
   public MessagesDataBase DataBase;

   
   public MessagesConfig()
   {
	 Chat = new MessagesChatManager();
	 ConsoleIO = new MessagesConsoleIO();
     Sign  = new MessagesSign();
     Player = new MessagesPlayer();
     TeleportSystem = new TeleportSystemMessageContainer();
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