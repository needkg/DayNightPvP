package me.needkg.daynightpvp.world;

import java.util.Set;

import me.needkg.daynightpvp.world.models.DnpWorld;

public class WorldManager {

    private Set<DnpWorld> worlds;

    public void setWorlds(Set<DnpWorld> worlds) {
        this.worlds = worlds;
    }

    public Set<DnpWorld> addWorld(DnpWorld world) {
        worlds.add(world);
        return worlds;
    }

    public Set<DnpWorld> removeWorld(DnpWorld world) {
        worlds.remove(world);
        return worlds;
    }

    public Set<DnpWorld> getWorlds() {
        return worlds;
    }

    public int getWorldCount() {
        return worlds.size();
    }

    public DnpWorld getWorld(String worldName) {
        return worlds.stream()
                .filter(world -> world.getName().equals(worldName))
                .findFirst()
                .orElse(null);
    }
    
}
