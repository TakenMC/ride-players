package org.taken.ridePlayers.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RidePlayerCommand implements CommandExecutor {
    private static final String PREFIX = ChatColor.GOLD + "[" + ChatColor.GREEN + "RidePlayers" + ChatColor.GOLD + "] ";

    private String success(String message) {
        return PREFIX + ChatColor.GREEN + message;
    }

    private String error(String message) {
        return PREFIX + ChatColor.RED + message;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1 || args.length > 2) {
            sender.sendMessage(error("Usage: /rideplayer <player> [player]"));
            return true;
        }

        Player rider;
        Player target;

        if (args.length == 1) {
            // One argument: command executor rides the target
            if (!sender.hasPermission("rideplayers.ride")) {
                sender.sendMessage(error("You don't have permission to use this command."));
                return true;
            }

            if (!(sender instanceof Player)) {
                sender.sendMessage(error("This command can only be used by players when specifying one argument."));
                return true;
            }
            rider = (Player) sender;
            target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                rider.sendMessage(error("Player not found: " + args[0]));
                return true;
            }

            if (target.equals(rider)) {
                rider.sendMessage(error("You cannot ride yourself!"));
                return true;
            }
        } else {
            // Two arguments: first player rides the second player
            if (!sender.hasPermission("rideplayers.ride.others")) {
                sender.sendMessage(error("You don't have permission to make other players ride each other."));
                return true;
            }

            rider = Bukkit.getPlayer(args[0]);
            target = Bukkit.getPlayer(args[1]);

            if (rider == null) {
                sender.sendMessage(error("Player not found: " + args[0]));
                return true;
            }

            if (target == null) {
                sender.sendMessage(error("Player not found: " + args[1]));
                return true;
            }

            if (target.equals(rider)) {
                sender.sendMessage(error("A player cannot ride themselves!"));
                return true;
            }
        }

        target.addPassenger(rider);
        rider.sendMessage(success("You are now riding " + target.getName()));
        target.sendMessage(success(rider.getName() + " is now riding you!"));

        return true;
    }
}
