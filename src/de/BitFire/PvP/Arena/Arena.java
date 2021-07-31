package de.BitFire.PvP.Arena;

import de.BitFire.Geometry.Cube;
import de.BitFire.Geometry.Point;
import de.BitFire.PvP.Lobby.GameLobby;
import de.BitFire.PvP.PlayMode.PvPMode;

public class Arena 
{
	public final int ID;
	public final String Name;
	public final Cube Field;
	public final int WorldID;
	public final PvPMode Mode;
	
	public int SignID;
	public GameLobby Lobby;
	public ArenaSpawnList SpawnList;
		
	public Arena(final int id, final String name, final Cube field, final int worldID, final PvPMode mode, final int maximalPlayers)
	{
		ID = id;
		Name = name;
		Field = field;
		Mode = mode;
		WorldID = worldID;
		
		SignID = -1;
		Lobby = new GameLobby(maximalPlayers);
		SpawnList = new ArenaSpawnList(maximalPlayers);
		
		SpawnList.AddSpawn(new Point(-234,30,-187));
		SpawnList.AddSpawn(new Point(-230,30,-187));
	}
	
	public String[] GetSignText()
	{		
		String[] lines = new String[4];
		String mode = "&4Error";
		String state = "&4Error";
		String tag = "&4E";
				
		switch(Mode)
		{
		case BedWars:
			mode = "&bBedWars";
			break;
			
		case Monopoly:
			mode = "&2Monopoly";
			break;
			
		case None:			
			break;
			
		case HungerGames:
			mode = "&aHungerGames";
			break;
			
		case PvP:
			mode = "&cPvP";
			break;
			
		default:
			break;		
		}
		
		
		switch(Lobby.GetState())
		{
		case Closed:
			state = "&cClosed";
			tag = "&cx";
			break;
			
		case Empty:
			state = "&bEmpty";
			tag = "&8+";
			break;
			
		case Running:
			state = "&aRunning";
			tag = "&c>";
			break;
			
		case Waiting:
			state = "&eWaiting";
			tag = "&c~";
			break;
			
		case Finished:
			state = "&1Finished";
			tag = "&cO";
			break;
			
		default:
			break;		
		}		
		
		lines[0] = "&0<&l" + mode + "&0>";
		lines[1] = "&0[&eMap&0:&e" + Name + "&0]";
		lines[2] = "[&r" + tag + "&0] &o" + state;
		lines[3] = "&d" + Lobby.GetCurrentPlayers() + "&0/&5" + Lobby.GetMaximalPlayer();
		
		return lines;
	}
}
