package com.needkg.daynightpvp.command.factory;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.needkg.daynightpvp.ApplicationLifecycle;
import com.needkg.daynightpvp.command.ReloadPluginCommand;
import com.needkg.daynightpvp.config.ResourceLoader;
import com.needkg.daynightpvp.feature.AutoPvpFeature;
import com.needkg.daynightpvp.feature.AutoPvpFeature.Config;

public final class ReloadPluginFactory {

    public static ReloadPluginCommand create(
            ApplicationLifecycle lifecycle,
            ResourceLoader languageResourceLoader,
            ResourceLoader defaultLanguageResourceLoader) {

        return new ReloadPluginCommand(
                lifecycle,
                getLanguageConfig(languageResourceLoader, defaultLanguageResourceLoader));
    }

    private static ReloadPluginCommand.Language getLanguageConfig(
            ResourceLoader resourceLoader,
            ResourceLoader defaultResourceLoader) {

        final var combatDisabled = resourceLoader.getValue(
                String.class,
                "command.reload.success",
                defaultResourceLoader);

        return new ReloadPluginCommand.Language(combatDisabled);
    }

}
