package org.callvdois.daynightpvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.callvdois.daynightpvp.runnables.AutomaticPvp;

public class WorldUtils {

    public SearchUtils searchUtils;

    public WorldUtils() {
        searchUtils = new SearchUtils();
    }

    public boolean checkPlayerIsInWorld(Player player) {
        String worldName = player.getWorld().getName();
        return searchUtils.worldExistsInWorldList(AutomaticPvp.worldsPvpOff, worldName);
    }

}
