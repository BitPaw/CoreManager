package de.SSC.CoreManager.Addons.MobHealth;

import java.lang.reflect.Method;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import de.SSC.CoreManager.Systems.Chat.Logger;
import de.SSC.CoreManager.Systems.Chat.MessageType;
import de.SSC.CoreManager.Systems.Chat.Module;

public class MobHealthSystem
{
    private MobHealthComfig _mobHealthComfig;
    private Logger _logger;
    
    public MobHealthSystem()
    {
        _mobHealthComfig = new MobHealthComfig();
        _logger = Logger.Instance();
    }

   
    public void SendHealth(final Player receiver, final LivingEntity entity, double health)
    {    
    	boolean isEntityAPlayer = entity instanceof Player;    	
    	boolean isPlayerInvisible = entity.hasPotionEffect(PotionEffectType.INVISIBILITY);
    	String output = getOutput(health, _mobHealthComfig.HealthMessage, receiver, entity);   
    	
    	
    	
    	if (isEntityAPlayer)        
		{        
			Player player = (Player)entity;
			GameMode playerGameMode = player.getGameMode();
			
			if (_mobHealthComfig.OnlyShowOnLook) 
	    	{       
				if (!receiver.canSee(player)) 
				{          
					return;          
				}
	    	}
			
			if (_mobHealthComfig.ShowInSpectatorMode)
	    	{ 
				if (playerGameMode == GameMode.SPECTATOR) 
				{           
					return;
				}  
	    	}	
			
			if(_mobHealthComfig.DontShowIfInvisible)
			{
				if(isPlayerInvisible)
				{
					return;
				}				
			}
		}

    	sendActionBar(receiver, output);     	
    }
    
    public String getOutput(double health, String output, Player receiver, LivingEntity entity)
    {      
    	@SuppressWarnings("deprecation")
		double maxHealth = entity.getMaxHealth();      
    	String name = getName(entity, receiver);
    	Player player;       
		String displayName;    
    	
    	if (health < 0.0D || entity.isDead()) 
    	{       
    		health = 0.0D;     
    	}
    	
     
    	if ((entity instanceof Player))     
    	{    		
    		player = (Player)entity;  
        
    		if (player.getDisplayName() == null)
    		{         
    			displayName = name;       
    		}
    		else 
    		{         
    			displayName = player.getDisplayName();       
    		}
       
    		output = output.replace("{displayname}", displayName);      
     
    	}
     
    	else     
    	{     	
    		output = _mobHealthComfig.HealthMessage;  
    		
    		output = output.replace("{displayname}", name);          
    	}
  
    	output = output.replace("{name}", name);     
    	output = output.replace("{health}", String.valueOf((int)health));     
    	output = output.replace("{maxhealth}", String.valueOf((int)maxHealth));     
    	output = output.replace("{opponentlastdamage}", String.valueOf((int)entity.getLastDamage()));    	
     
    	if (output.contains("{usestyle}"))    
    	{
        StringBuilder style = new StringBuilder();
       
        double healthCap = 10;
        double heart = maxHealth / healthCap;
        double halfHeart = heart / 2.0D;
        double tempHealth = health;
       
        int left = 10;
        
        if ((maxHealth != health) && (health >= 0.0D) && (!entity.isDead()))
        {
          for (int i = 0; i < healthCap; i++)
          {
            if (tempHealth - heart <= 0.0D) {
              break;
            }
            tempHealth -= heart;
            
            style.append(_mobHealthComfig.FullHealthIcon);
            left--;
          }
          if (tempHealth > halfHeart)
          {
            style.append(_mobHealthComfig.FullHealthIcon);
            left--;
          }
          else if ((tempHealth > 0.0D) && (tempHealth <= halfHeart))
          {
            style.append(_mobHealthComfig.HalfHealthIcon);
            left--;
          }
        }
        if (maxHealth != health) {
          for (int i = 0; i < left; i++) {
            style.append(_mobHealthComfig.EmptyHealthIcon);
          }
        } else {
          for (int i = 0; i < left; i++) 
          {
            style.append(_mobHealthComfig.FullHealthIcon);
          }
        }
        output = output.replace("{usestyle}", style.toString());
      }
    	
    	MobHealthSendEvent healthSendEvent = new MobHealthSendEvent(receiver, entity, output);
      
      Bukkit.getPluginManager().callEvent(healthSendEvent);
      
      if (healthSendEvent.isCancelled()) {
        output = null;
      } else {
        output = healthSendEvent.getMessage();
      }
      return output;
    }
    
    public String getName(LivingEntity entity, Player receiver)
    {

      String name;      

      

 
        if (entity.getCustomName() != null)
        {
          name = entity.getCustomName();
        }
        else
        {

            name = getNameReflection(entity);
          
        }


      return name;
    }
    
    private String getNameReflection(LivingEntity entity)
    {
      Method getName = null;
      String name;
      
      try
      {
        if (entity.getCustomName() == null) {
          getName = entity.getClass().getMethod("getName", (Class[])null);
        }
      }
      catch (Exception exception) 
      {
    	  exception.printStackTrace();
      }

      if (getName != null)
      {
     
        try
        {
          name = (String)getName.invoke(entity, (Object[])null);
        }
        catch (Exception e)
        {
          name = capitalizeFully(entity.getType().name().replace("_", ""));
        }
      }
      else
      {
        name = capitalizeFully(entity.getType().name().replace("_", ""));
      }
      return name;
    }
    
    private String capitalizeFully(String words)
    {
      words = words.toLowerCase();
      return WordUtils.capitalizeFully(words);
    }
    
    public void sendActionBar(Player player, String message)
    {
      message = ChatColor.translateAlternateColorCodes('&', message);
      
      try
      {
          new MobHealthBarPreAction(player, message);            
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }    
    
    public boolean MatchesRequirements(Player player, Entity entity)
    {
    	boolean isEntityNotAnArmorStand = entity.getType() != EntityType.ARMOR_STAND;
    	boolean isEntityInSameWorld = player.getWorld() == entity.getWorld();
    	boolean isEntityAPlayer = entity instanceof Player;
    	boolean isEntityAndPlayerIdentical = player.getUniqueId() != entity.getUniqueId();
    	boolean isEntityANPC = entity.hasMetadata("NPC");
    	
    	if(isEntityAPlayer)
    	{
    		if (!_mobHealthComfig.ShowPlayerHealth && !isEntityANPC) 
    		{    	       
    			// Entity is player
    			if(_mobHealthComfig.Debug)
    			{
    				_logger.SendToConsole(Module.MobHealth, MessageType.Info, "MobHealthBar canceled. Player bar is disabled.");
    			}
    			
    			return false;    	      
    		}    		
    	}
    	else if (!_mobHealthComfig.ShowMobHealth) 
        {
    		// Enity is Mob
    		if(_mobHealthComfig.Debug)
			{
				_logger.SendToConsole(Module.MobHealth, MessageType.Info, "MobHealthBar canceled. Mob bar is disabled.");
			}
    		
            return false;
        }
    	
    	if(_mobHealthComfig.Debug)
		{
    		String message = "Information\n";
    		
    		message += "isEntityNotAnArmorStand > " + isEntityNotAnArmorStand + "\n";
    		message += "isEntityInSameWorld > " + isEntityInSameWorld + "\n";
    		message += "isEntityAndPlayerIdentical > " + isEntityAndPlayerIdentical + "\n";
    		
			_logger.SendToConsole(Module.MobHealth, MessageType.Info, message);
		}
    	
    	return 	isEntityNotAnArmorStand && 
    			isEntityInSameWorld &&
    			isEntityAndPlayerIdentical;
    }   
}
