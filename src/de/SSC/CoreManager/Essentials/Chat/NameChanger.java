package de.SSC.CoreManager.Essentials.Chat;

import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Messages;
import de.SSC.CoreManager.Color.Logger;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.EntityTracker;
import net.minecraft.server.v1_12_R1.WorldServer;

public class NameChanger 
{	
	public boolean CheckCommand(CommandSender sender, String[] args)
	{	
		 if (!(sender instanceof CraftPlayer))
		    {
		      sender.sendMessage(Messages.Error + Messages.NotForConsole);
		      return false;
		    }
		    
		    if (sender.hasPermission(Messages.PermissionChangeName))
		    {
		      EntityPlayer player = ((CraftPlayer)sender).getHandle();
		      Player senderplayer = (Player)sender;
		      
		      if (args.length > 0)
		      {
		    	  String out = Messages.Info + Messages.NameChanged +  args[0];
		    	  
		        sender.sendMessage(Logger.TransformToColor(out));
		        senderplayer.setDisplayName(args[0]);
		        SetPlayerName(player, args[0]);
		      }
		      else
		      {		 
		    	  String out = Messages.Error + Messages.NameChangesWrongCommand;
		    	  
		        sender.sendMessage(Logger.TransformToColor(out));
		      }
		      
		      return true;
		    }
		    sender.sendMessage("Nope, can't do that!");
		    return false;
	}
	
	
	
	  public void SetPlayerName(EntityPlayer player, String name)
	  {
	    WorldServer world = (WorldServer)player.world;
	    EntityTracker tracker = world.tracker;
	    tracker.untrackEntity(player);
	    player.displayName = Logger.TransformToColor(name);
	    tracker.track(player);
	  }
}
