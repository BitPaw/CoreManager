package de.BitFire.DataBase;

import java.sql.Connection;
import java.sql.Statement;

import de.BitFire.Configuration.Config;
import de.BitFire.DataBase.System.Database;
import de.BitFire.DataBase.System.MySQL;
import de.BitFire.DataBase.System.SQLite;

public class DataBaseData 
{
	public Database SQLDatabase;
	public CoreManagerMySQLMessages SQLMessages;
	public Connection SQLConnection;
	public Statement SQLStatement;
	
	public DataBaseData() 
	{		
		Config config = Config.Instance();
		String dataBaseFilePath = config.DataBase.FileName;
		String hostName = config.DataBase.LoginData.HostName;
		String portName = Integer.toString(config.DataBase.LoginData.Port);
		String dataBaseName = config.DataBase.LoginData.DataBase;
		String userName = config.DataBase.LoginData.UserName;
		String password = config.DataBase.LoginData.PassWord;	
		
		SQLMessages = CoreManagerMySQLMessages.Instance();

		switch(config.DataBase.Type)
		{
		case MySQL:
			SQLDatabase = new MySQL(hostName, portName, dataBaseName, userName, password);
			break;
			
		case SQLite:
			SQLDatabase = new SQLite(dataBaseFilePath);
			break;
			
		default:
			break;
		
		}		
	}
}
