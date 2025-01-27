package me.needkg.daynightpvp.utis.logging;

import me.needkg.daynightpvp.core.DependencyContainer;
import org.bukkit.Bukkit;

public class Logger {

    
    public static void debug(String message) {
        if (DependencyContainer.getInstance().getGlobalConfigurationManager().isDebugEnabled()) {
            Bukkit.getLogger().info("[DayNightPvP Debug] " + message);
        }
    }

    public static void verbose(String message) {
        if (DependencyContainer.getInstance().getGlobalConfigurationManager().isDebugVerbose()) {
            Bukkit.getLogger().info("[DayNightPvP Verbose] " + message);
        }
    }

    public static void warning(String message) {
        Bukkit.getLogger().warning(message);
    }

}
