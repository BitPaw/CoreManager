package de.BitFire.Mob;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;

public class MobHealth extends BaseSystem implements ISystem
{	
	private static MobHealth _instance;
	
	private MobHealthComfig _mobHealthComfig;
	private MobHealthSystem _mobHealthSystem;
	private Logger _logger;
	
	public MobHealth()
	{
		super(Module.MobHealth, SystemState.Active, Priority.Low);    	
		_instance = this;
		
		_mobHealthComfig = new MobHealthComfig();
		_mobHealthSystem = new MobHealthSystem();
	}
	
	public static MobHealth Instance()
	{
		return _instance == null ? new MobHealth() : _instance;
	}

	@Override
	public void LoadReferences() 
	{
		_logger = Logger.Instance();		
	}

	@Override
	public void Reload(final boolean firstRun) 
	{
		
	}
	
	@Override
	public void PrintData(CommandSender sender) 
	{
		
	}
	
	public void OnDamage(EntityDamageByEntityEvent event)	   
	{
	        boolean isCancled = event.isCancelled();
	    	boolean isEnabled = _mobHealthComfig.IsActive;
	    	boolean isPvPDisabledEnabled;
	    	boolean isDamagerAProjectile;
	    	boolean isShooterAPlayer;
	    	boolean isDamagerALivingEntity;
	    	Player player;
	    	Entity damaged = event.getEntity();
	    	Entity damageDealer = event.getDamager();
			Projectile projectile;		  
			
			if(isCancled || !isEnabled)		
			{			
				return;		  
			}			
		
	    	isPvPDisabledEnabled = false;
			
			if(isPvPDisabledEnabled)		 
			{						
				return;		 
			}

		
			
			isDamagerAProjectile = damaged instanceof Projectile;
	    	isShooterAPlayer = damageDealer instanceof Player;	

		    if (isDamagerAProjectile)
		    {	      	    	
		    	projectile = (Projectile) event.getDamager();
		      
		    	isShooterAPlayer = projectile.getShooter() instanceof Player;
		    	
		    	if (isShooterAPlayer)           
		    	{
		    		player = (Player)projectile.getShooter();
		    	}
		    }   
		    
		    
		    if (isShooterAPlayer) 
		    {	    	
		    	player = (Player)event.getDamager();	  
		    	boolean matchesRequirements = _mobHealthSystem.MatchesRequirements(player, damaged);
		    	
		    	if (!matchesRequirements)
		    	{
		    		_logger.SendToConsole(Module.MobHealth, MessageType.Info, "\n\n\nMatches Requirements not correct\n\n\n");
		    		return;
		    	}
	    
		    	isDamagerALivingEntity = damaged instanceof LivingEntity;
		    	
		    	if (isDamagerALivingEntity)
		    	{
		    		LivingEntity livingEntity = (LivingEntity)damaged;

		    		livingEntity.setLastDamage(event.getFinalDamage());
		    		
		    		
		    		
		    		_mobHealthSystem.SendHealth(player, livingEntity, livingEntity.getHealth() - event.getFinalDamage());
		    	}
		    }
	    }
}
