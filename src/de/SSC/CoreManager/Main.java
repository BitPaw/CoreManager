///////////////////////////////////////////////////////////////////////////////////////////////////
//-----------------------------------------------------------------------------------------------//
package de.SSC.CoreManager;
//-----------------------------------------------------------------------------------------------//
import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Config.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

//-----------------------------------------------------------------------------------------------//

//-----------------------------------------------------------------------------------------------//
public class Main extends JavaPlugin
{
    public static JavaPlugin Plugin;
    public static CoreController coreController;
    public static Config config;
    public static Messages messages;

    public Main()
    {
        Plugin = this;
        config = new Config();
        messages = new Messages();
    }

    // --- Functions ---
    @Override
    public void onEnable()
    {
        coreController = CoreController.GetInstance();
        coreController.Initialize();


    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        return coreController._CoreListener.onCommand(sender, cmd, commandLabel, args);
    }

    @Override
    public void onDisable()
    {
        coreController._BukkitHook.SendConsolMessage(messages.CoreManager + messages.Off);
    }
}
//-----------------------------------------------------------------------------------------------//
///////////////////////////////////////////////////////////////////////////////////////////////////