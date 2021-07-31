package de.BitFire.SQL.SQLite;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import de.BitFire.SQL.DataBase;

/**
 * Connects to and uses a SQLite database
 */
public class SQLite extends DataBase 
{
    private final String _dbFilePath;

    /**
     * Creates a new SQLite instance
     *
     * @param Location of the Database (Must end in .db)
     */
    public SQLite(String dbFilePath) 
    {
    	_dbFilePath = dbFilePath;
    }

    @Override
    public Connection openConnection() throws SQLException,  ClassNotFoundException 
    {
        File file = new File(_dbFilePath);
        String dataBasePath = "jdbc:sqlite:"  + _dbFilePath; // jdbc:sqlite:sqlite_database_file_path
        final boolean doesNotFileExist = !(file.exists());
        
        if (doesNotFileExist) 
        {
            try 
            {
                file.createNewFile();
            }
            catch (IOException e) 
            {
                System.out.println("Unable to create database!");
            }
        }
        
        connection = DriverManager.getConnection(dataBasePath);
        
        return connection;
    }
}