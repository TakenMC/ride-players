package org.taken.ridePlayers.util;

import org.bukkit.ChatColor;

public class Messages {
    private static final String PREFIX = ChatColor.GOLD + "[" + ChatColor.GREEN + "RidePlayers" + ChatColor.GOLD + "] ";

    public static String success(String message) {
        return PREFIX + ChatColor.GREEN + message;
    }

    public static String error(String message) {
        return PREFIX + ChatColor.RED + message;
    }
}
