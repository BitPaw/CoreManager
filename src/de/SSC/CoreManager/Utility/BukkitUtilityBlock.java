package de.SSC.CoreManager.Utility;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class BukkitUtilityBlock 
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
}
