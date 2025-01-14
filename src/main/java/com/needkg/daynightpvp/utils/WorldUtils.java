package com.needkg.daynightpvp.utils;

import com.needkg.daynightpvp.runnables.AutomaticPvp;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WorldUtils {

    public static boolean isPlayerInWorld(Player player) {
        String worldName = player.getWorld().getName();
        return SearchUtils.containsWorldName(AutomaticPvp.dayWorlds, worldName);
    }

    public static boolean isWorldValid(String worldName) {
        return Bukkit.getWorlds().contains(Bukkit.getWorld(worldName));
    }

}