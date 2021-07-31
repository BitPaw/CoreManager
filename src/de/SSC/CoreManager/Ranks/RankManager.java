package de.SSC.CoreManager.Ranks;

import de.SSC.CoreManager.Config.Messages;
import de.SSC.CoreManager.CoreController;
import de.SSC.CoreManager.Main;
import de.SSC.CoreManager.Players.CoreManagerPlayer;
import de.SSC.CoreManager.Players.PlayerManager;
import de.SSC.CoreManagerPlugins.BukkitHook;
import de.SSC.CoreManagerPlugins.DataBaseController;
import de.SSC.CoreManagerPlugins.Messager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RankManager
{
    // Variables
    public List<RankCredentials> _ranks ;
    public RankCredentials DefaultRank;
    private boolean _hasChanged;

    // Referenzes
    private DataBaseController _dataBaseController;
    private BukkitHook _bukkitHook;
    private PlayerManager _playerManager;
    private Messager _messager;
    private Messages _messages;

    public RankManager()
    {
        DefaultRank = null;
        _hasChanged = true;
    }

    public void ReloadRanks()
    {
        if(_hasChanged)
        {
            _ranks = new ArrayList<>();

            CoreController coreController = Main.coreController;

            _dataBaseController = coreController._DataBaseController;
            _bukkitHook = coreController._BukkitHook;
            _messager = coreController._Messager;
            _messages = Main.messages;
            _playerManager = coreController._PlayerManager;

            try
            {
                _ranks = _dataBaseController.GetRanks();
            }
            catch (Exception e)
            {
                _bukkitHook.SendConsolMessage("&cError while reloading tha ranks.\n" + e.getMessage());
            }

            // DefaultRank
            for(RankCredentials rankCredentials : _ranks)
            {
                if(rankCredentials.IsDefault)
                {
                    DefaultRank = rankCredentials;
                }
            }

            _hasChanged = false;
        }
    }

    public RankCredentials GetRankPerName(String rankName)
    {

            ReloadRanks();


        RankCredentials returnValue = null;

        for (RankCredentials rankCredential : _ranks          )
        {
            if(rankName.equalsIgnoreCase(rankCredential.RankName))
            {
                returnValue =  rankCredential;
            }
        }

        return  returnValue;
    }

    public void CreateRank(CommandSender sender, String[] parameter)
    {

    }

    public void DeleteRank(CommandSender sender, String[] parameter)
    {

    }

    public void ChangeRankCommand(CommandSender sender, String[] parameter)
    {

            ReloadRanks();


        if(_messager == null) _messager = new Messager();
        if(_bukkitHook == null)
        {
            _bukkitHook = Main.coreController._BukkitHook;
        }

        if(_messager == null) throw new NullPointerException("Value <_messager> is null in PermissionManager.ChangeRank()");
        if(parameter == null) throw new NullPointerException("Value <parameter> is null in PermissionManager.ChangeRank()");

        String replyMessage = "";

        try
        {
            int parameterSize = parameter.length;

            switch (parameterSize)
            {
                case 0 :
                    replyMessage = _messages.PermissionSystem + " " +  _messages.ChangeRankToLessParameters;
                    break;
                case 1 :
                    if(_bukkitHook.CheckIfCommandSenderIsPlayer(sender))
                    {
                        String rankString = parameter[0];
                        RankCredentials rank = GetRankPerName(rankString);

                        if(rank == null)
                        {
                            replyMessage = "&cUnkown rank!";
                        }
                        else
                        {
                            Player player = (Player)sender;
                            CoreManagerPlayer coreManagerPlayer = _playerManager.GetUserPerUUID(player);

                            replyMessage = _messages.PermissionSystem + " " + _messages.ChangeRankPlayerChanged;
                            replyMessage = replyMessage.replace(_messages.TagOldRank,coreManagerPlayer.GroupRank.RankName);
                            replyMessage = replyMessage.replace(_messages.TagRank, rankString);

                            coreManagerPlayer.GroupRank = rank;

                            try
                            {
                                _dataBaseController.UpdatePlayerRank(coreManagerPlayer);
                            }
                            catch (Exception e)
                            {
                                _messager.MessageToSender(sender,"&cError while Updating PlayerRank!\n" + e.getMessage());
                            }
                        }
                    }
                    break;
                case 2 :
                    String rankString = parameter[0];
                    String targetPlayerString = parameter[1];
                    RankCredentials rank = GetRankPerName(rankString);
                    Player targetPlayer = _bukkitHook.FindPlayer(targetPlayerString);

                    if(targetPlayer == null)
                    {
                        replyMessage = _messages.PermissionSystem + " " + _messages.ChangeRankPlayerNotFound;
                        replyMessage = replyMessage.replace(_messages.TagPlayer, targetPlayerString);
                    }
                    else
                    {
                        CoreManagerPlayer coreManagerPlayer = _playerManager.LoadPlayer(targetPlayer);

                        replyMessage = _messages.PermissionSystem + " " + _messages.ChangeRankOtherPlayerChanged;
                        replyMessage = replyMessage.replace(_messages.TagPlayer, targetPlayerString);
                        replyMessage = replyMessage.replace(_messages.TagOldRank, coreManagerPlayer.GroupRank.RankName);
                        replyMessage = replyMessage.replace(_messages.TagRank, rankString);

                        coreManagerPlayer.GroupRank = rank;

                        _dataBaseController.UpdatePlayerRank(coreManagerPlayer);
                    }

                    break;
                default:
                    replyMessage = _messages.ChangeRankToManyParameters;

            }
        }
        catch(Exception e)
        {
            replyMessage = "&cThere was an error while executing PermissionManager.ChangeRank().\n" + e.getMessage();
        }


        _messager.MessageToSender(sender, replyMessage);

    }

    public void ResetPlayerRank(CommandSender sender)
    {
        if(_bukkitHook.CheckIfCommandSenderIsPlayer(sender))
        {
            Player player = (Player)sender;

            CoreManagerPlayer coreManagerPlayer = _playerManager.GetUserPerUUID(player);
            RankCredentials rank = DefaultRank;

            _bukkitHook.SendMessage(player, "&cRank reseted from <&e" + coreManagerPlayer.GroupRank.RankName + "&c> to <&e" + rank.RankName + "&c>");
            coreManagerPlayer.GroupRank = rank;
        }
    }

    public void PrintAllRanks(CommandSender sender)
    {
        if(_hasChanged)
        {
            ReloadRanks();
        }

        String message = "=====] Ranks [=====\n";

        for(RankCredentials rankCredentials : _ranks)
        {
            message += " " + rankCredentials.RankName + " &r" + rankCredentials.ColorCode + "\n&r";
        }

        message += "===================";

        _messager.MessageToSender(sender, message);
    }

    public List<String> GetRankNames()
    {
        List<String> ranks = new ArrayList<>();

        for(RankCredentials rankCredentials : _ranks)
        {
            ranks.add(rankCredentials.RankName);
        }

        return  ranks;
    }
}