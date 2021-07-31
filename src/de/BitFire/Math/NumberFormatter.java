package de.BitFire.Math;

public class NumberFormatter 
{
	public static String ConvertNumberToSmallText(final double value)
	{
		int roundedValue = (int)Math.round(value);
		int oneK = 1000;
		int oneM = 1000000;
		int oneB = 1000000000;
		int max = Integer.MAX_VALUE;
		String formatter;
		
		if(value > oneK)
		{				
			if(value > oneM)
			{
				if(value > oneB)
				{
					if(value >= max)
					{
						formatter = "INF";
					}
					else
					{
						roundedValue /= oneB;
						
						formatter = String.valueOf(roundedValue) + "B";		
					}				
				}
				else
				{
					roundedValue /= oneM;
					
					formatter = String.valueOf(roundedValue) + "M";		
				}				
			}
			else
			{
				roundedValue /= oneK;
				
				formatter = String.valueOf(roundedValue) + "K";			
			}
		}
		else
		{
			formatter = String.valueOf(roundedValue);
		}
		
		return formatter;
	}
}