package de.PlayerPing.SSC;

public class PlayerList extends org.bukkit.scheduler.BukkitRunnable
{
	/*
	  //private SpigotPing plugin;
	  private SSCPlayerPing plugin;
	  
	  public PingTabList(SpigotPing plugin)
	  {
	    this.plugin = plugin;
	  }	
	*/
	
  public void GetPlayers()
  {
	  
  }
  
  public void run()
  {/*
    for (Player player : plugin.getServer().getOnlinePlayers()) 
	{
	  String currentName;      
	  
      if (plugin.getConfig().getBoolean("tablist.show-real-name")) 
      {
        currentName = player.getName();
      } 
      else 
      {
        currentName = player.getDisplayName();
      }
      
      String prefix = plugin.getConfig().getString("tablist.prefix");
      
      if (!prefix.equals(""))
      {
        player.setPlayerListName(org.bukkit.ChatColor.translateAlternateColorCodes('&', prefix.replace("%ping%", new StringBuilder().append("").append(com.xdefcon.spigotping.utils.PingUtil.getPing(player)).toString())) + " " + currentName);
      }
      
      String suffix = plugin.getConfig().getString("tablist.suffix");
      
      if (!suffix.equals("")) 
      {
         player.setPlayerListName(currentName + " " + org.bukkit.ChatColor.translateAlternateColorCodes('&', suffix.replace("%ping%", new StringBuilder().append("").append(com.xdefcon.spigotping.utils.PingUtil.getPing(player)).toString())));
      }
      */
    
  }
}
