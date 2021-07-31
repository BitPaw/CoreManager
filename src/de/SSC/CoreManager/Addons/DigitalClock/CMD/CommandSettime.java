// 
// Decompiled by Procyon v0.5.36
// 

package de.SSC.CoreManager.Addons.DigitalClock.CMD;

import java.util.TimeZone;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Addons.DigitalClock.Clock;
import de.SSC.CoreManager.Addons.DigitalClock.DigitalClockSystem;

public class CommandSettime implements ICommand
{
    @Override
    public int getArgsSize() {
        return 3;
    }
    
    @Override
    public String getPermissionName() {
        return "digitalclock.settime";
    }
    
    @Override
    public boolean specialCondition(final DigitalClockSystem main, final Player player, final String[] args) {
        return false;
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
        return ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Correct usage: '/" + usedCmd + " settime <name> <HH:MM>'";
    }
    
    @Override
    public String reactNoPermissions() {
        return ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " You aren't allowed to use this command!";
    }
    
    @Override
    public void specialConditionProcess(final DigitalClockSystem main, final Player player, final String[] args) {
    }
    
    @Override
    public String reactBadClockList(final String clockName) {
        return ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Clock '" + clockName + "' not found!";
    }
    
    @Override
    public void process(final DigitalClockSystem main, final Player player, final String[] args) {
        final String input = args[2];
        if (input.length() == 5 && Character.isDigit(input.charAt(0)) && Character.isDigit(input.charAt(1)) && Character.isDigit(input.charAt(3)) && Character.isDigit(input.charAt(4)) && input.charAt(2) == ':') {
            final int hours = Integer.parseInt(Character.toString(input.charAt(0))) * 10 + Integer.parseInt(Character.toString(input.charAt(1)));
            final int mins = Integer.parseInt(Character.toString(input.charAt(3))) * 10 + Integer.parseInt(Character.toString(input.charAt(4)));
            if (hours >= 0 && hours < 24 && mins >= 0 && mins < 60) {
                final Clock clock = Clock.loadClockByClockName(args[1]);
                final int currentTimeInMinutes = Integer.parseInt(main.getGenerator().getRealNumbers(0, null)[0]) * 60 + Integer.parseInt(main.getGenerator().getRealNumbers(0, null)[1]);
                final int newAddingMinutes = (currentTimeInMinutes - hours * 60 - mins) * -1;
                clock.addMinutes(newAddingMinutes);
                player.sendMessage(ChatColor.DARK_GREEN + DigitalClockSystem.getMessagePrefix() + ChatColor.GREEN + " Time on clock '" + args[1] + " is now " + input + ". To set the time back to real time just use command 'addingminutes " + args[1] + " 0'.");
            }
            else {
                player.sendMessage(ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " HH must be integer from 00 to 23 and MM must be integer from 00 to 59!");
            }
        }
        else {
            player.sendMessage(ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Time has incorrect format, it has to be HH:MM!");
        }
    }
}
