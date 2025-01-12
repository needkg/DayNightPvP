package com.needkg.daynightpvp.placeholder;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.files.ConfigFile;
import com.needkg.daynightpvp.files.LangFile;

public class PlaceholderHandler {
    private final ConfigFile configFile;
    private final LangFile langFile;

    public PlaceholderHandler(ConfigFile configFile, LangFile langFile) {
        this.configFile = configFile;
        this.langFile = langFile;
    }

    public void register() {
        if (DayNightPvP.placeHolderIsPresent) {
            new PvpStatusPlaceholder(langFile, configFile).register();
        }
    }

    public void unregister() {
        if (DayNightPvP.placeHolderIsPresent) {
            new PvpStatusPlaceholder(langFile, configFile).unregister();
        }
    }
}
