package de.SSC.CoreManager.Tab;

import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PingTabList extends BukkitRunnable
{
	private JavaPlugin plugin;
			
	boolean UsePrefix = false;		
	boolean UseSuffix = true;
	
	String LocalPingTag = "&7[&f%ping%&7]";
	
	String LowPingTag = "&7[&b%ping%&7]";
	int LowPing = 20;
	
	String PingTag = "&7[&a%ping%&7]";	
	int Ping = 150;
	
	String MidPingTag = "&7[&e%ping%&7]";	
	int MidPing = 500;
	
	String HiPingTag = "&7[&c%ping%&7]";
	int HiPing = 1000;
	
	String DeathPingTag = "&7[&4%ping%&7]";	
	int DeathPing = 1200;
	
	
	  public PingTabList(JavaPlugin plugin)
	  {
	    this.plugin = plugin;	    
	  }
	  
	  public void run()
	  {
		Collection<?extends Player> players = this.plugin.getServer().getOnlinePlayers();
		 		
	    for (Player player : players)
	    {
	    	int ping = GetPing(player);
	    	String currentName = "";	
	    	String pingText = "";
	    	String tag = SetColorTag(ping);
	    	
	      if (UsePrefix)
	      {
	        currentName = player.getName();
	      } 
	      else 
	      {
	        currentName = player.getDisplayName();
	      } 
	      
	      if (UsePrefix) 
	      {	    	
	    	pingText = tag.replace("%ping%", new StringBuilder().append("").append(ping).toString()) + " " + currentName;  
	    	
	        player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', pingText));
	      }
	      
	      if (UseSuffix)
	      {
	    	pingText = tag.replace("%ping%", new StringBuilder().append("").append(ping).toString());  
	    	  
	        player.setPlayerListName(currentName + " " + ChatColor.translateAlternateColorCodes('&', pingText));
	      }
	    }
	  }

	  public int GetPing(Player player) 
	  {
		  org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer craftPlayer = (org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer) player; 		  
		  net.minecraft.server.v1_12_R1.EntityPlayer entityPlayer = craftPlayer.getHandle(); 
		  
		  int ping = entityPlayer.ping;
		  
		  return ping;
      }
	  
	  private String SetColorTag(int ping)
	  {
		  String pingTag = "";
		  
		  	if(ping >= DeathPing)
	    	{
		  		pingTag = DeathPingTag;
	    	}
	    	else 	    		
	    	{
	    		if(ping >= HiPing)
	    		{
	    			pingTag = HiPingTag;
	    		}
	    		else
	    		{
	    			if(ping >= MidPing)
		    		{
	    				pingTag = MidPingTag;
		    		}
	    			else
	    			{
	    				if(ping >= Ping)
	    				{
	    					pingTag = PingTag;
	    				}
	    				else
	    				{
	    					if(ping >= LowPing)
	    					{
	    						pingTag = LowPingTag;
	    					}
	    					else
	    					{
	    						pingTag = LocalPingTag; 
	    					}
	    					
	    				}
	    			}
	    		}
	    	   
	    	}
		  	
		  	return pingTag;
	  }
	 
}