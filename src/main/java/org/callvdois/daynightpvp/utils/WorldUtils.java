package org.callvdois.daynightpvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.callvdois.daynightpvp.service.TimeChecker;

public class WorldUtils {

    public static void setTime(String worldName, int time) {
        World world = Bukkit.getWorld(worldName);
        assert world != null;
        world.setTime(time);
    }

    public static boolean checkPlayerIsInWorld(Player player) {
        String worldName = player.getWorld().getName();
        if (SearchUtils.worldExistsInList(TimeChecker.worldsPvpOff, worldName)) {
            return true;
        }
        return false;
    }

}
