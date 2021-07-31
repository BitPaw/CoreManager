package de.BitFire.Economy;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Core.Exception.NotForConsoleException;
import de.BitFire.Core.Exception.TooFewParameterException;
import de.BitFire.Core.Exception.TooManyParameterException;
import de.BitFire.DataBase.DataBaseSystem;
import de.BitFire.Economy.Exception.InvalidAmountParameterException;
import de.BitFire.Economy.Exception.NegativeAmountException;
import de.BitFire.Economy.Exception.NotEnoghMoneyException;
import de.BitFire.Economy.Exception.NullAmountException;
import de.BitFire.Economy.Exception.RedundantTransactionException;
import de.BitFire.Economy.Exception.TooMuchMoneyException;
import de.BitFire.Player.CMPlayer;
import de.BitFire.Player.PlayerSystem;
import de.BitFire.Player.Exception.InvalidPlayerNameException;
import de.BitFire.Player.Exception.InvalidPlayerUUID;
import de.BitFire.Player.Exception.PlayerNotFoundException;

public class EconemySystem extends BaseSystem implements ISystem
{
	private static EconemySystem _instance;	
	private EconemyConfig _config;
	
	private PlayerSystem _playerSystem;
	private Logger _logger;
	private DataBaseSystem _dataBaseSystem;
	
	public static EconemySystem Instance()
	{
		return _instance == null ? new EconemySystem() : _instance;
	}
	
	private EconemySystem()
	{
		super(Module.Econemy, SystemState.Active, Priority.High);
		_instance = this;
		
		_config = new EconemyConfig();
	}
	
	@Override
	public void LoadReferences() 
	{
		_playerSystem = PlayerSystem.Instance();
		_logger = Logger.Instance();
		_dataBaseSystem = DataBaseSystem.Instance();
	}
	
	public void GetAccountBalanceCommand(CommandSender sender, String[] parameter) throws 	NotForConsoleException, 
																							PlayerNotFoundException, 
																							InvalidPlayerUUID, 
																							TooManyParameterException, 
																							InvalidPlayerNameException, 
																							InvalidAmountParameterException, 
																							NotEnoghMoneyException, 
																							NullAmountException, 
																							NegativeAmountException, 
																							TooMuchMoneyException
	{
		final int parameterLengh = parameter.length;
		final boolean isSenderPlayer = sender instanceof Player;
		int amount;
		CMPlayer cmPlayer;
		Player player;
		String targetedPlayer;	
		String message;
		String command;
		String amountText;
		
		switch(parameterLengh)
		{
		case 0:			
			if(isSenderPlayer)
			{
				player = (Player)sender;
				
				cmPlayer = _playerSystem.GetPlayer(player.getUniqueId());
				
				message = "&7Your currently own &6<&e" + cmPlayer.Information.Money.GetValue() + "&6> " + _config.CurrencySymbol;
				
				_logger.SendToSender(Module.System, MessageType.Info, cmPlayer.BukkitPlayer, message);
			}
			else
			{
				throw new NotForConsoleException();
			}
			
			break;
			
		case 1:
			
			targetedPlayer = parameter[0];
			
			cmPlayer = _playerSystem.GetPlayer(targetedPlayer);			
			
			message = "&6<&e" + cmPlayer.Information.PlayerName + "&6> &7currently owns &6<&e" + cmPlayer.Information.Money.GetValue() + "&6> " + _config.CurrencySymbol;
			
			_logger.SendToSender(Module.System, MessageType.Info, cmPlayer.BukkitPlayer, message);
			
			break;
		
		case 2:
			// add or remove own money
			if(isSenderPlayer)
			{
				command = parameter[0].toLowerCase();
				amountText = parameter[1];		
				player = (Player)sender;
				cmPlayer = _playerSystem.GetPlayer(player.getUniqueId());
				
				try
				{
					amount = Integer.parseInt(amountText);
				}
				catch(NumberFormatException e)
				{
					throw new InvalidAmountParameterException(amountText);
				}	
				
				switch(command)			
				{
				case "add":
					PayBankToPlayer(amount, cmPlayer);
					break;
					
				case "reduce":
					PayToBank(amount, cmPlayer);
					break;
				}
			}
			else
			{
				throw new NotForConsoleException();
			}			
		
			break;
			
		case 3:
			// money add 100 BitPaw			
			command = parameter[0].toLowerCase();
			amountText = parameter[1];	
			targetedPlayer = parameter[2];	
			
			try
			{
				amount = Integer.parseInt(amountText);
			}
			catch(NumberFormatException e)
			{
				throw new InvalidAmountParameterException(amountText);
			}	
			
			cmPlayer = _playerSystem.GetPlayer(targetedPlayer);			
			
			switch(command)			
			{
			case "add":
				PayBankToPlayer(amount, cmPlayer);
				break;
				
			case "reduce":
				PayToBank(amount, cmPlayer);
				break;
			}
			
			break;
			
		default:
			throw new TooManyParameterException();
		}
	}
	
	public void PayCommand(CommandSender sender, String[] parameter) throws TooFewParameterException, 
																			NotForConsoleException, 
																			PlayerNotFoundException, 
																			InvalidPlayerUUID, 
																			InvalidPlayerNameException, 
																			NotEnoghMoneyException, 
																			NullAmountException, 
																			NegativeAmountException, 
																			TooMuchMoneyException, 
																			TooManyParameterException, 
																			InvalidAmountParameterException, 
																			RedundantTransactionException
	{
		final int parameterLengh = parameter.length;
		final boolean isSenderPlayer = sender instanceof Player;
		boolean isSenderAndTargedTheSame;
		Player player;
		CMPlayer sourcePlayer;
		CMPlayer targetPlayer;
		String targetPlayerName;	
		String amountText;	
		String message;
		int amount;
		
		switch(parameterLengh)
		{
		case 0:			
		case 1:
			throw new TooFewParameterException();
			
		case 2:
			amountText = parameter[0];
			targetPlayerName = parameter[1];				
			
			try
			{
				amount = Integer.parseInt(amountText);
			}
			catch(NumberFormatException e)
			{
				throw new InvalidAmountParameterException(amountText);
			}		
			
			player = (Player)sender;
			
			sourcePlayer = _playerSystem.GetPlayer(player.getUniqueId());
			targetPlayer = _playerSystem.GetPlayer(targetPlayerName);
			
			isSenderAndTargedTheSame = sourcePlayer.Information.PlayerUUID == targetPlayer.Information.PlayerUUID;
			
			if(isSenderAndTargedTheSame)
			{
				throw new RedundantTransactionException();
			}
			
			if(isSenderPlayer)
			{
				PayToPlayer(amount, sourcePlayer, targetPlayer);				
				
				// Message to caster
				{
					message = "&7You now own &6<&e" + sourcePlayer.Information.Money.GetValue() + "&6> " + _config.CurrencySymbol;
					
					_logger.SendToSender(Module.System, MessageType.Info, sourcePlayer.BukkitPlayer, message);
				}
				
				// message to reciver
				{
					message = "&7You now own &6<&e" + targetPlayer.Information.Money.GetValue() + "&6> " + _config.CurrencySymbol;
					
					_logger.SendToSender(Module.System, MessageType.Info, targetPlayer.BukkitPlayer, message);
				}		
			}	
			else
			{
				throw new NotForConsoleException();
			}
			
			break;
			
		default:
			throw new TooManyParameterException();
		}
	}
	

	private void PayToBank(float amount, CMPlayer source) throws 	NotEnoghMoneyException, 
																	NullAmountException, 
																	NegativeAmountException 
	{
	boolean canPay = source.Information.Money.CanPay(amount);
		
		if(canPay)
		{
			source.Information.Money.Reduce(amount);
			
			String message = "&7You &cpayed &6<&e" + amount + "&6> &e" + _config.CurrencySymbol + " &7to the bank.";
			
			_logger.SendToSender(Module.System, MessageType.Info, source.BukkitPlayer, message);
		
			_dataBaseSystem.Player.UpdateAccountBalance(source);
		}
		else
		{
			throw new NotEnoghMoneyException(source.Information.Money.GetValue(), amount);
		}
	}
	
	private void PayBankToPlayer(float amount, CMPlayer targetCMPlayer) throws 	NullAmountException, 
																				NegativeAmountException, 
																				TooMuchMoneyException 
	{
		targetCMPlayer.Information.Money.Add(amount);
		
		String message = "&7You &agained &6<&e" + amount + "&6> &e" + _config.CurrencySymbol + " &7from the bank.";
		
		_logger.SendToSender(Module.System, MessageType.Info, targetCMPlayer.BukkitPlayer, message);
	
		_dataBaseSystem.Player.UpdateAccountBalance(targetCMPlayer);
	}
	
	private void PayToPlayer(final float amount, final CMPlayer source, final CMPlayer target) throws NotEnoghMoneyException, 
																					NullAmountException, 
																					NegativeAmountException, 
																					TooMuchMoneyException
	{
		final boolean canPay = source.Information.Money.CanPay(amount);
		
		if(canPay)
		{
			source.Information.Money.Reduce(amount);
			target.Information.Money.Add(amount);
			
			String message = "&7You &epayed &6<&e" + amount + "&6> &e" + _config.CurrencySymbol + "&7to &6<&e" + target.Information.PlayerName + "&6>&7.";
			
			_logger.SendToSender(Module.System, MessageType.Info, target.BukkitPlayer, message);
		
			_dataBaseSystem.Player.UpdateAccountBalance(source);
			_dataBaseSystem.Player.UpdateAccountBalance(target);
		}
		else
		{
			throw new NotEnoghMoneyException(source.Information.Money.GetValue(), amount);
		}
	}
}
