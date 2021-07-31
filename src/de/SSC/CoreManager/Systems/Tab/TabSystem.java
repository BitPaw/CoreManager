package de.SSC.CoreManager.Systems.Tab;

import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Systems.Chat.Logger;
import de.SSC.CoreManager.Systems.Chat.MessageTags;
import de.SSC.CoreManager.Systems.Chat.MessageType;
import de.SSC.CoreManager.Systems.Chat.Module;
import de.SSC.CoreManager.Systems.Player.CMPlayer;
import de.SSC.CoreManager.Systems.Player.PlayerSystem;
import de.SSC.CoreManager.Utility.BukkitUtility;

public class TabSystem extends BukkitRunnable
{
	private static TabSystem _instance;
	private Config _config;
	private Logger _logger;
	private MessageTags _messageTags;
	private BukkitUtility _bukkitUtility;
	private PlayerSystem _playerSystem;

	private  TabSystem()
	{
		_instance = this;

		_config = Config.Instance();
		_logger = Logger.Instance();
		_messageTags = MessageTags.Instance();
		_bukkitUtility = BukkitUtility.Instance();
		_playerSystem = PlayerSystem.Instance();

		_logger.SendToConsole(Module.TabSystem, MessageType.Online, _config.Messages.ConsoleIO.On);
	}

	public static TabSystem Instance()
	{
		return _instance == null ? new TabSystem() : _instance;
	}

	public void run()
	{		
		List<CMPlayer> cmPlayers = _playerSystem.GetList();
		int ping;
		int currentPlayers = cmPlayers.size();
		String pingTag;
		String pingTabSytax;

		if (currentPlayers <= 0)
		{
			return;
		}

		try
		{
			for (CMPlayer cmPlayer : cmPlayers)
			{
				ping = _bukkitUtility.PlayerUtility.GetPlayerPing(cmPlayer.BukkitPlayer);
				pingTag = SetColorTag(ping);

				pingTabSytax = _config.Chat.PlayerDisplaySyntax + pingTag;

				pingTabSytax = _messageTags.ReplaceOPTag(pingTabSytax, cmPlayer.BukkitPlayer.isOp());
				pingTabSytax = _messageTags.ReplaceRankTag(pingTabSytax, cmPlayer.RankGroup);
				pingTabSytax = _messageTags.ReplacePlayerTag(pingTabSytax, cmPlayer);
				pingTabSytax = _messageTags.ReplacePingTag(pingTabSytax, ping);

				cmPlayer.BukkitPlayer.setPlayerListName(_logger.TransformToColor(pingTabSytax));
			}
		}
		catch (Exception exeption)
		{
			String message = "Ping Tab could not do its work. " + exeption.getMessage();

			_logger.SendToConsole(Module.TabSystem, MessageType.Error, message);
		}
	}

	private String SetColorTag(int ping)
	{
		String pingTag = "";

		if (ping >= _config.Ping.DeathPing)
		{
			pingTag = _config.Ping.DeathPingSyntax;
		}
		else
		{
			if (ping >= _config.Ping.HiPing)
			{
				pingTag = _config.Ping.HiPingSyntax;
			}
			else
			{
				if (ping >= _config.Ping.MidPing)
				{
					pingTag = _config.Ping.MidPingSyntax;
				}
				else
				{
					if (ping >= _config.Ping.NormalPing)
					{
						pingTag = _config.Ping.NormalPingSyntax;
					}
					else
					{
						if (ping >= _config.Ping.LowPing)
						{
							pingTag = _config.Ping.LowPingSyntax;
						}
						else
						{
							pingTag = _config.Ping.LocalPingSyntax;
						}

					}
				}
			}
		}

		return pingTag;
	}
}