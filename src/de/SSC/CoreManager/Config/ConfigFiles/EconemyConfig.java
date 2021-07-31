package de.SSC.CoreManager.Config.ConfigFiles;

import de.SSC.CoreManager.Config.IConfig;

public class EconemyConfig implements IConfig
{
  public float StartMoney;
  public String CurrencySymbol;
	

	public void LoadDefaults() 
	{
		StartMoney = 250;
		CurrencySymbol = "$";
	}

}
