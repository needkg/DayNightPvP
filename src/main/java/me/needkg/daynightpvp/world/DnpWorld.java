package me.needkg.daynightpvp.world;

import org.bukkit.World;

import me.needkg.daynightpvp.world.settings.WorldSettings;

public record DnpWorld(
    World bukkitWorld,
    WorldSettings settings
) {
}
