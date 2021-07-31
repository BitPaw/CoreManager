package de.BitFire.CoreManager.Modules.Tab;


public class TabSystemConfig
{
	public boolean UsePrefix;		
	public boolean UseSuffix;
	
	public int PingTabListDelayMs;	
	
	public String LocalPingSyntax;
	
	public String LowPingSyntax;
	public int LowPing;
	
	public String NormalPingSyntax;	
	public int NormalPing;
	
	public String MidPingSyntax;	
	public int MidPing;
	
	public String HiPingSyntax;
	public int HiPing;
	
	public String DeathPingSyntax;	
	public int DeathPing;

	public void LoadDefaults() 
	{
		 UsePrefix = false;		
		 UseSuffix = true;
		
		 PingTabListDelayMs = 60;		 
	
		 LocalPingSyntax = "&7[&f{PING}&7]";
		
		 LowPingSyntax = "&7[&b{PING}&7]";
		 LowPing = 20;
		
		 NormalPingSyntax = "&7[&a{PING}&7]";	
		 NormalPing = 150;
		
		 MidPingSyntax = "&7[&e{PING}&7]";	
		 MidPing = 500;
		
		 HiPingSyntax = "&7[&c{PING}&7]";
		 HiPing = 1000;
		
		 DeathPingSyntax = "&7[&4{PING}&7]";	
		 DeathPing = 1200;		
	}
}