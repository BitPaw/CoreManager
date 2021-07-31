package de.BitFire.Player.DoubleJump;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import de.BitFire.API.Bukkit.BukkitAPIPlayer;
import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Module;

public class DoubleJump extends BaseSystem implements ISystem
{
	private static DoubleJump _instance;
	private int _effectPower;
	private Effect _useEffect;
	private Effect _useEffectEE;
	private HashMap<Player, Boolean> _cooldown;
	private List<Player> _fixed;

	private DoubleJump()
	{
		super(Module.DoubleJump, SystemState.Inactive, Priority.Low);
		_instance = this;

		_effectPower = 2004;
		_useEffect = Effect.SMOKE;
		_useEffectEE = Effect.MOBSPAWNER_FLAMES;
		_cooldown = new HashMap<Player, Boolean>();
		_fixed = new ArrayList<Player>();
	}
	
	public static DoubleJump Instance()
	{
		return _instance == null ? new DoubleJump() : _instance;
	}
	
	public void onDisable()
	{
		List<Player> players = BukkitAPIPlayer.GetAllOnlinePlayers();

		for (Player player : players)
		{
			player.setFlying(false);
			player.setAllowFlight(false);
		}
	}

	public void OnPlayerMove(PlayerMoveEvent e)
	{
		boolean hasPermission;
		boolean isPlayerCrative;
		boolean isAllowedToFly;
		Player player;
		List<Player> players;

		if (e == null)
		{
			return;
		}

		player = e.getPlayer();

		hasPermission = player.hasPermission("DJP.doubleJump");
		isPlayerCrative = player.getGameMode() == GameMode.CREATIVE;
		isAllowedToFly = player.getAllowFlight();


		if (!hasPermission && isAllowedToFly && (!_fixed.contains(player)))
		{
			player.setFlying(false);
			player.setAllowFlight(false);

			_fixed.add(player);
		}
		if (isPlayerCrative)
		{
			return;
		}
		if (!player.hasPermission("DJP.doubleJump"))
		{
			return;
		}
		if ((_cooldown.get(player) != null) && (((Boolean)_cooldown.get(player)).booleanValue()))
		{
			player.setAllowFlight(true);
		}
		else
		{
			player.setAllowFlight(false);
		}
		if (player.isOnGround())
		{
			_cooldown.put(player, Boolean.valueOf(true));
		}
		if ((_cooldown.get(player) != null) && (!((Boolean)_cooldown.get(player)).booleanValue()))
		{
			players = BukkitAPIPlayer.GetAllOnlinePlayers();

			for (Player targetedPlayer : players)
			{
				BukkitAPIPlayer.PlayEffectOnPlayer(targetedPlayer, _useEffect, _effectPower);
			}
		}
	}

	public void OnFly(PlayerToggleFlightEvent e)
	{
		Player player = e.getPlayer();
		List<Player> players;

		if (player.getGameMode() == GameMode.CREATIVE)
		{
			return;
		}
		if ((player.hasPermission("DJP.doubleJump")) && (((Boolean)_cooldown.get(player)).booleanValue()))
		{
			players = BukkitAPIPlayer.GetAllOnlinePlayers();

			e.setCancelled(true);

			_cooldown.put(player, Boolean.valueOf(false));

			player.setVelocity(player.getLocation().getDirection().multiply(1.6D).setY(1.0D));


			for (Player targedplayer : players)
			{
				BukkitAPIPlayer.PlayEffectOnPlayer(targedplayer, _useEffectEE, _effectPower);
			}

			player.setAllowFlight(false);
		}
	}

	public void OnSneak(PlayerToggleSneakEvent e)
	{
		Player player = e.getPlayer();

		if (player.getGameMode() == GameMode.CREATIVE)
		{
			return;
		}
		if (!player.hasPermission("DJP.groundPount"))
		{
			return;
		}
		if ((!player.isOnGround()) && (_cooldown.get(player) != null) && (!((Boolean)_cooldown.get(player)).booleanValue()))
		{
			player.setVelocity(new Vector(0, -5, 0));
		}
	}
}