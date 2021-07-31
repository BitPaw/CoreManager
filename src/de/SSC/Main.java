///////////////////////////////////////////////////////////////////////////////////////////////////
//-----------------------------------------------------------------------------------------------//
package de.SSC;
//-----------------------------------------------------------------------------------------------//
import de.SSC.CoreManager.CoreListener;
import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Systems.Chat.Logger;
import de.SSC.CoreManager.Systems.Chat.MessageType;
import de.SSC.CoreManager.Systems.Chat.Module;

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
    	_logger = Logger.Instance();    	
    	_config  = Config.Instance();    
    	_coreListener = CoreListener.Instance();
    	_coreListener.SetJavaPlugin(this);
    	
    	_logger.SendToConsole(Module.CoreManager, MessageType.Loading, _config.Messages.ConsoleIO.LoadingFiles);    	
    	
    	_config.Load();
    	
    	_coreListener.LoadAllInstances();       
    	_coreListener.LoadAllReferences();
    	
    	_logger.SendToConsole(Module.CoreManager, MessageType.Info,_config.Messages.ConsoleIO.LoadedFiles);
    }

    @Override
    public void onEnable()
    {
    	_logger.SendToConsole(Module.CoreManager, MessageType.Online,_config.Messages.ConsoleIO.On);
    	
    	_coreListener.RegisterAllEvents(this);
    	
    	
    	_coreListener.LoadAllData();
    	
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