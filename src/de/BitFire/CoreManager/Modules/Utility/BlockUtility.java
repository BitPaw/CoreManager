package de.BitFire.CoreManager.Modules.Utility;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public final class BlockUtility 
{
	private BlockUtility()
	{
		
	}	
	
	public final static boolean IsTreeStamBlock(final Block block)
	{
		return IsTreeStamBlock(block.getType());
	}
	
	public final static Location GetBlockInFrontOfPlayer(final Player player)
	{
		return player.getEyeLocation();
	}
	
	public final static boolean IsTreeStamBlock(final Material material)
	{		
		switch(material)
		{
		case ACACIA_LOG:
		case BIRCH_LOG:
		case DARK_OAK_LOG:
		case JUNGLE_LOG : 
		case OAK_LOG:
		case SPRUCE_LOG:
			return true;
			
			default :
				return false;
		}
	}
	
	public final static boolean IsLeaveBlock(final Block block)	
	{
		final Material material = block.getType();
		
		switch(material)
		{
		case OAK_LEAVES:
		case ACACIA_LEAVES:
		case BIRCH_LEAVES:
		case DARK_OAK_LEAVES: 
		case JUNGLE_LEAVES:
		case SPRUCE_LEAVES:
			return true;
			
			default :
				return false;
		}
	}	
	
	public final static boolean IsStairsBlock(final Block block)
	{
		final Material material = block.getType();
		
		switch(material)
		{
		case ACACIA_STAIRS :
		case ANDESITE_STAIRS:
		case BIRCH_STAIRS :
		case BRICK_STAIRS :
		case COBBLESTONE_STAIRS :
		case DARK_OAK_STAIRS :
		case DARK_PRISMARINE_STAIRS :
		case DIORITE_STAIRS :
		case END_STONE_BRICK_STAIRS :
		case GRANITE_STAIRS :
		case JUNGLE_STAIRS:
		case MOSSY_COBBLESTONE_STAIRS :
		case MOSSY_STONE_BRICK_STAIRS :
		case NETHER_BRICK_STAIRS :
		case OAK_STAIRS :
		case POLISHED_ANDESITE_STAIRS:
			return true;
			
			default :
				return false;
		}
		

	}
	
	public final static boolean IsBockSign(final Block block)
	{
		final Material material = block.getType();
		
		switch(material)
		{
		case ACACIA_SIGN:
		case ACACIA_WALL_SIGN :
		case BIRCH_SIGN:
		case BIRCH_WALL_SIGN :
		case DARK_OAK_SIGN:
		case DARK_OAK_WALL_SIGN :
		case JUNGLE_SIGN :
		case JUNGLE_WALL_SIGN :	
		case OAK_SIGN :
		case OAK_WALL_SIGN :
		case SPRUCE_SIGN :
		case SPRUCE_WALL_SIGN:
			return true;
			
		default :
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	public final static int GetID(final Block block) 
	{
		return block.getData();
	}
	
	public static boolean IsBlockChest(final Block block) 
	{
		final Material material = block.getType();
				
		switch(material)
		{	
		case TRAPPED_CHEST:
		case ENDER_CHEST:
		case SHULKER_BOX:
		case CHEST:
			return true;
			
		default :
			return false;
		}
	}	
}
