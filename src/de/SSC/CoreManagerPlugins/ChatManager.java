package de.SSC.CoreManagerPlugins;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Config.Messages;
import de.SSC.CoreManager.Main;
import de.SSC.CoreManager.Multiverse.CoreManagerWorld;
import de.SSC.CoreManager.Multiverse.WorldManager;
import de.SSC.CoreManager.Players.CoreManagerPlayer;
import de.SSC.CoreManager.Players.PlayerManager;
import de.SSC.CoreManager.Ranks.RankCredentials;
import de.SSC.CoreManager.Utility.CoreManagerPlugin;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatManager extends CoreManagerPlugin
{
	private boolean ShowWorld = true;

	// Referenzes
	private BukkitHook _bukkitHook;
	private Messages _messages;
	private Messager _messager;
	private WorldManager _worldManager;
	private PlayerManager _playerManager;
	private Config _config;

	private void RefreshReferenzes()
	{
		if(_bukkitHook == null) _bukkitHook = Main.coreController._BukkitHook;
		if(_playerManager == null)_playerManager = Main.coreController._PlayerManager;
		if(_messages == null)_messages = Main.messages;
		if(_messager == null)_messager = Main.coreController._Messager;
		if(_config == null) _config = Main.config;
		if(_worldManager == null) _worldManager = Main.coreController._WorldManager;

		if(_bukkitHook == null) throw new NullPointerException("_bukkitHook is null in ChatManager!");
		if(_playerManager == null)throw new NullPointerException("_playerManager is null in ChatManager!");
		if(_messages == null)throw new NullPointerException("_messages is null in ChatManager!");
		if(_messager == null)throw new NullPointerException("_messager is null in ChatManager!");
		if(_config == null) throw new NullPointerException("_config is null in ChatManager!");
		if(_worldManager == null) throw new NullPointerException("_worldManager is null in ChatManager!");
	}
	
	public void OnJoin(PlayerJoinEvent e)
	{
		RefreshReferenzes();

		Player player = e.getPlayer();

		String playerName = player.getCustomName();

		playerName = playerName == null ? player.getName() : playerName;
		
		String message = _bukkitHook.TransformColor(_messages.Join + playerName);
		
		e.setJoinMessage(message);
	}

	public void OnLeave(PlayerQuitEvent e)
	{
		RefreshReferenzes();

		Player player = e.getPlayer();

		String playerName = player.getCustomName();

		playerName = playerName == null ? player.getName() : playerName;

		String message = _bukkitHook.TransformColor(_messages.Quit + playerName);

		e.setQuitMessage(message);
	}	

	public void OnChat(AsyncPlayerChatEvent e) throws Exception
	{
		RefreshReferenzes();

		Player player ;
		World world;
		String playerCustomName;
		String playerName;
		String format;
		String rank;
		CoreManagerWorld coreManagerWorld;
		CoreManagerPlayer coreManagerPlayer;
		RankCredentials rankCredentials;


		// Variables
		player = e.getPlayer();
		playerCustomName = player.getCustomName();
		playerName = playerCustomName == null ? player.getName() : playerCustomName;
		world = player.getWorld();
		rank = "&8?";
		format = _messages.CharSyntax;
		rankCredentials = null;

		try
		{
			// Referenzes
			coreManagerPlayer = _playerManager.GetUserPerUUID(player);

			if(coreManagerPlayer != null)
			{
				rankCredentials = coreManagerPlayer.GroupRank;

				if(rankCredentials != null)
				{
					rank = rankCredentials.ColorCode;
				}
			}
			else
			{
				_messager.MessageToConsole("&cWarning the user is not registered! &7Is this right?");
			}

			coreManagerWorld = _worldManager.GetWorld(world);
		}
		catch (Exception exception)
		{
			throw new Exception("Error in ChatManager.OnChat() while creating variables.");
		}


		try
		{
			if(rankCredentials == null)
			{
				format = format.replace(_messages.TagPlayer, playerName);
			}
			else
			{
				format = format.replace(_messages.TagPlayer, rankCredentials.PlayerColor +  playerName);
			}


			format = format.replace(_messages.TagRank, rank);

			if(coreManagerWorld == null)
			{
				format = format.replace(_messages.TagWorld, "");
			}
			else
			{
				if(coreManagerWorld.IsLoaded())
				{
					format = format.replace(_messages.TagWorld, coreManagerWorld.Credentials.CustomName);
				}
				else
				{
					format = format.replace(_messages.TagWorld, world.getName());
				}
			}

			if(player.isOp())
			{
				format = format.replace(_messages.TagOP, _config.OPPermission);
			}
			else
			{
				format = format.replace(_messages.TagOP, "");
			}

			format = format.replace(_messages.TagMessage, e.getMessage());
			format = format.replace("%", "%%");

			format = _bukkitHook.TransformColor(format);
		}
		catch (Exception exeption)
		{
			throw new Exception("Error in ChatManager.OnChat() while creating the chat-format.");
		}
		
		e.setFormat(format);
	}

	public void OnChatFailSave(AsyncPlayerChatEvent e)
	{
		RefreshReferenzes();

		Player player = e.getPlayer();

		String format = _messages.CharSyntax;

		format = format.replace(_messages.TagPlayer, player.getName());
		format = format.replace(_messages.TagRank, "&bUser");
		format = format.replace(_messages.TagWorld, player.getWorld().getName());

		if(player.isOp())
		{
			format = format.replace(_messages.TagOP, _config.OPPermission);
		}
		else
		{
			format = format.replace(_messages.TagOP, "");
		}

		format = format.replace(_messages.TagMessage, e.getMessage());
		format = format.replace("%", "%%");

		format = _messager.TransformToColor(format);

		e.setFormat(format);
	}
}