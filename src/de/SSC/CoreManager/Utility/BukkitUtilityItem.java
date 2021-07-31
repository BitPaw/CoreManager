package de.SSC.CoreManager.Utility;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class BukkitUtilityItem 
{
	public boolean IsItemAxeEquiped(Player player)
	{
		PlayerInventory playerInventory =  player.getInventory();		
		ItemStack itemMainHand = playerInventory.getItemInMainHand();
		//ItemStack itemOffHand = playerInventory.getItemInOffHand();	
		
		return IsItemAxe(itemMainHand);// || IsItemAxe(itemOffHand);
	}
	
	public boolean IsItemAxe(ItemStack item)
	{
		Material itemMaterial= item.getType();
		
		return 	itemMaterial == Material.WOODEN_AXE ||
				itemMaterial == Material.STONE_AXE ||
				itemMaterial == Material.IRON_AXE ||
				itemMaterial == Material.GOLDEN_AXE ||
				itemMaterial == Material.DIAMOND_AXE;
	}
}
