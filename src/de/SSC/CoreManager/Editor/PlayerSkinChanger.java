package de.SSC.CoreManager.Editor;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.SSC.CoreManager.Main;
import de.SSC.CoreManagerPlugins.BukkitHook;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class PlayerSkinChanger
{
  private  SkinLoader _skinLoader;
  private BukkitHook _bukkitHook;
  private JavaPlugin _plugin;

    public PlayerSkinChanger()
    {
        _bukkitHook = Main.coreController._BukkitHook;
        _plugin = Main.Plugin;
    }

    private String GetUUID(String name)
    {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
        UUID uuid = offlinePlayer.getUniqueId();
        String returnValue = uuid.toString();

        returnValue = returnValue.replace("-", "");

        return returnValue;
    }

    public void ChangeSkin(CommandSender sender, String userName)
    {
        if(_bukkitHook.CheckIfCommandSenderIsPlayer(sender))
        {
            Player player = (Player) sender;
            GameProfile gp = ((CraftPlayer)sender).getProfile();

            gp.getProperties().clear();

            SkinLoader skin = new SkinLoader(GetUUID(userName));

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


            _bukkitHook.SendMessage(sender, "&6Skin &7changed as <&e" + userName + "&7>\n&8Remember you &8&ocan't &8see it yourself!");

        }
    }
}

