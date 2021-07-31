package de.SSC.CoreManager.Tab;

import java.util.List;

import org.bukkit.command.CommandSender;
import de.SSC.CoreManager.Chat.Logger;
import de.SSC.CoreManager.Chat.MessageTags;
import de.SSC.CoreManager.Chat.MessageType;
import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Player.CMPlayer;
import de.SSC.CoreManager.Player.PlayerSystem;
import de.SSC.CoreManager.System.BaseSystem;
import de.SSC.CoreManager.System.IRunnableSystem;
import de.SSC.CoreManager.System.ISystem;
import de.SSC.CoreManager.System.SystemPriority;
import de.SSC.CoreManager.System.SystemState;
import de.SSC.BukkitAPI.BukkitAPISystem;

public class TabSystem extends BaseSystem implements ISystem, IRunnableSystem
{
	private static TabSystem _instance;
	private Config _config;
	private Logger _logger;
	private MessageTags _messageTags;
	private BukkitAPISystem _bukkitUtility;
	private PlayerSystem _playerSystem;

	private TabSystem()
	{
		super(Module.TabSystem, SystemState.Active, SystemPriority.Low);
		_instance = this;
	}

	public static TabSystem Instance()
	{
		return _instance == null ? new TabSystem() : _instance;
	}

	@Override
	public void LoadReferences() 
	{
		_config = Config.Instance();
		_logger = Logger.Instance();
		_messageTags = MessageTags.Instance();
		_bukkitUtility = BukkitAPISystem.Instance();
		_playerSystem = PlayerSystem.Instance();		
	}

	@Override
	public void Reload(final boolean firstRun) 
	{
		
	}
	
	@Override
	public void PrintData(CommandSender sender) 
	{
		
	}

	public void Update()
	{		
		List<CMPlayer> cmPlayers = _playerSystem.GetPlayers();
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
				final boolean isPlayerInGame = cmPlayer.IsPlayerInGame(); 
				
				if(isPlayerInGame)
				{
					ping = _bukkitUtility.Player.GetPlayerPing(cmPlayer.BukkitPlayer);
					pingTag = SetColorTag(ping);

					pingTabSytax = _config.Chat.PlayerDisplaySyntax + pingTag;

					pingTabSytax = _messageTags.ReplaceOPTag(pingTabSytax, cmPlayer.BukkitPlayer.isOp());
					pingTabSytax = _messageTags.ReplaceRankTag(pingTabSytax, cmPlayer.RankGroup);
					pingTabSytax = _messageTags.ReplacePlayerTag(pingTabSytax, cmPlayer);
					pingTabSytax = _messageTags.ReplacePingTag(pingTabSytax, ping);

					cmPlayer.BukkitPlayer.setPlayerListName(_logger.TransformToColor(pingTabSytax));
				}				
			}
		}
		catch (Exception exeption)
		{
			String message = "Ping Tab could not do its work. " + exeption.getMessage();

			_logger.SendToConsole(Module.TabSystem, MessageType.Error, message);
			
			exeption.printStackTrace();
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