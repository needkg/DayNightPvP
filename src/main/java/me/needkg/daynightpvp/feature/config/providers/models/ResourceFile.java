package me.needkg.daynightpvp.feature.config.providers.models;

import java.nio.file.Path;

import org.bukkit.configuration.file.FileConfiguration;

public record ResourceFile(
    Path path,
    FileConfiguration configuration
) {}
