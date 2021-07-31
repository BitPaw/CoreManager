package de.SSC.CoreManagerPlugins;

import de.SSC.CoreManager.Config.Messages;
import de.SSC.CoreManager.CoreController;
import de.SSC.CoreManager.Main;
import de.SSC.CoreManager.Ranks.RankManager;
import de.SSC.CoreManager.Utility.CoreManagerPlugin;
import org.bukkit.entity.Player;

public class PermissionManager extends CoreManagerPlugin
{
	private boolean _hasChanged = false;

	// Referenzes
	private DataBaseController _dataBaseController;
	private BukkitHook _bukkitHook ;
	private RankManager _rankManager;
	private Messager _messager;
	private Messages _messages;
	
	public PermissionManager()
	{
		_hasChanged = true;

		CoreController coreController = Main.coreController;

		_bukkitHook = coreController._BukkitHook;
		_dataBaseController = coreController._DataBaseController;
		_rankManager = coreController._RankManager;
		_messager = coreController._Messager;
		_messages = Main.messages;
	}

	public boolean PlayerHasPermission(Player player)
	{
		return true;
	}

}
