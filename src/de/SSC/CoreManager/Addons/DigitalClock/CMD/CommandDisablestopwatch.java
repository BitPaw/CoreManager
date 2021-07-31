// 
// Decompiled by Procyon v0.5.36
// 

package de.SSC.CoreManager.Addons.DigitalClock.CMD;

import java.util.TimeZone;
import org.bukkit.event.Event;

import de.SSC.CoreManager.Addons.DigitalClock.Clock;
import de.SSC.CoreManager.Addons.DigitalClock.ClockMode;
import de.SSC.CoreManager.Addons.DigitalClock.DigitalClockSystem;
import de.SSC.CoreManager.Addons.DigitalClock.StopwatchEndEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;


public class CommandDisablestopwatch implements ICommand
{
    @Override
    public int getArgsSize() {
        return 2;
    }
    
    @Override
    public String getPermissionName() {
        return "digitalclock.disablestopwatch";
    }
    
    @Override
    public boolean specialCondition(final DigitalClockSystem main, final Player player, final String[] args) {
        return Clock.loadClockByClockName(args[1]).getClockMode() != ClockMode.STOPWATCH;
    }
    
    @Override
    public boolean checkClockExistence() {
        return true;
    }
    
    @Override
    public boolean neededClockExistenceValue() {
        return true;
    }
    
    @Override
    public String reactBadArgsSize(final String usedCmd) {
        return ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Correct usage: '/" + usedCmd + " disablestopwatch <name>'";
    }
    
    @Override
    public String reactNoPermissions() {
        return ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " You aren't allowed to use this command!";
    }
    
    @Override
    public void specialConditionProcess(final DigitalClockSystem main, final Player player, final String[] args) {
        player.sendMessage(ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " This clock hasn't enabled stopwatch mode!");
    }
    
    @Override
    public String reactBadClockList(final String clockName) {
        return ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Clock '" + clockName + "' not found!";
    }
    
    @Override
    public void process(final DigitalClockSystem main, final Player player, final String[] args)
    {
    	Server server = Bukkit.getServer();
    	
        final Clock clock = Clock.loadClockByClockName(args[1]);
        clock.enableStopwatch(false);
        server.getPluginManager().callEvent((Event)new StopwatchEndEvent(clock, clock.getStopwatchTime()));
        if (main.getClockTasks().containsKeyByClockName(args[1])) {
        	server.getScheduler().cancelTask((int)main.getClockTasks().getByClockName(args[1]));
            main.getClockTasks().removeByClockName(args[1]);
        }
        final String hours = main.getGenerator().getRealNumbers(clock.getAddMinutes(), null)[0];
        final String minutes = main.getGenerator().getRealNumbers(clock.getAddMinutes(), null)[1];
        final String seconds = main.getGenerator().getRealNumbers(clock.getAddMinutes(), null)[2];
        main.getGenerator().generatingSequence(clock, hours, minutes, seconds, null);
        player.sendMessage(ChatColor.DARK_GREEN + DigitalClockSystem.getMessagePrefix() + ChatColor.GREEN + " You have successfully disabled stopwatch mode on clock '" + args[1] + "'. This clock is now stopped, run it by command 'runclock <name>'.");
    }
}
