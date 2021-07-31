package de.SSC.CoreManagerPlugins;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.Main;
import de.SSC.CoreManager.Utility.CoreManagerPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Messager extends CoreManagerPlugin
{
    boolean MuteAll = false;

    // Referenz
    private Config _config;
    private ConsoleCommandSender _consoleCommandSender;

    public Messager()
    {
        _config = Main.config;
    }

    public String TransformToColor(String message)
    {
        if(_config.ChatColor)
        {
            message = ChatColor.translateAlternateColorCodes(_config.ColorCombineChar, message);
        }
        else
        {
            message = ChatColor.stripColor(message);
        }

        return message;
    }

    public void MessageToSender(CommandSender sender, String message)
    {
        sender.sendMessage(TransformToColor(message));
    }

    public void MessageToPlayer(Player player, String message)
    {
        if(!MuteAll)
        {
            player.sendMessage(TransformToColor(message));
        }
    }

    public void MessageToAllChat()
    {

    }

    public void MessageToConsole(String message)
    {
        if(_consoleCommandSender == null)
        {
            _consoleCommandSender = Bukkit.getConsoleSender();
        }

        _consoleCommandSender.sendMessage(TransformToColor(message));
    }
}
