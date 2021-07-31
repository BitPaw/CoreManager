package de.SSC.ExtraUtility.DoubleJump;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import de.SSC.CoreManager.Utility.Utility;

public class DoubleJump implements Listener
{
	private int _effectPower = 2004;
	private Effect _useEffect = Effect.SMOKE;
	private Effect _useEffectEE = Effect.MOBSPAWNER_FLAMES;

	
	HashMap<Player, Boolean> cooldown = new HashMap<Player, Boolean>();
	  ArrayList<Player> fixed = new ArrayList<Player>();
	  
	  public void onDisable()
	  {
    
	    Player[] players = (Player[])Bukkit.getOnlinePlayers().toArray();
	    
	    for (Player player : players)
	    {
	    	player.setFlying(false);
	    	player.setAllowFlight(false);
	    }
	  }
	  
	  	  
	  @EventHandler
	  public void onPlayerMove(PlayerMoveEvent e)
	  {
		  if(e == null) return;
			  
	    Player p = e.getPlayer();
	    if ((!p.hasPermission("DJP.doubleJump")) && (p.getAllowFlight()) && (!this.fixed.contains(p)))
	    {
	      p.setFlying(false);
	      p.setAllowFlight(false);
	      this.fixed.add(p);
	    }
	    if (p.getGameMode() == GameMode.CREATIVE) 
	    {
	      return;
	    }
	    if (!p.hasPermission("DJP.doubleJump")) 
	    {
	      return;
	    }
	    if ((this.cooldown.get(p) != null) && (((Boolean)this.cooldown.get(p)).booleanValue()))
	    {
	      p.setAllowFlight(true);
	    }
	    else 
	    {
	      p.setAllowFlight(false);
	    }
	    if (p.isOnGround())
	    {
	      this.cooldown.put(p, Boolean.valueOf(true));
	    }
	    if ((this.cooldown.get(p) != null) && (!((Boolean)this.cooldown.get(p)).booleanValue()))
	    {
	      Player[] players = Utility.GetOnlinePlayer();

	      for (Player player : players)
	      {
	    	  Utility.PlayEffectOnPlayer(player, _useEffect, _effectPower);	        
	      }
	    }
	  }
	  
	  @EventHandler
	  public void onFly(PlayerToggleFlightEvent e)
	  {
	    Player p = e.getPlayer();
	    if (p.getGameMode() == GameMode.CREATIVE)
	    {
	      return;
	    }
	    if ((p.hasPermission("DJP.doubleJump")) && (((Boolean)this.cooldown.get(p)).booleanValue()))
	    {
	      e.setCancelled(true);
	      this.cooldown.put(p, Boolean.valueOf(false));
	      p.setVelocity(p.getLocation().getDirection().multiply(1.6D).setY(1.0D));
	      
	      Player[] players = Utility.GetOnlinePlayer();
	      
	      for (Player player : players)
	      {
	    	  Utility.PlayEffectOnPlayer(player, _useEffectEE, _effectPower);
	      }
	      p.setAllowFlight(false);
	    }
	  }
	  
	  @EventHandler
	  public void onSneak(PlayerToggleSneakEvent e)
	  {
	    Player p = e.getPlayer();
	    if (p.getGameMode() == GameMode.CREATIVE) 
	    {
	      return;
	    }
	    if (!p.hasPermission("DJP.groundPount")) 
	    {
	      return;
	    }
	    if ((!p.isOnGround()) && (this.cooldown.get(p) != null) && (!((Boolean)this.cooldown.get(p)).booleanValue()))
	    {
	      p.setVelocity(new Vector(0, -5, 0));
	    }
	  }
}
