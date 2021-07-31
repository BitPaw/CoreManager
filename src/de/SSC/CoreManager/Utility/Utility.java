package de.SSC.CoreManager.Utility;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;


public class Utility 
{
 public static Player[] GetOnlinePlayer()
 {
	Player[] players = (Player[])Bukkit.getOnlinePlayers().toArray();
	
	return players;
 }
 
 @SuppressWarnings("deprecation")
public static void PlayEffectOnPlayer(Player player, Effect effect,  int power)
 {
	// player.playEffect(player.getLocation(), Effect.SMOKE,2004);
	 player.playEffect(player.getLocation(), effect,power);
 }
}
