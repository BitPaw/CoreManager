
 package de.PlayerPing.SSC;

 import de.PlayerPing.SSC.Messages;
 import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

 public class Main extends JavaPlugin 
 {
	 private Logger console = null; 
	 
	public Main()
	{
		//console = Logger.getLogger("Minecraft"); // Get The console
	}
	 
   public void onEnable() 
   {
	   Messages.Send(Messages.OnEnable); 
   }
	   
   public void onDisable() 
   {
	  Messages.Send(Messages.OnDisable);
   }
	   /*
	    @Override
	    public boolean onCommand(CommandSender sender,
	            Command command,
	            String label,
	            String[] args) {
	        if (command.getName().equalsIgnoreCase("mycommand")) 
	        {
	            sender.sendMessage("You ran /mycommand!");
	            return true;
	        }
	        return false;
	    }
	    */
 }