package de.SSC.CoreManager.DataBase;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import de.SSC.CoreManager.Chat.Logger;
import de.SSC.CoreManager.Chat.MessageType;
import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Player.CMPlayer;
import de.SSC.CoreManager.Rank.CMRank;
import de.SSC.CoreManager.Rank.CMRanksTable;

public class DataBaseRank 
{
	
	private DataBaseSystem _dataBaseSystem;
	private Logger _logger;
	private Config _config;

	public DataBaseRank(DataBaseSystem dataBaseSystem)
	{
		_dataBaseSystem = dataBaseSystem;
		
		_logger = Logger.Instance();
		_config = Config.Instance();

	}
	
	public void UpdateRank(CMPlayer cmPlayer)
	{
		_logger.SendToConsole(Module.DataBase, MessageType.Info, "Update rank.");
		
		_dataBaseSystem.SendData(_dataBaseSystem.Data.SQLMessages.UpdateRank(cmPlayer));
	}
	

	public List<CMRank> LoadAllRanks()
	{
		List<CMRank> ranks = new ArrayList<CMRank>();
		String sqlMessage = _dataBaseSystem.Data.SQLMessages.LoadAllRanks();
		ResultSet resultSet;

		String rankName;
		String colorTag;
		String playerColor;
		int id;
		
		_logger.SendToConsole(Module.DataBase, MessageType.Info, "Load all ranks...");

		_dataBaseSystem.CheckMySQLCommand(sqlMessage);

		_dataBaseSystem.OpenDataBase();

		_dataBaseSystem.TryExecuteCommand(sqlMessage);

		try
		{
			resultSet = _dataBaseSystem.Data.SQLStatement.getResultSet();

			while (resultSet.next())
			{
				id = resultSet.getInt(CMRanksTable.ID.toString());
				rankName = resultSet.getString(CMRanksTable.Name.toString());
				colorTag = resultSet.getString(CMRanksTable.Tag.toString());
				playerColor = resultSet.getString(CMRanksTable.PlayerColor.toString());				

				ranks.add(new CMRank(id, rankName, colorTag, playerColor));
			}
		}
		catch (Exception e)
		{
			String errorMessage = _config.Messages.Rank.FailedToLoadAllRanks + _config.Messages.ConsoleIO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
		}

		_dataBaseSystem.CloseDataBase();

		_logger.SendToConsole(Module.DataBase, MessageType.Info, "&aFound &6<" + ranks.size() + "&6> &7ranks.");
		
		
		return ranks;
	}
}
