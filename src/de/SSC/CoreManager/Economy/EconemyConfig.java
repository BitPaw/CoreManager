package de.SSC.CoreManager.Economy;

import de.SSC.CoreManager.Config.IConfig;

public class EconemyConfig implements IConfig
{
  public float StartMoney;
  public String CurrencySymbol;
	
  public EconemyConfig()
  {
	  LoadDefaults();
  }
  

	public void LoadDefaults() 
	{
		StartMoney = 250;
		CurrencySymbol = "$";
	}

}
