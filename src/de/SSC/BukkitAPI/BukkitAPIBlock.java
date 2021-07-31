package de.SSC.BukkitAPI;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class BukkitAPIBlock 
{
	public boolean IsWoodLog(Block block)
	{
		Material material = block.getType();
		
		return 	material == Material.ACACIA_LOG ||
				material == Material.BIRCH_LOG ||
				material == Material.DARK_OAK_LOG ||
				material == Material.JUNGLE_LOG || 
				material == Material.OAK_LOG || 
				material == Material.SPRUCE_LOG;
	}
	
	public boolean IsLeaveBlock(Block block)	
	{
		Material material = block.getType();
		
		return 	material == Material.OAK_LEAVES || 
				material == Material.ACACIA_LEAVES ||
				material == Material.BIRCH_LEAVES || 
				material == Material.DARK_OAK_LEAVES ||
				material == Material.JUNGLE_LEAVES ||
				material == Material.SPRUCE_LEAVES ;
	}	
	
	public boolean IsStairsBlock(Block block)
	{
		Material material = block.getType();
		
		return material == Material.ACACIA_STAIRS ||
				material == Material.ANDESITE_STAIRS ||
				material == Material.BIRCH_STAIRS ||
				material == Material.BRICK_STAIRS ||
				material == Material.BRICK_STAIRS ||
				material == Material.COBBLESTONE_STAIRS ||
				material == Material.DARK_OAK_STAIRS ||
				material == Material.DARK_PRISMARINE_STAIRS ||
				material == Material.DIORITE_STAIRS ||
				material == Material.END_STONE_BRICK_STAIRS ||
				material == Material.GRANITE_STAIRS ||
				material == Material.JUNGLE_STAIRS ||
				material == Material.MOSSY_COBBLESTONE_STAIRS ||
				material == Material.MOSSY_STONE_BRICK_STAIRS ||
				material == Material.NETHER_BRICK_STAIRS ||
				material == Material.OAK_STAIRS ||
				material == Material.POLISHED_ANDESITE_STAIRS;
	}
	
	public boolean IsBockSign(Block block)
	{
		Material material = block.getType();
		
		return material == Material.ACACIA_SIGN ||
				material == Material.ACACIA_WALL_SIGN ||
				material == Material.BIRCH_SIGN ||
				material == Material.BIRCH_WALL_SIGN ||
				material == Material.DARK_OAK_SIGN ||
				material == Material.DARK_OAK_WALL_SIGN ||
				material == Material.JUNGLE_SIGN ||
				material == Material.JUNGLE_WALL_SIGN ||
				//material == Material.LEGACY_SIGN ||
				//material == Material.LEGACY_SIGN_POST ||
				//material == Material.LEGACY_SIGN_POST ||
				material == Material.OAK_SIGN ||
				material == Material.OAK_WALL_SIGN ||
				material == Material.SPRUCE_SIGN ||
				material == Material.SPRUCE_WALL_SIGN;
	}

	@SuppressWarnings("deprecation")
	public int GetID(Block block) 
	{
		return block.getData();
	}	 
}