package de.BitFire.DataBase.System;

public class DataBaseLoginData 
{
	public final String HostName;
	public final int Port;
	public final String DataBase;
	public final String UserName;
	public final String PassWord;
	
	public DataBaseLoginData()
	{
		this("127.0.0.1", 3306, "coremanager", "root", "");
	}
	
	public DataBaseLoginData(String hostName, int port, String dataBase, String userName, String passWord)
	{
		HostName = hostName;
		Port = port;
		DataBase = dataBase;
		UserName = userName;
		PassWord = passWord;
	}	
}
