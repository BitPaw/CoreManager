package de.SSC.CoreManager.SignEdit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EditableSignEventListener implements Listener
{
  private static Method exemptMethod;
  private static Method unexemptMethod;
  private static Object NCP;
  private static Object AUTOSIGN_TYPE;
  private static boolean USENCP;
  private Method reEditSign;
  
  static
  {
    setNCPMethods();
  }
  
  private String bukkitVersion = Bukkit.getServer().getClass().getName().split("\\.")[3];
  private JavaPlugin plugin;
  private final List<Location> blockedSigns;
  private final List<Player> blockedPlayers;
  
  public EditableSignEventListener(JavaPlugin plugin)
  {
    this.plugin = plugin;
    this.blockedSigns = new ArrayList<Location>();
    this.blockedPlayers = new ArrayList<Player>();
  }
  
  @EventHandler(ignoreCancelled=true)
  public void onSignPlace(SignChangeEvent event)
  {
    if (event.getPlayer().hasPermission("editablesign.colorsign"))
    {
      for (int i = 0; i < 4; i++) 
      {
        event.setLine(i, event.getLine(i).replace("&", "�"));
      }
    }
  
    unExemptFromNCP(event.getPlayer());
  }  
  
  @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
  public void onInteract(PlayerInteractEvent event)
  {
    if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) && 
      (event.getPlayer().isSneaking()) && 
      ((event.getClickedBlock().getState() instanceof Sign)))
    {
      final Player p = event.getPlayer();
      final Location loc = event.getClickedBlock().getLocation();
      if (!p.hasPermission("editablesign.admin"))
      {
        if (this.blockedSigns.contains(loc))
        {
          event.setCancelled(true);
          return;
        }
        if (this.blockedPlayers.contains(p))
        {
          event.setCancelled(true);
          return;
        }
      }
      if (p.hasPermission("editablesign.edit"))
      {
       
        if ((EditableSign.DEBUG) && 
          (p.getName().equalsIgnoreCase("nippontaro"))) {
          EditableSign.LOGGER.info(p.getName() + " can use it");
        }
        event.setCancelled(true);
        
        exemptFromNCP(p);
        
        Sign sign = (Sign)event.getClickedBlock().getState();
        for (int i = 0; i < 4; i++)
{
          sign.setLine(i, sign.getLine(i).replace("�", "&"));
        }
        sign.update();
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
        {
          public void run()
          {
            try
            {
              Object entityPlayer = EditableSignEventListener.this.getNMSEntity(p);
              Object sign = null;
              try
              {
                sign = EditableSignEventListener.this.getNMSClass("World").getMethod("getTileEntity", new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE }).invoke(EditableSignEventListener.this.getCraftWorld(p.getWorld()), new Object[] { Integer.valueOf(loc.getBlockX()), Integer.valueOf(loc.getBlockY()), Integer.valueOf(loc.getBlockZ()) });
              }
              catch (Exception e1)
              {
                try
                {
                  Class<?> blockposition = EditableSignEventListener.this.getNMSClass("BlockPosition");
                  Constructor<?> b_const = blockposition.getConstructor(new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE });
                  sign = EditableSignEventListener.this.getNMSClass("World").getMethod("getTileEntity", new Class[] { EditableSignEventListener.this.getNMSClass("BlockPosition") }).invoke(EditableSignEventListener.this.getCraftWorld(p.getWorld()), new Object[] { b_const.newInstance(new Object[] { Integer.valueOf(loc.getBlockX()), Integer.valueOf(loc.getBlockY()), Integer.valueOf(loc.getBlockZ()) }) });
                }
                catch (Exception e2)
                {
                  e2.printStackTrace();
                  return;
                }
              }
              Field isEditable = EditableSignEventListener.this.getNMSClass("TileEntitySign").getField("isEditable");
              isEditable.set(sign, Boolean.valueOf(true));
              EditableSignEventListener.this.reEditSign.invoke(entityPlayer, new Object[] { sign });
            }
            catch (Exception ex)
            {
              ex.printStackTrace();
            }
          }
        }, 2L);
        
        
        
      
          this.blockedSigns.add(loc);
          this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
          {
            public void run()
            {
              EditableSignEventListener.this.blockedSigns.remove(loc);
            }
          }, 0);
        
        
        
        
       
          this.blockedPlayers.add(p);
          this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
          {
            public void run()
            {
              EditableSignEventListener.this.blockedPlayers.remove(p);
            }
          }, 0);
        
      }
    }
  }
  
  boolean setReEditSignMethod()
  {
    try
    {
      for (Method method : getNMSClass("EntityPlayer").getMethods())
      {
        if ((method.getParameterTypes().length == 1) && (method.getParameterTypes()[0].getSimpleName().equals("TileEntity")))
        {
          this.reEditSign = method;
          break;
        }
        if ((method.getParameterTypes().length == 1) && (method.getParameterTypes()[0].getSimpleName().equals("TileEntitySign")))
        {
          this.reEditSign = method;
          break;
        }
      }
    }
    catch (Exception e)
    {
      if (EditableSign.DEBUG) {
        e.printStackTrace();
      }
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
      if (EditableSign.DEBUG) {
        e.printStackTrace();
      }
    }
    return null;
  }
  
  private Class<?> getNMSClass(String className)
  {
    try
    {
      return Class.forName("net.minecraft.server." + this.bukkitVersion + "." + className);
    }
    catch (Exception e)
    {
      if (EditableSign.DEBUG) {
        e.printStackTrace();
      }
    }
    return null;
  }
  
  private Class<?> getCraftClass(String className)
  {
    try
    {
      return Class.forName("org.bukkit.craftbukkit." + this.bukkitVersion + "." + className);
    }
    catch (Exception e)
    {
      if (EditableSign.DEBUG) {
        e.printStackTrace();
      }
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
    if (!USENCP) {
      return;
    }
    try
    {
      exemptMethod.invoke(NCP, new Object[] { p, AUTOSIGN_TYPE });
      if (EditableSign.DEBUG) {
        System.out.println("exempting  " + p + " from " + AUTOSIGN_TYPE);
      }
    }
    catch (Exception e)
    {
      if (EditableSign.DEBUG) {
        e.printStackTrace();
      }
    }
  }
  
  private void unExemptFromNCP(Player p)
  {
    if (!USENCP) {
      return;
    }
    try
    {
      unexemptMethod.invoke(NCP, new Object[] { p });
      if (EditableSign.DEBUG) {
        System.out.println("unexempting  " + p);
      }
    }
    catch (Exception e)
    {
      if (EditableSign.DEBUG) {
        e.printStackTrace();
      }
    }
  }
  
  static boolean setNCPMethods()
  {
    NCP = null;
    exemptMethod = null;
    unexemptMethod = null;
    try
    {
      Class<?> klass = Class.forName("fr.neatmonster.nocheatplus.hooks.NCPExemptionManager");
      NCP = klass;
      Class<?> checktype = Class.forName("fr.neatmonster.nocheatplus.checks.CheckType");
      for (Object o : checktype.getEnumConstants())
      {
        String name = o.toString();
        if (name.equals("BLOCKPLACE_AUTOSIGN")) {
          AUTOSIGN_TYPE = o;
        }
      }
      for (Method method : klass.getMethods()) {
        if ((method.getName().equalsIgnoreCase("unexempt")) && (method.getParameterTypes().length == 1) && (method.getParameterTypes()[0] == Player.class)) {
          unexemptMethod = method;
        } else if ((method.getName().equalsIgnoreCase("exemptPermanently")) && (method.getParameterTypes().length == 2) && (method.getParameterTypes()[0] == Player.class) && (method.getParameterTypes()[1] == checktype)) {
          exemptMethod = method;
        }
      }
      if ((NCP != null) && (exemptMethod != null) && (unexemptMethod != null))
      {
        USENCP = true;
        System.out.println("[ES] : USENCP = " + USENCP);
        System.out.println("[ES] : AUTOSIGN_TYPE = " + AUTOSIGN_TYPE);
      }
    }
    catch (Exception e)
    {
      if (EditableSign.DEBUG) {
        e.printStackTrace();
      }
      return false;
    }
    return true;
  }
}
