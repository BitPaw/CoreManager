package de.SSC.API.MySQL.CoreSystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Abstract Database class, serves as a base for any connection method (MySQL,
 * SQLite, etc.)
 * 
 * @author -_Husky_-
 * @author tips48
 */
public abstract class Database 
{
	protected Connection ConnectionInstance;

	/**
	 * Creates a new Database
	 *
	 */
	protected Database() 
	{
		this.ConnectionInstance = null;
	}

	/**
	 * Opens a connection with the database
	 * 
	 * @return Opened connection
	 * @throws SQLException
	 *             if the connection can not be opened
	 * @throws ClassNotFoundException
	 *             if the driver cannot be found
	 */
	public abstract Connection OpenConnection() throws SQLException, ClassNotFoundException;

	/**
	 * Checks if a connection is open with the database
	 * 
	 * @return true if the connection is open
	 * @throws SQLException
	 *             if the connection cannot be checked
	 */
	public boolean CheckConnection() throws SQLException 
	{
		return ConnectionInstance != null && !ConnectionInstance.isClosed();
	}

	/**
	 * Gets the connection with the database
	 * 
	 * @return Connection with the database, null if none
	 */
	public Connection GetConnection() 
	{
		return ConnectionInstance;
	}

	/**
	 * Closes the connection with the database
	 * 
	 * @return true if successful
	 * @throws SQLException
	 *             if the connection cannot be closed
	 */
	public boolean CloseConnection() throws SQLException 
	{
		if (ConnectionInstance == null) 
		{
			return false;
		}
		ConnectionInstance.close();
		return true;
	}


	/**
	 * Executes a SQL Query<br>
	 * 
	 * If the connection is closed, it will be opened
	 * 
	 * @param query
	 *            Query to be run
	 * @return the results of the query
	 * @throws SQLException
	 *             If the query cannot be executed
	 * @throws ClassNotFoundException
	 *             If the driver cannot be found; see {@link #openConnection()}
	 */
	public ResultSet QuerySQL(String query) throws SQLException, ClassNotFoundException 
	{
		if (!CheckConnection()) 
		{
			OpenConnection();
		}

		Statement statement = ConnectionInstance.createStatement();

		ResultSet result = statement.executeQuery(query);

		return result;
	}

	/**
	 * Executes an Update SQL Query<br>
	 * See {@link java.sql.Statement#executeUpdate(String)}<br>
	 * If the connection is closed, it will be opened
	 * 
	 * @param query
	 *            Query to be run
	 * @return Result Code, see {@link java.sql.Statement#executeUpdate(String)}
	 * @throws SQLException
	 *             If the query cannot be executed
	 * @throws ClassNotFoundException
	 *             If the driver cannot be found; see {@link #openConnection()}
	 */
	public int UpdateSQL(String query) throws SQLException,	ClassNotFoundException 
	{
		Statement statement;
		int result;
		
		if (!CheckConnection()) 
		{
			OpenConnection();
		}

		statement = ConnectionInstance.createStatement();
		result = statement.executeUpdate(query);

		return result;
	}
}