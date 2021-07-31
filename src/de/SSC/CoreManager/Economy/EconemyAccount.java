package de.SSC.CoreManager.Economy;

import de.SSC.CoreManager.Economy.Exception.NegativeAmountException;
import de.SSC.CoreManager.Economy.Exception.NotEnoghMoneyException;
import de.SSC.CoreManager.Economy.Exception.NullAmountException;
import de.SSC.CoreManager.Economy.Exception.TooMuchMoneyException;

public class EconemyAccount 
{ 
	private float _value = 0;	
	
	public EconemyAccount(float money)
	{
		_value = money;
	}
	
	public boolean CanPay(float amount)
	{
		return (_value - amount) >= 0;
	}
	
	public void Add(float amount) throws 	NullAmountException, 
											NegativeAmountException, 
											TooMuchMoneyException
	{		
		if(amount == 0)
		{
			throw new NullAmountException();
		}
		
		if(amount < 0)
		{
			throw new NegativeAmountException(amount);
		}
		
		if((_value + amount) > Float.MAX_VALUE)
		{
			_value = Float.MAX_VALUE;
			
			throw new TooMuchMoneyException();
		}
		else
		{
			_value += amount; 
		}
	}
	
	public void Reduce(float amount) throws NullAmountException, 
											NegativeAmountException, 
											NotEnoghMoneyException
	{		
		if(amount == 0)
		{
			throw new NullAmountException();
		}
		
		if(amount < 0)
		{
			throw new NegativeAmountException(amount);
		}
		
		if(CanPay(amount))
		{			
			_value -= amount; 
		}
		else
		{			
			throw new NotEnoghMoneyException(_value, amount);
		}
		
	}
	
	public float GetValue()
	{
		return _value;
	}
}
