package de.SSC.Chairs;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public class SitData
{
    protected final Location teleportBackLocation;
    protected final Block occupiedBlock;
    protected final int resitTaskId;
    protected boolean sitting;
    protected Entity arrow;
    
    public SitData(final Entity arrow, final Location teleportLocation, final Block block, final int resitTaskId) {
        this.arrow = arrow;
        this.teleportBackLocation = teleportLocation;
        this.occupiedBlock = block;
        this.resitTaskId = resitTaskId;
    }
}
