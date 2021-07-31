package de.BitFire.Chest;

import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Module;

public class ChestShopSystem extends BaseSystem implements ISystem
{
	private static ChestShopSystem _instance;
	
	private ChestShopSystem()
	{
		super(Module.ChestShop, SystemState.Active , Priority.Low);
		_instance = this;
	}
	
	public static ChestShopSystem Instance()
	{
		return _instance == null ? new ChestShopSystem() : _instance;
	} 
}