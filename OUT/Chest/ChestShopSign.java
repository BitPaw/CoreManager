package de.BitFire.Chest;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import de.BitFire.API.Bukkit.BukkitAPIBlock;
import de.BitFire.API.Bukkit.BukkitAPIItem;
import de.BitFire.Chat.Logger;

public class ChestShopSign 
{		
	public String Owner;
	public boolean IsAdminShop;
	public int Amount;
	public int Sell;
	public int Buy;
	public int ItemID;
	
	public ChestShopSign()
	{
		
	}		
	
	public static boolean IsChestShopSign(final Block signBlock)
	{
		final Block blockUnderSign = signBlock.getRelative(BlockFace.DOWN);
		final boolean isBlockUnderSignChest = BukkitAPIBlock.IsBlockChest(blockUnderSign);
		
		return isBlockUnderSignChest;		
	}
			
	public static String[] GenerateShopTextSign(final int amount, final int buy, final int sell, final Material material)
	{				
		return GenerateShopTextSign(null, amount, buy, sell, material);
	}
	
	public static String[] GenerateShopTextSign(final int amount, final int buy, final int sell, final int itemID)
	{				
		final Material material = BukkitAPIItem.GetMaterialPerID(itemID);
		
		return GenerateShopTextSign(null, amount, buy, sell, material);
	}
	
	public static String[] GenerateShopTextSign(final String playerName, final int amount, final int buy, final int sell, final int itemID)
	{		
		final Material material = BukkitAPIItem.GetMaterialPerID(itemID);
		
		return GenerateShopTextSign(playerName, amount, buy, sell, material);
	}
	
	public static String[] GenerateShopTextSign(String playerName, final int amount, final int buy, final int sell, final Material material)
	{		
		
		String[] _signMessages = new String[4];		
		
		if(playerName == null)
		{
			playerName = "&aBank";
		}	
		
		_signMessages[0] = "&8[&c" + playerName + "&8]";
		_signMessages[1] = amount + "&0&dx";
		_signMessages[2] = "&dB &0" + buy + "&d:&0" + sell + " &dS";
		_signMessages[3] = "&b" + material.toString();
		
		return _signMessages;
	}

	public static String[] ParseSign(String[] lines, final Player player)
	{
		Logger logger = Logger.Instance();
		
		String seller = logger.ClearColor(lines[0]);
		String amount = logger.ClearColor(lines[1]);
		String buySell = logger.ClearColor(lines[2]);
		String ItemName = logger.ClearColor(lines[3]);
		
		final String playerName = player.getName();
		final boolean willBeABankShop = seller.compareToIgnoreCase("$") == 0;		
		final boolean noOwnerNameSet = seller.isEmpty();
		final boolean isOwnUser = seller.compareToIgnoreCase(playerName) == 0 || noOwnerNameSet;
		final boolean hasDoublePoint = buySell.contains(":");

		final int amountValue = Integer.parseInt(amount);
		int buyValue = 0;
		int sellValue = 0;
		final int itemID = Integer.parseInt(ItemName);
		
		
		if(hasDoublePoint)
		{
			String[] subStrings = buySell.split(":");
			int valueCounter = 0;
			
			for (String string : subStrings) 
			{
				if(valueCounter == 0)
				{
					if(string.isEmpty())
					{
						buyValue = 0;
					}
					else
					{
						buyValue = Integer.parseInt(string);
					}	
				}
				
				if(valueCounter == 1)
				{
					if(string.isEmpty())
					{
						sellValue = 0;
					}
					else
					{
						sellValue = Integer.parseInt(string);
					}	
				}				
				
				valueCounter++;
			}						
		}
		

		if(willBeABankShop)
		{
			lines = GenerateShopTextSign(amountValue, buyValue, sellValue, itemID);
		}
		else if(isOwnUser)
		{
			lines = GenerateShopTextSign(playerName, amountValue, buyValue, sellValue, itemID);
		}

		
		return lines;
	}
}
