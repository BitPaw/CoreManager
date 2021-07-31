package de.BitFire.DataBase.System;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import de.BitFire.DataBase.System.Database;

/**
 * Connects to and uses a SQLite database
 *
 * @author tips48
 */
public class SQLite extends Database 
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
        
        if (!(file.exists())) 
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
        
        /*
        if (checkConnection())
        {
            return connection;
        }
        */
        
       // Class.forName("org.sqlite.JDBC");
        
        connection = DriverManager.getConnection(dataBasePath);
        
        return connection;
    }
}