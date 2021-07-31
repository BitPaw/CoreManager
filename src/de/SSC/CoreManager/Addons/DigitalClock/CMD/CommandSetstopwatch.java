// 
// Decompiled by Procyon v0.5.36
// 

package de.SSC.CoreManager.Addons.DigitalClock.CMD;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Addons.DigitalClock.Clock;
import de.SSC.CoreManager.Addons.DigitalClock.ClockMode;
import de.SSC.CoreManager.Addons.DigitalClock.DigitalClockSystem;

public class CommandSetstopwatch implements ICommand
{
    @Override
    public int getArgsSize() {
        return 3;
    }
    
    @Override
    public String getPermissionName() {
        return "digitalclock.setstopwatch";
    }
    
    @Override
    public boolean specialCondition(final DigitalClockSystem main, final Player player, final String[] args) {
        final ClockMode cm = Clock.loadClockByClockName(args[1]).getClockMode();
        return cm == ClockMode.COUNTDOWN || cm == ClockMode.STOPWATCH;
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
        return ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Correct usage: '/" + usedCmd + " stopwatch <name> <seconds>'";
    }
    
    @Override
    public String reactNoPermissions() {
        return ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " You aren't allowed to use this command!";
    }
    
    @Override
    public void specialConditionProcess(final DigitalClockSystem main, final Player player, final String[] args) {
        player.sendMessage(ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Clock '" + args[1] + "' has already enabled countdown or stopwatch mode! Disable it with command 'disablecountdown <name>' or 'disablestopwatch <name>'.");
    }
    
    @Override
    public String reactBadClockList(final String clockName) {
        return ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Clock '" + clockName + "' not found!";
    }
    
    @Override
    public void process(final DigitalClockSystem main, final Player player, final String[] args)
    {
    	Server server = Bukkit.getServer();
    	
        final int secs = Integer.parseInt(args[2]);
        if (secs < 0) {
            player.sendMessage(ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Seconds value mustn't be less than 0!");
        }
        final Clock clock = Clock.loadClockByClockName(args[1]);
        clock.setStopwatchTime(secs);
        clock.enableStopwatch(true);
        if (main.getClockTasks().containsKeyByClockName(args[1])) {
        	server.getScheduler().cancelTask((int)main.getClockTasks().getByClockName(args[1]));
            main.getClockTasks().removeByClockName(args[1]);
        }
        final String[] num = main.getGenerator().getNumbersFromSeconds(clock.getStopwatchTime());
        final String hours = num[0];
        final String minutes = num[1];
        final String seconds = num[2];
        main.getGenerator().generatingSequence(clock, hours, minutes, seconds, null);
        player.sendMessage(ChatColor.DARK_GREEN + DigitalClockSystem.getMessagePrefix() + ChatColor.GREEN + " You have successfully set stopwatch time on clock '" + args[1] + "'. This clock is now stopped, run it by command 'runclock <name>'.");
    }
}
