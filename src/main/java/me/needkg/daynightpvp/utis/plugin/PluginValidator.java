package me.needkg.daynightpvp.utis.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PluginValidator {

    public static boolean exists(String pluginName) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        return plugin != null;
    }


}
