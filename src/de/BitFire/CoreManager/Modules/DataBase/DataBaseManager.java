package de.BitFire.CoreManager.Modules.DataBase;

import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.BitFire.SQL.DataBase;
import de.BitFire.SQL.SQLite.SQLite;
import de.BitFire.SQL.SQLite.SQLiteDataTypes;

public class DataBaseManager
{
	private static Connection _sqlConnection;
	private static DataBase _database;
	private static String _filePath;
	
	static
	{
		_filePath = "plugins/CoreManager/CoreManagerDataBase.db";
		_database = new SQLite(_filePath);
	}	
	
	public static boolean IsConnected()
	{
		return _database.checkConnection();
	}
	
	private static void OpenDataBase()
	{
		try 
		{
			_sqlConnection = _database.openConnection();
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}	
	}

	private static void CloseDataBase()
	{
		try 
		{
			_database.closeConnection();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	public static boolean CheckMySQLCommand(final String sqlMessage)
	{		
		if(sqlMessage == null)
		{
			return false;
		}
		
		if (sqlMessage.isEmpty())
		{
			return false;
		}
	
		return true;
	}

	private static void TryExecuteCommand(final String sqlMessage)
	{
		try 
		{
			Statement statement = _sqlConnection.createStatement();
			statement.execute(sqlMessage);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}		
	}

	public static void SendSQLCommandData(final String sqlCommand)
	{
		final boolean valid = CheckMySQLCommand(sqlCommand);

		if(!valid)
		{
			//throw new Exception();
		}
		
		OpenDataBase();

		TryExecuteCommand(sqlCommand);

		CloseDataBase();
	}
	
	public static <E extends ICloneable> List<E> LoadObject(E type)
	{		
		final Class<? extends Object> classType = type.getClass();
		final Field[] fields = classType.getFields();
		final String className = type.getClass().getSimpleName();
		final String sqlCommand = "select * from " + className;
		final List<E> typeList = new ArrayList<E>();	

		try
		{
			final ResultSet resultSet = _database.querySQL(sqlCommand);
			
			while(resultSet.next())
			{				
				for(Field field : fields)
				{		
					final String fieldName = field.getName();
					final SQLiteDataTypes dataType = GetCorrespondingType(field);	
					
					switch(dataType)
					{
					case Blob:
						field.set(type, resultSet.getBlob(fieldName)); 
						break;
					
					case Integer:
						int intValue = resultSet.getInt(fieldName);
												
						field.setInt(type, intValue); 
						break;
					
					case Numeric:				
					case Real:
						
						final double doubleValue = resultSet.getDouble(fieldName);
						
								
						field.setDouble(type, doubleValue); 
						break;
					
					case Text:
						field.set(type, resultSet.getString(fieldName)); 
						break;
						
					case Boolean:
						final boolean boolValue = resultSet.getBoolean(fieldName);
												
						field.set(type, boolValue); 
						break;	
					}
				}			
				
				@SuppressWarnings("unchecked")
				E newZ = (E) type.Clone(); // Copy to store it
				
				typeList.add(newZ);	// Store
			}
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}		
		
		return typeList;
	}
	
	private static SQLiteDataTypes GetCorrespondingType(final Field fields)
	{
		final String className = fields.getType().getSimpleName();
		
		// Integer
		{			
			final String integerClassName = int.class.getSimpleName();
	
			if(className == integerClassName)
			{
				return SQLiteDataTypes.Integer;
			}
		}
	
		// Boolean
		{
			final String booleanClassName = boolean.class.getSimpleName();
			
			if(className == booleanClassName)
			{
				return SQLiteDataTypes.Boolean;
			}
		}
		
		// Text
		{			
			final String textClassName =  String.class.getSimpleName();
						
			if(className.compareToIgnoreCase(textClassName) == 0)
			{
				return SQLiteDataTypes.Text;
			}
		}
		
		// Real
		{
			final String textClassName =  double.class.getSimpleName();
			
			if(className == textClassName)
			{
				return SQLiteDataTypes.Real;
			}
		}
		
		/*
		// Numeric
		{
			//final String numericClassName =  Numeric.class.getClass().getTypeName();
			
			if(className == numericClassName)
			{
				return SQLiteDataTypes.Numeric;
			}
		}
		*/
		
		// Blob
		{
			final String blobClassName =  Blob.class.getSimpleName();
			
			if(className == blobClassName)
			{
				return SQLiteDataTypes.Blob;
			}
		}
		
		throw new IllegalArgumentException("Unsupported Type : " + className);
	}
}
