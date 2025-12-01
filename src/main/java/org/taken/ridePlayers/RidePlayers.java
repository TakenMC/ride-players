package org.taken.ridePlayers;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.taken.ridePlayers.commands.RidePlayerCommand;
import org.taken.ridePlayers.listeners.PlayerInteractListener;

public final class RidePlayers extends JavaPlugin {
    public final static String PLUGIN_NAME = "RidePlayers";
    public final static Logger LOGGER = Logger.getLogger(PLUGIN_NAME);
    private static RidePlayers instance;

    @Override
    public void onEnable() {
        instance = this;

        // Save default config if it doesn't exist
        saveDefaultConfig();

        this.getCommand("rideplayer").setExecutor(new RidePlayerCommand());
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        LOGGER.info("Ride players plugin loaded");
    }

    @Override
    public void onDisable() {
        LOGGER.info("Ride players plugin unloaded");
    }

    public static RidePlayers getInstance() {
        return instance;
    }

    public int getMaxDistance() {
        return getConfig().getInt("max-distance", 15);
    }

    public int getRideOthersMaxDistance() {
        return getConfig().getInt("ride-others-max-distance", -1);
    }
}
