package de.SSC.CoreManager.Changer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Messages.Logger;
import de.SSC.CoreManager.Messages.MessageType;
import de.SSC.CoreManager.Messages.Module;

public class SignEdit 
{
	private static SignEdit _instance;
	private Config _config;

	private static Method exemptMethod;
	private static Object NCP;
	private static Object AUTOSIGN_TYPE;
	private static boolean USENCP;
	private Method reEditSign;

	private String bukkitVersion = Bukkit.getServer().getClass().getName().split("\\.")[3];
	private JavaPlugin plugin;
	private List<Location> blockedSigns;
	private List<Player> blockedPlayers;

	private SignEdit()
	{
		_instance = this;

		_config = Config.Instance();

		blockedSigns = new ArrayList<Location>();
		blockedPlayers = new ArrayList<Player>();
	}

	public static SignEdit Instance() 
	{
		return _instance == null ? new SignEdit() : _instance;
	}

	public void SetJavaPlugin(JavaPlugin javaPlugin) 
	{
		plugin = javaPlugin;
	}

	public void OnSignPlace(SignChangeEvent event) 
	{
		Player player = event.getPlayer();
		String line;
		boolean hasPermission = player.hasPermission("editablesign.colorsign");
		int i;

		if (hasPermission) 
		{
			for (i = 0; i < 4; i++) 
			{
				line = event.getLine(i);

				line = line.replace(_config.Chat.ColorCombineChar, _config.Chat.ColorChar);

				event.setLine(i, line);
			}
		}
	}


	public void OnInteract(PlayerInteractEvent event) 
	{
		boolean correctAction;
		boolean isSneaking;
		boolean isSign;
		Player player = event.getPlayer();
		Location location;
		
		Sign sign;

		Action currentAction = event.getAction();
		Action wantedAction = Action.RIGHT_CLICK_BLOCK;

		correctAction = currentAction == wantedAction;
		isSneaking = player.isSneaking();
		isSign = event.getClickedBlock().getState() instanceof Sign;

		if (correctAction && isSneaking && isSign) 
		{
			location = event.getClickedBlock().getLocation();

			if (!player.hasPermission("editablesign.admin"))
			{
				if (this.blockedSigns.contains(location)) 
				{
					event.setCancelled(true);
					return;
				}
				if (this.blockedPlayers.contains(player)) 
				{
					event.setCancelled(true);
					return;
				}
			}
			
			if (player.hasPermission("editablesign.edit"))
			{
				event.setCancelled(true);

				exemptFromNCP(player);

				sign = (Sign)event.getClickedBlock().getState();
				
				// De-Color the sign
				{
					for (int i = 0; i < 4; i++)
					{
						sign.setLine(i, sign.getLine(i).replace(_config.Chat.ColorChar, _config.Chat.ColorCombineChar));
					}
					
					sign.update();
				}		
				
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
				{
							
					public void run()					 
					{	
						int x = location.getBlockX();							
						int y = location.getBlockY();								
						int z = location.getBlockZ();													
							
						try
						{								
							Object entityPlayer = getNMSEntity(player);								
							Object sign = null;
								
							try						 
							{								
								sign = getNMSClass("World").getMethod
										("getTileEntity", new Class[] 
																						{
												Integer.TYPE, Integer.TYPE, Integer.TYPE 
												}
								).invoke(getCraftWorld(player.getWorld()), new Object[] 
										{
												x, y, z 
										}
								);	
							}
							
							catch (Exception e1)
							{
								Class < ? > blockposition;
								Constructor < ? > b_const;
								
							  try 
							  {
										blockposition = getNMSClass("BlockPosition");
										b_const = blockposition.getConstructor(new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE });
										sign = getNMSClass("World").getMethod("getTileEntity", new Class[]
									  {
												getNMSClass("BlockPosition")
											}).invoke(getCraftWorld(player.getWorld()), new Object[]
											{
													b_const.newInstance(new Object[]
															{
															x, y, z
														})
												});
								
							  }	
							  catch (Exception e2)					
							  {						
								  e2.printStackTrace();
						
								  return;
							  }				
							}
							
				Field isEditable = getNMSClass("TileEntitySign").getField("isEditable");
				isEditable.set(sign, Boolean.valueOf(true));
				reEditSign.invoke(entityPlayer, new Object[] { sign });
			}
						catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
					}, 2L);




				blockedSigns.add(location);
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
					{
		  public void run()
					  {
		  blockedSigns.remove(location);
	  }
					}, 0);





				this.blockedPlayers.add(player);
				this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
					{
		  public void run()
					  {
		  blockedPlayers.remove(player);
	  }
					}, 0);

			}
		}
	}

	public boolean setReEditSignMethod()
	{
		try 
		{
			for (Method method : getNMSClass("EntityPlayer").getMethods())
			{
				if ((method.getParameterTypes().length == 1) && (method.getParameterTypes()[0].getSimpleName().equals("TileEntity"))) {
					this.reEditSign = method;
					break;
				}
				if ((method.getParameterTypes().length == 1) && (method.getParameterTypes()[0].getSimpleName().equals("TileEntitySign"))) {
					this.reEditSign = method;
					break;
				}
			}
		}
		catch (Exception e)
		{
			Logger logger = Logger.Instance();

			String errorMessage = e.getStackTrace().toString();			
			
			logger.SendToConsole(Module.SignEdit, MessageType.Error, errorMessage);

			return false;
		}

		return true;
	}

	private Object getCraftWorld(World world)
	{
		try 
		{
			return getCraftClass("CraftWorld").getMethod("getHandle", new Class[0]).invoke(world, new Object[0]);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private Class < ? > getNMSClass(String className)
	{
		try 
		{
			return Class.forName("net.minecraft.server." + this.bukkitVersion + "." + className);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private Class < ? > getCraftClass(String className)
	{
		try 
		{
			return Class.forName("org.bukkit.craftbukkit." + this.bukkitVersion + "." + className);
		}
		catch (Exception e)
		{

			e.printStackTrace();

		}

		return null;
	}

	private Object getNMSEntity(Entity entity)
	{
		try 
		{
			return getCraftClass("entity.CraftEntity").getMethod("getHandle", new Class[0]).invoke(entity, new Object[0]);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	private void exemptFromNCP(Player p)
	{
		if (!USENCP)
		{
			return;
		}
		try 
		{
			exemptMethod.invoke(NCP, new Object[]{ p, AUTOSIGN_TYPE });

			System.out.println("exempting  " + p + " from " + AUTOSIGN_TYPE);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}