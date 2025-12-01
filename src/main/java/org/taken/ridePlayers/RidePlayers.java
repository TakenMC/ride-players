package org.taken.ridePlayers;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.taken.ridePlayers.commands.RidePlayerCommand;
import org.taken.ridePlayers.listeners.PlayerInteractListener;

public final class RidePlayers extends JavaPlugin {
    public final static String PLUGIN_NAME = "RidePlayers";
    public final static Logger LOGGER = Logger.getLogger(PLUGIN_NAME);

    @Override
    public void onEnable() {
        this.getCommand("rideplayer").setExecutor(new RidePlayerCommand());
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        LOGGER.info("Ride players plugin loaded");
    }

    @Override
    public void onDisable() {
        LOGGER.info("Ride players plugin unloaded");
    }
}
