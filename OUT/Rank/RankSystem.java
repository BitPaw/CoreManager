package de.BitFire.Rank;

import java.util.List;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageTags;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Configuration.Config;
import de.BitFire.Core.Exception.NotForConsoleException;
import de.BitFire.Core.Exception.SystemNotActiveException;
import de.BitFire.DataBase.DataBaseSystem;
import de.BitFire.Player.CMPlayer;
import de.BitFire.Player.PlayerSystem;
import de.BitFire.Player.Exception.InvalidPlayerNameException;
import de.BitFire.Player.Exception.InvalidPlayerUUID;
import de.BitFire.Player.Exception.PlayerNotFoundException;
import de.BitFire.Rank.Exception.RankNotFoundException;
import de.BitFire.Rank.Exception.RedundantRankChangeException;

public class RankSystem extends BaseSystem implements ISystem
{
	private static RankSystem _instance;
	private RankList _rankList;

	private Logger _logger;
	private Config _config;
	private MessageTags _messageTags;
	private DataBaseSystem _databaseManager;
	private PlayerSystem _playerSystem;

	private RankSystem()
	{
		super(Module.RankSystem, SystemState.Active, Priority.Essential);
		_instance = this;
		
		_rankList = new RankList();	
	}

	public static RankSystem Instance()
	{
		return _instance == null ? new RankSystem() : _instance;
	}
	
	@Override
	public void LoadReferences() 
	{		
		_logger = Logger.Instance();
		_config = Config.Instance();
		_databaseManager = DataBaseSystem.Instance();
		_messageTags = MessageTags.Instance();
		_playerSystem = PlayerSystem.Instance();			
	}

	@Override
	public void Reload(final boolean firstRun) throws SystemNotActiveException 
	{
		List<CMRank> ranks;
		_rankList.Clear();
		
		if(!Information.IsActive())
		{
			throw new SystemNotActiveException();
		}
		
		ranks = _databaseManager.Rank.LoadAllRanks();
		
		for(CMRank cmRank : ranks)
		{
			_rankList.Add(cmRank);
		}			
	}
	
	@Override
	public void PrintData(CommandSender sender) 
	{
		final boolean isSenderPlayer = sender instanceof Player;
		String message = "";
		String row;
		List<CMRank> ranks = _rankList.GetAllRanks();		
		
		if(!isSenderPlayer)
		{
			message += _config.Message.IO.NewLine;
		}
		
		message += _config.Message.Rank.RankListHeader + _config.Message.IO.NewLine;
		
		
		for (CMRank rank : ranks)
		{
			row = _config.Message.Rank.RankRow;

			row = _messageTags.ReplaceNameTag(row, rank.Name);
			row = _messageTags.ReplaceRankTag(row, rank);
			row = _messageTags.ReplaceValueTag(row, rank.Permission.GetAmount());
			
			message += row + _config.Message.IO.NewLine;
		}

		message += _config.Message.Rank.RankListFooter;


		_logger.SendToSender(Module.RankSystem, MessageType.None, sender, message);
	}
		
	public CMRank GetRank(String rankName) throws RankNotFoundException
	{		
		return _rankList.GetRank(rankName);
	}
	
	public CMRank GetRank(int rankID) throws RankNotFoundException
	{		
		return _rankList.GetRank(rankID);
	}

	public void ResetPlayerRank(CommandSender sender, String[] parameter) throws PlayerNotFoundException, 
																				 InvalidPlayerUUID, 
																				 InvalidPlayerNameException,
																				 NotForConsoleException, 
																				 RankNotFoundException
	{
		final int parameterLengh = parameter.length;
		final boolean isSenderPlayer = sender instanceof Player;
		Player player;
		UUID playerUUID;		
		CMPlayer cmPlayer;
		String targetedPlayer;
		String message;

		switch (parameterLengh)
		{
		case 0:
			if (isSenderPlayer)
			{
				player = (Player)sender;
				playerUUID = player.getUniqueId();
				cmPlayer = _playerSystem.GetPlayer(playerUUID);

				cmPlayer.Information.RankGroup = _rankList.GetDefaultRank();

				message = "Rank has been resetted.";

				_logger.SendToSender(Module.RankSystem, MessageType.Info, sender, message);
			}
			else
			{
				throw new NotForConsoleException();
			}
			break;

		case 1:
			if (isSenderPlayer)
			{
				targetedPlayer = parameter[0];
				cmPlayer = _playerSystem.GetPlayer(targetedPlayer);

				cmPlayer.Information.RankGroup = _rankList.GetDefaultRank();

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

	public void ChangeRankCommand(CommandSender sender, String[] parameter) throws 	RankNotFoundException,
																					PlayerNotFoundException, 
																					NotForConsoleException, 
																					RedundantRankChangeException,
																					InvalidPlayerNameException, 
																					InvalidPlayerUUID
	{
		int parameterLengh = parameter.length;
		 final boolean isSenderPlayer = sender instanceof Player;	
		boolean isSameRank;	
		Player player;
		UUID playerUUID;
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
				playerUUID = player.getUniqueId();
				cmPlayer = _playerSystem.GetPlayer(playerUUID);

				cmRank = _rankList.GetDefaultRank();

				isSameRank = cmRank == cmPlayer.Information.RankGroup;
				 
				 if(isSameRank)
				 {						
					 throw new RedundantRankChangeException(cmRank);
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
				throw new NotForConsoleException();
			}
			break;

		case 1:
			if (isSenderPlayer)
			{
				targetedRank = parameter[0];
				player = (Player)sender;
				playerUUID = player.getUniqueId();

				cmPlayer = _playerSystem.GetPlayer(playerUUID);
				cmRank = GetRank(targetedRank);

				if (cmRank == null)
				{					
					throw new RankNotFoundException(targetedRank);					
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
				throw new NotForConsoleException();
			}
			break;

		case 2:
			targetedRank = parameter[0];
			targetedPlayerName = parameter[1];
			
			cmPlayer = _playerSystem.GetPlayer(targetedPlayerName);
			cmRank = GetRank(targetedRank);	
			
			oldCMRank = cmPlayer.Information.RankGroup;
			
			if(cmRank == oldCMRank)
			{				
				throw new RedundantRankChangeException(oldCMRank);
			}			
			
			cmPlayer.SetRank(cmRank);
			
			message = "&7Rank from player &8<&e" + cmPlayer.GetPlayerCustomName() + "&8>&7 has been &3changed &7from &8<&e" + oldCMRank.Tag + "&8> &7to &8<&e" + cmRank.Tag + "&8>&7.";

			_logger.SendToSender(Module.RankSystem, MessageType.Info, sender, message);
			
		default:

			break;
		}
	}

	public List<CMRank> GetAllRanks() 
	{		
		return _rankList.GetAllRanks();
	}

	public CMRank GetDefaultRank() throws RankNotFoundException
	{
		return _rankList.GetDefaultRank();
	}

	public void ClearPermission() 
	{
		_rankList.ClearPermission();
	}	
	
	public CMRank GetFailSaveRank()
	{		
		return new CMRank(-1, "Error", "&c", "&c");				
	}
}