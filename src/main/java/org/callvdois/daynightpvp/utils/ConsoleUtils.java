package org.callvdois.daynightpvp.utils;

import org.bukkit.Bukkit;
import org.callvdois.daynightpvp.DayNightPvP;

public class ConsoleUtils {

    public void sendWarningMessage(String message) {
        Bukkit.getLogger().warning(message);
    }

    public void sendStartupMessage() {
        Bukkit.getConsoleSender().sendMessage("   §9 _     _");
        Bukkit.getConsoleSender().sendMessage("   §9| \\|\\||_)" + "   §3DayNightPvP §8v" + DayNightPvP.getInstance().getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage("   §9|_/| ||" + "         §8by §3needkg");
        Bukkit.getConsoleSender().sendMessage("");
    }
}
