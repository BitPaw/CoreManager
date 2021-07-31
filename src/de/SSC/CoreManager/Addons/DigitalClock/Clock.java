package de.SSC.CoreManager.Addons.DigitalClock;


import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class Clock 
{

	private String clockName;
	  private String clockCreator;
	  private Material material;
	  private Material fillingMaterial = Material.AIR;
	  private boolean retrieveData = true;
	  private byte data = 0;
	  private byte fillingData = 0;
	  private int addMinutes = 0;
	  protected boolean showSeconds;
	  private boolean blinking;
	  private boolean blinkingChanger;
	  protected boolean ampm;
	  private int countdownto;
	  private ClockMode clockMode;
	  private ClockArea clockArea;
	  private int stopwatchtime;
	  
	  
	  
	  public Clock(String name, String playerName, Block block, Block playersBlock, int depth)
	  {
	    this.clockName = name;
	    this.clockCreator = playerName;
	    this.retrieveData = true;
	    this.material = block.getType();
	    this.data = block.getData();
	    this.clockMode = ClockMode.NORMAL;
	    this.clockArea = new ClockArea(this, block, playersBlock, depth);
	  }
	  
	  public void writeAndGenerate()
	  {
	    if (!isSomethingMissing())
	    {
	      write();
	      Generator.getGenerator().generateOnce(this);
	    }
	    else
	    {
	      throw new NullPointerException("Missing data found when generating clock '" + this.clockName + "'!");
	    }
	  }
	  
	  public void write()
	  {
	    if (!isSomethingMissing())
	    {
	      Generator.getGenerator().getMain().getClocksConf().set(this.clockName + ".creator", this.clockCreator);
	      Generator.getGenerator().getMain().getClocksConf().set(this.clockName + ".world", this.clockArea.getStartBlock().getWorld().getName());
	      Generator.getGenerator().getMain().getClocksConf().set(this.clockName + ".x", Integer.valueOf(this.clockArea.getStartBlock().getX()));
	      Generator.getGenerator().getMain().getClocksConf().set(this.clockName + ".y", Integer.valueOf(this.clockArea.getStartBlock().getY()));
	      Generator.getGenerator().getMain().getClocksConf().set(this.clockName + ".z", Integer.valueOf(this.clockArea.getStartBlock().getZ()));
	      Generator.getGenerator().getMain().getClocksConf().set(this.clockName + ".x2", Integer.valueOf(this.clockArea.getPlayersBlock().getX()));
	      Generator.getGenerator().getMain().getClocksConf().set(this.clockName + ".y2", Integer.valueOf(this.clockArea.getPlayersBlock().getY()));
	      Generator.getGenerator().getMain().getClocksConf().set(this.clockName + ".z2", Integer.valueOf(this.clockArea.getPlayersBlock().getZ()));
	      Generator.getGenerator().getMain().getClocksConf().set(this.clockName + ".depth", Integer.valueOf(this.clockArea.getDepth()));
	      Generator.getGenerator().getMain().getClocksConf().set(this.clockName + ".direction", this.clockArea.getDirection().name());
	      Generator.getGenerator().getMain().getClocksConf().set(this.clockName + ".material", this.material.name());
	      Generator.getGenerator().getMain().getClocksConf().set(this.clockName + ".data", Byte.valueOf(this.data));
	      Generator.getGenerator().getMain().getClocksConf().set(this.clockName + ".filling", this.fillingMaterial.name());
	      Generator.getGenerator().getMain().getClocksConf().set(this.clockName + ".fdata", Byte.valueOf(this.fillingData));
	      Generator.getGenerator().getMain().getClocksConf().set(this.clockName + ".add", Integer.valueOf(this.addMinutes));
	      Generator.getGenerator().getMain().getClocksConf().set(this.clockName + ".seconds", Boolean.valueOf(this.showSeconds));
	      Generator.getGenerator().getMain().getClocksConf().set(this.clockName + ".blinking", Boolean.valueOf(this.blinking));
	      Generator.getGenerator().getMain().getClocksConf().set(this.clockName + ".changer", Boolean.valueOf(this.blinkingChanger));
	      Generator.getGenerator().getMain().getClocksConf().set(this.clockName + ".ampm", Boolean.valueOf(this.ampm));
	      Generator.getGenerator().getMain().getClocksConf().set(this.clockName + ".cdt", Integer.valueOf(this.countdownto));
	      Generator.getGenerator().getMain().getClocksConf().set(this.clockName + ".swt", Integer.valueOf(this.stopwatchtime));
	      Generator.getGenerator().getMain().getClocksConf().set(this.clockName + ".mode", this.clockMode.name());
	    }
	    else
	    {
	      throw new NullPointerException("Missing data to write when saving the clock '" + this.clockName + "'!");
	    }
	  }
	  
	  public void teleportToClock(Player player)
	  {
	    player.teleport(getClockArea().getPlayersBlock().getLocation());
	  }
	  
	  public Material getFillingMaterial()
	  {
	    return this.fillingMaterial;
	  }
	  
	  public byte getFillingData()
	  {
	    return this.fillingData;
	  }
	  
	  public ClockMode getClockMode()
	  {
	    return this.clockMode;
	  }
	  
	  public void enableIngameTime(boolean b)
	  {
	    Generator.removeClockAndRestore(this);
	    this.clockMode = (b ? ClockMode.INGAMETIME : ClockMode.NORMAL);
	    write();
	  }
	  	  
	  public Material setFillingMaterial(String id, int md)
	  {
	    this.fillingMaterial = Material.getMaterial(id);
	    this.fillingData = ((byte)md);
	    write();
	    return this.fillingMaterial;
	  }
	  
	  public static int stopTask(String clockName)
	  {
		  Server server = Bukkit.getServer();
		  
	    int task = Generator.getGenerator().getMain().getClockTasks().getByClockName(clockName).intValue();
	    server.getScheduler().cancelTask(task);
	    Generator.getGenerator().getMain().getClockTasks().removeByClockName(clockName);
	    return task;
	  }
	  
	  public static void eraseCompletely(Clock clock)
	  {
	    if (Generator.getGenerator().getMain().getClockTasks().containsKeyByClockName(clock.getName())) {
	      stopTask(clock.getName());
	    }
	    Generator.removeClockAndRestore(clock);
	    clock.setRetrieveData(false);
	    Generator.getGenerator().getMain().getClocksConf().set(clock.getName(), null);
	  }
	  
	  public static Clock loadClockByClockName(String clockName)
	  {
	    if (Generator.getGenerator().getMain().getClocksConf().getKeys(false).contains(clockName))
	    {
	        Server server = Bukkit.getServer();	
	    	
	      Location loc = new Location(server.getWorld(Generator.getGenerator().getMain().getClocksConf().getString(clockName + ".world")), Generator.getGenerator().getMain().getClocksConf().getInt(clockName + ".x"), Generator.getGenerator().getMain().getClocksConf().getInt(clockName + ".y"), Generator.getGenerator().getMain().getClocksConf().getInt(clockName + ".z"));
	      Location loc2 = new Location(server.getWorld(Generator.getGenerator().getMain().getClocksConf().getString(clockName + ".world")), Generator.getGenerator().getMain().getClocksConf().getInt(clockName + ".x2"), Generator.getGenerator().getMain().getClocksConf().getInt(clockName + ".y2"), Generator.getGenerator().getMain().getClocksConf().getInt(clockName + ".z2"));
	        
	      
	      return new Clock(clockName, Generator.getGenerator().getMain().getClocksConf().getString(clockName + ".creator"), server.getWorld(Generator.getGenerator().getMain().getClocksConf().getString(clockName + ".world")).getBlockAt(loc), server.getWorld(Generator.getGenerator().getMain().getClocksConf().getString(clockName + ".world")).getBlockAt(loc2), Generator.getGenerator().getMain().getClocksConf().getInt(clockName + ".depth"));
	    }
	    return null;
	  }
	  
		  
	  public Material changeMaterial(String id, int md)
	  {		  
	    this.material = Material.getMaterial(id);
	    this.data = ((byte)md);
	    writeAndGenerate();
	    return this.material;
	  }
	  
	  public String getCreator()
	  {
	    return this.clockCreator;
	  }
	  
	  public void enableCountdown(boolean c)
	  {
	    this.clockMode = (c ? ClockMode.COUNTDOWN : ClockMode.NORMAL);
	    write();
	  }
	  
	  public void enableStopwatch(boolean s)
	  {
	    this.clockMode = (s ? ClockMode.STOPWATCH : ClockMode.NORMAL);
	    write();
	  }
	  
	  public int getCountdownTime()
	  {

	    return this.countdownto;
	  }
	  
	  public void setCountdownTime(int t)
	  {

	    this.countdownto = t;
	    write();
	  }
	  
	  public int getStopwatchTime()
	  {
	 
	    return this.stopwatchtime;
	  }
	  
	  public void setStopwatchTime(int t)
	  {
	    
	    this.stopwatchtime = t;
	    write();
	  }
	  
	  public void setCreator(String creator)
	  {
	    this.clockCreator = creator;
	    write();
	  }
	  
	  public void setShowingSeconds(boolean ss)
	  {
	    Generator.removeClockAndRestore(this);
	    this.showSeconds = ss;
	    write();
	    ClockArea.resetDimensions(this);
	  }
	  
	  public void setBlinking(boolean bl)
	  {
	    this.blinking = bl;
	    write();
	  }
	  
	  public boolean isBlinking()
	  {
	   
	    return this.blinking;
	  }
	  
	  public void setAMPM(boolean ap)
	  {
	    Generator.removeClockAndRestore(this);
	    this.ampm = ap;
	    write();
	    ClockArea.resetDimensions(this);
	  }
	  
	  public boolean getAMPM()
	  {
	
	    return this.ampm;
	  }
	  
	  protected void setBlinkingChanger(boolean blm)
	  {
	    this.blinkingChanger = blm;
	    write();
	  }
	  
	  protected boolean isBlinkingChangerON()
	  {
	
	    return this.blinkingChanger;
	  }
	  
	  public boolean shouldShowSeconds()
	  {
	
	    return this.showSeconds;
	  }
	  
	  public void addMinutes(int m)
	  {
	 
	    this.addMinutes = m;
	    write();
	  }
	  
	  public String getName()
	  {
	 
	    return this.clockName;
	  }
	  
	  public void setName(String clockName)
	  {
	    this.clockName = clockName;
	    write();
	  }
	  
	  public Material getMaterial()
	  {
	    return this.material;
	  }
	  
	  public byte getData()
	  {
	    return this.data;
	  }
	  
	  public int getAddMinutes()
	  {
	    return this.addMinutes;
	  }
	  
	  public void updateClockArea(ClockArea ca)
	  {
	    this.clockArea = ca;
	    write();
	  }
	  
	  public ClockArea getClockArea()
	  {
	    return this.clockArea;
	  }
	  
	  protected void setRetrieveData(boolean retrieveData)
	  {
	    this.retrieveData = retrieveData;
	  }
	  
	  protected boolean isSomethingMissing()
	  {
	    if ((this.clockCreator != null) && (this.clockName != null) && (this.clockArea != null) && (this.material != null) && (this.clockMode != null)) {
	      return false;
	    }
	    return true;
	  }


}
