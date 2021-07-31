package de.SSC.CoreManager.Addons.DropHead;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import de.SSC.CoreManager.Utility.BukkitUtility;

public class DropHeadCommand 
{	
	private HeadAPI _headAPI;
	private BukkitUtility _bukkitUtility;

	public DropHeadCommand()
	{
		  _headAPI = new HeadAPI();		  
		  _bukkitUtility = BukkitUtility.Instance();	
	}
	
	  @SuppressWarnings("deprecation")
		public void GetHead(CommandSender sender, String[] args)
		  {
		  
		  
		  
			  if (!(sender instanceof Player))
			    {
			      sender.sendMessage(ChatColor.RED + "This command can only be run by in-game players!");
			    }
			    String headStr = args.length == 0 ? sender.getName() : String.join("_", args).replace(':', '|');
			    
			    int i = headStr.indexOf('|');
			    String extraData;
			    String target;
			    
			    if (i != -1)
			    {
			      target = headStr.substring(0, i);
			      extraData = headStr.substring(i + 1).toUpperCase();
			    }
			    else
			    {
			      target = headStr;
			      extraData = null;
			    }
			    ItemStack head = null;
			    
			    EntityType eType = EvUtils.getEntityByName(target.toUpperCase());
			    String textureKey = eType.name() + "|" + extraData;
			    if (((eType != null) && (eType != EntityType.UNKNOWN)) || (_headAPI.textureExists(textureKey)))
			    {
			      if (extraData != null) {
			        if (_headAPI.textureExists(textureKey)) {
			          sender.sendMessage(ChatColor.GRAY + "Getting entity head with data value: " + extraData);
			        } else {
			          sender.sendMessage(ChatColor.RED + "Unknown data value for " + eType + ": " + ChatColor.YELLOW + extraData);
			        }
			      }
			      head = _headAPI.getHead(eType, textureKey);
			    }
			    else
			    {
			    	
			    	
			      OfflinePlayer p = _bukkitUtility.PlayerUtility.GetOfflinePlayer(target);;
			      
			      if ((p.hasPlayedBefore()) || (EvUtils.checkExists(p.getName())))
			      {
			        head = HeadUtils.getPlayerHead(p);
			      }
			      else if ((target.startsWith("MHF_")) && (HeadUtils.MHF_Lookup.containsKey(target.toUpperCase())))
			      {
			        head = new ItemStack(Material.PLAYER_HEAD);
			        SkullMeta meta = (SkullMeta)head.getItemMeta();
			        meta.setOwner(target);
			        meta.setDisplayName(ChatColor.YELLOW + (String)HeadUtils.MHF_Lookup.get(target.toUpperCase()));
			        head.setItemMeta(meta);
			      }
			      else if (target.length() > 100)
			      {
			        head = HeadUtils.makeSkull(target);
			      }
			    }
			    if (head != null)
			    {
			      String headName = (head.hasItemMeta()) && (head.getItemMeta().hasDisplayName()) ? 
			        head.getItemMeta().getDisplayName() : head.getType().name(); // maybe wrong
			      ((Player)sender).getInventory().addItem(new ItemStack[] { head });
			      sender.sendMessage(ChatColor.GREEN + "Spawned Head: " + ChatColor.GOLD + headName);
			    }
			    else
			    {
			      sender.sendMessage(ChatColor.RED + "Head \"" + target + "\" not found");
			    }
			  }
}
