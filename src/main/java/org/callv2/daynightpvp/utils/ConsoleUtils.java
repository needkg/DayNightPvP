package org.callv2.daynightpvp.utils;

import org.bukkit.Bukkit;
import org.callv2.daynightpvp.DayNightPvP;

public class ConsoleUtils {

    public static void sendWarningMessage(String message) {
        Bukkit.getLogger().warning(message);
    }

    public static void sendInfoMessage(String message) {
        Bukkit.getLogger().info(message);
    }

    public static void sendStartupMessage() {
        Bukkit.getConsoleSender().sendMessage("   §9 _     _");
        Bukkit.getConsoleSender().sendMessage("   §9| \\|\\||_)" + "   §3DayNightPvP §8v" + DayNightPvP.getInstance().getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage("   §9|_/| ||" + "         §8by §3needkg");
        Bukkit.getConsoleSender().sendMessage("");
    }
}
