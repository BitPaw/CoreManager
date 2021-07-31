package de.SSC.CoreManager.Editor;

import de.SSC.CoreManager.Config.Messages;
import de.SSC.CoreManager.Main;
import de.SSC.CoreManager.Utility.CoreManagerPlugin;
import de.SSC.CoreManagerPlugins.BukkitHook;
import de.SSC.CoreManagerPlugins.Messager;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerEditor extends CoreManagerPlugin
{
    private BukkitHook _bukkitHook;
    private Messager _messager;
    private Messages _messages;

    public PlayerEditor()
    {
        _bukkitHook = Main.coreController._BukkitHook;
        _messager = Main.coreController._Messager;
        _messages = Main.messages;
    }

    public void Heal(CommandSender sender)
    {
        if(_bukkitHook.CheckIfCommandSenderIsPlayer(sender))
        {
            Player player = (Player)sender;
            String message = _messages.HealedBy;
            double healthDifferenz = 0;

            // Health
            {
                double oldHealth = player.getHealth();
                double maxHealth = player.getHealthScale();
                healthDifferenz = maxHealth - oldHealth;

                player.setHealth(maxHealth);
            }

            // Food
            {
                int maxFoodLevel = 20;

                player.setFoodLevel(maxFoodLevel);
            }

            message = message.replace(_messages.TagValue, Double.toString(healthDifferenz));

            _messager.MessageToPlayer(player, message);
        }
    }

    public void ChangeSpeed(CommandSender sender,  int value)
    {
        if(_bukkitHook.CheckIfCommandSenderIsPlayer(sender))
        {
            try
            {

            } catch (Exception e)
            {
               // _messager.MessageToPlayer(player, _messages.GameModeChangeError);
            }
        }
    }

    public void ChangeGameMode(CommandSender sender,  String mode)
    {
        if(_bukkitHook.CheckIfCommandSenderIsPlayer(sender))
        {
            Player player = (Player)sender;
            int value = Integer.parseInt(mode);
            String message = _messages.GameModeChanged;
            GameMode gameMode;

            try
            {
                gameMode = GameMode.getByValue(value);
            }
            catch (Exception e)
            {
                _messager.MessageToPlayer(player, _messages.GameModeChangeError);
                return;
            }

            message = message.replace(_messages.TagGameMode, gameMode.toString());

            _messager.MessageToPlayer(player, message);

            player.setGameMode(gameMode);

        }
    }
}
