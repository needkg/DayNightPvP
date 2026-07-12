package me.needkg.daynightpvp.feature.config.providers.models;

import java.util.Set;

public record GlobalSettings(
    String language,
    Set<String> worlds
) {}
