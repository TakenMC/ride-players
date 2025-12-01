package org.taken.ridePlayers;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.taken.ridePlayers.commands.RidePlayerCommand;

public final class RidePlayers extends JavaPlugin {
    public final static String PLUGIN_NAME = "RidePlayers";
    public final static Logger LOGGER = Logger.getLogger(PLUGIN_NAME);

    @Override
    public void onEnable() {
        this.getCommand("rideplayer").setExecutor(new RidePlayerCommand());
        LOGGER.info("Ride players plugin loaded");
    }

    @Override
    public void onDisable() {
        LOGGER.info("Ride players plugin unloaded");
    }
}
