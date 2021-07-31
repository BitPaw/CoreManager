package de.BitFire.Sign;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.BitFire.Main;
import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Configuration.Config;

public class SignEditSystem extends BaseSystem  implements ISystem
{
	private static SignEditSystem _instance;
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
	
	private SignEditSystem()
	{
		super(Module.SignEditSystem, SystemState.Active, Priority.Low);
		_instance = this;

		blockedSigns = new ArrayList<Location>();
		blockedPlayers = new ArrayList<Player>();
	}

	public static SignEditSystem Instance()
	{
		return _instance == null ? new SignEditSystem() : _instance;
	}
	
	@Override
	public void LoadReferences() 
	{		
		_config = Config.Instance();	
		
		plugin = Main.GetPluginInstance();
	}

	private String ConvertTagToText(String line)
	{
		if(line.contains(_config.SignEdit.LineTag.Tag))
		{
			line = _config.SignEdit.LineTag.Content;
		}
		else if(line.contains(_config.SignEdit.TeleportTag.Tag))
		{
			line = _config.SignEdit.TeleportTag.Content;
		}	
		
		return line;
	}
	
	private String ConvertTextToTag(String line)
	{
		if(line.contains(_config.SignEdit.LineTag.Content))
		{
			line = _config.SignEdit.LineTag.Tag;
		}
		else if(line.contains(_config.SignEdit.TeleportTag.Content))
		{
			line = _config.SignEdit.TeleportTag.Tag;
		}
		
		return line;
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

				line = ConvertTagToText(line);					
				
				line = line.replace(_config.Chat.ColorCombineChar, _config.Chat.ColorChar);

				event.setLine(i, line);
			}
		}
	}

	public void OnInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		Location location;
		Sign sign;
		Action currentAction = event.getAction();
		Action wantedAction = Action.RIGHT_CLICK_BLOCK;
		Block block = event.getClickedBlock();
		boolean correctAction = currentAction == wantedAction;;
		boolean isSneaking = player.isSneaking();;
		boolean isSign = block.getState() instanceof Sign;				
		
		if(!correctAction || !isSneaking || block == null)
		{
			return;
		}	

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
						String line = sign.getLine(i);
						
						// Remove Color
						line = line.replace(_config.Chat.ColorChar, _config.Chat.ColorCombineChar);
						
						// Revert used Tags 
						line = ConvertTextToTag(line);
								
						sign.setLine(i, line);
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

			logger.SendToConsole(Module.SignEditSystem, MessageType.Error, errorMessage);

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