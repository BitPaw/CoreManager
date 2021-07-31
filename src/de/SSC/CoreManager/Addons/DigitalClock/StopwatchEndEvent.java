package de.SSC.CoreManager.Addons.DigitalClock;

import java.util.Date;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Event;

public class StopwatchEndEvent extends Event
{
    private static final HandlerList handlers;
    private Clock clock;
    private Date endtime;
    private int endvalue;
    
    static {
        handlers = new HandlerList();
    }
    
    public static HandlerList getHandlerList() {
        return StopwatchEndEvent.handlers;
    }
    
    public HandlerList getHandlers() {
        return StopwatchEndEvent.handlers;
    }
    
    public StopwatchEndEvent(final Clock clock, final int endval) {
        this.clock = clock;
        this.endtime = new Date();
        this.endvalue = endval;
    }
    
    public Clock getClock() {
        return this.clock;
    }
    
    public Date getEndTime() {
        return this.endtime;
    }
    
    public int getEndValue() {
        return this.endvalue;
    }
}
