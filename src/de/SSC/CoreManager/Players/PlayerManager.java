package de.SSC.CoreManager.Players;

import de.SSC.CoreManager.Main;
import de.SSC.CoreManagerPlugins.DataBaseController;
import de.SSC.CoreManagerPlugins.Messager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class PlayerManager
{
    public List<CoreManagerPlayer> Players;

    // Referenzes
    private DataBaseController _dataBaseController;
    private Messager _messager;

    public PlayerManager()
    {
        Players = new ArrayList<>();
        //_messager = Main.coreController._Messager;
    }

    public CoreManagerPlayer LoadPlayer(Player player)
    {
        GetReferenzes();

        CoreManagerPlayer  coreManagerPlayer = null;

        try
        {
            coreManagerPlayer = _dataBaseController.GetPlayerInformation(player);

            if(coreManagerPlayer == null)
            {
                throw new NullPointerException("PlayerManager.LoadPlayer() is null! The databse brought a null value!");
            }
            else
            {
                Players.add(coreManagerPlayer);
            }
        }
        catch (Exception e)
        {
           // _messager = Main.coreController._Messager;

            e.printStackTrace();
        }

        PrintAllPlayers();

       return coreManagerPlayer;

    }

    public void PrintAllPlayers()
    {
        String message = "\n------------------------\n";

        message += " All CoreManagerPlayers\n";
        message += "------------------------\n";

        for(CoreManagerPlayer coreManagerPlayer : Players)
        {
         message += coreManagerPlayer.BukkitPlayer.getName() + "\n";
        }

        message += "------------------------";

        System.out.println(message);
    }

    public  void RemovePlayer(Player player)
    {
        GetReferenzes();

        for(CoreManagerPlayer coreManagerPlayer : Players)
        {
            if(coreManagerPlayer.BukkitPlayer.getUniqueId() == player.getUniqueId())
            {
                Players.remove(coreManagerPlayer);
            }
        }

        PrintAllPlayers();
    }

    public void GetAllUsersFromDatabase()
    {
        GetReferenzes();



    }

    private void CheckPlayerList()
    {
        int coreManagerPlayers = Players.size();
            int bukkitPlayers = Bukkit.getOnlinePlayers().size();

            if(coreManagerPlayers != bukkitPlayers)
            {
                GetMissingPlayers();
            }
    }

    private void GetMissingPlayers()
    {
        Collection<? extends Player> bukkitPlayers = Bukkit.getOnlinePlayers();

        for (Player player : bukkitPlayers)
        {
            //if(Players.contains())
        }
    }

    private void GetReferenzes()
    {
       if(_dataBaseController == null)  _dataBaseController = Main.coreController._DataBaseController;

       if(_dataBaseController == null) throw new NullPointerException("Error PlayerManager.GetReferenzes() DataBaseController is null!");
    }

    public CoreManagerPlayer GetUserPerName(String playerName)
    {
        CoreManagerPlayer returnValue = null;

        for(CoreManagerPlayer coreManagerPlayer : Players)
        {
            if(coreManagerPlayer.BukkitPlayer.getName().equalsIgnoreCase(playerName))
            {
                returnValue = coreManagerPlayer;
            }
        }

        return  returnValue;
    }

    private CoreManagerPlayer GetUserPerUUID(UUID playerUUID)
    {
        CoreManagerPlayer returnValue = null;

        for(CoreManagerPlayer coreManagerPlayer : Players)
        {
            if(coreManagerPlayer.BukkitPlayer.getUniqueId() == playerUUID)
            {
                returnValue = coreManagerPlayer;
            }
        }

        return  returnValue;
    }

    public CoreManagerPlayer GetUserPerUUID(Player player)
    {
        CoreManagerPlayer returnValue = null;
        UUID playerUUID = player.getUniqueId();

        for(CoreManagerPlayer coreManagerPlayer : Players)
        {
            if(coreManagerPlayer.BukkitPlayer.getUniqueId() == playerUUID)
            {
                returnValue = coreManagerPlayer;
            }
        }

        return  returnValue;
    }

}
