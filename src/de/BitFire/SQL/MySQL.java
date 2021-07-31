package de.BitFire.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connects to and uses a MySQL database
 * 
 * @author -_Husky_-
 * @author tips48
 */
public class MySQL extends DataBase 
{
	private final DataBaseLoginData _loginData; 

	/**
	 * Creates a new MySQL instance
	 *
	 * @param hostname
	 *            Name of the host
	 * @param port
	 *            Port number
	 * @param username
	 *            Username
	 * @param password
	 *            Password
	 */
	public MySQL(String hostname, int port, String username,	String password)
	{
		this(hostname, port, null, username, password);
	}
	
	public MySQL(final DataBaseLoginData loginData)
	{
		_loginData = loginData;
	}

	/**
	 * Creates a new MySQL instance for a specific database
	 *
	 * @param hostname
	 *            Name of the host
	 * @param port
	 *            Port number
	 * @param database
	 *            Database name
	 * @param username
	 *            Username
	 * @param password
	 *            Password
	 */
	public MySQL(String hostname, int port, String database, String username, String password)
	{
		this(new DataBaseLoginData(hostname, port, database, username, password));
	}

	@Override
	public Connection openConnection() throws SQLException,	ClassNotFoundException 
	{
		if (checkConnection()) 
		{
			return connection;
		}	
		
		final String connectionURL = CreateConnectionURL();		
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(connectionURL, _loginData.UserName, _loginData.PassWord);
		
		return connection;
	}
	
	private String CreateConnectionURL()
	{
		String url = "jdbc:mysql://" + _loginData.HostName + ":" + _loginData.Port;
		 		
		if (_loginData.DataBase != null) 
		{
			url += "/" + _loginData.DataBase;
		}
		
		return url;
	}
}
