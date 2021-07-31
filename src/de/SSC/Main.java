///////////////////////////////////////////////////////////////////////////////////////////////////
//-----------------------------------------------------------------------------------------------//
package de.SSC;
import de.SSC.CoreManager.CoreListener;
import de.SSC.CoreManager.Chat.Logger;
import de.SSC.CoreManager.Chat.MessageType;
import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.Config.Config;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
//-----------------------------------------------------------------------------------------------//
public class Main extends JavaPlugin
{
    private CoreListener _coreListener;
	private Logger _logger;
	private Config _config;
	
    public Main()
    {
    	CreateDataFolder();
    	
    	_logger = Logger.Instance();   
    	_logger.LoadReferences();
    	
    	_config  = Config.Instance();   
    	_config.LoadReferences();
    	
    	_coreListener = CoreListener.Instance();
    	_coreListener.SetJavaPlugin(this);
    	
    	_logger.SendToConsole(Module.CoreManager, MessageType.Loading, _config.Messages.ConsoleIO.LoadingFiles);    	
    	
    	_config.Load();  
    	
    	_logger.SendToConsole(Module.CoreManager, MessageType.Info,_config.Messages.ConsoleIO.LoadedFiles);
    }
    
    private void CreateDataFolder()
    {
        File dataFolder = new File("plugins/CoreManager/");
     
        if (!dataFolder.exists()) 
        {
            dataFolder.mkdirs();
        }
    }

    @Override
    public void onEnable()
    {
    	_logger.SendToConsole(Module.CoreManager, MessageType.Online,_config.Messages.ConsoleIO.On);
    	_coreListener.Start();   
    	_coreListener.RegisterAllEvents(this);    	
    	
    	_coreListener.ListEverything();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        return _coreListener.onCommand(sender, cmd, commandLabel, args);
    }

    @Override
    public void onDisable()
    {    	
    	_config.Save();
    	
    	_logger.SendToConsole(Module.CoreManager, MessageType.Offline,_config.Messages.ConsoleIO.Off);
    }
}
//-----------------------------------------------------------------------------------------------//
///////////////////////////////////////////////////////////////////////////////////////////////////