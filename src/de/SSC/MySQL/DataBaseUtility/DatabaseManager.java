package de.SSC.MySQL.DataBaseUtility;

import java.sql.Connection;
import java.sql.Statement;

import org.bukkit.entity.Player;

import de.SSC.CoreManager.Color.Logger;
import de.SSC.MySQL.MySQL;

public class DatabaseManager 
{
	private MySQL _mySQL = null;
	
	private String hostname = "localhost";
	private String port = "3306"; 
	private String database = "cakecraft";
	private String username = "root";
	private String password = "";	
	
	private class SQLMessages
	{
		public static final String GetPlayers = "select ";		
	}
	
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
		
		Logger.DataBaseInfo("MySQL connected successfully");
		
		try
		{
			statement = connection.createStatement();
			statement.executeUpdate(sqlMessage);
		}
		catch(Exception e)
		{
			Logger.WriteWarning("MySQL statement error!");		
			Logger.WriteWarning(e.getMessage());
			Logger.WriteWarning("\n--- Message --- \n" + sqlMessage + "\n--------------\n");
		}
		
		try
		{
			_mySQL.closeConnection();
		}
		catch(Exception e)
		{
			Logger.DataBaseInfo("MySQL closing error");	
			Logger.WriteWarning(e.getMessage());
		}		
	}
	
	public void RegisterNewPlayer(Player player)
	{
		try
		{
			Logger.DataBaseInfo("Registing new Player");
			DataSetPlayer dataSetPlayer = new DataSetPlayer(player, 0, null);
			String sqlCommand = dataSetPlayer.GenerateSQLCode();
			Write(sqlCommand);	
		}
		catch(Exception e)
		{
			Logger.WriteWarning("RegisterNewPlayer Error");
		}
		
	}
	
	public void CreateDataSet()
	{
		
	}
	
	public void GetPlayers()
	{
		Write(SQLMessages.GetPlayers);
	}

}
