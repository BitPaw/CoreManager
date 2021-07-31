package de.BitFire.CoreManager.Modules.Tab;

import java.util.Collection;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.BitFire.CoreManager.Modules.Chat.ChatManager;
import de.BitFire.CoreManager.Modules.Chat.ColorManager;
import de.BitFire.CoreManager.Modules.Utility.PlayerUtility;

public class TabSystem
{
	private static TabSystemConfig _config;
	
	static
	{
		_config = new TabSystemConfig();
		_config.LoadDefaults();
	}
		
	public static void OnTickUpdate()
	{		
		final Collection<? extends Player> players = Bukkit.getOnlinePlayers();	

		for(final Player bukkitPlayer : players)
		{
			final int playerPing = PlayerUtility.GetPlayerPing(bukkitPlayer);		
			final UUID playerUUID = bukkitPlayer.getUniqueId();
			final String pingTag = SetColorTag(playerPing);
			String fullName = ChatManager.GetFullPlayerName(playerUUID);

			//fullName += " &8[" + pingTag + "&8]";
			fullName += " " + pingTag;
			fullName = fullName.replace("{PING}", String.valueOf(playerPing));
			fullName = ColorManager.AddColor(fullName);
			
			bukkitPlayer.setPlayerListName(fullName);		
		}
	}

	private static String SetColorTag(int ping)
	{
		String pingTag = "";

		if (ping >= _config.DeathPing)
		{
			pingTag = _config.DeathPingSyntax;
		}
		else
		{
			if (ping >= _config.HiPing)
			{
				pingTag = _config.HiPingSyntax;
			}
			else
			{
				if (ping >= _config.MidPing)
				{
					pingTag = _config.MidPingSyntax;
				}
				else
				{
					if (ping >= _config.NormalPing)
					{
						pingTag = _config.NormalPingSyntax;
					}
					else
					{
						if (ping >= _config.LowPing)
						{
							pingTag = _config.LowPingSyntax;
						}
						else
						{
							pingTag = _config.LocalPingSyntax;
						}

					}
				}
			}
		}

		return pingTag;
	}
}