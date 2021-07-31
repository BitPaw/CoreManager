package de.BitFire.Sound;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Module;

public class SoundSystem extends BaseSystem implements ISystem
{
	private static SoundSystem _instance;
	private final static Sound _rightSound = Sound.BLOCK_NOTE_BLOCK_BELL;
	private final static Sound _falseSound = Sound.BLOCK_NOTE_BLOCK_BASEDRUM;
	private final static Sound _teleportSound = Sound.ENTITY_ENDERMAN_TELEPORT;
	
	private SoundSystem()
	{
		super(Module.Sound, SystemState.Active, Priority.Low);
		_instance = this;
	}
	
	public static SoundSystem Instance()
	{
		return _instance == null ? new SoundSystem() : _instance;
	}
	
	public void PlaySoundFalse(final Player player)
	{					
		PlaySound(player, _falseSound, 0);		
	}
	
	public void PlaySoundTrue(final Player player)
	{		
		PlaySound(player, _rightSound, 0);		
	}
	
	public void PlaySound(final Player player, final Sound sound, final int pitch)
	{		    
		if(Information.IsActive())
		{		
			player.playSound(player.getLocation(), sound, 10, pitch);	
		}
	}
	
	public void PlaySound(final Location location, final Sound sound, final int pitch)
	{
		if(Information.IsActive())
		{	
			final World w = location.getWorld();
		    
			w.playSound(location, sound, 10, pitch);	
		}
	}

	public void PlaySoundTeleport(Player player) 
	{
		PlaySound(player, _teleportSound, 0);			
	}
}