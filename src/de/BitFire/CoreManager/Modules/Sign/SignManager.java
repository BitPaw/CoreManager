package de.BitFire.CoreManager.Modules.Sign;

import de.BitFire.CoreManager.Cache.SignCache;
import de.BitFire.CoreManager.Modules.Player.Player;
import de.BitFire.CoreManager.Modules.Player.PlayerManager;
import de.BitFire.Math.NumberFormatter;

public class SignManager 
{
	private static SignCache _cache;
		
	static
	{
		_cache = new SignCache();
	}
	
	public boolean IsSignShop(Sign sign)
	{
		return true;
	}

	public static Sign GetSign(final int worldID, final int worldPositionID) 
	{		
		return _cache.GetSign(worldID, worldPositionID);
	}
	
	public static String[] GetSignContent(SignShop signShop)
	{			
		String[] lines = new String[4];	
		
		// Seller
		{
			final boolean isBank = signShop.OwnerID == -1;
			
			if(isBank)
			{
				lines[0] = "&0[&2$&0] &2Bank";
			}
			else
			{
				final Player player =PlayerManager.GetPlayer(signShop.OwnerID);
				
				lines[0] = "&[" + player.GetPreferedName() + "]";
			}
		}
	
		// Amount
		{
			lines[1] = "Amount: " + signShop.Amount + "x";
		}
		
		// Buy / Sale
		{
			final String buy = NumberFormatter.ConvertNumberToSmallText(signShop.BuyPrice);
			final String sale  = NumberFormatter.ConvertNumberToSmallText(signShop.SalePrice);
			
			lines[2] = "Buy " + buy + ":" + sale + " Sale";
		}
		
		// BlockName
		{
			String blockName = "BLOCKNAME";
			
			lines[3] = "" + blockName;
		}			
		
		return lines;		
	}
}
