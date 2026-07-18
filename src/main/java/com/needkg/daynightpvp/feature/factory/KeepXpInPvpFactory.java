package com.needkg.daynightpvp.feature.factory;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.needkg.daynightpvp.config.ResourceLoader;
import com.needkg.daynightpvp.feature.AutoPvpFeature;
import com.needkg.daynightpvp.feature.KeepXpInPvpFeature;

public final class KeepXpInPvpFactory {

    private KeepXpInPvpFactory() {
    }

    public static KeepXpInPvpFeature create(
            ResourceLoader worldResourceLoader,
            ResourceLoader languageResourceLoader) {

        final var worldConfigs = worldResourceLoader
                .getKeys(false)
                .stream()
                .map(worldName -> Map.entry(worldName, getConfig(worldResourceLoader, worldName)))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

        return new KeepXpInPvpFeature(
                worldConfigs,
                getLanguageConfig(languageResourceLoader));
    }

    private static KeepXpInPvpFeature.Config getConfig(ResourceLoader resourceLoader, String worldName) {

        final var dayEnd = resourceLoader.getValue(
                Long.class,
                worldName + ".pvp.day-end",
                12900L);

        final String whenString = resourceLoader.getValue(
                String.class,
                worldName + ".pvp.keep-on-death.exp.when",
                "night");

        KeepXpInPvpFeature.Config.When when = KeepXpInPvpFeature.Config.When.valueOf(whenString.toUpperCase());

        final var enabled = resourceLoader.getValue(
                Boolean.class,
                worldName + ".pvp.keep-on-death.exp.enabled",
                false);

        final var defaultLosePercent = resourceLoader.getValue(
                Integer.class,
                worldName + ".pvp.keep-on-death.exp.lose-percent.default",
                0);

        final Map<String, Integer> losePercentGroups = resourceLoader.getValue(
                Map.class,
                worldName + ".pvp.keep-on-death.exp.lose-percent.groups",
                Map.of());

        return new KeepXpInPvpFeature.Config(
                enabled,
                dayEnd,
                when,
                defaultLosePercent,
                losePercentGroups);
    }

    private static KeepXpInPvpFeature.Language getLanguageConfig(
            ResourceLoader resourceLoader) {

        final var lose = resourceLoader.getValue(
                String.class,
                "keep-on-death.xp.lose");

        final var noLose = resourceLoader.getValue(
                String.class,
                "keep-on-death.xp.no-lose");

        return new KeepXpInPvpFeature.Language(lose, noLose);
    }

}
