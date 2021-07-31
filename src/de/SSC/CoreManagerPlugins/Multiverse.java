package de.SSC.CoreManagerPlugins;

import de.SSC.CoreManager.Utility.CoreManagerPlugin;

class Multiverse  extends CoreManagerPlugin
{
	/*
	// Variables
	private List<CoreManagerWorld> _worlds;
	private boolean _hasChanged;

	// Referenzes
	private BukkitHook bukkitHook;
	private WorldManager _worldManager;
	private Messages _messages;

	public Multiverse()
	{
		_worldManager = new WorldManager();

        _hasChanged = true;
		 CoreController coreController = Main.coreController;
		bukkitHook = coreController._BukkitHook;
		_messages = Main.messages;
	}

	public void UpdateWorlds()
    {
    	if(bukkitHook == null) throw new NullPointerException("Error in Multiverse.UpdateWorlds BukkitHook is null");

    	boolean debug = true;
		_worlds = new ArrayList<>();
		List<CoreManagerWorld> coreManagerWorlds = null;

		try
		{
			coreManagerWorlds = _worldManager.LoadAllWorlds();

			if(coreManagerWorlds == null)
			{
				bukkitHook.SendConsolMessage("&cCould not load worlds.");
			}
			else
			{
				for(CoreManagerWorld coreManagerWorld : coreManagerWorlds)
				{
					if(debug)
					{
					  bukkitHook.SendConsolMessage(_messages.MultiverseSystem + "&2 Loading&7 world : &a" + world.getName());

					}
				}

				_hasChanged = false;
			}
		}
		catch(Exception e)
		{
			bukkitHook.SendConsolMessage("&cError while Updating Worlds. Reason : " + e.getMessage());
		}
	}
	
	public void CreateNewWorld(CommandSender sender,  String[] parameter)
	{
		if(bukkitHook == null)
		{
			String errorMessage = "Could't create a new world,\n" +
					"because bukkitHook is null in function CreateNewWorld in class Multiverse";

			bukkitHook.SendMessage(sender , errorMessage);

			throw new NullPointerException(errorMessage);
		}

		WorldCredentials worldCredentials = new WorldCredentials();

		bukkitHook.SendMessage(sender,"&bLoading world : &3" + worldCredentials.Name);


		_worldManager.CreateNewWorld(worldCredentials);


		UpdateWorlds();

	}


	/*
	public void DeleteWorld(World world)
	{
        _hasChanged = true;
		_worldManager.DeleteWorld();
	}

	public World GetWorldPerName(String worldName)
    {
        return _worldManager.FindWorldPerName(worldName);
    }

	public void UnloadWorld(String worldName)
	{
		Bukkit.unloadWorld(worldName, true);
	}

	*/
}
