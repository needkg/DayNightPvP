package me.needkg.daynightpvp.world.models;

import org.bukkit.World.Environment;

import me.needkg.daynightpvp.feature.config.providers.WorldSettingsProvider;

public class DnpWorld {

    private final String name;
    private Environment environment;
    private WorldSettingsProvider worldSettingsService;

    public DnpWorld(String name, Environment environment, WorldSettingsProvider worldSettingsService) {
        this.name = name;
        this.environment = environment;
        this.worldSettingsService = worldSettingsService;
    }

    public String getName() {
        return name;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public WorldSettingsProvider settings() {
        return worldSettingsService;
    }

}


