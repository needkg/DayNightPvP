package com.needkg.daynightpvp.utils;

import org.bukkit.Bukkit;

public class ConsoleUtils {

    public final String STARTUP_MESSAGE = "§7§lDayNightPvP §8§l-> §a§lStarting...";
    public final String STARTUP_END_MESSAGE = "§7§lDayNightPvP §8§l-> §a§lStarted!";
    public final String SHUTDOWN_MESSAGE = "§7§lDayNightPvP §8§l-> §c§lTurning Off...";
    public final String FILE_OUTDATED = "The {0} file was an outdated version. it has been replaced by the new version.";

    public void sendMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(message);
    }

    public void sendWarning(String warning) {
        Bukkit.getLogger().warning(warning);
    }

}
