package de.SSC.CoreManager.SignEdit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import de.SSC.CoreManager.Config;
import de.SSC.CoreManager.Messages;
import de.SSC.CoreManager.Color.Logger;
import net.minecraft.server.v1_12_R1.BlockPosition;

public class SignEdit implements Listener
{
	  @EventHandler
	  public void onSignChangeEvent(SignChangeEvent e)
	  {   
	    for (int i = 0; i < 4; i++) 
	    {
	    	String line = e.getLine(i);
	    	String coloredLine = "";
	    	
	    	if(line.contains(":l:"))
	    	{
	    		coloredLine = Messages.SignLine;
	    	}
	    	else
	    	{
	    		coloredLine = line;
	    	}	
	    	
	    	 e.setLine(i,  ChatColor.translateAlternateColorCodes(Config.ColorCombineChar , coloredLine));
	    }
	  } 
	  
	  @EventHandler
	  public void onPlayerInteract(PlayerInteractEvent event)
	  {
	    	if (event.getAction() == Action.RIGHT_CLICK_BLOCK) 
	    	{
	    		Player player = event.getPlayer(); 
	  		    Block block = event.getClickedBlock(); 
	    		Material blockMaterial = block.getType();
	    		
	            if (((blockMaterial == Material.SIGN_POST) || (blockMaterial == Material.WALL_SIGN)) && player.isSneaking()) 
	            {
	            	
	            	Sign sign = (Sign)block.getState();
	            	 try
	            	 {
	            		// OpenSignMenu(player, sign);	
	            	 }
	            	 catch(Exception e)
	            	 {
	            		player.sendMessage("Error");
	            	 }            	 
	            	                  
	            }    	
	    }
	  }
	  
	  
	 private void OpenSignMenu(Player player, Sign sign) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException   
	  {		 
		 		      Object tileEntity = Reflections.getDeclaredField(sign,  "sign");
		      
		      if(tileEntity == null)
		      {
		    	  player.sendMessage("Dead");
		    	  return;
		      }
		      
		      Reflections.setDeclaredField(tileEntity, "isEditable", true);
		      Reflections.setDeclaredField(tileEntity, Reflections.ver().startsWith("v1_7") ? "k" : "h", Reflections.getHandle(player));  
		      Reflections.sendPacket(player,  Reflections.getPacket("PacketPlayOutOpenSignEditor", new BlockPosition(sign.getX(), sign.getY(), sign.getZ())));
	  		     
	          /*
	          Reflections.setDeclaredField(tileEntity, Reflections.ver().startsWith("v1_7") ? "k" : "h", Reflections.getHandle(player)); 
	         
	          if(Reflections.ver().startsWith("v1_7"))
	          {
	        	  Reflections.sendPacket(player,  Reflections.getPacket("PacketPlayOutOpenSignEditor",  sign.getX(), sign.getY(), sign.getZ())); 
	          }
	          else
	          {
	        	  Reflections.callDeclaredConstructor(Reflections.getNMSClass("BlockPosition"), sign.getX(), sign.getY(), sign.getZ());
	          }
	          */

	  }	   
		  /*
	                    
			for (int i = 0; i < 4; ++i)
	        {
	          // Removes all color from the sign, pretty buggy if you don't
	          sign.setLine(i, sign.getLine(i).replace("§", "&"));
	          // Updates the sign so we open it after the color replace takes effect
	          sign.update();
	        }	
			
			
			
	        try 
	        {
	        
	        	 Object handle = player.getClass().getMethod("getHandle").invoke(player);
		            // Get the "CraftPlayer" from the player object so we can mess with
		            // it's connection
		            Object connection = handle.getClass().getField("playerConnection").get(handle);
		            // Gets the "PlayerConnection" from the "CraftPlayer" object we
		            // assinged as "handler"	           
	            Field tileField = sign.getClass().getDeclaredField("sign");
	            tileField.setAccessible(true);
	            // Gets the tileEntity sign for later use
	            Object tileSign = tileField.get(sign);
	            // Puts the tileEntity in an object
	            Field editable = tileSign.getClass().getDeclaredField("isEditable");
	            editable.setAccessible(true);
	            editable.set(tileSign, true);
	            // Makes the TileEntity sign editable so when we close the ui it
	            // doesn't undo the changes
	            Field handler = tileSign.getClass().getDeclaredField("h");
	            handler.setAccessible(true);
	            handler.set(tileSign, handle);
	            // This field is the handle of who's editing it so we set it as the
	            // craft player handle
	            Object position = getNMSClass("BlockPosition$PooledBlockPosition")
	                    .getMethod("d", double.class, double.class, double.class)
	                    .invoke(null, sign.getX(), sign.getY(), sign.getZ());
	            // Gets the "PooledPosition" of the sign for the packet editor
	            Object packet = getNMSClass("PacketPlayOutOpenSignEditor").getConstructor(getNMSClass("BlockPosition"))
	                    .newInstance(position);
	            // Gets the packet class, and initializes it as a new packet
	            // This packet is as it is named, it opens the sign ui
	            connection.getClass().getDeclaredMethod("sendPacket", getNMSClass("Packet")).invoke(connection, packet);
	            // This sends the packet to the playerconnection so the player
	            // "opens" the sign
	        }
	        catch (Exception x) 
	        {
	            v
	        }
	        
	    }
	  
	    private Class<?> getNMSClass(String clazz) 
	    {
	        try 
	        {	        	
	            return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + clazz);
	        } 
	        catch (ClassNotFoundException e)
	        {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    */
	    
}
