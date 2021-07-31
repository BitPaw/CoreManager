// 
// Decompiled by Procyon v0.5.36
// 

package de.SSC.CoreManager.Addons.DigitalClock.CMD;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Addons.DigitalClock.Clock;
import de.SSC.CoreManager.Addons.DigitalClock.DigitalClockSystem;


public class CommandStopclock implements ICommand
{
    @Override
    public int getArgsSize() {
        return 2;
    }
    
    @Override
    public String getPermissionName() {
        return "digitalclock.stopclock";
    }
    
    @Override
    public boolean specialCondition(final DigitalClockSystem main, final Player player, final String[] args) {
        return !main.getClockTasks().containsKeyByClockName(args[1]);
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
        return ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Correct usage: '/" + usedCmd + " stopclock <name>'";
    }
    
    @Override
    public String reactNoPermissions() {
        return ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " You aren't allowed to use this command!";
    }
    
    @Override
    public void specialConditionProcess(final DigitalClockSystem main, final Player player, final String[] args) {
        player.sendMessage(ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Clock '" + args[1] + "' is already stopped!");
    }
    
    @Override
    public String reactBadClockList(final String clockName) {
        return ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Clock '" + clockName + "' not found!";
    }
    
    @Override
    public void process(final DigitalClockSystem main, final Player player, final String[] args) {
        final int task = Clock.stopTask(args[1]);
        player.sendMessage(ChatColor.DARK_GREEN + DigitalClockSystem.getMessagePrefix() + ChatColor.GREEN + " You have successfully stopped clock '" + args[1] + "' (task number " + task + ").");
    }
}
