// 
// Decompiled by Procyon v0.5.36
// 

package de.SSC.Chairs;

import java.text.MessageFormat;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class SitUtils
{
    protected final ChairsSystem plugin;
    protected final ChairsConfig config;
    protected final PlayerSitData sitdata;
    
    public SitUtils(final ChairsSystem aplugin) {
        plugin = aplugin;
        config = plugin.getChairsConfig();
        sitdata = plugin.getPlayerSitData();
    }
    
    protected boolean canSitGeneric(final Player player, final Block block) {
        return !player.isSneaking() && player.hasPermission("chairs.sit") && !this.config.sitDisabledWorlds.contains(player.getWorld().getName()) && (this.config.sitMaxDistance <= 0.0 || player.getLocation().distance(block.getLocation().add(0.5, 0.0, 0.5)) <= this.config.sitMaxDistance) && (!this.config.sitRequireEmptyHand || player.getInventory().getItemInMainHand().getType() == Material.AIR) && !this.sitdata.isSittingDisabled(player.getUniqueId()) && !this.sitdata.isSitting(player) && !this.sitdata.isBlockOccupied(block);
    }
    
    @SuppressWarnings("incomplete-switch")
	public Location calculateSitLocation(final Player player, final Block block) {
        if (!this.canSitGeneric(player, block)) {
            return null;
        }
        final BlockData blockdata = block.getBlockData();
        float yaw = player.getLocation().getYaw();
        Double sitHeight = null;
        if (blockdata instanceof Stairs && this.config.stairsEnabled) {
            sitHeight = 0.5;
            final Stairs stairs = (Stairs)blockdata;
            if (!isStairsSittable(stairs)) {
                return null;
            }
            final BlockFace ascendingFacing = stairs.getFacing();
            if (this.config.stairsAutoRotate) {
                switch (ascendingFacing.getOppositeFace()) {
                    case NORTH: {
                        yaw = 180.0f;
                        break;
                    }
                    case EAST: {
                        yaw = -90.0f;
                        break;
                    }
                    case SOUTH: {
                        yaw = 0.0f;
                        break;
                    }
                    case WEST: {
                        yaw = 90.0f;
                        break;
                    }
                }
            }
            if (this.config.stairsMaxWidth > 0) {
                final BlockFace facingLeft = rotL(ascendingFacing);
                final BlockFace facingRight = rotR(ascendingFacing);
                final int widthLeft = this.calculateStairsWidth(ascendingFacing, block, facingLeft, this.config.stairsMaxWidth);
                final int widthRight = this.calculateStairsWidth(ascendingFacing, block, facingRight, this.config.stairsMaxWidth);
                if (widthLeft + widthRight + 1 > this.config.stairsMaxWidth) {
                    return null;
                }
                if (this.config.stairsSpecialEndEnabled) {
                    boolean specialEndCheckSuccess = false;
                    final Block blockLeft = block.getRelative(facingLeft, widthLeft + 1);
                    final Block blockRight = block.getRelative(facingRight, widthRight + 1);
                    if (this.config.stairsSpecialEndSign && isStairsEndingSign(facingLeft, blockLeft) && isStairsEndingSign(facingRight, blockRight)) {
                        specialEndCheckSuccess = true;
                    }
                    if (this.config.stairsSpecialEndCornerStairs && (isStairsEndingCornerStairs(facingLeft, Stairs.Shape.INNER_RIGHT, blockLeft) || isStairsEndingCornerStairs(ascendingFacing, Stairs.Shape.INNER_LEFT, blockLeft)) && (isStairsEndingCornerStairs(facingRight, Stairs.Shape.INNER_LEFT, blockRight) || isStairsEndingCornerStairs(ascendingFacing, Stairs.Shape.INNER_RIGHT, blockRight))) {
                        specialEndCheckSuccess = true;
                    }
                    if (!specialEndCheckSuccess) {
                        return null;
                    }
                }
            }
        }
        if (sitHeight == null) {
            sitHeight = this.config.additionalChairs.get(blockdata.getMaterial());
            if (sitHeight == null) {
                return null;
            }
        }
        final Location plocation = block.getLocation();
        plocation.setYaw(yaw);
        plocation.add(0.5, sitHeight - 0.5, 0.5);
        return plocation;
    }
    
    protected static final boolean isStairsSittable(final Stairs stairs) {
        return stairs.getHalf() == Bisected.Half.BOTTOM && stairs.getShape() == Stairs.Shape.STRAIGHT;
    }
    
    protected static boolean isStairsEndingSign(final BlockFace expectedFacing, final Block block) {
        final BlockData blockdata = block.getBlockData();
        return blockdata instanceof WallSign && expectedFacing == ((WallSign)blockdata).getFacing();
    }
    
    protected static boolean isStairsEndingCornerStairs(final BlockFace expectedFacing, final Stairs.Shape expectedShape, final Block block) {
        final BlockData blockdata = block.getBlockData();
        if (blockdata instanceof Stairs) {
            final Stairs stairs = (Stairs)blockdata;
            return stairs.getHalf() == Bisected.Half.BOTTOM && stairs.getFacing() == expectedFacing && stairs.getShape() == expectedShape;
        }
        return false;
    }
    
    protected int calculateStairsWidth(final BlockFace expectedFace, Block block, final BlockFace searchFace, final int limit) {
        for (int i = 0; i < limit; ++i) {
            block = block.getRelative(searchFace);
            final BlockData blockdata = block.getBlockData();
            if (!(blockdata instanceof Stairs)) {
                return i;
            }
            final Stairs stairs = (Stairs)blockdata;
            if (!isStairsSittable(stairs) || stairs.getFacing() != expectedFace) {
                return i;
            }
        }
        return limit;
    }
    
    protected static BlockFace rotL(final BlockFace face) {
        switch (face) {
            case NORTH: {
                return BlockFace.WEST;
            }
            case WEST: {
                return BlockFace.SOUTH;
            }
            case SOUTH: {
                return BlockFace.EAST;
            }
            case EAST: {
                return BlockFace.NORTH;
            }
            default: {
                throw new IllegalArgumentException(MessageFormat.format("Cant rotate blockface {0}", face));
            }
        }
    }
    
    protected static BlockFace rotR(final BlockFace face) {
        switch (face) {
            case NORTH: {
                return BlockFace.EAST;
            }
            case EAST: {
                return BlockFace.SOUTH;
            }
            case SOUTH: {
                return BlockFace.WEST;
            }
            case WEST: {
                return BlockFace.NORTH;
            }
            default: {
                throw new IllegalArgumentException(MessageFormat.format("Cant rotate blockface {0}", face));
            }
        }
    }
}
