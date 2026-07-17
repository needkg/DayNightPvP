package com.needkg.daynightpvp.command.factory;

import com.needkg.daynightpvp.command.DaynighPvpCommand;
import com.needkg.daynightpvp.config.ResourceLoader;

public final class DaynighPvpFactory {

    // public static DaynighPvpCommand create(ResourceLoader languageResourceLoader) {

    //     final var ahj = new DaynighPvpCommand(getLanguageConfig(languageResourceLoader), null);

    //     return null;
    // }

    public static DaynighPvpCommand.Language getLanguageConfig(ResourceLoader resourceLoader) {

        final var reloaded = resourceLoader.getValue(
                String.class,
                "command.reload.success");

        return new DaynighPvpCommand.Language();
    }

}
