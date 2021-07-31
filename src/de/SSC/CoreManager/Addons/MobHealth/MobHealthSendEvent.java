package de.SSC.CoreManager.Addons.MobHealth;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MobHealthSendEvent extends Event implements Cancellable
{
	private static final HandlerList handlers = new HandlerList();
	  private final Player player;
	  private final Entity damaged;
	  private boolean isCancelled;
	  private String message;
	  
	  public MobHealthSendEvent(Player player, Entity damaged, String message)
	  {
	    this.player = player;
	    this.damaged = damaged;
	    this.message = message;
	    this.isCancelled = false;
	  }
	  
	  public static HandlerList getHandlerList()
	  {
	    return handlers;
	  }
	  
	  public Player getPlayer()
	  {
	    return this.player;
	  }
	  
	  public Entity getDamaged()
	  {
	    return this.damaged;
	  }
	  
	  public String getMessage()
	  {
	    return this.message;
	  }
	  
	  public void setMessage(String message)
	  {
	    this.message = message;
	  }
	  
	  public HandlerList getHandlers()
	  {
	    return handlers;
	  }
	  
	  public boolean isCancelled()
	  {
	    return this.isCancelled;
	  }
	  
	  public void setCancelled(boolean b)
	  {
	    this.isCancelled = b;
	  }
}
