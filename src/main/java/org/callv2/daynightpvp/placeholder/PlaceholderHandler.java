package org.callv2.daynightpvp.placeholder;

import org.callv2.daynightpvp.DayNightPvP;
import org.callv2.daynightpvp.files.ConfigFile;
import org.callv2.daynightpvp.files.LangFile;

public class PlaceholderHandler {

    private final LangFile langFile;
    private final ConfigFile configFile;

    public PlaceholderHandler(LangFile langFile, ConfigFile configFile) {
        this.langFile = langFile;
        this.configFile = configFile;
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
