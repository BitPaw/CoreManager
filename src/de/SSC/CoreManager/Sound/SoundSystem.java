package de.SSC.CoreManager.Sound;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.System.BaseSystem;
import de.SSC.CoreManager.System.ISystem;
import de.SSC.CoreManager.System.SystemPriority;
import de.SSC.CoreManager.System.SystemState;

public class SoundSystem extends BaseSystem implements ISystem
{
	private static SoundSystem _instance;
	
	private SoundSystem()
	{
		super(Module.Sound, SystemState.Active, SystemPriority.Low);
		_instance = this;
	}
	
	public static SoundSystem Instance()
	{
		return _instance == null ? new SoundSystem() : _instance;
	}
	
	public void PlaySound(final Player player, final Sound sound, final int pitch)
	{		    
		player.playSound(player.getLocation(), sound, 10, pitch);		
	}
	
	public void PlaySound(final Location location, final Sound sound, final int pitch)
	{
		final World w = location.getWorld();
		    
		w.playSound(location, sound, 10, pitch);		
	}
}