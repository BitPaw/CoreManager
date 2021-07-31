// 
// Decompiled by Procyon v0.5.36
// 

package de.BitFire.Chair;

import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerEvent;

public class PlayerChairSitEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private Location sitLocation;
    
    public PlayerChairSitEvent(final Player who, final Location sitLocation) {
        super(who);
        this.cancelled = false;
        this.sitLocation = sitLocation;
    }
    
    public Location getSitLocation() {
        return this.sitLocation.clone();
    }
    
    public void setSitLocation(final Location location) {
        this.sitLocation = location.clone();
    }
    
    public HandlerList getHandlers() {
        return PlayerChairSitEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerChairSitEvent.handlers;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    static {
        handlers = new HandlerList();
    }
}
