package de.SSC.CoreManager.Addons.DigitalClock;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public class Generator 
{

	  private static Generator generator;
	  private DigitalClockSystem i;
	  
	  public Generator(DigitalClockSystem i)
	  {
	    this.i = i;
	    generator = this;
	  }
	  
	  public void generateOnce(Clock clock)
	  {
	    int mins = clock.getAddMinutes();
	    ClockMode cm = clock.getClockMode();
	    World w = clock.getClockArea().getStartBlock().getWorld();
	    
	    
	    String[] realnum = getRealNumbers(mins, null);
	    String hours = realnum[0];
	    String minutes = realnum[1];
	    String seconds = realnum[2];
	    int swt;
	    switch (cm)
	    {
	    case INGAMETIME: 
	      int cdt = clock.getCountdownTime();
	      if ((cdt != 0) && (cdt < 360000))
	      {
	        String[] num = getNumbersFromSeconds(cdt);
	        hours = num[0];
	        minutes = num[1];
	        seconds = num[2];
	        clock.setCountdownTime(cdt - 1);
	      }
	      else
	      {
	    	  Server server = Bukkit.getServer();
	    	  
	        Clock.stopTask(clock.getName());
	        clock.enableCountdown(false);
	        CountdownEndEvent event = new CountdownEndEvent(clock);
	        hours = "00";
	        minutes = "00";
	        seconds = "00";
	      }
	      break;
	    case NORMAL: 
	      hours = getIngameNumbers(w)[0];
	      minutes = getIngameNumbers(w)[1];
	      seconds = getIngameNumbers(w)[2];
	      break;
	    case STOPWATCH: 
	      swt = clock.getStopwatchTime();
	      String[] num = getNumbersFromSeconds(swt);
	      hours = num[0];
	      minutes = num[1];
	      seconds = num[2];
	      clock.setStopwatchTime(swt + 1);
	      break;
	    }
	    if ((this.i.shouldGenerateSeparately()) && (cm == ClockMode.NORMAL)) {
	      for (Player online : Bukkit.getServer().getOnlinePlayers())
	      {
	        InetAddress ia = online.getAddress().getAddress();
	        if ((!ia.getHostAddress().equals("127.0.0.1")) && (!ia.getHostAddress().startsWith("192.168.")) && (!ia.getHostAddress().startsWith("25.")))
	        {
	          TimeZone tz = getTimeZone(ia);
	          realnum = getRealNumbers(mins, tz);
	          hours = realnum[0];
	          minutes = realnum[1];
	          seconds = realnum[2];
	          generatingSequence(clock, hours, minutes, seconds, online);
	        }
	        else
	        {
	          generatingSequence(clock, hours, minutes, seconds, online);
	        }
	      }
	    } else {
	      generatingSequence(clock, hours, minutes, seconds, null);
	    }
	  }
	  
	
	  
	  private String[] getIngameNumbers(World w)
	  {
	    long time = w.getTime();
	    
	    if (time < 18000L) 
	    {
	      time += 6000L;
	    } else {
	      time -= 18000L;
	    }
	    
	    long m = time % 1000L * 60L / 1000L;
	    
	    
	    String minutes = Long.toString(m);
	    
	   
	    if (m < 10L) {
	      minutes = "0" + minutes;
	    }
	    String hours =  Long.toString(time / 1000L);
	    if (Integer.parseInt(hours) < 10) {
	      hours = "0" + hours;
	    }
	    String seconds = "00";
	    String[] result = { hours, minutes, seconds };
	    return result;
	  }
	  
	  public String[] getNumbersFromSeconds(int cdt)
	  {
	    String hours = "00";
	    String minutes = "00";
	    String seconds = "00";
	    int ho = (int)Math.floor(cdt / 3600);
	    int mi = (int)Math.floor((cdt - ho * 3600) / 60);
	    int se = cdt - ho * 3600 - mi * 60;
	    if ((ho < 100) && (mi < 60) && (se < 60))
	    {
	      hours = Integer.toString(ho);
	      minutes = Integer.toString(mi);
	      seconds = Integer.toString(se);
	      
	      if (ho < 10) {
	        hours = "0" + hours;
	      }
	     
	      if (mi < 10) {
	        minutes = "0" + minutes;
	      }
	
	      if (se < 10) {
	        seconds = "0" + seconds;
	      }
	    }
	    String[] result = { hours, minutes, seconds };
	    return result;
	  }
	  
	  public String[] getRealNumbers(int mins, TimeZone tz)
	  {
	    Calendar cal = Calendar.getInstance();
	    if (tz != null) {
	      cal.setTimeZone(tz);
	    }
	    cal.add(12, mins);
	    String hours = new SimpleDateFormat("HH").format(cal.getTime());
	    String minutes = new SimpleDateFormat("mm").format(cal.getTime());
	    String seconds = new SimpleDateFormat("ss").format(cal.getTime());
	    String[] result = { hours, minutes, seconds };
	    return result;
	  }
	  
	  public void generatingSequence(Clock clock, String phours, String minutes, String seconds, Player online)
	  {
	    ClockArea ca = clock.getClockArea();
	    Material m = clock.getMaterial();
	    Material f = clock.getFillingMaterial();
	    byte d = clock.getData();
	    byte fd = clock.getFillingData();
	    boolean ss = clock.shouldShowSeconds();
	    boolean bl = clock.isBlinking();
	    boolean blm = clock.isBlinkingChangerON();
	    boolean ampm = clock.getAMPM();
	    
	    String letter = null;
	    int newHours = 0;
	    String hours = phours;
	    if (ampm)
	    {
	      newHours = Integer.parseInt(hours);
	      if ((newHours > 11) && (newHours < 24))
	      {
	        if (newHours != 12) {
	          newHours -= 12;
	        }
	        hours = Integer.toString(newHours);
	        if (newHours < 10) {
	          hours = "0" + hours;
	        }
	        letter = "P";
	      }
	      else
	      {
	        if (newHours == 0) {
	          hours = "12";
	        }
	        letter = "A";
	      }
	    }
	    int w = getMain().getSettingsWidth();
	    
	    generate(0, Character.digit(hours.charAt(0), 10), ca, m, d, f, fd, online);
	    generate(3, w, ca, f, fd, f, fd, online);
	    generate(w + 1, hours.charAt(1) - '0', ca, m, d, f, fd, online);
	    if (bl)
	    {
	      if (blm)
	      {
	        generate(w * 2 + 1, 10, ca, f, d, f, fd, online);
	        clock.setBlinkingChanger(false);
	      }
	      else
	      {
	        generate(w * 2 + 1, 10, ca, m, d, f, fd, online);
	        clock.setBlinkingChanger(true);
	      }
	    }
	    else {
	      generate(w * 2 + 1, 10, ca, m, d, f, fd, online);
	    }
	    generate(w * 3 + 1, minutes.charAt(0) - '0', ca, m, d, f, fd, online);
	    generate(w * 4 + 1, w, ca, f, fd, f, fd, online);
	    generate(w * 4 + 2, minutes.charAt(1) - '0', ca, m, d, f, fd, online);
	    if (ss)
	    {
	      if (bl)
	      {
	        if (blm)
	        {
	          generate(w * 5 + 2, 10, ca, f, d, f, fd, online);
	          clock.setBlinkingChanger(false);
	        }
	        else
	        {
	          generate(w * 5 + 2, 10, ca, m, d, f, fd, online);
	          clock.setBlinkingChanger(true);
	        }
	      }
	      else {
	        generate(w * 5 + 2, 10, ca, m, d, f, fd, online);
	      }
	      generate(w * 6 + 2, seconds.charAt(0) - '0', ca, m, d, f, fd, online);
	      generate(w * 7 + 2, w, ca, f, fd, f, fd, online);
	      generate(w * 7 + 3, seconds.charAt(1) - '0', ca, m, d, f, fd, online);
	      if ((ampm) && (letter != null))
	      {
	        if (letter == "A") {
	          generate(w * 8 + 4, 11, ca, m, d, f, fd, online);
	        } else {
	          generate(w * 8 + 4, 12, ca, m, d, f, fd, online);
	        }
	        generate(w * 9 + 4, w, ca, f, fd, f, fd, online);
	        generate(w * 9 + 5, 13, ca, m, d, f, fd, online);
	      }
	    }
	    else if ((ampm) && (letter != null))
	    {
	      if (letter == "A") {
	        generate(w * 5 + 3, 11, ca, m, d, f, fd, online);
	      } else {
	        generate(w * 5 + 3, 12, ca, m, d, f, fd, online);
	      }
	      generate(w * 6 + 3, w, ca, f, fd, f, fd, online);
	      generate(w * 6 + 4, 13, ca, m, d, f, fd, online);
	    }
	  }
	  
	  public void generate(int i, int n, ClockArea ca, Material m, byte d, Material f, byte fd, Player online)
	  {
	    for (int q = 0; q < getGenerator().getMain().getSettingsWidth(); q++) {
	      for (int p = 0; p < ca.getHeight(); p++) {
	        for (int de = 0; de < ca.getDepth(); de++)
	        {
	          org.bukkit.Location newLoc = ca.getLocation(p, q, i, de);
	          if (!newLoc.getChunk().isLoaded()) {
	            return;
	          }
	          Block newBlock = newLoc.getBlock();
	          //String[] r = getGenerator().getMain().getConfig().getString("num" + n).split(";");
	          //String[] r2 = new StringBuffer(r[q]).reverse().toString().split(",");
	          BlockData md;
	          Material mat;

	        //  if (r2[p].equals("1"))
	         // {
	            mat = m;
	            //md = d;
	         // }
	         // else
	         // {
	            mat = f;
	           // md =  fd;
	         // }
	          if (online != null)
	          {
	           // online.sendBlockChange(newLoc, md);
	          }
	          else
	          {
	            newBlock.setType(mat);
	            //newBlock.setBlockData(md);
	          }
	        }
	      }
	    }
	  }
	  
	  public void generate(int i, ClockArea ca, Material f, BlockData fd)
	  {
	    for (int p = 0; p < ca.getHeight(); p++) {
	      for (int de = 0; de < ca.getDepth(); de++)
	      {
	        org.bukkit.Location newLoc = ca.getLocation(p, 0, i, de);
	        Block newBlock = newLoc.getBlock();
	        newBlock.setType(f);
	        newBlock.setBlockData(fd);
	      }
	    }
	  }
	  
	  public static void removeClockAndRestore(Clock clock)
	  {
		  /*
	    File file = new File(new File(getGenerator().getMain().getDataFolder(), "terrainbackups"), clock.getName() + ".txt");
	    
	    ArrayList<String> lines = new ArrayList();
	    try
	    {
	      BufferedReader br = new BufferedReader(new FileReader(file));
	      String line;
	      while ((line = br.readLine()) != null)
	      {
	        lines.add(line);
	      }
	      br.close();
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    int n = 0;
	    for (int u = 0; u < clock.getClockArea().getWidth(); u++) {
	      for (int v = 0; v < clock.getClockArea().getHeight(); v++) {
	        for (int de = 0; de < clock.getClockArea().getDepth(); de++)
	        {
	          if (n >= lines.size()) {
	            break;
	          }
	          String toSplit = (String)lines.get(n);
	          String[] data = toSplit.split(":");
	          org.bukkit.Location newLoc = clock.getClockArea().getLocation(v, u, 0, de);
	          Material mat = Material.valueOf(data[0]);
	          //BlockData md = (byte)Integer.parseInt(data[1]);
	          newLoc.getBlock().setType(mat);
	          //newLoc.getBlock().setBlockData(md);
	          if (getGenerator().getMain().shouldGenerateSeparately()) {
	            for (Player online : Bukkit.getServer().getOnlinePlayers()) {
	              //online.sendBlockChange(newLoc, mat, md);
	            }
	          }
	          n++;
	        }
	      }
	    }
	    file.delete();
	    
	    */
	  }
	  
	  public DigitalClockSystem getMain()
	  {
	    return this.i;
	  }
	  
	  private TimeZone getTimeZone(InetAddress ia)
	  {
	    TimeZone tz = null;
	    LookupService ls = null;
	   
	    /*
	    try
	    {
	      ls = new LookupService(new File(this.i.getDataFolder(), "GeoLiteCity.dat"), 1);
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    Location loc = ls.getLocation(ia);
	    if (loc != null) {
	      tz = TimeZone.getTimeZone(timeZone.timeZoneByCountryAndRegion(loc.countryCode, loc.region));
	    }
	    */
	    return tz;
	  }
	  
	  public static Generator getGenerator()
	  {
	    return generator;
	  }
}
