// 
// Decompiled by Procyon v0.5.36
// 

package de.SSC.CoreManager.Addons.DigitalClock.CMD;

import org.bukkit.Material;

import de.SSC.CoreManager.Addons.DigitalClock.Clock;
import de.SSC.CoreManager.Addons.DigitalClock.DigitalClockSystem;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandFill extends MaterialCommand implements ICommand
{
    @Override
    public int getArgsSize() {
        return 3;
    }
    
    @Override
    public String getPermissionName() {
        return "digitalclock.fill";
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
        return ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Correct usage: '/" + usedCmd + " fill <name> <material id:data>'";
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
        final String oldmat = clock.getFillingMaterial().name().toLowerCase().replace("_", " ");
        if (super.isUsableNumber(args[2])) {
            final Material m = Material.getMaterial(args[2].toString());
            if (m.isSolid()) {
                player.sendMessage(ChatColor.DARK_GREEN + DigitalClockSystem.getMessagePrefix() + ChatColor.GREEN + " Your clock '" + args[1] + "' changed filling material from " + oldmat + " to " + clock.setFillingMaterial(m.toString(), 0).name().toLowerCase().replace("_", " "));
            }
            else {
                player.sendMessage(ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " You can't use " + m.name().toLowerCase().replace("_", " ") + " as a material, because it is not a block (cuboid shape)!");
            }
        }
        else if (args[2].contains(":")) {
            final String[] matdata = args[2].split(":");
            if (super.isUsableNumber(matdata[1])) {
                if (super.isUsableNumber(matdata[0])) {
                    final Material i = Material.getMaterial(matdata[0].toString());
                    
                    if (i.isSolid()) 
                    {
                       // player.sendMessage(ChatColor.DARK_GREEN + DigitalClockSystem.getMessagePrefix() + ChatColor.GREEN + " Your clock '" + args[1] + "' changed filling material from " + oldmat + " to " + clock.setFillingMaterial(i.getId(), Integer.parseInt(matdata[1])).name().toLowerCase().replace("_", " "));
                    }
                    else {
                        player.sendMessage(ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " You can't use " + i.name().toLowerCase().replace("_", " ") + " as a filling material, because it is not a block (cuboid shape)!");
                    }
                }
                else {
                    try {
                        final Material i = Material.valueOf(matdata[0].toUpperCase());
                        if (i != null) {
                            if (i.isSolid()) {
                               // player.sendMessage(ChatColor.DARK_GREEN + DigitalClockSystem.getMessagePrefix() + ChatColor.GREEN + " Your clock '" + args[1] + "' changed filling material from " + oldmat + " to " + clock.setFillingMaterial(i.getId(), Integer.parseInt(matdata[1])).name().toLowerCase().replace("_", " "));
                            }
                            else {
                                player.sendMessage(ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " You can't use " + i.name().toLowerCase().replace("_", " ") + " as a material, because it is not a block (cuboid shape)!");
                            }
                        }
                        else {
                            player.sendMessage(ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Material " + matdata[0] + " does not exist!");
                        }
                    }
                    catch (IllegalArgumentException e) {
                        player.sendMessage(ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Material '" + args[2] + "' does not exist!");
                    }
                }
            }
            else {
                player.sendMessage(ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Material data must be positive integer!");
            }
        }
        else {
            try {
                final Material m = Material.valueOf(args[2].toUpperCase());
                if (m != null) {
                    if (m.isSolid()) {
                        player.sendMessage(ChatColor.DARK_GREEN + DigitalClockSystem.getMessagePrefix() + ChatColor.GREEN + " Your clock '" + args[1] + "' changed filling material from " + oldmat + " to " + clock.setFillingMaterial(m.toString(), 0).name().toLowerCase().replace("_", " "));
                    }
                    else {
                        player.sendMessage(ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " You can't use " + m.name().toLowerCase().replace("_", " ") + " as a material, because it is not a block (cuboid shape)!");
                    }
                }
                else {
                    player.sendMessage(ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Material " + args[2] + " does not exist!");
                }
            }
            catch (IllegalArgumentException e2) {
                player.sendMessage(ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Material '" + args[2] + "' does not exist!");
            }
        }
    }
}
