package de.SSC.CoreManager.Addons.DigitalClock.CMD;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import de.SSC.CoreManager.Addons.DigitalClock.DigitalClockSystem;

public class CommandReload implements ICommand
{
    @Override
    public int getArgsSize() {
        return 1;
    }
    
    @Override
    public String getPermissionName() {
        return "digitalclock.reload";
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
        return ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Correct usage: '/" + usedCmd + " reload'";
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
        main.reloadConf();
        final String s = ChatColor.DARK_GREEN + DigitalClockSystem.getMessagePrefix() + ChatColor.GREEN + " File config.yml has been reloaded!";
        if (player == null) {
            System.out.println(s);
        }
        else {
            player.sendMessage(s);
        }
    }
}
