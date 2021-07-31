package de.SSC.CoreManager.Economy;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Chat.Logger;
import de.SSC.CoreManager.Chat.MessageType;
import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.DataBase.DataBaseSystem;
import de.SSC.CoreManager.Economy.Exception.InvalidAmountParameterException;
import de.SSC.CoreManager.Economy.Exception.NegativeAmountException;
import de.SSC.CoreManager.Economy.Exception.NotEnoghMoneyException;
import de.SSC.CoreManager.Economy.Exception.NullAmountException;
import de.SSC.CoreManager.Economy.Exception.RedundantTransactionException;
import de.SSC.CoreManager.Economy.Exception.TooMuchMoneyException;
import de.SSC.CoreManager.Player.CMPlayer;
import de.SSC.CoreManager.Player.PlayerSystem;
import de.SSC.CoreManager.Player.Exception.InvalidPlayerNameException;
import de.SSC.CoreManager.Player.Exception.InvalidPlayerUUID;
import de.SSC.CoreManager.Player.Exception.PlayerNotFoundException;
import de.SSC.CoreManager.System.BaseSystem;
import de.SSC.CoreManager.System.ISystem;
import de.SSC.CoreManager.System.SystemPriority;
import de.SSC.CoreManager.System.SystemState;
import de.SSC.CoreManager.System.Exception.NotForConsoleException;
import de.SSC.CoreManager.System.Exception.TooFewParameterException;
import de.SSC.CoreManager.System.Exception.TooManyParameterException;

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
		super(Module.Econemy, SystemState.Active, SystemPriority.High);
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

	@Override
	public void Reload(final boolean firstRun) 
	{
		
		
	}
	
	@Override
	public void PrintData(CommandSender sender) 
	{
		
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
				
				message = "&7Your currently own &6<&e" + cmPlayer.Money.GetValue() + "&6> " + _config.CurrencySymbol;
				
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
			
			message = "&6<&e" + cmPlayer.PlayerName + "&6> &7currently owns &6<&e" + cmPlayer.Money.GetValue() + "&6> " + _config.CurrencySymbol;
			
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
			
			isSenderAndTargedTheSame = sourcePlayer.PlayerUUID == targetPlayer.PlayerUUID;
			
			if(isSenderAndTargedTheSame)
			{
				throw new RedundantTransactionException();
			}
			
			if(isSenderPlayer)
			{
				PayToPlayer(amount, sourcePlayer, targetPlayer);				
				
				// Message to caster
				{
					message = "&7You now own &6<&e" + sourcePlayer.Money.GetValue() + "&6> " + _config.CurrencySymbol;
					
					_logger.SendToSender(Module.System, MessageType.Info, sourcePlayer.BukkitPlayer, message);
				}
				
				// message to reciver
				{
					message = "&7You now own &6<&e" + targetPlayer.Money.GetValue() + "&6> " + _config.CurrencySymbol;
					
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
	boolean canPay = source.Money.CanPay(amount);
		
		if(canPay)
		{
			source.Money.Reduce(amount);
			
			String message = "&7You &cpayed &6<&e" + amount + "&6> &e" + _config.CurrencySymbol + " &7to the bank.";
			
			_logger.SendToSender(Module.System, MessageType.Info, source.BukkitPlayer, message);
		
			_dataBaseSystem.Player.UpdateAccountBalance(source);
		}
		else
		{
			throw new NotEnoghMoneyException(source.Money.GetValue(), amount);
		}
	}
	
	private void PayBankToPlayer(float amount, CMPlayer targetCMPlayer) throws 	NullAmountException, 
																				NegativeAmountException, 
																				TooMuchMoneyException 
	{
		targetCMPlayer.Money.Add(amount);
		
		String message = "&7You &agained &6<&e" + amount + "&6> &e" + _config.CurrencySymbol + " &7from the bank.";
		
		_logger.SendToSender(Module.System, MessageType.Info, targetCMPlayer.BukkitPlayer, message);
	
		_dataBaseSystem.Player.UpdateAccountBalance(targetCMPlayer);
	}
	
	private void PayToPlayer(float amount, CMPlayer source, CMPlayer target) throws NotEnoghMoneyException, 
																					NullAmountException, 
																					NegativeAmountException, 
																					TooMuchMoneyException
	{
		boolean canPay = source.Money.CanPay(amount);
		
		if(canPay)
		{
			source.Money.Reduce(amount);
			target.Money.Add(amount);
			
			String message = "&7You &epayed &6<&e" + amount + "&6> &e" + _config.CurrencySymbol + "&7to &6<&e" + target.PlayerName + "&6>&7.";
			
			_logger.SendToSender(Module.System, MessageType.Info, target.BukkitPlayer, message);
		
			_dataBaseSystem.Player.UpdateAccountBalance(source);
			_dataBaseSystem.Player.UpdateAccountBalance(target);
		}
		else
		{
			throw new NotEnoghMoneyException(source.Money.GetValue(), amount);
		}
	}
}
