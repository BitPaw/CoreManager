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

public class CommandSetcountdown implements ICommand
{
    @Override
    public int getArgsSize() {
        return 3;
    }
    
    @Override
    public String getPermissionName() {
        return "digitalclock.setcountdown";
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
        return ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Correct usage: '/" + usedCmd + " setcountdown <name> <seconds>'";
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
    public void process(final DigitalClockSystem main, final Player player, final String[] args) {
        if (Integer.parseInt(args[2]) >= 360000 || Integer.parseInt(args[2]) <= 0) {
            player.sendMessage(ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Seconds must be number between 0 and 359999!");
        }
        else 
        {
          Server server = Bukkit.getServer();
        	
            final Clock clock = Clock.loadClockByClockName(args[1]);
            clock.setCountdownTime(Integer.parseInt(args[2]));
            clock.enableCountdown(true);
            if (main.getClockTasks().containsKeyByClockName(args[1])) {
            	server.getScheduler().cancelTask((int)main.getClockTasks().getByClockName(args[1]));
                main.getClockTasks().removeByClockName(args[1]);
            }
            final String[] num = main.getGenerator().getNumbersFromSeconds(clock.getCountdownTime());
            final String hours = num[0];
            final String minutes = num[1];
            final String seconds = num[2];
            main.getGenerator().generatingSequence(clock, hours, minutes, seconds, null);
            player.sendMessage(ChatColor.DARK_GREEN + DigitalClockSystem.getMessagePrefix() + ChatColor.GREEN + " You have successfully set countdown time on clock '" + args[1] + "' to " + hours + ":" + minutes + ":" + seconds + ". This clock is now stopped, run it by command 'runclock <name>'.");
        }
    }
}
