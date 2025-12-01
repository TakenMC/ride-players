package org.taken.ridePlayers.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.taken.ridePlayers.util.Messages;

public class KickOffPlayerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.error("This command can only be used by players."));
            return true;
        }

        Player player = (Player) sender;

        // Check if anyone is riding the player
        if (player.getPassengers().isEmpty()) {
            player.sendMessage(Messages.error("Nobody is riding you!"));
            return true;
        }

        // Remove only the first (direct) passenger
        Entity passenger = player.getPassengers().get(0);
        player.removePassenger(passenger);

        if (passenger instanceof Player) {
            Player kickedPlayer = (Player) passenger;
            kickedPlayer.sendMessage(Messages.error("You have been kicked off " + player.getName() + "!"));
            player.sendMessage(Messages.success("Kicked off " + kickedPlayer.getName() + "!"));
        } else {
            player.sendMessage(Messages.success("Removed passenger!"));
        }

        return true;
    }
}
