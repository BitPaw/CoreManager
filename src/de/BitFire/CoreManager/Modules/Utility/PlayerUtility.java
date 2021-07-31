package de.BitFire.CoreManager.Modules.Utility;

import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_15_R1.EntityPlayer;

public class PlayerUtility 
{
	public static int GetPlayerPing(final Player bukkitPlayer) 
	{
		final CraftPlayer craftPlayer = (CraftPlayer) bukkitPlayer; 		
		final EntityPlayer entityPlayer = craftPlayer.getHandle(); 
		final int playerPing = entityPlayer.ping;
		
		return playerPing; 		
	}
}