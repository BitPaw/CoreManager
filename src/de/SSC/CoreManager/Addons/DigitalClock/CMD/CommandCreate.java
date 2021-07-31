// 
// Decompiled by Procyon v0.5.36
// 

package de.SSC.CoreManager.Addons.DigitalClock.CMD;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import de.SSC.CoreManager.Addons.DigitalClock.DigitalClockSystem;

public class CommandCreate implements ICommand
{
    @Override
    public int getArgsSize() {
        return 2;
    }
    
    @Override
    public String getPermissionName() {
        return "digitalclock.create";
    }
    
    @Override
    public boolean specialCondition(final DigitalClockSystem main, final Player player, final String[] args) {
        return main.getEnableBuildUsers().containsKey(player);
    }
    
    @Override
    public boolean checkClockExistence() {
        return true;
    }
    
    @Override
    public boolean neededClockExistenceValue() {
        return false;
    }
    
    @Override
    public String reactBadArgsSize(final String usedCmd) {
        return ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Correct usage: '/" + usedCmd + " create <name>'";
    }
    
    @Override
    public String reactNoPermissions() {
        return ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " You aren't allowed to use this command!";
    }
    
    @Override
    public void specialConditionProcess(final DigitalClockSystem main, final Player player, final String[] args) {
        player.sendMessage(ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " You are just creating another clock. You can't create more clocks in the same time!");
    }
    
    @Override
    public String reactBadClockList(final String clockName) {
        return ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Clock with this name already exists!";
    }
    
    @Override
    public void process(final DigitalClockSystem main, final Player player, final String[] args) {
        int count = 0;
        if (main.getUsersClock().get(player.getName()) != null) {
            count = main.getUsersClock().get(player.getName());
        }
        boolean limitReached = false;
        if (player.hasPermission("digitalclock.limit." + count) && count != 0 && !player.isOp() && !player.hasPermission("digitalclock.limit.*")) {
            limitReached = true;
        }
        if (!limitReached) {
            main.getEnableBuildUsers().put(player, args[1]);
            player.sendMessage(ChatColor.DARK_GREEN + DigitalClockSystem.getMessagePrefix() + ChatColor.GREEN + " Now you can create your " + (count + 1) + ". clock. Place any block anywhere to set start block. Click with empty hand to end creating.");
        }
        else {
            player.sendMessage(ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " You can't create next clock. You have reached the limit of " + count + " clocks.");
        }
    }
}
