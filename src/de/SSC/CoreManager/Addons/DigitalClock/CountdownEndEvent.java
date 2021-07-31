package de.SSC.CoreManager.Addons.DigitalClock;

import java.util.Date;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Event;

public class CountdownEndEvent {
	 private static final HandlerList handlers;
	    private Clock clock;
	    private Date endtime;
	    
	    static {
	        handlers = new HandlerList();
	    }
	    
	    public static HandlerList getHandlerList() {
	        return CountdownEndEvent.handlers;
	    }
	    
	    public HandlerList getHandlers() {
	        return CountdownEndEvent.handlers;
	    }
	    
	    public CountdownEndEvent(final Clock clock) {
	        this.clock = clock;
	        this.endtime = new Date();
	    }
	    
	    public Clock getClock() {
	        return this.clock;
	    }
	    
	    public Date getEndTime() {
	        return this.endtime;
	    }
}
