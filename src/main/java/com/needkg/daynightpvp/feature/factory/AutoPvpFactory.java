package com.needkg.daynightpvp.feature.factory;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.needkg.daynightpvp.config.ResourceLoader;
import com.needkg.daynightpvp.feature.AutoPvpFeature;
import com.needkg.daynightpvp.feature.AutoPvpFeature.Config;

public final class AutoPvpFactory {

    private AutoPvpFactory() {
    }

    public static AutoPvpFeature create(
            ResourceLoader worldResourceLoader,
            ResourceLoader languageResourceLoader) {

        final var worldConfigs = worldResourceLoader
                .getKeys(false)
                .stream()
                .map(worldName -> Map.entry(worldName, getConfig(worldResourceLoader, worldName)))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

        return new AutoPvpFeature(
                worldConfigs,
                getLanguageConfig(languageResourceLoader));
    }

    private static AutoPvpFeature.Config getConfig(ResourceLoader resourceLoader, String worldName) {

        final var enabled = resourceLoader.getValue(
                Boolean.class,
                worldName + ".pvp.auto.enabled",
                false);

        final var dayEnd = resourceLoader.getValue(
                Long.class,
                worldName + ".pvp.day-end",
                12900L);

        return new AutoPvpFeature.Config(
                enabled,
                dayEnd);
    }

    private static AutoPvpFeature.Language getLanguageConfig(
            ResourceLoader resourceLoader) {

        final var combatDisabled = resourceLoader.getValue(
                String.class,
                "auto-pvp.combat-disabled");

        final var playerImmune = resourceLoader.getValue(
                String.class,
                "auto-pvp.player-immune");

        final var selfImmune = resourceLoader.getValue(
                String.class,
                "auto-pvp.self-immune");

        return new AutoPvpFeature.Language(
                combatDisabled,
                playerImmune,
                selfImmune);
    }

}
