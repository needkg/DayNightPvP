package org.callv2.daynightpvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.callv2.daynightpvp.runnables.AutomaticPvp;

public class WorldUtils {

    public static boolean checkPlayerIsInWorld(Player player) {
        String worldName = player.getWorld().getName();
        return SearchUtils.worldExistsInWorldListSetString(AutomaticPvp.nightWorlds, worldName);
    }

    public static boolean checkWorldIsValid(String worldName) {
        return Bukkit.getWorlds().contains(Bukkit.getWorld(worldName));
    }

}
