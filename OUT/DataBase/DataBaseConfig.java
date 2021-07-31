package de.BitFire.DataBase;

import de.BitFire.Configuration.IConfig;
import de.BitFire.DataBase.System.DataBaseLoginData;
import de.BitFire.DataBase.System.DataBaseType;

public class DataBaseConfig implements IConfig
{
	public DataBaseLoginData LoginData;
	public String FileName;
	public DataBaseType Type;
	
	public DataBaseConfig()
	{
		LoginData = new DataBaseLoginData();
	}
	  
	public void LoadDefaults() 
	{
		LoginData = new DataBaseLoginData("localhost", 3306, "coremanager", "coremanager", "minecraft");
		FileName = "CoreManagerDataBase.db";
		Type = DataBaseType.SQLite;
		
		
		FileName = "plugins/CoreManager/" + FileName;
	}
}
