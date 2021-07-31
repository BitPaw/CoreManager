package de.BitFire.API.Bukkit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.minecraft.server.v1_15_R1.Item;

public final class BukkitAPIItem 
{
	private BukkitAPIItem()
	{
		
	}	
	
	public final static boolean IsItemAxeEquiped(Player player)
	{
		PlayerInventory playerInventory = player.getInventory();		
		ItemStack itemMainHand = playerInventory.getItemInMainHand();
		//ItemStack itemOffHand = playerInventory.getItemInOffHand();	
		
		return IsItemAxe(itemMainHand);// || IsItemAxe(itemOffHand);
	}
	
	public final static boolean IsItemAxe(final ItemStack item)
	{
		final Material itemMaterial = item.getType();
		
		switch(itemMaterial)
		{
		case WOODEN_AXE:
		case STONE_AXE:
		case IRON_AXE:
		case GOLDEN_AXE:
		case DIAMOND_AXE:
			return true;
			
			default:
				return false;
		}
	}
	
	@SuppressWarnings("deprecation")
	public final static int GetID(final Material material) 
	{
		return material.getId();
	}
	
	public final static Material GetMaterialPerID(final int id) 
	{
		final Item item = Item.getById(id);
		String name = item.getName();
		final int cutatindex = name.lastIndexOf(".") +1;
		name = name.substring(cutatindex);
		final Material material = Material.valueOf(name.toUpperCase());
		
				
		return material;
	}
}