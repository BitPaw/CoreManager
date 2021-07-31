package de.SSC.DoubleJump;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import de.SSC.BukkitAPI.BukkitAPISystem;
import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.System.BaseSystem;
import de.SSC.CoreManager.System.ISystem;
import de.SSC.CoreManager.System.SystemPriority;
import de.SSC.CoreManager.System.SystemState;

public class DoubleJump extends BaseSystem implements ISystem
{
	private static DoubleJump _instance;
	private int _effectPower;
	private Effect _useEffect;
	private Effect _useEffectEE;
	private HashMap<Player, Boolean> _cooldown;
	private List<Player> _fixed;

	private BukkitAPISystem _bukkitUtility;

	private DoubleJump()
	{
		super(Module.DoubleJump, SystemState.Inactive, SystemPriority.Low);
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

	@Override
	public void LoadReferences() 
	{
		_bukkitUtility = BukkitAPISystem.Instance();		
	}

	@Override
	public void Reload(final boolean firstRun) 
	{
		
	}
	
	@Override
	public void PrintData(CommandSender sender) 
	{
		
	}
	
	public void onDisable()
	{
		List<Player> players = _bukkitUtility.Player.GetAllOnlinePlayers();

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
			players = _bukkitUtility.Player.GetAllOnlinePlayers();

			for (Player targetedPlayer : players)
			{
				_bukkitUtility.Player.PlayEffectOnPlayer(targetedPlayer, _useEffect, _effectPower);
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
			players = _bukkitUtility.Player.GetAllOnlinePlayers();

			e.setCancelled(true);

			_cooldown.put(player, Boolean.valueOf(false));

			player.setVelocity(player.getLocation().getDirection().multiply(1.6D).setY(1.0D));


			for (Player targedplayer : players)
			{
				_bukkitUtility.Player.PlayEffectOnPlayer(targedplayer, _useEffectEE, _effectPower);
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