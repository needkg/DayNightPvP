package org.callv2.daynightpvp.placeholder;

import org.callv2.daynightpvp.DayNightPvP;
import org.callv2.daynightpvp.files.ConfigFile;
import org.callv2.daynightpvp.files.LangFile;

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
