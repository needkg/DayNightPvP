package org.callv2.daynightpvp.utils;

import org.bukkit.entity.Player;
import org.callv2.daynightpvp.runnables.AutomaticPvp;

public class WorldUtils {

    public static boolean checkPlayerIsInWorld(Player player) {
        String worldName = player.getWorld().getName();
        return SearchUtils.worldExistsInWorldList(AutomaticPvp.worldsPvpOff, worldName);
    }

}
