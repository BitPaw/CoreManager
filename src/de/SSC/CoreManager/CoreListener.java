package de.SSC.CoreManager;

import de.SSC.CoreManager.Config.Messages;
import de.SSC.CoreManager.Editor.PlayerEditor;
import de.SSC.CoreManager.Editor.PlayerSkinChanger;
import de.SSC.CoreManager.Essentials.Chat.NameChanger;
import de.SSC.CoreManager.Multiverse.WorldManager;
import de.SSC.CoreManager.NameTagChanger.NameTagManipulator;
import de.SSC.CoreManager.Ranks.RankManager;
import de.SSC.CoreManager.Players.CoreManagerPlayer;
import de.SSC.CoreManager.Utility.CoreManagerPlugin;
import de.SSC.CoreManager.Players.PlayerManager;
import de.SSC.CoreManager.Utility.Utility;
import de.SSC.CoreManagerPlugins.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

//import de.SSC.CoreManager.NameTagChanger.NameTagManipulator;

public class CoreListener extends CoreManagerPlugin implements Listener
{
	// Referenzes
	private NameTagManipulator nameTagManipulator;
	private BukkitHook _bukkitHook;
	private DataBaseController _dataBaseController;
	private Messages _messages;
	private PlayerEditor _playerEditor;
	private TeleportationManager _teleportationManager;
	private WorldManager _worldManager;
	private RankManager _rankManager;
	private	NameChanger _nameChanger;
	private ChatManager _chatManager;
	private	SignEdit _signEdit;
	private PlayerManager _playerManager;

	public CoreListener()
	{

	}


	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		UpdateReferenzes();
		boolean successful = true;
		String command = commandLabel.toLowerCase();

		try
		{
			/*
			String parameters = "";

			for(String parameter : args)
			{
				parameters += parameter + " ";
			}

			_bukkitHook.SendConsolMessage("&3COMMAND &7> &a" + commandLabel + " " + parameters);
			*/

			switch(command)
			{
				// Default
				case "me" :
				case "whoisme" :
				case "whoami" :
					if(_bukkitHook.CheckIfCommandSenderIsPlayer(sender))
					{
						Player player = (Player)sender;
						CoreManagerPlayer cmplayer =  _playerManager.GetUserPerUUID(player);
						_bukkitHook.SendMessage(sender, cmplayer.GetPersonalData());
					}
					break;
				case "day" : _bukkitHook.Weather.Day(sender); 	break;
				case "night" :  _bukkitHook.Weather.Night(sender); break;
				case "rain" :  _bukkitHook.Weather.Rain(sender); break;
				case "exit" : _bukkitHook.ShutdownServer(); break;
				case "gm" : _playerEditor.ChangeGameMode(sender, args[0]); break;
				case "home" : break;
				case "heal" : _playerEditor.Heal(sender);				break;

				// Teleport
				case "w" :
				case "warps" :  _teleportationManager.PrintAllWarps(sender); break;
				case "warp": 	_teleportationManager.Warp(sender, args); break;
				case "setwarp": _teleportationManager.SetWarp(sender, args); break;
				case "delwarp": _teleportationManager.DeleteWarp(sender, args); break;
				case "spawn": _teleportationManager.TeleportToWorldSpawn(sender); break;
				case "setspawn": _teleportationManager.SetSpawn(sender); break;
				case "tp" :
				case "teleport" :_teleportationManager.TeleportToPlayer(sender, args[0]); break;
				case "tpw" :
				case "teleporttoworld" : _teleportationManager.TeleportToWorld(sender, args[0]); break;

				// Namechanger
				case "cn" :
				case "nick" :
				case "changename" : _nameChanger.SetPlayerName(sender, args);
									nameTagManipulator.UpdatePlayerTag(sender);
									break;

				// Change Skin
				case "cs" :
				case "changeskin" :
					PlayerSkinChanger playerSkinChanger = new PlayerSkinChanger();
					playerSkinChanger.ChangeSkin(sender, args[0]);
					break;

				// Multiverse
				case "dw" :
				case "deleteworld" : _worldManager.CreateNewWorldCommand(sender, args); break;
				case "cw" :
				case "createworld" : _worldManager.DeleteWorldCommand(sender, args); break;
				case "lw" :
				case "listworlds" :  _worldManager.ListWorlds(sender); break;

				// RankSystem
				case "cr" :
				case "changerank" : _rankManager.ChangeRankCommand(sender, args);
					nameTagManipulator.UpdatePlayerTag(sender);
					break;
				case "rm" :
				case "removerank" :
				case "dr" :
				case "deleterank" : _rankManager.ResetPlayerRank(sender);
					nameTagManipulator.UpdatePlayerTag(sender);
					break;

				case "lr" :
				case "listranks" : _rankManager.PrintAllRanks(sender); break;

				// PlayerTag
				case "cpt" :
				case "changeplayertag" :  break;


				// Database
				case "sql" :
					switch (args.length)
					{
						case 1 :
							switch (args[0])
							{
								case "rp" :
								case"registerplayer" :
								break;
								default: successful = false;
							}
							break;

						default: successful = false;
					}
				default :
					successful = false;
					_bukkitHook.SendMessage(sender, "Unkown command");

			}

		}
		catch(Exception e)
		{
			_bukkitHook.SendMessage(sender, "&cThere was an error while using a command.&7\nError message : " + e.getMessage());
			e.printStackTrace();
		}

		return successful;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt)
	{
		UpdateReferenzes();
		Player player = evt.getPlayer();

		String players;

		// DynamicName
		String playerName;
		String playerRalName = player.getName();


		// Nr
		int numberOfPlayers;


		try
		{
			boolean isRegistered =  _dataBaseController.IsPlayerRegistered(player);

			if(!isRegistered)
			{
				_bukkitHook.SendConsolMessage( _messages.Info + "&aNew playerdetected!\n" +
						"UUID : " + player.getUniqueId() + "\n" +
						"Name : " + player.getName());

				String firstJoinMessage =  Main.messages.FirstJoin;

				numberOfPlayers = _dataBaseController.GetNumberOfRegestratedPlayers();

				players = Integer.toString( numberOfPlayers+ 1);


				String playerCustomName = player.getCustomName();
				playerName = playerCustomName == null ? playerRalName : playerCustomName;

				firstJoinMessage = firstJoinMessage.replace("%player%", playerName);
				firstJoinMessage = firstJoinMessage.replace("%nr%", players);

				_bukkitHook.SendMessage(player, _messages.System + " " + firstJoinMessage);
				_dataBaseController.RegisterNewPlayer(player);

			}
			else
			{

				_bukkitHook.SendConsolMessage( _messages.Info + "&8Old playerdetected!\n" +
						"UUID : " + player.getUniqueId() + "\n" +
						"Name : " + player.getName());

				_bukkitHook.SendMessage(player, _messages.System + " " +_messages.ReJoin);
				_nameChanger.LoadPlayerName(player, _dataBaseController);


			}
		}
		catch(Exception e)
		{
			_bukkitHook.SendMessage(player,"&cError in event PlayerJoin! " + e.getMessage());
		}



		try
		{
			_playerManager.LoadPlayer(player);
		}
		catch (Exception e)
		{
			_bukkitHook.SendMessage(player, "&cLoadplayer on corelistener.OnPlayerLogin() has caused an error!\n" + e.getMessage());
			e.printStackTrace();
		}

		try
		{
			nameTagManipulator.UpdatePlayerTag(player);
		}
		catch (Exception e)
		{
			_bukkitHook.SendMessage(player, "&c nameTagManipulator.UpdatePlayerTag() has caused an error!" + e.getMessage());
			e.printStackTrace();
		}


	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		UpdateReferenzes();
		Player player = e.getPlayer();

		try
		{
			Utility.FireWork(player);
			_chatManager.OnJoin(e);
		}
		catch(Exception ex)
		{
			_bukkitHook.SendMessage(player,"Error in event OnJoin! " + ex.getMessage());
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e)
	{
		Player player = e.getPlayer();

		try
		{
			_dataBaseController.UpdateRegisteredPlayer(player);
			_chatManager.OnLeave(e);
			_playerManager.RemovePlayer(player);
		}
		catch (Exception exception)
		{
			_bukkitHook.SendMessage(player,"Error in Event OnChat! + "  + exception.getMessage());
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e)
	{
		Player player = e.getPlayer();

		try
		{
			_chatManager.OnChat(e);
		}
		catch(Exception ex)
		{
			_bukkitHook.SendMessage(player,"Error in Event OnChat! + "  + ex.getMessage());
			_chatManager.OnChatFailSave(e);
		}
	}

	@EventHandler
	public void onSignChangeEvent(SignChangeEvent e)
	{
		_signEdit.OnSignChangeEvent(e);
	}

	@EventHandler
	public void OnSignClick(PlayerInteractEvent event)
	{

	}

	@EventHandler
	public void OnPlayerInteract(PlayerInteractEvent event)
	{

	}



	private void UpdateReferenzes()
	{
		CoreController coreController = Main.coreController;

		if(nameTagManipulator == null)  nameTagManipulator = new NameTagManipulator();
		if(_bukkitHook == null) _bukkitHook = coreController._BukkitHook;
		if(_dataBaseController == null) _dataBaseController = coreController._DataBaseController;
		if(_messages == null) _messages = Main.messages;
		if(_playerEditor == null) _playerEditor = coreController._PlayerEditor;
		if(_teleportationManager == null) _teleportationManager = coreController._TeleportationManager;
		if(_worldManager == null) _worldManager = coreController._WorldManager;
		if(_rankManager == null) _rankManager = coreController._RankManager;
		if(_nameChanger == null) _nameChanger = coreController._NameChanger;
		if(_chatManager == null) _chatManager = coreController._ChatManager;
		if(_signEdit == null) _signEdit = coreController._SignEdit;
		if(_playerManager == null) _playerManager = coreController._PlayerManager;

		if(nameTagManipulator == null)   throw new NullPointerException("nameTagManipulator is null in class CoreListener");
		if(_bukkitHook == null)  throw new NullPointerException("_bukkitHook is null in class CoreListener");
		if(_dataBaseController == null)  throw new NullPointerException("_dataBaseController is null in class CoreListener");
		if(_messages == null)  throw new NullPointerException("_messages is null in class CoreListener");
		if(_playerEditor == null)  throw new NullPointerException("_playerEditor is null in class CoreListener");
		if(_teleportationManager == null)  throw new NullPointerException("_teleportationManager is null in class CoreListener");
		if(_worldManager == null)  throw new NullPointerException("_worldManager is null in class CoreListener");
		if(_rankManager == null)  throw new NullPointerException("_rankManager is null in class CoreListener");
		if(_nameChanger == null)  throw new NullPointerException("_nameChanger is null in class CoreListener");
		if(_chatManager == null)  throw new NullPointerException("_chatManager is null in class CoreListener");
		if(_signEdit == null)  throw new NullPointerException("_signEdit is null in class CoreListener");

		_rankManager.ReloadRanks();

	}
}