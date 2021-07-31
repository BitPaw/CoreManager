package de.SSC.CoreManager.Multiverse;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.CoreController;
import de.SSC.CoreManager.Main;
import de.SSC.CoreManager.Utility.CoreManagerPlugin;
import de.SSC.CoreManagerPlugins.BukkitHook;
import de.SSC.CoreManagerPlugins.DataBaseController;
import de.SSC.CoreManagerPlugins.Messager;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WorldManager extends CoreManagerPlugin
{
   public List<CoreManagerWorld> Worlds;

    // Referenzes
    private BukkitHook _bukkitHook;
    private Config _config ;
    private Messager _messager;

    public WorldManager()
    {
        Worlds = new ArrayList<>();

        CoreController coreController = Main.coreController;

        _bukkitHook = coreController._BukkitHook;
        _config = Main.config;
        _messager = coreController._Messager;

        _hasChanged = true;
    }

    public void UpdateWorlds()
    {
        if(_messager == null) _messager = Main.coreController._Messager;
        if(_bukkitHook == null) _bukkitHook = Main.coreController._BukkitHook;
        if(_config == null) _config = Main.config;

        if(_hasChanged)
        {
            _hasChanged = false;
            Worlds = LoadAllWorlds();
        }
    }

    public void CreateNewWorldCommand(CommandSender commandSender, String[] parameter)
    {

    }

    public void DeleteWorldCommand(CommandSender commandSender, String[] parameter)
    {

    }

    public World CreateNewWorld(WorldCredentials worldCredentials)
    {
        UpdateWorlds();

        if(_messager == null) throw new NullPointerException("Messager is null in WorldManager.CreateNewWorld()!");
        if(_bukkitHook == null) throw new NullPointerException("BukkitHook is null in WorldManager.CreateNewWorld()!");

        boolean generateStructures = true;
        World world = null;

        try
        {
            _messager.MessageToConsole("&bLoading world : &3" + worldCredentials.Name);

            WorldCreator worldCreator;

            worldCreator = new WorldCreator(_config.WorldsFolder + "\\" + worldCredentials.Name);

            worldCreator.generateStructures(generateStructures);
            worldCreator.seed(worldCredentials.Seed);
            worldCreator.type(worldCredentials.MapType);

            world = _bukkitHook.server.createWorld(worldCreator);

            world.setPVP(worldCredentials.PvP);
            world.setKeepSpawnInMemory(worldCredentials.KeepInventory);
        }
        catch(Exception e)
        {
            _bukkitHook.SendConsolMessage("Error while Crating/Loading a world in WorldManager.CreateWorld");
        }

        return world;
    }

    public void DeleteWorld()
    {

    }

    public List<CoreManagerWorld> LoadAllWorlds()
    {
        UpdateWorlds();
        if(_config == null) throw  new  NullPointerException("In WorldManager.LoadAllMaps Config id null");

        List<CoreManagerWorld> coreManagerWorlds = null;
        String path = "";
        File file;
        String[] names;

        try
        {
            coreManagerWorlds = LoadAllWorldsFromDatabase();

            path = new java.io.File(".").getPath();

            file = new File(path + "\\" + _config.WorldsFolder);

            names = file.list();

            if(names != null && coreManagerWorlds != null)
            {
                _bukkitHook.SendConsolMessage("&aFound &7<&2" + names.length + "&7> &aWorlds in the Folder.\n" +
                                           "\t\t       &7<&2" + coreManagerWorlds.size() + "&7> &aWorlds in DataBase.");



                for (CoreManagerWorld coreManagerWorld : coreManagerWorlds)
                {

                        coreManagerWorld.BukkitWorld = CreateNewWorld(coreManagerWorld.Credentials);
                        Worlds.add(coreManagerWorld);
                }

                for(String name : names)
                {
                    String folderPath = file.getPath() + "\\"+ name;

                    if (new File(folderPath).isDirectory())
                    {

                      if(GetWorld(name) == null)
                      {
                          WorldCredentials worldCredentials = new WorldCredentials();
                          CoreManagerWorld coreManagerWorld = new CoreManagerWorld(worldCredentials);

                          folderPath = folderPath.replace(".\\", "");

                          coreManagerWorld.FolderPath = folderPath ;

                          Worlds.add(coreManagerWorld);
                      }
                    }
                }

            }
        }
        catch(Exception e)
        {
            _bukkitHook.SendConsolMessage("&cError in WorldManager.LoadAllMaps\n" + e.getMessage());
            e.printStackTrace();
        }

        return Worlds;
    }

    private List<CoreManagerWorld> LoadAllWorldsFromDatabase()
    {
        UpdateWorlds();

        DataBaseController dataBaseController = Main.coreController._DataBaseController;
        List<CoreManagerWorld> coreManagerWorlds = dataBaseController.LoadAllMaps();

        String message = "\n";

        message += "&3------------------\n";
        message += "&b Found worlds in database\n";
        message += "&3------------------\n";

        for(CoreManagerWorld coreManagerWorld : coreManagerWorlds)
        {
            message += "&b " + coreManagerWorld.Credentials.Name + "&r as " + coreManagerWorld.Credentials.CustomName + "&r Type " + coreManagerWorld.Credentials.MapType +  "\n";
        }

        message += "&3------------------";

        _messager.MessageToConsole(message);

        return coreManagerWorlds;
    }

    public CoreManagerWorld GetWorld(String worldName)
    {
        UpdateWorlds();

        CoreManagerWorld coreManagerWorld = null;
        worldName = Main.config.WorldsFolder + "\\" + worldName;

        _messager.MessageToConsole("&eSearching for world &7<&6" + worldName + "&7>&e.");

        for(CoreManagerWorld cmWorld: Worlds)
        {
            String currentWorldName = cmWorld.FolderPath.toLowerCase();
            String target = worldName.toLowerCase();

            if(currentWorldName.equalsIgnoreCase(target))
            {
                coreManagerWorld = cmWorld;
            }
        }

        return coreManagerWorld;
    }

    public CoreManagerWorld GetWorld(World world)
    {
        UpdateWorlds();

        CoreManagerWorld coreManagerWorld = null;

        _messager.MessageToConsole("&eSearching for world &7<&6" + world.getName() + "&7>&e.");

        for(CoreManagerWorld cmWorld: Worlds)
        {
            if(cmWorld.FolderPath.equalsIgnoreCase(world.getName()))
            {
                coreManagerWorld = cmWorld;
            }
        }

        return coreManagerWorld;
    }

    public void ListWorlds(CommandSender commandSender)
    {
        UpdateWorlds();

        String message = "&5========[&dWorlds]&5========\n";

        if(Worlds.size() <= 0)
        {
            message += "\n No loaded Worlds?\n";
        }
        else
        {
            for(CoreManagerWorld coreManagerWorld : Worlds)
            {
                if(coreManagerWorld.Credentials != null)
                {
                    if(coreManagerWorld.IsLoaded())
                    {
                        message += " &5Name : &d" + coreManagerWorld.Credentials.Name + " &5Path : &d" + coreManagerWorld.FolderPath + " &ras " + coreManagerWorld.Credentials.CustomName + "&a Loaded"+ "\n";
                    }
                    else
                    {
                        message += " &9Name : &7" + coreManagerWorld.Credentials.Name + " &8Path : &7" + coreManagerWorld.FolderPath + " &8as &7" + coreManagerWorld.Credentials.CustomName + "&c Unloaded"+ "\n";
                    }
                }
                else
                {
                    message += " &8Name : UnkownWorld\n";
                }
            }
        }

        message += "&5=========================";

        message = message.replace("NORMAL", "&aNormal");
        message = message.replace("NETHER", "&cNether");
        message = message.replace("THE_END", "&aEnd");

        _messager.MessageToSender(commandSender, message);
    }
}