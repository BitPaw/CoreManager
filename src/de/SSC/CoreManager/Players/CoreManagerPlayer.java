package de.SSC.CoreManager.Players;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Economy.Money;
import de.SSC.CoreManager.Main;
import de.SSC.CoreManager.Ranks.RankCredentials;
import de.SSC.CoreManager.Ranks.RankManager;
import org.bukkit.entity.Player;

public class CoreManagerPlayer 
{
	public Player BukkitPlayer;
	public Money MoneyValue;
	public RankCredentials GroupRank;
	
	public CoreManagerPlayer(Player player, float money, String rank)
	{
		BukkitPlayer = player;
		MoneyValue = new Money(money);

		RankManager rankManager = Main.coreController._RankManager;
		GroupRank = rankManager.GetRankPerName(rank);
	}
	
	public String GetPersonalData()
	{
		Config config = Main.config;

		String message = "&6========[&eWho am I?&6]========\n";
		String opMessage = BukkitPlayer.isOp() ? "&aYes" : "&cNo";
		
		message += " &6Name  : &e" + BukkitPlayer.getName() + "\n";
		message += " &6Custom: &e" + BukkitPlayer.getCustomName()+ "\n";
		message += " &6OP    : &e" + opMessage + "\n";
		message += " &6Money : &e" + MoneyValue.GetValue() + config.Currency + "\n";
		message += " &6Rank  : &e" + GroupRank + "\n";
		message += " &6IP    : &e" + BukkitPlayer.getAddress().getHostString()+ "\n";
		message += " &6Port  : &e" + BukkitPlayer.getAddress().getPort()+ "\n";
		message += "&6=========================";
		
		return message;
	}

	/*
	public static CoreManagerPlayer TranslateFromPlayer(Player player)
	{
		CoreController coreController = CoreController.GetInstance();

		DataBaseController dataBaseController = coreController._DataBaseController;
		
		return dataBaseController.GetPlayerInformation(player);
	}
	*/
}
