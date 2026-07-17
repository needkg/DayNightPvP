package com.needkg.daynightpvp.feature.factory;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.needkg.daynightpvp.config.ResourceLoader;
import com.needkg.daynightpvp.feature.TitleNotificationFeature;

public final class TitleNotificationFactory {

    private TitleNotificationFactory() {
    }

    public static TitleNotificationFeature create(
            ResourceLoader worldResourceLoader,
            ResourceLoader languageResourceLoader,
            ResourceLoader defaultLanguageResourceLoader) {

        final var worldConfigs = worldResourceLoader
                .getKeys(false)
                .stream()
                .map(worldName -> Map.entry(worldName, getConfig(worldResourceLoader, worldName)))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

        return new TitleNotificationFeature(
                worldConfigs,
                getLanguageConfig(languageResourceLoader, defaultLanguageResourceLoader));
    }

    private static TitleNotificationFeature.Config getConfig(ResourceLoader resourceLoader, String worldName) {

        final var titleEnabled = resourceLoader.getValue(
                Boolean.class,
                worldName + ".notifications.title.enabled",
                false);

        final var fadeIn = resourceLoader.getValue(
                Integer.class,
                worldName + ".notifications.title.fade-in",
                0);

        final var stay = resourceLoader.getValue(
                Integer.class,
                worldName + ".notifications.title.stay",
                20);

        final var fadeOut = resourceLoader.getValue(
                Integer.class,
                worldName + ".notifications.title.fade-out",
                20);

        return new TitleNotificationFeature.Config(
                titleEnabled,
                fadeIn,
                stay,
                fadeOut);
    }

    private static TitleNotificationFeature.Language getLanguageConfig(
            ResourceLoader resourceLoader,
            ResourceLoader defaultResourceLoader) {

        final var dayTitle = resourceLoader.getValue(
                String.class,
                "title.day-title",
                defaultResourceLoader);

        final var daySubtitle = resourceLoader.getValue(
                String.class,
                "title.day-subtitle",
                defaultResourceLoader);

        final var nightTitle = resourceLoader.getValue(
                String.class,
                "title.day-night-title",
                defaultResourceLoader);

        final var nightSubtitl = resourceLoader.getValue(
                String.class,
                "title.night-subtitle",
                defaultResourceLoader);

        return new TitleNotificationFeature.Language(
                dayTitle,
                daySubtitle,
                nightTitle,
                nightSubtitl);
    }

}
