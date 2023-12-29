package org.callvdois.daynightpvp.utils;

import org.bukkit.Bukkit;
import org.callvdois.daynightpvp.DayNightPvP;

public class ConsoleUtils {

    public static void warning(String warning) {
        Bukkit.getLogger().warning(warning);
    }

    public static void startMessage() {
        Bukkit.getConsoleSender().sendMessage("   §9 _     _");
        Bukkit.getConsoleSender().sendMessage("   §9| \\|\\||_)" + "   §3DayNightPvP §8v" + DayNightPvP.getInstance().getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage("   §9|_/| ||" + "         §8by §3needkg");
        Bukkit.getConsoleSender().sendMessage("");
    }
}
