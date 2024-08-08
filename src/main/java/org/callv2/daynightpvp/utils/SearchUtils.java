package org.callv2.daynightpvp.utils;

import org.bukkit.World;

import java.util.List;
import java.util.Set;

public class SearchUtils {

    public static boolean worldExistsInWorldListSetString(Set<String> list, String worldName) {
        return list.contains(worldName);
    }

    public static boolean worldExistsInWorldListSetString(List<World> list, String worldName) {
        return list.stream().map(World::getName).anyMatch(name -> name.contains(worldName));
    }

}
