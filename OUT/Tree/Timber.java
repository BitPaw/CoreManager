package de.BitFire.Tree;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import de.BitFire.API.Bukkit.BukkitAPIBlock;
import de.BitFire.API.Bukkit.BukkitAPIItem;
import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Module;
import de.BitFire.Configuration.Config;
import de.BitFire.Permission.PermissionSystem;
import de.BitFire.Player.Exception.BukkitPlayerMissingException;
import de.BitFire.Player.Exception.CMPlayerIsNullException;
import de.BitFire.Player.Exception.InvalidPlayerUUID;
import de.BitFire.Player.Exception.PlayerNotFoundException;

public class Timber extends BaseSystem implements ISystem
{
	private static Timber _instance;
	private Map<Player, Block[]> _trees;
	private Config _config;
	private PermissionSystem _permissionSystem;
	
	private Timber()
	{
		super(Module.Timber, SystemState.Active, Priority.Low);
		_instance = this;
		
		_trees = new HashMap<Player, Block[]>();
	}

	public static Timber Instance()
	{
		return _instance == null ? new Timber() : _instance;
	}

	@Override
	public void LoadReferences() 
	{
		_config = Config.Instance();
		_permissionSystem = PermissionSystem.Instance();		
	}
	
	private boolean CheckIfWood(Block block)
	{
		return BukkitAPIBlock.IsTreeStamBlock(block);
	}
	
	private boolean CheckIfLeeves(Block block)
	{
		return BukkitAPIBlock.IsLeaveBlock(block);
	}
	
	private boolean Chop(Block block, Player player, World world)
	{
		List<Block> blocks = new LinkedList<Block>();
		Block highest = getHighestLog(block);
		
		if (isTree(highest, player, block))
		{
			getBlocksToChop(block, highest, blocks);
			
			if (_config.Timber.LogsMoveDown) 
			{
				moveDownLogs(block, blocks, world, player);
			}
			else 
			{				
				popLogs(block, blocks, world, player);
			}
		}
		else
		{
			return false;
		}
		return true;
	}
	
	private void getBlocksToChop(Block block, Block highest, List<Block> blocks)
	{
		int blockData;

		while (block.getY() <= highest.getY())
		{
			if (!blocks.contains(block)) {
				blocks.add(block);
			}
			getBranches(block, blocks, block.getRelative(BlockFace.NORTH));
			getBranches(block, blocks, block.getRelative(BlockFace.NORTH_EAST));
			getBranches(block, blocks, block.getRelative(BlockFace.EAST));
			getBranches(block, blocks, block.getRelative(BlockFace.SOUTH_EAST));
			getBranches(block, blocks, block.getRelative(BlockFace.SOUTH));
			getBranches(block, blocks, block.getRelative(BlockFace.SOUTH_WEST));
			getBranches(block, blocks, block.getRelative(BlockFace.WEST));
			getBranches(block, blocks, block.getRelative(BlockFace.NORTH_WEST));
			if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH))) {
				getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH));
			}
			if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_EAST))) {
				getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_EAST));
			}
			if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.EAST))) {
				getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.EAST));
			}
			if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_EAST))) {
				getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_EAST));
			}
			if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH))) {
				getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH));
			}
			if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_WEST))) {
				getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_WEST));
			}
			if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.WEST))) {
				getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.WEST));
			}
			if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_WEST))) {
				getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_WEST));
			}		

			blockData =  BukkitAPIBlock.GetID(block);			
			
			if (blockData == 3 || blockData == 7 || blockData == 11 || blockData == 15)
			{
				if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH, 2))) {
					getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH, 2));
				}
				if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_EAST, 2))) {
					getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_EAST, 2));
				}
				if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.EAST, 2))) {
					getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.EAST, 2));
				}
				if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_EAST, 2))) {
					getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_EAST, 2));
				}
				if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH, 2))) {
					getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH, 2));
				}
				if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_WEST, 2))) {
					getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_WEST, 2));
				}
				if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.WEST, 2))) {
					getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.WEST, 2));
				}
				if (!blocks.contains(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_WEST, 2))) {
					getBranches(block, blocks, block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_WEST, 2));
				}
			}
			if ((blocks.contains(block.getRelative(BlockFace.UP))) || (!CheckIfWood(block.getRelative(BlockFace.UP)))) {
				break;
			}
			block = block.getRelative(BlockFace.UP);
		}
	}

	private void getBranches(Block block, List<Block> blocks, Block other)
	{
		if (!blocks.contains(other) && CheckIfWood(other)) 
		{
			getBlocksToChop(other, getHighestLog(other), blocks);
		}
	}

	
	private Block getHighestLog(Block block)
	{
		boolean isLog = true;
		
		while (isLog) 
		{
			Block upperBlock = block.getRelative(BlockFace.UP);
			
			boolean isBlockInDirectReach = 
					CheckIfWood(upperBlock) ||
					CheckIfWood(upperBlock.getRelative(BlockFace.NORTH)) ||
					CheckIfWood(upperBlock.getRelative(BlockFace.EAST)) ||
					CheckIfWood(upperBlock.getRelative(BlockFace.SOUTH)) ||
					CheckIfWood(upperBlock.getRelative(BlockFace.WEST)) ||
					CheckIfWood(upperBlock.getRelative(BlockFace.NORTH)) ||
					CheckIfWood(upperBlock.getRelative(BlockFace.NORTH_EAST)) ||
					CheckIfWood(upperBlock.getRelative(BlockFace.NORTH_WEST)) ||
					CheckIfWood(upperBlock.getRelative(BlockFace.SOUTH_EAST)) ||
					CheckIfWood(upperBlock.getRelative(BlockFace.SOUTH_WEST));
			
			
			if (isBlockInDirectReach)
			{
				if (CheckIfWood(upperBlock)) 
				{
					block = upperBlock;
				}
								
				else if (CheckIfWood(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH))) {
					block = block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH);
				}
				else if (CheckIfWood(block.getRelative(BlockFace.UP).getRelative(BlockFace.EAST))) {
					block = block.getRelative(BlockFace.UP).getRelative(BlockFace.EAST);
				}
				else if (CheckIfWood(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH))) {
					block = block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH);
				}
				else if (CheckIfWood(block.getRelative(BlockFace.UP).getRelative(BlockFace.WEST))) {
					block = block.getRelative(BlockFace.UP).getRelative(BlockFace.WEST);
				}
				else if (CheckIfWood(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_EAST))) {
					block = block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_EAST);
				}
				else if (CheckIfWood(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_WEST))) {
					block = block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_WEST);
				}
				else if (CheckIfWood(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_EAST))) {
					block = block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_EAST);
				}
				else if (CheckIfWood(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_WEST))) {
					block = block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_WEST);
				}
			}
			else {
				isLog = false;
			}
		}
		return block;
	}

	@SuppressWarnings("deprecation")
	private boolean isTree(Block block, Player player, Block first)
	{
		int counter = 0;
		
		if (!_config.Timber.OnlyTrees) {
			return true;
		}
		if (_trees.containsKey(player))
		{
			Block[] blockarray = (Block[])_trees.get(player);
			
			for (int i = 0; i < Array.getLength(blockarray); i++)
			{
				if (blockarray[i] == block) 
				{
					return true;
				}
				if (blockarray[i] == first) 
				{
					return true;
				}
			}
		}
		
		if (CheckIfLeeves(block.getRelative(BlockFace.UP))) {
			counter++;
		}
		if (CheckIfLeeves(block.getRelative(BlockFace.UP).getRelative(BlockFace.UP))) {
			counter++;
		}
		if (CheckIfLeeves(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH))) {
			counter++;
		}
		if (CheckIfLeeves(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH))) {
			counter++;
		}
		if (CheckIfLeeves(block.getRelative(BlockFace.UP).getRelative(BlockFace.EAST))) {
			counter++;
		}
		if (CheckIfLeeves(block.getRelative(BlockFace.UP).getRelative(BlockFace.WEST))) {
			counter++;
		}
		if (CheckIfLeeves(block.getRelative(BlockFace.DOWN))) {
			counter++;
		}
		if (CheckIfLeeves(block.getRelative(BlockFace.NORTH))) {
			counter++;
		}
		if (CheckIfLeeves(block.getRelative(BlockFace.EAST))) {
			counter++;
		}
		if (CheckIfLeeves(block.getRelative(BlockFace.SOUTH))) {
			counter++;
		}
		if (CheckIfLeeves(block.getRelative(BlockFace.WEST))) {
			counter++;
		}
		if (counter >= 2) {
			return true;
		}
		if (block.getData() == 1)
		{
			block = block.getRelative(BlockFace.UP);
			if (CheckIfLeeves(block.getRelative(BlockFace.UP))) {
				counter++;
			}
			if (CheckIfLeeves(block.getRelative(BlockFace.UP).getRelative(BlockFace.UP))) {
				counter++;
			}
			if (CheckIfLeeves(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH))) {
				counter++;
			}
			if (CheckIfLeeves(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH))) {
				counter++;
			}
			if (CheckIfLeeves(block.getRelative(BlockFace.UP).getRelative(BlockFace.EAST))) {
				counter++;
			}
			if (CheckIfLeeves(block.getRelative(BlockFace.UP).getRelative(BlockFace.WEST))) {
				counter++;
			}
			if (CheckIfLeeves(block.getRelative(BlockFace.NORTH))) {
				counter++;
			}
			if (CheckIfLeeves(block.getRelative(BlockFace.EAST))) {
				counter++;
			}
			if (CheckIfLeeves(block.getRelative(BlockFace.SOUTH))) {
				counter++;
			}
			if (CheckIfLeeves(block.getRelative(BlockFace.WEST))) {
				counter++;
			}
			if (counter >= 2) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	private void popLogs(Block block, List<Block> blocks, World world, Player player)
	{

		ItemStack item = new ItemStack(Material.STONE, 1, (short)0, null);
		item.setAmount(1);
		for (int counter = 0; counter < blocks.size(); counter++)
		{
			block = (Block)blocks.get(counter);
			item.setType(block.getType());
			item.setDurability((short)block.getData());
			block.breakNaturally();

			if (_config.Timber.PopLeaves)
			{
				popLeaves(block);
			}
			if ((_config.Timber.MoreDamageToTools) && (breaksTool(player, player.getItemInHand())))
			{
				player.getInventory().clear(player.getInventory().getHeldItemSlot());

				if (_config.Timber.InterruptIfToolBreaks)
				{
					break;
				}
			}
		}
	}

	private void popLeaves(Block block)
	{
		int leafRadius = _config.Timber.LeafRadius;

		for (int y = -leafRadius; y < leafRadius + 1; y++)
		{
			for (int x = -leafRadius; x < leafRadius + 1; x++)
			{
				for (int z = -leafRadius; z < leafRadius + 1; z++)
				{
					Block target = block.getRelative(x, y, z);

					if (CheckIfLeeves(target))
					{
						target.breakNaturally();
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void moveDownLogs(Block block, List<Block> blocks, World world, Player player)
	{

		ItemStack item = new ItemStack(Material.STONE, 1, (short)0, null);
		item.setAmount(1);

		List<Block> downs = new LinkedList<Block>();
		for (int counter = 0; counter < blocks.size(); counter++)
		{
			block = (Block)blocks.get(counter);
			Block down = block.getRelative(BlockFace.DOWN);
			if ((down.getType() == Material.AIR) || CheckIfLeeves(down))
			{
				down.setType(block.getType());

				block.setType(Material.AIR);
				downs.add(down);
			}
			else
			{
				item.setType(block.getType());
				item.setDurability((short)block.getData());
				block.setType(Material.AIR);
				world.dropItem(block.getLocation(), item);

				if ((_config.Timber.MoreDamageToTools) && (breaksTool(player, player.getItemInHand())))
				{
					player.getInventory().clear(player.getInventory().getHeldItemSlot());
				}
			}
		}
		for (int counter = 0; counter < downs.size(); counter++)
		{
			block = (Block)downs.get(counter);
			if (isLoneLog(block))
			{
				downs.remove(block);
				block.breakNaturally();

				if ((_config.Timber.MoreDamageToTools) && (breaksTool(player, player.getItemInHand())))
				{
					player.getInventory().clear(player.getInventory().getHeldItemSlot());
				}
			}
		}

		if (_config.Timber.PopLeaves)
		{
			moveLeavesDown(blocks);
		}
		if (_trees.containsKey(player))
		{
			_trees.remove(player);
		}

		if (downs.isEmpty())
		{
			return;
		}
		Block[] blockarray = new Block[downs.size()];
		for (int counter = 0; counter < downs.size(); counter++) {
			blockarray[counter] = ((Block)downs.get(counter));
		}

		_trees.put(player, blockarray);
	}

	private void moveLeavesDown(List<Block> blocks)
	{
		List<Block> leaves = new LinkedList<Block>();
		int y = 0;
		int leafRadius = _config.Timber.LeafRadius;
		Iterator<Block> blockIterator = blocks.iterator();
		Block block;

		while ((blockIterator.hasNext()) && (y < leafRadius + 1))
		{
			block = (Block)blockIterator.next();
			y = -leafRadius;

			for (int x = -leafRadius; x < leafRadius + 1; x++)
			{
				for (int z = -leafRadius; z < leafRadius + 1; z++)
				{
					if (CheckIfLeeves(block.getRelative(x, y, z)) && (!leaves.contains(block.getRelative(x, y, z))))
					{
						leaves.add(block.getRelative(x, y, z));
					}
				}
			}
			y++;
		}

		for (Block currentBlock : leaves)
		{
			boolean isLowerBlockAir = currentBlock.getRelative(BlockFace.DOWN).getType().equals(Material.AIR);
			boolean isLowerBlockLeeves = CheckIfLeeves(currentBlock.getRelative(BlockFace.DOWN));
			
			boolean isSecondLowerBlockLeeves = CheckIfLeeves(currentBlock.getRelative(BlockFace.DOWN, 2));		
			boolean isSecondLowerBlockWood = CheckIfWood(currentBlock.getRelative(BlockFace.DOWN, 2));
			boolean isSecondLowerBlockAir = currentBlock.getRelative(BlockFace.DOWN, 2).getType().equals(Material.AIR);
			
			boolean isThirdLowerBlockLeeves = CheckIfLeeves(currentBlock.getRelative(BlockFace.DOWN, 3));
			boolean isThirdLowerBlockWood = CheckIfWood(currentBlock.getRelative(BlockFace.DOWN, 3));
			boolean isThirdLowerBlockAir = currentBlock.getRelative(BlockFace.DOWN, 3).getType().equals(Material.AIR);
			
			if 
			(
					(isLowerBlockAir || isLowerBlockLeeves) 
					&& 
					(isSecondLowerBlockAir || isSecondLowerBlockLeeves ||	isSecondLowerBlockWood) 
					&&
					(isThirdLowerBlockAir || isThirdLowerBlockLeeves) || isThirdLowerBlockWood)
			{
				currentBlock.getRelative(BlockFace.DOWN).setType(currentBlock.getType());
				currentBlock.setType(Material.AIR);
			}
			else
			{
				currentBlock.breakNaturally();
			}
		}
	}

	@SuppressWarnings("deprecation")
	private boolean breaksTool(Player player, ItemStack item)
	{
		boolean isItemAxe;
		short damage;		
		
		if(player.getGameMode() != GameMode.CREATIVE)		
		{
			if (item != null)
			{
				isItemAxe = BukkitAPIItem.IsItemAxe(item);			
			
				damage = item.getDurability();
				
				if (isItemAxe) 
				{
					damage = (short)(damage + 1);
				}
				else 
				{
					damage = (short)(damage + 2);
				}
				
				if (damage >= item.getType().getMaxDurability()) 
				{
					return true;
				}
				
				item.setDurability(damage);		
			}
		}	
		
		return false;
	}


	private boolean isLoneLog(Block block)
	{
		if (CheckIfWood(block.getRelative(BlockFace.UP))) 
		{
			return false;
		}
		if (block.getRelative(BlockFace.DOWN).getType() != Material.AIR) 
		{
			return false;
		}
		if (hasHorizontalCompany(block)) 
		{
			return false;
		}
		if (hasHorizontalCompany(block.getRelative(BlockFace.UP))) 
		{
			return false;
		}
		if (hasHorizontalCompany(block.getRelative(BlockFace.DOWN)))
		{
			return false;
		}
		return true;
	}

	private boolean hasHorizontalCompany(Block block)
	{
		if (CheckIfWood(block.getRelative(BlockFace.NORTH)))
		{
			return true;
		}
		if (CheckIfWood(block.getRelative(BlockFace.NORTH_EAST))) 
		{
			return true;
		}
		if (CheckIfWood(block.getRelative(BlockFace.EAST)))
		{
			return true;
		}
		if (CheckIfWood(block.getRelative(BlockFace.SOUTH_EAST))) 
		{
			return true;
		}
		if (CheckIfWood(block.getRelative(BlockFace.SOUTH)))
		{
			return true;
		}
		if (CheckIfWood(block.getRelative(BlockFace.SOUTH_WEST))) 
		{
			return true;
		}
		if (CheckIfWood(block.getRelative(BlockFace.WEST))) 
		{
			return true;
		}
		if (CheckIfWood(block.getRelative(BlockFace.NORTH_WEST))) 
		{
			return true;
		}
		return false;
	}


	/*
	private boolean interruptWhenBreak(Player player)
	{
		if (_config.Timber.InterruptIfToolBreaks)
		{
			return true;
		}
		return false;
	}
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@SuppressWarnings("deprecation")
	public void OnBlockBreak(BlockBreakEvent event) throws PlayerNotFoundException, InvalidPlayerUUID
	{		
		boolean moreDamageToTools = _config.Timber.MoreDamageToTools;
		boolean hasPermissionToUseTimber;
		boolean hasTool;
		Block block = event.getBlock();;
		Player player = event.getPlayer();;
		World world = block.getWorld();	;		
		
		if (event.isCancelled())
		{
			return;
		}			
		
		if (CheckIfWood(block))
		{								
			hasTool = BukkitAPIItem.IsItemAxeEquiped(player);						
			try {
				hasPermissionToUseTimber = _permissionSystem.HasPlayerPermission(player, TimberPermission.Use.toString());
			
			
				if (hasTool && hasPermissionToUseTimber) 
				{
					if (Chop(block, player, world))
					{
						if (!moreDamageToTools && breaksTool(player, player.getItemInHand()))
						{
							player.getInventory().clear(player.getInventory().getHeldItemSlot());
						}
					}
					else 
					{
						event.setCancelled(false);
					}
				}	
			
			
			} catch (CMPlayerIsNullException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BukkitPlayerMissingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
}