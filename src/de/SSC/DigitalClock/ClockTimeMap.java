package de.SSC.DigitalClock;

public class ClockTimeMap 
{
	public final int Hour;
	public final int Minute;
	
	final boolean[][] HourDigitOne;
	final boolean[][] HourDigitTwo;
	final boolean[][] Divider;
	final boolean[][] MinuteDigitOne;
	final boolean[][] MinuteDigitTwo;
	
	public ClockTimeMap(int hour, int minute)
	{
		 Hour = hour;
		 Minute = minute;
		 
		 DigitalNumber digitalNumber = new DigitalNumber();
			String integerText;
			int firstDigit;
			int secondDigit;
			
			Divider = digitalNumber._doublePoint;
			
			if(Hour > 9)
			{
				integerText = Integer.toString(Hour);
				firstDigit = Character.getNumericValue(integerText.charAt(0));
				secondDigit = Character.getNumericValue(integerText.charAt(1));			
				
				HourDigitOne = digitalNumber.GetNumberLayout(firstDigit);
				HourDigitTwo = digitalNumber.GetNumberLayout(secondDigit);
			}
			else
			{
				HourDigitOne = digitalNumber.GetNumberLayout(0);
				HourDigitTwo = digitalNumber.GetNumberLayout(Hour);
			}
			
			if(Minute > 9)
			{
				integerText = Integer.toString(Minute);
				firstDigit = Character.getNumericValue(integerText.charAt(0));
				secondDigit = Character.getNumericValue(integerText.charAt(1));			
				
				MinuteDigitOne = digitalNumber.GetNumberLayout(firstDigit);
				MinuteDigitTwo = digitalNumber.GetNumberLayout(secondDigit);
			}
			else			
			{
				MinuteDigitOne = digitalNumber.GetNumberLayout(0);
				MinuteDigitTwo = digitalNumber.GetNumberLayout(Minute);
			}
	}
}
