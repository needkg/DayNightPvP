package me.needkg.daynightpvp.utils;

import org.bukkit.World;

import java.util.Collection;

public class SearchUtil {

    public static boolean containsWorldName(Collection<?> collection, String worldName) {
        return collection.stream()
                .map(item -> item instanceof World ? ((World) item).getName() : item.toString())
                .anyMatch(name -> name.equals(worldName));
    }

}
