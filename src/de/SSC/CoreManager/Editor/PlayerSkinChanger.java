package de.SSC.CoreManager.Editor;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import de.SSC.CoreManager.Messages.Logger;
import de.SSC.CoreManager.Messages.MessageType;
import de.SSC.CoreManager.Messages.Module;
import de.SSC.CoreManager.Utility.BukkitUtility;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_13_R2.CraftOfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class PlayerSkinChanger
{
	private static PlayerSkinChanger _instance;
  private Logger _logger;
  private BukkitUtility _bukkitUtility;
  private JavaPlugin _plugin;

    public PlayerSkinChanger()
    {
    	_logger = Logger.Instance();
    	_bukkitUtility = BukkitUtility.Instance();
    }



    @SuppressWarnings("deprecation")
	public void ChangeSkin(CommandSender sender, String userName)
    {
        if(_bukkitUtility.IsSenderPlayer(sender))
        {
            Player player = (Player) sender;
            GameProfile gp = ((CraftOfflinePlayer)sender).getProfile();
            String message = "&6Skin &7changed as <&e" + userName + "&7>\n&8Remember you &8&ocan't &8see it yourself!";
            
            UUID uuid = _bukkitUtility.GetOfflinePlayerUUID(userName);
            String playerUUID = uuid.toString();
            playerUUID = playerUUID.replace("-", "");           

            
            gp.getProperties().clear();

            SkinLoader skin = new SkinLoader(playerUUID);

            if(skin.getSkinName() != null)
            {
                gp.getProperties().put(skin.getSkinName(),new Property(skin.getSkinName(), skin.getSkinValue(),skin.getSkinSignatur()));
            }

            Bukkit.getScheduler().runTaskLater(_plugin, () ->
            {
                for(Player pl : Bukkit.getOnlinePlayers())
                {
                    pl.hidePlayer(player);
                }

            }, 1);

            Bukkit.getScheduler().runTaskLater(_plugin, () ->
            {
                for(Player pl : Bukkit.getOnlinePlayers())
                {
                    pl.showPlayer(player);
                }

            }, 20);


            _logger.SendToSender(Module.SkinChanger, MessageType.Info, sender, message);            

        }
    }



	public static PlayerSkinChanger Instance() 
	{
		if(_instance == null)
		{
			_instance = new PlayerSkinChanger();
		}
		
		return _instance;
	}
}

