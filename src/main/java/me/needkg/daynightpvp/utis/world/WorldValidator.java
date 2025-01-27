package me.needkg.daynightpvp.utis.world;

import org.bukkit.Bukkit;

public class WorldValidator {

    public static boolean exists(String worldName) {
        return Bukkit.getWorlds().contains(Bukkit.getWorld(worldName));
    }

}
