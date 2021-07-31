package de.BitFire.Chair;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Module;

public class ChairsSystem extends BaseSystem implements ISystem
{	  
	private static ChairsSystem _instance;
	private ChairsConfig _chairsConfig;

	private  PlayerSitData psitdata;	
	
	public  ChairEffects chairEffects;
	public  SitUtils utils;
	public ChairsEvent Event;
	
	private ChairsSystem()
	{		
		super(Module.ChairSystem, SystemState.Inactive, Priority.Low);
		_instance = this;	
		
		_chairsConfig = new ChairsConfig();		
		Event = new ChairsEvent(this);		
	    psitdata = new PlayerSitData(this);
	    chairEffects = new ChairEffects(this);
	    utils = new SitUtils(this);
	}
	
	public static ChairsSystem Instance()
	{
		return _instance == null ? new ChairsSystem() : _instance;
	}		
		    

	    
	    public static Entity spawnChairsArrow(final Location location) {
	        final Arrow arrow = location.getWorld().spawnArrow(location, new Vector(), 0.0f, 0.0f);
	        arrow.setGravity(false);
	        arrow.setInvulnerable(true);
	        arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
	        return (Entity)arrow;
	    }
	    
	    public ChairsConfig getChairsConfig() {
	        return _chairsConfig;
	    }
	    
	    public PlayerSitData getPlayerSitData() {
	        return this.psitdata;
	    }
	    	    
	    public void onDisable() {
	        for (final Player player : Bukkit.getOnlinePlayers()) {
	            if (this.psitdata.isSitting(player)) {
	                this.psitdata.unsitPlayerForce(player, true);
	            }
	        }
	        this.chairEffects.cancelHealing();
	        this.chairEffects.cancelPickup();
	    }
	    
	    public void reloadConfig() {
	      
	        if (_chairsConfig.effectsHealEnabled) {
	            this.chairEffects.restartHealing();
	        }
	        else {
	            this.chairEffects.cancelHealing();
	        }
	        if (_chairsConfig.effectsItemPickupEnabled) {
	            this.chairEffects.restartPickup();
	        }
	        else {
	            this.chairEffects.cancelPickup();
	        }
	    }
}
