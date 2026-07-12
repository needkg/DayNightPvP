package me.needkg.daynightpvp.world.models;

import org.bukkit.World;
import org.bukkit.World.Environment;

import me.needkg.daynightpvp.feature.config.models.WorldSettings;

public class DnpWorld {

    private final String name;
    private Environment environment;
    private WorldSettings settings;

    public DnpWorld(World world, WorldSettings settings) {
        this.name = world.getName();
        this.environment = world.getEnvironment();
        this.settings = settings;
    }

    public String getName() {
        return name;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public WorldSettings getSettings() {
        return settings;
    }

}
