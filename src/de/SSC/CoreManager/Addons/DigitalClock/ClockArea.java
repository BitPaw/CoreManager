package de.SSC.CoreManager.Addons.DigitalClock;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class ClockArea {
	 private Clock clock;
	  private Block startBlock;
	  private Block playersBlock;
	  private BlockFace direction;
	  private int width;
	  private int height;
	  private int depth;
	  
	  public ClockArea(Clock clock, Block startBlock, Block playersBlock, int de)
	  {
	    this.clock = clock;
	    this.startBlock = startBlock;
	    this.playersBlock = playersBlock;
	    setDirection();
	    setDimensions();
	    this.depth = de;
	  }
	  
	  protected ClockArea(Clock clock, Location sbL, Location pbL, BlockFace bf, int de)
	  {
	    this.clock = clock;
	    this.startBlock = sbL.getBlock();
	    this.playersBlock = pbL.getBlock();
	    this.direction = bf;
	    setDimensions();
	    this.depth = de;
	  }
	  
	  public void setDimensions()
	  {
	    this.width = (5 * Generator.getGenerator().getMain().getSettingsWidth() + 3);
	    if (getClock().showSeconds) {
	      this.width += 3 * Generator.getGenerator().getMain().getSettingsWidth() + 1;
	    }
	    if (getClock().ampm) {
	      this.width += 2 * Generator.getGenerator().getMain().getSettingsWidth() + 2;
	    }
	    this.height = 5;//Generator.getGenerator().getMain().getConfig().getInt("height");
	  }
	  
	  private void setDirection()
	  {
	    if (getStartBlock().getFace(getPlayersBlock()) != null)
	    {
	      this.direction = getStartBlock().getFace(getPlayersBlock());
	    }
	    else
	    {
	      int px = getPlayersBlock().getX();
	      int pz = getPlayersBlock().getZ();
	      int bx = getStartBlock().getX();
	      int bz = getStartBlock().getZ();
	      if ((px < bx) && (pz == bz)) {
	        this.direction = BlockFace.NORTH;
	      } else if ((px > bx) && (pz == bz)) {
	        this.direction = BlockFace.SOUTH;
	      } else if ((pz < bz) && (px == bx)) {
	        this.direction = BlockFace.EAST;
	      } else {
	        this.direction = BlockFace.WEST;
	      }
	    }
	  }
	  
	  public static void resetDimensions(Clock clock)
	  {
	    ClockArea ca = clock.getClockArea();
	    ca.setDimensions();
	    clock.updateClockArea(ca);
	    clock.write();
	  }
	  
	  public BlockFace rotate(String direction)
	  {
	    Generator.removeClockAndRestore(getClock());
	    this.direction = BlockFace.valueOf(direction.toUpperCase());
	    getClock().updateClockArea(this);
	    getClock().writeAndGenerate();
	    return this.direction;
	  }
	  
	  public void setDepth(int de)
	  {
	    Generator.removeClockAndRestore(getClock());
	    this.depth = de;
	    getClock().updateClockArea(this);
	    getClock().writeAndGenerate();
	  }
	  
	  public void move(Block block, Block playersblock)
	  {
	    Generator.removeClockAndRestore(getClock());
	    this.startBlock = block;
	    this.playersBlock = playersblock;
	    setDirection();
	    getClock().updateClockArea(this);
	    getClock().writeAndGenerate();
	  }
	  
	  public void setStartBlock(Block block)
	  {
	    this.startBlock = block;
	    getClock().write();
	  }
	  
	  public void setPlayersBlock(Block block)
	  {
	    this.playersBlock = block;
	    getClock().write();
	  }
	  
	  public Location getLocation(int up, int side, int before, int behind)
	  {
	    int x = getStartBlock().getX();
	    int y = getStartBlock().getY() + up;
	    int z = getStartBlock().getZ();
	    if (getDirection() == BlockFace.NORTH)
	    {
	      z += side + before;
	      x += behind;
	    }
	    else if (getDirection() == BlockFace.EAST)
	    {
	      x -= side + before;
	      z += behind;
	    }
	    else if (getDirection() == BlockFace.SOUTH)
	    {
	      z -= side + before;
	      x -= behind;
	    }
	    else
	    {
	      x += side + before;
	      z -= behind;
	    }
	    return new Location(getStartBlock().getWorld(), x, y, z);
	  }
	  
	  public Block getStartBlock()
	  {
	    return this.startBlock;
	  }
	  
	  public Block getPlayersBlock()
	  {
	    return this.playersBlock;
	  }
	  
	  public BlockFace getDirection()
	  {
	    return this.direction;
	  }
	  
	  public int getWidth()
	  {
	    return this.width;
	  }
	  
	  public int getHeight()
	  {
	    return this.height;
	  }
	  
	  public int getDepth()
	  {
	    return this.depth;
	  }
	  
	  public Clock getClock()
	  {
	    return this.clock;
	  }
	  
	  public static boolean containsAny(Location l)
	  {
	    for (String clockName : Generator.getGenerator().getMain().getClocksL())
	    {
	      Clock clock = Clock.loadClockByClockName(clockName);
	      if (clock.getClockArea().contains(l)) {
	        return true;
	      }
	    }
	    return false;
	  }
	  
	  public boolean contains(Location l)
	  {
	    Location max = getLocation(getHeight() - 1, getWidth() - 1, 0, getDepth() - 1);
	    if ((Math.abs(l.getBlockX() - getStartBlock().getX()) <= Math.abs(max.getBlockX() - getStartBlock().getX())) && (Math.abs(max.getBlockX() - l.getBlockX()) <= Math.abs(max.getBlockX() - getStartBlock().getX())) && (l.getBlockY() >= getStartBlock().getY()) && (l.getBlockY() <= max.getBlockY()) && (Math.abs(l.getBlockZ() - getStartBlock().getZ()) <= Math.abs(max.getBlockZ() - getStartBlock().getZ())) && (Math.abs(max.getBlockZ() - l.getBlockZ()) <= Math.abs(max.getBlockZ() - getStartBlock().getZ()))) {
	      return true;
	    }
	    return false;
	  }
}
