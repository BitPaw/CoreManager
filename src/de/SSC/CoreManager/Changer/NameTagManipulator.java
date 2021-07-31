package de.SSC.CoreManager.Changer;
/*
import com.bringholm.nametagchanger.NameTagChanger;
import de.SSC.CoreManager.Utility.CoreManagerPlayer;
import org.bukkit.entity.Player;
*/

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import de.SSC.CoreManager.DataBase.DataTypes.CMPlayer;
import de.SSC.CoreManager.DataBase.DataTypes.CMPlayerList;
import de.SSC.CoreManager.DataBase.DataTypes.CMRank;
import de.SSC.CoreManager.DataBase.DataTypes.CMRankList;
import de.SSC.CoreManager.Messages.Logger;
import de.SSC.CoreManager.Messages.MessageType;
import de.SSC.CoreManager.Messages.Module;
import de.SSC.CoreManager.Utility.BukkitUtility;

public class NameTagManipulator
{
    private static NameTagManipulator _instance;

	// Variables
    private  boolean _hasChanged ;

    private Scoreboard _scoreboard;
    private BukkitUtility _bukkitUtillity;
    private Logger _logger;
    
    private  NameTagManipulator ()
    {        
        _logger = Logger.Instance();
        _bukkitUtillity = BukkitUtility.Instance();

        _hasChanged = true;    
    }
    
    public void Init()
    {    
    	
    	try
    {       	
    	_scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        ResetTeams();
        //RegisterHealthBar();
    }
    catch(Exception e)
    {
    	String message = "&cError while creating NameTagManipulator! " +  e.getMessage();
    	
    	_logger.SendToConsole(Module.System, MessageType.Error, message);
    }
    	
    }

    private void UpdateTags()
    {
        try
        {           
            CMRankList cmRankList =  CMRankList.Instance();

            for (CMRank cmRank :  cmRankList.ListAllRanks())
            {
                RegisterNameTag(cmRank.RankName, cmRank.ColorTag);              
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
    	if(_scoreboard == null)
    	{
    		_logger.SendToConsole(Module.NameTagManipulator, MessageType.Error, "NameTagManipulator.RegisterNameTag -> Scoreboard is null!");
    	}
    	else
    	{
            if (_scoreboard.getTeam(tagName) != null)
            {
                _scoreboard.getTeam(tagName).unregister();
            }
            Team team = _scoreboard.registerNewTeam(tagName);
            team.setPrefix(_logger.TransformToColor(tag));
    	} 	
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
    @SuppressWarnings("deprecation")
	public  void UpdatePlayerTag(CommandSender sender)
    {
        Player player;
        CMPlayerList cmPlayerList;
        CMPlayer cmPlayer;
        String rankName;
        Team team;

        
        if(_bukkitUtillity.IsSenderPlayer(sender))
        {
            player = (Player) sender;
            cmPlayerList = CMPlayerList.Instance();
            cmPlayer = cmPlayerList.GetPlayer(player);
        	
            if(_hasChanged)
            {
                UpdateTags();
            }  

            if(cmPlayer == null)
            {
                throw new NullPointerException("The value coreManagerPlayer is null in NameTagManipulator.UpdatePlayerTag()");
            }
            else
            {
            	try
            	{
            		
           	     if(cmPlayer.RankGroup == null)
           	     {
           	    	 throw new Exception("cmPlayer.RankGroup is missing");
           	     }
           	     
           	     if(cmPlayer.RankGroup.ColorTag == null)
           	     {
           	    	 throw new Exception("cmPlayer.RankGroup.ColorTag is missing");
           	     }
           	     
           	     if(cmPlayer.RankGroup.PlayerColor == null)
           	     {
           	    	 throw new Exception("mPlayer.RankGroup.PlayerColor is missing");
           	     }
            		
            		
           	  team = _scoreboard.getTeam(cmPlayer.RankGroup.RankName);
            	     
            	     if(team == null)
            	     {
            	    	 throw new Exception("team is missing");
            	     }
            	     


                     // Rank
                     rankName = "&7[" + cmPlayer.RankGroup.ColorTag + "&7]" + cmPlayer.RankGroup.PlayerColor;
                     team.setPrefix(_logger.TransformToColor(rankName + " "));

                     // UserName
                     team.setDisplayName(_logger.TransformToColor(player.getDisplayName()));

                     team.addPlayer(player);
            	}
            	catch(Exception e)
            	{
            		_logger.SendToConsole(Module.NameTagManipulator, MessageType.Error, "NameTagManipulatorUpdatePlayerTag() set team Error. " + e.getMessage()); 
            	}           
            }

           // player.setScoreboard(_scoreboard);
        }
    }

	public static NameTagManipulator Instance()	
	{
        if(_instance == null)
        {
        	_instance = new NameTagManipulator();
        }

		return _instance ;
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
