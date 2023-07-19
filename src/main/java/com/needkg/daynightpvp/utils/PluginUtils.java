package com.needkg.daynightpvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PluginUtils {
    public static boolean isPluginInstalled(String pluginName, boolean configBoolean) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        return plugin != null && configBoolean;
    }

}
