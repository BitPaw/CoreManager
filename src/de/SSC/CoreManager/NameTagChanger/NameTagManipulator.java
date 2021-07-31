package de.SSC.CoreManager.NameTagChanger;
/*
import com.bringholm.nametagchanger.NameTagChanger;
import de.SSC.CoreManager.Utility.CoreManagerPlayer;
import org.bukkit.entity.Player;
*/

import de.SSC.CoreManager.CoreController;
import de.SSC.CoreManager.Main;
import de.SSC.CoreManager.Ranks.RankCredentials;
import de.SSC.CoreManager.Ranks.RankManager;
import de.SSC.CoreManager.Players.CoreManagerPlayer;
import de.SSC.CoreManager.Players.PlayerManager;
import de.SSC.CoreManagerPlugins.BukkitHook;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NameTagManipulator
{

    // Variables
    private  boolean _hasChanged ;
    private  List<Team> _tags;

 // Referenzes
    private Scoreboard _scoreboard;
    private BukkitHook _bukkitHook;
    private RankManager _rankManager;
    private PlayerManager _playerManager;

    public  NameTagManipulator ()
    {
        _scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        CheckReferenzes();
        _hasChanged = true;

        try
        {
            ResetTeams();
            //RegisterHealthBar();
        }
        catch(Exception e)
        {
            _bukkitHook.SendConsolMessage("&cEr  ror while creating NameTagManipulator! " +  e.getMessage());
        }


    }

    private void CheckReferenzes()
    {
        CoreController coreController = Main.coreController;

        _bukkitHook = coreController._BukkitHook;
        _rankManager = coreController._RankManager;
        _playerManager = coreController._PlayerManager;
    }

    private void UpdateTags()
    {
        try
        {
            _tags = new ArrayList<>();
            CheckReferenzes();

            List<RankCredentials>  rankCredentials = _rankManager._ranks;

            for (RankCredentials rankCredentials1 :   rankCredentials)
            {
                RegisterNameTag(rankCredentials1.RankName, rankCredentials1.ColorCode);
                _bukkitHook.SendConsolMessage("&5[&dRank&5] &3" + rankCredentials1.RankName + " as " + rankCredentials1.ColorCode);
            }

            _hasChanged = false;
        }
        catch(Exception e)
        {
            throw new RuntimeException("Something went wrong while Updating PlayerTags!\n" + e.getMessage());
        }
    }

    public void RegisterHealthBar()
    {
        if (_scoreboard.getObjective("health") != null)
        {
            _scoreboard.getObjective("health").unregister();
        }
        //Objective o = _scoreboard.registerNewObjective("health", "health");
       // o.setDisplayName(ChatColor.RED + "?");
        //o.setDisplaySlot(DisplaySlot.BELOW_NAME);
    }

    private void ResetTeams()
    {
        Set<Team> teams = _scoreboard.getTeams();

        for (Team team : teams)
        {
            team.unregister();
        }
    }

    private void RegisterNameTag(String tagName, String tag)
    {
        Set<Team> teams = null;

        if (_scoreboard.getTeam(tagName) != null)
        {
            _scoreboard.getTeam(tagName).unregister();
        }
        Team team = _scoreboard.registerNewTeam(tagName);
        team.setPrefix(_bukkitHook.TransformColor(tag));
    }
/*
    public void SetPlayerNameTag(CommandSender sender)
    {
       if(_bukkitHook.CheckIfCommandSenderIsPlayer(sender))
       {
           Player player = (Player) sender;
           String oldName = player.getName();

           _scoreboard.getTeam(teamName).addPlayer(player);
       }
    }
*/
    public  void UpdatePlayerTag(CommandSender sender)
    {
        CheckReferenzes();

        if(_bukkitHook.CheckIfCommandSenderIsPlayer(sender))
        {
            if(_hasChanged)
            {
                UpdateTags();
            }

            Player player = (Player) sender;
            CoreManagerPlayer coreManagerPlayer = _playerManager.GetUserPerUUID(player);

            if(coreManagerPlayer == null)
            {
                throw new NullPointerException("The value coreManagerPlayer is null in NameTagManipulator.UpdatePlayerTag()");
            }
            else
            {
                Team team = _scoreboard.getTeam(coreManagerPlayer.GroupRank.RankName);

                // Rank
                String rankName = "&7[" + coreManagerPlayer.GroupRank.ColorCode + "&7]" + coreManagerPlayer.GroupRank.PlayerColor;
                team.setPrefix(_bukkitHook.TransformColor(rankName + " "));

                // UserName
                team.setDisplayName(_bukkitHook.TransformColor(player.getCustomName()));

                team.addPlayer(player);
            }

           // player.setScoreboard(_scoreboard);
        }
    }

/*
    public NameTagManipulator()
    {
        _nameTagChanger = NameTagChanger.INSTANCE;

        //_nameTagChanger.setPlugin(Main.Plugin);
    }

    public void ChangeNameTag(Player player)
    {
        CoreManagerPlayer coreManagerPlayer = CoreManagerPlayer.TranslateFromPlayer(player);

        String nameTag = coreManagerPlayer.GroupRank.toString() + coreManagerPlayer.BukkitPlayer.getCustomName();

        _nameTagChanger.changePlayerName(player, nameTag);
    }

    public void ResetNameTag(Player player)
    {
        _nameTagChanger.resetPlayerName(player);
    }

    public void GetTagName(Player player)
    {
       // NameTagChanger.INSTANCE.(player);
    }
    */
}
