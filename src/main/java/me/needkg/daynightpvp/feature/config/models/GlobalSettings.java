package me.needkg.daynightpvp.feature.config.models;

import java.util.Set;

public record GlobalSettings(
    String language,
    Set<String> worlds
) {}
