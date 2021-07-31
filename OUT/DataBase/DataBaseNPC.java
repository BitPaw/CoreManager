package de.BitFire.DataBase;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Geometry.PointView;
import de.BitFire.NPC.NPC;
import de.BitFire.NPC.NPCTable;

public class DataBaseNPC 
{
	private DataBaseSystem _dataBaseSystem;
	private Logger _logger;
	
	public DataBaseNPC(DataBaseSystem dataBaseSystem) 
	{
		_dataBaseSystem = dataBaseSystem;
		_logger = Logger.Instance();
	}
	
	public List<NPC> LoadAllNPCs()
	{
		String sqlMessage = "select * from npc";
		List<NPC> npcList = new ArrayList<NPC>();
		ResultSet resultSet;
		
		_dataBaseSystem.CheckMySQLCommand(sqlMessage);
		_dataBaseSystem.OpenDataBase();
		_dataBaseSystem.TryExecuteCommand(sqlMessage);

		try
		{
			resultSet = _dataBaseSystem.Data.SQLStatement.getResultSet();

			while (resultSet.next())
			{				
				final int id = resultSet.getInt(NPCTable.ID.toString());
				final String name = resultSet.getString(NPCTable.Name.toString());			
				final double x = resultSet.getDouble(NPCTable.X.toString());
				final double y = resultSet.getDouble(NPCTable.Y.toString());
				final double z = resultSet.getDouble(NPCTable.Z.toString());
				final float yaw = resultSet.getFloat(NPCTable.Yaw.toString());
				final float pitch = resultSet.getFloat(NPCTable.Pitch.toString());
				final int worldID = resultSet.getInt(NPCTable.WorldID.toString());
				final boolean lookAtPlayer = resultSet.getBoolean(NPCTable.LookAtPlayer.toString());
				Integer playerID = resultSet.getInt(NPCTable.PlayerID.toString());
			
				NPC npc;
				PointView pointView = new PointView(x,y,z,yaw, pitch);
								
				npc = new NPC(id, name, worldID, pointView, lookAtPlayer, playerID);				
				
				npcList.add(npc);
			}
		}
		catch (Exception e)
		{	
			_logger.SendToConsole(Module.DataBase, MessageType.Error, "LoadAllNPCs error loading. " + e.getMessage());
		}

		_dataBaseSystem.CloseDataBase();		
		
		return npcList;
	}
	
	public void SaveNPC(NPC npc)
	{
		String sqlCommand = "";
		
		_logger.SendToConsole(Module.DataBase, MessageType.Command, "Registering new warp.");

		_dataBaseSystem.SendData(sqlCommand);
	}
}
