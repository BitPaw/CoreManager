package de.SSC.CoreManager.Addons.DigitalClock;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class DigitalClockSystem 
{
	 private Map<Player, String> enableBuildUsers;
	    private Map<Player, String> enableMoveUsers;
	    private Map<String, Integer> usersClock;
	    private ArrayList<String> clocks;
	    private final Logger console;
	    private ClockMap clockTasks;
	    private FileConfiguration clocksConf;
	    private File clocksFile;
	    private int settings_width;
	    private boolean separately;
	    private boolean shouldRun;
	    private boolean versionWarning;
	    private boolean protectClocks;
	    private long generatorAccuracy;
	    private Generator generator;
	    
	  
	    public DigitalClockSystem() {
	        this.enableBuildUsers = new HashMap<Player, String>();
	        this.enableMoveUsers = new HashMap<Player, String>();
	        this.usersClock = new HashMap<String, Integer>();
	        this.clocks = new ArrayList<String>();
	        this.console = Logger.getLogger("Minecraft");
	        this.clockTasks = new ClockMap();
	        this.clocksConf = null;
	        this.clocksFile = null;
	        this.settings_width = 0;
	        this.generatorAccuracy = 0L;
	    }
	    
	    public void onEnable() 
	    {
	    	Server server = Bukkit.getServer();
	    	
	        generator = new Generator(this);
	       
	        server.getPluginManager().registerEvents((Listener)new Events(), (Plugin)this);
	        final Commands cmdExecutor = new Commands(null);
	        server.getPluginCommand("digitalclock").setExecutor((CommandExecutor)cmdExecutor);
	        server.getPluginCommand("dc").setExecutor((CommandExecutor)cmdExecutor);
	      
	    }
	    
	    public void onDisable()
	    {
	    	Server server = Bukkit.getServer();
	    	
	    	server.getScheduler().cancelTasks((Plugin)this);
	        this.console.info("[DigitalClock] Plugin has been disabled!");
	 
	    }
	    
	    public void RegisterTasks(JavaPlugin plugin, Server server)
	    {
	    	
	    }
	    
	    protected void runTasks() 
	    {	
	        for (final String name : this.getClocksL())
	        {
	            this.run(name);
	        }
	    }
	    
	    public void run(final String name)
	    {
	        if (!this.getClockTasks().containsKeyByClockName(name)) 
	        {
	            final Clock clock = Clock.loadClockByClockName(name);
	       
	            Server server = Bukkit.getServer();
	            final int task = server.getScheduler().scheduleSyncRepeatingTask((Plugin)Generator.getGenerator().getMain(), (Runnable)new Runnable() {
	               
	            	@Override
	                public void run() {
	                    if (DigitalClockSystem.this.getClocksConf().getKeys(false).contains(clock.getName())) 
	                    {
	                        Generator.getGenerator().generateOnce(clock);
	                    }
	                }
	            }, 0L, 20L);
	            this.getClockTasks().put(clock, task);
	        }
	    }
	    
	    public void reloadConf() 
	    {
	        settings_width = 3;
	        separately =  false;
	        shouldRun = false;
	        versionWarning =  true;
	        protectClocks = false;
	        generatorAccuracy = 2000L;
	    }
	    
	    public void getClocks() {
	        this.getClocksL().clear();
	        this.getUsersClock().clear();
	        for (final String name : this.getClocksConf().getKeys(false)) {
	            final Clock clock = Clock.loadClockByClockName(name);
	            if (this.getUsersClock().containsKey(clock.getCreator())) {
	                final int n = this.getUsersClock().get(clock.getCreator());
	                this.getUsersClock().remove(clock.getCreator());
	                this.getUsersClock().put(clock.getCreator(), n + 1);
	            }
	            else {
	                this.getUsersClock().put(clock.getCreator(), 1);
	            }
	            this.getClocksL().add(name);
	        }
	    }
	    
	    public Map<String, Integer> getUsersClock() {
	        return this.usersClock;
	    }
	    
	    public void setUsersClock(final Map<String, Integer> usersClock) {
	        this.usersClock = usersClock;
	    }
	    
	    public Map<Player, String> getEnableBuildUsers() {
	        return this.enableBuildUsers;
	    }
	    
	    public void setEnableBuildUsers(final Map<Player, String> enableBuildUsers) {
	        this.enableBuildUsers = enableBuildUsers;
	    }
	    
	    public FileConfiguration getClocksConf() {
	        return this.clocksConf;
	    }
	    
	    public void setClocksConf(final FileConfiguration clocksConf) {
	        this.clocksConf = clocksConf;
	    }
	    
	    public Map<Player, String> getEnableMoveUsers() {
	        return this.enableMoveUsers;
	    }
	    
	    public void setEnableMoveUsers(final Map<Player, String> enableMoveUsers) {
	        this.enableMoveUsers = enableMoveUsers;
	    }
	    
	    public ClockMap getClockTasks() {
	        return this.clockTasks;
	    }
	    
	    public void setClockTasks(final ClockMap clockTasks) {
	        this.clockTasks = clockTasks;
	    }
	    
	    public Generator getGenerator() {
	        return this.generator;
	    }
	    
	    public ArrayList<String> getClocksL() {
	        return this.clocks;
	    }
	    
	    public void setClocksL(final ArrayList<String> clocks) {
	        this.clocks = clocks;
	    }
	    
	    public static String getMessagePrefix() {
	        return "[DigitalClock]";
	    }
	    
	    public Logger getConsole() {
	        return this.console;
	    }
	    
	    public int getSettingsWidth() {
	        return this.settings_width;
	    }
	    
	    public boolean shouldRun() {
	        return this.shouldRun;
	    }
	    
	    public boolean versionWarning() {
	        return this.versionWarning;
	    }
	    
	    public boolean protectClocks() {
	        return this.protectClocks;
	    }
	    
	    public boolean shouldGenerateSeparately() {
	        return this.separately;
	    }
	    
	    public long getGeneratorAccuracy() {
	        return this.generatorAccuracy;
	    }
}
