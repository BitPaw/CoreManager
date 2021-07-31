// 
// Decompiled by Procyon v0.5.36
// 

package de.SSC.CoreManager.Addons.DigitalClock.CMD;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Addons.DigitalClock.Clock;
import de.SSC.CoreManager.Addons.DigitalClock.DigitalClockSystem;

public class CommandRemove implements ICommand
{
    @Override
    public int getArgsSize() {
        return 2;
    }
    
    @Override
    public String getPermissionName() {
        return "digitalclock.remove";
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
        return ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Correct usage: '/" + usedCmd + " remove <name>'";
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
        final Clock clock = Clock.loadClockByClockName(args[1]);
        Clock.eraseCompletely(clock);
        main.getClocks();
        player.sendMessage(ChatColor.DARK_GREEN + DigitalClockSystem.getMessagePrefix() + ChatColor.GREEN + " Your clock '" + args[1] + "' has been successfully removed.");
    }
}
