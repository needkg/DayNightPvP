package me.needkg.daynightpvp.world;

import org.bukkit.World;

import me.needkg.daynightpvp.world.configuration.WorldSettings;

public record DnpWorld(
    World bukkitWorld,
    WorldSettings settings
) {
}
