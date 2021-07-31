package de.BitFire.CoreManager.Modules.Sign;

public class SignShop 
{
	public final int ID;
	public final int SignID;
	public final int Amount;
	public final double BuyPrice;
	public final double SalePrice;
	public final int BlockID;
	public final int SubID;
	public final int OwnerID;
	
	public SignShop(int id, int signID, int amount, double buyPrice, double dalePrice, int blockID, int subID, int ownerID)
	{
		 ID = id;
		 SignID = signID;
		 Amount = amount;
		 BuyPrice = buyPrice;
		 SalePrice = dalePrice;
		 BlockID = blockID;
		 SubID = subID;
		 OwnerID = ownerID;	
	}
}