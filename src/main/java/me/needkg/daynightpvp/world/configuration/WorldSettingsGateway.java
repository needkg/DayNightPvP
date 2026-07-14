package me.needkg.daynightpvp.world.configuration;

import me.needkg.daynightpvp.feature.config.ResourceFile;
import me.needkg.daynightpvp.world.DnpWorld;

import org.bukkit.Bukkit;

import java.util.Optional;
import java.util.Set;

public class WorldSettingsGateway {

    private final ResourceFile resourceFile;

    public WorldSettingsGateway(ResourceFile resourceFile) {
        this.resourceFile = resourceFile;
    }

    public Optional<Set<String>> findConfiguredWorlds() {
        return Optional.ofNullable(resourceFile.configuration().getConfigurationSection("").getKeys(false));
    }

    public Optional<DnpWorld> findDnpWorld(String worldName) {
        return Optional.ofNullable(new DnpWorld(Bukkit.getWorld(worldName), findDnpWorldSettings(worldName)));
    }

    private WorldSettings findDnpWorldSettings(String worldName) {
        return new WorldSettings(
                resourceFile.configuration().getBoolean("worlds." + worldName + ".enabled", true));
    }

}
