// 
// Decompiled by Procyon v0.5.36
// 

package de.BitFire.Chair;

import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import java.util.HashSet;
import java.util.Map;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.UUID;
import java.util.Set;

public class PlayerSitData
{
    private ChairsSystem plugin;
    private Set<UUID> sitDisabled;
    private Map<Player, SitData> sittingPlayers;
    private Map<Block, Player> occupiedBlocks;
    
    public PlayerSitData(ChairsSystem aplugin) 
    {
        sitDisabled = new HashSet<UUID>();
        sittingPlayers = new HashMap<Player, SitData>();
        occupiedBlocks = new HashMap<Block, Player>();
        plugin = aplugin;
    }
    
    public void disableSitting(final UUID playerUUID) {
        sitDisabled.add(playerUUID);
    }
    
    public void enableSitting(final UUID playerUUID) {
        sitDisabled.remove(playerUUID);
    }
    
    public boolean isSittingDisabled(final UUID playerUUID) {
        return sitDisabled.contains(playerUUID);
    }
    
    public boolean isSitting(final Player player) {
        return sittingPlayers.containsKey(player) && this.sittingPlayers.get(player).sitting;
    }
    
    public boolean isBlockOccupied(final Block block) {
        return occupiedBlocks.containsKey(block);
    }
    
    public Player getPlayerOnChair(final Block chair) {
        return occupiedBlocks.get(chair);
    }
    
    public boolean sitPlayer(final Player player, final Block blocktooccupy, Location sitlocation) 
    {
        final PlayerChairSitEvent playersitevent = new PlayerChairSitEvent(player, sitlocation.clone());
        Bukkit.getPluginManager().callEvent((Event)playersitevent);
        
        if (playersitevent.isCancelled()) 
        {
            return false;
        }
        
        sitlocation = playersitevent.getSitLocation().clone();
        
        if (this.plugin.getChairsConfig().msgEnabled) 
        {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getChairsConfig().msgSitEnter));
        }
        
        final Entity arrow = ChairsSystem.spawnChairsArrow(sitlocation);
        
        JavaPlugin javaPlugin = (JavaPlugin) Bukkit.getPluginManager().getPlugin("CoreManager"); 
        
        
        final SitData sitdata = new SitData(arrow, player.getLocation(), blocktooccupy, Bukkit.getScheduler().scheduleSyncRepeatingTask(javaPlugin, () -> this.resitPlayer(player), 1000L, 1000L));
        player.teleport(sitlocation);
        arrow.addPassenger((Entity)player);
        this.sittingPlayers.put(player, sitdata);
        this.occupiedBlocks.put(blocktooccupy, player);
        return sitdata.sitting = true;
    }
    
    public void resitPlayer(final Player player) {
        final SitData sitdata = this.sittingPlayers.get(player);
        sitdata.sitting = false;
        final Entity prevArrow = sitdata.arrow;
        final Entity newArrow = ChairsSystem.spawnChairsArrow(prevArrow.getLocation());
        newArrow.addPassenger((Entity)player);
        sitdata.arrow = newArrow;
        prevArrow.remove();
        sitdata.sitting = true;
    }
    
    public boolean unsitPlayer(final Player player) 
    {
        return unsitPlayer(player, true, true);
    }
    
    public void unsitPlayerForce(final Player player, final boolean teleport)
    {
        unsitPlayer(player, false, teleport);
    }
    
    private boolean unsitPlayer(final Player player, final boolean canCancel, final boolean teleport) {
        final SitData sitdata = this.sittingPlayers.get(player);
        final PlayerChairUnsitEvent playerunsitevent = new PlayerChairUnsitEvent(player, sitdata.teleportBackLocation.clone(), canCancel);
        Bukkit.getPluginManager().callEvent((Event)playerunsitevent);
        if (playerunsitevent.isCancelled() && playerunsitevent.canBeCancelled()) {
            return false;
        }
        sitdata.sitting = false;
        player.leaveVehicle();
        sitdata.arrow.remove();
        player.setSneaking(false);
        this.occupiedBlocks.remove(sitdata.occupiedBlock);
        Bukkit.getScheduler().cancelTask(sitdata.resitTaskId);
        this.sittingPlayers.remove(player);
        if (teleport) {
            player.teleport(playerunsitevent.getTeleportLocation().clone());
        }
        if (this.plugin.getChairsConfig().msgEnabled) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getChairsConfig().msgSitLeave));
        }
        return true;
    }
  
}
