package com.needkg.daynightpvp.utils;

import com.needkg.daynightpvp.DayNightPvP;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ConsoleUtils {

    public final String FILE_OUTDATED = "[DayNightPvP] The {0} file was an outdated version. it has been replaced by the new version.";

    public void sendMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(message);
    }

    public void sendWarning(String warning) {
        Bukkit.getLogger().warning(warning);
    }

    public void sendStartMessage(JavaPlugin plugin) {
        Bukkit.getConsoleSender().sendMessage("   §9 _     _");
        Bukkit.getConsoleSender().sendMessage("   §9| \\|\\||_)" + "   §3DayNightPvP §8v" + plugin.getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage("   §9|_/| ||" + "         §8by §3needkg");
        Bukkit.getConsoleSender().sendMessage("");
    }
}
