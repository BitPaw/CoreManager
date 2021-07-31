package de.PlayerPing.SSC;

import org.bukkit.entity.Player;
import net.minecraft.server.v1_13_R2.ServerPing;

 public class Ping 
 {
	  private ServerPing serverPing;
	 
   public int GetPing()
   {
	    int ping = 0;//((CraftPlayer) player).getHandle().ping;
			   
	   return ping;
   }
	 
	 
   public int GetPing(Player player)
   {
	  String packageName = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];	   
	  boolean isPlayer = player.getClass().getName().equals("org.bukkit.craftbukkit." + packageName + ".entity.CraftPlayer"); 
  	  
	  int currentPing = 0; 
	  
     if (!isPlayer)
     {
	   player = org.bukkit.Bukkit.getPlayer(player.getUniqueId());
     }
   
	 try 
	 {
	      Class<?> CraftPlayerClass = Class.forName("org.bukkit.craftbukkit." + packageName + ".entity.CraftPlayer");
	      Object CraftPlayer = CraftPlayerClass.cast(player);
	      java.lang.reflect.Method getHandle = CraftPlayer.getClass().getMethod("getHandle", new Class[0]);
	      Object EntityPlayer = getHandle.invoke(CraftPlayer, new Object[0]);
	      java.lang.reflect.Field ping = EntityPlayer.getClass().getDeclaredField("ping");
	       
	      currentPing = ping.getInt(EntityPlayer);
	  }	    
	  catch (Exception e) 
	  {
	    e.printStackTrace();
	  }  
	 
	 return currentPing;
  }
}
