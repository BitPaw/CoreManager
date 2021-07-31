package de.SSC.API.MySQL.CoreSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import de.SSC.API.MySQL.DataBaseInformation;

/**
 * Connects to and uses a MySQL database
 * 
 * @author -_Husky_-
 * @author tips48
 */
public class MySQL extends Database
{
	private DataBaseInformation _dataBaseInformation = null;
	
	public MySQL(String hostname, String port, String database, String username, String password)
	{
		_dataBaseInformation = new DataBaseInformation();
		
		_dataBaseInformation.Hostname = hostname;
		_dataBaseInformation.Port = port;
		_dataBaseInformation.Database = database;
		_dataBaseInformation.User = username;
		_dataBaseInformation.Password = password;	
	}

	@Override
	public Connection OpenConnection() throws SQLException,	ClassNotFoundException 
	{
		if (CheckConnection()) 
		{
			return ConnectionInstance;
		}
		
		String connectionURL = "jdbc:mysql://"	+ _dataBaseInformation.Hostname + ":" + _dataBaseInformation.Port;
		if (_dataBaseInformation.Database != null) 
		{
			connectionURL = connectionURL + "/" + _dataBaseInformation.Database;
		}
		
		Class.forName("com.mysql.jdbc.Driver");
		ConnectionInstance = DriverManager.getConnection(connectionURL, _dataBaseInformation.User, _dataBaseInformation.Password);
		return ConnectionInstance;
	}
}
