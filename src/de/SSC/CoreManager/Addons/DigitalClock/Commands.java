package de.SSC.CoreManager.Addons.DigitalClock;

import org.bukkit.ChatColor;

import org.bukkit.entity.Player;

import de.SSC.CoreManager.Addons.DigitalClock.CMD.*;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

import org.bukkit.command.CommandExecutor;

public class Commands implements CommandExecutor 
{
	 private DigitalClockSystem i;
	    public static final HashMap<String, Class<?>> commandList;
	    
	    static {
	        (commandList = new HashMap<String, Class<?>>()).put("create", CommandCreate.class);
	        Commands.commandList.put("remove", CommandRemove.class);
	        Commands.commandList.put("delete", CommandRemove.class);
	        Commands.commandList.put("rotate", CommandRotate.class);
	        Commands.commandList.put("material", CommandMaterial.class);
	        Commands.commandList.put("fill", CommandFill.class);
	        Commands.commandList.put("move", CommandMove.class);
	        Commands.commandList.put("addingminutes", CommandAddingminutes.class);
	        Commands.commandList.put("tp", CommandTP.class);
	        Commands.commandList.put("stopclock", CommandStopclock.class);
	        Commands.commandList.put("runclock", CommandRunclock.class);
	        Commands.commandList.put("toggleseconds", CommandToggleseconds.class);
	        Commands.commandList.put("toggleingametime", CommandToggleingametime.class);
	        Commands.commandList.put("toggleampm", CommandToggleampm.class);
	        Commands.commandList.put("toggleblinking", CommandToggleblinking.class);
	        Commands.commandList.put("setcountdown", CommandSetcountdown.class);
	        Commands.commandList.put("disablecountdown", CommandDisablecountdown.class);
	        Commands.commandList.put("setstopwatch", CommandSetstopwatch.class);
	        Commands.commandList.put("disablestopwatch", CommandDisablestopwatch.class);
	        Commands.commandList.put("depth", CommandDepth.class);
	        Commands.commandList.put("list", CommandList.class);
	        Commands.commandList.put("reload", CommandReload.class);
	        Commands.commandList.put("settime", CommandSettime.class);
	        Commands.commandList.put("update", CommandUpdate.class);
	        Commands.commandList.put("off", CommandOff.class);
	        Commands.commandList.put("help", CommandHelp.class);
	        Commands.commandList.put("?", CommandHelp.class);
	    }
	    
	    public Commands(final DigitalClockSystem i) {
	        this.i = i;
	    }
	    
	    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
	        if (command.getName().equalsIgnoreCase("digitalclock") || command.getName().equalsIgnoreCase("dc")) {
	            final String usedcmd = command.getName().toLowerCase();
	            if (args.length > 0) {
	                if (sender instanceof Player) {
	                    final Player player = (Player)sender;
	                    if (Commands.commandList.containsKey(args[0].toLowerCase())) {
	                        ICommand ic = null;
	                        try {
	                            final Class<?> clazz = Commands.class.getClassLoader().loadClass(Commands.commandList.get(args[0].toLowerCase()).getName());
	                            ic = (ICommand)clazz.newInstance();
	                        }
	                        catch (ClassNotFoundException ex) {}
	                        catch (InstantiationException ex2) {}
	                        catch (IllegalAccessException e) {
	                            System.err.println("Problem occured when processing command: " + e.getMessage());
	                            return true;
	                        }
	                        finally {
	                            if (ic != null) {
	                                this.processCommand(usedcmd, player, args, ic);
	                            }
	                        }
	                        if (ic != null) {
	                            this.processCommand(usedcmd, player, args, ic);
	                        }
	                    }
	                    else {
	                        player.sendMessage(ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " This argument doesn't exist. Show '/" + usedcmd + " help' for more info.");
	                    }
	                }
	                else {
	                    final String lowerCase;
	                    switch (lowerCase = args[0].toLowerCase()) {
	                        case "reload": {
	                            this.processCommand(usedcmd, null, args, new CommandReload());
	                            return true;
	                        }
	                        case "update": {
	                            this.processCommand(usedcmd, null, args, new CommandUpdate());
	                            return true;
	                        }
	                        case "off": {
	                            this.processCommand(usedcmd, null, args, new CommandOff());
	                            return true;
	                        }
	                        default:
	                            break;
	                    }
	                    sender.sendMessage(ChatColor.DARK_RED + DigitalClockSystem.getMessagePrefix() + ChatColor.RED + " This command can be executed only from the game!");
	                }
	            }
	            else {
	               // sender.sendMessage(ChatColor.GREEN + "---- DigitalClock v" + this.i.getDescription().getVersion() + " ----\nAuthor: PerwinCZ\nRun command '/" + usedcmd + " help' in game for commands list.");
	            }
	            return true;
	        }
	        return false;
	    }
	    
	    private void processCommand(final String usedcmd, final Player player, final String[] args, final ICommand ic) {
	        if (args.length != ic.getArgsSize()) {
	            player.sendMessage(ic.reactBadArgsSize(usedcmd));
	        }
	        else if (player != null && !player.hasPermission(ic.getPermissionName()) && !player.isOp()) {
	            player.sendMessage(ic.reactNoPermissions());
	        }
	        else if (ic.checkClockExistence() && this.i.getClocksConf().getKeys(false).contains(args[1]) != ic.neededClockExistenceValue()) {
	            player.sendMessage(ic.reactBadClockList(args[1]));
	        }
	        else if (ic.specialCondition(this.i, player, args)) {
	            ic.specialConditionProcess(this.i, player, args);
	        }
	        else {
	            ic.process(this.i, player, args);
	        }
	    }
}
