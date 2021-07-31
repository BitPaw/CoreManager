package de.BitFire.Configuration;

import de.BitFire.Chat.MessagesChatManager;
import de.BitFire.DataBase.MessagesDataBase;
import de.BitFire.Player.MessagesPlayer;
import de.BitFire.Rank.MessagesRank;
import de.BitFire.Teleport.TeleportSystemMessageContainer;
import de.BitFire.World.MessagesWorld;

public class MessageConfig implements IConfig
{
   public MessagesChatManager Chat;
   public MessageIO IO;
   public MessagesPlayer Player;
   public TeleportSystemMessageContainer TeleportSystem;
   public MessagesWorld World;
   public MessagesRank Rank;
   public MessagesDataBase DataBase;
   
   public MessageConfig()
   {
	 Chat = new MessagesChatManager();
	 IO = new MessageIO();
     Player = new MessagesPlayer();
     TeleportSystem = new TeleportSystemMessageContainer();
     World = new MessagesWorld();
     Rank = new MessagesRank();
     DataBase = new MessagesDataBase();
   }

   public void LoadDefaults() 
   {
	   Chat.LoadDefaults();
	   IO.LoadDefaults();
	   Player.LoadDefaults();
	   TeleportSystem.LoadDefaults();
	   World.LoadDefaults();
	   Rank.LoadDefaults();
	   DataBase.LoadDefaults();
   }
}