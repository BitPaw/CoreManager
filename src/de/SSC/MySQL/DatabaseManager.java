package de.SSC.MySQL;

import java.sql.Connection;
import java.sql.Statement;

import de.SSC.CoreManager.Color.Logger;

public class DatabaseManager 
{
	private MySQL _mySQL = null;
	
	private String hostname = "localhost";
	private String port = "3306"; 
	private String database = "cakecraft";
	private String username = "root";
	private String password = "";	
	
	public DatabaseManager()
	{
		_mySQL = new MySQL(hostname, port, database, username, password);			
	}
	
	public void Write(String sqlMessage)
	{
		Connection connection = null;
		Statement statement = null; 
		
		try
		{
			connection =_mySQL.openConnection();			
		}		
		catch(Exception e)
		{
			Logger.WriteWarning("MySQL connection failed!\n" +	e.getMessage());
			return;
		}
		
		Logger.WriteInfo("MySQL connected successfully");
		
		try
		{
			statement = connection.createStatement();
			statement.executeUpdate(sqlMessage);
		}
		catch(Exception e)
		{
			Logger.WriteWarning("MySQL statement error!");		
			Logger.WriteWarning(e.getMessage());
		}
		
		try
		{
			_mySQL.closeConnection();
		}
		catch(Exception e)
		{
			Logger.WriteWarning("MySQL closing error");	
			Logger.WriteWarning(e.getMessage());
		}		
	}
	
	

}
