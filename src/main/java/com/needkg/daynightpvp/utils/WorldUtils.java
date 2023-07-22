package com.needkg.daynightpvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class WorldUtils {

    public static void setTime(String worldName, int time) {
        World world = Bukkit.getWorld(worldName);
        assert world != null;
        world.setTime(time);
    }

    public static void setPvp(World world, boolean pvp) {
        world.setPVP(pvp);
    }

}
