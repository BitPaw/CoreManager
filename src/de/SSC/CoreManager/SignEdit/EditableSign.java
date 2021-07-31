package de.SSC.CoreManager.SignEdit;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class EditableSign
{
  static boolean DEBUG = false;
  static Logger LOGGER = Logger.getLogger("Minecraft");
  private final EditableSignCommandExecutor commandExecutor ;
  private final EditableSignEventListener eventListener ;

  private JavaPlugin plugin;


  public EditableSign   (JavaPlugin plugin)
  {
    this.plugin = plugin;

    commandExecutor = new EditableSignCommandExecutor();
    eventListener= new EditableSignEventListener(plugin);

    plugin.saveDefaultConfig();

    plugin.getCommand("editsign").setExecutor(this.commandExecutor);
    
    PluginManager pm =   plugin.getServer().getPluginManager();
    pm.registerEvents(this.eventListener, plugin);
    
    
    PluginDescriptionFile pdfile =   plugin.getDescription();
    if (!this.eventListener.setReEditSignMethod())
    {
      pm.disablePlugin(plugin);
      plugin.getLogger().info(pdfile.getName() + " version " + pdfile.getVersion() + " is Disabled. Could not find a sign method.");
    }
    else
    {
      plugin.getLogger().info(pdfile.getName() + " version " + pdfile.getVersion() + " is Enabled");
    }
  }
}
