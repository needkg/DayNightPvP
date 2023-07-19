package com.needkg.daynightpvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ConsoleUtils {

    public static void warning(String warning) {
        Bukkit.getLogger().warning(warning);
    }

    public static void startMessage(JavaPlugin plugin) {
        Bukkit.getConsoleSender().sendMessage("   §9 _     _");
        Bukkit.getConsoleSender().sendMessage("   §9| \\|\\||_)" + "   §3DayNightPvP §8v" + plugin.getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage("   §9|_/| ||" + "         §8by §3needkg");
        Bukkit.getConsoleSender().sendMessage("");
    }
}
