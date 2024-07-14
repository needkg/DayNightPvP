package org.callv2.daynightpvp.utils;

import org.bukkit.World;

import java.util.List;

public class SearchUtils {

    public static boolean worldExistsInWorldList(List<World> list, String worldName) {
        return list.stream().map(World::getName).anyMatch(name -> name.contains(worldName));
    }

}
