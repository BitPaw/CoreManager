// 
// Decompiled by Procyon v0.5.36
// 

package de.SSC.CoreManager.Addons.DigitalClock.CMD;

import org.bukkit.entity.Player;
import de.SSC.CoreManager.Addons.DigitalClock.DigitalClockSystem;

public interface ICommand
{
    int getArgsSize();
    
    String getPermissionName();
    
    boolean specialCondition(final DigitalClockSystem p0, final Player p1, final String[] p2);
    
    boolean checkClockExistence();
    
    boolean neededClockExistenceValue();
    
    String reactBadArgsSize(final String p0);
    
    String reactNoPermissions();
    
    String reactBadClockList(final String p0);
    
    void process(final DigitalClockSystem p0, final Player p1, final String[] p2);
    
    void specialConditionProcess(final DigitalClockSystem p0, final Player p1, final String[] p2);
}
