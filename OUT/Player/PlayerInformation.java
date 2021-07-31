package de.BitFire.Player;

import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import de.BitFire.API.Bukkit.BukkitAPIServer;
import de.BitFire.Configuration.Config;
import de.BitFire.Economy.EconemyAccount;
import de.BitFire.Rank.CMRank;
import de.BitFire.Rank.RankSystem;
import de.BitFire.Time.TimePoint;

public class PlayerInformation 
{
	public UUID PlayerUUID;
	public boolean IsCracked;
	public String PlayerName;
	public TimePoint Registered;	
	public int ID;
	
	public boolean IsOP;
	public boolean IsBanned;
	public boolean IsGodModeActive;	
	public GameMode PlayerGameMode;
	public String CustomName;
	public String IP; 	
	public EconemyAccount Money;	
	public CMRank RankGroup;
	public TimePoint LastSeen;	
	
	private PlayerInformation()
	{
		final RankSystem rankSystem = RankSystem.Instance();
		final Config config = Config.Instance();			
		final TimePoint timePoint = TimePoint.Now();
			
		ID = -1;
		IsCracked = BukkitAPIServer.IsServerCracked();
		IsBanned = false;		
		CustomName = null;
		IsGodModeActive = false;
		Money = new EconemyAccount(config.Econemy.StartMoney);
		RankGroup = rankSystem.GetFailSaveRank();		
		Registered = timePoint;
		LastSeen = timePoint;	  
		IsOP = false;
		
		PlayerUUID = new UUID(0L, 0L);
		PlayerName = "[N/A]";
		PlayerGameMode = GameMode.SURVIVAL;
	}
	
	public PlayerInformation(final Player player) 
	{		
		this();
						
		PlayerUUID = player.getUniqueId();		
		IP = CMPlayer.GetPlayerIP(player); 
		PlayerName = player.getName();	
		Registered = TimePoint.Now();
		
		IsCracked = BukkitAPIServer.IsServerCracked();
		IsOP = player.isOp();
	}
	
	public PlayerInformation(final int playerID, final UUID uuid, final boolean isOP, final boolean isCracked, String playerName, TimePoint registered) 
	{		
		this();
		
		ID = playerID;
		IsOP = isOP;
		PlayerUUID = uuid;		
		PlayerName = playerName;	
		Registered = registered;
		IsCracked = isCracked;
	}
	
	public void Update(final Player player)
	{
			
		
	}	
}