package de.BitFire.API.Bukkit;

import org.bukkit.entity.EntityType;

public final class BukkitAPIEntity 
{
	private BukkitAPIEntity() 
	{
		
	}

	public final static boolean IsExplosive(final EntityType entityType)
	{
		switch(entityType)
		{
			case PRIMED_TNT :
			case ENDER_CRYSTAL :
			case WITHER :
			case MINECART_TNT :			
				return true;
				
			default:
				return false;
		}
	}
}
