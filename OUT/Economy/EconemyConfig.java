package de.BitFire.Economy;

import de.BitFire.Configuration.IConfig;

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
