package de.SSC.API.MySQL;

import org.bukkit.entity.Player;

import java.net.InetSocketAddress;
import java.sql.Time;
import java.util.UUID;

public class DataSetPlayer
{
	public boolean IsOP;		
	public float Money;	
	public UUID UUID;		
	public String Name;
	public String CustomName;	
	public String RankGroup;
	public String IP; 	
	public Time Registered;
	public Time LastSeen;
	
	public DataSetPlayer(Player player, float money, String rank)
	{
		try
		{
			InetSocketAddress inetadress = player.getAddress();
			String ipAdress = inetadress.getAddress().toString();
			ipAdress = ipAdress.replace("/", "");

			this.UUID = player.getUniqueId();
			this.Name = player.getName();
			this.CustomName = player.getCustomName();
			this.IsOP = player.isOp();

			this.Money = money;
			this.RankGroup = rank;
			this.IP = ipAdress;

		}
		catch(Exception e)
		{
			System.out.println("Error while creating an DataSetPlayer");
		}
	}
}