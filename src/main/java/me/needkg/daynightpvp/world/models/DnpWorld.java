package me.needkg.daynightpvp.world.models;

import org.bukkit.World.Environment;

import me.needkg.daynightpvp.feature.config.services.WorldSettingsService;

public class DnpWorld {

    private final String name;
    private Environment environment;
    private WorldSettingsService worldSettingsService;

    public DnpWorld(String name, Environment environment, WorldSettingsService worldSettingsService) {
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

    public WorldSettingsService settings() {
        return worldSettingsService;
    }

}


