package de.BitFire.DataBase;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Configuration.Config;
import de.BitFire.Player.CMPlayer;
import de.BitFire.Rank.CMRank;
import de.BitFire.Rank.CMRanksTable;

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
			String errorMessage = _config.Message.Rank.FailedToLoadAllRanks + _config.Message.IO.ErrorArrow + e.getMessage();

			_logger.SendToConsole(Module.DataBase, MessageType.Error, errorMessage);
		}

		_dataBaseSystem.CloseDataBase();

		_logger.SendToConsole(Module.DataBase, MessageType.Info, "&aFound &6<" + ranks.size() + "&6> &7ranks.");
		
		
		return ranks;
	}
}
