package de.SSC.CoreManager.SignEdit;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class EditableSignCommandExecutor implements CommandExecutor
{
  private ChatColor getAllowed(CommandSender p, String perm)
  {
    return p.hasPermission(perm) ? ChatColor.AQUA : ChatColor.GRAY;
  }
  
  private boolean isAllowed(CommandSender p, String perm)
  {
    return p.hasPermission(perm);
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    if ((args == null) || (args.length == 0) || ((args.length == 1) && (args[0].equalsIgnoreCase("help"))))
    {
      Do_Help(sender);
      return true;
    }
    String subcmd = args[0].toLowerCase();
    switch (subcmd)
    {
      case "debug":       Do_Debug(sender, args);      break;
    }
    return true;
  }
  
  private void Do_Help(CommandSender sender)
  {
    sender.sendMessage("=== " + ChatColor.YELLOW + "[" + ChatColor.GREEN + "EditableSign Commands List" + ChatColor.YELLOW + "] " + ChatColor.RESET + "==="); 
    sender.sendMessage("- " + ChatColor.AQUA + "/editsign or /editsign help : display this help menu.");
    
    if (isAllowed(sender, "editablesign.reload"))
    {
      sender.sendMessage("- " + getAllowed(sender, "editablesign.reload") + "/editsign reload : reloads config file.");
    }
  }
  
  private void Do_Debug(CommandSender sender, String[] args)
  {
    if ((((sender instanceof Player)) && (sender.isOp())) || (((sender instanceof ConsoleCommandSender)) && (args.length > 1) && (args[0].equalsIgnoreCase("debug"))))
    {
      EditableSign.DEBUG = Boolean.parseBoolean(args[1]);
      EditableSign.LOGGER.info("[SE] turned the DEBUG mode " + EditableSign.DEBUG);
    }
  }
}
