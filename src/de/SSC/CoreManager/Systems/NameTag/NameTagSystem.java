package de.SSC.CoreManager.Systems.NameTag;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import de.SSC.CoreManager.Systems.Chat.Logger;
import de.SSC.CoreManager.Systems.Chat.MessageType;
import de.SSC.CoreManager.Systems.Chat.Module;
import de.SSC.CoreManager.Systems.Player.CMPlayer;
import de.SSC.CoreManager.Systems.Player.PlayerSystem;
import de.SSC.CoreManager.Systems.Rank.CMRank;
import de.SSC.CoreManager.Systems.Rank.RankSystem;
import de.SSC.CoreManager.Utility.BukkitUtility;

public class NameTagSystem
{
	private static NameTagSystem _instance;
	private boolean _hasChanged;
	private Scoreboard _scoreboard;

	private BukkitUtility _bukkitUtility;
	private Logger _logger;
	private RankSystem _rankSystem;
	private PlayerSystem _playerSystem;

	private  NameTagSystem()
	{
		_instance = this;
		
		_logger = Logger.Instance();
		_bukkitUtility = BukkitUtility.Instance();
		_rankSystem = RankSystem.Instance();
		_playerSystem = PlayerSystem.Instance();

		_hasChanged = true;
	}

	public static NameTagSystem Instance()
	{
		return _instance == null ? new NameTagSystem() : _instance;
	}

	public void Init()
	{
		try
		{
			_scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
			ResetTeams();
			//RegisterHealthBar();
		}
		catch (Exception e)
		{
			String message = "&cError while creating NameTagManipulator! " + e.getMessage();

			_logger.SendToConsole(Module.System, MessageType.Error, message);
		}

	}

	private void UpdateTags()
	{
		try
		{
			for (CMRank cmRank : _rankSystem.ListAllRanks())
			{
				RegisterNameTag(cmRank.RankName, cmRank.ColorTag);
			}

			_hasChanged = false;
		}
		catch (Exception e)
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
		if (_scoreboard == null)
		{
			_logger.SendToConsole(Module.NameTagSystem, MessageType.Error, "NameTagManipulator.RegisterNameTag -> Scoreboard is null!");
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
		CMPlayer cmPlayer;
		//String rankName;
		Team team;

		if (_bukkitUtility.PlayerUtility.IsSenderPlayer(sender))
		{
			player = (Player)sender;
			cmPlayer = _playerSystem.GetPlayer(player);

			if (_hasChanged)
			{
				UpdateTags();
			}

			if (cmPlayer == null)
			{
				throw new NullPointerException("The value coreManagerPlayer is null in NameTagManipulator.UpdatePlayerTag()");
			}
			else
			{
				try
				{
					if (cmPlayer.RankGroup == null)
					{
						throw new Exception("cmPlayer.RankGroup is missing");
					}

					if (cmPlayer.RankGroup.ColorTag == null)
					{
						throw new Exception("cmPlayer.RankGroup.ColorTag is missing");
					}

					if (cmPlayer.RankGroup.PlayerColor == null)
					{
						throw new Exception("cmPlayer.RankGroup.PlayerColor is missing");
					}


					team = _scoreboard.getTeam(cmPlayer.RankGroup.RankName);

					if (team == null)
					{
						throw new Exception("team is missing");
					}

					String nameTag = "&7[" + cmPlayer.RankGroup.ColorTag + "&7] ";
					
					team.setPrefix(_logger.TransformToColor(_logger.TransformToColor(nameTag)));
					
					team.setDisplayName(cmPlayer.GetPlayerCustomName());
					
					team.setColor(_bukkitUtility.GetColorCode(cmPlayer.RankGroup.PlayerColor));
					
					_logger.SendToConsole(Module.NameTagSystem, MessageType.Info, "Setting NameTag : " + nameTag);
					
					team.addPlayer(player);
				}
				catch (Exception e)
				{
					_logger.SendToConsole(Module.NameTagSystem, MessageType.Error, "NameTagManipulatorUpdatePlayerTag() set team Error. " + e.getMessage());
				}
			}

			 player.setScoreboard(_scoreboard);
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
