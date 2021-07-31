package de.BitFire.Time;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class TimePoint 
{
	private Timestamp _timestamp;
		
	public TimePoint(Timestamp timestamp) 
	{
		_timestamp = timestamp;
	}
	
	public static TimePoint Now()
	{
		final LocalDateTime localDayTime = LocalDateTime.now();
		final Timestamp timestamp = Timestamp.valueOf(localDayTime);		
		final TimePoint timePoint = new TimePoint(timestamp);
		
		return timePoint;
	}
	
	@SuppressWarnings("deprecation")
	public String toString()
	{
		// (0 = Sunday, 1 = Monday, 2 = Tuesday, 3 = Wednesday, 4 = Thursday, 5 = Friday, 6 = Saturday) 
		final int day = _timestamp.getDate();
		final int month = _timestamp.getMonth() + 1;
		final int year = _timestamp.getYear() + 1900;
		final int hour = _timestamp.getHours();
		final int minutes = _timestamp.getMinutes();		
		final String doubleDigitFormatter = "%02d";
		final String quadDigitsFormatter = "%04d";
		
		String message = 
		
				String.format(doubleDigitFormatter, day) + 
				"." + 
				String.format(doubleDigitFormatter,month) + 
				"." + 
				String.format(quadDigitsFormatter,year) +
				" &7at&r " + 
				String.format(doubleDigitFormatter,hour) + 
				":" + 
				String.format(doubleDigitFormatter,minutes);
		 
		 
		 return message;
	}
	
	public String ToSQLCompatibleString()
	{
		return _timestamp.toString();
	}
	
	public int GetPassedDays()
	{
		final Timestamp now = Timestamp.valueOf(LocalDateTime.now());
		final long diffInMillies = Math.abs(_timestamp.getTime() - now.getTime());       
		final long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS); 
		final int numberOfDays = Math.toIntExact(days);
		
		return numberOfDays;
	}	
}
