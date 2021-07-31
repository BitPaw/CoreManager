package de.SSC.CoreManager.Addons.DigitalClock;

import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import de.SSC.CoreManager.Addons.DigitalClock.CMD.MaterialCommand;

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.Listener;

public class Events  
{	    
	 /*
	    public void onBlockPlace(final BlockPlaceEvent evt) {
	      
	    	
	    	if (this.i.protectClocks() && ClockArea.containsAny(evt.getBlock().getLocation())) {
	            evt.setCancelled(true);
	        }
	        else {
	            final Player player = evt.getPlayer();
	            if (this.i.getEnableBuildUsers().containsKey(player)) {
	                final Block block = evt.getBlockPlaced();
	                if (new MaterialCommand().isSolid(block.getType())) {
	                    final Block playersBlock = player.getWorld().getBlockAt(new Location(player.getWorld(), (double)player.getLocation().getBlockX(), (double)block.getY(), (double)player.getLocation().getBlockZ()));
	                    final Clock clock = new Clock(this.i.getEnableBuildUsers().get(player), player.getName(), block, playersBlock, 1);
	                    clock.writeAndGenerate();
	                    player.sendMessage(ChatColor.DARK_GREEN + DigitalClockSystem.getMessagePrefix() + ChatColor.GREEN + " Your clock '" + clock.getName() + "' has been successfully created!" + (this.i.shouldRun() ? " It is running now." : (" It isn't running, you can start it by '/dc runclock " + clock.getName() + "'.")));
	                    this.i.getClocks();
	                    if (this.i.shouldRun()) {
	                        this.i.run(clock.getName());
	                    }
	                    this.i.getEnableBuildUsers().remove(player);
	                }
	                else {
	                    player.sendMessage(ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " Placed item is not a block (cuboid shape). Please place a block.");
	                }
	                evt.setCancelled(true);
	            }
	        }
	        
	    }
	    
	
	    public void onPlayerInteract(final PlayerInteractEvent evt) {
	        final Player player = evt.getPlayer();
	        if (this.i.getEnableBuildUsers().containsKey(player) && evt.getItem() == null) {
	            player.sendMessage(ChatColor.DARK_GREEN + DigitalClockSystem.getMessagePrefix() + ChatColor.GREEN + " Creating of new clock stopped.");
	            this.i.getEnableBuildUsers().remove(player);
	        }
	        if (evt.getAction() == Action.RIGHT_CLICK_BLOCK && this.i.getEnableMoveUsers().containsKey(player)) {
	            final Block block = evt.getClickedBlock();
	            final Block playersblock = player.getWorld().getBlockAt(new Location(player.getWorld(), (double)player.getLocation().getBlockX(), (double)block.getY(), (double)player.getLocation().getBlockZ()));
	            final Clock clock = Clock.loadClockByClockName(this.i.getEnableMoveUsers().get(player));
	            clock.getClockArea().move(block, playersblock);
	            player.sendMessage(ChatColor.DARK_GREEN + DigitalClockSystem.getMessagePrefix() + ChatColor.GREEN + " Your clock '" + clock.getName() + "' has successfully moved to your position!");
	            this.i.getEnableMoveUsers().remove(player);
	        }
	    }
	    
	 
	    public void onCountdownEnd(final CountdownEndEvent evt) {
	        this.i.getConsole().info("[DigitalClock] Countdown of clock '" + evt.getClock().getName() + "' ended! Clock has been stopped.");
	    }
	    
	  
	    public void onStopwatchEnd(final StopwatchEndEvent evt) {
	        this.i.getConsole().info("[DigitalClock] Stopwatch of clock '" + evt.getClock().getName() + "' ended at " + evt.getEndValue() + " seconds! Clock has been stopped.");
	    }
	    
	    
	    public void onPlayerJoin(final PlayerJoinEvent evt) {
	        if (this.i.versionWarning() && (evt.getPlayer().isOp() || evt.getPlayer().hasPermission("digitalclock.update")) && !Version.getActualVersion().getVersion().equals(this.i.getDescription().getVersion())) {
	            evt.getPlayer().sendMessage(ChatColor.DARK_GREEN + DigitalClockSystem.getMessagePrefix() + ChatColor.GREEN + " There is a newer version (v" + Version.getActualVersion().getVersion() + ") of this plugin (v" + this.i.getDescription().getVersion() + "). Download it from " + ChatColor.UNDERLINE + ChatColor.BLUE + Version.getActualVersion().getDownloadLink() + ChatColor.RESET + ChatColor.GREEN + " or use command '/dc update'.");
	        }
	    }
	    
	   
	    public void onBlockDamage(final BlockDamageEvent evt) {
	        if (this.i.protectClocks() && ClockArea.containsAny(evt.getBlock().getLocation())) {
	            evt.setCancelled(true);
	        }
	    }
	    
	  
	    public void onBlockBreak(final BlockBreakEvent evt) {
	        if (this.i.protectClocks() && ClockArea.containsAny(evt.getBlock().getLocation())) {
	            evt.setCancelled(true);
	        }
	    }
	    

	    public void onItemSpawn(final ItemSpawnEvent evt) {
	        if (ClockArea.containsAny(evt.getLocation())) {
	            evt.setCancelled(true);
	        }
	    }
	    */
}
