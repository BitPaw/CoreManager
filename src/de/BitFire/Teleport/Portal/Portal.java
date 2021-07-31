package de.BitFire.Teleport.Portal;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;

import de.BitFire.API.Bukkit.BukkitAPIPlayer;
import de.BitFire.File.FlagParser;
import de.BitFire.File.FlagType;
import de.BitFire.Geometry.Cube;
import de.BitFire.Geometry.Point;
import de.BitFire.World.CMWorld;
import de.BitFire.World.WorldSystem;

public class Portal
{
	public final int ID;
	public final String Name;
	public final Cube Field;	
	
	/**
	 * Is the current Portal active? If the player still interacts, tell him!
	 */
	public boolean IsActive;
	public boolean NeedsPermission;
	public int LinkedPortalID;
	public int WorldID;
	public BlockFace Direction;

	public Portal(final int id, final Cube cube, final String name) 
	{
		ID = id;
		Field = cube;
		Name = name;
		IsActive = true;
		NeedsPermission = false;
		LinkedPortalID = -1;
		Direction = BlockFace.NORTH;
	}
	
	public boolean HasPortalAttached()
	{
		return LinkedPortalID != -1;
	}
	
	public Map<Character, Boolean> GenerateFlagMap()
	{
		Map<Character, Boolean> map = new HashMap<Character, Boolean>();
		
		map.put(FlagParser.GetCorospondingChar(FlagType.PermissionToUse), NeedsPermission);
		
		return map;
	}
	
	public Location GetLocation() 
	{
		WorldSystem worldSystem = WorldSystem.Instance();
		
		final CMWorld cmWorld = worldSystem.GetWorld(WorldID);
		final World world = cmWorld.BukkitWorld;
		final Point point = Field.GetMiddlePoint();		
		final float pitch = 0;
		final float yaw = BukkitAPIPlayer.GetYawFromDirection(Direction);	
		final Location location = new Location(world, point.X, point.Y, point.Z, yaw, pitch);

		return location;
	}
}