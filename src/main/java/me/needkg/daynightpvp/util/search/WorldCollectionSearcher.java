package me.needkg.daynightpvp.util.search;

import org.bukkit.World;

import java.util.Collection;

public class WorldCollectionSearcher {

    public static boolean containsWorld(Collection<?> collection, String worldName) {
        return collection.stream()
                .map(item -> item instanceof World ? ((World) item).getName() : item.toString())
                .anyMatch(name -> name.equals(worldName));
    }

}
