package de.BitFire.Player.Login;

import org.bukkit.entity.Player;

import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Module;

public class LoginSystem extends BaseSystem implements ISystem
{
	private static LoginSystem _instance;
	
	public LoginSystem() 
	{
		super(Module.Login, SystemState.Active, Priority.High);
		_instance = this;
	}
	
	public static LoginSystem Instance()
	{
		return _instance == null ? new LoginSystem() :_instance;
	}
	
	public void ClawPlayer(final Player player)
	{
		
	}
}