package com.needkg.daynightpvp.command.factory;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.needkg.daynightpvp.ApplicationLifecycle;
import com.needkg.daynightpvp.command.ReloadPluginCommand;
import com.needkg.daynightpvp.config.ResourceLoader;

public final class ReloadPluginFactory {

    public static ReloadPluginCommand.Language getLanguageConfig(ResourceLoader resourceLoader) {

        final var reloaded = resourceLoader.getValue(
                String.class,
                "command.reload.success");

        return new ReloadPluginCommand.Language(reloaded);
    }

}
