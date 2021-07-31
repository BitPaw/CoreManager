// 
// Decompiled by Procyon v0.5.36
// 

package de.SSC.CoreManager.Addons.DigitalClock.CMD;

import java.util.Iterator;

import de.SSC.CoreManager.Addons.DigitalClock.Clock;
import de.SSC.CoreManager.Addons.DigitalClock.DigitalClockSystem;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandList implements ICommand
{
    @Override
    public int getArgsSize() {
        return 1;
    }
    
    @Override
    public String getPermissionName() {
        return "digitalclock.list";
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
        return ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Correct usage: '/" + usedCmd + " list'";
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
        player.sendMessage(ChatColor.DARK_GREEN + DigitalClockSystem.getMessagePrefix() + ChatColor.GREEN + " List of all existing clocks:");
        String list = "";
        int i = 0;
        if (main.getClocksL().size() != 0) {
            for (final String name : main.getClocksL()) {
                final Clock clock = Clock.loadClockByClockName(name);
                list = String.valueOf(list) + clock.getName();
                if (i != main.getClocksL().size() - 1) {
                    list = String.valueOf(list) + ", ";
                }
                ++i;
            }
        }
        else {
            list = ChatColor.ITALIC + "No clocks found!";
        }
        player.sendMessage(ChatColor.GREEN + list);
    }
}
