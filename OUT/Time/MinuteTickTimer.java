package de.BitFire.Time;

public class MinuteTickTimer extends BaseTickTimer implements Runnable
{
	private static MinuteTickTimer _instance;
	
	private MinuteTickTimer()
	{
		super(1200);
		_instance = this;
	}
	
	public static MinuteTickTimer Instance()
	{
		return _instance == null ? new MinuteTickTimer() : _instance;
	}
}
