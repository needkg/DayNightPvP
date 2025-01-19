package me.needkg.daynightpvp.util.plugin;

import me.needkg.daynightpvp.DayNightPvP;
import org.bukkit.Bukkit;

public class StartupBanner {

    public static void display() {
        Bukkit.getConsoleSender().sendMessage("   §9 _     _");
        Bukkit.getConsoleSender().sendMessage("   §9| \\|\\||_)" + "   §3DayNightPvP §8v" + DayNightPvP.getInstance().getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage("   §9|_/| ||" + "         §8by §3needkg");
        Bukkit.getConsoleSender().sendMessage("");
    }

}
