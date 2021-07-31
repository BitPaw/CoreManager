package de.SSC.CoreManager.Addons.DigitalClock.CMD;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Addons.DigitalClock.Clock;
import de.SSC.CoreManager.Addons.DigitalClock.DigitalClockSystem;

public class CommandAddingminutes implements ICommand
{
    @Override
    public int getArgsSize() {
        return 3;
    }
    
    @Override
    public String getPermissionName() {
        return "digitalclock.addingminutes";
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
        return ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Correct usage: '/" + usedCmd + " addingminutes <name> <minutes>'";
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
        final int mins = Integer.parseInt(args[2]);
        if (mins >= 0 && mins < 1440) {
            final Clock clock = Clock.loadClockByClockName(args[1]);
            clock.addMinutes(mins);
            player.sendMessage(ChatColor.DARK_GREEN + DigitalClockSystem.getMessagePrefix() + ChatColor.GREEN + " Clock '" + args[1] + "' now will add " + args[2] + " minutes.");
        }
        else {
            player.sendMessage(ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Minutes can be only integer between 0 and 1439, not " + args[2] + "!");
        }
    }
}
