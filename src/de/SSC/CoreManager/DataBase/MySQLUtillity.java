package de.SSC.CoreManager.DataBase;

public class MySQLUtillity 
{
	private static char c = ',';
	private static char ic = '\"';
	private static char nl = '\n';
	
  public static String BooleanToMySQLText(boolean value)
  {
	  String message;
	  
	  message = (value ? "true"  : "false") + c + nl ;
	  
	  return message;  
  }
  
  public static String StringToMySQLText(String text)
  {
       String message;
	  
	  message = ic + text + ic + c + nl;
	  
	  return message;  
  }
  
  public static String LastStringToMySQLText(String text)
  {
       String message;
	  
	  message = ic + text + ic + nl;
	  
	  return message;  
  }
  
  public static String FloatToMySQLText(float value)
  {
       String message;
	  
	  message = String.valueOf(value) + c + nl;
	  
	  return message;  
  }
  
  public static String LastFloatToMySQLText(float value)
  {
       String message;
	  
	  message = String.valueOf(value) + nl;
	  
	  return message;  
  }
}
