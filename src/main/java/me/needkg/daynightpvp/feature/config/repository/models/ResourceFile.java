package me.needkg.daynightpvp.feature.config.repository.models;

import org.bukkit.configuration.file.FileConfiguration;

import java.nio.file.Path;

public record ResourceFile(
    Path path,
    FileConfiguration configuration
) {}
