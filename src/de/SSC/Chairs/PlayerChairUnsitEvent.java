// 
// Decompiled by Procyon v0.5.36
// 

package de.SSC.Chairs;

import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerEvent;

public class PlayerChairUnsitEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private boolean canbecancelled;
    private Location unsitLocation;
    
    public PlayerChairUnsitEvent(final Player who, final Location unsitLocation, final boolean canbecancelled) {
        super(who);
        this.cancelled = false;
        this.canbecancelled = true;
        this.unsitLocation = unsitLocation;
        this.canbecancelled = canbecancelled;
    }
    
    public boolean canBeCancelled() {
        return this.canbecancelled;
    }
    
    public Location getTeleportLocation() {
        return this.unsitLocation.clone();
    }
    
    public void setTeleportLocation(final Location location) {
        this.unsitLocation = location.clone();
    }
    
    public HandlerList getHandlers() {
        return PlayerChairUnsitEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerChairUnsitEvent.handlers;
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
