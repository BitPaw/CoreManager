package de.SSC.CoreManager.Addons.MobHealth;

import org.bukkit.entity.Player;

public class MobHealthBarPreAction 
{
	private final String packageVersion = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	  
	  public MobHealthBarPreAction(Player player, String message)
	  {
	    try
	    {
	      Object chatComponentText = getNMSClass("ChatComponentText").getConstructor(new Class[] { String.class }).newInstance(new Object[] { message });
	      Object chatMessageType = getNMSClass("ChatMessageType").getField("GAME_INFO").get(null);
	      Object packetPlayOutChat = getNMSClass("PacketPlayOutChat").getConstructor(new Class[] { getNMSClass("IChatBaseComponent"), getNMSClass("ChatMessageType") }).newInstance(new Object[] { chatComponentText, chatMessageType });
	      Object getHandle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
	      Object playerConnection = getHandle.getClass().getField("playerConnection").get(getHandle);
	      
	      playerConnection.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") }).invoke(playerConnection, new Object[] { packetPlayOutChat });
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	  }
	  
	  private Class<?> getNMSClass(String nmsClassName)
	  {
	    try
	    {
	      return Class.forName("net.minecraft.server." + this.packageVersion + "." + nmsClassName);
	    }
	    catch (ClassNotFoundException e)
	    {
	      e.printStackTrace();
	    }
	    return null;
	  }
}
