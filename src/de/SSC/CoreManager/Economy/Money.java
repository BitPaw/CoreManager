package de.SSC.CoreManager.Economy;

public class Money 
{
	private double _value = 0;
	
	// Admin
	public void Add(float amount)
	{
		_value += amount;
	}
	
	public void Remove(float amount)
	{
		_value -= amount;
	}
	
	public double GetValue()
	{
		return _value;
	}
}
