package de.SSC.CoreManager.Systems.Money;

public class Money 
{
	private float _value = 0;
	
	
public Money(float money)
{
	_value = money;
}
	
	public boolean Add(float amount)
	{
		boolean succsessful = false;
		
		if(amount > 0)
		{
			
		}
		else if(amount == 0)
		{
			
		}		
		
		return succsessful;
	}
	
	public boolean Remove(float amount)
	{
		boolean succsessful = false;
		
		if(amount > 0)
		{
			
		}
		else if(amount == 0)
		{
			
		}		
		
		return succsessful;
	}
	
	public double GetValue()
	{
		return _value;
	}
}
