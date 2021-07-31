package de.SSC.CoreManager.Essentials.Chat;
/*
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;
*/
public class ChatMain 
{/*
	 public Chat chat = null;
	  public Permission perms = null;
	  public Essentials essentials = null;
	  public MultiverseCore mv = null;
	  
	  public void onEnable()
	  {
	    setup();
	  }
	  
	  public void onDisable()
	  {
	    this.chat = null;
	    this.perms = null;
	    this.econ = null;
	    this.essentials = null;
	    this.pvpstats = null;
	    this.mv = null;
	    this.kingkits = false;
	    this.placeholders = false;
	  }
	  
	  public void setup()
	  {
	    if (getServer().getPluginManager().getPlugin("Vault") == null)
	    {
	      getLogger().info("**** CHATFORMAT ****");
	      getLogger().info(" ");
	      getLogger().info("   - Vault not found! Disabling plugin...");
	      getLogger().info(" ");
	      getLogger().info("   - Plugin sucesfully disabled!");
	      getLogger().info("   - Plugin by Adin_SK");
	      getLogger().info("   - My next spigot plugins: https://www.spigotmc.org/resources/authors/adin_sk.80050/");
	      getLogger().info(" ");
	      getLogger().info("********************");
	      getServer().getPluginManager().disablePlugin(this);
	    }
	    else
	    {
	      setupPermissions();
	      setupChat();
	      getLogger().info("**** CHATFORMAT ****");
	      getLogger().info(" ");
	      getLogger().info("   - Vault hooked!");
	      getLogger().info(" ");
	      if (setupEconomy())
	      {
	        getLogger().info("   - Vault economy hooked!");
	        getLogger().info(" ");
	      }
	      if (setupEssentials())
	      {
	        getLogger().info("   - Essentials hooked!");
	        getLogger().info(" ");
	      }
	      if (setupPvPStats())
	      {
	        getLogger().info("   - PvPStats hooked!");
	        getLogger().info(" ");
	      }
	      if (setupMultiverseCore())
	      {
	        getLogger().info("   - Multiverse-Core hooked!");
	        getLogger().info(" ");
	      }
	      if (setupKingKits())
	      {
	        getLogger().info("   - KingKits hooked!");
	        getLogger().info(" ");
	      }
	      if (setupPlaceHolderAPI())
	      {
	        getLogger().info("   - PlaceHolderAPI hooked!");
	        getLogger().info(" ");
	      }
	      getLogger().info("   - Plugin sucesfully enabled!");
	      getLogger().info("   - Plugin by Adin_SK");
	      getLogger().info("   - My next spigot plugins: https://www.spigotmc.org/resources/authors/adin_sk.80050/");
	      getLogger().info(" ");
	      getLogger().info("********************");
	      getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
	      getCommand("chatformat").setExecutor(new MainCommand(this));
	    }
	  }
	  
	  public boolean setupChat()
	  {
	    RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
	    this.chat = ((Chat)rsp.getProvider());
	    return this.chat != null;
	  }  
*/

}
