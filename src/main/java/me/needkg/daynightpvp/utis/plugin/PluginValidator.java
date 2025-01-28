package me.needkg.daynightpvp.utis.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PluginValidator {

    private static boolean exists(String pluginName) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        return plugin != null;
    }

    public static boolean isVaultPresent() {
        return exists("Vault");
    }

    public static boolean isWorldGuardPresent() {
        return exists("WorldGuard");
    }

    public static boolean isGriefPresent() {
        return exists("GriefPrevention");
    }

    public static boolean isPlaceholderPresent() {
        return exists("PlaceholderAPI");
    }

}
