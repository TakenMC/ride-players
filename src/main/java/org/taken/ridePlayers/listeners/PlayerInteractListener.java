package org.taken.ridePlayers.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.taken.ridePlayers.util.Messages;

public class PlayerInteractListener implements Listener {
    private static final int MAX_DISTANCE = 15;

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        // Only handle main hand interactions to avoid double triggering
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Player rider = event.getPlayer();

        // Check if the player is right-clicking another player
        if (!(event.getRightClicked() instanceof Player)) {
            return;
        }

        Player target = (Player) event.getRightClicked();

        // Check if the player has an empty hand
        ItemStack itemInHand = rider.getInventory().getItemInMainHand();
        if (itemInHand != null && itemInHand.getType() != Material.AIR) {
            return;
        }

        // Check permissions
        if (!rider.hasPermission("rideplayers.ride")) {
            return;
        }

        // Prevent self-riding (shouldn't happen but just in case)
        if (target.equals(rider)) {
            rider.sendMessage(Messages.error("You cannot ride yourself!"));
            return;
        }

        // Check distance
        if (!rider.getWorld().equals(target.getWorld())
                || rider.getLocation().distance(target.getLocation()) > MAX_DISTANCE) {
            rider.sendMessage(Messages.error("You are too far away from " + target.getName() + "!"));
            return;
        }

        // Find the topmost player in the stack
        Player topmostPlayer = getTopmostPlayer(target);

        // Prevent circular riding
        if (topmostPlayer.equals(rider)) {
            rider.sendMessage(Messages.error("You cannot create a circular ride chain!"));
            return;
        }

        // Mount the rider on the topmost player
        topmostPlayer.addPassenger(rider);
        rider.sendMessage(Messages.success("You are now riding " + topmostPlayer.getName()));
        topmostPlayer.sendMessage(Messages.success(rider.getName() + " is now riding you!"));

        // Cancel the event to prevent other interactions
        event.setCancelled(true);
    }

    /**
     * Finds the topmost player in a riding stack
     */
    private Player getTopmostPlayer(Player player) {
        Player topmost = player;
        while (!topmost.getPassengers().isEmpty()) {
            // Check if any passenger is a player
            Player nextPlayer = null;
            for (Entity passenger : topmost.getPassengers()) {
                if (passenger instanceof Player) {
                    nextPlayer = (Player) passenger;
                    break;
                }
            }
            if (nextPlayer == null) {
                break;
            }
            topmost = nextPlayer;
        }
        return topmost;
    }
}
