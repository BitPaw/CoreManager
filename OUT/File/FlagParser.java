package de.BitFire.File;

import java.util.HashMap;
import java.util.Map;

public final class FlagParser 
{
	private final static char _trueChar = 't';
	private final static char _falseChar = 'f';
	private final static char _tupleSeperator = ':';
	private final static char _valueSeperator = ',';
	
	private final static void WriteToConsole(final String string)
	{
		System.out.print(string);
	}
	
	public final static char GetCorospondingChar(FlagType flagType)
	{
		switch(flagType)
		{
		case InfinityHeight:
			return 'H';
			
		case InfinitySize:
			return 'S';
			
		case PermissionToLeave:
			return 'L';
			
		case PermissionToModify:
			return 'M';
			
		case PermissionToTrespass:
			return 'T';
			
		case PermissionToUse:
			return 'P';
			
		default:
			break;		
		}
		
		return '?';
	}
	
	public final static Map<Character, Boolean> ToList(final String dataString)
	{
		Map<Character, Boolean> data = new HashMap<Character, Boolean>();
		
		WriteToConsole("-----[FlagParser to Map]-----");
		
		WriteToConsole(" Beginn Parsing : " + dataString);
		
		String[] subStrings = dataString.split(String.valueOf(_valueSeperator));	
		
		for(String sub : subStrings)
		{
			WriteToConsole(" SubParsing : " + sub);
			
			final char character = String.valueOf(sub.charAt(0)).toUpperCase().charAt(0);
			final boolean value = sub.charAt(2) == _trueChar;		
			
			WriteToConsole(" Add entity <" + character +  ":" + value + ">");
			
			data.put(character, value);
		}
		
		WriteToConsole("--------------------------------");
		
		return data;
	}
	
	public final static String ToString(final Map<Character, Boolean> data)
	{
		String dataString = "";
		int i = 0;		
		
		WriteToConsole("-----[FlagParser to String]-----");
		
		for(Map.Entry<Character, Boolean> entry : data.entrySet())
		{
			final String character = String.valueOf(entry.getKey()).toUpperCase();			
			final String bool = String.valueOf(entry.getValue() ? _trueChar : _falseChar);
			
			WriteToConsole(" Parse entity <" + character +  ":" + bool + ">");
			
			if(i++ != 0)
			{
				dataString += _valueSeperator;
			}	
			
			dataString += character + _tupleSeperator + bool;
		}		
		WriteToConsole("-----[Finished]-----------------");
		
		WriteToConsole(" Parsed to : " + dataString);
		
		WriteToConsole("--------------------------------");
		
		return dataString;
	}
}
