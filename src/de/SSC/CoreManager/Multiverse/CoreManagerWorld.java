package de.SSC.CoreManager.Multiverse;

import de.SSC.CoreManager.Main;
import org.bukkit.World;

public class CoreManagerWorld
{
    public World BukkitWorld;
    public String FolderPath;
    public WorldCredentials Credentials;

    public boolean IsLoaded()
    {
        return BukkitWorld != null;
    }

    public CoreManagerWorld(WorldCredentials worldCredentials)
    {
        Credentials = worldCredentials;
        FolderPath =  Main.config.WorldsFolder + "\\" + Credentials.Name;
    }
}
