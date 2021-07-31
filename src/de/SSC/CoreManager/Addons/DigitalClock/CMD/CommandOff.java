// 
// Decompiled by Procyon v0.5.36
// 

package de.SSC.CoreManager.Addons.DigitalClock.CMD;

import org.bukkit.plugin.Plugin;

import de.SSC.CoreManager.Addons.DigitalClock.DigitalClockSystem;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandOff implements ICommand
{
    @Override
    public int getArgsSize() {
        return 1;
    }
    
    @Override
    public String getPermissionName() {
        return "digitalclock.off";
    }
    
    @Override
    public boolean specialCondition(final DigitalClockSystem main, final Player player, final String[] args) {
        return false;
    }
    
    @Override
    public boolean checkClockExistence() {
        return false;
    }
    
    @Override
    public boolean neededClockExistenceValue() {
        return false;
    }
    
    @Override
    public String reactBadArgsSize(final String usedCmd) {
        return ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Correct usage: '/" + usedCmd + " off'";
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
        return null;
    }
    
    @Override
    public void process(final DigitalClockSystem main, final Player player, final String[] args) {
        final String s = ChatColor.DARK_GREEN + DigitalClockSystem.getMessagePrefix() + ChatColor.GREEN + " Turning off DigitalClock plugin!";
        if (player == null) {
            System.out.println(s);
        }
        else {
            player.sendMessage(s);
        }
        System.out.println("Turning off DigitalClock!");
        //main.getPluginLoader().disablePlugin((Plugin)main);
    }
}
