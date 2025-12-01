package org.taken.ridePlayers.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.taken.ridePlayers.RidePlayers;
import org.taken.ridePlayers.util.Messages;

public class RidePlayerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1 || args.length > 2) {
            sender.sendMessage(Messages.error("Usage: /rideplayer <player> [player]"));
            return true;
        }

        Player rider;
        Player target;

        if (args.length == 1) {
            // One argument: command executor rides the target
            if (!sender.hasPermission("rideplayers.ride")) {
                sender.sendMessage(Messages.error("You don't have permission to use this command."));
                return true;
            }

            if (!(sender instanceof Player)) {
                sender.sendMessage(
                        Messages.error("This command can only be used by players when specifying one argument."));
                return true;
            }
            rider = (Player) sender;
            target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                rider.sendMessage(Messages.error("Player not found: " + args[0]));
                return true;
            }

            if (target.equals(rider)) {
                rider.sendMessage(Messages.error("You cannot ride yourself!"));
                return true;
            }

            // Check distance
            int maxDistance = RidePlayers.getInstance().getMaxDistance();
            if (maxDistance >= 0 && (!rider.getWorld().equals(target.getWorld())
                    || rider.getLocation().distance(target.getLocation()) > maxDistance)) {
                rider.sendMessage(Messages.error("You are too far away from " + target.getName() + "!"));
                return true;
            }
        } else {
            // Two arguments: first player rides the second player
            if (!sender.hasPermission("rideplayers.ride.others")) {
                sender.sendMessage(Messages.error("You don't have permission to make other players ride each other."));
                return true;
            }

            rider = Bukkit.getPlayer(args[0]);
            target = Bukkit.getPlayer(args[1]);

            if (rider == null) {
                sender.sendMessage(Messages.error("Player not found: " + args[0]));
                return true;
            }

            if (target == null) {
                sender.sendMessage(Messages.error("Player not found: " + args[1]));
                return true;
            }

            if (target.equals(rider)) {
                sender.sendMessage(Messages.error("A player cannot ride themselves!"));
                return true;
            }

            // Check distance for ride others
            int maxDistance = RidePlayers.getInstance().getRideOthersMaxDistance();
            if (maxDistance >= 0 && (!rider.getWorld().equals(target.getWorld())
                    || rider.getLocation().distance(target.getLocation()) > maxDistance)) {
                sender.sendMessage(Messages.error(rider.getName() + " is too far away from " + target.getName() + "!"));
                return true;
            }
        }

        target.addPassenger(rider);
        rider.sendMessage(Messages.success("You are now riding " + target.getName()));
        target.sendMessage(Messages.success(rider.getName() + " is now riding you!"));

        return true;
    }
}
