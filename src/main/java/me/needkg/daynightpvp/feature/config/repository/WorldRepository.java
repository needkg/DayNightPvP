package me.needkg.daynightpvp.feature.config.repository;

import me.needkg.daynightpvp.feature.config.repository.models.ResourceFile;
import me.needkg.daynightpvp.feature.config.repository.models.WorldSettings;
import me.needkg.daynightpvp.world.models.DnpWorld;
import org.bukkit.Bukkit;

import java.util.Optional;
import java.util.Set;

public class WorldRepository {

    private final ResourceFile resourceFile;

    public WorldRepository(ResourceFile resourceFile) {
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
