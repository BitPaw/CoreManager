package de.BitFire.Tab;

import java.util.List;

import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageTags;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Configuration.Config;
import de.BitFire.Player.CMPlayer;
import de.BitFire.Player.PlayerSystem;
import de.BitFire.Time.IUpdateable;

public class TabSystem extends BaseSystem implements ISystem, IUpdateable
{
	private static TabSystem _instance;
	private Config _config;
	private Logger _logger;
	private MessageTags _messageTags;
	private PlayerSystem _playerSystem;

	private TabSystem()
	{
		super(Module.TabSystem, SystemState.Active, Priority.Low);
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
		_playerSystem = PlayerSystem.Instance();		
	}

	@Override
	public void OnTickUpdate()
	{		
		final List<CMPlayer> cmPlayers = _playerSystem.GetPlayers();	
		final int currentPlayers = cmPlayers.size();
		int ping;
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
					ping = cmPlayer.GetPlayerPing();
					pingTag = SetColorTag(ping);

					pingTabSytax = _config.Chat.PlayerDisplaySyntax + pingTag;

					pingTabSytax = _messageTags.ReplaceOPTag(pingTabSytax, cmPlayer.BukkitPlayer.isOp());
					pingTabSytax = _messageTags.ReplaceRankTag(pingTabSytax, cmPlayer.Information.RankGroup);
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