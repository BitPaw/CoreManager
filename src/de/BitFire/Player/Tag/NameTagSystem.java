package de.BitFire.Player.Tag;

import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Core.Exception.SystemNotActiveException;
import de.BitFire.Player.CMPlayer;
import de.BitFire.Player.PlayerSystem;
import de.BitFire.Player.Exception.InvalidPlayerUUID;
import de.BitFire.Player.Exception.PlayerNotFoundException;
import de.BitFire.Rank.CMRank;
import de.BitFire.Rank.RankSystem;

public class NameTagSystem extends BaseSystem implements ISystem
{
	private static NameTagSystem _instance;
	private boolean _hasChanged;
	private Scoreboard _scoreboard;

	private Logger _logger;
	private RankSystem _rankSystem;
	private PlayerSystem _playerSystem;

	private  NameTagSystem()
	{
		super(Module.NameTagSystem, SystemState.Inactive, Priority.Low);    	
		_instance = this;		

		_hasChanged = true;
	}

	public static NameTagSystem Instance()
	{
		return _instance == null ? new NameTagSystem() : _instance;
	}

	@Override
	public void LoadReferences() 
	{		
		_logger = Logger.Instance();
		_rankSystem = RankSystem.Instance();
		_playerSystem = PlayerSystem.Instance();		
	}

	@Override
	public void Reload(final boolean firstRun) throws SystemNotActiveException
	{
		if(!Information.IsActive())
		{
			throw new SystemNotActiveException();
		}
		
		ResetTeams();
	}	
	
	public void SetupScoreBoard()
	{	
		// Warning! Bukkit static functions are not accessible from the main constructor
		_scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();	
	}
	
	private void UpdateTags()
	{
		try
		{
			for (CMRank cmRank : _rankSystem.GetAllRanks())
			{
				RegisterNameTag(cmRank.Name, cmRank.Tag);
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
		if(_scoreboard != null)
		{
			Set<Team> teams = _scoreboard.getTeams();

			for (Team team : teams)
			{
				team.unregister();
			}
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
		UUID playerUUID;
		Team team;
		
		if(_scoreboard == null)
		{
			_logger.SendToConsole(Module.NameTagSystem, MessageType.Error, "_scoreboard is NULL! NameTagSystem.UpdatePlayerTag()");
			return;
		}
		
		if (sender instanceof Player)
		{
			player = (Player)sender;
			playerUUID = player.getUniqueId();
			try 
			{
				cmPlayer = _playerSystem.GetPlayer(playerUUID);
			} 
			catch (PlayerNotFoundException | InvalidPlayerUUID e1) 
			{
				e1.printStackTrace();				
				return;
			}

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
					if (cmPlayer.Information.RankGroup == null)
					{
						throw new Exception("cmPlayer.RankGroup is missing");
					}

					if (cmPlayer.Information.RankGroup.Tag == null)
					{
						throw new Exception("cmPlayer.RankGroup.ColorTag is missing");
					}

					if (cmPlayer.Information.RankGroup.PlayerColor == null)
					{
						throw new Exception("cmPlayer.RankGroup.PlayerColor is missing");
					}


					team = _scoreboard.getTeam(cmPlayer.Information.RankGroup.Name);

					if (team == null)
					{
						throw new Exception("team is missing");
					}

					String nameTag = "&7[" + cmPlayer.Information.RankGroup.Tag + "&7] ";
					
					team.setPrefix(_logger.TransformToColor(_logger.TransformToColor(nameTag)));
					
					team.setDisplayName(cmPlayer.GetPlayerCustomName());
					
					team.setColor(_logger.GetColorCode(cmPlayer.Information.RankGroup.PlayerColor));
					
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
