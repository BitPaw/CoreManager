package de.BitFire.Time;

public class SecondTickTimer extends BaseTickTimer implements Runnable
{
	private static SecondTickTimer _instance;
	
	private SecondTickTimer()
	{
		super(20);
		_instance = this;
	}
	
	public static SecondTickTimer Instance()
	{
		return _instance == null ? new SecondTickTimer() : _instance;
	}
}
