package me.needkg.daynightpvp.util.search;

import org.bukkit.World;

import java.util.Collection;

public class WorldCollectionSearcher {

    public static boolean containsWorld(Collection<World> collection, World world) {
        return collection.stream()
                .map(World::getName)
                .anyMatch(name -> name.equals(world.getName()));
    }

}
