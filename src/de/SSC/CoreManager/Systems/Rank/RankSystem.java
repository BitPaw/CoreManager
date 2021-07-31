package de.SSC.CoreManager.Systems.Rank;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Systems.Chat.ExceptionInformation;
import de.SSC.CoreManager.Systems.Chat.Logger;
import de.SSC.CoreManager.Systems.Chat.MessageTags;
import de.SSC.CoreManager.Systems.Chat.MessageType;
import de.SSC.CoreManager.Systems.Chat.Module;
import de.SSC.CoreManager.Systems.DataBase.DataBaseSystem;
import de.SSC.CoreManager.Systems.Player.CMPlayer;
import de.SSC.CoreManager.Systems.Player.PlayerNotFoundException;
import de.SSC.CoreManager.Systems.Player.PlayerSystem;
import de.SSC.CoreManager.Utility.BukkitUtility;
import de.SSC.CoreManager.Utility.NotForConsoleException;

public class RankSystem
{
	private static RankSystem _instance;
	private ArrayList<CMRank> _ranks;
	private CMRank _defaultRank;

	private Logger _logger;
	private Config _config;
	private MessageTags _messageTags;
	private DataBaseSystem _databaseManager;
	private BukkitUtility _bukkitUtility;
	private PlayerSystem _playerSystem;

	private RankSystem()
	{
		_instance = this;

		_ranks = new ArrayList<CMRank>();

		_logger = Logger.Instance();
		_config = Config.Instance();
		_databaseManager = DataBaseSystem.Instance();
		_messageTags = MessageTags.Instance();
		_bukkitUtility = BukkitUtility.Instance();
		_playerSystem = PlayerSystem.Instance();		
	}

	public static RankSystem Instance()
	{
		return _instance == null ? new RankSystem() : _instance;
	}
	
	public CMRank GetRank(CMRank rankGroup)
	{
		int index;
		CMRank returnRank = null;
		
		index = _ranks.indexOf(rankGroup);
		
		if(index != -1)
		{
			_ranks.get(index);
		}
		
		return returnRank;
	}
	
	public CMRank GetRank(String name)
	{
		CMRank returnRank = null;

		for (CMRank rank : _ranks)
		{
			if (rank.RankName.equalsIgnoreCase(name))
			{
				returnRank = rank;
				break;
			}
		}

		if (returnRank == null)
		{
			String message = "The rank " + name + " was searched but not found.";

			_logger.SendToConsole(Module.RankSystem, MessageType.Warning, message);
		}

		return returnRank;
	}

	public void ListAllRanks(CommandSender sender)
	{
		String message = _config.Messages.Rank.RankListHeader + _config.Messages.ConsoleIO.NewLine;
		String row;

		for (CMRank rank : _ranks)
		{
			row = _config.Messages.Rank.RankRow;

			row = _messageTags.ReplaceNameTag(row, rank.RankName);
			row = _messageTags.ReplaceRankTag(row, rank);

			message += row + _config.Messages.ConsoleIO.NewLine;
		}

		message += _config.Messages.Rank.RankListFooter;


		_logger.SendToSender(Module.RankSystem, MessageType.None, sender, message);
	}

	public List<CMRank> ListAllRanks()
	{
		List<CMRank> ranks = new ArrayList<CMRank>();

		for (CMRank rank : _ranks)
		{
			ranks.add(rank);
		}

		return ranks;
	}

	public CMRank GetDefaultRank()
	{
		if (_defaultRank == null)
		{
			for (CMRank rank : _ranks)
			{
				if (rank.IsDefault)
				{
					_defaultRank = rank;
					break;
				}
			}
		}

		return _defaultRank;
	}

	public void ResetPlayerRank(CommandSender sender, String[] parameter)
	{
		int parameterLengh = parameter.length;
		boolean isSenderPlayer = _bukkitUtility.PlayerUtility.IsSenderPlayer(sender);
		
		Player player;
		CMPlayer cmPlayer;
		String targetedPlayer;
		String message;

		switch (parameterLengh)
		{
		case 0:
			if (isSenderPlayer)
			{
				player = (Player)sender;
				cmPlayer = _playerSystem.GetPlayer(player);

				cmPlayer.RankGroup = GetDefaultRank();

				message = "Rank has been resetted.";

				_logger.SendToSender(Module.RankSystem, MessageType.Info, sender, message);
			}
			else
			{

			}
			break;

		case 1:
			if (isSenderPlayer)
			{
				targetedPlayer = parameter[0];

				cmPlayer = _playerSystem.GetPlayer(targetedPlayer);

				cmPlayer.RankGroup = GetDefaultRank();

				message = "Rank has been changed from .";

				_logger.SendToSender(Module.RankSystem, MessageType.Info, sender, message);
			}
			else
			{

			}
			break;

		default:

			break;
		}
	}

	public void ReloadRanks()
	{
		_ranks.clear();
		_ranks = _databaseManager.LoadAllRanks();
	}
	
	public void ChangeRankCommand(CommandSender sender, String[] parameter) throws RankNotFoundException, PlayerNotFoundException, NotForConsoleException, RedundantRankChangeException
	{
		int parameterLengh = parameter.length;
		boolean isSenderPlayer = _bukkitUtility.PlayerUtility.IsSenderPlayer(sender);		
		boolean isSameRank;
		ExceptionInformation  exceptionInformation = new ExceptionInformation(this, sender);		
		Player player;
		CMPlayer cmPlayer;
		String targetedPlayerName;
		String message;
		String targetedRank;
		CMRank cmRank;
		CMRank oldCMRank;
		
		switch (parameterLengh)
		{
		case 0:
			if (isSenderPlayer)
			{
				player = (Player)sender;
				cmPlayer = _playerSystem.GetPlayer(player);

				cmRank = GetDefaultRank();

				isSameRank = cmRank == cmPlayer.RankGroup;
				 
				 if(isSameRank)
				 {						
					 throw new RedundantRankChangeException(exceptionInformation, cmRank);
				 }
				 else
				 {
					 cmPlayer.SetRank(cmRank);
					 
					 message = "Rank has been resetted.";

						_logger.SendToSender(Module.RankSystem, MessageType.Info, sender, message);
				 }	
			}
			else
			{
		
				
				throw new NotForConsoleException(exceptionInformation);
			}
			break;

		case 1:
			if (isSenderPlayer)
			{
				targetedRank = parameter[0];
				player = (Player)sender;

				cmPlayer = _playerSystem.GetPlayer(player);
				cmRank = GetRank(targetedRank);

				if (cmRank == null)
				{					
					throw new RankNotFoundException(exceptionInformation, targetedRank);					
				}
				else
				{
					cmPlayer.SetRank(cmRank);

					message = "Rank has been changed.";

					_logger.SendToSender(Module.RankSystem, MessageType.Info, sender, message);
				}
			}
			else
			{				
				throw new NotForConsoleException(exceptionInformation);
			}
			break;

		case 2:
			targetedRank = parameter[0];
			targetedPlayerName = parameter[1];
			
			cmPlayer = _playerSystem.GetPlayer(targetedPlayerName);
			cmRank = GetRank(targetedRank);			
			
			if(cmPlayer == null)
			{
				throw new PlayerNotFoundException(exceptionInformation, targetedPlayerName);
			}
			
			if(cmRank == null)
			{				
				throw new RankNotFoundException(exceptionInformation, targetedRank);
			}
			
			oldCMRank = cmPlayer.RankGroup;
			
			if(cmRank == oldCMRank)
			{				
				throw new RedundantRankChangeException(exceptionInformation, oldCMRank);
			}			
			
			cmPlayer.SetRank(cmRank);
			
			message = "&7Rank from player &8<&e" + cmPlayer.GetPlayerCustomName() + "&8>&7 has been &3changed &7from &8<&e" + oldCMRank.ColorTag + "&8> &7to &8<&e" + cmRank.ColorTag + "&8>&7.";

			_logger.SendToSender(Module.RankSystem, MessageType.Info, sender, message);
			
		default:

			break;
		}
	}
}