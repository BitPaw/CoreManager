package de.SSC.CoreManager.System;

import java.net.InetAddress;

public class SystemInformation
{
	private Runtime runtime = null;
	private String filler = "-";

	public String CPUModel;
	public String AvailableProcessors;
	public String CurrentMemory;
	public String MaxMemory;
	public String RAMUsageIInPercent;
	public String HostName;
	public String IPAddress;

	public SystemInformation()
	{
		runtime = Runtime.getRuntime();

		CPUModel = filler;
		AvailableProcessors = filler;
		CurrentMemory = filler;
		MaxMemory = filler;
		RAMUsageIInPercent = filler;
		HostName = filler;
		IPAddress = filler;


		RefreshValues();
	}

	public void RefreshValues()
	{
		long freeRAM = runtime.freeMemory();
		long maxRAM = runtime.maxMemory();
		long currentRAM = maxRAM - freeRAM;
		float usage = ((float)currentRAM / (float)maxRAM) * 100;

		try
		{
			InetAddress ip = InetAddress.getLocalHost();
			HostName = ip.getHostName();
			IPAddress = ip.getHostAddress();
		}
		catch (Exception e)
		{

		}

		CPUModel = System.getenv("PROCESSOR_IDENTIFIER");
		//System.getenv("PROCESSOR_ARCHITECTURE");	
		//System.getenv("PROCESSOR_ARCHITEW6432");
		//System.getenv("NUMBER_OF_PROCESSORS");
		
		AvailableProcessors = Integer.toString(runtime.availableProcessors());
		CurrentMemory = Long.toString(currentRAM / 1000000) + " MB";
		MaxMemory = Long.toString(maxRAM / 1000000) + " MB";
		RAMUsageIInPercent = Float.toString(usage) + "%";
	}
}