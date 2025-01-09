package org.callv2.daynightpvp.utils;

import org.bukkit.World;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class SearchUtils {

    public static boolean containsWorldName(Collection<?> collection, String worldName) {
        return collection.stream()
                .map(item -> item instanceof World ? ((World) item).getName() : item.toString())
                .anyMatch(name -> name.equals(worldName));
    }

}
