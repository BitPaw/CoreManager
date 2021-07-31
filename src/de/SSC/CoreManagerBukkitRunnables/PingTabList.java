package de.SSC.CoreManagerBukkitRunnables;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.CoreController;
import de.SSC.CoreManager.Main;
import de.SSC.CoreManager.Players.CoreManagerPlayer;
import de.SSC.CoreManager.Players.PlayerManager;
import de.SSC.CoreManagerPlugins.BukkitHook;
import de.SSC.CoreManagerPlugins.PermissionManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class PingTabList extends BukkitRunnable
{
	// Variables
	boolean UsePrefix = false;		
	boolean UseSuffix = true;

	String LocalPingTag = "&7[&f%ping%&7]";

	String LowPingTag = "&7[&b%ping%&7]";
	int LowPing = 10;

	String PingTag = "&7[&a%ping%&7]";	
	int Ping = 25;

	String MidPingTag = "&7[&e%ping%&7]";	
	int MidPing = 500;

	String HiPingTag = "&7[&c%ping%&7]";
	int HiPing = 1000;

	String DeathPingTag = "&7[&4%ping%&7]";	
	int DeathPing = 1200;

	// Referenzes
	private BukkitHook _bukkitHook;
	private PermissionManager _permissionManager;
	private PlayerManager _playerManager;
	private Config _config;

	public PingTabList()
	{
		try
		{
			CoreController coreController = Main.coreController;
			_bukkitHook = coreController._BukkitHook;
			_permissionManager = coreController._PermissionManager;
			_config = Main.config;
			_playerManager = coreController._PlayerManager;
		}
		catch(Exception e)
		{
			System.out.println("\n\nUnexpected Error while creating PingTabList");
		}
	}


	public void run()
	{
		List<Player> players = _bukkitHook.GetOnlinePlayers();

		for (Player player : players)
		{
			try
			{
				int pingValue = GetPing(player);
				String ping = Integer.toString(pingValue);
				String currentName = "";
				String pingText = "";
				String tag = SetColorTag(pingValue);
                String customName = player.getCustomName();

				if(customName == null)
				{
					currentName = player.getName();
				}
				else
				{
					currentName = customName;
				}

				if (UsePrefix)
				{
					pingText = tag.replace("%ping%", ping + " " + currentName);

					String out = pingText;

					SetPlayerName(player, out);
				}

				if (UseSuffix)
				{
					pingText = tag.replace("%ping%", ping);

					String out = currentName + " " + pingText;

					SetPlayerName(player, out);
				}
			}
			catch (Exception e)
			{
				_bukkitHook.SendConsolMessage("&cError while running PingTabList, "  + e.getMessage());
			}
		}
	}

	private void SetPlayerName(Player player, String message)
	{		  
		// Rank
		CoreManagerPlayer cmPlayer = _playerManager.GetUserPerUUID(player);

		if(cmPlayer == null)
		{

		}
		else
		{
			String rank = "&7[&6" + cmPlayer.GroupRank.ColorCode + "&7]&a";
			message =  rank + message;
		}

		// Add OP Tag
		if(player.isOp())
		{
			message = _config.OPPermission + message;
		}
		
		// Sets player Name
		player.setPlayerListName(ChatColor.translateAlternateColorCodes(_config.ColorCombineChar, message));
	}

	public int GetPing(Player player) 
	{
		org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer craftPlayer = (org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer) player; 		  
		net.minecraft.server.v1_12_R1.EntityPlayer entityPlayer = craftPlayer.getHandle(); 

		int ping = entityPlayer.ping;

		return ping;
	}

	private String SetColorTag(int ping)
	{
		String pingTag = "";

		if(ping >= DeathPing)
		{
			pingTag = DeathPingTag;
		}
		else 	    		
		{
			if(ping >= HiPing)
			{
				pingTag = HiPingTag;
			}
			else
			{
				if(ping >= MidPing)
				{
					pingTag = MidPingTag;
				}
				else
				{
					if(ping >= Ping)
					{
						pingTag = PingTag;
					}
					else
					{
						if(ping >= LowPing)
						{
							pingTag = LowPingTag;
						}
						else
						{
							pingTag = LocalPingTag; 
						}

					}
				}
			}

		}

		return pingTag;
	}

}