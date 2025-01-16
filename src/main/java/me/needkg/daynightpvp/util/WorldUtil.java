package me.needkg.daynightpvp.util;

import me.needkg.daynightpvp.task.WorldStateController;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WorldUtil {

    public static boolean isPlayerInDayWorld(Player player) {
        String worldName = player.getWorld().getName();
        return SearchUtil.containsWorldName(WorldStateController.dayWorlds, worldName);
    }

    public static boolean isWorldValid(String worldName) {
        return Bukkit.getWorlds().contains(Bukkit.getWorld(worldName));
    }

}