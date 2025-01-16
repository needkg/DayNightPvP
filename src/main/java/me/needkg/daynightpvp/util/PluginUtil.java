package me.needkg.daynightpvp.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PluginUtil {

    public static boolean isPluginInstalled(String pluginName) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        return plugin != null;
    }


}
