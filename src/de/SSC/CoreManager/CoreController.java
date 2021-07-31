package de.SSC.CoreManager;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Config.Messages;
import de.SSC.CoreManager.Editor.PlayerEditor;
import de.SSC.CoreManager.Essentials.Chat.NameChanger;
import de.SSC.CoreManager.Multiverse.WorldManager;
import de.SSC.CoreManager.Ranks.RankManager;
import de.SSC.CoreManager.Players.PlayerManager;
import de.SSC.CoreManager.SignEdit.EditableSign;
import de.SSC.CoreManager.SignEdit.EditableSignEventListener;
import de.SSC.CoreManager.Utility.CoreManagerPlugin;
import de.SSC.CoreManagerBukkitRunnables.PingTabList;
import de.SSC.CoreManagerPlugins.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CoreController
{
    private static CoreController _instance = null;
    public boolean Enable = false;

    public static JavaPlugin plugin;
    public static PluginManager pluginManager;

    List<CoreManagerPlugin> _plugins;

    // III - Core
    public BukkitHook _BukkitHook;
    public CoreListener _CoreListener;

    // II - Importand
    public WorldManager _WorldManager;
    public ChatManager _ChatManager;
    public DataBaseController _DataBaseController;
    public TeleportationManager _TeleportationManager;
    public PermissionManager _PermissionManager;
    public Messager _Messager;
    public PlayerManager _PlayerManager;

    // I - Extra
    public PingTabList _PingTabList;
    public SignEdit _SignEdit;
    public NameChanger _NameChanger;
    public PlayerEditor _PlayerEditor;
    public EditableSignEventListener _EditableSignEventListener;
    public RankManager _RankManager;

    // Referenzes
    private Config _config;
    private Messages _messages;

    public CoreController()
    {
        _instance = this;
        _plugins = new ArrayList<CoreManagerPlugin>();
        plugin = Main.Plugin;
        pluginManager = Bukkit.getPluginManager();

        _config = Main.config;
        _messages = Main.messages;
        _EditableSignEventListener = new EditableSignEventListener(plugin);
        _RankManager = new RankManager();
        _PlayerManager = new PlayerManager();
    }

    private void RegisterEvents()
    {
        if(plugin == null) throw new NullPointerException("Plugin in function RegisterEvents was null");
        if(pluginManager == null) throw new NullPointerException("PluginManager in function RegisterEvents was null");
        if(_BukkitHook == null) throw new NullPointerException("BukkitHook in function RegisterEvents was null");
        if(_WorldManager == null) throw new NullPointerException("WorldManager in function RegisterEvents was null");
        if(_messages == null) throw new NullPointerException("Messages in function RegisterEvents was null");
        if(_config == null) throw new NullPointerException("Config in function RegisterEvents was null");
        if(_CoreListener == null) throw new NullPointerException("CoreListener in function RegisterEvents was null");

        try
        {
            pluginManager.registerEvents(_CoreListener, plugin);
        }
        catch(Exception e)
        {
            _BukkitHook.SendConsolMessage("&4Error while registering evnents for CoreListener");
        }

        try
        {
            _PingTabList = new PingTabList();
            _PingTabList.runTaskTimerAsynchronously(plugin, 0, _config.PingTabListDelayMs);
        }
        catch (Exception e)
        {
            _BukkitHook.SendConsolMessage(_messages.CoreManager + _messages.Error + _messages.Shutdown);
        }

        try
        {
            //pluginManager.registerEvents(_EditableSignEventListener, plugin);
            EditableSign  editableSign = new EditableSign(plugin);
        }
        catch (Exception e)
        {
            _BukkitHook.SendConsolMessage(_messages.CoreManager + _messages.Error + " EditableSignEventListener threw an error!\nMessage : " + e.getMessage());
        }




        try
        {
            _WorldManager.UpdateWorlds();
        }
        catch(Exception e)
        {
            _BukkitHook.SendConsolMessage(_messages.CoreManager + _messages.Error + " Error while loading worlds");
        }






        _BukkitHook.SendConsolMessage(_messages.CoreManager + _messages.On);

    }

    public  void Initialize()
    {


        try        {            _BukkitHook = new BukkitHook();        }
        catch (Exception e)  {            ErrorWhileInitMessage("BukkitHook", e);        }

        try        {            _CoreListener = new CoreListener();        }
        catch (Exception e)  {            ErrorWhileInitMessage("CoreListener", e);        }

        try        {            _WorldManager = new WorldManager(); }
        catch (Exception e)  {            ErrorWhileInitMessage("WorldManager", e);        }

        try        {            _ChatManager = new ChatManager(); }
        catch (Exception e)  {            ErrorWhileInitMessage("ChatManager", e);        }

        try        {            _DataBaseController = new DataBaseController();                 }
        catch (Exception e)  {            ErrorWhileInitMessage("DataBaseController", e);        }

        try                  {            _TeleportationManager = new TeleportationManager();        }
        catch (Exception e)  {            ErrorWhileInitMessage("TeleportationManager", e);        }

        try                  { _PermissionManager = new PermissionManager(); }
        catch (Exception e)  { ErrorWhileInitMessage("PermissionManager", e);}

        try                  { _Messager = new Messager(); }
        catch (Exception e)  { ErrorWhileInitMessage("Messager", e);}

        try                  { _PlayerEditor = new PlayerEditor(); }
        catch (Exception e)  { ErrorWhileInitMessage("PlayerEditor", e);}

        try                  { _NameChanger = new NameChanger(); }
        catch (Exception e)  { ErrorWhileInitMessage("NameChanger", e);}

        try                  { _SignEdit = new SignEdit(); }
        catch (Exception e)  { ErrorWhileInitMessage("SignEdit", e);}


        // Load Array
        {
            _plugins.add(_BukkitHook);
            _plugins.add(_CoreListener);
            _plugins.add(_WorldManager);
            _plugins.add(_ChatManager);
            _plugins.add(_PlayerEditor);
            _plugins.add(_DataBaseController);
            _plugins.add(_TeleportationManager);
            _plugins.add(_PermissionManager);
            _plugins.add(_Messager);
            // _plugins.add(_PingTabList);
            // _plugins.add(_SignEdit);
            //_plugins.add(_NameChanger);
        }


        /*
        try
        {
            _SignEdit = SignEdit.GetInstance();
            _SignEdit.Enable();
        }
        catch (Exception e)
        {
            ErrorWhileInitMessage(_SignEdit.toString(), e);
        }

        try
        {
            _NameChanger = NameChanger.GetInstance();
            _NameChanger.Enable();
        }
        catch (Exception e)
        {
            ErrorWhileInitMessage(_NameChanger.toString(), e);
        }
        */





        try
        {
            ListAllPlugins(null);

        }
        catch(Exception e)
        {
            System.out.println("\n\n\n\nError in ListAllPlugins\n" + e.getMessage() + "\n\n");
        }

        try
        {

            RegisterEvents();
        }
        catch(Exception e)
        {
            System.out.println("\n\n\n\nError in RegisterEvents\n" + e.getMessage() + "\n\n");
        }

    }


    public static CoreController GetInstance ()
    {
        return _instance == null ? new CoreController () : _instance;
    }

    private void ErrorWhileInitMessage(String pluginName, Exception errorMessage )
    {
        System.out.println(Color.red + _messages.System + _messages.Error + "\n" +
                "----------------------------------------------------------\n" +
                " There was an error while enabling the plugin <" + pluginName + ">\n" +
                " This plugin will be disabled...\n" +
                " Error message : " +errorMessage+ "\n" +
                "----------------------------------------------------------");
    }

    public void ListAllPlugins(CommandSender sender)
    {
        if( _BukkitHook != null)
        {
            String message = "\n";
            message += "&6=============================================================================\n";
            message += "&e CoreManager PluginList                   Plugins Loaded <" + _plugins.size()+ "/" +"X" + ">  \n";
            message += "&6=============================================================================";

            for (CoreManagerPlugin plugins :  _plugins  )
            {
                String modeAddition = "";

                if(plugins != null)
                {
                    plugins.Enable();

                    switch (plugins.State)
                    {
                        case Online: modeAddition = _messages.On; break;
                        case Offline: modeAddition = _messages.Off;break;
                        case Error: modeAddition = _messages.Err;break;
                        case Initialized: modeAddition = _messages.On;break;
                        case Null: modeAddition = _messages.Null; break;
                    }
                }
                else
                {
                    modeAddition = _messages.Null;
                }

                message += "\n\t" + modeAddition + " &r" + plugins;
            }

            message += "\n&6=============================================================================";

            if(sender == null)
            {
                _BukkitHook.SendConsolMessage(message);
            }
            else
            {
                _BukkitHook.SendMessage(sender, message);
            }
        }
    }


}
