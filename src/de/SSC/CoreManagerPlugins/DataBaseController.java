package de.SSC.CoreManagerPlugins;
//---------------------------------------------------------------------------------------------------------------
import de.SSC.API.MySQL.CoreSystem.MySQL;
import de.SSC.API.MySQL.DataSetPlayer;
import de.SSC.API.MySQL.SQLMessageBuilder;
import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Config.Messages;
import de.SSC.CoreManager.CoreController;
import de.SSC.CoreManager.Main;
import de.SSC.CoreManager.Multiverse.CoreManagerWorld;
import de.SSC.CoreManager.Multiverse.WorldCredentials;
import de.SSC.CoreManager.Multiverse.WorldManager;
import de.SSC.CoreManager.Players.CoreManagerPlayer;
import de.SSC.CoreManager.Ranks.RankCredentials;
import de.SSC.CoreManager.Ranks.RankManager;
import de.SSC.CoreManager.Teleport.Warp;
import de.SSC.CoreManager.Utility.CoreManagerPlugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//---------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------
public class DataBaseController  extends CoreManagerPlugin
{
	// Variables
	private MySQL _dataBase;
	private Connection _currentConnection;
	private String _lastResult;

	public SQLMessageBuilder SQLMSGBuilder;

	// Reverenzes
	private BukkitHook _bukkitHook;
	private WorldManager _worldManager;
	private PermissionManager _permissionManager;
	private RankManager _rankManager;
	private Config _config;
	private Messages _messages;

	//---------------------------------------------------------------------------------------------------------------
	public DataBaseController()
	{
		Config config = Main.config;
		_dataBase = new MySQL(config.Hostname, config.Port, config.Database, config.Username, config.Password);
		SQLMSGBuilder = new SQLMessageBuilder();

		CoreController coreController = Main.coreController;

		_permissionManager = coreController._PermissionManager;
		_bukkitHook = coreController._BukkitHook;
		_worldManager = coreController._WorldManager;
		_rankManager = coreController._RankManager;
		_config = Main.config;
		_messages = Main.messages;
	}	
	//---------------------------------------------------------------------------------------------------------------

	// I/O
	private void SendErrorMessage(String errorMessage, String rawErrorMessage)
	{

		String message = _messages.DataBase + _messages.Error + errorMessage + "\n"+ rawErrorMessage;

		_bukkitHook.SendConsolMessage(message);
	}

	private void SendInfoMessage(String infoMessage)
	{
		String message = _messages.DataBase + _messages.Info + infoMessage;

		_bukkitHook.SendConsolMessage(message);
	}

	private void SendSQLCommand(String function,String sqlMessage)
	{
		if(_config.ShowSQLCommands)
		{
			String message = "\n"+ _messages.SQLMessageFooter + "\n" + _messages.Command + " &1[&3" + function + "&1]&r" + _messages.DataBase + "\n" + _messages.SQLMessageHeader + "\n " + sqlMessage + "\n"+ _messages.SQLMessageFooter;

			_bukkitHook.SendConsolMessage(message);
		}
	}

	public void SendCommand(String sqlMessage)
	{

		SendSQLCommand("SendCommand", sqlMessage);

		try
		{
			_currentConnection = _dataBase.OpenConnection();
		}
		catch (Exception e)
		{
			SendErrorMessage(_messages.SQLConnectionFailed, e.getMessage());
			return;
		}

		try {
			char s = (char) sqlMessage.charAt(0);
			Statement statement = _currentConnection.createStatement();

			if (s == 's') {
				ResultSet res = statement.executeQuery(sqlMessage);


				res.next();
				_lastResult = res.getString(1);
			} else {
				statement.execute(sqlMessage);
			}
		} catch (Exception e) {
			SendErrorMessage(_messages.SQLStatementError, e.getMessage());
		}

		try {
			_dataBase.CloseConnection();
		} catch (Exception e) {
			SendErrorMessage(_messages.SQLClosingError, e.getMessage());
		}

	}
	
	public void UpdateCustomName(Player player)
	{
		String customName = player.getCustomName();
		String name = player.getName();		
		
		if(name.equalsIgnoreCase(customName))
		{
			player.setCustomName(name);
			SendCommand(SQLMSGBuilder.RemoveCustomName(player));
		}
		else
		{
			SendCommand(SQLMSGBuilder.ChangeCustomName(player));
		}		
	}
	
	public String GetCustomName(Player player)
	{
		SendCommand(SQLMSGBuilder.GetCustomName(player));

		return _lastResult;		
	}

	public int GetNumberOfRegestratedPlayers()
	{
		int value;
		String sqlMessage = SQLMSGBuilder.GetAllRegestratedPlayers();
		
		SendCommand(sqlMessage);
		value = Integer.parseInt(_lastResult);		
		
		return value;
	}

    public void RegisterNewPlayer(Player player)
	{
		// Text for Console		"Register new player"
		String text = _messages.SQLRegisteringNewPlayer;
		text = text.replace("%player%", "&6" + player.getDisplayName());
		SendInfoMessage(text);


		// Values for the new player
		_rankManager = Main.coreController._RankManager;
		System.out.println("\n\nCome not far\n\n");
		RankCredentials defaultRank = _rankManager.DefaultRank;
		System.out.println("\n\nCome a far\n\n");
        int money = _config.StartMoney;
		//System.out.println("\n\ndefault rank = " + defaultRank.RankName + "\n\n");
        String rank = /*defaultRank.RankName */ "I";
		System.out.println("\n\nCome c far\n\n");
		DataSetPlayer dataSetPlayer = new DataSetPlayer(player, money, rank);
		System.out.println("\n\nCome d far\n\n");

		try
		{
			System.out.println("\n\nCome this far\n\n");
		  SendCommand(SQLMSGBuilder.RegisterNewPlayer(dataSetPlayer));
		}
		catch(Exception e)
		{
			SendErrorMessage(_messages.SQLRegistrationError , e.getMessage());
		}
	}

	public void UpdateRegisteredPlayer(Player player)
	{
		SendCommand(SQLMSGBuilder.UpdatePlayer(player));
	}

	public void UpdatePlayerRank(CoreManagerPlayer cmPlayer)
	{
		SendCommand(SQLMSGBuilder.UpdateRank(cmPlayer));
	}

    public CoreManagerPlayer GetPlayerInformation(Player player)
    {   
    	CoreManagerPlayer coreManagerPlayer;
    	String sqlMessage = SQLMSGBuilder.GetPlayer(player);
		Statement statement;
		ResultSet res;

		boolean isOP = false;
		float money = Main.config.StartMoney;

		if(_rankManager.DefaultRank == null)
		{
			throw new NullPointerException("Error : Null value '_rankManager.DefaultRank' in class DataBaseController.GetPlayerInformation()");
		}

		String rank = _rankManager.DefaultRank.RankName;

		if(_config.ShowSQLCommands)
    	{
    		SendSQLCommand("GetPlayerInformation", sqlMessage);
    	}		

    	try
    	{    		
			_currentConnection =_dataBase.OpenConnection();	
			statement = _currentConnection.createStatement();
			res = statement.executeQuery(sqlMessage);

			while(res.next())
			{								 
				 // Get Values
				 isOP = res.getInt("OP") == 1;
				 money = res.getFloat("Money");
				 rank =  res.getString("RankGroup");
			}

			_dataBase.CloseConnection();

		}		
		catch(Exception e)
		{
			SendErrorMessage("Error in GetPlayerInformation", e.getMessage());
		}

		player.setOp(isOP);
		coreManagerPlayer = new CoreManagerPlayer(player, money, rank);
    	
    	return coreManagerPlayer;
    }
    
	public void GetPlayers()
	{		
		SendCommand(SQLMSGBuilder.GetAllPlayersSQLCode());
	}
	
	public boolean IsPlayerRegistered(Player player)
	{
		String sqlMessage = SQLMSGBuilder.DoesPlayerExist(player);
		String uuid = null;

		if(_config.ShowSQLCommands)
		{
			SendSQLCommand("DoesPlayerExist", sqlMessage);
		}

		try
		{
			_currentConnection =_dataBase.OpenConnection();
			Statement statement = _currentConnection.createStatement();
			ResultSet res = statement.executeQuery(sqlMessage);

			while(res.next())
			{
				try
				{
					uuid = res.getString("UUID");
				}
				catch(Exception e)
				{
					SendErrorMessage("Error in GetRanks, Loading Values." , e.getMessage());
				}
			}

			_dataBase.CloseConnection();
		}
		catch(Exception e)
		{
			SendErrorMessage("Error in IsPlayerRegistered." , e.getMessage());
		}

		return uuid != null;
	}


	public List<RankCredentials> GetRanks()
	{
		String sqlMessage = "select * from ranks;";
		List<RankCredentials> rankCredentials = new ArrayList<>();

		if(_config.ShowSQLCommands)
		{
			SendSQLCommand("GetRanks", sqlMessage);
		}

		try
		{
			_currentConnection =_dataBase.OpenConnection();
			Statement statement = _currentConnection.createStatement();
			ResultSet res = statement.executeQuery(sqlMessage);

			while(res.next())
			{
				String rankName = null;
				String colorTag = null;
				String playerColor = null;
				boolean idDefault = false;

				try
				{
					 rankName = res.getString("RankName");
					 colorTag = res.getString("ColorTag");
					 playerColor = res.getString("PlayerColor");
					idDefault = res.getBoolean("IsDefault");

				}
				catch(Exception e)
				{
					SendErrorMessage("Error in GetRanks, Loading Values." , e.getMessage());
				}

					RankCredentials rankCredentials1 = new RankCredentials(rankName, colorTag, playerColor, null, idDefault);

					rankCredentials.add(rankCredentials1);

			}

			_dataBase.CloseConnection();
		}
		catch(Exception e)
		{
			SendErrorMessage("Error in GetRanks." , e.getMessage());
		}

		return rankCredentials;

	}
	
	public List<Warp> LoadAllWarps()
	{		
		String sqlMessage = SQLMSGBuilder.LoadAllWarps();
		List<Warp> warps = new ArrayList<Warp>();

		if(_config.ShowSQLCommands)
		{
			SendSQLCommand("LoadAllWarps", sqlMessage);
		}		
		
		try
		{
			_currentConnection =_dataBase.OpenConnection();	
			Statement statement = _currentConnection.createStatement();	
			ResultSet res = statement.executeQuery(sqlMessage);				

			while(res.next())
			{
				CoreManagerWorld coreManagerWorld = null;
				String name = null;
				String worldName = null;
				float x = 0;
				float y = 0;
				float z = 0;
				float yaw = 0;
				float pitch = 0;
				Location location = null;
				Warp warp = null;
				
				try
				{
					name = res.getString("Name");
					worldName = res.getString("World");


					coreManagerWorld = _worldManager.GetWorld(worldName);

					x = res.getFloat("X");
					y = res.getFloat("Y");
					z = res.getFloat("Z");
					yaw = res.getFloat("yaw");
					pitch = res.getFloat("pitch");
				}
				catch(Exception e)
				{
					SendErrorMessage("Error in LoadAllWarps, Loading Values." , e.getMessage());
				}
				
				
				if(coreManagerWorld.BukkitWorld == null)
				{
					SendErrorMessage("There were missleading warps, the world is missing!", "World is null");
					return null;
				}
				else
				{
					location = new Location(coreManagerWorld.BukkitWorld, x, y, z, yaw, pitch);
					
					warp = new Warp(name, location);

					warps.add(warp);
				}			
			}

			_dataBase.CloseConnection();
		}		
		catch(Exception e)
		{
			SendErrorMessage("Error in LoadAllWarps." , e.getMessage());
		}			
		
		return warps;
	}

	public List<CoreManagerWorld> LoadAllMaps()
	{
		List<CoreManagerWorld> maps = new ArrayList<>();
		String sqlMessage = SQLMSGBuilder.GetAllWorlds();


		if(_config.ShowSQLCommands)
		{
			SendSQLCommand("LoadAllMaps", sqlMessage);
		}

		try
		{
			_currentConnection =_dataBase.OpenConnection();
			Statement statement = _currentConnection.createStatement();
			ResultSet res = statement.executeQuery(sqlMessage);

			while(res.next())
			{
				CoreManagerWorld coreManagerWorld = null;
				WorldCredentials worldCredentials = null;

				try
				{
					worldCredentials = new WorldCredentials();

					worldCredentials.Name = res.getString("Name");
					worldCredentials.CustomName = res.getString("CustomName");

					String mapTypeString = res.getString("MapStyle");
					String mapStyleString =res.getString("MapType");

					worldCredentials.MapType = WorldType.getByName(mapTypeString);
					worldCredentials.MapStyle = World.Environment.valueOf(mapStyleString);

					worldCredentials.BorderSize    = res.getInt("BorderSize");
					worldCredentials.PvP       = res.getBoolean("PvP");
					worldCredentials.KeepInventory  = res.getBoolean("KeepInventory");
					worldCredentials.MobGrief     = res.getBoolean("MobGrief");
					worldCredentials.MobSpaning   = res.getBoolean("MobSpaning");
					worldCredentials.Weather     = res.getBoolean("Weather");
					worldCredentials.DoFireTick  = res.getBoolean("DoFireTick");
					worldCredentials.Seed      = res.getLong("Seed");
					worldCredentials.SpawnX = res.getFloat("SpawnX");
					worldCredentials.Spawny = res.getFloat("Spawny");
					worldCredentials.Spawnz    = res.getFloat("Spawnz");
					worldCredentials.SpawnPitch = res.getFloat("SpawnPitch");
					worldCredentials.SpawnYaw  = res.getFloat("SpawnYaw");
				}
				catch(Exception e)
				{
					SendErrorMessage("Error in LoadAllMaps, Loading Values.", e.getMessage());
					e.printStackTrace();
				}

				try
				{
					if(worldCredentials == null)
					{
						throw  new NullPointerException("Error while LoadAllMaps, worldCredentials is null!");
					}
					else
					{
						coreManagerWorld = new CoreManagerWorld(worldCredentials);
						maps.add(coreManagerWorld);
					}
				}
				catch (Exception e)
				{
					SendErrorMessage("Error in LoadAllMaps() while adding a new World" , e.getMessage());
				}

			}

			_dataBase.CloseConnection();
		}
		catch(Exception e)
		{
			SendErrorMessage("Error in LoadAllMaps." , e.getMessage());
		}

		return maps;
	}
}
//---------------------------------------------------------------------------------------------------------------