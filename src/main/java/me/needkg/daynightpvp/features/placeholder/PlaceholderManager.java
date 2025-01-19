package me.needkg.daynightpvp.features.placeholder;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.features.placeholder.providers.WorldStateProvider;
import me.needkg.daynightpvp.utils.LoggingUtil;

public class PlaceholderManager {

    public void register() {
        if (DayNightPvP.isPlaceholderPresent) {
            LoggingUtil.sendVerboseMessage("Registering WorldStateProvider...");
            new WorldStateProvider().register();
        } else {
            LoggingUtil.sendDebugMessage("PlaceholderAPI is not installed, skipping registration...");
        }
    }

    public void unregister() {
        if (DayNightPvP.isPlaceholderPresent) {
            new WorldStateProvider().unregister();
        }
    }

    public void restart() {
        unregister();
        register();
    }
}
